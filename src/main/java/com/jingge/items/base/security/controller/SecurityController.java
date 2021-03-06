/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.items.base.security.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.ConnectionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jingge.platform.common.security.AuthenticationAdapter;
import com.jingge.platform.common.web.WebHelper;
import com.jingge.platform.core.identity.dataobject.DefaultUserAccess;
import com.jingge.platform.core.identity.dataobject.User;
import com.jingge.platform.core.identity.service.UserService.ChangePasswordInfo;
import com.jingge.platform.core.identity.service.UserService.ChangePasswordResult;
import com.jingge.platform.core.identity.service.UserService.ChangePasswordResultType;
import com.jingge.platform.core.identity.service.UserService.ResetPasswordInfo;
import com.jingge.platform.core.identity.service.UserService.ResetPasswordResult;
import com.jingge.platform.core.identity.service.UserService.ResetPasswordResultType;
import com.jingge.platform.core.identity.service.UserService.SigninResult;
import com.jingge.platform.core.identity.service.UserService.SigninResultType;
import com.jingge.platform.core.identity.service.UserService.SignupInfo;
import com.jingge.platform.core.identity.service.UserService.SignupResult;
import com.jingge.platform.core.identity.service.UserService.SignupResultType;
import com.jingge.platform.core.identity.service.UserService.SignwithInfo;
import com.jingge.platform.core.security.controller.ChangePasswordInfoValidator;
import com.jingge.platform.core.security.controller.GetbackPasswordInfo;
import com.jingge.platform.core.security.controller.GetbackPasswordInfoValidator;
import com.jingge.platform.core.security.controller.ResetPasswordInfoValidator;
import com.jingge.platform.core.security.controller.SigninInfoValidator;
import com.jingge.platform.core.security.controller.SignupInfoValidator;
import com.jingge.platform.core.security.controller.SocialProviderSignInAttempt;
import com.jingge.platform.core.security.controller.SocialSignInController;
import com.jingge.platform.core.security.service.SecurityService;

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
 * 	2012-8-10	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@RequestMapping(value = "/public")
@Controller( "public/security" )
public class SecurityController {
    
    private static final String REDIRECT_TO_HOME = "redirect:/pp/default"; //TODO configure it stead of hardcoding it

    private static final String REDIRECT_TO_MY = "redirect:/portal/main"; //TODO configure it stead of hardcoding it
    
    @Value("#{sysProps['system.domain']}")
    private String domain;
    
    @Autowired
    private SecurityService userService;

    @Autowired
    private AuthenticationAdapter authenticationAdapter;

    @ModelAttribute
    public void appAttribute(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession( false );
        if( session==null ){
            model.addAttribute( "sessionStatus", false );
            model.addAttribute( "signinStatus", false );
        }
        else{
            model.addAttribute( "sessionStatus", true );
            if( session.getAttribute( "username" )==null ){
                model.addAttribute( "signinStatus", false );
            }
            else{
                model.addAttribute( "signinStatus", true );
            }
        }
    }
    
    @RequestMapping(value = "/security/check-username")
    @ResponseBody
    public Map<String, Object> checkUsername(@RequestParam( "username" ) String username ) {
        boolean existed = true;
        Map<String, Object> response = new HashMap<String, Object>();
        try{
            existed = userService.checkUsernameExistence( username );
            response.put( "success", true ); //TODO: generate response hashmap
        }
        catch(Throwable e){
            response.put( "success", false );
            response.put( "message", e.getMessage() );
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put( "available", !existed );
        response.put( "result", result );
        return response;
    }

    @RequestMapping(value = "/security/signup", method=RequestMethod.GET)
    public String signupView() {
        return "public/security/signup";
    }
    
    @RequestMapping(value = "/security/signup", method=RequestMethod.POST)
    public String signup(
            HttpServletRequest request, 
            HttpServletResponse response,
            @ModelAttribute("signupInfo") SignupInfo signupInfo,
            BindingResult bindingResult,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes
            ) {
        SignupResult result = null;
        
        /*
         * Validate input parameters
         */
        new SignupInfoValidator().validate( signupInfo, bindingResult );
        if( bindingResult.hasErrors() ){
            modelMap.putAll( bindingResult.getModel() );
            modelMap.addAttribute( "username", signupInfo.getUsername() );
            modelMap.addAttribute( "email", signupInfo.getEmail() );
            return "security/signup";
        }
        
        /*
         * Sign up
         */
        result = userService.signup( signupInfo );
        if( SignupResultType.Successful.equals( result.type ) ){
            doSignout( request );
            doSignin( result.currentUserAccess );
            rewriteSession( request, response );
            
            if( WebHelper.hasTargetUrl( request ) ){
                String redirectUrl = WebHelper.getTargetUrl( request );
                return "redirect:" + redirectUrl;
            }
            else{
                return REDIRECT_TO_HOME;
            }
        }
        else{
            if( SignupResultType.ExistedUsername.equals( result.type ) ){
                bindingResult.rejectValue( "username", "app.username.existent" );
            }
            else {
                bindingResult.reject( "app.systemerror" );
            }
            
            modelMap.putAll( bindingResult.getModel() );
            modelMap.addAttribute( "username", signupInfo.getUsername() );
            modelMap.addAttribute( "email", signupInfo.getEmail() );
            return "public/security/signup";
        }
    }

    @RequestMapping(value = "/security/signin")
    public String signinView(@RequestParam( required=false ) String username, Model model ){
        if(username!=null){
            model.addAttribute( "username", username );
        }
        return "public/security/signin";
    }
    
    private void transferSocialInfo(NativeWebRequest request, ModelMap modelMap, String action){
        modelMap.addAttribute( "action", action );

        SocialProviderSignInAttempt signInAttempt = (SocialProviderSignInAttempt)request.getAttribute( SocialSignInController.SESSION_ATTRIBUTE, RequestAttributes.SCOPE_SESSION );
        if( signInAttempt!=null ){
            modelMap.addAttribute( "socialImageUrl", signInAttempt.getConnection().getImageUrl() );
            modelMap.addAttribute( "socialDisplayName", signInAttempt.getConnection().getDisplayName() );
            ConnectionData createData = signInAttempt.getConnection().createData();
            modelMap.addAttribute( "providerId", createData.getProviderId() );
            modelMap.addAttribute( "providerUserId", createData.getProviderUserId() );
            
            if( modelMap.get( "displayName" )==null ){
                modelMap.addAttribute( "displayName", signInAttempt.getConnection().getDisplayName() );
            }
            modelMap.addAttribute( "hasSocialInfo", Boolean.TRUE );
        }
        else{
            modelMap.addAttribute( "hasSocialInfo", Boolean.FALSE );
        }
        
        
        if( SocialSignInController.ACTION_SIGNUP_AND_BIND.equals( action ) || SocialSignInController.ACTION_SIGNIN_AND_BIND.equals( action )){
            
        }
        else if( SocialSignInController.ACTION_WARN_AND_SIGNIN.equals( action ) ){
            final String USERNAME_LIST = "usernameList"; 
            List<String> usernameList = null;
            @SuppressWarnings("unchecked")
            List<String> userIds = (List<String>)request.getAttribute( SocialSignInController.USER_IDS, RequestAttributes.SCOPE_SESSION );
            if( userIds==null || userIds.size()==0 ){
                usernameList = new ArrayList<String>();
            }
            else{
                usernameList = userService.listUsernames( userIds );
            }
            modelMap.addAttribute( USERNAME_LIST, usernameList );
        }
        else{
            throw new IllegalArgumentException( "Query parameter \"action\"=" + action + " is not supported" );
        }        
    }
    
    @RequestMapping(value = "/security/signwith", method=RequestMethod.GET, params="action")
    public String signwithView(
              NativeWebRequest request,
              ModelMap modelMap,
              @RequestParam String action){
        transferSocialInfo( request, modelMap, action );
        
        return "security/signwith";
    }
    
    @RequestMapping(value = "/security/signwith", method=RequestMethod.POST, params="action")
    public String signwith(
            NativeWebRequest nativeWebRequest, 
            @ModelAttribute("signwithInfo") SignwithInfo signwithInfo,
            BindingResult bindingResult,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes) {
        String action = signwithInfo.getAction();
        
        if( SocialSignInController.ACTION_SIGNUP_AND_BIND.equals( action ) ){
            /*
             * Validate input parameters
             */
            new SignupInfoValidator().validate( signwithInfo, bindingResult );
            if( bindingResult.hasErrors() ){
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", signwithInfo.getUsername() );
                modelMap.addAttribute( "email", signwithInfo.getEmail() );
                modelMap.addAttribute( "displayName", signwithInfo.getDisplayName() );
                transferSocialInfo( nativeWebRequest, modelMap, action );
                return "security/signwith";
            }
            
            /*
             * Sign up and bind
             */
            SignupResult result = userService.signup( signwithInfo );
            if( SignupResultType.Successful.equals( result.type ) ){
                HttpServletRequest request = nativeWebRequest.getNativeRequest( HttpServletRequest.class );
                HttpServletResponse response = nativeWebRequest.getNativeResponse( HttpServletResponse.class );
                SocialProviderSignInAttempt signInAttempt = (SocialProviderSignInAttempt)nativeWebRequest.getAttribute( SocialSignInController.SESSION_ATTRIBUTE, RequestAttributes.SCOPE_SESSION );
                signInAttempt.addConnection( result.user.getId().toString() );
                
                doSignout( request );
                doSignin( request.getSession( true ), result.user, result.currentUserAccess );
                rewriteSession( request, response );

                if( WebHelper.hasTargetUrl( request ) ){
                    String redirectUrl = WebHelper.getTargetUrl( request );
                    return "redirect:" + redirectUrl;
                }
                else{
                    return REDIRECT_TO_HOME;
                }
            }
            else{
                if( SignupResultType.ExistedUsername.equals( result.type ) ){
                    bindingResult.rejectValue( "username", "app.username.existent" );
                }
                else {
                    bindingResult.reject( "app.systemerror" );
                }
                
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", signwithInfo.getUsername() );
                modelMap.addAttribute( "email", signwithInfo.getEmail() );
                modelMap.addAttribute( "displayName", signwithInfo.getDisplayName() );
                transferSocialInfo( nativeWebRequest, modelMap, action );
                return "security/signwith";
            }            
            
        }
        else if( SocialSignInController.ACTION_SIGNIN_AND_BIND.equals( action ) || SocialSignInController.ACTION_WARN_AND_SIGNIN.equals( action )){
            /*
             * Validate input parameters
             */
            new SigninInfoValidator().validate( signwithInfo, bindingResult );
            if( bindingResult.hasErrors() ){
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", signwithInfo.getUsername() );
                transferSocialInfo( nativeWebRequest, modelMap, action );
                return "security/signwith";
            }

            /*
             * Sign in and bind
             */
            SigninResult result = userService.signin( signwithInfo );
            if( SigninResultType.Successful.equals( result.type ) ){
                if( SocialSignInController.ACTION_SIGNIN_AND_BIND.equals( action ) ){
                    SocialProviderSignInAttempt signInAttempt = (SocialProviderSignInAttempt)nativeWebRequest.getAttribute( SocialSignInController.SESSION_ATTRIBUTE, RequestAttributes.SCOPE_SESSION );
                    signInAttempt.addConnection( result.user.getId().toString() );
                }
                HttpServletRequest request = nativeWebRequest.getNativeRequest( HttpServletRequest.class );
                HttpServletResponse response = nativeWebRequest.getNativeResponse( HttpServletResponse.class );
                
                doSignout( request );
                doSignin( request.getSession( true ), result.user, result.currentUserAccess );
                rewriteSession( request, response );

                /*
                 * Set autoSignToken cookie
                 */
//                CookieManager cm = CookieHelper.getCookieManager( request, response, domain );
//                updateAutoSigninCookie( cm, signwithInfo.getUsername(), signwithInfo.getPassword() );
                
                if( WebHelper.hasTargetUrl( request ) ){
                    String redirectUrl = WebHelper.getTargetUrl( request );
                    return "redirect:" + redirectUrl;
                }
                else{
                    return REDIRECT_TO_MY;
                }
            }
            else{
                if( SigninResultType.WrongUsername.equals( result.type ) ){
                    bindingResult.reject( "app.signin.error" );
                }
                else if( SigninResultType.WrongPassword.equals( result.type ) ){
                    bindingResult.reject( "app.signin.error" );
                }
                else{
                    bindingResult.reject( "app.systemerror" );
                }
                
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", signwithInfo.getUsername() );            
                transferSocialInfo( nativeWebRequest, modelMap, action );
                return "security/signwith";
            }
        }
        else{
            throw new IllegalArgumentException(); //TODO
        }
    }
    
//    @RequestMapping(value = "/security/signwith", method=RequestMethod.POST, params={"action"})
//    public String signinAndBind(
//            NativeWebRequest nativeWebRequest, 
//            @ModelAttribute("signwithInfo") SignwithInfo signwithInfo,
//            BindingResult bindingResult,
//            @RequestParam String action,
//            ModelMap modelMap,
//            RedirectAttributes redirectAttributes
//            ) {
//        /*
//         * Validate input parameters
//         */
//        new SigninInfoValidator().validate( signwithInfo, bindingResult );
//        if( bindingResult.hasErrors() ){
//            modelMap.putAll( bindingResult.getModel() );
//            modelMap.addAttribute( "username", signwithInfo.getUsername() );
//            transferSocialInfo( nativeWebRequest, modelMap, action );
//            return "security/signwith";
//        }
//        
//        /*
//         * Sign in and bind
//         */
//        SigninResult result = userService.signin( signwithInfo );
//        if( SigninResultType.Successful.equals( result.type ) ){
//            HttpServletRequest request = nativeWebRequest.getNativeRequest( HttpServletRequest.class );
//            HttpServletResponse response = nativeWebRequest.getNativeResponse( HttpServletResponse.class );
//            
//            doSignout( request );
//            doSignin( request.getSession( true ), result.user, result.currentUserAccess );
//            
//            /*
//             * Set autoSignToken cookie
//             */
//            CookieManager cm = CookieHelper.getCookieManager( request, response, domain );
//            updateAutoSigninCookie( cm, signwithInfo.getUsername(), signwithInfo.getPassword() );
//            
//            if( WebHelper.hasTargetUrl( request ) ){
//                String redirectUrl = WebHelper.getTargetUrl( request );
//                return "redirect:" + redirectUrl;
//            }
//            else{
//                return REDIRECT_TO_MY;
//            }
//        }
//        else{
//            if( SigninResultType.WrongUsername.equals( result.type ) ){
//                bindingResult.reject( "app.signin.error" );
//            }
//            else if( SigninResultType.WrongPassword.equals( result.type ) ){
//                bindingResult.reject( "app.signin.error" );
//            }
//            else{
//                bindingResult.reject( "app.systemerror" );
//            }
//            
//            modelMap.putAll( bindingResult.getModel() );
//            modelMap.addAttribute( "username", signwithInfo.getUsername() );            
//            transferSocialInfo( nativeWebRequest, modelMap, action );
//            return "security/signwith";
//        }
//    }
    
//    @RequestMapping(value = "/security/signwith", method=RequestMethod.POST, params="action=signinAndBind")
//    public String warnAndSignin(
//            NativeWebRequest nativeWebRequest, 
//            @ModelAttribute("signinInfo") SigninInfo signinInfo,
//            BindingResult bindingResult,
//            @RequestParam String action,
//            ModelMap modelMap,
//            RedirectAttributes redirectAttributes
//            ) {
//        /*
//         * Validate input parameters
//         */
//        new SigninInfoValidator().validate( signinInfo, bindingResult );
//        if( bindingResult.hasErrors() ){
//            modelMap.putAll( bindingResult.getModel() );
////                modelMap.addAttribute( "username", signwithInfo.getUsername() );
////                modelMap.addAttribute( "email", signwithInfo.getEmail() );
////                modelMap.addAttribute( "displayName", signwithInfo.getDisplayName() );
//            transferSocialInfo( nativeWebRequest, modelMap, action );
//            return "security/signwith";
//        }
//        
//        /*
//         * Sign in and bind
//         */
//        SigninResult result = userService.signin( signinInfo );
//        if( SigninResultType.Successful.equals( result.type ) ){
//            HttpServletRequest request = nativeWebRequest.getNativeRequest( HttpServletRequest.class );
//            HttpServletResponse response = nativeWebRequest.getNativeResponse( HttpServletResponse.class );
//            
//            doSignout( request );
//            doSignin( request.getSession( true ), result.user, result.currentUserAccess );
//            
//            /*
//             * Set autoSignToken cookie
//             */
//            CookieManager cm = CookieHelper.getCookieManager( request, response, domain );
//            updateAutoSigninCookie( cm, signinInfo.getUsername(), signinInfo.getPassword() );
//            
//            if( WebHelper.hasTargetUrl( request ) ){
//                String redirectUrl = WebHelper.getTargetUrl( request );
//                return "redirect:" + redirectUrl;
//            }
//            else{
//                return REDIRECT_TO_MY;
//            }
//        }
//        else{
//            if( SigninResultType.WrongUsername.equals( result.type ) ){
//                bindingResult.reject( "app.signin.error" );
//            }
//            else if( SigninResultType.WrongPassword.equals( result.type ) ){
//                bindingResult.reject( "app.signin.error" );
//            }
//            else{
//                bindingResult.reject( "app.systemerror" );
//            }
//            
//            modelMap.putAll( bindingResult.getModel() );
//            transferSocialInfo( nativeWebRequest, modelMap, action );
//            return "security/signwith";
//        }
//    }
    
    /**
     * Process the authentication callback when neither the oauth_token or code parameter is given, likely indicating that the user denied authorization with the provider.
     * Redirects to application's sign in URL, as set in the signWithUrl property.
     */
    @RequestMapping(value="/signwith", method=RequestMethod.GET, params={"error", "!action"})
    public String canceledAuthorizationCallback(
            NativeWebRequest request,
            ModelMap modelMap,
            @RequestParam String error) {
        modelMap.addAttribute( "hasSocialInfo", Boolean.FALSE );
        modelMap.addAttribute( "error", error );
        return "security/signwith";
    }

    @RequestMapping(value = "/security/getback-pwd", method=RequestMethod.GET)
    public String getbackPasswordView(){
        return "security/getback-pwd";
    }

    @RequestMapping(value = "/security/getback-pwd", method=RequestMethod.POST)
    public String getbackPassword(
            HttpServletRequest request,
            @ModelAttribute( "getbackPasswordInfo" ) GetbackPasswordInfo getbackPasswordInfo,
            BindingResult bindingResult,
            ModelMap modelMap){
        boolean matched = false;
        
        /*
         * Validate input parameters
         */
        new GetbackPasswordInfoValidator().validate( getbackPasswordInfo, bindingResult );
        if( bindingResult.hasErrors() ){
            modelMap.putAll( bindingResult.getModel() );
            modelMap.addAttribute( "username", getbackPasswordInfo.getUsername() );
            return "security/getback-pwd";
        }
        
        matched = userService.checkResetPassword( getbackPasswordInfo.getUsername() );
        if( matched ){
            return "forward:/security/reset-pwd-notification";
        }
        else{
            bindingResult.reject( "app.getbackpwd.error" );
            modelMap.putAll( bindingResult.getModel() );
            modelMap.addAttribute( "username", getbackPasswordInfo.getUsername() );
            return "security/getback-pwd";
        }
    }

    @RequestMapping(value = "/security/reset-pwd-notification")
    public String generateResetPasswordNotification(String username, ModelMap model){
        model.addAttribute( "username", username );
        return "security/reset-pwd-notification";
    }

    @RequestMapping(value = "/security/reset-pwd", method=RequestMethod.GET)
    public String resetPasswordView(String username, ModelMap modelMap){
        modelMap.addAttribute( "username", username );
        return "security/reset-pwd";
    }

    @RequestMapping(value = "/security/reset-pwd", method=RequestMethod.POST)
    public String resetPassword(
            HttpServletRequest request,
            @ModelAttribute( "resetPasswordInfo" ) ResetPasswordInfo resetPasswordInfo,
            BindingResult bindingResult,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes) {
        ResetPasswordResult result = null;
        
        /*
         * Validate input parameters
         */
        new ResetPasswordInfoValidator().validate( resetPasswordInfo, bindingResult );
        if( bindingResult.hasErrors() ){
            modelMap.putAll( bindingResult.getModel() );
            modelMap.addAttribute( "username", resetPasswordInfo.getUsername() );
            return "security/reset-pwd";
        }
        
        try{
            result = userService.resetPassword( resetPasswordInfo );
            if( ResetPasswordResultType.Successful.equals( result.type ) ){
                return REDIRECT_TO_MY;
            }
            else if( ResetPasswordResultType.TheSamePassword.equals( result.type ) ){
                bindingResult.rejectValue( "password", "app.resetpassword.thesame" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", resetPasswordInfo.getUsername() );
                return "security/reset-pwd";
            }
            else if( ResetPasswordResultType.NoUsername.equals( result.type ) ){
                bindingResult.rejectValue( "username", "app.resetpassword.nousername" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", resetPasswordInfo.getUsername() );
                return "security/reset-pwd";
            }
            else if( ResetPasswordResultType.SystemError.equals( result.type ) ){
                bindingResult.reject( "app.systemerror" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", resetPasswordInfo.getUsername() );
                return "security/error";
            }
            else{
                bindingResult.reject( "app.systemerror" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", resetPasswordInfo.getUsername() );
                return "security/error";
            }
        }
        catch(Exception e){
            //TODO: handle exception
            throw new RuntimeException( e );
        }
    }

    @RequestMapping(value = "/security/change-pwd", method=RequestMethod.GET)
    public String changePasswordView(){
        return "security/change-pwd";
    }

    @RequestMapping(value = "/security/change-pwd", method=RequestMethod.POST)
    public String changePassword(
            HttpServletRequest request,
            @ModelAttribute( "changePasswordInfo" ) ChangePasswordInfo changePasswordInfo,
            BindingResult bindingResult,
            ModelMap modelMap,
            RedirectAttributes redirectAttributes) {
        ChangePasswordResult result = null;
        
        /*
         * Validate input parameters
         */
        new ChangePasswordInfoValidator().validate( changePasswordInfo, bindingResult );
        if( bindingResult.hasErrors() ){
            modelMap.putAll( bindingResult.getModel() );
            modelMap.addAttribute( "username", changePasswordInfo.getUsername() );
            return "security/change-pwd";
        }

        try{
            result = userService.changePassword( changePasswordInfo );
            if( ChangePasswordResultType.Successful.equals( result.type ) ){
                return REDIRECT_TO_MY;
            }
            else if( ChangePasswordResultType.TheSamePassword.equals( result.type ) ){
                bindingResult.rejectValue( "newPassword", "app.changepassword.thesame" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", changePasswordInfo.getUsername() );
                return "security/change-pwd";
            }
            else if( ChangePasswordResultType.WrongOldPassword.equals( result.type ) ){
                bindingResult.rejectValue( "oldPassword", "app.changepassword.wrongoldpassword" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", changePasswordInfo.getUsername() );
                return "security/change-pwd";
            }
            else if( ChangePasswordResultType.NoUsername.equals( result.type ) ){
                bindingResult.rejectValue( "username", "app.changepassword.nousername" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", changePasswordInfo.getUsername() );
                return "security/change-pwd";
            }
            else if( ChangePasswordResultType.SystemError.equals( result.type ) ){
                bindingResult.reject( "app.systemerror" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", changePasswordInfo.getUsername() );
                return "security/change-pwd";
            }
            else{
                bindingResult.reject( "app.systemerror" );
                modelMap.putAll( bindingResult.getModel() );
                modelMap.addAttribute( "username", changePasswordInfo.getUsername() );
                return "security/change-pwd";
            }
        }
        catch(Exception e){
            //TODO: handle exception
            throw new RuntimeException( e );
        }
    }
    
    private void doSignin(HttpSession session, User user, DefaultUserAccess userAccess){
        Authentication token = authenticationAdapter.createAuthentication( userAccess.getUserId().toString() );
        SecurityContextHolder.getContext().setAuthentication( token );
    }

    private void doSignin(DefaultUserAccess userAccess){
        Authentication token = authenticationAdapter.createAuthentication( userAccess.getUserId().toString() );
        SecurityContextHolder.getContext().setAuthentication( token );
    }

    private void rewriteSession(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
        cookie.setSecure(false);
        cookie.setPath( request.getContextPath() );
        response.addCookie(cookie);
    }

    private void doSignout(HttpServletRequest request){
        SecurityContextHolder.getContext().setAuthentication( null );
        HttpSession session = request.getSession( false );
        if( session!=null ){
            session.invalidate();
        }
    }
    
}
