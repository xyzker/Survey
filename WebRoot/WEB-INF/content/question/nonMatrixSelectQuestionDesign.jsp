<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>非矩阵型问题设计</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
	</head>
	<body>
		<s:include value="/header.jsp" />
		<s:form action="saveOrUpdateQuestion" namespace="/question" method="post">
		<s:hidden name="page.id" />
		<s:hidden name="question.questionType" />
		<s:hidden name="question.page.id"/>
		<s:hidden name="question.id"/>
		<table>
			<tr>
				<td colspan="2" class="tdQHeaderL">非矩阵型问题设计:</td>
			</tr>
			<tr>
				<td width="35%" style="text-align: right;">问题标题:</td>
				<td width="*" style="text-align: left;"><s:textfield name="question.title" cssClass="text" /></td>
			</tr>
			<tr>
				<td style="text-align: right;vertical-align: top;">问题选项:</td>
				<td width="*" style="text-align: left;"><s:textarea cols="41" rows="8" name="question.options"/></td>
			</tr>
			<tr>
				<td style="text-align: right;"></td>
				<td width="*" style="text-align: left;"><input type="submit" name="ok" value="确定" class="btn" style="cursor:pointer"></td>
			</tr>
		</table>
		</s:form>
	</body>
</html>