package com.rokin.reactive.game;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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
	
	public Flux<Game> streamGames() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(1));
		
		Flux<Game> games = Flux.fromStream(Stream.generate(() -> new Game(UUID.randomUUID().toString(), "asf", 500)));
		
		return Flux.zip(interval, games).map(Tuple2::getT2);
	}

}
