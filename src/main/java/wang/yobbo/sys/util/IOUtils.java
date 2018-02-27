package wang.yobbo.sys.util;

import java.io.*;

/**
 * IO工具类
 * @author zhaolong
 *
 */
public class IOUtils {

    public IOUtils() {
    }

    public static byte[] fileTobytes(String path) {
        return fileTobytes(new File(path));
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static void byteToOutputStream(byte[] datas, OutputStream os){
        if (datas.length < 3 || os==null)
            return;
        try {
            os.write(datas,0,datas.length);
            os.flush();
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] fileTobytes(File file) {
        byte[] data = null;
        FileInputStream input = null;
        try {
            input = new FileInputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
}