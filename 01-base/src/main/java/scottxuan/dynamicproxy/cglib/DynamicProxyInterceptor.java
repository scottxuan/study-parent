package scottxuan.dynamicproxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class DynamicProxyInterceptor implements MethodInterceptor {
    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        //用于创建无参的目标对象代理类
        //对于有参构造器则调用Enhancer.create(Class[] argumentTypes, Object[] arguments)，
        //第一个参数表示参数类型，第二个参数表示参数的值
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return methodProxy.invokeSuper(o, objects);
    }
}
