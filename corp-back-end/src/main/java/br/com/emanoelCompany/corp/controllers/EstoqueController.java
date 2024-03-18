package br.com.emanoelCompany.corp.controllers;

import br.com.emanoelCompany.corp.DTO.EstoqueDTO;
import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import br.com.emanoelCompany.corp.services.EstoqueServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueServices estoqueServices;

    @GetMapping
    public ResponseEntity<EstoqueDTO> getDadosEstoque() {
        EstoqueDTO estoqueDTO = estoqueServices.dadosEstoque();
        return ResponseEntity.ok(estoqueDTO);
    }

    @GetMapping("/emitir-relatorio")
    public ResponseEntity<byte[]> gerarRelatorioProdutos() {
        byte[] relatorioTxt = estoqueServices.criarRelatorioProdutosTxt();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("filename", "relatorio_produtos.txt");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(relatorioTxt);
    }
}
