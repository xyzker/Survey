<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="divOuterFrame">
	<div class="divInnerFrame">欢迎使用SurveyDoor调查系统!</div>
</div>
<div class="divWhiteLine"></div>
<div class="divNavigatorOuterFrame">
	<div class="divNavigatorInnerFrame">
		<s:a action="index" namespace="/">[首页]</s:a>&nbsp;
		<s:a action="newSurvey" namespace="/survey">[新建调查]</s:a>&nbsp;
		<s:a action="mySurveys" namespace="/survey">[我的调查]</s:a>&nbsp;
		<s:a action="engageSurveyList" namespace="/engageSurvey">[参与调查]</s:a>&nbsp;
		<s:a action="register-input" namespace="/user">[用户注册]</s:a>&nbsp;
		<s:a action="findAllUsers" namespace="/authorize">[用户授权管理]</s:a>&nbsp;
		<s:a action="findAllRoles" namespace="/role">[角色管理]</s:a>&nbsp;
		<s:a action="findAllRights" namespace="/right">[权限管理]</s:a>&nbsp;
		<s:a action="findNearestLogs" namespace="/log">[日志管理]</s:a>&nbsp;
		<div style="float: right;">
			<s:if test="#session.user != null"><span><s:property value="#session.user.nickName"/></span><s:a action="logout" namespace="/user">[注销]</s:a>&nbsp;</s:if>
			<s:else>您还未登陆！<s:a action="index" namespace="/">[登陆]</s:a>&nbsp;</s:else>
		</div>
	</div>
</div>
<div class="divWhiteLine"></div>