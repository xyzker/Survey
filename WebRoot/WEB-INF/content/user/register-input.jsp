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
		<title>注册页面</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
		<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/user.js"></script>
	</head>
	<body>
		<s:include value="/header.jsp" />
		<s:form action="register" namespace="/user" method="post" onsubmit="return check_all();">
		<table>
			<tr>
				<td colspan="2" class="tdWhiteLine"></td>
			</tr>
			<tr>
				<td colspan="2" class="tdHeader">注册新用户</td>
			</tr>
			<tr>
				<td colspan="2" class="tdWhiteLine"></td>
			</tr>
			<tr>
				<td class="tdFormLabel">E-mail:</td>
				<td class="tdFormControl">
					<s:textfield name="user.email" id="email" cssClass="text" onblur="check_email(); validate_email();"/>
					<font class="fonterror" id="emailError"><s:fielderror></s:fielderror></font>
				</td>
			</tr>
			<tr>
				<td class="tdFormLabel">密  &nbsp;&nbsp;  码:</td>
				<td class="tdFormControl">
					<s:password name="user.password" cssClass="text" id="password1" onblur="check_passNotNull();"/>
					<font class="fonterror" id="passNotNullError"></font>
				</td>
			</tr>
			<tr>
				<td class="tdFormLabel">确认密码:</td>
				<td class="tdFormControl"><s:password id="password2" cssClass="text" onblur="check_password();"/>
				<font class="fonterror" id="passwordError"></font>
				</td>
			</tr>
			<tr>
				<td class="tdFormLabel">昵称:</td>
				<td class="tdFormControl">
					<s:textfield name="user.nickName" id="nickName" cssClass="text" onblur="check_nickName();"/>
					<font class="fonterror" id="nickNameError"></font>
				</td>
			</tr>
			<tr>
				<td class="tdFormLabel"></td>
				<td class="tdFormControl"><s:submit cssClass="btn" cssStyle="cursor:pointer" value="确定"/></td>
			</tr>
		</table>
		</s:form>
	</body>
</html>