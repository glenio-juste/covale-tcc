--covale--

CREATE TABLE caixa (
    id_caixa serial,
    data character varying(10) DEFAULT 'vazio'::character varying,
    tipo character varying(20) DEFAULT 'vazio'::character varying,
    valor character varying(15) DEFAULT 'vazio'::character varying,
    op character varying(20) DEFAULT 'vazio'::character varying
);

CREATE TABLE cidade (
    id_cidade serial,
    nome character varying(30) DEFAULT 'vazio'::character varying,
    estado character varying(2) DEFAULT 'vazio'::character varying
);

CREATE TABLE cliente (
    id_cliente serial,
    nome character varying(50),
    tipo character varying(15) DEFAULT 'vazio'::character varying,
    endereco character varying(50) DEFAULT 'vazio'::character varying,
    cep character varying(10) DEFAULT 'vazio'::character varying,
    telefone character varying(15) DEFAULT 'vazio'::character varying,
    email character varying(50) DEFAULT 'vazio'::character varying,
    id_cidade integer,
    bairro character varying(30) DEFAULT 'vazio'::character varying,
    doc character varying(12) DEFAULT 'vazio'::character varying,
    fonecom character varying(15) DEFAULT 'vazio'::character varying,
    fonecel character varying(15) DEFAULT 'vazio'::character varying,
    pessoa character varying(10) DEFAULT 'vazio'::character varying
);

CREATE TABLE compra (
    id_compra serial,
    data character varying(10) DEFAULT 'vazio'::character varying,
    valor character varying(10) DEFAULT 'vazio'::character varying,
    nf character varying(30) DEFAULT 'vazio'::character varying,
    id_condpag integer,
    id_formapag integer,
    id_fornecedor integer
);

CREATE TABLE condpag (
    id_condpag serial,
    descricao character varying(30)
);

CREATE TABLE formapag (
    id_formapag serial,
    descricao character varying(30)
);

CREATE TABLE fornecedor (
    id_fornecedor serial,
    nome character varying(50) DEFAULT 'vazio'::character varying,
    cnpj character varying(15) DEFAULT 'vazio'::character varying,
    endereco character varying(50) DEFAULT 'vazio'::character varying,
    cep character varying(15) DEFAULT 'vazio'::character varying,
    telefone character varying(15) DEFAULT 'vazio'::character varying,
    email character varying(50) DEFAULT 'vazio'::character varying,
    bairro character varying(30) DEFAULT 'vazio'::character varying,
    fonecom character varying(15) DEFAULT 'vazio'::character varying,
    fonecel character varying(15) DEFAULT 'vazio'::character varying,
    id_cidade integer
);

CREATE TABLE prod_compra (
    id_compra serial,
    id_produto serial,
    qtde character varying(10)
);

CREATE TABLE prod_venda (
    id_produto serial,
    id_venda serial,
    qtde character varying(10)
);

CREATE TABLE produto (
    id_produto serial,
    nome character varying(30),
    marca character varying(30),
    quantidade character varying(20),
    preco_compra character varying(11) DEFAULT 'vazio'::character varying,
    grupo character varying(30),
    medida character varying(15),
    val_unit numeric(6,2)
);

CREATE TABLE usuario (
    id serial,
    nome character varying(30) NOT NULL,
    login character varying(20) NOT NULL,
    senha character varying(20) NOT NULL
);

CREATE TABLE val_prod (
    id_val_prod serial,
    data_compra character varying(10),
    lote character varying(20),
    data_validade character varying(10),
    id_produto integer,
    verifica character varying(30)
);

CREATE TABLE venda (
    id_venda serial,
    data character varying(10) DEFAULT 'vazio'::character varying,
    valor character varying(10) DEFAULT 'vazio'::character varying,
    id_formapag integer,
    id_condpag integer,
    id_cliente integer,
    id_vendedor integer,
    nfv character varying(20)
);

CREATE TABLE vendedor (
    id_vendedor serial,
    nome character varying(50),
    cpf character varying(15) DEFAULT 'vazio'::character varying,
    salario character varying(8) DEFAULT 'vazio'::character varying,
    telefone character varying(15) DEFAULT 'vazio'::character varying,
    email character varying(50) DEFAULT 'vazio'::character varying,
    id_cidade integer,
    endereco character varying(50) DEFAULT 'vazio'::character varying,
    bairro character varying(20) DEFAULT 'vazio'::character varying,
    fonecel character varying(15),
    rg character varying(14),
    adm character varying(10)
);

ALTER TABLE ONLY produto
    ADD CONSTRAINT id_produto PRIMARY KEY (id_produto);

ALTER TABLE ONLY caixa
    ADD CONSTRAINT pk_id_caixa PRIMARY KEY (id_caixa);

ALTER TABLE ONLY compra
    ADD CONSTRAINT pk_id_compra PRIMARY KEY (id_compra);

ALTER TABLE ONLY condpag
    ADD CONSTRAINT pk_id_condpag PRIMARY KEY (id_condpag);

ALTER TABLE ONLY formapag
    ADD CONSTRAINT pk_id_formapag PRIMARY KEY (id_formapag);

ALTER TABLE ONLY venda
    ADD CONSTRAINT pk_id_venda PRIMARY KEY (id_venda);

ALTER TABLE ONLY cidade
    ADD CONSTRAINT id_cidade PRIMARY KEY (id_cidade);
	
ALTER TABLE ONLY cliente
    ADD CONSTRAINT id_cliente PRIMARY KEY (id_cliente);
	
ALTER TABLE ONLY fornecedor
    ADD CONSTRAINT id_fornecedor PRIMARY KEY (id_fornecedor);
	
ALTER TABLE ONLY val_prod
    ADD CONSTRAINT id_val_prod PRIMARY KEY (id_val_prod);
	
ALTER TABLE ONLY usuario
    ADD CONSTRAINT id PRIMARY KEY (id);
	
ALTER TABLE ONLY vendedor
    ADD CONSTRAINT id_vendedor PRIMARY KEY (id_vendedor);
	


INSERT INTO cidade (id_cidade, nome, estado) VALUES (1, 'Assis', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (2, 'Cândido Mota', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (3, 'Tarumã', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (4, 'Rio Claro', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (5, 'Cafezal', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (6, 'Ourinhos', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (7, 'Alexandria', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (8, 'Abatiá', 'PR');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (9, 'Marília', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (10, 'São Paulo', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (11, 'Campo do Jordão', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (12, 'Ibirarema', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (13, 'Maracaí', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (14, 'Pedrinhas Paulista', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (15, 'Frutal do Campo', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (18, 'Paraguaçu Paulista', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (19, 'Cruzália', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (20, 'Palmital', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (21, 'Jundiaí', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (22, 'Florínea', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (23, 'Echaporã', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (25, 'Pirajú', 'SP');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (26, 'Bandeirantes', 'PR');
INSERT INTO cidade (id_cidade, nome, estado) VALUES (24, 'Londrina', 'PR');

INSERT INTO condpag (id_condpag, descricao) VALUES (1, 'à Vista');
INSERT INTO condpag (id_condpag, descricao) VALUES (2, 'Em Carteira');
INSERT INTO condpag (id_condpag, descricao) VALUES (3, 'p/ 15 dias');
INSERT INTO condpag (id_condpag, descricao) VALUES (4, 'p/ 30 dias');
INSERT INTO condpag (id_condpag, descricao) VALUES (5, 'p/ 30/60/90/120 dias');
INSERT INTO condpag (id_condpag, descricao) VALUES (6, 'p/ 30/60 dias');
INSERT INTO condpag (id_condpag, descricao) VALUES (7, 'p/ 30/60/90 dias');

INSERT INTO formapag (id_formapag, descricao) VALUES (1, 'em Dinheiro');
INSERT INTO formapag (id_formapag, descricao) VALUES (2, 'com Cheque');
INSERT INTO formapag (id_formapag, descricao) VALUES (3, 'Cartão de Crédito b. Visa');
INSERT INTO formapag (id_formapag, descricao) VALUES (4, 'Cartão de Crédito b. Master');
INSERT INTO formapag (id_formapag, descricao) VALUES (5, 'Boleto');

INSERT INTO usuario (id, nome, login, senha) VALUES (1, 'Glenn', 'glenn', '0000');