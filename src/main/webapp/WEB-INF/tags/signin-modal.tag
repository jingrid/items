<%@tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="rui" tagdir="/WEB-INF/tags"%>

<div class="container-fluid"><div class="row-fluid">
<div class="modal hide fade offset2 span8 signin-modal" id="signinModel">
	<div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	    <h3><s:message code="app.signin" /></h3>
	</div>
    <div class="modal-body">
    
        <rui:signin-form/>
    
    </div>
    <div class="modal-footer">
        <a class="btn" data-dismiss="modal" aria-hidden="true"><s:message code="app.close" /></a>
    </div>
</div>
</div></div>