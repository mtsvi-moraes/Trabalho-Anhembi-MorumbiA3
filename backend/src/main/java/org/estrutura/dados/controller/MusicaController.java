package org.estrutura.dados.controller;

import org.estrutura.dados.model.Musica;
import org.estrutura.dados.service.MusicService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MusicaController {

    private final MusicService musicService;


    public MusicaController(MusicService musicService) {
        this.musicService = musicService;
    }

    // 1. GET: Retorna a lista de músicas (JSON)
    // URL: http://localhost:8080/api/playlist
    @GetMapping("/playlist")
    public List<Musica> getPlaylist() {
        return musicService.listarMusicas();
    }

    // 2. POST: Adiciona uma nova música
    // O Front manda o JSON e o Java converte em objeto Musica
    @PostMapping("/playlist")
    public void addPlaylist(@RequestBody Musica musica) {
        musicService.adicionarMusica(musica);
        System.out.println("Nova música recebida: " + musica.getTitulo());
    }

    // 3. DELETE: Remove uma música pelo ID
    // URL: http://localhost:8080/api/playlist/1
    @DeleteMapping("/playlist/{id}")
    public void removePlaylist(@PathVariable int id) {
        musicService.removerMusica(id);
        System.out.println("Música removida ID: " + id);
    }
}