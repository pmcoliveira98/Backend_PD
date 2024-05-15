package api.service;

import api.entity.Livro;
import api.entity.Proposal;
import api.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    @Autowired
    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }


    public List<Livro> getAll() {
        return livroRepository.findAll();
    }

    public Livro getLivroById(Long id) {
        Optional<Livro> livroOptional = livroRepository.findById(id);
        return livroOptional.orElse(null);
    }

    public Livro createLivro(String nome, String autor,String categoria,String edicao) {
        Livro livro = new Livro(nome, autor,categoria,edicao);
        return livroRepository.save(livro);
    }

    public Livro updateLivro(Long id, String nome, String autor,String categoria,String edicao, List<Proposal> proposalList) {
        Livro p = getLivroById(id);
        if (p == null) {
            return null;
        }

        p.setNome(nome);
        p.setAutor(autor);
        p.setCategoria(categoria);
        p.setEdicao(edicao);
        p.setProposals(proposalList);
        return livroRepository.save(p);
    }

    public void deleteLivro(Long id) {
        livroRepository.deleteById(id);
    }

    public void save(Livro livro) {
        livroRepository.save(livro);
    }

    public List<Livro> getLivrosOrderByProposalsSize() {
        return livroRepository.findAllOrderByProposalsSizeAsc();
    }
}
