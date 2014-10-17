<%@tag language="java" pageEncoding="UTF-8" %><%@tag import="com.jingge.platform.common.web.WebHelper"%><%@attribute name="fetch" required="false" %><%
    boolean force = fetch!=null ? Boolean.parseBoolean( fetch ) : false;
    String targetUrl = WebHelper.getEncodedTargetUrl( request );
    if(targetUrl==null){
        if( force ){
            targetUrl = WebHelper.encodeURL( WebHelper.getOriginalUrl( request ) );
        }
        else{
            targetUrl = "";
        }
    }
%><input type="hidden" name="_targetUrl" value="<%=targetUrl%>">