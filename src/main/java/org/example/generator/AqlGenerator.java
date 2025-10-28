package org.example.generator;

import org.example.util.FileUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Gerador de código AQL para o ArangoDB
 */
public class AqlGenerator extends Generator {

    public AqlGenerator(int qtdUsuarios) {
        super(qtdUsuarios);
    }

    @Override
    public void gerar() {
        String command = gerarUsuarios() +
                gerarPosts() +
                gerarTags() +
                gerarPublicacoes() +
                gerarRelacionamentos() +
                gerarCurtidas() +
                gerarComentarios() +
                gerarCompartilhamentos();

        FileUtils.writeToFile("aql/script.aql", command);
    }

    @Override
    protected String gerarUsuarios() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= QTD_USUARIOS; i++) {
            String nome = faker.name().firstName();
            int idade = faker.random().nextInt(1, 99);
            String cidade = faker.address().city();

            String cmd = "db.Usuario.save({" +
                    " _key: \"%d\"," +
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

            String cmd = "db.Post.save({" +
                    " _key: \"%d\"," +
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
                db.Tag.save({ _key: "1", nome: "humor" });
                db.Tag.save({ _key: "2", nome: "política" });
                db.Tag.save({ _key: "3", nome: "memes" });
                db.Tag.save({ _key: "4", nome: "viagem" });
                db.Tag.save({ _key: "5", nome: "fotografia" });
                db.Tag.save({ _key: "6", nome: "arte" });
                db.Tag.save({ _key: "7", nome: "pintura" });
                db.Tag.save({ _key: "8", nome: "DIY" });
                db.Tag.save({ _key: "9", nome: "música" });
                db.Tag.save({ _key: "10", nome: "cinema" });
                db.Tag.save({ _key: "11", nome: "livros" });
                db.Tag.save({ _key: "12", nome: "cultura pop" });
                db.Tag.save({ _key: "13", nome: "comida" });
                db.Tag.save({ _key: "14", nome: "treino" });
                db.Tag.save({ _key: "15", nome: "dieta" });
                db.Tag.save({ _key: "16", nome: "tecnologia" });
                db.Tag.save({ _key: "17", nome: "programação" });
                db.Tag.save({ _key: "18", nome: "marketing" });
                db.Tag.save({ _key: "19", nome: "moda" });
                db.Tag.save({ _key: "20", nome: "maquiagem" });
                db.Tag.save({ _key: "21", nome: "lifestyle" });
                db.Tag.save({ _key: "22", nome: "pets" });
                db.Tag.save({ _key: "23", nome: "motivacional" });
                db.Tag.save({ _key: "24", nome: "curiosidades" });
                db.Tag.save({ _key: "25", nome: "tutoriais" });
                
                """;
    }

    @Override
    protected String gerarPublicacoes() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= QTD_POSTS; i++) {
            int tagId = faker.random().nextInt(1, QTD_TAGS);
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);

            String cmdPublicadoPor = "db.publicadoPor.save({" +
                    " _from: \"Post/%d\"," +
                    " _to: \"Usuario/%d\"" +
                    " });\n";

            String cmdPossui = "db.possui.save({" +
                    " _from: \"Post/%d\"," +
                    " _to: \"Tag/%d\"" +
                    " });\n";

            sb.append(String.format(cmdPublicadoPor + cmdPossui, i, usuarioId, i, tagId));
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

                String cmd = "db.segue.save({" +
                        " _from: \"Usuario/%d\"," +
                        " _to: \"Usuario/%d\"," +
                        " data: \"%s\"" +
                        " });\n";
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

                String cmd = "db.curtiu.save({" +
                        " _from: \"Usuario/%d\"," +
                        " _to: \"Post/%d\"," +
                        " data: \"%s\"" +
                        " });\n";
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

                String cmd = "db.comentou.save({" +
                        " _from: \"Usuario/%d\"," +
                        " _to: \"Post/%d\"," +
                        " data: \"%s\"," +
                        " comentario: \"%s\"" +
                        " });\n";
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

                String cmd = "db.compartilhou.save({" +
                        " _from: \"Usuario/%d\"," +
                        " _to: \"Post/%d\"," +
                        " data: \"%s\"" +
                        " });\n";
                sb.append(String.format(cmd, usuarioId, postId, data));
            }
        }

        return sb + NOVA_LINHA;
    }

}
