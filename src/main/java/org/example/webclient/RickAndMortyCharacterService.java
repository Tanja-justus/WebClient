package org.example.webclient;



import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class RickAndMortyCharacterService {

    private RestClient restClient ;

public RickAndMortyCharacterService(RestClient.Builder restClientBuilder){
    this.restClient=restClientBuilder
     .baseUrl("https://rickandmortyapi.com/api")
     .build();



}
    public List<RickAndMortyCharacter> getCharacters() {

        RickAndMortyResponse body = restClient.get()
                .uri("/character")
                .retrieve()
                .body(RickAndMortyResponse.class);

        return body.results();
    }

    public RickAndMortyCharacter getCharacterById(int id) {
       return restClient.get()
                .uri("/character/{id}", id)
                .retrieve()
                .body(RickAndMortyCharacter.class);
    }

    public RickAndMortyResponse loadAllCharactersByStatus(String status) {
        return restClient.get()
                .uri("/api/character?status=" + status)
                .retrieve()
                .body(RickAndMortyResponse.class);
    }
}
