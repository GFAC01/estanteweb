package br.com.estanteweb;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LivroController {

    private final LivroRepository repositorio;

    public LivroController(LivroRepository repositorio) {
        this.repositorio = repositorio;
    }

    // Página com todos os livros
    @GetMapping("/livros")
    public String listarLivros(Model model) {

        model.addAttribute("livros", repositorio.listarTodas());

        return "livros";
    }

    // Conta quantos livros existem no banco
    @GetMapping("/livros/total")
    @ResponseBody
    public String calcularTotal() {

        int total = repositorio.contarLivros();

        return String.format(
                "<h1>Total de Livros: %d</h1>",
                total
        );
    }

    // Busca livros pelo preço máximo
    @GetMapping("/livros/busca")
    public ResponseEntity<String> precoMaximo(
            @RequestParam(defaultValue = "999") double precoMaximo) {

        List<Livro> livros =
                repositorio.listarAtePreco(precoMaximo);

        if (livros.isEmpty()) {

            String mensagem = String.format(
                    "<h1>Nenhum livro encontrado com o preço até R$ %.2f.</h1>",
                    precoMaximo
            );

            return ResponseEntity
                    .status(404)
                    .body(mensagem);
        }

        StringBuilder sb = new StringBuilder();

        sb.append(
                String.format(
                        "<h1>Livros até R$: %.2f</h1>",
                        precoMaximo
                )
        );

        sb.append("<ul>");

        for (Livro livro : livros) {

            sb.append("<li>")
                    .append(livro.getTitulo())
                    .append(" - ")
                    .append(livro.getAutor())
                    .append(" (")
                    .append(livro.getAno())
                    .append(") - R$ ")
                    .append(livro.getPreco())
                    .append("</li>");
        }

        sb.append("</ul>");

        return ResponseEntity
                .ok()
                .header(
                        "X-Total-Encontrados",
                        String.valueOf(livros.size())
                )
                .body(sb.toString());
    }

    // Mostra o navegador utilizado pelo usuário
    @GetMapping("/livros/quem-acessa")
    @ResponseBody
    public String quemAcessa(
            @RequestHeader("User-Agent") String userAgent) {

        return "<h1>Você acessou o sistema usando:</h1>"
                + "<p>"
                + userAgent
                + "</p>";
    }

    // Calcula o valor total dos livros
    @GetMapping("/livros/valor-total")
    @ResponseBody
    public double valorTotal() {

        return repositorio.valorTotal();
    }
}