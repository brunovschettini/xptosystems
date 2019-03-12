-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019
-- Parte 3 – PL/SQL Prático
-- Exercício 3.
-- Crie uma procedure chamada DEFINIR_VALORES para que altere o valor dos itens 
-- da tabela EXAME_ITEMNF, definindo números inteiros aleatórios entre 1 e 100. 
-- Faça com que a linha da tabela EXAME_NF receba o valor da somatória de seus 
-- itens na coluna TOTALGERAL. Salve o fonte do script no arquivo SCRIPT3.SQL.

CREATE OR REPLACE FUNCTION DEFINIR_VALORES()
  [(parameter_name [IN | OUT | IN OUT] type [, ...])]
  RETURN return_datatype
  {IS | AS}
  BEGIN
     < function_body >
  END [function_name];