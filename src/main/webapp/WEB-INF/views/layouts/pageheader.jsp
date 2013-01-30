<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/tlds/haksvn.tld" prefix="haksvn" %>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-cache" />
	<meta http-equiv="EXPIRES" content="-1">
	<meta http-equiv="Pragma" content="no-cache" />
	<title><tiles:getAsString name="title" /></title>
	
	<link rel="stylesheet" href="<c:url value="/css/style.css"/>" type="text/css"	media="screen" />
	<script src="<c:url value="/js/jquery.js"/>" type="text/javascript" charset="utf-8"></script>
	<script src="<c:url value="/js/global.js"/>" type="text/javascript" charset="utf-8"></script>
	<script src="<c:url value="/js/modal.js"/>" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$.ajaxSetup({ cache: false });
	</script>
</head>