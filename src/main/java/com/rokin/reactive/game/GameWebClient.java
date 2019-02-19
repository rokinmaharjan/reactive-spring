package com.rokin.reactive.game;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class GameWebClient {
	WebClient client = WebClient.create("http://localhost:8080/games");

	public void consumeGameServerSentEvents() {
		ParameterizedTypeReference<ServerSentEvent<Game>> type = new ParameterizedTypeReference<ServerSentEvent<Game>>() {};

		Flux<ServerSentEvent<Game>> games = this.client.get().uri("/stream").retrieve().bodyToFlux(type);

		games.subscribe(content -> System.err.println(content.data()));
	}
}
