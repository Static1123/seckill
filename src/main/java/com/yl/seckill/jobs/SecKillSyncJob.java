package com.yl.seckill.jobs;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Administrator
 */
@Component
public class SecKillSyncJob implements SimpleJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecKillSyncJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        LOGGER.info("start job");
        System.out.println(new Date());

        LOGGER.info("end job");
    }
}