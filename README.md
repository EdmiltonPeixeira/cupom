# Cupom Service
Sistema de gerenciamento de cupons desenvolvido em **Spring Boot 3.x** com **Java 17**.  
Este projeto pode ser executado via **Docker** e possui **Swagger** para documentação da API.

## Estrutura do projeto
```
cupom/
├─ src/ # Código fonte Java
├─ target/ # Artefatos compilados
├─ postman/ # Collection do Postman
├─ pom.xml # Arquivo de configuração de dependências maven
├─ Dockerfile # Dockerfile para build e execução
└─ README.md
```

## Pré-requisitos
- Java 17
- Maven
- Docker
- Postman (opcional, para testar a API)
- Navegador para Swagger UI

## Criar a imagem Docker
No diretório raiz do projeto (onde está o Dockerfile):
docker build -t cupom-service:1.0 .

## Rodar o container Docker
docker run -p 8080:8080 --name cupom-container cupom-service:1.0

## Acessar Swagger UI
O projeto utiliza Springdoc OpenAPI, então a documentação Swagger fica disponível em: http://localhost:8080/swagger-ui.html

## Testar com Postman
Se você tiver a collection do Postman:

Importe o arquivo postman/CupomCollection.postman_collection.json

Configure a URL base: http://localhost:8080

Teste os endpoints conforme necessário.

## Autor
Edmilton Ribeiro Peixeira
