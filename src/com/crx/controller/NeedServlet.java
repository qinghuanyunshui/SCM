package com.crx.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.GoodsDAO;
import com.crx.dao.NeedDAO;
import com.crx.dao.PurchaseDAO;
import com.crx.model.Goods;
import com.crx.model.Need;
import com.crx.model.Purchase;
import com.google.gson.Gson;
@WebServlet("/NeedServlet")
public class NeedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NeedDAO ndao = new NeedDAO();
	private GoodsDAO gdao = new GoodsDAO();
	private PurchaseDAO pdao = new PurchaseDAO();
	private String isCheck="";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String flag=request.getParameter("flag");
	   isCheck=request.getParameter("isCheck");
	   request.setAttribute("isCheck", isCheck);
	 
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
	      case "findByGoodsName":
	    	  findByGoodsName(request,response);
			  break;
	      case "findByFullGoodsName":
	    	  findByFullGoodsName(request,response);
			  break;
	default:
		break;
	}
	   
	  
	}

private void findByFullGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String name=request.getParameter("name");
	List<Goods> goods =gdao.findByFullGoodsName(name.replace("'", ""));
	String mess="";
	if(goods.size()==0){
		mess="暂无此商品，或此商品未通过审核";
	}
    try {
		response.getWriter().print(mess);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

private void findByGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		List<Goods> goods =gdao.findByGoodsName(name.replace("'", ""));
		Gson gson = new Gson();
	    String json = gson.toJson(goods);
	    try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void check(HttpServletRequest request, HttpServletResponse response) {
          // TODO Auto-generated method stub
		String id = request.getParameter("id");
		String ischeck=request.getParameter("ischeck");
		Boolean res=Boolean.parseBoolean(request.getParameter("res"));
		Need n = ndao.findById(Integer.parseInt(id));
		n.setStatus(res?"已审核通过":"审核不通过");
		ndao.updateStatus(n);
		
		//如果需求表审核通过  默认存入采购表 初始状态为 未审核
		if(res){
			Purchase pur = new Purchase(0,n.getGoods(),0,n.getNum(),"","未审核","","");
			pdao.insert(pur);
		}
	
		request.setAttribute("ischeck", ischeck);
		findAll(request, response);
	}


	private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		String ischeck=request.getParameter("ischeck");
		
		List<Goods> goods=gdao.findByName(name);
		String ids="";
		for(Goods g:goods){
			ids+=g.getId()+",";
		}
		List<Need> needs=ndao.findByGIds(ids);
		request.setAttribute("needlist", needs);
		request.setAttribute("ischeck", ischeck);
		request.getRequestDispatcher("need_list.jsp").forward(request, response);
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
			  ndao.deleteSome(ids);
		 }
		 findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		int id=Integer.parseInt(request.getParameter("id"));
		Goods name=gdao.findByFullName(request.getParameter("g")).get(0);
		String stopdate=request.getParameter("stopdate");
		int num=Integer.parseInt(request.getParameter("num"));
	   String status =request.getParameter("status");
		Need need = new Need(id,name,num,stopdate,status,"");
	    ndao.update(need);
	    findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Need updateNeed = ndao.findById(Integer.parseInt(id));
		request.setAttribute("updateNeed", updateNeed);
		
		if(updateNeed.getStatus().equals("未审核"))
			request.setAttribute("opa", "edit");
			findAll(request, response);
		
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String id = request.getParameter("id");
		 String ischeck=request.getParameter("ischeck");
		  ndao.delete(Integer.parseInt(id));
		  request.setAttribute("ischeck", ischeck);
		  findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Goods goods =  gdao.findByFullName(request.getParameter("goods")).get(0);
		String num=request.getParameter("num");
		String stopdate=request.getParameter("stopdate");
		String status="未审核";
		Need n = new Need(0,goods,Integer.parseInt(num),stopdate,status,"");
		ndao.insert(n);
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  List<Need> needs = ndao.findAll();
		  request.setAttribute("needlist", needs);
		  try {
			request.getRequestDispatcher("need_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}