package com.ems.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.CreatePost;
import com.ems.entity.Employee;
import com.ems.repository.CreatePostRepo;
import com.ems.repository.EmployeeRepo;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private CreatePostRepo createPostRepo;
	public Employee addEmployee(Employee employee) {
		Employee save=employeeRepo.save(employee);
		return save;
	}
	public List<Employee> getAllEmployee(){
		List<Employee> findAll=employeeRepo.findAll();
		return findAll;
	}
	
	public CreatePost addPost(CreatePost createPost) {
		 return createPostRepo.save(createPost);
		
	}
}
