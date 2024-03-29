package br.com.emanoelCompany.corp.repository;

import br.com.emanoelCompany.corp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(value = "select p from Produto p where upper(trim(p.nome)) like %?1%")
    List<Produto> buscarNome(String nome);

    @Query(value = "select p from Produto p where upper(trim(p.categoria)) like %?1%")
    List<Produto> findProdutoByCategoria(String categoria);

    @Transactional
    @Modifying
    @Query("UPDATE Produto p SET p.quantidade = p.quantidade - :quantidade WHERE p.id = :produtoID")
    void venderProduto(@Param("produtoID") Long produtoID , @Param("quantidade") int quantidade);

}
