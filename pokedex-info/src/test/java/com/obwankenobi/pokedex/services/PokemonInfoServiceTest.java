package com.obwankenobi.pokedex.services;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.obwankenobi.pokedex.feignclients.PokemonClient;
import com.obwankenobi.pokedex.feignclients.SpeciesClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class PokemonInfoServiceTest {

    @InjectMocks
    private static PokemonInfoService pokemonInfoService;

    @Mock
    private PokemonClient pokemonClient;
    @Mock
    private SpeciesClient speciesClient;

    @BeforeAll
    public static void setup(){
        pokemonInfoService = new PokemonInfoService();
    }

    @Test
    void givenNullName_whenPokemonClientFails_throwsFeignException() throws Exception {
        String name = null;
        when(pokemonClient.getPokemonData(name)).thenThrow(FeignException.class);
        assertThrows(FeignException.class, ()->pokemonInfoService.getPokemonInfoByName(name));
    }

    @Test
    void givenNullName_whenSpeciesClientFails_throwsFeignException() throws Exception {
        String name = null;
        when(pokemonClient.getPokemonData(name)).thenReturn("{'id':9, 'name':'onix'}");
        when(speciesClient.getSpeciesData(name)).thenThrow(FeignException.class);
        assertThrows(FeignException.class, ()->pokemonInfoService.getPokemonInfoByName(name));
    }
}
