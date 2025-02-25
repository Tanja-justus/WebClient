package org.example.webclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class CharacterIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MockRestServiceServer mockServer;

    @Test
    void getAllCharacters() throws Exception {

        //GIVEN
        mockServer.expect(requestTo("https://rickandmortyapi.com/api/character"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "info": {
                                "count": 826,
                                "pages": 42,
                                "next": "https://rickandmortyapi.com/api/character?page=2",
                                "prev": null
                            },
                            "results": [
                                {
                                    "id": 1,
                                    "name": "Florian",
                                    "status": "Alive",
                                    "species": "Human",
                                    "type": "",
                                    "gender": "Male",
                                    "origin": {
                                        "name": "Earth (C-137)",
                                        "url": "https://rickandmortyapi.com/api/location/1"
                                    },
                                    "location": {
                                        "name": "Citadel of Ricks",
                                        "url": "https://rickandmortyapi.com/api/location/3"
                                    }
                                },
                                {
                                    "id": 2,
                                    "name": "Morty Smith",
                                    "status": "Alive",
                                    "species": "Human",
                                    "type": "",
                                    "gender": "Male",
                                    "origin": {
                                        "name": "unknown",
                                        "url": ""
                                    },
                                    "location": {
                                        "name": "Citadel of Ricks",
                                        "url": "https://rickandmortyapi.com/api/location/3"
                                    }
                                },
                                {
                                    "id": 3,
                                    "name": "Summer Smith",
                                    "status": "Alive",
                                    "species": "Human",
                                    "type": "",
                                    "gender": "Female",
                                    "origin": {
                                        "name": "Earth (Replacement Dimension)",
                                        "url": "https://rickandmortyapi.com/api/location/20"
                                    },
                                    "location": {
                                        "name": "Earth (Replacement Dimension)",
                                        "url": "https://rickandmortyapi.com/api/location/20"
                                    }
                                }
                            ]
                        }
                        """, MediaType.APPLICATION_JSON));

        //WHEN

        mockMvc.perform(get("/api/characters"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                          {
                          "id":1,
                          "name": "Florian",
                          "status":"Alive",
                          "species":"Human"
                       
                            },
                          {
                          "id":2,
                          "name":"Morty Smith",
                          "status":"Alive",
                          "species":"Human"
  
                         },
                         {
                         "id":3,
                         "name":"Summer Smith",
                         "status":"Alive",
                         "species":"Human"
                     
                          }
                         ]
                        """));
    }

    @Test
    public void testGetCharacterById() throws Exception {
        //GIVEN
        // int characterId = 1;

        // Hier mocken wir die Antwort für den Charakter mit ID 1
        mockServer.expect(requestTo("https://rickandmortyapi.com/api/character/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "id": 1,
                            "name": "Florian",
                            "status": "Alive",
                            "species": "Human"
                        }
                        """, MediaType.APPLICATION_JSON));

        //WHEN
        mockMvc.perform(get("/api/characters/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "name": "Florian",
                            "status": "Alive",
                            "species": "Human"
                        }
                        """));
    }

    @Test
    public void testGetCharacterByStatus() throws Exception {
        // GIVEN
        String status = "Alive";

        // Hier mocken wir die Antwort für den Status "Alive"
        mockServer.expect(requestTo("https://rickandmortyapi.com/api/character?status=" + status))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                            "info": {
                                "count": 826,
                                "pages": 42,
                                "next": "https://rickandmortyapi.com/api/character?page=2",
                                "prev": null
                            },
                            "results": [
                                {
                                    "id": 1,
                                    "name": "Florian",
                                    "status": "Alive",
                                    "species": "Human"
                                },
                                {
                                    "id": 2,
                                    "name": "Morty Smith",
                                    "status": "Alive",
                                    "species": "Human"
                                }
                            ]
                        }
                        """, MediaType.APPLICATION_JSON));

        // WHEN: Die MockMvc-Anfrage mit dem richtigen Endpunkt
        mockMvc.perform(get("/api/characters?status=" + status))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                    
                        [
                        {
                            "id": 1,
                            "name": "Florian",
                            "status": "Alive",
                            "species": "Human"
                        },
                        {
                            "id": 2,
                            "name": "Morty Smith",
                            "status": "Alive",
                            "species": "Human"
                        }
                    ]
                    """));
    }
}
