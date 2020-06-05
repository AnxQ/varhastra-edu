package top.varhastra.edu;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


@SpringBootApplication
@EnableJpaAuditing
public class EduApplication implements WebMvcConfigurer {

    @Value("${varhastra.img.path}")
    private String imgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:" + imgPath);
    }

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
