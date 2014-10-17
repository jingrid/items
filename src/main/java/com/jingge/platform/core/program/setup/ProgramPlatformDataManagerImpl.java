/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.program.setup;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.program.dataobject.ProgramPlatform;
import com.jingge.platform.core.program.service.ProgramPlatformService;
import com.realpaas.platform.framework.setup.impl.AbstractGenericDataManagerImpl;

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
 *  <b>IMMUTABLE</b> and <b>MUTABLE</b>
 * </dd>
 * 
 * <p><dt><b>Thread Safety:</b></dt> 
 * <dd>
 *  <b>NOT-THREAD-SAFE</b> and <b>NOT-APPLICABLE</b> (for it will never be used on multi-thread occasion.)
 * </dd>
 * 
 * <p><dt><b>Serialization:</b></dt>
 * <dd>
 *  <b>NOT-SERIALIIZABLE</b> and <b>NOT-APPLICABLE</b> (for it have no need to be serializable.)
 * </dd>
 * 
 * <p><dt><b>Design Patterns:</b></dt>
 * <dd>
 *  
 * </dd>
 * 
 * <p><dt><b>Change History:</b></dt>
 * <dd>
 *  Date        Author      Action
 * </dd>
 * <dd>
 *  Apr 2, 2012    henry leu   Create the class
 * </dd>
 * 
 * </dl>
 * @author  henry leu Email/MSN: hongli_leu@126.com
 */

@Component( "programPlatformDataManager" )
public class ProgramPlatformDataManagerImpl extends AbstractGenericDataManagerImpl<ProgramPlatformService> {

    @Override
    protected void doTearDown() {
        service.deleteAll();
    }

    @Override
    protected void doSetUp() {
        ProgramPlatform programPlatform = null;

        /*
         * realpaas
         */
        programPlatform = new ProgramPlatform();
        programPlatform.setCode( ProgramPlatform.DEFAULT_CODE );
        programPlatform.setName( ProgramPlatform.DEFAULT_NAME );
        service.create( programPlatform );

    }

    @Override
    protected void doValidate() {
        ProgramPlatform programPlatform = null;

        /*
         * realpaas
         */
        programPlatform = service.loadByCode( ProgramPlatform.DEFAULT_CODE );
        checkExistence( programPlatform, ProgramPlatform.DEFAULT_CODE );
    }

    @Override
    protected void doUpdate() {
        ProgramPlatform programPlatform = null;

        /*
         * realpaas
         */
        programPlatform = service.loadByCode( ProgramPlatform.DEFAULT_CODE );
        if(programPlatform==null){
            doSetUp();
        }
        doValidate();
    }
    
}
