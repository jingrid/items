/**
 * Copyright (c) 2012, RealPaaS Technologies, Ltd. All rights reserved.
 */
package com.realpaas.platform.common.http;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.realpaas.platform.core.identity.dataobject.User;

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
 * 	jan 9, 2012	henry leu	Create the class
 * </dd>
 * 
 * </dl>
 * @author	henry leu
 */
public class AppSessionAccessor extends AbstractSessionAccessor{
    public static final String SESSION_KEY_BASE = "com.realpaas.linker.app.session";
    
    public static final String SESSION_KEY_SYSTEM_SESSION_ID = SESSION_KEY_BASE + ".sessionId";
    
    public static final String SESSION_KEY_ACCOUNT = SESSION_KEY_BASE + ".User";
    
    public static final String SESSION_KEY_LOCALE = "session.locale";

    private HttpSession httpSession;
    
    private Map<String, Object> sessionMap;
    
    public AppSessionAccessor(HttpSession httpSession) {
        super();
        this.httpSession = httpSession;
    }

    public AppSessionAccessor(Map<String, Object> sessionMap) {
        super();
        this.sessionMap = sessionMap;
    }

    @Override
    public Object get(String name) {
        Object value = null;
        if( httpSession!=null ){
            value = httpSession.getAttribute( name );
        }
        else if ( sessionMap!=null ){
            value = sessionMap.get( name );
        }
        return value;
    }
    
    @Override
    public void remove(String name) {
        if( httpSession!=null ){
            httpSession.removeAttribute( name );
        }
        else if ( sessionMap!=null ){
            sessionMap.remove( name );
        }
    }
    
    @Override
    public void set(String name, Object value) {
        if( httpSession!=null ){
            httpSession.setAttribute( name, value );
        }
        else if ( sessionMap!=null ){
            sessionMap.put( name, value );
        }
    }
    
    /*
     * ================ System Session Id =================
     */
    public static String getSystemSessionId(Map<String, Object> sessionMap){
        String systemSessionId = (String)sessionMap.get( SESSION_KEY_SYSTEM_SESSION_ID );
        return systemSessionId;
    }
    
    public static String getSystemSessionId(HttpSession session){
        String systemSessionId = (String)session.getAttribute( SESSION_KEY_SYSTEM_SESSION_ID );
        return systemSessionId;
    }
    
    public String getSystemSessionId(){
        String systemSessionId = (String)get( SESSION_KEY_SYSTEM_SESSION_ID );
        return systemSessionId;
    }
    
    public void setSystemSessionId(String systemSessionId){
        set( SESSION_KEY_SYSTEM_SESSION_ID, systemSessionId );
    }

    public void removeSystemSessionId() {
        remove( SESSION_KEY_SYSTEM_SESSION_ID );
    }
    
    /*
     * ================ Current logged-in User =================
     */
    public User getUser(){
        User user = (User)get( SESSION_KEY_ACCOUNT );
        return user;
    }
    
    public void setUser(User user){
        set( SESSION_KEY_ACCOUNT, user );
    }

    public void removeUser() {
        remove( SESSION_KEY_ACCOUNT );
    }
    
    /*
     * ================ Current logged-in User's Locale =================
     */
    public Locale getLocale(){
        Locale locale = (Locale)get( SESSION_KEY_LOCALE );
        return locale;
    }
    
    public void setLocale(Locale locale){
        set( SESSION_KEY_LOCALE, locale );
    }

    public void removeLocale() {
        remove( SESSION_KEY_LOCALE );
    }
}
