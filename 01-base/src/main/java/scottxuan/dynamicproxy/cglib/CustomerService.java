package scottxuan.dynamicproxy.cglib;

import lombok.extern.slf4j.Slf4j;
import scottxuan.dynamicproxy.jdk.UserService;

@Slf4j
public class CustomerService {
    public void findByCode(String code) {
        log.info("name : "+ code + "-name");
    }
}
