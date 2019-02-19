package com.rokin.reactive.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/games")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private GameWebClient gameWebClient;
	
//	@Bean
//	RouterFunction<?> routes(GameService gameService) {
//		return route(RequestPredicates.GET("/games"),
//	                request -> ok().body(gameService.findAll(), Game.class));
//	}
	
	@PostMapping
	public Mono<Game> addGame(@RequestBody Game game) {
		return gameService.add(game);
	}
	
	@GetMapping()
	public Flux<Game> getAllGames() {
		return gameService.findAll();
	}
	
	@GetMapping("/{id}")
	public Mono<Game> getGameById(@RequestParam String id) {
		return gameService.findById(id);
	}
	
	@GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<Game>> streamGames() {
		return gameService.streamGames();
	}
	
	@GetMapping(value = "/stream/consume")
	public void consumeGameStream() {
		gameWebClient.consumeGameServerSentEvents();
	}
}
