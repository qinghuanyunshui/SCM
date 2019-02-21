package com.crx.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.crx.dao.GoodsDAO;
import com.crx.dao.NeedDAO;
import com.crx.dao.PurchaseDAO;
import com.crx.model.Goods;
import com.crx.model.Purchase;
import com.google.gson.Gson;
@WebServlet("/PurchaseServlet")
@MultipartConfig
public class PurchaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PurchaseDAO pdao = new PurchaseDAO();
	private GoodsDAO gdao = new GoodsDAO();
	private NeedDAO ndao = new NeedDAO();
	private String oper="";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   String flag=request.getParameter("flag");
	   oper=request.getParameter("oper");
	   request.setAttribute("oper", oper);
	 
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
	      case "findByGoodsName":
	    	  findByGoodsName(request,response);
			  break; 
	      case "findByFullGoodsName":
	    	  findByFullGoodsName(request,response);
			  break; 
	      case "see":
	    	  see(request,response);
			  break;  
//	      case "upload":
//	    	  upload(request,response);
//			  break; 
	     case "check":
	    	  check(request,response);
			  break;
	     case "ywriteOffer":
	    	 ywriteOffer(request,response);
	    	 break;
	     case "writeOffer":
	    	 writeOffer(request,response);
	    	 break;
	     case "giveMoney":
	    	 giveMoney(request,response);
	    	 break;
	     case "ybill":
	    	 ybill(request,response);
	    	 break;
	     case "bill":
	    	 bill(request,response);
	    	 break;
		 
			  /*      case "findByGoodsName":
	    	  findByGoodsName(request,response);
			  break;
	      case "findByFullName":
	    	  findByFullName(request,response);
			  break;*/
	default:
		break;
	}
	   
	  
	}



private void bill(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase wPur = pdao.findById(Integer.parseInt(id));
	
	String st = request.getParameter("st");
	if(st.equals("yes")){
		String purdate= request.getParameter("purdate");
        Part part = request.getPart("bills");
        File f = new File("d:/SCM/bills");
        if(!f.exists())
        	f.mkdirs();
        String header = part.getHeader("content-disposition");
        String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String p=f.getPath() + File.separator + wPur.getGood().getName()+now+header.substring(header.lastIndexOf("."),header.length()-1); 
        part.write(p);
        wPur.setBills(p); 
        wPur.setPurdate(purdate);
        wPur.setStatus("已采购完成-等待入库");
	}else{
		 wPur.setStatus("采购失败");
	}
	
	
	pdao.update(wPur);
	findAll(request, response);
	}

private void ybill(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase upPur = pdao.findById(Integer.parseInt(id));
	
	
	request.setAttribute("upPur", upPur);
	if(upPur.getStatus().equals("请款成功-开始采购"))
	request.setAttribute("opa", "bill");
	findAll(request, response);
	}

private void giveMoney(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Boolean res=Boolean.parseBoolean(request.getParameter("res"));
	Purchase p= pdao.findById(Integer.parseInt(id));
	p.setStatus(res?"请款成功-开始采购":"请款失败");
	pdao.updateStatus(p);
	findAll(request, response);
}

private void writeOffer(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase wPur = pdao.findById(Integer.parseInt(id));
	double price = Double.parseDouble(request.getParameter("price"));
	wPur.setPrice(price);
	wPur.setStatus("请款中-已提交报价单");
	pdao.update(wPur);
	findAll(request, response);
	}

private void ywriteOffer(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id = request.getParameter("id");
	Purchase upPur = pdao.findById(Integer.parseInt(id));
	
	
	request.setAttribute("upPur", upPur);
	if(!upPur.getStatus().equals("请款中-已提交报价单"))
	   request.setAttribute("opa", "offer");
	findAll(request, response);
	}

private void see(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String id=request.getParameter("id");
	Purchase pur=null;
	String str="";

	if(Integer.parseInt(id)==0){
		str="nodata";
	}else{
		pur=pdao.findById(Integer.parseInt(id));
		if(pur==null){
				str="nodata";	
			}else{
				str=new Gson().toJson(pur);
			}
	}
	try {
		response.getWriter().print(str);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

private void findByFullGoodsName(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	String name=request.getParameter("name");
    List<Goods> goods =gdao.findByFullGoodsName(name.replace("'", ""));
    
    try {
    	String mess="";
	if(goods.size()==0)
		mess="nodata";
	else
        mess = new Gson().toJson(goods.get(0));
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
        
	    String json = new Gson().toJson(goods);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//private void findByFullName(HttpServletRequest request, HttpServletResponse response) {
//		// TODO Auto-generated method stub
//	String name=request.getParameter("name");
//	List<Goods> goods =gdao.findByFullName(name.replace("'", ""));
//	String mess="";
//	if(goods.size()==0){
//		mess="暂无此商品";
//	}
//    try {
//		response.getWriter().print(mess);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	}
//
//private void findByGoodsName(HttpServletRequest request, HttpServletResponse response) {
//		// TODO Auto-generated method stub
//		String name=request.getParameter("name");
//		List<Goods> goods =gdao.findByName(name.replace("'", ""));
//		Gson gson = new Gson();
//	    String json = gson.toJson(goods);
//	    try {
//			response.getWriter().print(json);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//private void findAllSup(HttpServletRequest request, HttpServletResponse response) {
//		// TODO Auto-generated method stub
//		/*List<Supplier> sups = sdao.findAll();
//		request.setAttribute("sups", sups);
//		Gson gson = new Gson();
//	    String json = gson.toJson(sups);
//	    try {
//			response.getWriter().print(json);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//	}

	private void check(HttpServletRequest request, HttpServletResponse response) {
          // TODO Auto-generated method stub
		String id = request.getParameter("id");
		Boolean res=Boolean.parseBoolean(request.getParameter("res"));
		Purchase p= pdao.findById(Integer.parseInt(id));
		p.setStatus(res?"请款中":"审核未通过");
		pdao.updateStatus(p);
		findAll(request, response);
	}


	private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		String oper =request.getParameter("oper");
		
		List<Goods> goods=gdao.findByName(name);
		
		switch (oper) {
		case "plan"://采购计划管理
			oper ="'未审核','审核未通过','请款中-未提交报价单'";//查询采购计划 ：采购单未审核或审核未通过的
			break;
		case "offer"://报价单提交
			oper ="'请款中-已提交报价单','请款中-未提交报价单'";
			break;
		case "money"://请款管理
			oper ="'请款中-已提交报价单','请款失败','请款成功-开始采购'";/*"请款成功-开始采购":"请款失败"*/
			break;
		case "bill"://采购发票管理
			oper ="'请款成功-开始采购','采购失败','已采购完成-等待入库'";/*'已采购完成==待入库'*/
			break;
		case "proof"://采购凭证管理
			oper ="'已采购完成-等待入库'";
			break;
		case "state"://结算单生成
			oper ="'已采购完成-等待入库'";
			break;
		default:
			break;
		}
		String ids="";
		for(Goods g:goods){
			ids+=g.getId()+",";
		}
		List<Purchase> purs=pdao.findByGIdsAndOper(ids.substring(0, ids.length()-1),oper);
		request.setAttribute("purlist", purs);
		  try {
			request.getRequestDispatcher("pur_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		 String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
			  pdao.deleteSome(ids);
		 }
		 findAll(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String id = request.getParameter("id");
		int num=Integer.parseInt(request.getParameter("num"));
		String demo =request.getParameter("demo");
	    Purchase pur = pdao.findById(Integer.parseInt(id));
	    pur.setNum(num);
	    pur.setDemo(demo);
	    pdao.update(pur);
	    findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		Purchase upPur = pdao.findById(Integer.parseInt(id));
		request.setAttribute("upPur", upPur);
		if(upPur.getStatus().equals("未审核"))
		   request.setAttribute("opa", "edit");
		findAll(request, response);
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  pdao.delete(Integer.parseInt(request.getParameter("id")));
		  findAll(request, response);
	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println(oper);
		if(oper.equals("")){
			
		}
		String name=request.getParameter("goods");
		int num=Integer.parseInt(request.getParameter("num"));
		String demo=request.getParameter("demo");
	    Goods gid =gdao.findByName(name).get(0);
	    String status="未审核";
	    double price=0.0;
	    String purdate="";
	    String bills="暂未上传";
	    
	  /* price= Double.parseDouble(request.getParameter("price"));
	   purdate = request.getParameter("purdate");
	   bills=request.getParameter("bills").length()>0?"已上传":"暂未上传";*/
	    
		Purchase p = new Purchase(0,gid,price,num,purdate,status,bills,demo);
		pdao.insert(p);
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		  List<Purchase> purs = null;
		  switch (oper) {
		case "plan"://采购计划管理
			purs =pdao.findAllByCondition("'未审核','审核未通过','请款中-未提交报价单'");//查询采购计划 ：采购单未审核或审核未通过的
			break;
		case "offer"://报价单提交
			purs =pdao.findAllByCondition("'请款中-已提交报价单','请款中-未提交报价单'");
			break;
		case "money"://请款管理
			purs =pdao.findAllByCondition("'请款中-已提交报价单','请款失败','请款成功-开始采购'");/*"请款成功-开始采购":"请款失败"*/
			break;
		case "bill"://采购发票管理
			purs =pdao.findAllByCondition("'请款成功-开始采购','采购失败','已采购完成-等待入库'");/*'已采购完成==待入库'*/
			break;
		case "proof"://采购凭证管理
			purs =pdao.findAllByCondition("'已采购完成-等待入库'");
			break;
		
		case "state"://结算单生成
			purs =pdao.findAllByCondition("'已采购完成-等待入库'");
			break;
		default:
			break;
		}
			
		  request.setAttribute("purlist", purs);
		  try {
			request.getRequestDispatcher("pur_list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
}