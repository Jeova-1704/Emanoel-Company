// FUNCAO MODAL CADASTRAR PRODUTO
document.getElementById('produtoForm').addEventListener('submit', function (event) {

    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const codigo_produto = document.getElementById('codigo_produto').value;
    const data_entrada = document.getElementById('data_entrada').value;
    const quantidade = parseInt(document.getElementById('quantidade').value);
    const preco = parseFloat(document.getElementById('preco').value);
    const categoria = document.getElementById('categoria').value;
    const fornecedor = document.getElementById('fornecedor').value;

    const data_entrada_parts = data_entrada.split('/');
    const data_entrada_formatada = `${data_entrada_parts[2]}-${data_entrada_parts[1]}-${data_entrada_parts[0]}`;

    const produtoData = {
        nome: nome,
        codigoProduto: codigo_produto,
        dataEntrada: data_entrada_formatada,
        quantidade: quantidade,
        preco: preco,
        categoria: categoria,
        fornecedor: fornecedor
    };


    fetch('http://localhost:8080/produto/cadastrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(produtoData)
    })
        .then(response => {
            if (response.ok) {

                document.getElementById('nome').value = '';
                document.getElementById('codigo_produto').value = '';
                document.getElementById('data_entrada').value = '';
                document.getElementById('quantidade').value = '';
                document.getElementById('preco').value = '';
                document.getElementById('categoria').value = '';
                document.getElementById('fornecedor').value = '';

                const toastElement = document.getElementById('toast');
                const toast = new bootstrap.Toast(toastElement);
                toast.show();

                setTimeout(() => {
                    toast.hide();
                }, 2000);

            } else {
                response.json().then(data => {
                    console.error('Falha ao cadastrar o produto.', data);
                    const toastErroElement = document.getElementById('toastErro');
                    const toastErroBody = toastErroElement.querySelector('.toast-body');
                    toastErroBody.textContent = `Erro ao cadastrar produto! Erro: ${data.message}`;
                    const toastErro = new bootstrap.Toast(toastErroElement);
                    toastErro.show();

                    setTimeout(() => {
                        toastErro.hide();
                    }, 2000);
                });
            }
        })
        .catch(error => {
            console.error('Erro:', error);
        });
});


document.addEventListener('DOMContentLoaded', function (event) {
    document.querySelector('#listarProdutosModal').addEventListener('show.bs.modal', function (event) {
        carregarProdutos();
    });
});

// FIM MODAL CADASTRAR PRODUTO

// INICIO MODAL LISTAR PRODUTOS
function carregarProdutos() {
    const tabelaCorpo = document.querySelector("#listarProdutosModal tbody");
    tabelaCorpo.innerHTML = '';

    fetch('http://localhost:8080/produto/listar')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            data.forEach((produto, index) => {
                const linha = tabelaCorpo.insertRow();
                const celulaId = linha.insertCell(0);
                const celulaNome = linha.insertCell(1);
                const celulaCodigo = linha.insertCell(2);
                const celulaDataEntrada = linha.insertCell(3);
                const celulaQuantidade = linha.insertCell(4);
                const celulaPreco = linha.insertCell(5);
                const celulaValorToral = linha.insertCell(6);
                const celulaCategoria = linha.insertCell(7);
                const celulaFornecedor = linha.insertCell(8);
                const celulaDeletar = linha.insertCell(9);
                const celulaEditar = linha.insertCell(10);

                celulaId.textContent = produto.id;
                celulaNome.textContent = produto.nome;
                celulaCodigo.textContent = produto.codigoProduto;
                celulaDataEntrada.textContent = produto.dataEntrada;
                celulaQuantidade.textContent = produto.quantidade;
                celulaFornecedor.textContent = produto.fornecedor;
                celulaPreco.textContent = produto.preco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
                celulaValorToral.textContent = produto.precoTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
                celulaCategoria.textContent = produto.categoria;

                // Botão Deletar
                const btnDeletar = document.createElement('button');
                btnDeletar.classList.add('btn', 'btn-danger');
                btnDeletar.textContent = 'Deletar';
                btnDeletar.onclick = function () { abrirModalDeDelecao(produto.id); };
                celulaDeletar.appendChild(btnDeletar);

                // Botão Editar
                const btnEditar = document.createElement('button');
                btnEditar.classList.add('btn', 'btn-primary');
                btnEditar.textContent = 'Editar';
                btnEditar.onclick = function () { editarProduto(produto.id); };
                celulaEditar.appendChild(btnEditar);
            });
        })
        .catch(error => console.error('Erro ao buscar produtos:', error));
}

//Abrir modal de confirmação para deletar
function abrirModalDeDelecao(id) {
    const confirmacaoModal = new bootstrap.Modal(document.getElementById('confirmacaoDelecaoModal'));
    confirmacaoModal.show();


    const btnConfirmar = document.getElementById('confirmarDelecao');

    btnConfirmar.onclick = null;
    btnConfirmar.onclick = function () {
        deletarProduto(id);
    };
}

//requisição para deletar
function deletarProduto(id) {
    const url = `http://localhost:8080/produto/deletar/${id}`;

    fetch(url, {
        method: 'DELETE',
    }).then(data => {
        const toastElement = document.getElementById('toastDeletar');
        const toast = new bootstrap.Toast(toastElement);
        toast.show();

        setTimeout(() => {
            toast.hide();
        }, 2000);
        carregarProdutos();
    }).catch(error => {

        const toastErroElement = document.getElementById('toastErroDeletar');
        const toastErro = new bootstrap.Toast(toastErroElement);
        toastErro.show();

        console.error('Erro ao deletar produto:', error);
    });

    const confirmacaoModal = bootstrap.Modal.getInstance(document.getElementById('confirmacaoDelecaoModal'));
    confirmacaoModal.hide();
}


function buscarProdutoPeloId(id) {
    const url = `http://localhost:8080/produto/buscarID/${id}`;
    return fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            return response.json();
        })
        .then(data => {
            return data;
        })
        .catch(error => {
            console.error('Erro na busca do produto:', error);
            return undefined;
        });
}



// editar produto
function converterData(dataISO) {
    const data = new Date(dataISO);
    const dia = data.getDate().toString().padStart(2, '0');
    const mes = (data.getMonth() + 1).toString().padStart(2, '0'); // getMonth() retorna mês de 0 a 11
    const ano = data.getFullYear();
    return `${dia}/${mes}/${ano}`;
}
function editarProduto(id) {
    const confirmacaoModal = new bootstrap.Modal(document.getElementById('editarProdutoModal'));
    fetch(`http://localhost:8080/produto/buscarID/${id}`)
        .then(response => {
            return response.json();
        })
        .then(produto => {
            document.getElementById('produto_id').value = produto.id;
            document.getElementById('produto_nome').value = produto.nome;
            document.getElementById('produto_codigo_produto').value = produto.codigoProduto;
            document.getElementById('produto_data_entrada').value = converterData(produto.dataEntrada);
            document.getElementById('produto_quantidade').value = produto.quantidade;
            document.getElementById('produto_preco').value = produto.preco;
            document.getElementById('produto_categoria').value = produto.categoria;
            document.getElementById('produto_fornecedor').value = produto.fornecedor;

            confirmacaoModal.show();
        })
        .catch(error => console.error('Erro ao carregar dados do produto:', error));
}


document.addEventListener('DOMContentLoaded', function() {
    const formEditarProduto = document.getElementById('editarProdutoForm');

    formEditarProduto.addEventListener('submit', function(event) {
        event.preventDefault();

        // Coleta os dados do formulário
        const idProdutoElement = document.getElementById('produto_id');
        const nomeProdutoElement = document.getElementById('produto_nome');
        const codigoProdutoElement = document.getElementById('produto_codigo_produto');
        const dataEntradaElement = document.getElementById('produto_data_entrada');
        const quantidadeElement = document.getElementById('produto_quantidade');
        const precoElement = document.getElementById('produto_preco');
        const categoriaElement = document.getElementById('produto_categoria');
        const fornecedorElement = document.getElementById('produto_fornecedor');

        if (!idProdutoElement || !nomeProdutoElement || !codigoProdutoElement || !dataEntradaElement
            || !quantidadeElement || !precoElement || !categoriaElement || !fornecedorElement) {
            console.error('Um ou mais elementos do formulário não foram encontrados.');
            return;
        }

        const idProduto = idProdutoElement.value;
        const nomeProduto = nomeProdutoElement.value;
        const codigoProduto = codigoProdutoElement.value;
        const dataEntrada = dataEntradaElement.value;
        const quantidade = quantidadeElement.value;
        const preco = precoElement.value;
        const categoria = categoriaElement.value;
        const fornecedor = fornecedorElement.value;

        const dadosProduto = {
            id: idProduto,
            nome: nomeProduto,
            codigoProduto: codigoProduto,
            dataEntrada: dataEntrada,
            quantidade: parseInt(quantidade),
            preco: parseFloat(preco),
            categoria: categoria,
            fornecedor: fornecedor
        };

        // Envia a requisição PUT
        fetch(`http://localhost:8080/produto/atualizar`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dadosProduto)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Falha na requisição');
                }
                return response.json();
            })
            .then(data => {
                const confirmacaoModal = bootstrap.Modal.getInstance(document
                    .getElementById('editarProdutoModal'));
                confirmacaoModal.hide();

                const toastElement = document.getElementById('toastAtualizado');
                const toast = new bootstrap.Toast(toastElement);
                toast.show();

                setTimeout(() => {
                    toast.hide();
                }, 2000);

                carregarProdutos();
            })
            .catch(error => {

                const toastErroElement = document.getElementById('toastErroAtualizar');
                const toastErro = new bootstrap.Toast(toastErroElement);
                toastErro.show();

                console.error('Erro:', error);
            });
    });
});


function pesquisarPorCategoria() {
    var categoria = document.querySelector('select[name="nomecategoria"]').value.toUpperCase();
    var linhas = document.querySelectorAll('#listarProdutosModal tbody tr');

    linhas.forEach(function(linha) {
        var nomeCategoria = linha.querySelector('td:nth-child(8)').textContent.toUpperCase();

        if (nomeCategoria.includes(categoria)) {
            linha.style.display = 'table-row';

        } else {
            linha.style.display = 'none';
        }
    });
}

document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();
    pesquisarPorCategoria();
});

function pesquisarPorFornecedor() {
    var fornecedor = document.querySelector('select[name="fornecedores"]').value.toUpperCase();
    var linhas = document.querySelectorAll('#listarProdutosModal tbody tr');

    linhas.forEach(function(linha) {
        var nomeFornecedor = linha.querySelector('td:nth-child(9)').textContent.toUpperCase();

        if (nomeFornecedor.includes(fornecedor)) {
            linha.style.display = 'table-row';

        } else {
            linha.style.display = 'none';
        }
    });
}

document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();
    pesquisarPorFornecedor();
});

// FIM MODAL LISTAR PRODUTOS

// FUNCAO MODAL VENDER PRODUTO

document.addEventListener('DOMContentLoaded', function (event) {
    document.querySelector('#venderProdutoModal').addEventListener('show.bs.modal', function (event) {
        listagemVendaProduto();
    });
});
document.getElementById("vender_salvarAlteracoes").addEventListener("click", function(event) {
    event.preventDefault();

    var rows = document.getElementsByClassName("input-row");
    var dataToSend = {};

    for (var i = 0; i < rows.length; i++) {
        var quantidade = rows[i].querySelector('input[name="quantidade"]').value;
        var produto_id = rows[i].querySelector('input[name="produto_id"]').value;

        // Verifica se ambos os campos possuem valores
        if (quantidade && produto_id) {
            dataToSend[parseInt(produto_id)] = parseInt(quantidade);
        }
    }

    // Verifica se há dados para enviar
    if (Object.keys(dataToSend).length > 0) {

        fetch(`http://localhost:8080/produto/vender`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dataToSend),
        })
            .then(response => {
                if (response.ok) {

                    document.getElementById('quantidade_venda').value = '';
                    document.getElementById('id_venda').value = '';

                    var container = document.getElementById("inputs-container");
                    var rows = container.getElementsByClassName("input-row");

                    if (rows.length > 1) {
                        let c = 0;
                        for(c;(rows.length)-1;c++){
                            container.removeChild(container.lastChild);
                        }
                    }

                    const toastElement = document.getElementById('mensagemVendaProduto');
                    const toast = new bootstrap.Toast(toastElement);
                    toast.show();

                    setTimeout(() => {
                        toast.hide();
                    }, 2000);

                    listagemVendaProduto()
                } else {
                    response.json().then(data => {
                        console.error('Falha ao vender o produto.', data);
                        const toastErroElement = document.getElementById('mensagemErroVendaProduto');
                        const toastErroBody = toastErroElement.querySelector('.toast-body');
                        toastErroBody.textContent = `Erro ao vender produto! Erro: ${data.message}`;
                        const toastErro = new bootstrap.Toast(toastErroElement);
                        toastErro.show();

                        setTimeout(() => {
                            toastErro.hide();
                        }, 2000);

                    }).catch(error => {
                        console.error("Erro ao fazer parsing json" ,  error);
                    })
                }
            })
            .catch(error => {
                console.error("Erro ao enviar dados para o backend:", error);
            });
    } else {
        const toastElement = document.getElementById('vendaDadosInsuficiente');
        const toast = new bootstrap.Toast(toastElement);
        toast.show();

        setTimeout(() => {
            toast.hide();
        }, 2000);
    }
});

function pesquisarPorNomeVender() {
    var nome = document.querySelector('input[name="pesquisaVender"]').value.toLowerCase();
    var linhas = document.querySelectorAll('#venderProdutoModal tbody tr');

    linhas.forEach(function(linha) {
        var nomeProduto = linha.querySelector('td:nth-child(2)').textContent.toLowerCase();

        if (nomeProduto.includes(nome)) {
            linha.style.display = 'table-row';
        } else {
            linha.style.display = 'none';
        }
    });
}


function listagemVendaProduto() {
    const tabelaCorpo = document.querySelector("#venderProdutoModal tbody");
    tabelaCorpo.innerHTML = '';

    fetch('http://localhost:8080/produto/listar')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            data.forEach((produto, index) => {
                const linha = tabelaCorpo.insertRow();
                const celulaId = linha.insertCell(0);
                const celulaNome = linha.insertCell(1);
                const celulaQuantidade = linha.insertCell(2);
                const celulaPreco = linha.insertCell(3);

                celulaId.textContent = produto.id;
                celulaNome.textContent = produto.nome;
                celulaQuantidade.textContent = produto.quantidade;
                celulaPreco.textContent = produto.preco.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

            });
        })
        .catch(error => console.error('Erro ao buscar produtos:', error));
}


function pesquisarPorNome() {
    var nome = document.querySelector('input[name="nomepesquisa"]').value.toLowerCase();
    var linhas = document.querySelectorAll('#listarProdutosModal tbody tr');

    linhas.forEach(function(linha) {
        var nomeProduto = linha.querySelector('td:nth-child(2)').textContent.toLowerCase();

        if (nomeProduto.includes(nome)) {
            linha.style.display = 'table-row';

        } else {
            linha.style.display = 'none';
        }
    });
}

document.querySelector('form').addEventListener('submit', function(event) {
    event.preventDefault();
    pesquisarPorNome();
});

document.getElementById("add-row").addEventListener("click", function () {
    var container = document.getElementById("inputs-container");
    var newRow = document.createElement("div");
    newRow.classList.add("row", "input-row", "mt-3");
    newRow.innerHTML = `
        <div class="col">
            <input type="number" class="form-control" id="quantidade_venda" name="quantidade" placeholder="Insira a quantidade">
        </div>
        <div class="col">
            <input type="text" class="form-control" id="id_venda" name="produto_id" placeholder="Insira o ID do produto">
        </div>
    `;
    container.appendChild(newRow);
});

document.getElementById("remove-row").onclick = function () {
    var container = document.getElementById("inputs-container");
    var rows = container.getElementsByClassName("input-row");

    if (rows.length > 1) {
        container.removeChild(container.lastChild);
    }
};

document.getElementById("venderProdutoForm").onsubmit = function (event) {
    event.preventDefault();
};

// FIM DA FUNÇÃO MODAL VENDER PRODUTO

// INICIO MODAL CONTROLE DE CAIXA
document.addEventListener('DOMContentLoaded', function () {
    const modal = document.getElementById('controleCaixaModal');
    modal.addEventListener('show.bs.modal', function (event) {
        fetch('http://localhost:8080/estoque')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao obter os dados do estoque');
                }
                return response.json();
            })
            .then(data => {
                document.getElementById('totalEstoque').value = data.totalDinheiroEstoque.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });;
                document.getElementById('totalProduto').value = data.totalProdutos;
                document.getElementById('totalUnitario').value = data.totalUnitario;
            })
            .catch(error => {
                console.error(error);
            });
    });
});
// FIM MODAL CONTROLE DE CAIXA

//Inicio modal de emitir relatorio do estoque

document.getElementById('emitirNotaLink').addEventListener('click', function(e) {
    e.preventDefault(); // Impede o comportamento padrão do link
    fetch('http://localhost:8080/estoque/products/pdf', {
        method: 'GET',
        responseType: 'blob'
    })
        .then(response => {
            if (response.ok) return response.blob();
            throw new Error('Erro na solicitação do PDF.');
        })
        .then(blob => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'products.pdf'; // Define o nome do arquivo para download
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        })
        .catch(error => {
            console.error('Erro ao baixar o PDF:', error);
        });
});