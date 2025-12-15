package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ems.entity.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    Employee findByIdAndPassword(int empId, String password);

    Employee findByIdAndPasswordAndRole(int empId, String password, String role);

    @Query(
    	    value = """
    	        SELECT *
    	        FROM employee e
    	        WHERE
    	          (
    	            DATE_FORMAT(STR_TO_DATE(e.dob, '%Y-%m-%d'), '%m-%d')
    	            BETWEEN DATE_FORMAT(CURDATE(), '%m-%d') AND '12-31'
    	          )
    	          OR
    	          (
    	            DATE_FORMAT(STR_TO_DATE(e.dob, '%Y-%m-%d'), '%m-%d')
    	            BETWEEN '01-01'
    	            AND DATE_FORMAT(DATE_ADD(CURDATE(), INTERVAL 60 DAY), '%m-%d')
    	          )
    	        ORDER BY DATE_FORMAT(STR_TO_DATE(e.dob, '%Y-%m-%d'), '%m-%d')
    	    """,
    	    nativeQuery = true
    	)
    	List<Employee> findUpcomingBirthdays();
    	
    @Query(
    	    value = """
    	        SELECT *
    	        FROM employee e
    	        WHERE
    	          (
    	            DATE_FORMAT(
    	                STR_TO_DATE(e.doj, '%Y-%m-%d'),
    	                '%m-%d'
    	            )
    	            BETWEEN DATE_FORMAT(CURDATE(), '%m-%d')
    	            AND '12-31'
    	          )
    	          OR
    	          (
    	            DATE_FORMAT(
    	                STR_TO_DATE(e.doj, '%Y-%m-%d'),
    	                '%m-%d'
    	            )
    	            BETWEEN '01-01'
    	            AND DATE_FORMAT(
    	                DATE_ADD(CURDATE(), INTERVAL 90 DAY),
    	                '%m-%d'
    	            )
    	          )
    	        ORDER BY DATE_FORMAT(
    	            STR_TO_DATE(e.doj, '%Y-%m-%d'),
    	            '%m-%d'
    	        )
    	    """,
    	    nativeQuery = true
    	)
    	List<Employee> findUpcomingAnniversaries();

    @Query("SELECT e.department, COUNT(e) FROM Employee e GROUP BY e.department")
    List<Object[]> countByDepartment();
    long count();
    

    
}
