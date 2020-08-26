package scottxuan.io.inputstream;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class InputStreamExample3 {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        try(InputStream inputStream = InputStreamExample3.class.getResourceAsStream("/111.png")){
            try(OutputStream outputStream = new FileOutputStream("C:\\Users\\scottxuan\\Desktop\\222.png")){
                byte[] bytes = new byte[1024];
                int n;
                while ((n = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes,0,n);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        log.info("耗时:{}毫秒",endTime-startTime);
    }
}
