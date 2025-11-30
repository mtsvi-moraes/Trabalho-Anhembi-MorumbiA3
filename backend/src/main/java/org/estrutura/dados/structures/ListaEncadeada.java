package org.estrutura.dados.structures;

import org.estrutura.dados.model.Musica;
import java.util.ArrayList;
import java.util.List;

public class ListaEncadeada {
    private No inicio;

    // Adiciona no final da lista
    public void adicionar(Musica m) {
        No novo = new No(m);
        if (inicio == null) {
            inicio = novo;
        } else {
            No atual = inicio;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
    }

    // 2.Remove pelo ID da música
    public boolean remover(int id) {
        if (inicio == null) return false;

        // Se for a primeira música da lista
        if (inicio.dado.getId() == id) {
            inicio = inicio.proximo;
            return true;
        }

        No atual = inicio;
        while (atual.proximo != null) {
            if (atual.proximo.dado.getId() == id) {
                atual.proximo = atual.proximo.proximo; // "Pula" o nó removido
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    // 3. BUSCA (Procura pelo ID)
    public Musica buscar(int id) {
        No atual = inicio;
        while (atual != null) {
            if (atual.dado.getId() == id) {
                return atual.dado;
            }
            atual = atual.proximo;
        }
        return null;
    }

    // Transforma em Lista padrão para visualização
    public List<Musica> listarTodas() {
        List<Musica> listaVisual = new ArrayList<>();
        No atual = inicio;
        while (atual != null) {
            listaVisual.add(atual.dado);
            atual = atual.proximo;
        }
        return listaVisual;
    }
}