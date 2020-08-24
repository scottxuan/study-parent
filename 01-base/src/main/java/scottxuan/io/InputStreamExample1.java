package scottxuan.io;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class InputStreamExample1 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        InputStream inputStream = new FileInputStream("C:\\Users\\scottxuan\\Desktop\\111.pdf");
        OutputStream outputStream = new FileOutputStream("C:\\Users\\scottxuan\\Desktop\\222.pdf");
        byte[] buffer = new byte[1024];
        int n;
        while ((n = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer,0,n);
        }
        inputStream.close();
        outputStream.close();
        long endTime = System.currentTimeMillis();
        log.info("耗时:{}毫秒",endTime-startTime);
    }
}
