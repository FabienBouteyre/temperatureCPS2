package cps2.project.temperature.Controller;



import cps2.project.temperature.Entity.Roles;
import cps2.project.temperature.Entity.User;
import cps2.project.temperature.Repository.RepUser;
import cps2.project.temperature.Service.ServiceUserDetails;
import cps2.project.temperature.Validators.Validator1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class UserContr {

    @Autowired
    private ServiceUserDetails servUD;

    @Autowired
    private RepUser repUser;

    @GetMapping("/home")
    public  String GetHome(Model model){
        model.addAttribute("mess", "Hello");
        return "home";
    }

    @GetMapping("/home2")
    public  String GetHome2(){
        return "singlepage";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/userlist")
    public String GetUserList(Model model){
        model.addAttribute("users", repUser.findAll());
        return "userlist";
    }

    @GetMapping("/regist")
    public String GetRegist(Model model){
        model.addAttribute("user", new User());
        return "regist";
    }

    @PostMapping("/regist")
    public String PostRegist(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            Map<String, String> errorsMap = Validator1.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        } else {
            if (StringUtils.isEmpty(user)) {
                model.addAttribute("mess", "Field of user is empty");
                return "regist";
            }
            if (servUD.addUser(user))
                model.addAttribute("mess", String.format("User: %s %s scccuessfuly registrated", user.getName(), user.getSurn()));
            else
                model.addAttribute("mess", String.format("User: %s %s is exist", user.getName(), user.getSurn()));
        }
        return "regist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/del")
    public String PostDelUser(@RequestParam("id") User user, Model model){
        if (!StringUtils.isEmpty(user)) {
            repUser.delete(user);
            model.addAttribute("mess", String.format("User: %s %s has deleted", user.getName(), user.getSurn()));
        }
        model.addAttribute("users", repUser.findAll());
        return "userlist";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user/change")
    public String PostChangeUser(@RequestParam(value = "id", required = false) User user, @RequestParam HashMap<String, String> map, Model model){
        if (!StringUtils.isEmpty(user)) {
            model.addAttribute("user", user);
            model.addAttribute("roles", Roles.values());
//            model.addAttribute("mess", String.format("User: %s %s has deleted", user.getName(), user.getSurn()));
        }
        if (!StringUtils.isEmpty(map.get("action")) && map.get("action").contentEquals("save")){
            if (!StringUtils.isEmpty(map.get("mail")))
                user.setEmail(map.get("mail"));
            if (!StringUtils.isEmpty(map.get("name")))
                user.setName(map.get("name"));
            if (!StringUtils.isEmpty(map.get("surn")))
                user.setSurn(map.get("surn"));
            if (!StringUtils.isEmpty(map.get("pass")))
                user.setPass(map.get("pass"));

            user.getRoles().clear();
            Set<String> roles = Arrays.stream(Roles.values()).map(Roles::name).collect(Collectors.toSet());
            for (String key: map.keySet()){
                if (roles.contains(key)){
                    user.getRoles().add(Roles.valueOf(key));
                }
            }

            repUser.save(user);
            model.addAttribute("mess", "User change is save");
        }
        return "userchange";
    }



}
