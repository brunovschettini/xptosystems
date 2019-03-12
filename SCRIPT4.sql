-- Folha de respostas
-- Nome: Bruno Vieira Schettini da Silva
-- Data: 11/03/2019
-- Parte 3 – PL/SQL Prático
-- Exercício 4.
-- Crie uma consulta SELECT que exiba o total de linhas da tabela EXAME_NF por dia. 
-- Exiba somente as linhas que possuam ao menos um item (EXAME_ITEMNF) com valor 
-- inferior a 50. Salve a consulta no arquivo SCRIPT4.SQL.

SELECT NF.DATACADASTRO, COUNT(*)  AS TOTAL_LINHAS
FROM EXAME_NF NF
INNER JOIN EXAME_ITEMNF ITEMNF ON ITEMNF.IDNF = NF.IDNF
WHERE ITEMNF.VALOR < 50
GROUP BY NF.DATACADASTRO
ORDER BY NF.DATACADASTRO;

/*

Esse resultado foi referente a um teste que fiz no site para teste da orale
Local do teste: https://livesql.oracle.com

DATACADASTRO	TOTAL_LINHAS
02-MAR-19	16
03-MAR-19	18
04-MAR-19	12
05-MAR-19	8
06-MAR-19	8
07-MAR-19	15
08-MAR-19	10
09-MAR-19	14
10-MAR-19	18
11-MAR-19	18
*/