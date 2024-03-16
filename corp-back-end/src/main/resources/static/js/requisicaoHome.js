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


    fetch('http://localhost:8080/produto', {
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
