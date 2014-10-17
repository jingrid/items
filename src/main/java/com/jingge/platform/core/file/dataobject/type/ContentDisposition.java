/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.dataobject.type;

import java.util.HashMap;
import java.util.Map;

import com.realpaas.platform.ssos.core.dataobject.enumtype.ByteEnumType;

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
 * 	Feb 4, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
public enum ContentDisposition implements ByteEnumType<ContentDisposition>{
    Attachment((byte)10, "ext.contentdisposition.attachment", "Attachment"),
    Inline((byte)20, "ext.contentdisposition.inline", "Inline");
    
    private Byte value;
    
    private String key;
    
    private String displayName;
    
    private ContentDisposition(byte value, String key, String displayName) {
        this.value = value;
        this.key = key;
        this.displayName = displayName;
    }
    
    public Byte getValue() {
        return value;
    }
    
    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    /**
     * cache all the constant instance for the enum type.
     */
    private static final Map<Byte, ContentDisposition> valueToEnum = new HashMap<Byte, ContentDisposition>();
    
    /**
     * Initialize map from constant name to enum constant
     */
    static {
        for(ContentDisposition enumInstance : values()) {
            valueToEnum.put(enumInstance.getValue(), enumInstance);
        }
    }
    
    /**
     * Returns an instance by value in existed enum list, or null if value is
     * invalid. It is very fast and fully optimized.
     * 
     * @param displayName a name string as a key to find an instance.
     * @return
     */
    public static ContentDisposition valueOf(int value) {
        return valueToEnum.get(value);
    }
    
}
