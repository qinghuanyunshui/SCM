package com.crx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.crx.model.Goods;
import com.crx.model.Purchase;
import com.crx.utils.DBService;

public class PurchaseDAO implements IBaseDAO<Purchase>{
   private GoodsDAO gdao =new GoodsDAO();
	@Override
	public void insert(Purchase p) {
		// TODO Auto-generated method stub
		String sql = "insert into purchase values(null,"+p.getGood().getId()+","+p.getPrice()+","+p.getNum()+",'"+p.getPurdate()+"','"+p.getStatus()+"','"+p.getBills()+"','"+p.getDemo()+"')";
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
	public void update(Purchase t) {
		// TODO Auto-generated method stub
	String sql="update purchase set g_id="+t.getGood().getId()+",price="+t.getPrice()+",num="+t.getNum()+",purdate='"+t.getPurdate()+"',status='"+t.getStatus()+"',bills='"+t.getBills()+"',demo='"+t.getDemo()+"' where id="+t.getId();
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
		String sql="delete from purchase where id="+id;
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
	public Purchase findById(int id) {
		// TODO Auto-generated method stub
		String sql="select * from purchase where id="+id;
		List<Purchase> purs = findBySQL(sql);
		return purs.size()>0?purs.get(0):null;
	}

	@Override
	public List<Purchase> findAll() {
		// TODO Auto-generated method stub
		String sql="select * from purchase";
		return findBySQL(sql);
	}

	@Override
	public List<Purchase> findByName(String name) {
		// TODO Auto-generated method stub
		String sql="select * from goods where name like '%"+name+"%'";
		
		return findBySQL(sql);
	}

	@Override
	public List<Purchase> findBySQL(String sql) {
		// TODO Auto-generated method stub
		List<Purchase> purs = new ArrayList<>();
		try {
			Statement stmt = DBService.getStmt();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Purchase p = new Purchase();
				p.setId(rs.getInt(1));
				p.setGood(gdao.findById(rs.getInt(2)));
				p.setPrice(rs.getDouble(3));
				p.setNum(rs.getInt(4));
				p.setPurdate(rs.getString(5));
				p.setStatus(rs.getString(6));
				p.setBills(rs.getString(7));
				p.setDemo(rs.getString(8));
				purs.add(p);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return purs;
	}

	public void deleteSome(String ids) {
		// TODO Auto-generated method stub
		String sql="delete from purchase where id in("+ids+")";
		try {
			Statement stmt = DBService.getStmt();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateStatus(Purchase p) {
		// TODO Auto-generated method stub
		String sql="update purchase set status='"+p.getStatus()+"' where id="+p.getId();
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

	
	public List<Purchase> findAllByCondition(String con) {
		String sql="select * from purchase where status in("+con+")";
		System.out.println(sql);
		return findBySQL(sql);
		
	}
	public List<Purchase> findByFullName(String name) {
		// TODO Auto-generated method stub
	    String sql="select * from purchase where name ='"+name+"'";
		return findBySQL(sql);
	}

	public List<Purchase> findByGIdsAndOper(String ids,String oper) {
		// TODO Auto-generated method stub
		String sql="select * from purchase where g_id in("+ids+") and status in("+oper+")";
		System.out.println(sql);
		return findBySQL(sql);
	}


	

}
