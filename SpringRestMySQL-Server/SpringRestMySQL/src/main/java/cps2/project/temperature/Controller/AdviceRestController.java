package cps2.project.temperature.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cps2.project.temperature.Entity.Advice;
import cps2.project.temperature.Repository.RepAdvice;

@Controller
@RestController
@RequestMapping(path="/api")
public class AdviceRestController {
	
	@Autowired
	private RepAdvice repAdvice;

	@GetMapping(path = "/advice")
    public List<Advice> GetAdviceList(Model model){
        return repAdvice.findAll();
    }
}
