package com.coles.springbootrestmusicbrainz.Controller;

import com.coles.springbootrestmusicbrainz.Service.MusicService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @GetMapping()
    public Mono<List<JsonNode>> getArtist(@RequestParam("artistname") String artistName) {
        return musicService.getArtist(artistName);
    }

}


