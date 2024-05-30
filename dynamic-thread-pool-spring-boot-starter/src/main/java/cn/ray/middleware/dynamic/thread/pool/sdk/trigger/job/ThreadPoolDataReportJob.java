package cn.ray.middleware.dynamic.thread.pool.sdk.trigger.job;

import cn.ray.middleware.dynamic.thread.pool.sdk.domain.IDynamicThreadPoolService;
import cn.ray.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.ray.middleware.dynamic.thread.pool.sdk.registry.IRegistryService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author Ray
 * @date 2024/5/29 16:32
 * @description 线程池数据上报任务
 */
public class ThreadPoolDataReportJob {

    private Logger logger = LoggerFactory.getLogger(ThreadPoolDataReportJob.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistryService registryService;

    public ThreadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRegistryService registryService) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registryService = registryService;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void execReportThreadPool() {
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.listThreadPoolConfig();
        registryService.reportMultipleThreadPool(threadPoolConfigEntities);
        logger.info("动态线程池，上报所有线程池信息：{}", JSON.toJSONString(threadPoolConfigEntities));

        for (ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigEntities) {
            registryService.reportThreadPool(threadPoolConfigEntity);
            logger.info("动态线程池，上报单个线程池配置：{}", JSON.toJSONString(threadPoolConfigEntity));
        }
    }
}
