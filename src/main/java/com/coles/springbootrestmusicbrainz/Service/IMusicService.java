package com.coles.springbootrestmusicbrainz.Service;

import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMusicService {
    Mono<List<JsonNode>> getArtist(String name);
}
