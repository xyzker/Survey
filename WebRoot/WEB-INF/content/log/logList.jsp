<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>日志管理</title>
		<link rel="stylesheet" type="text/css" href='<s:url value="/styles.css" />'>
		<script type="text/javascript" src='<s:url value="/js/jquery-1.11.1.min.js" />'></script>
		<script type="text/javascript">
		$(function(){
			$("input[name^=r_]").parent().click(function(){
				$(this).children("input[name^=r_]").removeAttr("disabled");
				if($(this).children("input").attr("value") == "未命名"){
					$(this).children("input").attr("value","");
				}
				$(this).children("input[name^=r_]").focus();
			});	
			$("input[name^=r_]").attr("disabled","disabled");
		});
		
		//全选
		$(function(){
			$('#cbSelectAll').click(function(){
				var v = $(this).prop("checked");
				if(v == true){
					$(":checkbox").prop("checked",true);
				}
				else{
					$(":checkbox").prop("checked",false);
				}
			});
			
			$('#inverseSelectAll').click(function(){
				$(":checkbox").each(function(){
					var v = $(this).prop("checked");
					$(this).prop("checked",!v);
				});
			});
		});
		</script>
	</head>
	<body>
		<s:include value="/header.jsp" />
		<table>
			<tr>
				<td colspan="10" style="height: 5px"></td>
			</tr>
			<tr>
				<td colspan="10" class="tdPHeaderR">
					<s:form namespace="/log" action="findNearestLogs">
						最近<s:textfield name="monthNum" cssClass="text" cssStyle="width:20px"/>个月的日志记录（最多12个月）
						<s:submit cssClass="button" value="查询"/>
					</s:form>
				</td>
			</tr>
			<tr>
				<td colspan="10" style="height: 5px"></td>
			</tr>
		</table>
		<s:if test="logs.isEmpty() == true">没有日志!</s:if >
		<s:else>
			<table>
				<thead>
					<tr>
						<td colspan="10" style="height: 5px"></td>
					</tr>
					<tr>
						<td colspan="10" class="tdHeader">日志管理:</td>
					</tr>
					<tr>
						<td class="tdListHeader">操作人</td>
						<td class="tdListHeader">操作名称</td>
						<td class="tdListHeader">参数</td>
						<td class="tdListHeader">操作结果</td>
						<td class="tdListHeader">消息</td>
						<td class="tdListHeader">时间</td>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="logs" status="st">
						<tr>
							<td>
								<s:property value="operator" />
							</td>
							<td>
								<s:property value="operName" />
							</td>
							<td>
								<span title='<s:property value="operParams"/>'><s:property value="@survey.util.StringUtil@getDescString(operParams)"/></span>
							</td>
							<td>
								<span title='<s:property value="operResult"/>'><s:property value="@survey.util.StringUtil@getDescString(operResult)"/></span>
							</td>
							<td>
								<span title='<s:property value="resultMsg"/>'><s:property value="@survey.util.StringUtil@getDescString(resultMsg)"/></span>
							</td>
							<td>
								<s:date name="operTime" format="yyyy/MM/dd hh:mm:ss"/>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		</s:else>
	</body>
</html>