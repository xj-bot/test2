package cn.edu.zjut.service;

import cn.edu.zjut.dao.IgroupDAO;
import cn.edu.zjut.po.Igroup;

public class EmployeeCreateTeamController implements IEmployeeCreateTeamController {
	private IgroupDAO igroupDAO;
	
	public boolean create(Igroup igroup) {
		// TODO Auto-generated method stub
		if(igroupDAO.create(igroup)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public IgroupDAO getIgroupDAO() {
		return igroupDAO;
	}
	public void setIgroupDAO(IgroupDAO igroupDAO) {
		this.igroupDAO = igroupDAO;
	}

}
