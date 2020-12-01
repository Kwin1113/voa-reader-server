package xyz.kwin.voa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Kwin
 * @since 2020/11/28 下午4:33
 */
@SpringBootApplication
@MapperScan(basePackages = {"xyz.kwin.voa.mapper"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
