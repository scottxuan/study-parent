package scottxuan.io.reader;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Slf4j
public class BufferedReaderExample1 {
    public static void main(String[] args) {
        BufferedReader bufferedReader = null;
        FileReader reader = null;
        try {
            String path1 = FileReaderExample1.class.getResource("/test.txt").getPath();
            reader = new FileReader(path1);
            bufferedReader = new BufferedReader(reader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            log.info("builder : \r\n{}", builder.toString().trim());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader == null && reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
