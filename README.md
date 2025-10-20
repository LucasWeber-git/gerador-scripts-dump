## Gerador de Scripts de Dump

### Descrição
Este projeto tem como objetivo gerar um dump de dados aleatório para ser inserido em um banco de dados em grafo. Com 
isso, podem ser realizados testes utilizandos esses dados.

O dump cria um grafo no contexto de um rede social, com vértices do tipo Usuario, Post e Tag.

A quantidade de registros é baseada na quantidade de usuários, da seguinte forma:
- QTD_USUARIOS = ${qtdUsuarios};
- QTD_POSTS = (QTD_USUARIOS * 3);
- QTD_TAGS = 25;
- QTD_RELACIONAMENTOS = (QTD_USUARIOS * 10);
- QTD_CURTIDAS = (QTD_USUARIOS * 10);
- QTD_COMENTARIOS = (QTD_USUARIOS * 5);
- QTD_COMPARTILHAMENTOS = (QTD_USUARIOS * 3);

Os resultados serão gerados na pasta /src/main/resources/output/

### Versão

- Versão Java: 17
- Versão do projeto: 1.0.0

### Execução
- Este é um projeto Maven
- Para executar, basta alterar na classe Main a quantidade de usuários desejada para o dump, compilar e executar