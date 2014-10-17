/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.security.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.social.connect.web.ConnectSupport;
import org.springframework.web.servlet.view.RedirectView;

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
 * 	Oct 28, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
abstract class AbstractSocialSignInController {
    
    public static final String CONTROLLER_MODULE_PATH = "/security";
    
    protected final Log logger = LogFactory.getLog( this.getClass() );
    
    protected final ConnectSupport webSupport = new ConnectSupport();

    protected String signInUrl = CONTROLLER_MODULE_PATH + "/signin";
    
    protected String signUpUrl = CONTROLLER_MODULE_PATH + "/signup";

    protected String signWithUrl = CONTROLLER_MODULE_PATH + "/signwith";

    protected String postSignInUrl = "/portal/main";

    /**
     * Sets the URL of the application's sign in page.
     * Defaults to "/security/signin".
     * @param signInUrl the signIn URL
     */
    //@Value( "#{webProps['application.signInUrl']}" )
    public void setSignInUrl(String signInUrl) {
        this.signInUrl = signInUrl;
    }
    
    /**
     * Sets the URL to redirect the user to if no local user account can be mapped when signing in using a provider.
     * Defaults to "/security/signup". 
     * @param signUpUrl the signUp URL
     */
    //@Value( "#{webProps['application.signUpUrl']}" )
    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl; 
    }

    /**
     * Sets the URL to redirect the user to if no local user or multiple account(s) can be mapped when signing in using a provider.
     * Defaults to "/security/signwith". 
     * @param signWithUrl the signUp or signIn URL
     */
    //@Value( "#{webProps['application.signWithUrl']}" )
    public void setSignWithUrl(String signWithUrl) {
        this.signWithUrl = signWithUrl; 
    }

    /**
     * Sets the default URL to redirect the user to after signing in using a provider.
     * Defaults to "/".
     * @param postSignInUrl the postSignIn URL
     */
    //@Value( "#{webProps['application.postSignInUrl']}" )
    public void setPostSignInUrl(String postSignInUrl) {
        this.postSignInUrl = postSignInUrl;
    }

    /**
     * Configures the base secure URL for the application this controller is being used in e.g. <code>https://myapp.com</code>. Defaults to null.
     * If specified, will be used to generate OAuth callback URLs.
     * If not specified, OAuth callback URLs are generated from web request info.
     * You may wish to set this property if requests into your application flow through a proxy to your application server.
     * In this case, the request URI may contain a scheme, host, and/or port value that points to an internal server not appropriate for an external callback URL.
     * If you have this problem, you can set this property to the base external URL for your application and it will be used to construct the callback URL instead.
     * @param applicationUrl the application URL value
     */
    //@Value( "#{webProps['application.url']}" )
    public void setApplicationUrl(String applicationUrl) {
        webSupport.setApplicationUrl(applicationUrl);
    }

    protected RedirectView redirect(String url) {
        return new RedirectView(url, true);
    }
}
