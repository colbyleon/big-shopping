package redis;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataOutputStreamTest {
    private static final ByteArrayOutputStream outData = new ByteArrayOutputStream();
    public static void main(String[] args) throws IOException {
        writeData();
        readData();


    }



    private static void writeData() throws IOException {
        DataOutputStream out = new DataOutputStream(outData);
        out.writeUTF("序号");
        out.writeInt(123);
        out.writeUTF("名字");
        out.writeUTF("dubbo");
        out.writeUTF("价格");
        out.writeDouble(181.56);
        out.writeFloat(23.01f);

        out.close();
    }

    private static void readData() throws IOException {
        byte[] bytes = outData.toByteArray();
        DataInputStream in = new DataInputStream(new ByteInputStream(bytes,bytes.length));
        String s = in.readUTF();
        System.out.println(s);
        int i = in.readInt();
        System.out.println(i);
        String s1 = in.readUTF();
        System.out.println(s1);
        String s2 = in.readUTF();
        System.out.println(s2);
        System.out.println(in.readUTF());
        System.out.println(in.readDouble());
        System.out.println(in.readFloat());

        in.close();
    }
}
