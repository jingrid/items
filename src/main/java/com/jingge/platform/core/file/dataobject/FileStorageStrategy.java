/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.dataobject;

import javax.persistence.Column;
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
 * 	2012-8-3	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Entity
@Table( name="PLF_FILE_STORAGE_STRATEGY" )
@Cache( usage=CacheConcurrencyStrategy.READ_WRITE )
public class FileStorageStrategy 
    extends AbstractPlatformType{

    private static final long serialVersionUID = -240784823125407858L;

    public static final String RDB_CODE = "RDB";
    
    public static final String RDB_NAME = "Relational Database";
    
    public static final String FS_CODE = "FS";
    
    public static final String FS_NAME = "File System";
    
    public static final String BAIDU_CODE = "BAIDU";
    
    public static final String BAIDU_NAME = "Baidu Cloud";
    
    public static final String ALI_CODE = "ALI";
    
    public static final String ALI_NAME = "Aliyun";
    
    public static final String YOUPAI_CODE = "YOUPAI";
    
    public static final String YOUPAI_NAME = "Youpai Cloud";
    
    private String beanName;

    @Column(name = "BEAN_NAME", insertable = true, updatable = false, nullable = false, length = 100)
    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
