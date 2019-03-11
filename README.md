# XPTO Systems
Desafio das Cidades Sênior

* [Considerações](#considerações)
* [Desenvolvimento](#desenvolvimento)
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

Resposta:

    [
        {
            "id": 1,
            "name": "classic"
        },
        {
            "id": 2,
            "name": "acoustic"
        },
        {
            "id": 3,
            "name": "afrobeat"
        },
        {
            "id": 4,
            "name": "alt-rock"
        }
    ]
 

### GET /album/genre/[genre]/[offset]

 - Consultar o catálogo de discos de forma paginada, filtrando por gênero e ordenando de forma crescente pelo nome do disco

Exemplo: http://localhost/api/album/genre/[genre]/[offset]

Resposta:

    [
        {
            "id": 1,
            "spotify_id": "id do album no spotify",
            "name": "Nome do album",
            "artist": {
                "id": null,
                "spotify_id": "id do artista no spotify",
                "name": "Nome do artista"
            },
            "genre": {
                "id": null,
                "name": "Nome do gênero"
            },
            "price": 0.00,
            "created_at": now()
        },
        {
            "id": 2,
            "spotify_id": "id do album no spotify",
            "name": "Nome do album",
            "artist": {
                "id": null,
                "spotify_id": "id do artista no spotify",
                "name": "Nome do artista"
            },
            "genre": {
                "id": null,
                "name": "Nome do gênero"
            },
            "price": 0.00,
            "created_at": now()
        }
    ]


### GET /album/id/[id]

 - Consultar o disco pelo seu identificador

Exemplo: http://localhost/api/album/id/[id]

Resposta:

    {
        "id": null,
        "spotify_id": "id do album no spotify",
        "name": "Nome do album",
        "artist": {
            "id": null,
            "spotify_id": "id do artista no spotify",
            "name": "Nome do artista"
        },
        "genre": {
            "id": null,
            "name": "Nome do gênero"
        },
        "price": 0.00,
        "created_at": now()
    } 

### GET /album/spotify_id/[spotify_id]

 - Consultar o disco pelo identificador do spotify

Exemplo: http://localhost/api/album/id/[id]

Resposta:

    {
        "id": null,
        "spotify_id": "id do album no spotify",
        "name": "Nome do album",
        "artist": {
            "id": null,
            "spotify_id": "id do artista no spotify",
            "name": "Nome do artista"
        },
        "genre": {
            "id": null,
            "name": "Nome do gênero"
        },
        "price": 0.00,
        "created_at": now()
    } 

### GET /order/find/

 - Consultar todas as vendas efetuadas

Exemplo: http://localhost/api/order/find/

Resposta:

    {
        "status_code": 1,
        "status": "info: list orders by range date",
        "result": {
            "total": 0,
            "total_cashback": 0,
            "ordersItems": []
        }
    }

Com resultados

Resposta:

    {
        "status_code": 1,
        "status": "info: list orders by range date",
        "result": {
            "total": 85.15,
            "total_cashback": 25.54,
            "ordersItems": [
                {
                    "id": 3,
                    "order": {
                        "id": 1,
                        "user": {
                            "id": 1,
                            "name": "admin",
                            "login": "admin",
                            "created_at": 1549721442702
                        },
                        "created_at": 1549677600000
                    },
                    "album": {
                        "id": 3,
                        "spotify_id": "3wvoawKuMJw5ROGw92BS4X",
                        "name": "Grandes mestres da MPB",
                        "artist": {
                            "id": 3,
                            "spotify_id": "5JYtpnUKxAzXfHEYpOeeit",
                            "name": "Jorge Ben Jor"
                        },
                        "genre": {
                            "id": 80,
                            "name": "mpb"
                        },
                        "price": 85.15,
                        "created_at": 1549721443468
                    },
                    "cashback_percent_log": 30,
                    "cashback": 25.54,
                    "cost": 85.15,
                    "created_at": 1549677600000
                }
        }
    }

### GET /order/find/[query]

 - Consultar todas as vendas efetuadas de forma paginada, filtrando pelo range de datas (inicial e final) da venda e ordenando de forma decrescente pela data da venda;

Example: http://localhost/api/order/find/{"start_date":"01-01-1900","end_date":"01-01-1900"}

| PARAMETER   | TYPE      | FORMAT      |
| ----------- | --------- | ----------- |
| start_date  | String    | 01-01-1900  |
| end_date    | String    | 01-01-1900  |

Resposta:

    {
        "status_code": 1,
        "status": "info: list orders by range date",
        "result": {
            "total": 85.15,
            "total_cashback": 25.54,
            "ordersItems": [
                {
                    "id": 3,
                    "order": {
                        "id": 1,
                        "user": {
                            "id": 1,
                            "name": "admin",
                            "login": "admin",
                            "created_at": 1549721442702
                        },
                        "created_at": 1549677600000
                    },
                    "album": {
                        "id": 3,
                        "spotify_id": "3wvoawKuMJw5ROGw92BS4X",
                        "name": "Grandes mestres da MPB",
                        "artist": {
                            "id": 3,
                            "spotify_id": "5JYtpnUKxAzXfHEYpOeeit",
                            "name": "Jorge Ben Jor"
                        },
                        "genre": {
                            "id": 80,
                            "name": "mpb"
                        },
                        "price": 85.15,
                        "created_at": 1549721443468
                    },
                    "cashback_percent_log": 30,
                    "cashback": 25.54,
                    "cost": 85.15,
                    "created_at": 1549677600000
                }
            ]
        }
    }
 

### GET /order/id/[id]

 - Consultar uma venda pelo seu identificador

Example: http://localhost/api/order/id/[id]

Resposta:

    {
        "status_code": 1,
        "status": "info: order nº 1",
        "result": {
            "total": 234.31,
            "total_cashback": 70.28,
            "ordersItems": [
                {
                    "id": 1,
                    "order": {
                        "id": 1,
                        "user": {
                            "id": 1,
                            "name": "admin",
                            "login": "admin",
                            "created_at": 1549721442702
                        },
                        "created_at": 1549677600000
                    },
                    "album": {
                        "id": 1,
                        "spotify_id": "4gjq4aTa0Y4rbxCG5J4bSy",
                        "name": "O Sonho, a Vida, a Roda Viva! 50 Anos (Ao Vivo)",
                        "artist": {
                            "id": 1,
                            "spotify_id": "3SKTkAUNa3oUa2rkd8DAyM",
                            "name": "MPB4"
                        },
                        "genre": {
                            "id": 80,
                            "name": "mpb"
                        },
                        "price": 67.61,
                        "created_at": 1549721443376
                    },
                    "cashback_percent_log": 30,
                    "cashback": 20.28,
                    "cost": 67.61,
                    "created_at": 1549677600000
                },
                {
                    "id": 2,
                    "order": {
                        "id": 1,
                        "user": {
                            "id": 1,
                            "name": "admin",
                            "login": "admin",
                            "created_at": 1549721442702
                        },
                        "created_at": 1549677600000
                    },
                    "album": {
                        "id": 2,
                        "spotify_id": "26Vd2zx3iCZVRHoCalDqXF",
                        "name": "The Best of Brazilian MPB",
                        "artist": {
                            "id": 2,
                            "spotify_id": "0LyfQWJT6nXafLPZqxe9Of",
                            "name": "Various Artists"
                        },
                        "genre": {
                            "id": 80,
                            "name": "mpb"
                        },
                        "price": 81.55,
                        "created_at": 1549721443460
                    },
                    "cashback_percent_log": 30,
                    "cashback": 24.46,
                    "cost": 81.55,
                    "created_at": 1549677600000
                },
                {
                    "id": 3,
                    "order": {
                        "id": 1,
                        "user": {
                            "id": 1,
                            "name": "admin",
                            "login": "admin",
                            "created_at": 1549721442702
                        },
                        "created_at": 1549677600000
                    },
                    "album": {
                        "id": 3,
                        "spotify_id": "3wvoawKuMJw5ROGw92BS4X",
                        "name": "Grandes mestres da MPB",
                        "artist": {
                            "id": 3,
                            "spotify_id": "5JYtpnUKxAzXfHEYpOeeit",
                            "name": "Jorge Ben Jor"
                        },
                        "genre": {
                            "id": 80,
                            "name": "mpb"
                        },
                        "price": 85.15,
                        "created_at": 1549721443468
                    },
                    "cashback_percent_log": 30,
                    "cashback": 25.54,
                    "cost": 85.15,
                    "created_at": 1549677600000
                }
            ]
        }
    }
 
### POST /order/add

 - Registrar uma nova venda de discos calculando o valor total de cashback considerando a tabela, para meus testes usei o POSTMAN

Content-Type:

    application/json

Charset:
    
    UTF-8

POST (raw format JSON):

    [
        {
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
        }
    ]

Resposta (Com erro) e o apresenta a exceção gerada pela api. Se status_code = 0 identifica que houve algum erro:

    {
        "status_code": 0,
        "status": "e->org.hibernate.exception.ConstraintViolationException: could not execute statement",
        "result": null
    }

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

### POST /order/add2

 -  Cria uma order de venda com items de albums, esse formato usa um post simples enviado de um exemplo:

    < form method = " POST " action = " /order/add2 " >
        < input name = " albums " value = " [1, 2, 3] ">
        < input type = " submit " >
    < / form >

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
