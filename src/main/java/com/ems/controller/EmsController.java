package com.ems.controller;




import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ems.entity.Compose;
import com.ems.entity.CreatePost;
import com.ems.entity.Employee;
import com.ems.repository.ComposeRepo;
import com.ems.repository.CreatePostRepo;
import com.ems.repository.EmployeeRepo;
import com.ems.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmsController {

  
	@Autowired
	private EmployeeService service;
	@Autowired
	private CreatePostRepo createPostRepo;
	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private ComposeRepo composeRepo;

    
    @GetMapping({"/login","/"})
    public String login() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @PostMapping("/home")      
    public String home(@RequestParam("employeeId") String username,
                       @RequestParam("password") String password,
                       Model model,
                       HttpSession session ) {
    
    	try {
			String empId=username.substring(3);
			Employee employee=employeeRepo.findByIdAndPassword(Integer.parseInt(empId),password);
			if(employee!=null) {
				model.addAttribute("error",false);
				session.setAttribute("userId", Integer.parseInt(empId));
				session.setAttribute("name", employee.getEmpName());
				session.setAttribute("designation", employee.getDesignation());
				if(employee.getRole().equals("USER")) {
					return "redirect:/user-dashboard";
				}
				else if(employee.getRole().equals("ADMIN")) {
					
					return "redirect:/dash-board";
				}else {
					return "redirect:/login";
				}
			}
			else {
				return "login";
			}
		} catch (NumberFormatException e) {
			
			System.err.println(e.getMessage());
			return "redirect:login";
		}
        
      
    }

    @GetMapping("/dash-board")
    public String dashboard(Model model) {

        //  Fetch recent 10 of all users
        List<Compose> statusList =
            composeRepo.findTop10ByOrderByCreatedDateDesc();

        // Set designation safely
//        statusList.forEach(k -> {
//            employeeRepo.findById(k.getParentUKid())
//                .ifPresent(emp -> k.setPosition(emp.getDesignation()));
//        });
        int reminder=employeeRepo.findUpcomingBirthdays().size()+ employeeRepo.findUpcomingAnniversaries().size();
        model.addAttribute("allCount",composeRepo.count());
        model.addAttribute("pendingCount", composeRepo.countByStatus("PENDING"));
        model.addAttribute("approvedCount", composeRepo.countByStatus("APPROVED"));
        model.addAttribute("cancelledCount", composeRepo.countByStatus("CANCELLED"));
        model.addAttribute("deniedCount", composeRepo.countByStatus("REJECTED"));
        model.addAttribute("reminderCount", reminder);
        model.addAttribute("statusList", statusList);
        model.addAttribute("upcomingBirthdays", employeeRepo.findUpcomingBirthdays());
        model.addAttribute("upcomingAnniversaries", employeeRepo.findUpcomingAnniversaries());
        List<Object[]> deptCounts = employeeRepo.countByDepartment();
        List<Map<String, Object>> departmentCards = new ArrayList<>();

        for (Object[] row : deptCounts) {
            departmentCards.add(
                Map.of(
                    "title", row[0],
                    "count", row[1],
                    "color", "light"
                )
            );
        }

        model.addAttribute("departmentCards", departmentCards);
        model.addAttribute("totalcnt", employeeRepo.count());
        
        return "dash-board";
    }
    
    @GetMapping("/add-employee")
    public String addEmployee() {
		return "add-employee";
	}
    
    @GetMapping("/all-employee")
    public String allEmployee(Model model) {
    	//get all record
    	List<Employee> allEmployee=service.getAllEmployee();
    	model.addAttribute("allEmployee",allEmployee);
		return "all-employee";
	}
    
    @GetMapping("/create-post")
    public String createPost(Model model) {
    	List<CreatePost> findAll=createPostRepo.findAll();
    	model.addAttribute("post",findAll);
        return "create-post";
    }
    
    @GetMapping("/status")
    public String status(Model model) {
        List<Compose> statusList = composeRepo.findAll();
        statusList.stream()
        .forEach(k->{
        	int id=k.getParentUKid();
        	String designation=employeeRepo.findById(id).get().getDesignation();
        	k.setPosition(designation);
        });
        model.addAttribute("statusList", statusList);
        return "status";
    }

    @GetMapping("/profile")
    public String Profile(HttpSession session,Model model) {
    	Object attribute=session.getAttribute("userId");
    	int userId=Integer.parseInt(attribute.toString());
    	Employee employee=employeeRepo.findById(userId).get();
    	model.addAttribute("employee",employee);
    	System.out.println("Object:- "+attribute);
        return "profile";
    }
    
    @GetMapping("/settings")
    public String settings() {
        return "settings";
    }
    
    @PostMapping("/save-employee")
    public String saveEmployee(@ModelAttribute Employee employee) {
    	employee.setPassword(employee.getDob());
    	service.addEmployee(employee);
    	return "redirect:/all-employee";
    }
    
    @PostMapping("/save-post")
    public String savePost(@ModelAttribute CreatePost createPost) {
    	createPost.setAddDate(new Date().toString());
    	service.addPost(createPost);
    	return "redirect:/create-post";
    }
    
    @PostMapping("/update-password")
    public String updatePassword(@RequestParam("password") String password,
    		@RequestParam("newpassword") String password1,
    		@RequestParam("repassword") String password2,
    		HttpSession session,
    		Model model					) {
    	Object attribute=session.getAttribute("userId");
    	int userId=Integer.parseInt(attribute.toString());
    	Employee employee=employeeRepo.findByIdAndPassword(userId, password);
    	if(employee!=null && password1.equals(password2)) {
    		employee.setPassword(password2);
    		employeeRepo.save(employee);
    		model.addAttribute("error",false);
    		
    	}else {
    		model.addAttribute("error",true);
    		return "settings";
    	}
    	
    	return "redirect:/login";
    }
    
    @GetMapping("/edit-record")
    public String editRecord(@RequestParam("id") int id ,Model model) {
    	
    	System.out.println(id);
    	Employee employee=employeeRepo.findById(id).get();
    	model.addAttribute("employee",employee);
    	return "edit-record";
    }
    
    @PostMapping("/edit-employee")
    public String updateRecord(@ModelAttribute Employee employee) {
    	int id=employee.getId();
    	Employee getEmp=employeeRepo.findById(id).get();
    	if(getEmp!=null) {
    		employee.setPassword(employee.getDob());
    		employeeRepo.save(employee);
    	}
		return "redirect:/all-employee";
	}
    
    @GetMapping("/deleteRecord-byId")
    public String deleteRecordById(@RequestParam("id") int id) {
    	employeeRepo.deleteById(id);
		return "redirect:/all-employee";
	}
	
    @GetMapping("/user-dashboard")
    public String userDashboard(Model model, HttpSession session) {

    	Object uid = session.getAttribute("userId");
        if (uid == null) {
            return "redirect:/login";
        }
        int userId = Integer.parseInt(uid.toString());

        // Fetch only recent 5 records
        List<Compose> statusList =
            composeRepo.findTop10ByParentUKidOrderByCreatedDateDesc(userId);
     
        List<Object[]> deptCounts = employeeRepo.countByDepartment();
        List<Map<String, Object>> departmentCards = new ArrayList<>();

        for (Object[] row : deptCounts) {
            departmentCards.add(
                Map.of(
                    "title", row[0],
                    "count", row[1],
                    "color", "light"
                )
            );
        }
        int reminder=employeeRepo.findUpcomingBirthdays().size()+ employeeRepo.findUpcomingAnniversaries().size();

        model.addAttribute("departmentCards", departmentCards);
        model.addAttribute("totalcnt", employeeRepo.count());
        model.addAttribute("statusList", statusList);
        model.addAttribute("upcomingBirthdays", employeeRepo.findUpcomingBirthdays());
        model.addAttribute("upcomingAnniversaries", employeeRepo.findUpcomingAnniversaries());
        model.addAttribute("reminder", reminder);
        model.addAttribute("allCount", composeRepo.countByParentUKid(userId));
        model.addAttribute("pendingCount", composeRepo.countByParentUKidAndStatus(userId, "PENDING"));
        model.addAttribute("approvedCount", composeRepo.countByParentUKidAndStatus(userId, "APPROVED"));
        model.addAttribute("cancelledCount", composeRepo.countByParentUKidAndStatus(userId, "CANCELLED"));
        model.addAttribute("deniedCount", composeRepo.countByParentUKidAndStatus(userId, "REJECTED"));
        model.addAttribute("reminder", reminder);

        return "user-dashboard";

    }


    
    
    @GetMapping("/user-profile")
    public String userProfile(HttpSession session,Model model) {
    	Object attribute=session.getAttribute("userId");
    	int userId=Integer.parseInt(attribute.toString());
    	Employee employee=employeeRepo.findById(userId).get();
    	model.addAttribute("employee",employee);
    	System.out.println("Object:- "+attribute);
        return "user-profile";
    }
    
    @GetMapping("/user-setting")
    public String userSetting() {
        return "user-setting";
    }
    
    @GetMapping("/user-compose")
    public String userCompose() {
        return "user-compose";
    }
    
    @PostMapping("/compose")
    public String addCompose(@RequestParam("subject") String subject,
    						@RequestParam("text") String text,
    						HttpSession session) {
    	try {
			Object attribute=session.getAttribute("userId");
			int userId=Integer.parseInt(attribute.toString());
			Employee employee=employeeRepo.findById(userId).get();
			Compose com=new Compose();
			com.setEmpName(employee.getEmpName());
			com.setSubject(subject);
			com.setText(text);
			com.setParentUKid(userId);
			com.setAddedDate(new Date().toString());
			com.setStatus("PENDING");
			Compose save=composeRepo.save(com);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return "redirect:/user-compose";
    }
    
    @PostMapping("/approve-byId")
    public String approve(@RequestParam("id") int id,
                          @RequestParam("type") String type) {

        composeRepo.findById(id).ifPresent(compose -> {
            compose.setStatus(type);
            composeRepo.save(compose);
        });

        return "redirect:/status";
    }

    
}
