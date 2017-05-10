<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="error/bootstrap.css" />
<style type="text/css">
	div{padding-left: 10px;}
	h4,li{color:red}
	li{padding-left:2em}
	span{color:#0000AD}
</style>
<%	Exception ex = (Exception)request.getAttribute("exception_"); %>
</head>
<body>

		<div>
			<h4><span><%=ex.getClass()+":"%></span>&nbsp;&nbsp;<%=ex.getMessage() %></h4>
			<ul class="list-unstyled">
			<%
				for(StackTraceElement s :ex.getStackTrace()){
					out.print("<li> at "+s.getClassName()+"( <span>"+s.getFileName()+":"+s.getLineNumber()+"</span> )"+"</li>");
				}
			%>
			</ul>
		</div>

</body>
</html>