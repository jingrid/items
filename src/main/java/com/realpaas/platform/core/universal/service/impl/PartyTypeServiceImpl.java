/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.universal.service.impl;

import org.springframework.stereotype.Component;

import com.realpaas.platform.core.universal.dao.PartyTypeDao;
import com.realpaas.platform.core.universal.dataobject.PartyType;
import com.realpaas.platform.core.universal.service.PartyTypeService;

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
 * 	Jul 22, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Component( "partyTypeService" )
public class PartyTypeServiceImpl
    extends AbstractObjectTypeServiceImpl<PartyType, PartyTypeDao>
    implements PartyTypeService{

//    @Autowired
//    @Qualifier( "partyTypeDao" )
//    @Override
//    public void setDao(PartyTypeDao dao) {
//        super.setDao( dao );
//    }
    
}
