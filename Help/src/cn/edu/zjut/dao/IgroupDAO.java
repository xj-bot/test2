package cn.edu.zjut.dao;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.opensymphony.xwork2.ActionContext;

import cn.edu.zjut.po.Igroup;

public class IgroupDAO extends BaseHibernateDAO implements IIgroupDAO {
	private Map<String,Object> session,request;
	public boolean create(Igroup igroup) {
		ActionContext ctx= ActionContext.getContext();
		session=(Map) ctx.getSession();
		request=(Map) ctx.get("request");
		Transaction tran = null;
		Session esession = null; 
		try {
			esession = getSession();
			tran = esession.beginTransaction();
			esession.save(igroup);
			tran.commit();
			System.out.println(igroup.getGroupID());
			session.put("igroup", igroup);
			
			request.put("tip", "创建成功");
			System.out.println("创建成功");
		} catch (RuntimeException re) {
			if(tran != null) tran.rollback();
			request.put("tip", "创建失败，该队伍名已被使用");
			System.out.println("创建失败，该队伍名已被使用");
			re.printStackTrace();
			return false;
		} catch(Exception e){
			System.out.println("失败");
			e.printStackTrace();
		}finally {
			getSession().close();
		}
		return true;
		// TODO Auto-generated method stub
		
	}
	

}
