package scottxuan.reflection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Student {
    public String name;
    private int age;

    private Student() {
        log.info("private Constructor finished");
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
        log.info("public Constructor finished");
    }

    public void showName() {
        log.info("我叫{}", this.name);
    }

    private void showAge() {
        log.info("我今年{}岁", this.age);
    }
}
