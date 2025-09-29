package org.example.generator;

import org.example.util.FileUtils;

/**
 * Cypher code generator for Neo4j
 */
public class CypherGenerator extends Generator {

    public CypherGenerator(int qtd) {
        super(qtd);
    }

    @Override
    public void generate() {
        String command = generateUsers() +
                generatePosts() +
                generateTags() +
                generateTagPost() +
                generateRelationships() +
                generateInteractions();

        FileUtils.writeToFile("cypher.txt", command);
    }

    private String generateUsers() {
        StringBuilder sb = new StringBuilder();

        sb.append("UNWIND [\n");

        for (int i = 1; i <= QTD; i++) {
            String nome = getRandomName();
            int idade = getRandomInt(1, 99);
            String cidade = getRandomCity();

            sb.append(String.format("      {id: %d, nome: %s, idade: %d, cidade: %s}\n",
                    i, nome, idade, cidade)
            );
        }

        sb.append("""
                ] AS u
                CREATE(u:Usuario {
                      id: u.id,
                      nome: u.nome,
                      idade: u.idade,
                      cidade: u.cidade
                });
                """);

        return sb.toString();
    }

    private String generatePosts() {
        //TODO
        return "";
    }

    private String generateTags() {
        //TODO: esse aqui pode ser fixo
        return "";
    }

    private String generateTagPost() {
        //TODO: Post - POSSUI -> Tag
        return "";
    }

    private String generateRelationships() {
        //TODO: Usuario - SEGUE -> Usuario
        return "";
    }

    private String generateInteractions() {
        //TODO Post - PUBLICOU, CURTIU, COMENTOU -> Post
        return "";
    }

}
