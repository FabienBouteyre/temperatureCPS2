package cps2.project.temperature.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cps2.project.temperature.Repository.RepAdvice;

@Controller
public class AdviceController {
	
	@Autowired
	private RepAdvice repAdvice;

	@GetMapping(path = "/advice")
    public String GetAdviceList(Model model){
        model.addAttribute("adviceList", repAdvice.findAll());
        return "advice_page";
    }

}
