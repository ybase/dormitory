<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<!--footer-->
<%-- <jsp:include page="footer_sec1.jsp" /> --%>

<!--footer menu-->
<section id="footer-menu">
 	<jsp:include page="footer_sec2.jsp" />
</section>


<script>
$(document).ready(function(){
	if("null"==="<%=session.getAttribute("flo")%>"){
		layer.msg('�˴β��԰汾�����˼ƻ����ӹ��ܡ��������!', 5, {
		       rate: 'top',
		       type: -1,
		       shade: false
		   });
	}
	
	if("1"==="<%=request.getAttribute("status")%>"){
		layer.alert("����������");
	}
});
</script>

<%session.setAttribute("flo", "true");%> 
<script>
    $(window).load(function() {
        $('.flexslider').flexslider({
            animation: "slide"
        });
    });
</script>
</body>
</html>