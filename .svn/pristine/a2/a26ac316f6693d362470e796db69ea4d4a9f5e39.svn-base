<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>移动/复制页面</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
	</head>
	<body>
		<s:include value="/header.jsp" />
		<table>
			<tr>
				<td colspan="3" class="tdWhiteLine"></td>
			</tr>
			<tr>
				<td colspan="3" class="tdHeader">移动/复制页:[同一调查内是移动,不同调查间是复制]</td>
			</tr>
			<tr>
				<td colspan="3" class="tdWhiteLine"></td>
			</tr>
			<s:iterator id="s" value="mySurveys">
				<s:set id="sId" value="#s.id" />
				<tr>
					<td colspan="3" class="tdSHeaderL"><s:property value="title" /></td>
				</tr>
				<s:bean id="pageComparator" name="survey.util.PageComparator"></s:bean>
				<s:sort comparator="#pageComparator" source="#s.pages" var="sortedPages"></s:sort>
				<s:iterator id="p" value="#attr.sortedPages" status="st">
					<s:set id="pId" value="#p.id"/>
					<!-- 当前的页面高亮 -->
					<s:if test="#pId == page.id">
						<!-- 设置字符串变量值,保持颜色 -->
						<s:set id="bgcolor" value="'pink'" />
					</s:if>
					<s:else>
						<s:set id="bgcolor" value="'white'" />
					</s:else>
					<tr bgcolor='<s:property value="#bgcolor"/>'>
						<td style="width:30px;border-width:0;background-color: white"></td>
						<td><s:property value="#p.title" /></td>
						<td>
							<s:if test="#pId != page.id">
								<s:form name="form%{#pId}" action="moveOrCopyPage" namespace="/page" method="post">
									<s:hidden name="page.id" />
									<s:hidden name="targetPage.id" value="%{#pId}" />
									<!-- 当移动/复制完成后,需要重定向到目标调查的设计页面 -->
									<s:hidden name="survey.id" value="%{#sId}" />
									<s:radio list="#{0:'之前',1:'之后'}" listKey="key" listValue="value" name="pos"/>
									<input type="submit" class="btn" value="确定" style="cursor:pointer">
								</s:form>
							</s:if>
						</td>
					</tr>
				</s:iterator>
			</s:iterator>
		</table>
	</body>
</html>