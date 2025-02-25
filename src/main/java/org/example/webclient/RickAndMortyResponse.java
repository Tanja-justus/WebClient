package org.example.webclient;



import java.util.List;

public record RickAndMortyResponse(
        List<RickAndMortyCharacter> results
) {
}