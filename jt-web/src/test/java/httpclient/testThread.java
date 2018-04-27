package httpclient;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class testThread {
    @Test
    public void test01(){
        Map<String, String> map = new HashMap<>();
        String aa = "我是key";
        map.put(aa, "我是aa的value");
        System.out.println("我是正常的aa在取值   ："+map.get(aa));
        aa = null;
        System.out.println("我是=null后的aa在取值："+map.get(aa));
        System.out.println("我是冒充的key:        "+map.get("我是key"));
    }
}
