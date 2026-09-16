CREATE TABLE tb_historico_status (
     id BIGSERIAL PRIMARY KEY,
     status_anterior VARCHAR(30),
     status_novo VARCHAR(30) NOT NULL,
     data_mudanca TIMESTAMP WITHOUT TIME ZONE NOT NULL,
     ordem_servico_id BIGINT NOT NULL,
     CONSTRAINT fk_historico_os FOREIGN KEY (ordem_servico_id) REFERENCES tb_ordem_servico(id) ON DELETE CASCADE
);

CREATE INDEX idx_historico_os ON tb_historico_status(ordem_servico_id);