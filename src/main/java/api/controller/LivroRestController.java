package api.controller;
import api.entity.Livro;
import api.service.CompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/livros")
public class LivroRestController {
    private final CompositeService compositeService;

    @Autowired
    public LivroRestController(CompositeService compositeService) {
        this.compositeService = compositeService;
    }

    @GetMapping
    public List<Livro> getAll() {
        return compositeService.getAllLivros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> getStudentById(@PathVariable Long id) {
        Livro livro = compositeService.getLivroById(id);

        if (livro != null) {
            return ResponseEntity.ok(livro);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Livro> createLivro(
            @RequestParam String nome,
            @RequestParam String autor,
            @RequestParam String categoria,
            @RequestParam String edicao
    ) {
        Livro p = compositeService.createLivro(nome, autor,categoria,edicao);
        if (p == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLivro(
            @PathVariable Long id,
            @RequestParam String nome,
            @RequestParam String autor,
            @RequestParam String categoria,
            @RequestParam String edicao,
            @RequestParam List<Long> proposalsIds
            ) {
        try {
            Livro p = compositeService.updateLivro(id, nome,autor, categoria,edicao, proposalsIds);
            if (p != null) {
                return ResponseEntity.ok(p);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        compositeService.deleteLivro(id);
        return ResponseEntity.noContent().build();
    }
}
