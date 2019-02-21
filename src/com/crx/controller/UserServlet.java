package com.crx.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crx.dao.UserDAO;
import com.crx.model.User;
import com.google.gson.Gson;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO udao = new UserDAO();

    public UserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String flag=request.getParameter("flag");
		switch (flag) {
		case "login":
			login(request,response);
			break;
		case "findAll":
			findAll(request,response);
			break;
		case "add":
			AddUser(request,response);
			break;
		case "check":
			check(request,response);
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
		case "findByName":
			findByName(request,response);
		    break;
		case "deledeSome":
			deledeSome(request,response);
		    break;
		default:
			break;
		}
	}

	private void deledeSome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String ids = request.getParameter("ids");
		 if(ids!=null&&!ids.equals("")){
			  ids=ids.substring(0,ids.length()-1);
			  udao.deleteSome(ids);
		 }
		 findAll(request, response);
	}

	private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username=request.getParameter("username");
		request.setAttribute("userlist", udao.findByLikeName(username));
		request.getRequestDispatcher("user_list.jsp").forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		User user = (User) request.getSession().getAttribute("updateUser");
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setType(request.getParameter("type"));
		udao.update(user);
		findAll(request, response);
	}

	private void yupdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		User user = udao.findById(Integer.parseInt(id));
		request.getSession().setAttribute("updateUser", user);
		
	    response.getWriter().print(new Gson().toJson(user));
	}

	private void deleteOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id=request.getParameter("id");
		udao.delete(Integer.parseInt(id));
		findAll(request, response);
	}

	private void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username=request.getParameter("username");
	    String choose = request.getParameter("choose");
	    User user = (User)request.getSession().getAttribute("updateUser");
	    if(choose.equals("edit")){
	    	if(udao.findByNameExceptSelf(username, user.getUsername()).size()>0){
	    		response.getWriter().print("no");
	    	}
	    }else{
	    	if(udao.findByName(username).size()>0){
				response.getWriter().print("no");
			}else{
				response.getWriter().print("yes");
			}
	    }
		
	}

	private void AddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String type=request.getParameter("type");
		User user = new User(0,username,password,"",type,"");
		udao.insert(user);
		request.setAttribute("opr","add");
		findAll(request, response);
	}

	private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     List<User> users =udao.findAll();
		 request.setAttribute("userlist", users);
		 request.getRequestDispatcher("user_list.jsp").forward(request, response);
		 return;
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     String username =request.getParameter("username");
	     String password =request.getParameter("password");
	 
	     User user = new User(0,username,password,"","","");
	     List<User> users =udao.login(user);
	     if(users.size()>0){
	    	 String logtime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
	    	 User u = users.get(0);
	    	 u.setLogtime(logtime);
	    	 udao.update(u);
	    	 
	    	 request.getSession().setAttribute("loginUser", u);
	    	 request.getRequestDispatcher("main.jsp").forward(request, response);
	     }else{
	    	 request.setAttribute("message", "您的用户名或密码错误！");
	    	 request.getRequestDispatcher("login.jsp").forward(request, response);
	     }
		return ;
	}
	
}
