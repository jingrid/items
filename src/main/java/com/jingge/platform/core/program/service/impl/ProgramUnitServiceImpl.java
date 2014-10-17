/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.program.service.impl;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.program.dao.ProgramUnitDao;
import com.jingge.platform.core.program.dataobject.ProgramUnit;
import com.jingge.platform.core.program.service.ProgramUnitService;
import com.realpaas.platform.framework.service.impl.PlatformServiceTemplate;

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
 * 	Apr 3, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Component( "programUnitService" )
public class ProgramUnitServiceImpl<Do extends ProgramUnit, Dao extends ProgramUnitDao<Do>>
    extends PlatformServiceTemplate<Do, Dao>
    implements ProgramUnitService<Do>{

    @Override
    public Do loadByCode(String code) {
        return getDao().loadByCode( code );
    }

    @Override
    public int deleteAll() {
        return getDao().deleteAll();
    }

}
