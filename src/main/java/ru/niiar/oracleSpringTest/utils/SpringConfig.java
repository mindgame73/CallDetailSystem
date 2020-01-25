package ru.niiar.oracleSpringTest.utils;

import jssc.SerialPort;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.MultipartConfigElement;

@Configuration
@PropertySource("classpath:com.properties")
public class SpringConfig {
    @Value("${serialPort.portName}")
    private String portName;

    /*@Bean
    public SerialPort serialPort(){
        return new SerialPort(portName);
    }*/

    @Bean
    public HSSFWorkbook workbook(){
        return new HSSFWorkbook();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        return factory.createMultipartConfig();
    }

}
