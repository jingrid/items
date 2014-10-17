/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.dao.hibernate;

import org.springframework.beans.factory.annotation.Autowired;

import com.jingge.platform.core.file.dao.AbstractFileInfoDao;
import com.jingge.platform.core.file.dao.FileTypeDao;
import com.jingge.platform.core.file.dataobject.FileInfo;
import com.jingge.platform.core.file.dataobject.FileType;
import com.realpaas.platform.framework.dao.hibernate.PlatformDaoTemplate;
import com.realpaas.platform.ssos.core.dataobject.id.Identifier;
import com.realpaas.platform.ssos.ext.dao.hibernate.DaoLifecyclingDelegate;
import com.realpaas.platform.ssos.ext.dataobject.enumtype.LifeFlag;

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
public abstract class AbstractFileInfoDaoImpl<Do extends FileInfo> extends PlatformDaoTemplate<Do> implements AbstractFileInfoDao<Do>{

    private DaoLifecyclingDelegate<Do> lifecyclingDelegate;
    
    @Autowired
    private FileTypeDao fileTypeDao;

    private String fileTypeCode;
    
    @Override
    protected void initDao() throws Exception {
        super.initDao();
        lifecyclingDelegate = new DaoLifecyclingDelegate<Do>( delegate );
    }
    
    @Override
    public Do create(Do dataObject) {
        FileType fileType = fileTypeDao.loadByCode( fileTypeCode );
        if( fileType==null ){
            throw new IllegalStateException();
        }
        dataObject.setFileTypeId( fileType.getId() );
        return super.create( dataObject );
    }

    @Override
    public void updateLifeFlag(Identifier<?> id, LifeFlag lifeFlag) {
        lifecyclingDelegate.updateLifeFlag( id, lifeFlag );
    }

    @Override
    public void markActive(Identifier<?> id) {
        lifecyclingDelegate.markActive( id );
    }

    @Override
    public void markInactive(Identifier<?> id) {
        lifecyclingDelegate.markInactive( id );
    }

    @Override
    public void markDeleted(Identifier<?> id) {
        lifecyclingDelegate.markDeleted( id );
    }

    public void setFileTypeCode(String fileTypeCode) {
        this.fileTypeCode = fileTypeCode;
    }
    
}
