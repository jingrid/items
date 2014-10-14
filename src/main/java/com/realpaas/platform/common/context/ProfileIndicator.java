package com.realpaas.platform.common.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

public class ProfileIndicator implements InitializingBean {
    
    private String profile = "";

    private Log logger = LogFactory.getLog( getClass() );
    
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info( "Active profile is " + profile );
    }
    
}
