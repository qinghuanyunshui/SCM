<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/css/main.css"  type="text/css" rel="styleSheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/table.js" charset="gb2312"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formcheck.js" charset="gb2312"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/usercheck.js" charset="gb2312"></script> 
<script type="text/javascript">
 
</script>
</head>
<body>
 
<jsp:include page="head.jsp"></jsp:include>
<section>

<div class="dirbox">
    <span>基础数据管理&nbsp;&nbsp;>&nbsp;&nbsp;用户管理</span>
</div>
<div class="tablebox">
<form action="UserServlet?flag=findByName" method="post" class="findbox">
       <div class="btn-add">+&nbsp;新增</div>
       <div class="btn-delete">🗑&nbsp;删除</div>
       <div class="btn-export">🎞&nbsp;导出</div>
       <input type="text" placeholder="请输入要查询的用户名..." name="username"/>
       <div id="btn-find"></div>
 </form>
    <table>
   <tr>
      <td width="50"><input type="checkbox" name="check" id="checkAll"/></td>
      <td width="100" >编号</td>
      <td width="200">用户名</td>
      <td width="200">身份</td>
      <td width="300">最后登录时间</td>
      <td width="200">操作</td>
   </tr>
   <c:forEach items="${userlist }" var="ul" begin="0" end="9">
         <tr>
      <td><input type="checkbox" name="check" id="c${ul.id }"/></td>
      <td>${ul.id }</td>
      <td>${ul.username }</td>
      <td>${ul.type}</td>
      <td>${ul.logtime }</td>
      <td><div class="btn-delete"><a href="UserServlet?flag=deleteOne&id=${ul.id }">
      <img src="${pageContext.request.contextPath}/img/btn-delete.png"/>删除</a></div>
          <div class="btn-edit" id="${ul.id }"><a class="edit-data" >
          <img src="${pageContext.request.contextPath}/img/btn-edit.png"/>编辑</a></div></td>
   </tr> 
   </c:forEach>
   
   
   </table> 
   </div>
   <div class="inpage">分页预留位置</div>
   <footer>CopyRight<br> <br>
  
   </footer>
   
   <!-- 弹出层  -->
   
   <div class="alertDiv">
         <form action="UserServlet?flag=add" method="post" id="addform" class="alertDiv-add">
           <div class="hid">×</div>
           <input type="hidden" value="user" class="hid"/>
           <input type="text" placeholder="用户名" name="username" required id="username" /><br /> 
		   <input type="password" placeholder="密码"  name="password" required  id="password" /><br /> 
			<select name="type">
			  <option value="" selected>---请选择身份类型---</option>
			  <option value="需求计划员">需求计划员</option>
			  <option value="采购业务员">采购业务员</option>
			  <option value="采购业务主管">采购业务主管</option>
			  <option value="结算员">结算员</option>
			  <option value="保管员">保管员</option>
			</select><br /> 
			<input type="submit" value="增    加" class="btn" />
			<input type="reset" value="取     消" class="btn" />
            
         </form>
       </div>
</section>
</body>
</html>