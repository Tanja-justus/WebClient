package org.example.webclient;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/characters")
@AllArgsConstructor
public class CharacterController {


    private final RickAndMortyCharacterService rickAndMortyCharacterService;

    @GetMapping
    public List<RickAndMortyCharacter> getAllCharacters() {
        return rickAndMortyCharacterService.getCharacters();
    }
    @GetMapping("/{id}")
    public RickAndMortyCharacter getCharacterById(@PathVariable int id) {
        return rickAndMortyCharacterService.getCharacterById(id);
    }
    @GetMapping("character")
    public List<RickAndMortyCharacter> getAllCharactersByStatus(@RequestParam String status) {

        return rickAndMortyCharacterService.loadAllCharactersByStatus(status).results();
    }
}