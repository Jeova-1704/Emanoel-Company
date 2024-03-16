CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,
    categoria VARCHAR(255),
    quantidade INT NOT NULL,
    data_entrada DATE NOT NULL,
    codigo_produto VARCHAR(255) NOT NULL
);
