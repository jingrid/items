/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.dataobject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.realpaas.platform.ssos.core.dataobject.enumtype.GenericEnumType;

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
 * <p><dt><b>Immutability:</b></dt> 
 * <dd>
 * 	<b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
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
 * 	2013-5-9	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public class EnumRegistry {

    private static Map<Class<?>, Object> enumClassMap = new HashMap<Class<?>, Object>();
    
    public static <V, E extends GenericEnumType<V, E>> void registerEnumMap(Class<E> enumClass, Map<V, E> enumMap){
        enumClassMap.put( enumClass, Collections.unmodifiableMap( enumMap ) );
    }
    
    @SuppressWarnings("unchecked")
    public static <V, E extends GenericEnumType<V, E>> Map<V, E> getEnumMap(Class<E> enumClass){
        Object map = enumClassMap.get( enumClass );
        if( map == null){
            return null;
        }
        return (Map<V, E>)map;
    }
}
