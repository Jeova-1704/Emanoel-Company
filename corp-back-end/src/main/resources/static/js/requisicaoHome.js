// requisição cadastar produtos
document.getElementById('produtoForm').addEventListener('submit', function (event) {

    event.preventDefault();

    const nome = document.getElementById('nome').value;
    const codigo_produto = document.getElementById('codigo_produto').value;
    const data_entrada = document.getElementById('data_entrada').value;
    const quantidade = parseInt(document.getElementById('quantidade').value);
    const preco = parseFloat(document.getElementById('preco').value);
    const categoria = document.getElementById('categoria').value;

    const data_entrada_parts = data_entrada.split('/');
    const data_entrada_formatada = `${data_entrada_parts[2]}-${data_entrada_parts[1]}-${data_entrada_parts[0]}`;

    const produtoData = {
        nome: nome,
        codigoProduto: codigo_produto,
        dataEntrada: data_entrada_formatada,
        quantidade: quantidade,
        preco: preco,
        categoria: categoria
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

//Listar produtos
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
                const celulaDeletar = linha.insertCell(8);
                const celulaEditar = linha.insertCell(9);

                celulaId.textContent = produto.id;
                celulaNome.textContent = produto.nome;
                celulaCodigo.textContent = produto.codigoProduto;
                celulaDataEntrada.textContent = produto.dataEntrada;
                celulaQuantidade.textContent = produto.quantidade;
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
    btnConfirmar.addEventListener('click', function () {
        deletarProduto(id);
    });
}

//requisição para deletar
function deletarProduto(id) {
    const url = `http://localhost:8080/produto/deletar/${id}`;

    fetch(url, {
        method: 'DELETE',
    }).then(data => {
        console.log('Produto deletado:', data);
        const toastElement = document.getElementById('toastDeletadoSucesso');
        const toast = new bootstrap.Toast(toastElement);
        toast.show();
        setTimeout(() => {
            toast.hide();
        }, 2000);
        carregarProdutos();
    }).catch(error => {
        console.error('Erro ao deletar produto:', error);
        const toastElement = document.getElementById('toastErroDeletar');
        const toast = new bootstrap.Toast(toastElement);
        toast.show();

        setTimeout(() => {
            toast.hide();
        }, 2000);
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
            console.log(response.nome)
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
function editarProduto(id) {
    const confirmacaoModal = new bootstrap.Modal(document.getElementById('editarProdutoModal'));
    confirmacaoModal.show();}