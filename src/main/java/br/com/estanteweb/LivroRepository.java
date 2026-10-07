package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LivroRepository {

    private final JdbcTemplate jdbcTemplate;

    public LivroRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Busca todos os livros no banco
    public List<Livro> listarTodas() {

        String sql = "SELECT * FROM livro";

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);

        List<Livro> livros = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {

            Livro livro = new Livro();

            livro.setId(((Number) linha.get("id")).intValue());
            livro.setTitulo((String) linha.get("titulo"));
            livro.setAutor((String) linha.get("autor"));
            livro.setAno(((Number) linha.get("ano")).intValue());
            livro.setPreco(((Number) linha.get("preco")).doubleValue());

            livros.add(livro);
        }

        return livros;
    }

    // Conta quantos livros existem no banco
    public int contarLivros() {

        String sql = "SELECT COUNT(*) FROM livro";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // Busca livros até determinado preço
    public List<Livro> listarAtePreco(double precoMaximo) {

        String sql = "SELECT * FROM livro WHERE preco <= ?";

        List<Map<String, Object>> linhas =
                jdbcTemplate.queryForList(sql, precoMaximo);

        List<Livro> livrosAtePreco = new ArrayList<>();

        for (Map<String, Object> linha : linhas) {

            Livro livro = new Livro();

            livro.setId(((Number) linha.get("id")).intValue());
            livro.setTitulo((String) linha.get("titulo"));
            livro.setAutor((String) linha.get("autor"));
            livro.setAno(((Number) linha.get("ano")).intValue());
            livro.setPreco(((Number) linha.get("preco")).doubleValue());

            livrosAtePreco.add(livro);
        }

        return livrosAtePreco;
    }

    // Soma o preço de todos os livros
    public double valorTotal() {

        String sql = "SELECT SUM(preco) FROM livro";

        Double total = jdbcTemplate.queryForObject(sql, Double.class);

        return total != null ? total : 0.0;
    }
}