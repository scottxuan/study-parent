package scottxuan.dynamicproxy.jdk;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Slf4j
public class Test {
    public static void main(String[] args) {
        InvocationHandler handler = new UserInvocationHandler<>(new UserServiceImpl());
        UserService userService = (UserService)Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class<?>[]{UserService.class}, handler);
        userService.findByCode("code");
    }
}
