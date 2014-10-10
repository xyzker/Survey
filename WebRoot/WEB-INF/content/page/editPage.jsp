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
		<title>增加/编辑页内容</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
	</head>
	<body>
		<s:include value="/header.jsp" />
		<table>
			<tr>
				<td class="tdHeader">增加/编辑页内容:</td>
			</tr>
			<tr>
				<td style="vertical-align: top;">
					<table>
						<tr>
							<td>
								<s:form action="saveOrUpdatePage" namespace="/page" method="post">
								<s:hidden name="page.id" />
								<s:hidden name="survey.id" />
								<table>
									<tr>
										<td class="tdFormLabel">页面标题:</td>
										<td class="tdFormControl"><s:textfield name="page.title" cssClass="text" /></td>
									</tr>
									<tr>
										<td class="tdFormLabel">页面描述:</td>
										<td class="tdFormControl"><s:textarea name="page.description" cssClass="text" cols="20" rows="8"/></td>
									</tr>
									<tr>
										<td class="tdFormLabel"></td>
										<td class="tdFormControl"><s:submit value="确定" cssClass="btn" cssStyle="cursor:pointer"/>
											<input type="button" value="取消" class="btn" style="cursor:pointer" onclick="javascript:window.history.go(-1);"/>
										</td>
									</tr>
								</table>
								</s:form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>