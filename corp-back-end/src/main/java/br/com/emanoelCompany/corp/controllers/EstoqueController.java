package br.com.emanoelCompany.corp.controllers;

import br.com.emanoelCompany.corp.DTO.EstoqueDTO;
import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import br.com.emanoelCompany.corp.services.EstoqueServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/products/pdf")
    public ResponseEntity<byte[]> getProductPDF() {
        try {
            byte[] pdfBytes = estoqueServices.generateProductPDF();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "relatorio-produtos.pdf";
            headers.setContentDispositionFormData(filename, filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
