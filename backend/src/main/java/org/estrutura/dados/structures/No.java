package org.estrutura.dados.structures;

import org.estrutura.dados.model.Musica;

public class No {
    public Musica dado;
    public No proximo;

    public No(Musica m) {
        this.dado = m;
        this.proximo = null;
    }
}