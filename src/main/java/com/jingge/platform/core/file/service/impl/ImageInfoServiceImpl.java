/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.file.service.impl;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.file.dao.ImageInfoDao;
import com.jingge.platform.core.file.dataobject.ImageInfo;
import com.jingge.platform.core.file.service.ImageInfoService;

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

@Component( "imageInfoService" )
public class ImageInfoServiceImpl extends AbstractFileInfoServiceImpl<ImageInfo, ImageInfoDao> implements ImageInfoService{

    @Override
    public ImageInfo instantiate() {
        return new ImageInfo();
    }

    @Override
    public ImageInfo instantiate(Map<String, Object> initParams) {
        return new ImageInfo();
    }

}
