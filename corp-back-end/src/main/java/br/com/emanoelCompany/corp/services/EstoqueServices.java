package br.com.emanoelCompany.corp.services;

import br.com.emanoelCompany.corp.DTO.EstoqueDTO;
import br.com.emanoelCompany.corp.DTO.ProdutoDTO;
import br.com.emanoelCompany.corp.exceptions.ErroGerarPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class EstoqueServices {

    @Autowired
    private ProdutoService produtoService;

    public EstoqueDTO dadosEstoque() {
        List<ProdutoDTO> listaProdutosEstoque = produtoService.listarProdutos();
        Double totalDinheiroEstoque = listaProdutosEstoque.stream().mapToDouble(ProdutoDTO::precoTotal).sum();
        Integer totalProdutos = listaProdutosEstoque.stream().mapToInt(ProdutoDTO::quantidade).sum();
        Integer totalUnitario = listaProdutosEstoque.size();
        return new EstoqueDTO(totalDinheiroEstoque, totalProdutos, totalUnitario);
    }

    public byte[] criarRelatorioProdutosTxt() {
        List<ProdutoDTO> produtos = produtoService.listarProdutos();
        StringBuilder sb = new StringBuilder();
        sb.append("Relatório de Produtos\n\n");
        for (ProdutoDTO produto : produtos) {
            sb.append("ID: ").append(produto.id()).append(" -- ");
            sb.append("Nome: ").append(produto.nome()).append(" -- ");
            sb.append("Preço: ").append(produto.preco()).append(" -- ");
            sb.append("Categoria: ").append(produto.categoria()).append(" -- ");
            sb.append("Quantidade: ").append(produto.quantidade()).append(" -- ");
            sb.append("Data de Entrada: ").append(produto.dataEntrada()).append(" -- ");
            sb.append("Código do Produto: ").append(produto.codigoProduto()).append(" -- ");
            sb.append("Fornecedor: ").append(produto.fornecedor()).append(" -- ");
            sb.append("Valor Total: ").append(produto.precoTotal()).append(" | ");
            sb.append("\n");
        }
        return sb.toString().getBytes();
    }



    public byte[] generateProductPDF() {
        List<ProdutoDTO> listaProduto = produtoService.listarProdutos();
        try (PDDocument documento = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            documento.addPage(page);

            try (PDPageContentStream conteudoPDF = new PDPageContentStream(documento, page)) {
                conteudoPDF.beginText();
                conteudoPDF.setFont(PDType1Font.HELVETICA, 10);
                conteudoPDF.newLineAtOffset(50, 700);
                conteudoPDF.showText("Relatorio dos produtos:");
                conteudoPDF.endText();

                float margin = 50;
                float yStart = 680;
                float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
                float yPosition = yStart;

                String[] headers = {"Nome", "Categoria", "Quantidade", "Data Entrada", "Cód. Produto", "Fornecedor", "Preço", "Preço Total"};
                float rowHeight = 20;
                float tableTopY = yPosition - (rowHeight / 2) - (rowHeight / 2);

                conteudoPDF.setFont(PDType1Font.HELVETICA_BOLD, 7);
                conteudoPDF.beginText();
                conteudoPDF.newLineAtOffset(margin, tableTopY);

                for (String header : headers) {
                    conteudoPDF.showText(header + "          ");
                }
                conteudoPDF.endText();
                tableTopY -= rowHeight;
                conteudoPDF.setFont(PDType1Font.HELVETICA, 7);

                for (ProdutoDTO produto : listaProduto) {
                    String[] dadosProdutos = {
                            produto.nome(), produto.categoria(), String.valueOf(produto.quantidade()), produto.dataEntrada().toString(),
                            produto.codigoProduto(), produto.fornecedor(), String.valueOf(produto.preco()), String.valueOf(produto.precoTotal())
                    };

                    conteudoPDF.beginText();
                    conteudoPDF.newLineAtOffset(margin, tableTopY);
                    for (String p : dadosProdutos) {
                        conteudoPDF.showText(p + "     |    ");
                    }
                    conteudoPDF.endText();
                    tableTopY -= rowHeight;
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            documento.save(out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new ErroGerarPDF();
        }
    }
}
