package scottxuan.reflection;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class Test {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        //访问public构造方法
        Constructor<Student> constructor = Student.class.getConstructor(String.class, int.class);
        Student student = constructor.newInstance("张三", 25);
        //访问private构造方法
        Constructor<Student> constructor1 = Student.class.getDeclaredConstructor();
        constructor1.setAccessible(true);
        Student student1 = constructor1.newInstance();
        Class studentClass = student.getClass();
        //获取public字段和值
        Field fieldName = studentClass.getField("name");
        Object name = fieldName.get(student);
        log.info("name={}",name);
        //获取private字段和值
        Field fieldAge = studentClass.getDeclaredField("age");
        fieldAge.setAccessible(true);
        Object age = fieldAge.get(student);
        log.info("age={}",age);
        //获取public方法
        Method showName = studentClass.getMethod("showName");
        showName.invoke(student);
        //获取private方法
        Method showAge = studentClass.getDeclaredMethod("showAge");
        showAge.setAccessible(true);
        showAge.invoke(student);

    }
}
