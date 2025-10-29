package org.example.generator;

import org.example.util.FileUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CypherGenerator extends Generator {

    public CypherGenerator(int qtdUsuarios) {
        super(qtdUsuarios);
    }

    @Override
    public void gerar() {
        String relativePath = "cypher/script.cypher";

        FileUtils.resetFile(relativePath);

        FileUtils.appendToFile(relativePath, gerarUsuarios());
        FileUtils.appendToFile(relativePath, gerarPosts());
        FileUtils.appendToFile(relativePath, gerarTags());
        FileUtils.appendToFile(relativePath, gerarPublicacoes());
        FileUtils.appendToFile(relativePath, gerarRelacionamentos());
        FileUtils.appendToFile(relativePath, gerarCurtidas());
        FileUtils.appendToFile(relativePath, gerarComentarios());
        FileUtils.appendToFile(relativePath, gerarCompartilhamentos());
    }

    @Override
    protected String gerarUsuarios() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= QTD_USUARIOS; i++) {
            String nome = faker.name().firstName();
            int idade = faker.random().nextInt(1, 99);
            String cidade = faker.address().city();

            String cmd = "CREATE(u:Usuario {" +
                    " id: %d," +
                    " nome: \"%s\"," +
                    " idade: %d," +
                    " cidade: \"%s\"" +
                    " });\n";
            sb.append(String.format(cmd, i, nome, idade, cidade));
        }

        return sb + NOVA_LINHA;
    }

    @Override
    protected String gerarPosts() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= QTD_POSTS; i++) {
            String conteudo = faker.lorem().paragraph();
            String data = getRandomDate();
            int qtdCurtidas = faker.random().nextInt(0, QTD_CURTIDAS);
            int qtdComentarios = faker.random().nextInt(0, QTD_COMENTARIOS);
            int qtdCompartilhamentos = faker.random().nextInt(0, QTD_COMPARTILHAMENTOS);

            String cmd = "CREATE(p:Post {" +
                    " id: %d," +
                    " conteudo: \"%s\"," +
                    " data: \"%s\"," +
                    " qtdCurtidas: %d," +
                    " qtdComentarios: %d," +
                    " qtdCompartilhamentos: %d" +
                    " });\n";
            sb.append(String.format(cmd, i, conteudo, data, qtdCurtidas, qtdComentarios, qtdCompartilhamentos));
        }

        return sb + NOVA_LINHA;
    }

    @Override
    protected String gerarTags() {
        return """
                UNWIND[
                    {id: 1, nome: "humor"},
                    {id: 2, nome: "política"},
                    {id: 3, nome: "memes"},
                    {id: 4, nome: "viagem"},
                    {id: 5, nome: "fotografia"},
                    {id: 6, nome: "arte"},
                    {id: 7, nome: "pintura"},
                    {id: 8, nome: "DIY"},
                    {id: 9, nome: "música"},
                    {id: 10, nome: "cinema"},
                    {id: 11, nome: "livros"},
                    {id: 12, nome: "cultura pop"},
                    {id: 13, nome: "comida"},
                    {id: 14, nome: "treino"},
                    {id: 15, nome: "dieta"},
                    {id: 16, nome: "tecnologia"},
                    {id: 17, nome: "programação"},
                    {id: 18, nome: "marketing"},
                    {id: 19, nome: "moda"},
                    {id: 20, nome: "maquiagem"},
                    {id: 21, nome: "lifestyle"},
                    {id: 22, nome: "pets"},
                    {id: 23, nome: "motivacional"},
                    {id: 24, nome: "curiosidades"},
                    {id: 25, nome: "tutoriais"}
                ] AS values
                CREATE(t:Tag {
                    id: values.id,
                    nome: values.nome
                });
                
                """;
    }

    @Override
    protected String gerarPublicacoes() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= QTD_POSTS; i++) {
            int tagId = faker.random().nextInt(1, QTD_TAGS);
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);

            String cmd = "MATCH (p:Post {id: %d}), (u:Usuario {id: %d}), (t:Tag {id: %d})\n" +
                    "CREATE (p)-[:PUBLICADO_POR]->(u)\n" +
                    "CREATE (p)-[:POSSUI]->(t);\n";

            sb.append(String.format(cmd, i, usuarioId, tagId));
        }

        return sb + NOVA_LINHA;
    }

    @Override
    protected String gerarRelacionamentos() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= QTD_RELACIONAMENTOS; i++) {
            int usuarioA = faker.random().nextInt(1, QTD_USUARIOS);
            int usuarioB = faker.random().nextInt(1, QTD_USUARIOS);
            String data = getRandomDate();

            if (usuarioA != usuarioB && notExists(relations, usuarioA, usuarioB)) {
                relations.computeIfAbsent(usuarioA, k -> new HashSet<>()).add(usuarioB);

                String cmd = "MATCH (a:Usuario {id: %d}), (b:Usuario {id: %d})\n" +
                        "CREATE (a)-[:SEGUE {data: \"%s\"}]->(b);\n";

                sb.append(String.format(cmd, usuarioA, usuarioB, data));
            }
        }

        return sb + NOVA_LINHA;
    }

    @Override
    protected String gerarCurtidas() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= QTD_CURTIDAS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();

            if (notExists(relations, usuarioId, postId)) {
                relations.computeIfAbsent(usuarioId, k -> new HashSet<>()).add(postId);

                String cmd = "MATCH (u:Usuario {id: %d}), (p:Post {id: %d})\n" +
                        "CREATE (u)-[:CURTIU {data: \"%s\"}]->(p);\n";
                sb.append(String.format(cmd, usuarioId, postId, data));
            }
        }

        return sb + NOVA_LINHA;
    }

    @Override
    protected String gerarComentarios() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= QTD_COMENTARIOS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();
            String comentario = faker.lorem().paragraph();

            if (notExists(relations, usuarioId, postId)) {
                relations.computeIfAbsent(usuarioId, k -> new HashSet<>()).add(postId);

                String cmd = "MATCH (u:Usuario {id: %d}), (p:Post {id: %d})\n" +
                        "CREATE (u)-[:COMENTOU {data: \"%s\", comentario: \"%s\"}]->(p);\n";
                sb.append(String.format(cmd, usuarioId, postId, data, comentario));
            }
        }

        return sb + NOVA_LINHA;
    }

    @Override
    protected String gerarCompartilhamentos() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <= QTD_COMPARTILHAMENTOS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();

            if (notExists(relations, usuarioId, postId)) {
                relations.computeIfAbsent(usuarioId, k -> new HashSet<>()).add(postId);

                String cmd = "MATCH (u:Usuario {id: %d}), (p:Post {id: %d})\n" +
                        "CREATE (u)-[:COMPARTILHOU {data: \"%s\"}]->(p);\n";
                sb.append(String.format(cmd, usuarioId, postId, data));
            }
        }

        return sb + NOVA_LINHA;
    }

}
