package com.yl.seckill;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.yl.seckill.utils.SpringApplicationUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 */
@SpringBootApplication
@NacosPropertySource(dataId = "seckill-default", autoRefreshed = true)
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);

        System.out.println(SpringApplicationUtil.getContext().getBean(DruidDataSource.class));
    }
}