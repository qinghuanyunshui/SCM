package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Goods;
import com.crx.utils.DBService;

public class GoodsDAO implements IBaseDAO<Goods>{
  
   private SupplierDAO sdao =new SupplierDAO();
	@Override
	public void insert(Goods t) {
		// TODO Auto-generated method stub
		String sql = "insert into goods values(null,'"+t.getName()+"','"+t.getAddress()+"','"+t.getSize()+"','"+t.getBatch()+"','"+t.getDescribe()+"','"+t.getPrice()+"','"+t.getStatus()+"','"+t.getSup().getId()+"','"+t.getStore()+"','"+t.getDemo()+"')";
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
	public void update(Goods t) {
		// TODO Auto-generated method stub
		String sql="update goods set name='"+t.getName()+"',address='"+t.getAddress()+"',size='"+t.getSize()+"',batch='"+t.getBatch()+"',describes='"+t.getDescribe()+"',price="+t.getPrice()+",status='"+t.getStatus()+"',sup_id="+t.getSup().getId()+",store='"+t.getStore()+"',demo='"+t.getDemo()+"' where id="+t.getId();
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
		String sql="delete from goods where id="+id;
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
	public Goods findById(int id) {
		// TODO Auto-generated method stub
		String sql="select * from goods where id="+id;
		List<Goods> goods = findBySQL(sql);
		return goods.size()>0?goods.get(0):null;
	}

	@Override
	public List<Goods> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from goods";
		return findBySQL(sql);
	}

	@Override
	public List<Goods> findByName(String name) {
		// TODO Auto-generated method stub
		String sql="select * from goods where name like '%"+name+"%'";
		return findBySQL(sql);
	}
	
	
	@Override
	public List<Goods> findBySQL(String sql) {
		// TODO Auto-generated method stub
		List<Goods> goods = new ArrayList<>();
		try {
			 Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Goods g = new Goods();
				g.setId(rs.getInt(1));
				g.setName(rs.getString(2));
				g.setAddress(rs.getString(3));
				g.setSize(rs.getString(4));
				g.setBatch(rs.getString(5));
				g.setDescribe(rs.getString(6));
				g.setPrice(rs.getDouble(7));
				g.setStatus(rs.getString(8));
				g.setSup(sdao.findById(rs.getInt(9)));
				g.setStore(rs.getInt(10));
				g.setDemo(rs.getString(11));
				goods.add(g);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return goods;
	}

	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from goods where id in("+ids+")";
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

	public List<Goods> findByFullName(String name) {
		// TODO Auto-generated method stub
	String sql="select * from goods where name ='"+name+"' and status!='审核未通过'";
	System.out.println(sql);
		return findBySQL(sql);
	}

	//public List<Goods> findByNameAndCon(String name) {
		// TODO Auto-generated method stub
		//String sql="select g.*,n.* from goods g,need n where g.id=n.g_id and g.name like '%"+name+"%' and g.status='审核通过' and n.status='审核通过'";
		//return findBySQL(sql);
	//}

	/*public List<Goods> findByFullNameAndCon(String name) {
		// TODO Auto-generated method stub
		String sql="select g.*,n.* from goods g,need n where g.id=n.g_id and g.name = '"+name+"' and g.status='审核通过' and n.status='审核通过'";
		return findBySQL(sql);
	}*/
  
	public List<Goods> findByGoodsName(String name) {
		// TODO Auto-generated method stub
		String  sql="select * from goods where status='已审核通过' and name like '%"+name+"%'";
		return findBySQL(sql);
	}

	public List<Goods> findByFullGoodsName(String name) {
		// TODO Auto-generated method stub
		String  sql="select * from goods where status='已审核通过' and name = '"+name+"'";
		System.out.println(sql);
		return findBySQL(sql);
	}

}
