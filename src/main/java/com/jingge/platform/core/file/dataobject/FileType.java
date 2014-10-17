/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.dataobject;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.realpaas.platform.framework.dataobject.AbstractPlatformType;

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
 * 	Aug 9, 2012	huangqr	Create the class
 * </dd>
 * 
 * </dl>
 * @author	huangqr Email/MSN: huangqingrong520@163.com
 */

@Entity
@Table( name="PLF_FILE_TYPE" )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class FileType extends AbstractPlatformType{

    private static final long serialVersionUID = 2204255644742734687L;
    
    public static final String ATTACHMENT_CODE = "ATTACHMENT"; 

    public static final String ATTACHMENT_NAME = "Attachment Files"; 

    public static final String IMAGE_CODE = "IMG";
    
    public static final String IMAGE_NAME = "Image Files";
    
    public static final String DOC_CODE = "DOC"; 
    
    public static final String DOC_NAME = "Office Documents"; 
    
}
