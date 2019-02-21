package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.User;
import com.crx.utils.DBService;

public class UserDAO implements IBaseDAO<User>{
   private Statement stmt = DBService.getStmt();
   
	public List<User> login(User u){
		System.out.println(u.getUsername());
		String sql="select * from user where username='"+u.getUsername()+"' and password='"+u.getPassword()+"'";
		System.out.println(sql);
		return findBySQL(sql);
	}
	
	
	@Override
	public void insert(User t) {
		String sql="insert into user values(null,'"+t.getUsername()+"','"+t.getPassword()+"','"+t.getLogtime()+"','"+t.getType()+"','"+t.getDemo()+"')";
		
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void update(User t) {
		// TODO Auto-generated method stub
		String sql = "update user set username='"+t.getUsername()+"',password='"+t.getPassword()+"',logtime='"+t.getLogtime()+"',type='"+t.getType()+"',demo='"+t.getDemo()+"' where id="+t.getId();
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		String sql="delete from user where id="+id;
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from user where id in("+ids+")";
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public User findById(int id) {
		String sql="select * from user where id="+id;
		return findBySQL(sql).get(0);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from user";
		return findBySQL(sql);
	}

	@Override
	public List<User> findByName(String name) {
		// TODO Auto-generated method stub
		String sql="select * from user where username='"+name+"'";
		return findBySQL(sql);
	}
	public List<User> findByNameExceptSelf(String findname,String selfname) {
		// TODO Auto-generated method stub
		String sql="select * from user where username='"+findname+"' and username!='"+selfname+"'";
		System.out.println(sql);
		return findBySQL(sql);
	}

	@Override
	public List<User> findBySQL(String sql) {
		// TODO Auto-generated method stub
		
		ResultSet rs;
		List<User> users = new ArrayList<>();
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()){
			   User u = new User();
			   u.setId(rs.getInt(1));
			   u.setUsername(rs.getString(2));
			   u.setPassword(rs.getString(3));
			   u.setLogtime(rs.getString(4));
			   u.setType(rs.getString(5));
			   u.setDemo(rs.getString(6));
			   users.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}


	public List<User> findByLikeName(String username) {
		// TODO Auto-generated method stub
		String sql="select * from user where username  like '%"+username+"%'";
		return findBySQL(sql);
	}

}
