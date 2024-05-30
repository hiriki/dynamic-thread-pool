package cn.ray.middleware.dynamic.thread.pool.sdk.domain;

import cn.ray.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author Ray
 * @date 2024/5/22 22:26
 * @description
 */
public interface IDynamicThreadPoolService {

    /**
     * 列出全部线程池配置
     * @return
     */
    List<ThreadPoolConfigEntity> listThreadPoolConfig();

    /**
     * 根据线程池名称查询其配置
     * @param threadPoolName
     * @return
     */
    ThreadPoolConfigEntity getThreadPoolConfigByName(String threadPoolName);

    /**
     * 修改指定线程池配置
     * @param threadPoolConfigEntity
     */
    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity);
}
