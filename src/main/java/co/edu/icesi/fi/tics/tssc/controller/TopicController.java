package co.edu.icesi.fi.tics.tssc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.TopicService;
import co.icesi.fi.tics.tssc.validations.ValidationTopic;

@Controller
public class TopicController {

	private TopicService topicService;

	@Autowired
	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}

	@GetMapping("/topic/")
	public String indexUser(Model model) {
		model.addAttribute("topics", topicService.findAll());
		return "topic/index";
	}

	@GetMapping("/topic/add")
	public String addTopic(Model model) {
		model.addAttribute("tsscTopic", new TsscTopic());
		return "topic/add-topic";
	}

	@PostMapping("/topic/add")
	public String saveTopic(@Validated(ValidationTopic.class) TsscTopic tsscTopic, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, Model model) {

		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				
				model.addAttribute("name", tsscTopic.getName());
				model.addAttribute("description", tsscTopic.getDescription());
				model.addAttribute("defaultGroups", tsscTopic.getDefaultGroups());
				model.addAttribute("defaultSprints", tsscTopic.getDefaultSprints());
				model.addAttribute("groupPrefix", tsscTopic.getGroupPrefix());
				
				return "topic/add-topic";
			} else {

				try {
					topicService.saveTopic(tsscTopic);
				} catch (TopicException | CapacityException | SpringException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return "redirect:/topic/";

			}

		}else {
			
			model.addAttribute("topics", topicService.findAll());
			return "topic/index";
		}

		
	}

}
