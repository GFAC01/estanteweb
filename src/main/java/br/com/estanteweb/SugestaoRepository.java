package br.com.estanteweb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SugestaoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void salvar(Sugestao sugestao) {
        String sql = "INSERT INTO sugestao (nome_cliente, livro_sugerido) VALUES (?, ?)";
        jdbcTemplate.update(sql, sugestao.getNomeCliente(), sugestao.getLivroSugerido());
    }

    public List<Sugestao> listarTodas() {
        String sql = "SELECT * FROM sugestao";
        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql);
        List<Sugestao> sugestoes = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            sugestoes.add(Sugestao.deMap(linha));
        }
        return sugestoes;
    }

    public List<Sugestao> buscar(String termoCliente, String termoLivro) {
        StringBuilder sql = new StringBuilder("SELECT * FROM sugestao WHERE 1=1");
        List<Object> parametros = new ArrayList<>();

        if (termoCliente != null && !termoCliente.isBlank()) {
            sql.append(" AND nome_cliente LIKE ? ESCAPE '\\'");
            parametros.add("%" + escaparLike(termoCliente.trim()) + "%");
        }
        if (termoLivro != null && !termoLivro.isBlank()) {
            sql.append(" AND livro_sugerido LIKE ? ESCAPE '\\'");
            parametros.add("%" + escaparLike(termoLivro.trim()) + "%");
        }

        List<Map<String, Object>> linhas = jdbcTemplate.queryForList(sql.toString(), parametros.toArray());
        List<Sugestao> sugestoes = new ArrayList<>();
        for (Map<String, Object> linha : linhas) {
            sugestoes.add(Sugestao.deMap(linha));
        }
        return sugestoes;
    }

    private String escaparLike(String valor) {
        return valor.replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_");
    }

    public List<Sugestao> listarTodos() {
        return listarTodas();
    }

    public int contar() {
        String sql = "SELECT COUNT(*) FROM sugestao";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
        return total != null ? total : 0;
    }
}
