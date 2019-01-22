package cps2.project.temperature.Controller;

import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cps2.project.temperature.Entity.Advice;
import cps2.project.temperature.Repository.RepAdvice;

@RestController
@CrossOrigin
@RequestMapping(path = "/api")
public class AdviceRestController {

	@Autowired
	private RepAdvice repAdvice;

	@Transactional(readOnly=true)
	@GetMapping("/advice")
	public List<Advice> GetAdviceList(Model model) {
		return repAdvice.findAll();
	}

	@Transactional(readOnly=false)
	@PostMapping("/advice/add")
	public void AddAdvice(@RequestBody Advice advice) {
		Advice newAdvice = new Advice();
		List<Advice> adviceList = repAdvice.findAll();
		Iterator<Advice> it = adviceList.iterator();
		while (it.hasNext()) {
			if (advice.getId() == (it.next().getId())) {
			return;
			}
		}
		newAdvice.setId(advice.getId());
		newAdvice.setDate(advice.getDate());
		newAdvice.setDescription(advice.getDescription());
		newAdvice.setFloor(advice.getFloor());
		newAdvice.setLight(advice.getLight());
		newAdvice.setOutsideTemperature(advice.getOutsideTemperature());
		newAdvice.setRoom(advice.getRoom());
		newAdvice.setRoomTemperature(advice.getRoomTemperature());
		newAdvice.setType(advice.getType());
		newAdvice.setActive(advice.getActive());	
		repAdvice.save(newAdvice);
		return;
	}

	@Transactional(readOnly=true)
	@GetMapping("/advice/active")
	public List<Advice> GetActiveAdvice() {
		List<Advice> adviceList = repAdvice.findAllWhereActive();
		return adviceList;
	}

	@Transactional(readOnly=false)
	@PostMapping("/advice/active")
	public void SetInactiveAdvice(@RequestParam Long id) {
		Advice advice = repAdvice.findByAdviceId(id);
		advice.setActive(0);
		repAdvice.save(advice);
		return;
	}
}
