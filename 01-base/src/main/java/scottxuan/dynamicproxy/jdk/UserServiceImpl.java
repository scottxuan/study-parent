package scottxuan.dynamicproxy.jdk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public void findByCode(String code) {
        log.info("name : "+ code + "-name");
    }
}
