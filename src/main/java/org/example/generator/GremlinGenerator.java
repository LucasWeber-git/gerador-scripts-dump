package org.example.generator;

import org.example.util.FileUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Gerador de código Gremlin para o Amazon Neptune e o Azure Cosmos
 */
public class GremlinGenerator extends Generator {

    public GremlinGenerator(int qtdUsuarios) {
        super(qtdUsuarios);
    }

    @Override
    public void gerar() {
        String relativePath = "gremlin/script.groovy";

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
        StringBuilder sb = new StringBuilder("g\n");

        for (int i = 1; i <= QTD_USUARIOS; i++) {
            String nome = faker.name().firstName();
            int idade = faker.random().nextInt(1, 99);
            String cidade = faker.address().city();

            String cmd = ".addV('Usuario')" +
                    ".property('pk','default')" +
                    ".property('idUsuario','%s')" +
                    ".property('nome','%s')" +
                    ".property('idade',%d)" +
                    ".property('cidade','%s')\n";
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

            String cmd = ".addV('Post')" +
                    ".property('pk','default')" +
                    ".property('idPost','%d')" +
                    ".property('conteudo','%s')" +
                    ".property('data','%s')" +
                    ".property('qtdCurtidas',%d)" +
                    ".property('qtdComentarios',%d)" +
                    ".property('qtdCompartilhamentos',%d)\n";
            sb.append(String.format(cmd, i, conteudo, data, qtdCurtidas, qtdComentarios, qtdCompartilhamentos));
        }

        return sb + NOVA_LINHA;
    }

    @Override
    protected String gerarTags() {
        return """
                .addV('Tag').property('pk','default').property('idTag','1').property('nome','humor')
                .addV('Tag').property('pk','default').property('idTag','2').property('nome','política')
                .addV('Tag').property('pk','default').property('idTag','3').property('nome','memes')
                .addV('Tag').property('pk','default').property('idTag','4').property('nome','viagem')
                .addV('Tag').property('pk','default').property('idTag','5').property('nome','fotografia')
                .addV('Tag').property('pk','default').property('idTag','6').property('nome','arte')
                .addV('Tag').property('pk','default').property('idTag','7').property('nome','pintura')
                .addV('Tag').property('pk','default').property('idTag','8').property('nome','DIY')
                .addV('Tag').property('pk','default').property('idTag','9').property('nome','música')
                .addV('Tag').property('pk','default').property('idTag','10').property('nome','cinema')
                .addV('Tag').property('pk','default').property('idTag','11').property('nome','livros')
                .addV('Tag').property('pk','default').property('idTag','12').property('nome','cultura pop')
                .addV('Tag').property('pk','default').property('idTag','13').property('nome','comida')
                .addV('Tag').property('pk','default').property('idTag','14').property('nome','treino')
                .addV('Tag').property('pk','default').property('idTag','15').property('nome','dieta')
                .addV('Tag').property('pk','default').property('idTag','16').property('nome','tecnologia')
                .addV('Tag').property('pk','default').property('idTag','17').property('nome','programação')
                .addV('Tag').property('pk','default').property('idTag','18').property('nome','marketing')
                .addV('Tag').property('pk','default').property('idTag','19').property('nome','moda')
                .addV('Tag').property('pk','default').property('idTag','20').property('nome','maquiagem')
                .addV('Tag').property('pk','default').property('idTag','21').property('nome','lifestyle')
                .addV('Tag').property('pk','default').property('idTag','22').property('nome','pets')
                .addV('Tag').property('pk','default').property('idTag','23').property('nome','motivacional')
                .addV('Tag').property('pk','default').property('idTag','24').property('nome','curiosidades')
                .addV('Tag').property('pk','default').property('idTag','25').property('nome','tutoriais')
                
                """;
    }

    @Override
    protected String gerarPublicacoes() {
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= QTD_POSTS; i++) {
            int tagId = faker.random().nextInt(1, QTD_TAGS);
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);

            String cmd = ".addE('publicadoPor')" +
                    ".from(__.V().has('Post','idPost','%d'))" +
                    ".to(__.V().has('Usuario','idUsuario','%d'))\n" +
                    ".addE('possui')" +
                    ".from(__.V().has('Post','idPost','%d'))" +
                    ".to(__.V().has('Tag','idTag','%d'))\n";
            sb.append(String.format(cmd, i, usuarioId, i, tagId));
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

                String cmd = ".addE('segue')" +
                        ".from(__.V().has('Usuario','idUsuario','%d'))" +
                        ".to(__.V().has('Usuario','idUsuario','%d'))" +
                        ".property('data', '%s')\n";
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

                String cmd = ".addE('curtiu')" +
                        ".from(__.V().has('Usuario','idUsuario','%d'))" +
                        ".to(__.V().has('Post','idPost','%d'))" +
                        ".property('data', '%s')\n";
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

                String cmd = ".addE('comentou')" +
                        ".from(__.V().has('Usuario','idUsuario','%d'))" +
                        ".to(__.V().has('Post','idPost','%d'))" +
                        ".property('data', '%s')" +
                        ".property('comentario', '%s')\n";
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

                String cmd = ".addE('compartilhou')" +
                        ".from(__.V().has('Usuario','idUsuario','%d'))" +
                        ".to(__.V().has('Post','idPost','%d'))" +
                        ".property('data', '%s')\n";
                sb.append(String.format(cmd, usuarioId, postId, data));
            }
        }

        return sb + NOVA_LINHA;
    }

}
