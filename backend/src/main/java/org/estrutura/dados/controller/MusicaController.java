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

    // 2. GET: Retorna a música atual
    // URL: http://localhost:8080/api/current
    @GetMapping("/current")
    public Musica getCurrent() {
        return musicService.atual();
    }

    // 3. POST: Adiciona uma nova música
    // O Front manda o JSON e o Java converte em objeto Musica
    @PostMapping("/playlist")
    public void addPlaylist(@RequestBody Musica musica) {
        musicService.adicionarMusica(musica);
        System.out.println("Nova música recebida: " + musica.getTitulo());
    }

    // 4. POST: Próxima música
    // URL: http://localhost:8080/api/next
    @PostMapping("/next")
    public Musica next() {
        return musicService.proxima();
    }

    // 5. POST: Música anterior
    // URL: http://localhost:8080/api/previous
    @PostMapping("/previous")
    public Musica previous() {
        return musicService.anterior();
    }

    // 6. POST: Reproduz uma música pelo ID
    // URL: http://localhost:8080/api/play/{id}
    @PostMapping("/play/{id}")
    public Musica playById(@PathVariable int id) {
        return musicService.setAtual(id);
    }

    // 7. DELETE: Remove uma música pelo ID
    // URL: http://localhost:8080/api/playlist/1
    @DeleteMapping("/playlist/{id}")
    public void removePlaylist(@PathVariable int id) {
        musicService.removerMusica(id);
        System.out.println("Música removida ID: " + id);
    }
}