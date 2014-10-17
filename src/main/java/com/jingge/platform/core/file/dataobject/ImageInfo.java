/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.dataobject;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
 * 	Feb 3, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@DiscriminatorValue( ImageInfo.OBJECT_TYPE_TREE_CODE )
public class ImageInfo extends FileInfo {

    private static final long serialVersionUID = -1246228960898859090L;

    public static final String OBJECT_TYPE_TREE_CODE = "IMAGE";
    
    public static final String OBJECT_TYPE_NAME = "Image";

    private short width;
    
    private short height;
    
    public ImageInfo() {
        super();
        width = 0;
        height = 0;
    }

    @Column(name = "WIDTH", insertable = true, updatable = true, nullable = true)
    public short getWidth() {
        return width;
    }

    public void setWidth(short width) {
        this.width = width;
    }

    @Column(name = "HEIGHT", insertable = true, updatable = true, nullable = true)
    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        this.height = height;
    }
}
