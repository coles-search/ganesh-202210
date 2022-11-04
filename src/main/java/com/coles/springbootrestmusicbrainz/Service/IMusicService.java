package com.coles.springbootrestmusicbrainz.Service;

import com.coles.springbootrestmusicbrainz.Model.ArtistNames;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IMusicService {
    Mono<List<JsonNode>> getArtist(String name);
}
