package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Goods;
import com.crx.model.Need;
import com.crx.model.Supplier;
import com.crx.utils.DBService;

public class NeedDAO implements IBaseDAO<Need>{
 
   private GoodsDAO gdao =new GoodsDAO();
	@Override
	public void insert(Need t) {
		// TODO Auto-generated method stub
		String sql = "insert into need values(null,"+t.getGoods().getId()+","+t.getNum()+",'"+t.getStopdate()+"','"+t.getStatus()+"','"+t.getDemo()+"')";
		try {
			 Statement stmt = DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
		}
	}

	@Override
	public void update(Need t) {
		// TODO Auto-generated method stub
		String sql="update need set g_id='"+t.getGoods().getId()+"',stopdate='"+t.getStopdate()+"',num="+t.getNum()+",demo='"+t.getDemo()+"' where id="+t.getId();
		System.out.println(sql);
		try {
			 Statement stmt = DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		String sql="delete from need where id="+id;
		try {
			 Statement stmt = DBService.getStmt();
		stmt.executeUpdate(sql);
		stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Need findById(int id) {
		// TODO Auto-generated method stub
		String sql="select * from need where id="+id;
		System.out.println(sql);
		List<Need> needs = findBySQL(sql);
		return needs.size()>0?needs.get(0):null;
	}

	@Override
	public List<Need> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from need";
		return findBySQL(sql);
	}

	@Override
	public List<Need> findByName(String name) {
		// TODO Auto-generated method stub
		String sql="select * from need where name like '%"+name+"%'";
		
		return findBySQL(sql);
	}

	@Override
	public List<Need> findBySQL(String sql) {
		// TODO Auto-generated method stub
		List<Need> needs = new ArrayList<>();
		try {
			 Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Need n = new Need();
				n.setId(rs.getInt(1));
				n.setGoods(gdao.findById(rs.getInt(2)));
				n.setNum(rs.getInt(3));
				n.setStopdate(rs.getString(4));
				n.setStatus(rs.getString(5));
				n.setDemo(rs.getString(6));
				needs.add(n);
			}
			rs.close();
stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return needs;
	}

	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from need where id in("+ids+")";
		try {
			 Statement stmt = DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateStatus(Goods g) {
		// TODO Auto-generated method stub
		String sql="update goods set status='"+g.getStatus()+"' where id="+g.getId();
		try {
			 Statement stmt = DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Need> findByGIds(String ids) {
		// TODO Auto-generated method stub
		ids=ids.length()>0?ids.substring(0,ids.length()-1):ids;
		
		String sql = "select * from need where g_id in("+ids+")";
		return findBySQL(sql);
	}

	public void updateStatus(Need n) {
		// TODO Auto-generated method stub
		String sql="update need set status='"+n.getStatus()+"' where id="+n.getId();
		try {
			 Statement stmt = DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
