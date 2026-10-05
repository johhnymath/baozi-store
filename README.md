# Baozi Store - API REST

Java 17 + Spring Boot 3 + Spring Data JPA + H2.

## Como rodar
    mvn spring-boot:run
API em http://localhost:8080 | Console H2 em http://localhost:8080/h2-console
(JDBC URL: `jdbc:h2:mem:baozidb`, user `sa`, senha vazia)

## Endpoints (para /clientes, /produtos e /pedidos)
| Metodo | Rota | Descricao |
|---|---|---|
| POST | /recurso | criar |
| GET | /recurso | listar todos |
| GET | /recurso/{id} | consultar por ID |
| PUT | /recurso/{id} | atualizar (opcional) |
| DELETE | /recurso/{id} | apagar |

## Roteiro Postman (nesta ordem; Content-Type: application/json)
1. POST /clientes  -> {"nome": "Johnny4747056"}
2. POST /produtos  -> {"nome": "Baozi de Lótus", "preco": 8.50, "estoque": true}
3. POST /pedidos   -> {"cliente": {"id": 1}, "produto": {"id": 1}, "quantidade": 3}
4. GET /clientes, GET /produtos, GET /pedidos
5. GET /clientes/1, GET /produtos/1, GET /pedidos/1
6. DELETE /pedidos/1 (depois GET /pedidos para mostrar a lista vazia)
