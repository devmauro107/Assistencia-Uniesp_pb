CREATE TABLE tb_cliente (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE tb_tecnico (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    especialidade VARCHAR(50) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE tb_equipamento (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(80) NOT NULL,
    marca VARCHAR(80) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    numero_serie VARCHAR(100) UNIQUE,
    descricao_problema VARCHAR(255),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    cliente_id BIGINT NOT NULL,
    CONSTRAINT fk_equipamento_cliente FOREIGN KEY (cliente_id) REFERENCES tb_cliente(id)
);

CREATE INDEX idx_equipamento_cliente_id ON tb_equipamento(cliente_id);