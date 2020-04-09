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
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.GameService;
import co.edu.icesi.fi.tics.tssc.services.TopicService;
import co.icesi.fi.tics.tssc.validations.ValidationGame;

@Controller
public class GameController {

	private GameService gameService;
	
	private TopicService topicService;

	@Autowired
	public GameController(GameService gameService, TopicService topicService) {
		this.gameService = gameService;
		this.topicService = topicService;
	}

	@GetMapping("/game/")
	public String indexGame(Model model) {
		model.addAttribute("games", gameService.findAll());
		return "game/index";
	}

	@GetMapping("/game/add")
	public String addGame(Model model) {
		model.addAttribute("tsscGame", new TsscGame());
		model.addAttribute("topics", topicService.findAll());
		return "game/add-game";
	}

	@PostMapping("/game/add")
	public String saveGame(@Validated(ValidationGame.class) TsscGame tsscGame, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, Model model) {
		
		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				
				model.addAttribute("name", tsscGame.getName());
				model.addAttribute("adminPassword", tsscGame.getAdminPassword());
				model.addAttribute("scheduledDate", tsscGame.getScheduledDate());
				model.addAttribute("scheduledTime", tsscGame.getScheduledTime());
				model.addAttribute("nGroups", tsscGame.getNGroups());
				model.addAttribute("nSprints", tsscGame.getNSprints());
				model.addAttribute("userPassword", tsscGame.getUserPassword());
				model.addAttribute("guestPassword", tsscGame.getGuestPassword());
				model.addAttribute("topics", topicService.findAll());
				
				return "game/add-game";
			} else {

				try {
					
					if(tsscGame.getTsscTopic() == null) {
						
						gameService.saveGame(tsscGame);
						
					}else {
						
						gameService.saveGameWithTopic(tsscGame, tsscGame.getTsscTopic().getId());
					}
					
					
				} catch (TopicException | CapacityException | SpringException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return "redirect:/game/";
			}
		}else {
			
			model.addAttribute("games", gameService.findAll());
			return "game/index";
		}

		
	}
}
