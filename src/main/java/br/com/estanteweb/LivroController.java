package br.com.estanteweb;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

@Controller
public class LivroController {

    private LivroRepository repositorio = new LivroRepository();
    
    @GetMapping("/livros/html")
    public String listarLivros(Model model) {

        model.addAttribute("livros", repositorio.listarTodas());

        return "livros";
    }

    @GetMapping("/livros/total")
    @ResponseBody
    public String calcularTotal() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<h1>Total de Livros: %d </h1>", getTotal()));

        return sb.toString();
    }

    
    @GetMapping("/livros/busca")
    public ResponseEntity<String> precoMaximo(@RequestParam(defaultValue = "999") double precoMaximo) {

        List<Livro> livros = repositorio.listarAtePreco(precoMaximo);

        if (livros.isEmpty()) {
            String mensagem = String.format("<h1>Nenhum livro encontrado com o preço até R$ %.2f.</h1>", precoMaximo);
            return ResponseEntity.status(404).body(mensagem);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("<h1> Livros até R$: %.2f </h1>", precoMaximo));

        sb.append("<ul>");
        for (Livro l : livros) {
            sb.append("<li>")
            .append(l.getTitulo()).append(" - ")
            .append(l.getAutor()).append(" (")
            .append(l.getAno()).append(") - R$ ")
            .append(l.getPreco())
            .append("</li>");
        }
        sb.append("</ul>");

        return ResponseEntity.ok().header("X-Total-Encontrados", String.valueOf(livros.size())).body(sb.toString());
        
    }

    @GetMapping("/livros/quem-acessa")
    @ResponseBody
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent) {

        return "<h1>Você acessou o sistema usando:</h1> <p>" + userAgent + "</p>";

    }

    public int getTotal() {
        
        LivroRepository quantidade = new LivroRepository();

        int total = quantidade.contarLivros();

        return total;
    }

}