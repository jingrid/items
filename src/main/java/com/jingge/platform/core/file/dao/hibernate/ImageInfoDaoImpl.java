/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.jingge.platform.core.file.dao.ImageInfoDao;
import com.jingge.platform.core.file.dataobject.FileType;
import com.jingge.platform.core.file.dataobject.ImageInfo;

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

@Repository( "imagetInfoDao" )
public class ImageInfoDaoImpl extends AbstractFileInfoDaoImpl<ImageInfo> implements ImageInfoDao{

    @Override
    protected void initDao() throws Exception {
        super.initDao();
        super.setFileTypeCode( FileType.IMAGE_CODE );
    }
}
