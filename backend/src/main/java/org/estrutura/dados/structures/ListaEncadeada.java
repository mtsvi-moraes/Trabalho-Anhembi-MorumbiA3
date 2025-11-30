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

    // Retorna a primeira música (ou null)
    public Musica primeira() {
        return inicio != null ? inicio.dado : null;
    }

    private No ultimoNo() {
        No atual = inicio;
        if (atual == null) return null;
        while (atual.proximo != null) {
            atual = atual.proximo;
        }
        return atual;
    }

    // Próxima música em relação ao idAtual (com wrap-around)
    public Musica proxima(int idAtual) {
        if (inicio == null) return null;
        No atual = inicio;
        while (atual != null) {
            if (atual.dado.getId() == idAtual) {
                return (atual.proximo != null) ? atual.proximo.dado : inicio.dado;
            }
            atual = atual.proximo;
        }
        // Se não achar, cai na primeira
        return inicio.dado;
    }

    // Anterior em relação ao idAtual (com wrap-around)
    public Musica anterior(int idAtual) {
        if (inicio == null) return null;
        if (inicio.dado.getId() == idAtual) {
            No ult = ultimoNo();
            return (ult != null) ? ult.dado : null;
        }
        No prev = inicio;
        No cur = inicio.proximo;
        while (cur != null) {
            if (cur.dado.getId() == idAtual) {
                return prev.dado;
            }
            prev = cur;
            cur = cur.proximo;
        }
        No ult = ultimoNo();
        return (ult != null) ? ult.dado : null;
    }
}