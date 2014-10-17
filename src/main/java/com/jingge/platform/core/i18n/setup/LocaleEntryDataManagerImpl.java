/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.i18n.setup;

import org.springframework.stereotype.Component;

import com.jingge.platform.core.i18n.dataobject.LocaleEntry;
import com.jingge.platform.core.i18n.service.LocaleEntryService;
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

@Component( "localeEntryDataManager" )
public class LocaleEntryDataManagerImpl extends AbstractGenericDataManagerImpl<LocaleEntryService> {

    @Override
    protected void doTearDown() {
        service.deleteAll();
    }

    @Override
    protected void doSetUp() {
        LocaleEntry localeEntry = null;

        /*
         * zh_CN
         */
        localeEntry = new LocaleEntry();
        localeEntry.setCode( LocaleEntry.CODE_ZH_CN );
        localeEntry.setName( LocaleEntry.NAME_ZH_CN );
        localeEntry.setDescription( LocaleEntry.NAME_ZH_CN );
        service.create( localeEntry );

        /*
         * en
         */
        localeEntry = new LocaleEntry();
        localeEntry.setCode( LocaleEntry.CODE_EN );
        localeEntry.setName( LocaleEntry.NAME_EN );
        localeEntry.setDescription( LocaleEntry.NAME_EN );
        service.create( localeEntry );

    }

    @Override
    protected void doValidate() {
        LocaleEntry localeEntry = null;

        /*
         * zh_CN
         */
        localeEntry = service.loadByCode( LocaleEntry.CODE_ZH_CN );
        checkExistence( localeEntry, LocaleEntry.CODE_ZH_CN );

        /*
         * en
         */
        localeEntry = service.loadByCode( LocaleEntry.CODE_EN );
        checkExistence( localeEntry, LocaleEntry.CODE_EN );
    }

    @Override
    protected void doUpdate() {
        LocaleEntry localeEntry = null;

        /*
         * zh_CN
         */
        localeEntry = service.loadByCode( LocaleEntry.CODE_ZH_CN );
        if( localeEntry==null ){
            localeEntry = new LocaleEntry();
            localeEntry.setCode( LocaleEntry.CODE_ZH_CN );
            localeEntry.setName( LocaleEntry.NAME_ZH_CN );
            localeEntry.setDescription( LocaleEntry.NAME_ZH_CN );
            service.create( localeEntry );
        }

        /*
         * en
         */
        localeEntry = service.loadByCode( LocaleEntry.CODE_EN );
        if( localeEntry==null ){
            localeEntry = new LocaleEntry();
            localeEntry.setCode( LocaleEntry.CODE_EN );
            localeEntry.setName( LocaleEntry.NAME_EN );
            localeEntry.setDescription( LocaleEntry.NAME_EN );
            service.create( localeEntry );
        }
    }
    
}
