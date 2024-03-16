package br.com.emanoelCompany.corp.repository;

import br.com.emanoelCompany.corp.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrador, Integer> {
    @Query("SELECT a FROM Administrador a WHERE a.usuario = :usuario AND a.senha = :senha")
    Administrador login(@Param("usuario") String usuario, @Param("senha") String senha);

}
