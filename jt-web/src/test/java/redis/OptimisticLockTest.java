package redis;

import redis.clients.jedis.JedisCluster;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OptimisticLockTest {
    final static JedisCluster jedisCluster = RedisClusterUtils.getJedisCluster();

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        long startTime = System.currentTimeMillis();

        initPrduct();
        initClient(countDownLatch);

        countDownLatch.await();
        printResult();
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        System.out.println("程序运行时：" + time + "ms");
    }

    private static void printResult() {
        int i = 1;
        String name = null;
        while (true) {
            name = jedisCluster.rpop("name");
            if (name == null) break;
            System.out.println(name + "抢到了第" + i++ + "个商品");
        }

        System.out.println("共卖出" + jedisCluster.get("saleNum") + "个商品");
    }

    private static void initClient(CountDownLatch countDownLatch) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        long clientNum = countDownLatch.getCount();
        try {
            for (int i = 0; i < clientNum; i++) {
                threadPool.execute(new ClientThread(i,countDownLatch));
            }
        } catch (Exception e) {
        }
        threadPool.shutdown();
    }

    private static void initPrduct() {
        int prdNum = 10;
        String key = "prdNum";
        String clientList = "name";

        if (jedisCluster.exists(key)) {
            jedisCluster.del(key);
        }

        if (jedisCluster.exists(clientList)) {
            jedisCluster.del(clientList);
        }

        if (jedisCluster.exists("saleNum")) {
            jedisCluster.del("saleNum");
        }

        jedisCluster.set("saleNum", 0 + "");
        jedisCluster.set(key, prdNum + "");
    }

}

class ClientThread implements Runnable {
    private final CountDownLatch countDownLatch;
    JedisCluster jedisCluster = OptimisticLockTest.jedisCluster;
    String key = "prdNum";// 商品主键
    String clientName;
    String saleNum = "saleNum";


    ClientThread(int num, CountDownLatch countDownLatch) {
        clientName = "编号=" + num;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        int sNum = Integer.parseInt(jedisCluster.get(key));
        if (sNum <= 0) {
            countDownLatch.countDown();
            return;
        }
        long num = jedisCluster.decr(key);
        if (num >= 0) {
            System.out.println("还剩" + num);
            jedisCluster.incr(saleNum);
            jedisCluster.lpush("name", clientName);
        }
        countDownLatch.countDown();
    }

}