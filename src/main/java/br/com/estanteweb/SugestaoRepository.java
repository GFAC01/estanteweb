package br.com.estanteweb;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SugestaoRepository {

    private List<Sugestao> sugestoes = new ArrayList<>();

    public void adicionar(Sugestao s) {
        sugestoes.add(s);
    }
    
    public List<Sugestao> listarTodas() {
        return sugestoes;
    }
}
