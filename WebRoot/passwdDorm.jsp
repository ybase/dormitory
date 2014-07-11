<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@taglib uri="/dorm" prefix="dorm"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />
<!--breadcrumbs -->
<div class="container breadcrumbs">
    <h1>�����޸�</h1>
    <div>�������������޸��û�����</div>
</div>

<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignrleft span9">
                <article>
                    <h3>�޸���������</h3>
                    <p>
				                    �����޸�����֮ǰ����Ҫ����������룬�������˺ų����쳣����ϵϵͳ����ԱΪ��������⣡
                    </p>
                </article>  

                <hr />
                
                <form name="contact" method="post" action="<%=basePath%>updatePasswd" class="af-form" id="af-form" />
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="oldpass" id="oldpass_label">������:</label>
                            <input type="password" name="oldpass" id="oldpass" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="oldpass" id="oldpass_error">����.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="newpass" id="newpass_label">������:</label>
                            <input type="password" name="newpass" id="newpass" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="newpass" id="newpass_error">����.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="cfpass" id="cfpass_label">ȷ������:</label>
                            <input type="password" name="cfpass" id="cfpass" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="cfpass" id="cfpass_error_1">����.</label>
                            <label class="error" for="cfpass" id="cfpass_error_2">���벻һ��.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <input type="submit" name="submit" class="update-button btn" id="submit_btn" value="�ύ" />
                        </div>
                    </div>
                </form>
            </section>
            <!--sidebar-->
            <aside id="sidebar" class="alignright span3">
                <section>
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>��������</h4>             
                    </div>
                    <address>
                        <strong><c:out value="${top.name}"/></strong><br />
                      	<c:out value="${top.topDesc}"/><br />
                      </address>
                    <address>
                        <strong>����:<a href="javascript:topRequest('<c:out value="${top.id}" />','Y');" id="topY"><c:out value="${top.yesCount}" /></a>��</strong><br />
                    </address>
                    <address>
                        <strong>�²�:<a href="javascript:topRequest('<c:out value="${top.id}"/>','N');" id="topN"><c:out value="${top.noCount}" /></a>��</strong><br />
                    </address>
                </section>
                <!--twitter -->
                <section id="twitter-sidebar">
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>YBASE</h4>             
                    </div>
                    <div class="twitter"></div>
                </section>
            </aside>
        </div>
    </div>    
</section>
<script type="text/javascript">
	$(document).ready(function() {
		<%
			String prevText = (String)request.getAttribute("tip");
			String status = (String)request.getAttribute("status");
			if(prevText != null){
				%>
			 layer.alert("<%= prevText%>",9);
			<% }%>
			 <% if(status !=null && "0".equals(status.trim())){%>
			 	 <% session.invalidate(); %>
				 location.href="<%=basePath%>" + "dormIndex";
			 <%} %>
			
	});
	$("#A").addClass("current");
</script>
}
<jsp:include page="include/footer.jsp" />