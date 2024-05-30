package cn.ray.middleware.dynamic.thread.pool.sdk.registry.redis;

import cn.ray.middleware.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.ray.middleware.dynamic.thread.pool.sdk.domain.model.vo.RegistryEnumVO;
import cn.ray.middleware.dynamic.thread.pool.sdk.registry.IRegistryService;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;

import java.time.Duration;
import java.util.List;

/**
 * @author Ray
 * @date 2024/5/29 15:57
 * @description
 */
public class RedisRegistryService implements IRegistryService {

    private final RedissonClient redissonClient;

    public RedisRegistryService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void reportMultipleThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntities) {
        RList<ThreadPoolConfigEntity> list = redissonClient.getList(RegistryEnumVO.THREAD_POOL_CONFIG_LIST_KEY.getKey());
//        for (ThreadPoolConfigEntity entity : threadPoolConfigEntities) {
//            if (!list.contains(entity)) {
//                list.add(entity);
//            }
//        }
        list.delete();
        list.addAll(threadPoolConfigEntities);
    }

    @Override
    public void reportThreadPool(ThreadPoolConfigEntity threadPoolConfigEntity) {
        String cacheKey = RegistryEnumVO.THREAD_POOL_CONFIG_KEY.getKey() + "_" + threadPoolConfigEntity.getApplicationName() + "_" + threadPoolConfigEntity.getThreadPoolName();
        RBucket<ThreadPoolConfigEntity> bucket = redissonClient.getBucket(cacheKey);
        bucket.set(threadPoolConfigEntity, Duration.ofDays(30));
    }
}
