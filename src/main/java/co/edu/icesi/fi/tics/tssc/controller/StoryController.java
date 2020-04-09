package co.edu.icesi.fi.tics.tssc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.services.GameService;
import co.edu.icesi.fi.tics.tssc.services.StoryService;
import co.icesi.fi.tics.tssc.validations.ValidationStory;

@Controller
public class StoryController {

	private StoryService storyService;

	private GameService gameService;

	@Autowired
	public StoryController(StoryService storyService, GameService gameService) {
		this.storyService = storyService;
		this.gameService = gameService;
	}

	@GetMapping("/story/")
	public String indexStory(Model model) {
		model.addAttribute("stories", storyService.findAll());
		return "story/index";
	}

	@GetMapping("/story/add")
	public String addStory(Model model) {
		model.addAttribute("tsscStory", new TsscStory());
		model.addAttribute("games", gameService.findAll());
		return "story/add-story";
	}

	@PostMapping("/story/add")
	public String saveStory(@Validated(ValidationStory.class) TsscStory tsscStory, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, Model model) {

		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {

				model.addAttribute("description", tsscStory.getDescription());
				model.addAttribute("businessValue", tsscStory.getBusinessValue());
				model.addAttribute("initialSprint", tsscStory.getInitialSprint());
				model.addAttribute("priority", tsscStory.getPriority());
				model.addAttribute("games", gameService.findAll());

				return "story/add-story";
			} else {

				// Guarda una Historia con el juego obligatorio.
				try {
					storyService.saveStory(tsscStory, tsscStory.getTsscGame().getId());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return "redirect:/story/";
			}
		}else {
			
			model.addAttribute("stories", storyService.findAll());
			return "story/index";
		}

		
	}

}
