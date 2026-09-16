CREATE TABLE tb_ordem_servico (
      id BIGSERIAL PRIMARY KEY,
      status VARCHAR(30) NOT NULL,
      prioridade VARCHAR(20) NOT NULL,
      data_abertura TIMESTAMP WITHOUT TIME ZONE NOT NULL,
      data_conclusao TIMESTAMP WITHOUT TIME ZONE,
      descricao_defeito TEXT NOT NULL,
      equipamento_id BIGINT NOT NULL,
      tecnico_id BIGINT,
      CONSTRAINT fk_os_equipamento FOREIGN KEY (equipamento_id) REFERENCES tb_equipamento(id),
      CONSTRAINT fk_os_tecnico FOREIGN KEY (tecnico_id) REFERENCES tb_tecnico(id)
);

CREATE INDEX idx_os_status ON tb_ordem_servico(status);
CREATE INDEX idx_os_equipamento ON tb_ordem_servico(equipamento_id);