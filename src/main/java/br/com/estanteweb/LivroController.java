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

import jakarta.servlet.http.HttpSession;

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

        // Outros títulos do mesmo autor
        List<Livro> outrosLivros = repoLivros.listarDoMesmoAutor(livro.getAutor(), id);
        model.addAttribute("outrosLivros", outrosLivros);

        return "livro";
    }

    @GetMapping("/livros/{id}/editar")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        Livro livro = repoLivros.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("livro", livro);
        return "livro-editar";
    }

    @PostMapping("/livros/{id}/editar")
    public String atualizar(@PathVariable Long id, Model model,
                            @RequestParam(defaultValue = "") String titulo,
                            @RequestParam(defaultValue = "") String autor,
                            @RequestParam(required = false) Integer ano,
                            @RequestParam(required = false) Double preco,
                            HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        Livro existente = repoLivros.buscarPorId(id);
        if (existente == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (titulo.isBlank() || autor.isBlank() || ano == null || preco == null || preco <= 0) {
            model.addAttribute("erro", "Todos os campos são obrigatórios e o preço deve ser maior que zero.");
            Livro livroInvalido = new Livro();
            livroInvalido.setId(id);
            livroInvalido.setTitulo(titulo);
            livroInvalido.setAutor(autor);
            livroInvalido.setAno(ano != null ? ano : 0);
            livroInvalido.setPreco(preco != null ? preco : 0.0);
            model.addAttribute("livro", livroInvalido);
            return "livro-editar";
        }

        existente.setTitulo(titulo);
        existente.setAutor(autor);
        existente.setAno(ano);
        existente.setPreco(preco);

        repoLivros.atualizar(existente);

        return "redirect:/livros/" + id;
    }

    @GetMapping("/livros/{id}/remover")
    public String telaRemover(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        Livro livro = repoLivros.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("livro", livro);
        return "livro-remover";
    }

    @PostMapping("/livros/{id}/remover")
    public String confirmarRemover(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        Livro livro = repoLivros.buscarPorId(id);
        if (livro == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        repoLivros.remover(id);
        return "redirect:/livros/html";
    }

    @GetMapping(value = "/livros/html", produces = "text/html")
    public String listarHtml(Model model,
                             @RequestParam(required = false) String q,
                             @RequestParam(required = false) String autor,
                             @RequestParam(required = false) String ordem) { 
        List<Livro> livros;
        boolean temFiltro = (q != null && !q.isBlank()) || (autor != null && !autor.isBlank());

        if (temFiltro) {
            livros = repoLivros.buscar(q, autor);
        } else if (ordem != null && !ordem.isBlank()) {
            livros = repoLivros.listarOrdenadoPor(ordem);
        } else {
            livros = repoLivros.listarTodos();
        }

        model.addAttribute("livros", livros);
        model.addAttribute("q", q);
        model.addAttribute("autor", autor);
        model.addAttribute("temFiltro", temFiltro);

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
    public String cadastro(HttpSession session){
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }
        return "livro-novo";
    }

    @PostMapping("/livros")
    public String cadastros(Model model,
                            @RequestParam(defaultValue = "") String titulo,
                            @RequestParam(defaultValue = "") String autor,
                            @RequestParam(required = false) Integer ano,
                            @RequestParam(required = false) Double preco,
                            HttpSession session){
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/login";
        }

        if (titulo.isBlank() || autor.isBlank() || ano == null || preco == null || preco <= 0) {
            model.addAttribute("erro", "Todos os campos são obrigatórios e o preço deve ser maior que zero.");
            model.addAttribute("titulo", titulo);
            model.addAttribute("autor", autor);
            model.addAttribute("ano", ano);
            model.addAttribute("preco", preco);
            return "livro-novo";
        }

        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setPreco(preco);

        repoLivros.salvar(livro);

        return "redirect:/livros/html";
    }
}
