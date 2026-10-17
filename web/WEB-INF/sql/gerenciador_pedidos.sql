DROP DATABASE IF EXISTS gerenciador__de_pedidos;

CREATE DATABASE gerenciador__de_pedidos 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE gerenciador__de_pedidos;

CREATE TABLE produto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(150) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(100),
    processador VARCHAR(100),
    memoria_ram VARCHAR(50),
    armazenamento VARCHAR(50),
    cor VARCHAR(30),
    voltagem VARCHAR(20),
    garantia_meses INT
);

CREATE TABLE decorator_adicional (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    valor DECIMAL(10,2) NOT NULL DEFAULT 0,
    percentual BOOLEAN DEFAULT FALSE,
    meses INT DEFAULT 0,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente VARCHAR(150) NOT NULL,
    data_pedido DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    decorator_frete_id INT NULL,
    decorator_brinde_id INT NULL,
    decorator_personalizacao_id INT NULL,
    total_final DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (decorator_frete_id) REFERENCES decorator_adicional(id),
    FOREIGN KEY (decorator_brinde_id) REFERENCES decorator_adicional(id),
    FOREIGN KEY (decorator_personalizacao_id) REFERENCES decorator_adicional(id)
);

CREATE TABLE item_pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pedido_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    decorator_garantia_id INT NULL,
    valor_garantia DECIMAL(10,2) DEFAULT 0,
    subtotal DECIMAL(10,2) DEFAULT 0,

    FOREIGN KEY (pedido_id) REFERENCES pedido(id) ON DELETE CASCADE,
    FOREIGN KEY (produto_id) REFERENCES produto(id),
    FOREIGN KEY (decorator_garantia_id) REFERENCES decorator_adicional(id)
);

INSERT INTO decorator_adicional 
(nome, tipo, valor, percentual, meses, ativo) 
VALUES
('Frete Padrão', 'FRETE', 25.00, FALSE, 0, TRUE),
('Frete Expresso', 'FRETE', 50.00, FALSE, 0, TRUE),
('Garantia Estendida 12 meses', 'GARANTIA', 10.00, TRUE, 12, TRUE),
('Brinde Premium', 'BRINDE', 35.00, FALSE, 0, TRUE),
('Personalização do Produto', 'PERSONALIZACAO', 45.00, FALSE, 0, TRUE);
