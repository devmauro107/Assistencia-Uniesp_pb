# Assistência Técnica API (Assistencia-Uniesp)

API RESTful para gerenciamento de ordens de serviço, clientes, técnicos e equipamentos em assistências técnicas especializadas.

---

## 🛠️ Tecnologias e Ferramentas

* **Linguagem:** Java 21+
* **Framework:** Spring Boot 3+
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de Dados:** PostgreSQL
* **Versionamento de Banco:** Flyway Migration
* **Build e Dependências:** Maven
* **Produtividade:** Lombok

---

## 🏛️ Arquitetura

O projeto utiliza uma abordagem **Package-by-Feature** sob o namespace `internal`, combinando isolamento modular de domínios com as camadas técnicas clássicas:

* `controller`: Endpoints REST e validações de entrada.
* `dto`: Objetos de transferência de dados (Request/Response).
* `entity`: Entidades mapeadas via JPA.
* `repository`: Interfaces de acesso e consultas ao banco de dados.
* `service`: Regras de negócio, transações e orquestração.
* `config` / `exception`: Configurações transversais e tratamento global de erros.

---

## 📋 Status do Projeto

* [x] Configuração de DataSource com variáveis de ambiente e fallback local
* [x] Migrações iniciais via Flyway (`V1__criar_tabelas_iniciais.sql`)
* [x] Mapeamento das entidades fundamentais (`Cliente`, `Equipamento`, `Tecnico`)
* [ ] Modelagem e fluxo da Ordem de Serviço (`OrdemServico`, `HistoricoStatus`)
* [ ] Camada de regras de negócio (`Service`) e APIs (`Controller`)
* [ ] Testes automatizados com JUnit 5 e Mockito