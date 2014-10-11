/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.core.file.setup;

import org.springframework.stereotype.Component;

import com.realpaas.platform.core.file.dataobject.FileStorageStrategy;
import com.realpaas.platform.core.file.service.FileStorageStrategyService;
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
 *  Aug 8, 2012    henry leu   Create the class
 * </dd>
 * 
 * </dl>
 * @author  henry leu Email/MSN: hongli_leu@126.com
 */

@Component( "fileStorageStrategyDataManager" )
public class FileStorageStrategyDataManagerImpl extends AbstractGenericDataManagerImpl<FileStorageStrategyService> {

    @Override
    protected void doTearDown() {
        service.deleteAll();
    }

    @Override
    protected void doSetUp() {
        FileStorageStrategy strategy = null;

        /*
         * RDBMS Strategy
         */
        strategy = new FileStorageStrategy();
        strategy.setCode( FileStorageStrategy.RDB_CODE );
        strategy.setName( FileStorageStrategy.RDB_NAME );
        strategy.setDescription( FileStorageStrategy.RDB_NAME );
        strategy.setBeanName( "fileInRdbStrategy" );
        service.create( strategy );

        /*
         * File System Strategy
         */
        strategy = new FileStorageStrategy();
        strategy.setCode( FileStorageStrategy.FS_CODE );
        strategy.setName( FileStorageStrategy.FS_NAME );
        strategy.setDescription( FileStorageStrategy.FS_NAME );
        strategy.setBeanName( "fileInFileSystemStrategy" );
        service.create( strategy );

//        /*
//         * Baidu Cloud
//         */
//        strategy = new FileStorageStrategy();
//        strategy.setCode( FileStorageStrategy.BAIDU_CODE );
//        strategy.setName( FileStorageStrategy.BAIDU_NAME );
//        strategy.setDescription( FileStorageStrategy.BAIDU_NAME );
//        service.create( strategy );
//
//        /*
//         * Aliyun Cloud
//         */
//        strategy = new FileStorageStrategy();
//        strategy.setCode( FileStorageStrategy.ALI_CODE );
//        strategy.setName( FileStorageStrategy.ALI_NAME );
//        strategy.setDescription( FileStorageStrategy.ALI_NAME );
//        service.create( strategy );
//
//        /*
//         * Youpai Cloud
//         */
//        strategy = new FileStorageStrategy();
//        strategy.setCode( FileStorageStrategy.YOUPAI_CODE );
//        strategy.setName( FileStorageStrategy.YOUPAI_NAME );
//        strategy.setDescription( FileStorageStrategy.YOUPAI_NAME );
//        service.create( strategy );
    }

    @Override
    protected void doValidate() {
        FileStorageStrategy strategy = null;

        /*
         * RDBMS
         */
        strategy = service.loadByCode( FileStorageStrategy.RDB_CODE );
        checkExistence( strategy, FileStorageStrategy.RDB_NAME );
        
        /*
         * FS
         */
        strategy = service.loadByCode( FileStorageStrategy.FS_CODE );
        checkExistence( strategy, FileStorageStrategy.FS_NAME );
        
//        /*
//         * Baidu Cloud
//         */
//        strategy = service.loadByCode( FileStorageStrategy.BAIDU_CODE );
//        checkExistence( strategy, FileStorageStrategy.BAIDU_NAME );
//        
//        /*
//         * Aliyun Cloud
//         */
//        strategy = service.loadByCode( FileStorageStrategy.ALI_CODE );
//        checkExistence( strategy, FileStorageStrategy.ALI_NAME );
//        
//        /*
//         * Youpai Cloud
//         */
//        strategy = service.loadByCode( FileStorageStrategy.YOUPAI_CODE );
//        checkExistence( strategy, FileStorageStrategy.YOUPAI_NAME );
    }

    @Override
    protected void doUpdate() {
        FileStorageStrategy strategy = null;

        /*
         * RDBMS
         */
        strategy = service.loadByCode( FileStorageStrategy.RDB_CODE );
        if(strategy==null){
            strategy = new FileStorageStrategy();
            strategy.setCode( FileStorageStrategy.RDB_CODE );
            strategy.setName( FileStorageStrategy.RDB_NAME );
            strategy.setDescription( FileStorageStrategy.RDB_NAME );
            strategy.setBeanName( "fileInRdbStrategy" );
            service.create( strategy );
        }
        
        /*
         * FS
         */
        strategy = service.loadByCode( FileStorageStrategy.FS_CODE );
        if(strategy==null){
            strategy = new FileStorageStrategy();
            strategy.setCode( FileStorageStrategy.FS_CODE );
            strategy.setName( FileStorageStrategy.FS_NAME );
            strategy.setDescription( FileStorageStrategy.FS_NAME );
            strategy.setBeanName( "fileInFileSystemStrategy" );
            service.create( strategy );
        }
        
//        /*
//         * Baidu Cloud
//         */
//        strategy = service.loadByCode( FileStorageStrategy.BAIDU_CODE );
//        if(strategy==null){
//            strategy = new FileStorageStrategy();
//            strategy.setCode( FileStorageStrategy.BAIDU_CODE );
//            strategy.setName( FileStorageStrategy.BAIDU_NAME );
//            strategy.setDescription( FileStorageStrategy.BAIDU_NAME );
//            service.create( strategy );
//        }
//        
//        /*
//         * Aliyun Cloud
//         */
//        strategy = service.loadByCode( FileStorageStrategy.ALI_CODE );
//        if(strategy==null){
//            strategy = new FileStorageStrategy();
//            strategy.setCode( FileStorageStrategy.ALI_CODE );
//            strategy.setName( FileStorageStrategy.ALI_NAME );
//            strategy.setDescription( FileStorageStrategy.ALI_NAME );
//            service.create( strategy );
//        }
//        
//        /*
//         * Youpai Cloud
//         */
//        strategy = service.loadByCode( FileStorageStrategy.YOUPAI_CODE );
//        if(strategy==null){
//            strategy = new FileStorageStrategy();
//            strategy.setCode( FileStorageStrategy.YOUPAI_CODE );
//            strategy.setName( FileStorageStrategy.YOUPAI_NAME );
//            strategy.setDescription( FileStorageStrategy.YOUPAI_NAME );
//            service.create( strategy );
//        }
        
        doValidate();
    }
    
}
