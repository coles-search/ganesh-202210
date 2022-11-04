package com.coles.springbootrestmusicbrainz.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MusicService implements IMusicService {

    @Autowired
    WebClient webClient;

    public Mono<List<JsonNode>> getArtist(String name) {

        Mono<JsonNode> artistResponse = getArtistResponse(name);

        Mono<Integer> artistCount = artistResponse
                                    .map(jsonNode ->
                                    Integer.parseInt((jsonNode.path("count"))
                                    .toString()
                                    .replaceAll("\\[", "")
                                    .replaceAll("\\]",""))
                                    );

        return artistCount.map(this::isSingleArtist)
                .map((singleArtist) -> {
                    if (singleArtist)
                        return artistResponse
                                .map(jsonNode -> jsonNode.path("artists").get(0).get("id")
                                                .toString())
                                .map((id) -> getReleasesResponse(id.substring(1, id.length() - 1)))
                                .flatMap(s -> s)
                                .map(jsonNode -> jsonNode.findValues("releases"));
                    else
                        return artistResponse.map(jsonNode -> jsonNode.findValues("artists"));
                })
                .flatMap(s -> s);

    }

    public Mono<JsonNode> getArtistResponse(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/artist")
                        .queryParam("query", "artist:" + name)
                        .queryParam("fmt", "json")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    public Mono<JsonNode> getReleasesResponse(String id) {
        System.out.println(id);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/release")
                        .queryParam("artist", id)
                        .queryParam("fmt", "json")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    public boolean isSingleArtist(Integer count) {
        return count == 1;
    }

    public String getArtistId(JsonNode artist) {
        return artist.path("id").toString();
    }

}
