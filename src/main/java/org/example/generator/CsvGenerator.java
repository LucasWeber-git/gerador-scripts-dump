package org.example.generator;

import org.example.util.FileUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Gerador de arquivo CSV para o ArangoDB
 */
public class CsvGenerator extends Generator {

    public CsvGenerator(int qtdUsuarios) {
        super(qtdUsuarios);
    }

    @Override
    public void gerar() {
        FileUtils.writeToFile("csv/usuarios.csv", gerarUsuarios());
        FileUtils.writeToFile("csv/posts.csv", gerarPosts());
        FileUtils.writeToFile("csv/tags.csv", gerarTags());
        FileUtils.writeToFile("csv/publicadoPor.csv", gerarPublicacoes());
        FileUtils.writeToFile("csv/possui.csv", gerarPossuiTag());
        FileUtils.writeToFile("csv/relacionamentos.csv", gerarRelacionamentos());
        FileUtils.writeToFile("csv/curtidas.csv", gerarCurtidas());
        FileUtils.writeToFile("csv/comentarios.csv", gerarComentarios());
        FileUtils.writeToFile("csv/compartilhamentos.csv", gerarCompartilhamentos());
    }

    @Override
    protected String gerarUsuarios() {
        String formato = "%d,\"%s\",%d,\"%s\"\n";
        String header = "id,nome,idade,cidade";

        StringBuilder content = new StringBuilder();

        for (int i = 1; i <= QTD_USUARIOS; i++) {
            String nome = faker.name().firstName();
            int idade = faker.random().nextInt(1, 99);
            String cidade = faker.address().city();

            content.append(String.format(formato, i, nome, idade, cidade));
        }

        return header + NOVA_LINHA + content;
    }

    @Override
    protected String gerarPosts() {
        String formato = "%d,\"%s\",\"%s\",%d,%d,%d\n";
        String header = "id,conteudo,data,qtdCurtidas,qtdComentarios,qtdCompartilhamentos";

        StringBuilder content = new StringBuilder();

        for (int i = 1; i <= QTD_POSTS; i++) {
            String conteudo = faker.lorem().paragraph();
            String data = getRandomDate();
            int qtdCurtidas = faker.random().nextInt(0, 10000);
            int qtdComentarios = faker.random().nextInt(0, 1000);
            int qtdCompartilhamentos = faker.random().nextInt(0, 100);

            content.append(String.format(formato, i, conteudo, data, qtdCurtidas, qtdComentarios, qtdCompartilhamentos));
        }

        return header + NOVA_LINHA + content;
    }

    @Override
    protected String gerarTags() {
        return """
                id,nome
                1,"humor",
                2,"política",
                3,"memes",
                4,"viagem",
                5,"fotografia",
                6,"arte",
                7,"pintura",
                8,"DIY",
                9,"música",
                10,"cinema",
                11,"livros",
                12,"cultura pop",
                13,"comida",
                14,"treino",
                15,"dieta",
                16,"tecnologia",
                17,"programação",
                18,"marketing",
                19,"moda",
                20,"maquiagem",
                21,"lifestyle",
                22,"pets",
                23,"motivacional",
                24,"curiosidades",
                25,"tutoriais"
                """;
    }

    @Override
    protected String gerarPublicacoes() {
        String formato = "%d,%d\n";
        String header = "postId,usuarioId";

        StringBuilder content = new StringBuilder();

        for (int i = 1; i <= QTD_POSTS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            content.append(String.format(formato, i, usuarioId));
        }

        return header + NOVA_LINHA + content;
    }

    private String gerarPossuiTag() {
        String formato = "%d,%d\n";
        String header = "postId,tagId";

        StringBuilder content = new StringBuilder();

        for (int i = 1; i <= QTD_POSTS; i++) {
            int tagId = faker.random().nextInt(1, QTD_TAGS);
            content.append(String.format(formato, i, tagId));
        }

        return header + NOVA_LINHA + content;
    }

    @Override
    protected String gerarRelacionamentos() {
        String formato = "%d,%d,\"%s\"\n";
        String header = "usuarioA,usuarioB,data";

        Map<Integer, Integer> relations = new HashMap<>();
        StringBuilder content = new StringBuilder();

        for (int i = 0; i <= QTD_RELACIONAMENTOS; i++) {
            int usuarioA = faker.random().nextInt(1, QTD_USUARIOS);
            int usuarioB = faker.random().nextInt(1, QTD_USUARIOS);
            String data = getRandomDate();

            if (usuarioA == usuarioB) {
                continue;
            } else if (relations.get(usuarioA) != null && relations.get(usuarioA).equals(usuarioB)) {
                continue;
            }

            relations.put(usuarioA, usuarioB);
            content.append(String.format(formato, i, usuarioA, usuarioB, data));
        }

        return header + NOVA_LINHA + content;
    }

    @Override
    protected String gerarCurtidas() {
        String formato = "%d,%d,\"%s\"\n";
        String header = "usuarioId,postId,data";

        Map<Integer, Integer> relations = new HashMap<>();
        StringBuilder content = new StringBuilder();

        for (int i = 0; i <= QTD_CURTIDAS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();

            if (relations.get(usuarioId) == null || !relations.get(usuarioId).equals(postId)) {
                relations.put(usuarioId, postId);
                content.append(String.format(formato, usuarioId, postId, data));
            }
        }

        return header + NOVA_LINHA + content;
    }

    @Override
    protected String gerarComentarios() {
        String formato = "%d,%d,\"%s\",\"%s\"\n";
        String header = "usuarioId,postId,data,comentario";

        Map<Integer, Integer> relations = new HashMap<>();
        StringBuilder content = new StringBuilder();

        for (int i = 0; i <= QTD_COMENTARIOS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();
            String comentario = faker.lorem().paragraph();

            if (relations.get(usuarioId) == null || !relations.get(usuarioId).equals(postId)) {
                relations.put(usuarioId, postId);
                content.append(String.format(formato, usuarioId, postId, data, comentario));
            }
        }

        return header + NOVA_LINHA + content;
    }

    @Override
    protected String gerarCompartilhamentos() {
        String formato = "%d,%d,\"%s\"\n";
        String header = "usuarioId,postId,data";

        Map<Integer, Integer> relations = new HashMap<>();
        StringBuilder content = new StringBuilder();

        for (int i = 0; i <= QTD_COMPARTILHAMENTOS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();

            if (relations.get(usuarioId) == null || !relations.get(usuarioId).equals(postId)) {
                relations.put(usuarioId, postId);
                content.append(String.format(formato, usuarioId, postId, data));
            }
        }

        return header + NOVA_LINHA + content;
    }

}
