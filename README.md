# XPTO Systems

Desafio das Cidades

* [Considerações](#considerações)
* [Desenvolvimento](#desenvolvimento)
* [Parte 1 – SQL Conceitual](https://github.com/ilines/xptosystems/blob/master/RESPOSTAS.md#folha-de-respostas)
* [Parte 2 – SQL Prático](https://github.com/ilines/xptosystems/blob/master/RESPOSTAS.md#parte-2--sql-pr%C3%A1tico)
* [Parte 3 – SQL Prático](#parte-3--sql-prático)
* [Relação dos UF Brasileiros](#relacaodosufbrasileiros)
* [Pedidos & Respostas](#pedidos--respostas)
* [Comentários](#comentarios)


## Considerações

Este documento fornece informações e exemplos para as API do desafio.

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

## Parte 3 – SQL Prático

* [Excercício 1](https://github.com/ilines/xptosystems/blob/master/SCRIPT1.sql)
* [Excercício 2](https://github.com/ilines/xptosystems/blob/master/SCRIPT2.sql)
* [Excercício 3](https://github.com/ilines/xptosystems/blob/master/SCRIPT3.sql)
* [Excercício 4](https://github.com/ilines/xptosystems/blob/master/SCRIPT4.sql)
* [Excercício 5](https://github.com/ilines/xptosystems/blob/master/SCRIPT5.sql)
 

## Relações dos UF Brasileiros

- [AC, AL, AM ,AP, BA, CE, DF, ES, GO, MA, MG, MS, MT, PA, PB, PE, PI, PR, RJ, RN, RO, RR, RS, SC, SE, SP, TO]

## Pedidos & Respostas

### API Recursos

  - [GET /status](#get-status) 
  - [POST /city/readCsv](#post-readCsv)  
  - [GET /city/capital](#get-citycapital)
  - [GET /city/statesBiggerAndSmallerNumberOfCities](#get-citystatesBiggerAndSmallerNumberOfCities)
  - [GET /city/numberOfCitiesByState/[stateName]](#get-citynumberOfCitiesByStatestatename)
  - [GET /city/findByIbgeId/[ibgeId]](#get-cityfindByIbgeIdIbgeId)
  - [GET /city/findByState/[stateName]](#get-cityfindbystatestatename)
  - [POST /city](#post-city)
  - [DELETE /city/[ibge_id]](#delete-cityibge_id)  
  - [GET /city/find/column/[column]/query/[query]](#get-cityfindcolumncolumnqueryquery)
  - [GET /city/stats/count/column/[column]](#get-citystatscountcolumncolumn)  
  - [GET /city/stats/total](#get-citystatstotal)
  - [GET /city/find/two_most_distant](#get-cityfindtwo_most_distant)

### Extras

  - [PUT /city](#put-city)
  - [GET /city/[id]](#get-cityid)

### GET /status

Exemplo: http://localhost/api/status

Resposta:

    [200 - OK]

### POST /city/readCsv

 - Permite o upload de arquivo CSV das cidades para a base de dados; 

Exemplo: http://localhost/api/city/readCsv

Teste via Postman: 

Content-Type:

    form-data

Charset:
    
    UTF-8

Param:
    
    key: csv (type file)
    value: select file (procurar o local do arquivo)

### GET /city/capital

 - Retornar somente as cidades que são capitais ordenadas por nome

Exemplo: http://localhost/api/city/capital

Resposta (Http Status 200):

    [
        {
            "id": null,
            "ibgeId": null,
            "uf": {
                "id": null,
                "name": "UF"
            },
            "name": "city name",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "locationPoint": {
                "x": 0,
                "y": 0
            },
            "noAccents": "city name no accents",
            "alternativeNames": "",
            "microregions": {
                "id": null,
                "name": "microregion name"
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
            "ibgeId": null,
            "uf": {
                "id": null,
                "name": "UF"
            },
            "name": "city name 2",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "locationPoint": {
                "x": 0,
                "y": 0
            },            
            "noAccents": "city name 2 no accents",
            "alternativeNames": "",
            "microregions": {
                "id": null,
                "name": "microregion name"
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


### GET /city/statesBiggerAndSmallerNumberOfCities

 - Retornar o nome do estado com a maior e menor quantidade de cidades e a quantidade de cidades

Exemplo: http://localhost/api//city/statesBiggerAndSmallerNumberOfCities

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

### GET /city/numberOfCitiesByState/[stateName]

 - Retornar a quantidade de cidades por estado; 

Exemplo: http://localhost/api/city/numberOfCitiesByState/[stateName]

Resposta (Http Status 200):

    {
        "state": "AC",
        "total": 22
    } 

### GET /city/findByIbgeId/[ibgeId]

 - Obter os dados da cidade informando o id do IBGE

Exemplo: http://localhost/api/city/findByIbgeId/[ibgeId]

Resposta (Http Status 200):

    {
        "id": null,
        "ibgeId": null,
        "state": {
            "id": null,
            "name": "UF"
        },
        "name": "city name 2",
        "capital": true,
        "lon": 0,
        "lat": 0,
        "locationPoint": {
            "x": 0,
            "y": 0
        },        
        "noAccents": "city name 2 no accents",
        "alternativeNames": "",
        "microregions": {
            "id": null,
            "name": "microregion name"
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
    
### GET /city/findByState/[stateName]

 - Retornar o nome das cidades baseado em um estado selecionado

Example: http://localhost/api/city/findByState/[stateName]

Resposta (Http Status 200):

    [
         {
            "id": null,
            "ibgeId": null,
            "state": {
                "id": null,
                "name": "UF"
            },
            "name": "city name 2",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "locationPoint": {
                "x": 0,
                "y": 0
            },            
            "noAccents": "city name 2 no accents",
            "alternativeNames": "",
            "microregions": {
                "id": null,
                "name": "microregion name"
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
            "ibgeId": null,
            "state": {
                "id": null,
                "name": "UF"
            },
            "name": "city name 2",
            "capital": true,
            "lon": 0,
            "lat": 0,
            "locationPoint": {
                "x": 0,
                "y": 0
            },            
            "noAccents": "city name 2 no accents",
            "alternativeNames": "",
            "microregions": {
                "id": null,
                "name": "microregion name"
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
        "ibgeId": 1234567,
        "state": {
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


Content-Type:

    application/json

Resposta (Http Status 200):

    {
        "status_code": 1,
        "status": "success: city nº 1004 registered",
        "result": {
            "id": 1004,
            "ibgeId": 1234567,
            "state": {
                "id": 5,
                "name": "PA"
            },
            "name": "parará",
            "capital": false,
            "lon": 0,
            "lat": 0,
            "locationPoint": {
                "x": 0,
                "y": 0
            },            
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
    
 ### DELETE /city/deleteByIbgeId[ibgeId]

 - Permitir deletar uma cidade pelo id do IBGE
 
 Example: http://localhost/api/city/deleteByIbgeId/[ibgeId]
 
Content-Type:

    application/json 

Resposta (Status Code 200):

    {
        "status_code": 1,
        "status": "success: city nº 1234567 removed",
        "result": null
    }

Resposta (Status Code 404) (Com erro) e o apresenta a exceção gerada pela api. Se status_code = 0 identifica que houve algum erro:

    {
        "status": 404,
        "message": "empty city!",
        "timestamp": "2019-03-11T15:30:58.316+0000"
    }
    
    
 ### DELETE /city/[id]

 - Permitir deletar uma cidade
 
 Example: http://localhost/api/city/[id]
 
Content-Type:

    application/json 

Resposta (Status Code 200):

    {
        "status_code": 1,
        "status": "success: city nº 1234567 removed",
        "result": null
    }

Resposta (Status Code 404) (Com erro) e o apresenta a exceção gerada pela api. Se status_code = 0 identifica que houve algum erro:

    {
        "status": 404,
        "message": "empty city!",
        "timestamp": "2019-03-11T15:30:58.316+0000"
    }



### GET /city/findByColumn/[column]/[filter]

 - Permitir selecionar uma coluna (do CSV) e através dela entrar com uma string para filtrar. retornar assim todos os objetos que contenham tal string
 
* OPTIONS

| COLUMN             | TYPE        | EXAMPLE     |
| ------------------ | ----------- | ---------   |
| ibge_id            | Long        | 1234567     |
| state              | String      | SP          |
| no_accents         | String      | rio         |
| alternative_names  | String      | salvador    |
| microregion        | String      | jequitin    |
| mesoregion         | String      | vale        |

Example: http://localhost/api/city/findByColumn/[column]/[filter]

Content-Type:

    application/json

Resposta (200 - OK):

    [
        {
            "id": 152,
            "ibgeId": 1500107,
            "state": {
                "id": 5,
                "name": "PA"
            },
            "name": "Abaetetuba",
            "capital": false,
            "lon": -48.8844038207,
            "lat": -1.7234698628,
            "locationPoint": {
                "x": -1.7234698628,
                "y": -48.8844038207
            },
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
            "ibgeId": 1500131,
            "state": {
                "id": 5,
                "name": "PA"
            },
            "name": "Abel Figueiredo",
            "capital": false,
            "lon": -48.3967621258,
            "lat": -4.9513908955,
            "locationPoint": {
                "x": -4.9513908955,
                "y": -48.3967621258
            },
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
    
### GET /city/countByColumn/[column]

- Retornar a quantidade de registro baseado em uma coluna. Não deve contar itens iguais 

Example: http://localhost/api/city/countByColumn/[column]

* Column [opções de colunas]: ibge_id, state, name, no_accents, alternative_names, microregion e mesoregion

Content-Type:

    application/json

Resposta (Status Code 200):

    {
        "column": "state",
        "total": 0
    }
    
### GET /city/countAll

- Retornar a quantidade de registros total

Example: http://localhost/api/city/findByColumn/[column]/[filter]

Content-Type:

    application/json

Resposta (Status Code 200):

    {
        "total": 1003
    }

### GET /city/find/findTwoDistanceCities

- Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base na localização (distância em KM em linha reta)

Example: http://localhost/api/city/findTwoDistanceCities

Content-Type:

    application/json

Resposta (Status Code 200): 

    {
        "distance": 0,
        "from": "city a",
        "to": "city b",
        "factor": "km"
    }

### GET /city/[id]

 - Obter os dados da cidade informando o id cadastrado na base de dados

Exemplo: http://localhost/api/city/[id]

Content-Type:

    application/json

Resposta (Http Status 200):

    {
        "id": 1,
        "ibgeId": 1234567,
        "state": {
            "id": 1,
            "name": "UF"
        },
        "name": "city name 2",
        "capital": true,
        "lon": 0,
        "lat": 0,
        "locationPoint": {
            "x": 0,
            "y": 0
        },        
        "noAccents": "city name 2 no accents",
        "alternativeNames": "",
        "microregions": {
            "id": 1,
            "name": "microregion name"
        },
        "mesoregions": {
            "id": 1,
            "name": "mesoregion name"
        },
        "createdAt": "1900-01-01T00:00:00.000+0000",
        "ufName": 1,
        "microregionName": null,
        "mesoregionName": null
    }
   
## Comentário:

* As respostas de erro virão usando o status 404.

* Espero ter atingido o objetivo no teste, fiz uma documentação mais completa para facilitar o entendimento, quaisquer dúvidas estou a disposição.*
