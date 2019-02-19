package com.rokin.reactive.game;

import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {
	@Autowired
	private GameRepository gameRepository;
	
	
//	public Flux<Game> findAll() {
//		return Flux.fromArray("DotA2, LOL, PubG".split(","))
//				.map(name -> new Game(UUID.randomUUID().toString(), name, randomGamePrice()));
//	}
//
//	private float randomGamePrice() {
//		float[] prices = new float[] {50, 100,70, 90};
//		
//		return prices[new Random().nextInt(prices.length)];
//		
//	}

	public Mono<Game> add(Game game) {
		return gameRepository.save(game);
	}
	
	public Flux<Game> findAll() {
		return gameRepository.findAll();
	}

	public Mono<Game> findById(String id) {
		return gameRepository.findById(id);
	}
	
	public Flux<ServerSentEvent<Game>> streamGames() {
		return Flux.interval(Duration.ofSeconds(1))
				.map(sn -> ServerSentEvent.<Game> builder()
						.id(String.valueOf(sn))
						.event("game-event")
						.data(new Game(UUID.randomUUID().toString(), "DotA2", 500))
						.build());
	}

}
