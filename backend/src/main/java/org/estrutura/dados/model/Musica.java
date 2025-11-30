package org.estrutura.dados.model;

public class Musica {
    private int id;
    private String titulo;
    private String artista;
    private String album;
    private String nomeArquivo;

    public Musica(int id, String titulo, String artista, String album, String nomeArquivo) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.nomeArquivo = nomeArquivo;
    }

    public Musica() {}

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public String getAlbum() {
        return album;
    }
    public String getNomeArquivo() {
        return nomeArquivo;
    }
}


