<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/css/main.css"
	type="text/css" rel="styleSheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/table.js" charset="gb2312"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/formcheck.js"
	charset="gb2312"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/purchase_check.js"
	charset="gb2312"></script>
<style type="text/css">
/* 增加框 复写*/
section  .alertDiv-add {
	width: 400px;
	height: 320px;
	position: absolute;
	top: 20%;
	left: 30%;
	/*      padding-bottom:70px; */
}

section  .alertDiv-add #searchbox {
	left: 110px;
	width: 248px;
}
</style>

</head>
<body>

	<jsp:include page="head.jsp"></jsp:include>
	<section>
		<input type="hidden" value="goods" id="hid" />
		<div class="dirbox">
			<span>采购管理&nbsp;&nbsp;>&nbsp;&nbsp;采购计划管理</span>
		</div>
		<div class="tablebox">
			<form action="PurchaseServlet?flag=findByName&oper=${oper }"
				method="post" class="findbox">
				<div class="btn-add" style="${(oper eq 'offer')||(oper eq 'money')||(oper eq 'bill')||(oper eq 'proof')||(oper eq 'state') ?'background:gray':''}">+&nbsp;新增</div>
				<div class="btn-delete">🗑&nbsp;删除</div>
				<div class="btn-export">🎞&nbsp;导出</div>
				<input type="hidden" value="${oper }" name="oper" id="oper">
				<input type="text" placeholder="请输入商品名称..." name="name" />
				<div id="btn-find"></div>
			</form>
			<table>
				<tr>
					<td width="50"><input type="checkbox" name="check"
						id="checkAll" /></td>
					<td width="50">编号</td>
					<td width="200">商品名称</td>
					<td width="200">供应商</td>
					<td width="150">数量</td>
					<td width="150">单价</td>
					<td width="150">总价</td>
					<td width="200">状态</td>
					<td width="300">操作</td>
				</tr>
				<c:forEach items="${purlist }" var="pl" begin="0" end="9">
					<tr>
						<td><input type="checkbox" name="check" id="c${pl.id }" /></td>
						<td>${pl.id }</td>
						<td>${pl.good.name }</td>
						<td>${pl.good.sup.name }</td>
						<td>${pl.num}</td>
					    <td>${pl.good.price}</td>
					    <td> <fmt:formatNumber value="${pl.good.price*pl.num}" pattern="#.00"/></td>
						<td>${pl.status }</td>
						<td>
						
						<div class="btn-delete">
						 <a href="PurchaseServlet?flag=deleteOne&id=${pl.id }&oper=${oper}">
						 <img src="${pageContext.request.contextPath}/img/btn-delete.png" />删除</a>
						</div>
						
						<c:choose>
								<c:when test="${oper eq 'plan' }"><!-- 计划 -->
									<!-- 普通采购业务员 无此项功能  只有采购主管有审核 -->
									<div class="btn-check" id="${pl.id }"
										${pl.status ne "未审核"?"style='background:gray;'":""}>
										<a> <img src="${pageContext.request.contextPath}/img/btn-check.png" />审核</a>
									</div>
									<div class="btn-edit" id="${pl.id }"  ${pl.status ne "未审核"?"style='background:gray;'":"" }>
										<a class="edit-data" href="PurchaseServlet?flag=yupdate&id=${pl.id }&oper=${oper}">
											<img src="${pageContext.request.contextPath}/img/btn-edit.png" />编辑
										</a>
									</div>
								</c:when>
								<c:when test="${oper eq 'money' }"><!-- 请款 -->
								 <div class="btn-check" id="${pl.id }"
										${pl.status eq "请款失败"?"style='background:gray;'":""}>
										<a> <img src="${pageContext.request.contextPath}/img/btn-check.png" />请款</a>
								  </div>
							    </c:when>
								
								<c:when test="${oper eq 'offer' }"><!-- 报价单 -->
								<div class="btn-check" style="width:80px;${pl.status eq '请款中-已提交报价单'?'background:gray;':''}" id="${pl.id }">
										<a class="edit-data" href="PurchaseServlet?flag=ywriteOffer&id=${pl.id }&oper=${oper}">
											<img src="${pageContext.request.contextPath}/img/btn-edit.png" />填写报价单</a>
									</div>
								</c:when>
								<c:when test="${oper eq 'bill' }"><!-- 发票 -->
								    <div class="btn-edit"  style="width:65px;${pl.status ne '请款成功-开始采购'?'background:gray;':''}" id="${pl.id }" >
										<a class="edit-data" href="PurchaseServlet?flag=ybill&id=${pl.id }&oper=${oper}">
											<img src="${pageContext.request.contextPath}/img/btn-edit.png" />上传发票</a>
									</div>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
							</td>
					</tr>
				</c:forEach>


			</table>
			<div class="see">
				<ul></ul>
			</div>
		</div>
		<div class="inpage">分页预留位置</div>
		<footer>
			CopyRight &copy; 2018 创睿信教育集团 <br> <br> Powered by Teacher
			Lei版权所有
		</footer>

		<!-- 弹出层  -->
		<%-- <c:if test="${opa eq 'edit' }">style='height:420px' enctype='multipart/form-data'</c:if> --%>
		
		<div class="alertDiv" style="${(opa eq 'edit')||(opa eq 'offer')||(opa eq 'bill')?'display:block;':''}">
		
		    
			<form action="PurchaseServlet?flag=<c:choose><c:when test="${opa eq 'edit'}">update</c:when><c:when test="${opa eq 'offer'}">writeOffer</c:when><c:when test="${opa eq 'bill'}">bill</c:when><c:otherwise>add</c:otherwise></c:choose>&oper=${oper}"
				method="post" id="addform" class="alertDiv-add" style="${(opa eq 'offer')?'height:450px;':''}${opa eq 'bill'?'height:470px;top: 10%;':'' }" enctype="multipart/form-data">
				<div class="hid">×</div>
				
      品名：<input type="text" placeholder="" autocomplete="on" name="goods" required value="${upPur.good.name }"
					${(opa eq 'edit')||(opa eq 'offer')||(opa eq 'bill' )?'disabled':'' } /> <br />
		 <div id="searchbox"> <ul></ul> </div>
		 
        <input type="hidden" value="${upPur.id }" name="id" >
   供应商：<span id="goods_sup" style="color: gray;margin-right:80px;">${upPur.good.sup.name }</span>
   进价：<span id="goods_pice" style="color: gray">${upPur.good.price } </span>  <br />    <br /> <br />   
   数量：<input type="text" placeholder="" name="num" required  value="${upPur.num }" style="width: 60px; readonly margin-right: 30px;" ${(opa eq 'offer')||(opa eq 'bill' )?'disabled':''}/>
				<span id="mess" style="margin: 10px"></span><br />
  用途：<input type="text" placeholder="" name="demo" style="width: 280px" value="${upPur.demo }" ${(opa eq 'offer')||(opa eq 'bill' )?'disabled':'' }/><br />

<c:if test="${(opa eq 'offer')||(opa eq 'bill') }">
 总价：<fmt:formatNumber value="${upPur.num*upPur.good.price }" pattern="#.00"/>元<br><br>
 请款金额：<input type="text" placeholder="" name="price" required value="${upPur.price }"  ${opa eq 'bill'?'disabled':'' }/><br />
 </c:if> 
 <c:if test="${opa eq 'bill' }">
此单状态：<input type="radio" name="st" value="yes" />采购成功-上传采购单
         <input type="radio" name="st" value="采购失败 "/>采购失败<br>
         <div id="upbill_box" style="display:none;">
采购日期：<input type="date" name="purdate" placeholder="" style="width:240px;" value="${upPur.purdate }"/><br />
上传发票：<input type="file" name="bills" id="bills" placeholder="" style="width:240px;" accept="image/*"/><br />
<!-- <div class="bills-box"><img/></div> --></div>
</c:if>
				 <input type="submit" value="${opa eq 'edit'?'修     改':'增    加' }" class="btn" />
                 <input type="reset" value="取     消" class="btn" />
			</form>
		</div>


	</section>
</body>
</html>