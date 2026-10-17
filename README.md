# Gerenciador de Pedidos

Sistema web de gerenciamento de pedidos feito em **Java (Servlets + JSP)** com **MySQL**, que aplica o padrão de projeto **Decorator** para calcular o valor final do pedido (frete, garantia, brinde e personalização).

## Funcionalidades

- CRUD de produtos (10 atributos: nome, preço, marca, modelo, processador, memória RAM, armazenamento, cor, voltagem e garantia)
- CRUD de adicionais (decorators): frete, garantia, brinde e personalização
- Criação de pedidos com vários itens e cálculo automático do total
- Listagem de pedidos com detalhamento de itens, garantias e adicionais

## Tecnologias

- Java 17
- Servlets e JSP (Jakarta EE 10)
- MySQL 8 + JDBC (MySQL Connector/J 9.2.0)
- Apache Tomcat 10.1
- NetBeans (projeto Ant)

## Arquitetura e padrões de projeto

- **Decorator:** `PedidoBase`, `FreteDecorator`, `GarantiaDecorator` e `AdicionalDecorator` somam os adicionais ao valor do pedido
- **Builder:** `Produto` e `Pedido`
- **Factory:** `DAOFactory` e `ServiceFactory`
- **DAO:** acesso ao banco separado da regra de negócio
- **Camadas:** controller (servlets), service, DAO e model

## Como rodar

### Pré-requisitos

- JDK 17 ou superior
- MySQL Server
- Apache Tomcat **10.1** (o projeto usa `jakarta.servlet`, então não funciona no Tomcat 9)
- NetBeans (ou outra IDE com suporte a projetos web)
- [MySQL Connector/J 9.2.0](https://dev.mysql.com/downloads/connector/j/) (arquivo `.jar`)

### Passo a passo

1. **Banco de dados:** execute o script `web/WEB-INF/sql/gerenciador_pedidos.sql` no MySQL Workbench. Ele cria o banco `gerenciador__de_pedidos`, as tabelas e os adicionais iniciais.
2. **Credenciais:** em `src/java/util/ConnectionFactory.java`, ajuste `USER` e `PASSWORD` para os do seu MySQL.
3. **Driver:** adicione o `mysql-connector-j-9.2.0.jar` em *Libraries* do projeto.
4. **Servidor:** configure o Tomcat 10.1 no NetBeans (*Services → Servers*) e selecione-o em *Properties → Run*.
5. **Execução:** faça *Clean and Build* e *Run*. O sistema abre em `http://localhost:8080/`.

## Estrutura

```
src/java
├── controller   # Servlets
├── service      # Regras de negócio
├── dao          # Acesso ao banco
├── decorator    # Padrão Decorator
├── factory      # Factories
├── model        # Entidades
└── util         # ConnectionFactory
web                  # JSPs e configuração
web/WEB-INF/sql      # Script do banco
```

## Autor

Juan Victor Brito Zavanela
GitHub: [Juan-Zavanela](https://github.com/Juan-Zavanela)
