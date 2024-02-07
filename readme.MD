<h1 align="center">
  E-COMMERCE SIMPLIFICADO
</h1>

Este projeto é um e-commerce simplificado desenvolvido em Java com o framework Spring. 
O sistema implementa camadas de segurança com autenticação 
e autorização utilizando JSON Web Tokens (JWT) através do Spring Security.
## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Json Web Token](https://jwt.io/)
- [MySQL](https://www.mysql.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [JUnit5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)



## Como Executar

- Clonar repositório git
```
git clone git@github.com:igorbarret0/e-commerce-simplified.git
```

- Construir o projeto:
```
./mvnw clean package
```


A API poderá ser acessada em [localhost:8080](http://localhost:8080).

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/)

- USER - 
```
POST /user - Criar usuário - permitAll
```

- BODY
```
{
    "name": "name",
    "login": "name@email.com",
    "password": "123456",
    "moneyAccount": 850.0
}
```

- AUTHENTICATION 
```
POST /auth - Fazer login - permitAll
```

- BODY
```
{
  "login": "login@email.com",
  "password": "password"
}
```

- CATEGORY
```
POST /api/category - Criar uma nova categoria - require authentication and role ADMIM
GET /api/category - Recuperar todas as categorias - permitAll
```

- BODY
```
{
  "name": "hardware"
}
```

- PRODUCT
```
POST /api/product - Criar uma novo produto - require authentication and role ADMIM
PUT /api/product - Atualizar um produto - require authentication and role ADMIM
GET /api/product - Recuperar todos os produtos - permitAll
GET /api/product/{id} - Recuperar produto pelo ID - permitAll
GET /api/product/category/{id} - Recuperar produtos pela categoria - permitAll
DELETE /api/product/{id} - Deletar um produto - require authentication and role ADMIN
```

- BODY
```
{
    "name": "Notebook",
    "description": "some description",
    "price": 750.00,
    "inventory": 100,
    "categoryId": 1
}
```

- SALES
```
POST /api/sales - Comprar produto - require authentication
```

- BODY
```
{
    "productId": 5,
    "buyerLogin": "igor@email.com"
}
```