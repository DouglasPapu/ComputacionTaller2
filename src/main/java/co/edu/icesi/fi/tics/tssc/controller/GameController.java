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
import co.icesi.fi.tics.tssc.validations.ValidationGame;

@Controller
public class GameController {

	private GameService gameService;

	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping("/game/")
	public String indexUser(Model model) {
		model.addAttribute("games", gameService.findAll());
		return "game/index";
	}

	@GetMapping("/game/add")
	public String addTopic(Model model) {
		model.addAttribute("tsscGame", new TsscGame());
		return "game/add-game";
	}

	@PostMapping("/game/add")
	public String saveTopic(@Validated(ValidationGame.class) TsscGame tsscGame, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, Model model) {
		
		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				return "game/add-game";
			} else {

				try {
					gameService.saveGame(tsscGame);
				} catch (TopicException | CapacityException | SpringException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return "redirect:/game/";
			}
		}

		return "game/index";
	}
}
