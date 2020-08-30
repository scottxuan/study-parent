package scottxuan.io.inputstream;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * FileInputStream最普通的读取
 * 不含效率的优化
 */
@Slf4j
public class FileInputStreamExample1 {
    public static void main(String[] args) throws IOException {
        File file = new File(FileInputStreamExample1.class.getResource("/test.txt").getPath());
        if (!file.exists()) {
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buff = new byte[1024];
        int n;
        StringBuilder builder = new StringBuilder();
        while ((n = fileInputStream.read(buff)) != -1) {
            builder.append(new String(buff,0,n));
        }
        log.info("builder : \r\n{}",builder.toString());
    }
}
