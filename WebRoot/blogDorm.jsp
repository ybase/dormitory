<%@ page language="java" import="java.util.*,com.ybase.dorm.bas.*" pageEncoding="GB18030"%>
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
    <div>�����������Գ�������</div>
</div>
<!--container-->
<section id="container">
    <div class="container">
        <div class="row">
            <section id="page-sidebar" class="alignleft span9">
            	<c:choose>
            		<c:when test="${blog!=null}">
            			<article class="blog-post">
		                    <a href="#"><img src="<%=basePath%>images/upload/<c:out value="${blog.picPath}"/>" alt="photo" /></a>
		                    <h2 class="post-title"><a href="#"><c:out value="${blog.theme}" /></a></h2>
		                    <ul class="meta clearfix">
		                        <li><c:out value="${dorm:dtformat(blog.crDate,blog.crTime)}"/></li>
		                        <li><a href="#"><c:out value="${blog.usrName}" /></a>����</li>
		                        <li>������<a href="javascript:void(0)" id="blogYes"><c:out value="${blog.yesCount}" /></a>��</li>
		                    </ul>
		                    <p><c:out value="${blog.blogDesc}"></c:out></p>
		                    <a href="javascript:addYes('<c:out value="${blog.id}"/>');" class="read-more">��</a>
		                </article>
            		</c:when>
            		<c:otherwise>
            			<article class="blog-post">
		                    <a href="#"><img src="<%=basePath%>images/test9.jpg" alt="photo" /></a>
		                    <h2 class="post-title"><a href="#">���ϴ������⽫�ڴ˴���ʾ�����������⴦���ϴ�����!!!</a></h2>
		                    <ul class="meta clearfix">
		                        <li>0000��00��00��  00:00:00</li>
		                        <li><a href="#">�����������ơ�</a>����</li>
		                        <li>������<a href="javascript:void(0)" id="blogYes">N</a>��</li>
		                    </ul>
		                    <p><c:out value="${blog.blogDesc}"></c:out></p>
		                    <a href="javascript:void(0)" class="read-more">��</a>
		                </article>
            		</c:otherwise>
            	</c:choose>

				<c:if test="${talk!=null}">
				<c:forEach items="${talk}" var="row">
					<hr class="soft"/>
	                <article class="blog-post">
	                    <ul class="meta clearfix">
	                        <li><c:out value="${dorm:dformat(row.crDate)}" />&nbsp;&nbsp;<c:out value="${dorm:tformat(row.crTime)}" /></li>
	                        <li><a href="javascript:addYnTalk('<c:out value="${row.id}"/>','Y')">��</a><font id="topY<c:out value="${row.id}"/>"><c:out value="${row.yesCount}" /></font></li>
	                        <li><a href="javascript:addYnTalk('<c:out value="${row.id}"/>','N')">��</a><font id="topN<c:out value="${row.id}"/>"><c:out value="${row.noCount}" /></font></li>
	                    </ul>
	                    <p><c:out value="${row.talkDesc}"/></p>
	                </article>
				</c:forEach>
				</c:if>
                
                 <hr class="soft"/>
                 
                <article class="blog-post">
	                <div class="af-outer af-required">
	                        <div class="af-inner">
	                            <label for="input-message" id="message_label">��һ��:</label>
	                            <c:choose>
	                            	<c:when test="${blog!=null}">
	                            		<textarea name="message" id="input-msg" cols="30" class="text-input" blog="<c:out value="${blog.id}"/>" current="<c:out value="${page.current}"/>" first="<c:out value="${page.first}"/>" last="<c:out value="${page.last}"/>" ></textarea>
	                            	</c:when>
	                            	<c:otherwise>
	                            		<textarea name="message" id="input-msg" cols="30" class="text-input" blog="<c:out value="${blog.id}"/>" disabled="disabled">�˴���д�������������!!!</textarea>
	                            	</c:otherwise>
	                            </c:choose>
	                            <label class="error" for="input-message" id="message_error">����Ϊ��</label>
	                        <p>(CTRL + ENTER ��������)</p>
	                        </div>
	                </div>
  				</article>
  				
                <hr />

                <!--pagination-->
                <div class="pagination pagination-centered">
                	<c:set var="pageurl" value="/dormBlog" scope="request"/>
                    <jsp:include page="include/page.jsp" />
                </div>
            </section>
            <!--sidebar-->
            <aside id="sidebar" class="alignright span3">

                <section class="post-widget">
                    <div class="title-divider">
                        <div class="divider-arrow"></div>                               
                        <h4>��������</h4>             
                    </div>
                    <ul class="clearfix">
                    	<c:choose>
                    		<c:when test="${fiveMap!=null}">
                    			<c:forEach var="item" items="${fiveMap}">
	                    			<li>
	                    				<dorm:map items="${item}" key="theme" />
			                            <a href="#"><img src="<%=basePath%>images/upload/<c:out value="${dorm:covertAddStr(blog.picPath,'sma')}"/>" alt="photo" /></a>
			                            <a href="#"><c:out value="${dorm:contact(item['theme'],item['blogdesc'])}" escapeXml="false" />  </a>
			                            <p> ����    <dorm:map items="${item}" key="yescount" /> ��</p>
			                            <div class="clear"></div>
			                        </li>
		                    	</c:forEach>
                    		</c:when>
                    		<c:otherwise>
                    				<li>
			                            <a href="#"><img src="<%=basePath%>images/test11.jpg" alt="photo" /></a>
			                            <a href="#"> �˴���չʾ��������(���޴������)  </a>
			                            <p> ����    N ��</p>
			                            <div class="clear"></div>
			                        </li>
                    		</c:otherwise>
                    	</c:choose>
                    </ul>
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
<script>
	$("#A").removeClass("current");
	$("#D").addClass("current");
</script>
<jsp:include page="include/footer.jsp" />