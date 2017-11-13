package org.ps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Core启动入口
 */
@SpringBootApplication
public class PsCoreApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(PsCoreApp.class,args);
    }
}
