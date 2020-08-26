package scottxuan.io.file;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * @author scottxuan
 */
@Slf4j
public class FileExample1 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        //resources下路径开始有'/'
        String path2 = FileExample1.class.getResource("/111.png").getPath();
        File file2 = new File(path2);
        log.info("path2 : {}",path2);
        log.info("file2 : {}",file2.exists());

        //resources下路径开始没有'/'
        String path3 = Objects.requireNonNull(FileExample1.class.getClassLoader().getResource("111.png")).getPath();
        File file3 = new File(path3);
        log.info("path3 : {}",path3);
        log.info("file3 : {}",file3.exists());
    }
}
