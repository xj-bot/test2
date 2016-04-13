/*受雇者DAO，包含增删改查的基本操作以及衍生的其他对数据库的操作*/
package cn.edu.zjut.dao;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.opensymphony.xwork2.ActionContext;

import cn.edu.zjut.po.Employee;
import cn.edu.zjut.po.Merchant;
import cn.edu.zjut.po.Order;//临时测试用

public class EmployeeDAO extends BaseHibernateDAO implements IEmployeeDAO{
	private Map<String, Object> request, session;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean register(Employee employee) {//注册
		ActionContext ctx= ActionContext.getContext();
		session=(Map) ctx.getSession();
		request=(Map) ctx.get("request");
		Transaction tran = null;
		Session esession = null; 
		try {
			esession = getSession();
			tran = esession.beginTransaction();
			esession.save(employee);
			tran.commit();
			session.put("employee", employee);
			request.put("tip", "注册成功");
		} catch (RuntimeException re) {
			if(tran != null) tran.rollback();
			request.put("tip", "注册失败，该学号已被使用");
			return false;
		} finally {
			getSession().close();
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean update(Employee employee) {//受雇者信息更新
		ActionContext ctx= ActionContext.getContext();
		session=(Map) ctx.getSession();
		request=(Map) ctx.get("request");
		Transaction tran = null;
		Session esession = null;
		try {
			esession = getSession();
			tran = esession.beginTransaction();
			esession.update(employee);
			tran.commit();
			request.put("updateTip", "信息修改成功！");
			session.put("employee", employee);
		} catch (RuntimeException re) {
			return false;
		} finally {
			getSession().close();
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean login(Employee employee) {//登录
		ActionContext ctx= ActionContext.getContext();
		session=(Map) ctx.getSession();
		request=(Map) ctx.get("request");
		String employeeID = employee.getEmployeeID();
		String employeePassword = employee.getEmployeePassword();
		String hql = "from Employee as emp where employeeID='" + employeeID
				+ "' and employeePassword='" + employeePassword + "'";
		try {
			String queryString = hql;
			Query queryObject = getSession().createQuery(queryString);
			List list=queryObject.list();
			if (list.isEmpty()){
				request.put("tip", "用户名或密码错误，请核对！");
				return false;
			}
			else{
				employee=(Employee)list.get(0);
				session.put("employee", employee);
				request.put("tip", "登录成功！");
				
			}
		}catch (RuntimeException re) {
			System.out.println(re);
			return false;
		}
		getSession().close();
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean findByIDNum(Employee employee){//验证身份证号是否被使用
		ActionContext ctx= ActionContext.getContext();
		session=(Map) ctx.getSession();
		request=(Map) ctx.get("request");
		String employeeIDNum = employee.getEmployeeIDNum();
		String hql = "from Employee as emp where employeeIDNum='" + employeeIDNum+ "'";
		try {
			String queryString = hql;
			Query queryObject = getSession().createQuery(queryString);
			List list=queryObject.list();
			if (!list.isEmpty()){
				request.put("tip", "注册失败，该身份证号已被使用");
				return false;
			}
		}catch (RuntimeException re) {
			System.out.println(re);
			request.put("tip", "注册失败，服务器发生异常");
			return false;
		}
		getSession().close();
		return true;
	}

	public List findByHQL(String hql){//根据HQL语句查询
		try {
			String queryString = hql;
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			throw re;
		} finally{
			getSession().close();
		}
	}
	
	public void save(Order order) {
		try {
			getSession().save(order);
		} catch (RuntimeException re) {
			throw re;
		} finally{
			getSession().close();
		}
	}
	
}
