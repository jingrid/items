<%@tag language="java" pageEncoding="UTF-8" %>
<%@tag import="com.realpaas.platform.core.i18n.dataobject.LocaleEntry"%>
<%@tag import="com.realpaas.platform.common.web.LocaleHelper"%>
<%@tag import="java.util.*"%>

<%@attribute name="id" required="false" %>
<%@attribute name="name" required="false" %>

<%
    id = id==null ? "locale" : id;
    name = name==null ? "locale" : name;
    
    LocaleEntry locale = null;
    List<LocaleEntry> localeList = new ArrayList<LocaleEntry>();
    locale = new LocaleEntry();
    locale.setCode( "zh_CN" );
    locale.setName( "简体中文" );
    localeList.add( locale );

    locale = new LocaleEntry();
    locale.setCode( "en" );
    locale.setName( "English" );
    localeList.add( locale );
    request.setAttribute( "localeList", localeList );
    String currentLocale = LocaleHelper.getLocaleCode( request );
%>

<select id="<%=id %>" name="<%=name %>" class="r-i18n pull-right">
<%
    Iterator iter = localeList.iterator();
    String selected = "";
    for( ; iter.hasNext(); ){
        LocaleEntry LocaleEntry = (LocaleEntry)iter.next();
        if( LocaleEntry.getCode().equals( currentLocale ) ){
            selected = "selected";
        }
        else{
            selected = "";
        }
%>
    <option value="<%=LocaleEntry.getCode() %>" <%=selected %> ><%=LocaleEntry.getName() %></option>
<%
    }
 %>
</select>