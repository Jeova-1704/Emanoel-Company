package br.com.emanoelCompany.corp.repository;

import br.com.emanoelCompany.corp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(value = "select p from Produto p where upper(trim(p.nome)) like %?1%")
    List<Produto> buscarNome(String nome);

    @Query(value = "select p from Produto p where upper(trim(p.categoria)) like %?1%")
    List<Produto> findProdutoByCategoria(String categoria);
}
