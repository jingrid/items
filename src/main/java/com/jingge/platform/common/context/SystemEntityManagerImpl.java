/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.common.context;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.infras.service.SystemEntityService;
import com.realpaas.platform.framework.context.SystemEntity;
import com.realpaas.platform.framework.context.SystemEntityManager;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifier;
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
 * 	2012-8-5	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@Component( "systemEntityManager" )
public class SystemEntityManagerImpl implements SystemEntityManager {
    
    private SystemEntityService systemEntityService;
    
    public void setSystemEntityService(SystemEntityService systemEntityService) {
        this.systemEntityService = systemEntityService;
    }

    @Override
    public SystemEntity load(String code) {
        com.jingge.platform.core.infras.dataobject.SystemEntity entry =  systemEntityService.loadByCode( code );
        if( entry==null ){
            return null;
        }
        else{
            SystemEntity obj = new SystemEntity();
            obj.setCode( code );
            obj.setName( entry.getName() );
            obj.setDescription( entry.getDescription() );
            obj.setSerialNo( entry.getSerialNo() );
            obj.setVersion( entry.getLicenseVersion() );
            obj.setId( new GenericIdentifier( entry.getSerialNo() ) );
            return obj;
        }
    }

    @Override
    public void create(SystemEntity systemEntity) {
        com.jingge.platform.core.infras.dataobject.SystemEntity dataobject = new com.jingge.platform.core.infras.dataobject.SystemEntity();
        dataobject.setId( new GenericIdentifier( systemEntity.getSerialNo() ) );
        dataobject.setLifeFlag( LifeFlag.Active );
        dataobject.setCode( systemEntity.getCode() );
        dataobject.setName( systemEntity.getName() );
        dataobject.setDescription( systemEntity.getDescription() );
        dataobject.setSerialNo( systemEntity.getSerialNo() );
        dataobject.setLicenseVersion( systemEntity.getVersion() );
        systemEntityService.create( dataobject );
    }

    @Override
    public void mutate(SystemEntity systemEntity) {
        com.jingge.platform.core.infras.dataobject.SystemEntity entry = systemEntityService.loadByCode( systemEntity.getCode() );
        entry.setName( systemEntity.getName() );
        entry.setDescription( systemEntity.getDescription() );
        entry.setLicenseVersion( systemEntity.getVersion() );
        systemEntityService.update( entry );
    }

}
