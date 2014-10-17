/**
 * Copyright (c) 2014, JingGe Technologies, Ltd. All rights reserved.
 */
package com.jingge.items.base.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jingge.platform.common.web.WebHelper;

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
 * 	Jan 22, 2013	henryleu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henryleu Email/MSN: hongli_leu@126.com
 */

@RequestMapping( "/public" )
@Controller( "/public/portal" )
public class HomeController {

    private static final String REDIRECT_TO_MY = "redirect:/bbs/portal/main"; //TODO configure it stead of hardcoding it

    @RequestMapping( value="/portal/main" )
    public String main(){
        return "public/portal/main";
    }
    
    @RequestMapping(value = "/portal/home")
    public String homePage( 
            HttpServletRequest request, 
            HttpServletResponse response, 
            ModelMap modelMap,
            RedirectAttributes redirectAttributes) {

        if( WebHelper.hasSignedIn() ){
            if( WebHelper.hasTargetUrl( request ) ){
                String redirectUrl = WebHelper.getTargetUrl( request );
                return "redirect:" + redirectUrl;
            }
            else{
                return REDIRECT_TO_MY;
            }
        }
        else{
            WebHelper.setTargetUrl( request, "/public/portal/default" );
            return "redirect:/public/security/signin";
        }
    }
    
    @RequestMapping(value = "/portal/default")
    public String defaultPage(){
        return "public/portal/default";
    }
    
    @RequestMapping(value = "/portal/debug")
    public String debug(){
        return "common/jsp/debug";
    }
}
