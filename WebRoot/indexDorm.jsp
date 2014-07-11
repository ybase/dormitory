<%@ page language="java" import="java.util.*,com.ybase.dorm.bas.*" pageEncoding="GB18030"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@taglib uri="/dorm" prefix="dorm"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%> 
<jsp:include page="include/common.jsp" />
<section id="slider">
    <div class="flexslider">
        <ul class="slides">
        	<c:choose>
        		<c:when test="${tops!=null}">
       				<c:forEach var="it" items="${tops}">
			        	<li>
			                <img src="<%=basePath%>images/upload/<c:out value="${dorm:covertAddStr(it.picPath,'idx')}"/>" />
			            </li>
		        	</c:forEach>
        		</c:when>
        		<c:otherwise>
        			<li>
		                <img src="<%=basePath%>images/error.jpg" />
		            </li>
        		</c:otherwise>
        	</c:choose>
        </ul>
    </div>
</section>
<section id="container">
    <div class="container">
        <!--Recent Projects Block-->
        <div class="row">
            <div class="span3">
                <h3>ĺȻ����...</h3>
                <p class="hidden-phone">
				�����������˶�Զ����ͷ������Щ�߹������£���ʵҲ��һ���������ܡ�
				���µļ��䣬���õĻ��䣬��������ȥ�ߺ������·��
                </p>
            </div>
            <div class="span9">
                <div class="carousel btleft" id="latest-work">
                    <div class="carousel-wrapper">
                        <ul class="da-thumbs filtrable" style="width: 1800px; display: block; margin-left: 0px;">
                        	<c:choose>
                        		<c:when test="${classic!=null}">
                        			<c:forEach var="item" items="${classic}">
			                        	<li style="margin-right: 30px; width: 270px;">
			                                <img src="<%=basePath %>images/upload/<c:out value="${dorm:covertAddStr(item.picPath,'mid')}"/>" />
			                                <div style="display: block;" class="da-animate da-slideFromLeft">
			                                	<a class="p-view" href="#" onclick="addImgYes('<c:out value="${item.id}"/>');"></a>
		<!-- 	                                    <a data-rel="prettyPhoto" class="p-view" href="example/view.jpg" rel="prettyPhoto" onclick=""></a> -->
			                                    <a class="p-link" href="#" onclick="dispatchBlog('<c:out value="${item.id}"/>','dormIndex');"></a>
			                                </div>
			                            </li>
						        	</c:forEach>
                        		</c:when>
                        		<c:otherwise>
                        			<li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg" />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
		                            <li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg" />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
		                            <li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg" />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
		                            <li style="margin-right: 30px; width: 270px;">
		                                <img src="<%=basePath %>images/test1.jpg " />
		                                <div style="display: block;" class="da-animate da-slideFromLeft">
		                                	<a class="p-view" href="#" onclick="javascript:void(0)"></a>
		                                    <a class="p-link" href="#" onclick="javascript:void(0)"></a>
		                                </div>
		                            </li>
                        		</c:otherwise>
                        	</c:choose>
                        </ul>
                    </div>
                    <div class="es-nav"><span class="es-nav-prev" style="opacity: 0.5;">Previous</span><span class="es-nav-next" style="opacity: 1;">Next</span></div></div>
                <script type="text/javascript">
                    $(document).ready(function(){
                        $('#latest-work').elastislide({
                            imageW  : 270,
                            margin  : 30
                        });
                    });
                </script>
            </div>
        </div>
        <div class="divider">
            <img alt="" src="images/div-bg.jpg" />
        </div>
        <!--3 columns block-->
        <div class="row bottom30">
            <div class="span4 features-block">
                <img class="im-bottom" src="images/icon1a.png" />
                <img class="im-top" src="images/icon1.png" />
                <h3>���ư�</h3>
                <p> �ϴ���������������£�����������»��ˣ����ϴ����»��˵�������齫�ϴ������ư񣬹���ҵ��޻��߲��������ư�鿴
				                �������£��������˵�������ҳ��֮����ҳ�������ϱ߲��ֿ��ԶԷ��ư����
                ...</p>
                <button class="btn btn-read" type="button" onclick="javascript:alertFengYun();">��ɧһ��</button>
            </div>
            <div class="span4 features-block">

                <img class="im-bottom" src="images/icon2a.png" />
                <img class="im-top" src="images/icon2.png" />
                <h3>��һ��</h3>
                <p>
                	����·��ۿ�������Ӱ�ť��������Ϊ����ṩCCTV1,CCTV2,CCTV3,������
                	Ƶ��ͬ�����ţ���ҿ��Ը����Լ���ϲ��ѡ����ط�����ͬ�����Ÿ���Ŀ��
                	���ڸ�������CCTV5��CCTV5+������Ƶ���޷�����ͬ�����ţ�������кõĽ��飬
                	��վ���ṩ�����������ս�Ŀ�Ĳ��š�
                </p>
                <button class="btn btn-read" type="button" onclick="javascript:alertNetTv();" id="netTv">�ۿ��������</button>
            </div>
            <div class="span4 features-block">

                <img class="im-bottom" src="images/icon3a.png" />
                <img class="im-top" src="images/icon3.png" />
                <h3>41�üƻ�</h3>
                <p id="planWhere">
                	<c:choose>
                		<c:when test="${plans==null}">
                			<span> ������������������ļƻ���</span><br/>
                			<span> �����԰����뷨�������ǣ�</span><br/>
                			<span> һ����������ϲ�ã�</span><br/>
                			<span> һ���δ���������õ��㽣�</span><br/>
                		</c:when>
                		<c:otherwise>
                			<c:forEach items="${plans}" var="plan">
		                		<span><c:out value="${dorm:omitStr(plan.planDesc,30)}"/></span><br/>
		                	</c:forEach>
                		</c:otherwise>
                	</c:choose>
                </p>
                <button class="btn btn-read" type="button" onclick="JavaScript:alertAddPlan();">���뵽��һ���ƻ�</button>
            </div>
        </div>

</div></section>
<jsp:include page="include/footer.jsp" />