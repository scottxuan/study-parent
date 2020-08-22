package scottxuan.dynamicproxy.cglib;

public class Test {
    public static void main(String[] args) {
        CustomerService customerService = (CustomerService) new DynamicProxyInterceptor().getProxy(CustomerService.class);
        customerService.findByCode("scottxuan");
    }
}
