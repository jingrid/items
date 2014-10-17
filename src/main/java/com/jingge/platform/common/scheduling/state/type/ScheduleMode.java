/**
 * Copyright (c) 2011, Lenovo Group, Ltd. All rights reserved.
 */
package com.jingge.platform.common.scheduling.state.type;

import java.util.HashMap;
import java.util.Map;

import com.realpaas.platform.ssos.core.dataobject.enumtype.StringEnumType;

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
 * 	Jun 22, 2011	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu
 * @see			
 * @see			
 */
public enum ScheduleMode implements StringEnumType<ScheduleMode>{
    
    CronExpression("CE", "scheduling.cronexpression", "CronExpression"), 
    RepeatInterval("RI", "scheduling.repeatinterval", "RepeatInterval");
    
    private String value;
    
    private String key;
    
    private String displayName;
    
    private ScheduleMode(String value, String key, String displayName) {
        this.value = value;
        this.key = key;
        this.displayName = displayName;
    }
    
    @Override
    public String getValue() {
        return value;
    }
    
    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * cache all the constant instance for the enum type.
     */
    private static final Map<String, ScheduleMode> valueToEnum = new HashMap<String, ScheduleMode>();
    
    /**
     * Initialize map from constant name to enum constant
     */
    static {
        for(ScheduleMode enumInstance : values()) {
            valueToEnum.put(enumInstance.getValue(), enumInstance);
        }
    }
    
    /**
     * Returns an instance by value in existed enum list, or null if string is
     * invalid It is very fast and fully optimized.
     * 
     * @param name a name string as a key to find an instance.
     * @return
     */
    public static ScheduleMode fromValue(int value) {
        return valueToEnum.get(value);
    }
}
