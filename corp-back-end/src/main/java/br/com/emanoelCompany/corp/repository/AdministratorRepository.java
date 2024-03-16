package br.com.emanoelCompany.corp.repository;

import br.com.emanoelCompany.corp.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdministratorRepository extends JpaRepository<Administrador, Integer> {
    @Query(value = "select * from administradores where usuario = :usuario and senha = :senha", nativeQuery = true)
    public Administrador login(@Param("usuario") String usuario, @Param("senha") String senha);

}
