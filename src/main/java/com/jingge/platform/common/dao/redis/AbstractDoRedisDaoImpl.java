/**
 * Copyright (c) 2011, RealPaas Technologies Ltd. All rights reserved.
 */
package com.jingge.platform.common.dao.redis;

import java.lang.reflect.ParameterizedType;

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

public abstract class AbstractDoRedisDaoImpl<V, Do extends DataObject> extends AbstractRedisDaoImpl<V>{
    
    protected String dataObjectName;
    
    protected Class<Do> dataObjectClass;

    protected abstract String getDoIdName();
    
    @SuppressWarnings("unchecked")
    protected AbstractDoRedisDaoImpl(){
        dataObjectClass = (Class<Do>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        dataObjectName = dataObjectClass.getName();
    }

    protected String getDoKey(Long dataObjectId){
        StringBuilder sbKey = new StringBuilder(30);
        sbKey.append( getDoIdName() )
            .append( KEY_DELIMITER_CHAR )
            .append( dataObjectId.toString() );
        return sbKey.toString();
    }
    
    protected String getDoPropKey(Long dataObjectId, String propName){
        StringBuilder sbKey = new StringBuilder(30);
        sbKey.append( getDoIdName() )
            .append( KEY_DELIMITER_CHAR )
            .append( dataObjectId.toString() )
            .append( KEY_DELIMITER_CHAR )
            .append( propName );
        return sbKey.toString();
    }

}
