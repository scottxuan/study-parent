package scottxuan.io.inputstream;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 */
@Slf4j
public class BufferedInputStreamExample1 {
    public static void main(String[] args) throws IOException {
        File file = new File(BufferedInputStreamExample1.class.getResource("/test.txt").getPath());
        if (!file.exists()) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        int n;
        byte[] buffer = new byte[1024];
        StringBuilder builder = new StringBuilder();
        while ((n = bufferedInputStream.read(buffer)) != -1) {
            builder.append(new String(buffer, 0, n));
        }
        log.info("builder : \r\n{}",builder.toString());
    }
}
