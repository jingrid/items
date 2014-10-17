/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.platform.core.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.support.URIBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
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
 * 	Oct 26, 2012	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */
@Controller
@RequestMapping( SocialSignInController.CONTROLLER_MODULE_PATH )
public class SocialSignInController extends AbstractSocialSignInController{
    
    public static final String SESSION_ATTRIBUTE = ProviderSignInAttempt.class.getName();

    public static final String USER_IDS = "userIds";

    public static final String KEY_ACTION = "action";

    public static final String ACTION_BIND = "bind";

    public static final String ACTION_SIGNUP_AND_BIND = "signupAndBind";

    public static final String ACTION_SIGNIN_AND_BIND = "signinAndBind";

    public static final String ACTION_WARN_AND_SIGNIN = "warnAndSignin";

    @Autowired
    private ConnectionFactoryLocator connectionFactoryLocator;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    
    @Autowired
    private SignInAdapter signInAdapter;
    
    /**
     * Process a sign-in form submission by commencing the process of establishing a connection to the provider on behalf of the user.
     * For OAuth1, fetches a new request token from the provider, temporarily stores it in the session, then redirects the user to the provider's site for authentication authorization.
     * For OAuth2, redirects the user to the provider's site for authentication authorization.
     */
    @RequestMapping(value="/signwith-{providerId}", method=RequestMethod.POST)
    public RedirectView signIn(@PathVariable String providerId, NativeWebRequest request) {
        ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(providerId);
        try {
            return new RedirectView(webSupport.buildOAuthUrl(connectionFactory, request));
        } catch (Exception e) {
            logger.error("Exception while redirecting authorizatin (" + e.getMessage() + "). Redirecting to " + signWithUrl);            
            return redirect(URIBuilder.fromUri(signWithUrl + "-" + providerId).queryParam("error", e.getMessage()).build().toString());
        }
    }

    /**
     * Process the authentication callback from an OAuth 1 service provider.
     * Called after the member authorizes the authentication, generally done once by having he or she click "Allow" in their web browser at the provider's site.
     * Handles the provider sign-in callback by first determining if a local user account is associated with the connected provider account.
     * If so, signs the local user in by delegating to {@link SignInAdapter#signIn(String, Connection, NativeWebRequest)}
     * If not, redirects the user to a signup page to create a new account with {@link SocialProviderSignInAttempt} context exposed in the HttpSession.
     * @see SocialProviderSignInAttempt
     */
    @RequestMapping(value="/signwith-{providerId}", method=RequestMethod.GET, params="oauth_token")
    public RedirectView oauth1Callback(@PathVariable String providerId, NativeWebRequest request) {
        try {
            OAuth1ConnectionFactory<?> connectionFactory = (OAuth1ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = webSupport.completeConnection(connectionFactory, request);
            return handleSignIn(connection, request);
        } catch (Exception e) {
            logger.error("Exception while handling OAuth1 callback (" + e.getMessage() + "). Redirecting to " + signWithUrl);
            return redirect(URIBuilder.fromUri(signWithUrl + "-" + providerId).queryParam("error", providerId).build().toString());
        }
    }

    /**
     * Process the authentication callback from an OAuth 2 service provider.
     * Called after the user authorizes the authentication, generally done once by having he or she click "Allow" in their web browser at the provider's site.
     * Handles the provider sign-in callback by first determining if a local user account is associated with the connected provider account.
     * If so, signs the local user in by delegating to {@link SignInAdapter#signIn(String, Connection, NativeWebRequest)}.
     * If not, redirects the user to a signup page to create a new account with {@link SocialProviderSignInAttempt} context exposed in the HttpSession.
     * @see SocialProviderSignInAttempt
     */
    @RequestMapping(value="/signwith-{providerId}", method=RequestMethod.GET, params="code")
    public RedirectView oauth2Callback(@PathVariable String providerId, @RequestParam("code") String code, NativeWebRequest request) {
        try {
            OAuth2ConnectionFactory<?> connectionFactory = (OAuth2ConnectionFactory<?>) connectionFactoryLocator.getConnectionFactory(providerId);
            Connection<?> connection = webSupport.completeConnection(connectionFactory, request);
            return handleSignIn(connection, request);
        } catch (Exception e) {
            logger.error("Exception while handling OAuth2 callback (" + e.getMessage() + "). Redirecting to " + signWithUrl);
            return redirect(URIBuilder.fromUri(signWithUrl + "-" + providerId).queryParam("error", providerId).build().toString());
        }
    }
    
    /**
     * Process the authentication callback when neither the oauth_token or code parameter is given, likely indicating that the user denied authorization with the provider.
     * Redirects to application's sign in URL, as set in the signWithUrl property.
     */
    @RequestMapping(value="/signwith-{providerId}", method=RequestMethod.GET, params="error")
    public String canceledAuthorizationCallback(
            NativeWebRequest request,
            ModelMap modelMap,
            @RequestParam String error) {
        modelMap.addAttribute( "hasSocialInfo", Boolean.FALSE );
        modelMap.addAttribute( "error", error );
        return "security/signwith";
    }

    // internal helpers
    private RedirectView handleSignIn(Connection<?> connection, NativeWebRequest request) {
        List<String> userIds = usersConnectionRepository.findUserIdsWithConnection( connection );
        
        if( userIds.size() == 0 ) {
            SocialProviderSignInAttempt signInAttempt = new SocialProviderSignInAttempt( connection, connectionFactoryLocator, usersConnectionRepository );
            request.setAttribute( SESSION_ATTRIBUTE, signInAttempt, RequestAttributes.SCOPE_SESSION );
            return redirect( URIBuilder.fromUri( signWithUrl ).queryParam( KEY_ACTION, ACTION_SIGNUP_AND_BIND ).build().toString() );
        }
        else if( userIds.size() == 1 ) {
            usersConnectionRepository.createConnectionRepository( userIds.get( 0 ) ).updateConnection( connection );
            String originalUrl = signInAdapter.signIn( userIds.get( 0 ), connection, request );
            return originalUrl != null ? redirect( originalUrl ) : redirect( postSignInUrl );
        }
        else {
            SocialProviderSignInAttempt signInAttempt = new SocialProviderSignInAttempt( connection, connectionFactoryLocator,
                    usersConnectionRepository );
            request.setAttribute( SESSION_ATTRIBUTE, signInAttempt, RequestAttributes.SCOPE_SESSION );
            request.setAttribute( USER_IDS, userIds, RequestAttributes.SCOPE_SESSION );
            return redirect( URIBuilder.fromUri( signWithUrl ).queryParam( KEY_ACTION, ACTION_WARN_AND_SIGNIN ).build().toString() );
        }
    }

}
