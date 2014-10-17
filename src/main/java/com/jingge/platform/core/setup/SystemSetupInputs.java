/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.setup;


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
 * 	Oct 2, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
abstract class SystemSetupInputs {
    
    protected String systemDbUrl;
    
    protected String systemDbUser;
    
    protected String systemDbPassword;
    
    protected String dbCatalog;
    
    protected String dbUser;
    
    protected String dbPassword;
    
    protected String dbHost;
    
    protected String dbCharset;
    
    protected String dbCollation;

    public void setSystemDbUrl(String systemDbUrl) {
        this.systemDbUrl = systemDbUrl;
    }

    public void setSystemDbUser(String systemDbUser) {
        this.systemDbUser = systemDbUser;
    }

    public void setSystemDbPassword(String systemDbPassword) {
        this.systemDbPassword = systemDbPassword;
    }

    public void setDbCatalog(String dbCatalog) {
        this.dbCatalog = dbCatalog;
    }
    
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }
    
    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public void setDbCharset(String dbCharset) {
        this.dbCharset = dbCharset;
    }

    public void setDbCollation(String dbCollation) {
        this.dbCollation = dbCollation;
    }
}
