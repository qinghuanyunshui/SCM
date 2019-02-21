package com.crx.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.GoodsDAO;
import com.crx.dao.SupplierDAO;
import com.crx.model.Goods;
import com.crx.model.Supplier;
import com.google.gson.Gson;
@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GoodsDAO gdao = new GoodsDAO();
	private SupplierDAO sdao = new SupplierDAO();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String flag=request.getParameter("flag");
	   switch (flag) {
	      case "findAll":
		    findAll(request,response);
		  break;
	      case "add":
			    add(request,response);
			  break;
	      case "deleteOne":
	    	  deleteOne(request,response);
			  break;
	      case "yupdate":
	    	  yupdate(request,response);
			  break;
	      case "update":
	    	  update(request,response);
			  break;
	      case "deledeSome":
	    	  deledeSome(request,response);
			  break; 
	      case "findByName":
	    	  findByName(request,response);
			  break; 
	      case "check":
	    	  check(request,response);
			  break;
	      case "findSupName":
	    	  findSupName(request,response);
			  break;
	      case "findSupFullNameBlur":
	    	  findSupFullNameBlur(request,response);
			  break;
	      case "see":
	    	  see(request,response);
			  break; 
	      case "checkName":
	    	  checkName(request,response);
			  break; 
	default:
		break;
	}
	   
	  
	}

private void checkName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String name =request.getParameter("name");
	List<Goods> gs = gdao.findByFullName(name);
   // System.out.println(name+"--"+gs.size());
	String res ="";
	if(gs.size()>0){
		res="此商品已存在或还未被审核请勿重复添加";
	}
	try {
		response.getWriter().print(res);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

private void see(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id=request.getParameter("id");
	Goods goods=null;
	String str="";

	if(Integer.parseInt(id)==0){
		str="nodata";
	}else{
		goods=gdao.findById(Integer.parseInt(id));
	}
	try {
	if(goods==null){
		str="nodata";	
		response.getWriter().print(str);
	}else{
		response.getWriter().print(new Gson().toJson(goods));
	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

private void findSupFullNameBlur(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String name=request.getParameter("name");
	List<Supplier> sups =sdao.findSupFullNameByCon(name.replace("'", ""));
    String json = new Gson().toJson(sups);
    try {
    	if(sups.size()>0)
		 response.getWriter().print(json);
    	else{
    		 response.getWriter().print("nodata");	
    	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

private void findSupName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String name=request.getParameter("name");
	List<Supplier> goods =sdao.findSupNameByCon(name.replace("'", ""));
    String json = new Gson().toJson(goods);
    try {
    	if(goods.size()>0)
		 response.getWriter().print(json);
    	else{
    		 response.getWriter().print("");	
    	}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}


	private void check(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Boolean res=Boolean.parseBoolean(request.getParameter("res"));
		Goods g = gdao.findById(Integer.parseInt(id));
		g.setStatus(res?"已审核通过":"审核未通过");
		gdao.updateStatus(g);
		findAll(request, response);
	}


	private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		 request.setAttribute("goodslist", gdao.findByName(name));
		request.getRequestDispatcher("goods_list.jsp").forward(request, response);
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
			  gdao.deleteSome(ids);
		 }
		 findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id =Integer.parseInt(request.getParameter("id"));
		System.out.println("update -id"+id);
		String address=request.getParameter("address");
		String batch=request.getParameter("batch");
		String desc=request.getParameter("describe");
		String status = request.getParameter("status");
		double price=Double.parseDouble(request.getParameter("price"));
	    String size=request.getParameter("size");
	    int store=Integer.parseInt(request.getParameter("store"));
	    Supplier sup = sdao.findSupFullNameByCon(request.getParameter("sup")).get(0);
		
	    Goods goods =gdao.findById(id);
	    goods.setAddress(address);
	    goods.setBatch(batch);
	    goods.setDescribe(desc);
	    goods.setPrice(price);
	    goods.setSize(size);
	    goods.setStore(store);
	    goods.setSup(sup);
	  //  goods = new Goods(id, name, address, size, batch, desc, price, status, sup, store, "");
	    gdao.update(goods);
	    findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		String id = request.getParameter("id");
		Goods updateGoods = gdao.findById(Integer.parseInt(id));
		request.setAttribute("updateGoods", updateGoods);
		if(updateGoods.getStatus().equals("未审核"))
		request.setAttribute("opa", "edit");
		findAll(request, response);
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  String id = request.getParameter("id");
		  gdao.delete(Integer.parseInt(id));
		  findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		String address=request.getParameter("address");
		String size=request.getParameter("size");
		String batch=request.getParameter("batch");
		String describe=request.getParameter("describe");
		double price=Double.parseDouble(request.getParameter("price"));
		int store=Integer.parseInt(request.getParameter("store"));
		
		Supplier  sup=sdao.findSupFullNameByCon(request.getParameter("sup")).get(0);
		
		Goods g = new Goods(0,name,address,size,batch,describe,price,"未审核",sup,store,"");
		gdao.insert(g);
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  List<Goods> goods = gdao.findAll();
		  request.setAttribute("goodslist", goods);
		  try {
			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}