package cps2.project.temperature.Controller;

import cps2.project.temperature.Entity.Roles;
import cps2.project.temperature.Entity.User;
import cps2.project.temperature.Repository.RepUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@CrossOrigin
@RequestMapping(path = "/api/users")
public class UserRestContr {

	@Autowired
    private RepUser repUser;

    @Transactional(readOnly=true)
    @GetMapping("/userList")
    public List<User> getApiData(){
        return repUser.findAll();
    }
    
    @Transactional(readOnly=true)
    @RequestMapping("/user/login/{email}")
    public User GetLogUser(@PathVariable("email") String email){
    	User user = repUser.findByEmail(email);
    	return user;
    }
    
    @Transactional(readOnly=false)
    @PostMapping("/user/add")
    public Boolean addUser(@RequestBody User user) {
    	User userInDatabase = repUser.findByEmail(user.getEmail());
    	if (StringUtils.isEmpty(userInDatabase)) {
	    	User newUser = new User();
	    	newUser.setName(user.getName());
	    	newUser.setSurn(user.getSurn());
	    	newUser.setEmail(user.getEmail());
	    	newUser.setPass(user.getPass());
	    	newUser.setActive(true);
	    	Set<Roles> roles = new HashSet<Roles>();
	    	roles.add(Roles.USER);
	    	newUser.setRoles(roles);
	    	repUser.save(newUser);
	    	return true;
    	}
		return false;
    }
    
    @Transactional(readOnly=false)
    @GetMapping("/user/setUser/{id}")
    public void setUser(@PathVariable("id") Long id){
    	User user = repUser.findByid(id);
    	if (!StringUtils.isEmpty(user)) {
    		Set<Roles> roles = new HashSet<Roles>();
	    	roles.add(Roles.USER);
	    	user.setRoles(roles);
    		repUser.save(user);
    	}
    }
    
    @Transactional(readOnly=false)
    @GetMapping("/user/setAdmin/{id}")
    public void setAdmin(@PathVariable("id") Long id){
    	User user = repUser.findByid(id);
    	if (!StringUtils.isEmpty(user)) {
    		Set<Roles> roles = new HashSet<Roles>();
	    	roles.add(Roles.USER);
	    	roles.add(Roles.ADMIN);
	    	user.setRoles(roles);
    		repUser.save(user);
    	}
    }
    
    @Transactional(readOnly=false)
    @PostMapping("/user/edit/{id}")
    public Boolean updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    	User userEdit = repUser.findByid(id);
    	if (!StringUtils.isEmpty(userEdit)) {
    		if (user.getEmail().equals(userEdit.getEmail())) {
    			userEdit.setName(user.getName());
		    	userEdit.setSurn(user.getSurn());
		        userEdit.setEmail(user.getEmail());
		        userEdit.setPass(user.getPass());
		    	repUser.save(userEdit);
		    	return true;
    		}
    		else {
    			User userWithEmail = repUser.findByEmail(user.getEmail());
    			if (StringUtils.isEmpty(userWithEmail)) {
    				userEdit.setName(user.getName());
    		    	userEdit.setSurn(user.getSurn());
    		        userEdit.setEmail(user.getEmail());
    		        userEdit.setPass(user.getPass());
    		    	repUser.save(userEdit);
    		    	return true;
    			}
    		}
    	}
    	return false;
    }	

    @Transactional(readOnly=false)
    @PostMapping("/user/del")
    public String PostDelUser(@RequestParam("id") User user, Model model){
        if (!StringUtils.isEmpty(user)) {
            repUser.delete(user);
            model.addAttribute("mess", String.format("User: %s %s has deleted", user.getName(), user.getSurn()));
        }
        model.addAttribute("users", repUser.findAll());
        return "userlist";
    }
    
    @Transactional(readOnly=false)
    @GetMapping("/user/delete/{id}")
    public void GetDelUser(@PathVariable("id") Long id){
    	User user = repUser.findByid(id);
    	if (!StringUtils.isEmpty(user)) {
    		repUser.delete(user);
    	}
    }
    
    @Transactional(readOnly=false)
    @GetMapping("/user/desactivate/{id}")
    public void desactivateUser(@PathVariable("id") Long id){
    	User user = repUser.findByid(id);
    	if (!StringUtils.isEmpty(user)) {
    		user.setActive(false);
    		repUser.save(user);
    	}
    }
    
    @Transactional(readOnly=false)
    @GetMapping("/user/activate/{id}")
    public void activateUser(@PathVariable("id") Long id){
    	User user = repUser.findByid(id);
    	if (!StringUtils.isEmpty(user)) {
    		user.setActive(true);
    		repUser.save(user);
    	}
    }
    
    /*@GetMapping("/data/current")
    public List<User> GetUsers() {
        List<User> users = repUser.findAll();
        for (int i = 0; i < users.size(); i++){
            User user = users.get(i);
            user.setU   // .setSensorDataEntity(repSensorData.findTopBySensoridOrderByDateDesc(sensorID));
            users.set(i, user);
        }
       return users;
    }*/

}
