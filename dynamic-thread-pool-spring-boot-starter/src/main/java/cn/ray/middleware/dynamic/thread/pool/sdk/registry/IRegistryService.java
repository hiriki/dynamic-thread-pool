package cn.ray.middleware.dynamic.thread.pool.sdk.registry;

import cn.ray.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author Ray
 * @date 2024/5/29 15:56
 * @description 注册中心接口
 */
public interface IRegistryService {

    void reportMultipleThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntities);

    void reportThreadPool(ThreadPoolConfigEntity threadPoolConfigEntity);
}
