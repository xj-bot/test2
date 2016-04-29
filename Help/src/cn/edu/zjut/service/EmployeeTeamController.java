package cn.edu.zjut.service;


import java.util.List;

import cn.edu.zjut.dao.EmployeeDAO;
import cn.edu.zjut.po.Employee;

public class EmployeeTeamController implements IEmployeeTeamController {
	private EmployeeDAO employeeDAO;
	
	public List findTeam() {
		// TODO Auto-generated method stub
		System.out.println("execute --findTeams()-- method.");
		return employeeDAO.findTeams();
	}
	
	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}

	@Override
	public Boolean updateIgroupID() {
		// TODO Auto-generated method stub
		System.out.println("开始更新");
		return employeeDAO.updateIgroupID();
		
	}

	public boolean exitTeam() {
		// TODO Auto-generated method stub
		return employeeDAO.exitTeam();
	}

}
