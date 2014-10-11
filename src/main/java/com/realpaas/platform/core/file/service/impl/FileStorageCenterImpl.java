/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.realpaas.platform.core.file.dataobject.FileStorage;
import com.realpaas.platform.core.file.dataobject.FileStorageStrategy;
import com.realpaas.platform.core.file.dataobject.FileStorageStrategyObject;
import com.realpaas.platform.core.file.service.AbstractInboundFile;
import com.realpaas.platform.core.file.service.FileStorageCenter;
import com.realpaas.platform.core.file.service.FileStorageService;
import com.realpaas.platform.core.file.service.FileStorageStrategyService;
import com.realpaas.platform.core.file.service.OutboundFile;
import com.realpaas.platform.key.Base62;
import com.realpaas.platform.ssos.core.dataobject.id.GenericIdentifierInstantiator;

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

@Component( "fileStorageCenter" )
public class FileStorageCenterImpl implements FileStorageCenter, ApplicationContextAware{

    private ApplicationContext ac;
    
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileStorageStrategyService fileStorageStrategyService;
    
    @Autowired
    protected GenericIdentifierInstantiator identifierInstantiator;

    @Override
    public FileStorage storeFile(AbstractInboundFile<?> inboundFile) {
        FileStorageStrategy strategy = getFileStorageStrategy( inboundFile.getStrategyCode() );
        FileStorageStrategyObject strategyObject = getFileStorageStrategyObject( strategy );
        return strategyObject.store( inboundFile );
    }

    @Override
    public OutboundFile getFile(String base62Code) {
        long idNo = Base62.decode( base62Code );
        FileStorage fileStorage = fileStorageService.loadById( identifierInstantiator.identifier( idNo ) );
        if( fileStorage==null ){
            return null;
        }
        FileStorageStrategy strategy = getFileStorageStrategy( fileStorage.getStrategyCode() );
        FileStorageStrategyObject strategyObject = getFileStorageStrategyObject( strategy );
        return strategyObject.get( fileStorage );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }
    
    private FileStorageStrategy getFileStorageStrategy(String strategyCode){
        FileStorageStrategy strategy = fileStorageStrategyService.loadByCode( strategyCode );
        if( strategy==null ){
            throw new IllegalStateException();//TODO:
        }
        return strategy;
    }
    
    private FileStorageStrategyObject getFileStorageStrategyObject(FileStorageStrategy strategy){
        FileStorageStrategyObject strategyObject = (FileStorageStrategyObject)ac.getBean( strategy.getBeanName() );
        if( strategyObject==null ){
            throw new IllegalStateException();//TODO:
        }
        return strategyObject;
    }

}
