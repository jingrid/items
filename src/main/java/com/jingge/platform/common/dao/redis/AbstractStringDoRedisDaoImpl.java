/**
 * Copyright (c) 2011, RealPaas Technologies Ltd. All rights reserved.
 */
package com.jingge.platform.common.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.realpaas.platform.ssos.core.dataobject.DataObject;

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
 * 	Jul 22, 2012	henryleu	Create the class
 * </dd>
 *
 * </dl>
 * @author	henryleu
 * @see
 * @see
 */

public abstract class AbstractStringDoRedisDaoImpl<Do extends DataObject> 
    extends AbstractDoRedisDaoImpl<String, Do>{

    @Qualifier( "stringRedisTemplate" )
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        super.setRedisTemplate( redisTemplate );
    }
}
