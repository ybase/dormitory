<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<nav class="container" id="menu">
    <ul>
        <li><a href="<%=basePath %>dormIndex" class="current" id="A">��ҳ</a></li>
        <li><a href="javascript:void(0)" id="B">Some things</a></li>
<%--         <li><a href="<%=basePath %>dormAbout" id="B">Some things</a></li> --%>
        <li><a href="<%=basePath %>dormPortfolio4?pageCurrent=1" id="C">����</a></li>
        <li><a href="<%=basePath %>dormBlog?pageCurrent=1" id="D">����</a></li>
        <li><a href="<%=basePath %>dormContact" id="E">����</a></li>
    </ul>
</nav>