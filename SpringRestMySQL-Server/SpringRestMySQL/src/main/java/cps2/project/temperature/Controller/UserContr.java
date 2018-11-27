package cps2.project.temperature.Controller;



import cps2.project.temperature.Entity.User;
import cps2.project.temperature.Repository.RepUser;
import cps2.project.temperature.Service.ServiceUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String PostRegist(@ModelAttribute User user, Model model){

        if (StringUtils.isEmpty(user)){
            model.addAttribute("mess", "Field of user is empty");
            return "regist";
        }
        if (servUD.addUser(user))
            model.addAttribute("mess", String.format("User: %s %s scccuessfuly registrated", user.getName(), user.getSurn()));
        else
            model.addAttribute("mess", String.format("User: %s %s is exist", user.getName(), user.getSurn()));

        return "regist";
    }

}
