package org.estrutura.dados.service;

import org.estrutura.dados.model.Musica;
import org.estrutura.dados.structures.ListaEncadeada;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MusicService {

    private final ListaEncadeada playlist = new ListaEncadeada();
    private Integer currentId = null;

    // Construtor: Agora chama o scanner automático
    public MusicService() {
        carregarAudiosDaPasta();
        Musica primeira = playlist.primeira();
        if (primeira != null) {
            currentId = primeira.getId();
        }
    }

    private void carregarAudiosDaPasta() {
        try {
            // 1. Ferramenta do Spring para achar arquivos
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

            // 2. Busca tudo que termina com .mp3 dentro de static/audios
            Resource[] recursos = resolver.getResources("classpath:static/audios/*.mp3");

            int idGerado = 1;

            for (Resource arquivo : recursos) {
                String nomeArquivo = arquivo.getFilename(); // Ex: "linkin_park.mp3"

                // 3. Limpeza simples do nome para virar Título (Tira o .mp3 e troca _ por espaço)
                String titulo = nomeArquivo.replace(".mp3", "").replace("_", " ");
                String artista = "Desconhecido"; // Sem biblioteca externa, não dá pra ler o artista real dentro do arquivo

                // 4. Cria o objeto e adiciona na Lista Encadeada
                Musica novaMusica = new Musica(idGerado, titulo, artista, "Local", nomeArquivo);
                playlist.adicionar(novaMusica);

                System.out.println("Arquivo encontrado: " + nomeArquivo);
                idGerado++;
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler pasta de audios: " + e.getMessage());
        }
    }

    public List<Musica> listarMusicas() {
        return playlist.listarTodas();
    }

    public void adicionarMusica(Musica musica) {
        playlist.adicionar(musica);
    }

    public void removerMusica(int id) {
        playlist.remover(id);
    }

    public Musica atual() {
        return (currentId != null) ? playlist.buscar(currentId) : playlist.primeira();
    }

    public Musica proxima() {
        Musica prox = (currentId == null) ? playlist.primeira() : playlist.proxima(currentId);
        if (prox != null) currentId = prox.getId();
        return prox;
    }

    public Musica anterior() {
        Musica ant = (currentId == null) ? playlist.primeira() : playlist.anterior(currentId);
        if (ant != null) currentId = ant.getId();
        return ant;
    }

    public Musica setAtual(int id) {
        Musica m = playlist.buscar(id);
        if (m != null) currentId = id;
        return m;
    }
}