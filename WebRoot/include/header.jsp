<%@ page language="java" import="java.util.*,com.ybase.dorm.vo.*" pageEncoding="GB18030"%>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object drUser = request.getSession().getAttribute("loginusr");
String login = "false";
String usrname = "";
if(drUser !=null ){
	login = "true";
	usrname = ((DrUser)drUser).getName();
}
%>
<script>
	var context_path="<%=basePath%>";
	var login = "<%=login%>";
</script> 
<div id="top-menu">
    <div id="social" class="span4 pull-right">

        <div id="vimeo"> <a id="vimeo_img" href="#">Vimeo</a></div>
        <div id="lin"> <a id="lin_img" href="#">Linkedin</a></div>
        <div id="twitter"> <a id="twitter_img" href="#">Twitter</a></div>
        <div id="facebook"> <a id="facebook_img" href="#">Facebook</a></div>
        <div id="rss"> <a id="rss_img" href="#">Rss</a></div>
        <div id="gplus"> <a id="gplus_img" href="#">Google plus</a></div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="span3 logo">
            <a href="./index.html"><img src="images/logo.png" alt="logo" /></a>
        </div>
        <div class="span5 offset7 login-form hidden-phone" id="login"><p class="pull-right">�������ս���ȥ��<strong>�ഺ</strong>����������ϧ... </p>
            <form class="bs-docs-example form-inline pull-right" />
                <input type="text" placeholder="����" class="input-small" id="usrEmail" name="usrEmail" value="862968455@qq.com"/>
                <input type="password" placeholder="����" class="input-small" id="usrPasswd" name="usrPasswd" value="yxb"/>
                <button class="btn" type="submit" id="login-button">�ף���¼</button>
            </form>
       </div>
       <div class="span5 offset7 login-form hidden-phone" id="logout"><p class="pull-right">�������ս���ȥ��<strong>�ഺ</strong>����������ϧ... </p>
            <form class="bs-docs-example form-inline pull-right" action="javascript:void(0);"/>
                <p class="pull-right">���ã���ӭ������վ��<strong id="log-usr-name"><%=usrname%></strong>�����ڵ�½��...... 
                	<button class="btn" type="button" id="logout-button">ע��</button>
					<button class="btn" type="button" id="passwd-button">�����޸�</button>                
                </p>
            </form>
       </div>
    </div>
</div>