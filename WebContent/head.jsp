<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/css/icon.css"  type="text/css" rel="styleSheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tree.js"></script>
</head>
<body>
 <header>
      <img src="${pageContext.request.contextPath}/img/logo白白.png"/>
        <span>供应链管理系统</span>
        <span style="font-size:12px;margin-top:1px;">Supply Chain Management</span>
        <div class="user-txt">${loginUser.type }：${loginUser.username}</div>
          <div class="user-icon"><img alt="" src="${pageContext.request.contextPath}/img/user-icon.jpg"></div>
 </header>
   <nav>
      <ul>
         <li><a class="ina">
            <i class="icon-need"></i>
           <span>需求计划管理</span></a>
            <ul>
            <li><a href="NeedServlet?flag=findAll&isCheck=add">需求计划提交</a></li>
            <li><a href="NeedServlet?flag=findAll&isCheck=false">需求计划汇总</a></li>
            <li><a href="NeedServlet?flag=findAll&isCheck=true">需求计划审批</a></li>
            </ul>
         </li>
         <li><a class="ina">
         <i class="icon-purchase"></i>
         <span>采购管理</span></a>
         <ul>
            <li><a href="PurchaseServlet?flag=findAll&oper=plan">采购计划管理</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=offer">报价单提交</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=money">请款管理</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=bill">采购发票管理</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=proof">采购凭证管理</a></li>
            <li><a href="PurchaseServlet?flag=findAll&oper=state">结算单生成</a></li>
         </ul>
         </li>
         <li><a class="ina">
         <i class="icon-supplier"></i>
         <span>供应商管理</span></a>
          <ul>
            <li><a href="SupplierServlet?flag=findAll&isCheck=false">供应商选择</a></li>
            <li><a href="SupplierServlet?flag=findAll&isCheck=true">供应商考核</a></li>
         </ul>
         </li>
         <li><a class="ina"><i class="icon-stock"></i>
         <span>库存管理</span></a>
         <ul>
            <li><a href="#">入库管理</a></li>
            <li><a href="#">保管账管理</a></li>
            <li><a href="#">物资出库管理</a></li>
            <li><a href="#">盘点耗损管理</a></li>
         </ul>
         </li>
          <li><a class="ina"><i class="icon-data"></i>
         <span>基础数据管理</span></a>
         <ul>
            <li><a href="GoodsServlet?flag=findAll">物资信息管理</a></li>
            <li><a href="#">物资地区价格</a></li>
            <li><a href="#">合同条款维护</a></li>
            <li><a href="UserServlet?flag=findAll">用户管理</a></li>
         </ul>
         </li>
          <li><a class="ina"><i class="icon-search"></i>
         <span>查询分析</span></a>
         <ul>
            <li><a href="#">需求查询</a></li>
            <li><a href="#">采购查询</a></li>
            <li><a href="#">库存查询</a></li>
         </ul>
         </li>
      </ul>
</nav>
</body>
</html>