package scottxuan.io.reader;

import lombok.extern.slf4j.Slf4j;
import scottxuan.io.file.FileExample1;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class FileReaderExample1 {
    public static void main(String[] args) {
        FileReader reader = null;
        try {
            String path1 = FileReaderExample1.class.getResource("/test.txt").getPath();
            reader = new FileReader(path1);
            log.info("reader1 success");

            String path2 = Objects.requireNonNull(FileExample1.class.getClassLoader().getResource("test.txt")).getPath();
            reader = new FileReader(path2);
            log.info("reader2 success");

            //此处参考FileExample1中File路径
            //reader = new FileReader(new File("path"));

            char[] buf = new char[1024];
            int len;
            StringBuilder builder = new StringBuilder();
            while ((len = reader.read(buf)) != -1) {
                builder.append(new String(buf, 0, len));
            }
            log.info("builder : \r\n{}",builder.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
