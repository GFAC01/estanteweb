package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private List<Livro> livros = List.of(
        new Livro("God Of War Origins", "Santa Monica Studio", 2026, 19.90),
        new Livro("Cyberpunk 2077", "CD Projekt Red", 2020, 29.90),
        new Livro("<strong>Dark Souls</strong>", "FromSoftware", 2011, 39.90),
        new Livro("The Last Of Us", "Naughty Dog", 2013, 19.90),
        new Livro("Halo", "Bungie", 2001, 29.90),
        new Livro("The Witcher 3: Wild Hunt", "CD Projekt Red", 2015, 39.90)
    );

    public List<Livro> listarTodas() {
        return livros;
    };

    public int contarLivros() {
        return livros.size();
    };

    public List<Livro> listarAtePreco(double precoMaximo) {

        List<Livro> livrosAtePreco = new ArrayList<>();
        for (Livro l : livros) {
            if (l.getPreco() <= precoMaximo) {
                livrosAtePreco.add(l);
            }
        }
        return livrosAtePreco;
    };

}