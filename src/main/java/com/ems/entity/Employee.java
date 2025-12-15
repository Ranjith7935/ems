package com.ems.entity;

import java.beans.Transient;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="EMPLOYEE")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="EMPLOYYEE_NAME",length=100)
	@NotNull
	private String empName;
	@Email
	private String email;
	private String gender;
	private String  dob;
	private String doj;
	private String mobile;
	private String adhar;
	private String account;
	private String department;
	private String designation;
	private String previousCompany;
	private String pfNumber;
	private Double salary;
	private String currAddress;
	@Size(max=1000,min=10)
	private String perAddress;
	private Boolean active=true;
	@CreationTimestamp
	private LocalDateTime createdDate;
	@UpdateTimestamp
	private LocalDateTime updateDate;
	private String password;
	private String role;
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Employee() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAdhar() {
		return adhar;
	}
	public void setAdhar(String adhar) {
		this.adhar = adhar;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getPreviousCompany() {
		return previousCompany;
	}
	public void setPreviousCompany(String previousCompany) {
		this.previousCompany = previousCompany;
	}
	public String getPfNumber() {
		return pfNumber;
	}
	public void setPfNumber(String pfNumber) {
		this.pfNumber = pfNumber;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getCurrAddress() {
		return currAddress;
	}
	public void setCurrAddress(String currAddress) {
		this.currAddress = currAddress;
	}
	public String getPerAddress() {
		return perAddress;
	}
	public void setPerAddress(String perAddress) {
		this.perAddress = perAddress;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", empName=" + empName + ", email=" + email + ", gender=" + gender + ", dob="
				+ dob + ", doj=" + doj + ", mobile=" + mobile + ", adhar=" + adhar + ", account=" + account
				+ ", department=" + department + ", designation=" + designation + ", previousCompany=" + previousCompany
				+ ", pfNumber=" + pfNumber + ", salary=" + salary + ", currAddress=" + currAddress + ", perAddress="
				+ perAddress + ", active=" + active + "]";
	}
	
	
}
