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
  - [GET /city/stats/min_max_uf](#get-citystatsmin_max_uf)
  - [GET /city/stats/count/cities/uf/[uf]](#get-citystatscountcitiesufuf)
  - [GET /city/find/ibge_id/[ibge_id]](#get-cityfindibge_idibge_id)
  - [GET /city/find/uf/[uf]](#get-cityfindufuf)
  - [POST /city](#post-city)
  - [DELETE /city/delete/ibge_id/[ibge_id]](#delete-citydeleteibge_idibge_id)  
  - [GET /city/find/column/[column]/query/[query]](#get-cityfindcolumncolumnqueryquery)
  - [GET /city/stats/count/column/[column]](#get-citystatscountcolumncolumn)  
  - [GET /city/stats/total](#get-citystatstotal)

### Extras

  - [GET /city/find/id/[id]](#get-findidid)
  - [PUT /city](#put-city)
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

Resposta (Http Status 200):

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

Resposta (Http Status 200):

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

Resposta (Http Status 200):

    {
        "uf": "AC",
        "total": 22
    } 

### GET /city/find/ibge_id/[ibge_id]

 - Obter os dados da cidade informando o id do IBGE

Exemplo: http://localhost/api/city/find/ibge_id/1234567

Resposta (Http Status 200):

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

Resposta (Http Status 200):

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
 
 * Se não existir estado, microregion ou mesoregion, serão adicionadas, porém devem ser enviadas pelos parâmetros
 
    - mesoregion_name: valor
    - microregion_name: valor
    - uf_name: valor
 
* Teste feito pela ferramenta de testes Postman, formato de envio usado foi raw tipo application/json

Example: http://localhost/api/city

Content-Type:

    application/json

Charset:
    
    UTF-8

Raw:
    
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

Resposta (Http Status 200):

    {
        "status_code": 1,
        "status": "success: city nº 1004 registered",
        "result": {
            "id": 1004,
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
            "microregions": {
                "id": 133,
                "name": "microregion do parara name"
            },
            "mesoregions": {
                "id": 43,
                "name": "mesoregion do parara"
            },
            "createdAt": "1900-01-01T00:00:00.000+0000",
            "ufName": null,
            "microregionName": "microregion do parara name",
            "mesoregionName": "mesoregion do parara"
        }
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
 
- Column [opções de colunas]: ibge_id, uf, name, lon, lat, no_accents, alternative_names, microregion e mesoregion

Example: http://localhost/api/city/find/column/uf/query/sp

Resposta (200 - OK):

    [
        {
            "id": 152,
            "ibge_id": 1500107,
            "uf": {
                "id": 5,
                "name": "PA"
            },
            "name": "Abaetetuba",
            "capital": false,
            "lon": -48.8844038207,
            "lat": -1.7234698628,
            "noAccents": "Abaetetuba",
            "alternativeNames": "",
            "microregions": {
                "id": 31,
                "name": "Camet"
            },
            "mesoregions": {
                "id": 11,
                "name": "Nordeste Paraense"
            },
            "createdAt": "2019-03-11T01:18:55.621+0000",
            "ufName": null,
            "microregionName": null,
            "mesoregionName": null
        },
        {
            "id": 153,
            "ibge_id": 1500131,
            "uf": {
                "id": 5,
                "name": "PA"
            },
            "name": "Abel Figueiredo",
            "capital": false,
            "lon": -48.3967621258,
            "lat": -4.9513908955,
            "noAccents": "Abel Figueiredo",
            "alternativeNames": "",
            "microregions": {
                "id": 32,
                "name": "Paragominas"
            },
            "mesoregions": {
                "id": 12,
                "name": "Sudeste Paraense"
            },
            "createdAt": "2019-03-11T01:18:55.650+0000",
            "ufName": null,
            "microregionName": null,
            "mesoregionName": null
        }
    ]    
    
### GET /city/stats/count/column/[column]

- Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens iguais 

* Column [opções de colunas]: ibge_id, uf, name, no_accents, alternative_names, microregion e mesoregion

Resposta (Status Code 200):

    {
        "column": "uf",
        "total": 10
    }
    
### GET /city/stats/total  

- Retornar a quantidade de registros total

Resposta (Status Code 200):

{
    "total": 1003
}

### DELETE /city/delete/id/[id]

 - Permitir deletar uma cidade pelo id
 
 Example: http://localhost/api/city/delete/id/1

Resposta (Status Code 200):

    {
        "status_code": 1,
        "status": "success: city nº 1 removed",
        "result": null
    }

Resposta (Status Code 404) (Com erro) e o apresenta a exceção gerada pela api. Se status_code = 0 identifica que houve algum erro:

    {
        "status_code": 0,
        "status": "empty city!",
        "result": null
    }
   
## Comentário:

* As respostas de erro virão usando o status 404.

* Espero ter atingido o objetivo no teste, fiz uma documentação mais completa para facilitar o entendimento, quaisquer dúvidas estou a disposição.*
