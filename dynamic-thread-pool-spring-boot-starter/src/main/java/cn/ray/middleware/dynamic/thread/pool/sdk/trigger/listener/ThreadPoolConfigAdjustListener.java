package cn.ray.middleware.dynamic.thread.pool.sdk.trigger.listener;

import cn.ray.middleware.dynamic.thread.pool.sdk.domain.IDynamicThreadPoolService;
import cn.ray.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.ray.middleware.dynamic.thread.pool.sdk.registry.IRegistryService;
import com.alibaba.fastjson.JSON;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Ray
 * @date 2024/5/30 20:41
 * @description
 */
public class ThreadPoolConfigAdjustListener implements MessageListener<ThreadPoolConfigEntity> {

    private Logger logger = LoggerFactory.getLogger(ThreadPoolConfigAdjustListener.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistryService registryService;

    public ThreadPoolConfigAdjustListener(IDynamicThreadPoolService dynamicThreadPoolService, IRegistryService registryService) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registryService = registryService;
    }

    @Override
    public void onMessage(CharSequence charSequence, ThreadPoolConfigEntity threadPoolConfigEntity) {
        logger.info("动态线程池，调整线程池配置。线程池名称:{} 核心线程数:{} 最大线程数:{}", threadPoolConfigEntity.getThreadPoolName(), threadPoolConfigEntity.getCorePoolSize(), threadPoolConfigEntity.getMaximumPoolSize());
        dynamicThreadPoolService.updateThreadPoolConfig(threadPoolConfigEntity);

        // 更新后上报最新数据
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.listThreadPoolConfig();
        registryService.reportMultipleThreadPool(threadPoolConfigEntities);

        ThreadPoolConfigEntity threadPoolConfigEntityCurrent = dynamicThreadPoolService.getThreadPoolConfigByName(threadPoolConfigEntity.getThreadPoolName());
        registryService.reportThreadPool(threadPoolConfigEntityCurrent);
        logger.info("动态线程池，上报线程池配置：{}", JSON.toJSONString(threadPoolConfigEntity));

    }
}
