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
    <h1>����</h1>
    <div>�����﷢����������</div>
</div>

<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignrleft span9">
                <article>
                    <h3>��������</h3>
                    <p>
                    ����������������֮ǰ������Ҫ�˽ⷢ������ǰ���һЩ���̡��������������·���д��ص�������Ϣ����ʼһ���������⣻������
                    ��֮�����������������鿴���ղŷ�����������⣬ÿ�����������·����е��ޣ��ظ��������ԡ� ��������뷢��һ���µ�������
                    �⣬������ֱ������������ѡ����ϲ�����������⣬�鿴������������¶��򣬻��߶���������������ԡ�	��Ҳ�������������鿴��
                    ��ͼƬ��ѡ����ϲ�õ�����ͼƬ�����ͼƬ���ӽ�������������⡣ף��������죡
                    </p>
                </article>  

                <hr />
                
                <form name="contact" method="post" action="<%=basePath%>addBlog" class="af-form" id="af-form" enctype="multipart/form-data"/>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="name" id="name_label">����:</label>
                            <input type="text" name="name" id="name" size="30" value="" class="text-input input-xlarge" />
                            <label class="error" for="name" id="name_error">����.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="file" id="file_label">ͼƬ:</label>
                            <input type="file" name="file" id="file" size="30" value=""  />
                             <label class="error" for="file" id="file_error">ѡ��ͼƬ.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <label for="input-message" id="message_label">����:</label>
                            <textarea name="message" id="input-message" cols="30" class="text-input"></textarea>
                            <label class="error" for="input-message" id="message_error">����.</label>
                        </div>
                    </div>
                    <div class="af-outer af-required">
                        <div class="af-inner">
                            <input type="submit" name="submit" class="form-button btn" id="submit_btn" value="�ύ����" />
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
			String prevText = (String)request.getAttribute("prevText");
			if(prevText != null){
				%>
			 layer.alert("<%= prevText%>",9);
			<% }%>
		
	});
	$("#A").removeClass("current");
	$("#E").addClass("current");
</script>
}
<jsp:include page="include/footer.jsp" />