package br.com.emanoelCompany.corp.services;

import br.com.emanoelCompany.corp.model.Administrador;
import br.com.emanoelCompany.corp.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administradorRepository;

    public boolean administradorExiste(String usuario, String senha) {
        Administrador administrador = administradorRepository.login(usuario, senha);
        return administrador != null;
    }
}
