<%@tag language="java" pageEncoding="UTF-8" %><%@attribute name="type" required="true" %><%@attribute name="value" required="true" %><%
    
String name = value;
if( "lifeFlag".equals( type ) ){
    if("0".equals( value )){
        name = "Active";
    }
    else if("1".equals( value )){
        name = "Inactive";
    }
    else if("2".equals( value )){
        name = "Deleted";
    }
}
%><%=name%>