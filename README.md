# XPTO Systems
Desafio das Cidades Sênior

* [Considerações](#considerações)
* [Desenvolvimento](#desenvolvimento)
* [Relação dos UF Brasileiros](#relacaodosufbrasileiros)
* [Pedidos & Respostas](#pedidos--respostas)
* [Comentários](#comentarios)


## Considerações

Este documento fornece informações e exemplos para as API do desafio da Sênior.

* [Url da API](http://localhost:80/api/)

## Desenvolvimento

Para construir esse projeto utilizei as dependências disponibilizadas no site oficial do Spring, o desenvolvimento foi feito usando as IDES Spring Tools Suite e IntelliJ IDEA

* [Spring](https://start.spring.io)

* JPA, REST, H2, Jackson, JSON e WEB

A base de dados foi usada [H2](http://www.h2database.com) (banco de dados em memória) para facilitar a construção (building) e permitir um teste mais rápido com menos configurações.

* [H2 Console](http://localhost/api/h2-console) ou [8080](http://localhost:8080/api/h2-console/)

### H2 Configuração
    Driver Class: org.h2.Driver
    JDBC URL: jdbc:h2:mem:testdb
    User Name: sa
    Password:

O [Tomcat](https://tomcat.apache.org/download-80.cgi#8.5.37) (Versão 8.044) foi usado como o container da aplicação.

Depois da primeira execução as entidades serão criadas no banco de dados e o arquivo cities.csv será exportado para o banco de dados.

## Relações dos UF Brasileiros

- [AC, AL, AM ,AP, BA, CE, DF, ES, GO, MA, MG, MS, MT, PA, PB, PE, PI, PR, RJ, RN, RO, RR, RS, SC, SE, SP, TO]

## Pedidos & Respostas

### API Recursos

  - [GET /status](#get-status) 
  - [POST /city/upload](#post-cityupload)  
  - [GET /city/find/capitals](#get-cityfindcapitals)
  - [GET /city/stats/min_max_uf](#get-min_max_uf)
  - [GET /city/stats/count/cities/uf/[uf]](#get-citystatscountcitiesufuf)
  - [GET /city/find/ibge_id/[ibge_id]](#get-findibgeidibgeid)
  - [GET /city/find/uf/[uf]](#get-cityfindufuf)
  - [POST /city](#post)
  - [DELETE /city/delete/ibge_id/[ibge_id]](#delete-citydeleteibgeidibgeid)  
  - [GET /city/find/column/[column]/query/[query]](#get-cityfindcolumncolumnqueryquery)
  - [GET /city/stats/count/column/[column]](#get-/citystatscountcolumncolumn)  
  - [GET /city/stats/total](#get-citystatstotal)

### Extras

  - [GET /city/find/id/[id]](#get-findidid)
  - [PUT /city](#put)
  - [DELETE /city/[id]](#delete-cityid)

### GET /status

Exemplo: http://localhost/api/status

Resposta:

    [200 - OK]

### POST /city/upload

 - Permite o upload de arquivo CSV das cidades para a base de dados; 

Exemplo: http://localhost/api/city/upload

Teste via Postman: 

Content-Type:

    form-data

Charset:
    
    UTF-8

Param:
    
    key: csv (type file)
    value: select file (procurar o local do arquivo)

### GET /city/find/capitals

 - Retornar somente as cidades que são capitais ordenadas por nome

Exemplo: http://localhost/api/city/find/capitals

Resposta:
    [
        {
            "id": null,
            "ibge_id": null,
            "uf": {
                "id": null,
                "name": "UF"
            },
            "name": "city name",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "noAccents": "city name no accents",
            "alternativeNames": '''',
            "microregions": {
                "id": null,
                "name": ''microregion name''
            },
            "mesoregions": {
                "id": null,
                "name": "mesoregion name"
            },
            "createdAt": "1900-01-01T00:00:00.000+0000",
            "ufName": null,
            "microregionName": null,
            "mesoregionName": null
        },
        {
            "id": null,
            "ibge_id": null,
            "uf": {
                "id": null,
                "name": "UF"
            },
            "name": "city name 2",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "noAccents": "city name 2 no accents",
            "alternativeNames": '''',
            "microregions": {
                "id": null,
                "name": ''microregion name''
            },
            "mesoregions": {
                "id": null,
                "name": "mesoregion name"
            },
            "createdAt": "1900-01-01T00:00:00.000+0000",
            "ufName": null,
            "microregionName": null,
            "mesoregionName": null
        }
    ]


### GET /city/stats/min_max_uf

 - Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades

Exemplo: http://localhost/api//city/stats/min_max_uf

Resposta:
    [
        [
            "bigger",
            "RR",
            15
        ],
        [
            "smaller",
            "PI",
            224
        ]
    ] 

### GET /city/stats/count/cities/uf/[uf]

 - Retornar a quantidade de cidades por estado; 

Exemplo: http://localhost/api/city/stats/count/cities/uf/ac

Resposta:

    {
        "uf": "AC",
        "total": 22
    } 

### GET /city/find/ibge_id/[ibge_id]

 - Obter os dados da cidade informando o id do IBGE

Exemplo: http://localhost/api/city/find/ibge_id/1234567

Resposta:

    {
        "id": null,
        "ibge_id": null,
        "uf": {
            "id": null,
            "name": "UF"
        },
        "name": "city name 2",
        "capital": true,
        "lon": 0,
        "lat": 0,
        "noAccents": "city name 2 no accents",
        "alternativeNames": '''',
        "microregions": {
            "id": null,
            "name": ''microregion name''
        },
        "mesoregions": {
            "id": null,
            "name": "mesoregion name"
        },
        "createdAt": "1900-01-01T00:00:00.000+0000",
        "ufName": null,
        "microregionName": null,
        "mesoregionName": null
    }
    
### GET /city/find/uf/[uf]

 - Retornar o nome das cidades baseado em um estado selecionado

Example: http://localhost/api/city/find/uf/sp

Resposta:

    [
         {
            "id": null,
            "ibge_id": null,
            "uf": {
                "id": null,
                "name": "UF"
            },
            "name": "city name 2",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "noAccents": "city name 2 no accents",
            "alternativeNames": '''',
            "microregions": {
                "id": null,
                "name": ''microregion name''
            },
            "mesoregions": {
                "id": null,
                "name": "mesoregion name"
            },
            "createdAt": "1900-01-01T00:00:00.000+0000",
            "ufName": null,
            "microregionName": null,
            "mesoregionName": null
        },
        {
            "id": null,
            "ibge_id": null,
            "uf": {
                "id": null,
                "name": "UF"
            },
            "name": "city name 2",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "noAccents": "city name 2 no accents",
            "alternativeNames": '''',
            "microregions": {
                "id": null,
                "name": ''microregion name''
            },
            "mesoregions": {
                "id": null,
                "name": "mesoregion name"
            },
            "createdAt": "1900-01-01T00:00:00.000+0000",
            "ufName": null,
            "microregionName": null,
            "mesoregionName": null
        }
    ]    

### POST /city

 - Permitir adicionar uma nova Cidade
 
* Teste feito pela ferramenta de testes Postman, formato de envio usado foi raw tipo application/json

Example: http://localhost/api/city

Resposta:

    {
        "id": null,
        "ibge_id": 1234567,
        "uf": {
            "id": 5,
            "name": "PA"
        },
        "name": "parará",
        "capital": false,
        "lon": 0,
        "lat": 0,
        "noAccents": "parara",
        "alternativeNames": "parararara",
        "createdAt": "1900-01-01T00:00:00.000+0000",
        "ufName": null,
        "microregionName": "microregion do parara name",
        "mesoregionName": "mesoregion do parara"
    }
    
 ### DELETE /city/delete/ibge_id/[ibge_id]

 - Permitir deletar uma cidade
 
 Example: http://localhost/api/city/delete/ibge_id/1234567

Resposta (Status Code 200):

    {
        "status_code": 1,
        "status": "success: city nº 1234567 removed",
        "result": null
    }

Resposta (Status Code 404) (Com erro) e o apresenta a exceção gerada pela api. Se status_code = 0 identifica que houve algum erro:

    {
        "status_code": 0,
        "status": "empty city!",
        "result": null
    }

### GET /city/find/column/[column]/query/[query]

 - Permitir selecionar uma coluna (do CSV) e através dela entrar com uma string para filtrar. retornar assim todos os objetos que contenham tal string

Example: http://localhost/api/city/find/column/uf/query/sp


Content-Type:

    x-www-form-urlencoded

Charset:
    
    UTF-8

Param:
    
    key: albums_id
    value: [1,2,3] 

Resposta (200 - OK):

    {
        "status_code": 1,
        "status": "success: order nº 2 registered",
        "result": {
            "total": 7.23,
            "total_cashback": 2.17,
            "ordersItems": [
                {
                    "id": 4,
                    "order": {
                        "id": 2,
                        "user": {
                            "id": 1,
                            "name": "admin",
                            "login": "admin",
                            "created_at": 1549721442702,
                            "hibernateLazyInitializer": {}
                        },
                        "created_at": 1549727996559
                    },
                    "album": {
                        "id": 4,
                        "spotify_id": "1X42b0NEC8OPNnPKcJJgIY",
                        "name": "10 Anos Depois",
                        "artist": {
                            "id": 1,
                            "spotify_id": "3SKTkAUNa3oUa2rkd8DAyM",
                            "name": "MPB4"
                        },
                        "genre": {
                            "id": 80,
                            "name": "mpb"
                        },
                        "price": 7.23,
                        "created_at": 1549645694118
                    },
                    "cashback_percent_log": 30,
                    "cashback": 2.17,
                    "cost": 7.23,
                    "created_at": 1549727996586
                }
            ]
        }
    }
 
### DELETE /order/delete]/[id]

 - Excluir uma ordem de venda

Exemplo: http://localhost/api/order/delete/[id]

Resposta:

    {
        "status_code": 1,
        "status": "success: order nº 1 removed",
        "result": null
    }

 
### DELETE /order/item/delete]/[id]

 - Excluir item de uma ordem de venda

Exemplo: http://localhost/api/order/item/delete/[id]

Resposta:

    {
        "status_code": 1,
        "status": "success: item nº 1 removed",
        "result": null
    }
## Comentário:
*Espero ter atingido o objetivo no teste, fiz uma documentação mais completa para facilitar o entendimento, quaisquer dúvidas estou a disposição.*
