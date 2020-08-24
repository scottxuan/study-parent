package scottxuan.io;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @author scottxuan
 */
@Slf4j
public class FileExample1 {
    public static void main(String[] args) throws IOException {
        File file = new File("111.png");
        String path1 = file.getPath();
        log.info("path1: "+ path1);
        String path2 = file.getAbsolutePath();
        log.info("path2: "+ path2);
        String path3 = file.getCanonicalPath();
        log.info("path3: "+ path3);
        String path4 = FileExample1.class.getResource("").getPath();
        log.info("path4: "+ path4);
        String path5 = FileExample1.class.getResource("/").getPath();
        log.info("path5: "+ path5);
    }
}
