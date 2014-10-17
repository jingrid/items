/**
 * Copyright (c) 2011, RealPaas Technologies Ltd. All rights reserved.
 */
package com.jingge.platform.common.dao.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * <p>
 *
 * <dl>
 * <dt><b>Examples:</b></dt>
 * <p>
 * <pre>
 *
 * </pre>
 *
 * <p><dt><b>Thread Safety:</b></dt>
 * <dd>
 * 	<b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 *
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 * 	<b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 *
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 * 	
 * </dd>
 *
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 * 	Date		Author		Action
 * </dd>
 * <dd>
 * 	May 22, 2013	henryleu	Create the class
 * </dd>
 *
 * </dl>
 * @author	henryleu
 * @see
 * @see
 */

public abstract class AbstractRedisDaoImpl<V> implements InitializingBean{
    
    protected static final char KEY_DELIMITER_CHAR = ':';
    
    protected RedisTemplate<String, V> redisTemplate;
    
    protected ValueOperations<String, V> valueOperations;
    
    protected ListOperations<String, V> listOperations;
    
    protected SetOperations<String, V> setOperations;
    
    protected ZSetOperations<String, V> zsetOperations;
    
    protected HashOperations<String, String, V> hashOperations;

    public void setRedisTemplate(RedisTemplate<String, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        valueOperations = redisTemplate.opsForValue();
        listOperations = redisTemplate.opsForList();
        setOperations = redisTemplate.opsForSet();
        zsetOperations = redisTemplate.opsForZSet();
        hashOperations = redisTemplate.opsForHash();
    }

}
