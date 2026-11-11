package br.com.estanteweb;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class LivroController {

    @Autowired 
    private LivroRepository repoLivros;

    @GetMapping("/livros/{id}")
    public String detalhes(@PathVariable Long id, Model model) {
        Livro livro = repoLivros.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("livro", livro);

        // 4.4 Desafio: outros títulos do mesmo autor
        List<Livro> outrosLivros = repoLivros.listarDoMesmoAutor(livro.getAutor(), id);
        model.addAttribute("outrosLivros", outrosLivros);

        return "livro";
    }

    @GetMapping(value = "/livros/html", produces = "text/html")
    public String listarHtml(Model model, @RequestParam(required = false) String ordem) { 
        if (ordem != null && !ordem.isBlank()) {
            model.addAttribute("livros", repoLivros.listarOrdenadoPor(ordem));
        } else {
            model.addAttribute("livros", repoLivros.listarTodos());
        }

        return "livros";
    }

    @ResponseBody
    @GetMapping("/livros")
    public String books(){
        String resposta = "Acervo EstanteWeb: ";
        for(Livro l : repoLivros.listarTodos()){
            resposta += l.getTitulo() + ", " +
            l.getAutor() + " " +
            l.getAno() + " (R$ " +
            String.format("%.2f", l.getPreco()) + ") | ";
        }
        return resposta;
    }

    @ResponseBody
    @GetMapping("/livros/total")
    public String contBooks(){
        String response = repoLivros.contarLivros();
        return response;
    }

    @GetMapping("/livros/busca")
    public ResponseEntity<String> buscaLivros(@RequestParam(defaultValue = "999") double precoMaximo){
        String resposta = "Acervo EstanteWeb: ";
        for(Livro l : repoLivros.listarAtePreco(precoMaximo)){
            resposta += l.getTitulo() + ", " +
            l.getAutor() + " " +
            l.getAno() + " (R$ " +
            String.format("%.2f", l.getPreco()) + ") | ";
        }
        List<Livro> resultado = repoLivros.listarAtePreco(precoMaximo);
        if(resultado.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum livro encontrado");
        }
        return ResponseEntity.ok()
        .header("X-Total-Encontrados", String.valueOf(resultado.size()))
        .body(resposta);
    }

    @ResponseBody
    @GetMapping("/livros/quem-acessa")
    public String quemAcessa(@RequestHeader("User-Agent") String userAgent) {
        return "User-Agent em questão: " + userAgent;
    }
    
    @GetMapping("/livros/valor-total")
    @ResponseBody
    public String valorTotal() {
        Double total = repoLivros.somarValorAcervo();

        return String.format(
            "Valor total do acervo: R$ %.2f",
            total
        );
    }

    @GetMapping("/livros/novo")
    public String cadastro(){
        return "livro-novo";
    }

    @PostMapping("/livros")
    public String cadastros(Model model, @RequestParam String titulo, @RequestParam String autor, @RequestParam int ano, @RequestParam double preco){
        Livro livro = new Livro();

        if (livro.getTitulo().isEmpty()) {
            model.addAttribute("erro", "Todos os campos são obrigatórios e o preço deve ser maior que zero.");
        }
        if (livro.getAutor().isEmpty()) {
            model.addAttribute("erro", "Todos os campos são obrigatórios e o preço deve ser maior que zero.");
        }
        if (livro.getPreco() <= 0) {
            model.addAttribute("erro", "Todos os campos são obrigatórios e o preço deve ser maior que zero.");
        }

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);

        repoLivros.salvar(livro);

        return "redirect:/livros/html";
    }
}
