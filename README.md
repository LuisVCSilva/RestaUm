# RestaUm

Pequeno trabalho de IA

Este programa é um solver do problema das 8 rainhas utilizando a estratégia de backtracking.

Implementado na IDE Netbeans; Scripts escritos em Java.

Problema: Dado uma chapa com (n * m) células e (n * m - 1) pedras com numeradas de 1 até (n * m - 1), disponha-as em sequência no tabuleiro, deixando apenas a última célula do tabuleiro livre. As operações válidas são a movimentação de uma pedra para uma possível célula vazia em sua Vizinhança de Neumann.
https://pt.wikipedia.org/wiki/Resta_um

Detalhes da implementação:

 - Vá na classe Tabuleiro, e crie um tabuleiro arbitrário no método main, um tabuleiro é um array bidimensional de inteiros, de tal forma que o elemento (i,j) desse array representa um elemento correspondente no tabuleiro, com exceção do elemento zero que representa uma célula vazia. A partir daí, rode o exemplo, posteriormente será impresso o passo a passo de como computar uma solução final a partir do tabuleiro inicial.
 
 Exemplo de tabuleiro:
  {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
  
  
  |8 1 3|
  |4 2  |
  |7 6 5|
