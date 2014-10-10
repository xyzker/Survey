<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>登陆页面</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
		<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/user.js"></script>
	</head>
	<body>
		<s:include value="header.jsp" />
		<s:if test="#session['user'] != null">
			<div class="divNavigatorOuterFrame">
				<div class="divNavigatorInnerFrame" style="text-align: left;">
					欢迎您，<s:property value="#session['user'].nickName" />！&nbsp;&nbsp;
				</div>
			</div>
			<div class="divWhiteLine"></div>
			${message}
		</s:if>
		<s:else>
		<s:form action="login" namespace="/user" method="post" onsubmit="return check_login();">
		<s:actionerror cssStyle="color:red"/>
		<table>
			<tr>
				<td colspan="2" class="tdWhiteLine"></td>
			</tr>
			<tr>
				<td class="tdHeader" colspan="2">用户登陆&nbsp;&nbsp;<span style="color:red">${message}</span></td>
			</tr>
			<tr>
				<td colspan="2" class="tdWhiteLine"></td>
			</tr>
			<tr>
				<td class="tdFormLabel" width="40%">E-mail:</td>
				<td class="tdFormControl">
					<input type="text"  id="email" name="user.email" class="text" onblur="check_email();">
					<font class="fonterror" id="emailError"></font>
				</td>
			</tr>
			<tr>
				<td class="tdFormLabel">密码:</td>
				<td class="tdFormControl">
					<input type="password" id="password1" name="user.password" class="text" onblur="check_passNotNull();">
					<font class="fonterror" id="passNotNullError"></font>
				</td>
			</tr>
			<tr>
				<td class="tdFormLabel"></td>
				<td class="tdFormControl"><s:submit type="submit" cssClass="btn"  cssStyle="cursor:pointer" value="登录" /></td>
			</tr>
		</table>
		</s:form>
		</s:else>
	</body>
</html>