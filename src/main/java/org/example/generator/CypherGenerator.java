package org.example.generator;

import org.example.util.FileUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Gerador de código Cypher para o Neo4j
 */
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
        sb.append("UNWIND [\n");

        for (int i = 1; i <= QTD_USUARIOS; i++) {
            String nome = faker.name().firstName();
            int idade = faker.random().nextInt(1, 99);
            String cidade = faker.address().city();

            sb.append(String.format("    {id: %d, nome: \"%s\", idade: %d, cidade: \"%s\"}",
                    i, nome, idade, cidade)
            );
            sb.append(i < QTD_USUARIOS ? VIRGULA_NL : NOVA_LINHA);
        }

        sb.append("""
                ] AS values
                CREATE(u:Usuario {
                    id: values.id,
                    nome: values.nome,
                    idade: values.idade,
                    cidade: values.cidade
                });
                
                """);

        return sb.toString();
    }

    @Override
    protected String gerarPosts() {
        StringBuilder sb = new StringBuilder();
        sb.append("UNWIND [\n");

        for (int i = 1; i <= QTD_POSTS; i++) {
            String conteudo = faker.lorem().paragraph();
            String data = getRandomDate();
            int qtdCurtidas = faker.random().nextInt(0, QTD_CURTIDAS);
            int qtdComentarios = faker.random().nextInt(0, QTD_COMENTARIOS);
            int qtdCompartilhamentos = faker.random().nextInt(0, QTD_COMPARTILHAMENTOS);

            sb.append(String.format("    {id: %d, conteudo: \"%s\", data: \"%s\", qtd_curtidas: %d, qtd_comentarios: %d, qtd_compartilhamentos: %d}",
                    i, conteudo, data, qtdCurtidas, qtdComentarios, qtdCompartilhamentos)
            );
            sb.append(i < QTD_POSTS ? VIRGULA_NL : NOVA_LINHA);
        }

        sb.append("""
                ] AS values
                CREATE(p:Post {
                    id: values.id,
                    conteudo: values.conteudo,
                    data: values.data,
                    qtd_curtidas: values.qtd_curtidas,
                    qtd_comentarios: values.qtd_comentarios,
                    qtd_compartilhamentos: values.qtd_compartilhamentos
                });
                
                """);

        return sb.toString();
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
        sb.append("UNWIND [\n");

        for (int i = 1; i <= QTD_POSTS; i++) {
            int tagId = faker.random().nextInt(1, QTD_TAGS);
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);

            sb.append(String.format("    {post_id: %d, usuario_id: %d, tag_id: %d}", i, usuarioId, tagId));
            sb.append(i < QTD_POSTS ? VIRGULA_NL : NOVA_LINHA);
        }

        sb.append("""
                ] AS values
                MATCH (p:Post {id: values.post_id}), (u:Usuario {id: values.usuario_id}), (t:Tag {id: values.tag_id})
                CREATE (p)-[:PUBLICADO_POR]->(u)
                CREATE (p)-[:POSSUI]->(t);
                
                """);

        return sb.toString();
    }

    @Override
    protected String gerarRelacionamentos() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("UNWIND [\n");

        for (int i = 1; i <= QTD_RELACIONAMENTOS; i++) {
            int usuarioA = faker.random().nextInt(1, QTD_USUARIOS);
            int usuarioB = faker.random().nextInt(1, QTD_USUARIOS);
            String data = getRandomDate();

            if (usuarioA != usuarioB && notExists(relations, usuarioA, usuarioB)) {
                relations.computeIfAbsent(usuarioA, k -> new HashSet<>()).add(usuarioB);
                sb.append(String.format("    {usuario_a: %d, usuario_b: %d, data: \"%s\"}", usuarioA, usuarioB, data));
                sb.append(i < QTD_RELACIONAMENTOS ? VIRGULA_NL : NOVA_LINHA);
            }
        }

        sb.append("""
                ] AS values
                MATCH (a:Usuario {id: values.usuario_a}), (b:Usuario {id: values.usuario_b})
                CREATE (a)-[:SEGUE {data: values.data}]->(b);
                
                """);

        return sb.toString();
    }

    @Override
    protected String gerarCurtidas() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("UNWIND [\n");

        for (int i = 0; i <= QTD_CURTIDAS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();

            if (notExists(relations, usuarioId, postId)) {
                relations.computeIfAbsent(usuarioId, k -> new HashSet<>()).add(postId);
                sb.append(String.format("    {usuario_id: %d, post_id: %d, data: \"%s\"}", usuarioId, postId, data));
                sb.append(i < QTD_CURTIDAS ? VIRGULA_NL : NOVA_LINHA);
            }
        }

        sb.append("""
                ] AS values
                MATCH (u:Usuario {id: values.usuario_id}), (p:Post {id: values.post_id})
                CREATE (u)-[:CURTIU {data: values.data}]->(p);
                
                """);

        return sb.toString();
    }

    @Override
    protected String gerarComentarios() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("UNWIND [\n");

        for (int i = 0; i <= QTD_COMENTARIOS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();
            String comentario = faker.lorem().paragraph();

            if (notExists(relations, usuarioId, postId)) {
                relations.computeIfAbsent(usuarioId, k -> new HashSet<>()).add(postId);

                sb.append(String.format("    {usuario_id: %d, post_id: %d, data: \"%s\", comentario: \"%s\"}",
                        usuarioId, postId, data, comentario
                ));
                sb.append(i < QTD_COMENTARIOS ? VIRGULA_NL : NOVA_LINHA);
            }
        }

        sb.append("""
                ] AS values
                MATCH (u:Usuario {id: values.usuario_id}), (p:Post {id: values.post_id})
                CREATE (u)-[:COMENTOU {data: values.data, comentario: values.comentario}]->(p);
                
                """);

        return sb.toString();
    }

    @Override
    protected String gerarCompartilhamentos() {
        Map<Integer, Set<Integer>> relations = new HashMap<>();

        StringBuilder sb = new StringBuilder();
        sb.append("UNWIND [\n");

        for (int i = 0; i <= QTD_COMPARTILHAMENTOS; i++) {
            int usuarioId = faker.random().nextInt(1, QTD_USUARIOS);
            int postId = faker.random().nextInt(1, QTD_POSTS);
            String data = getRandomDate();

            if (notExists(relations, usuarioId, postId)) {
                relations.computeIfAbsent(usuarioId, k -> new HashSet<>()).add(postId);
                sb.append(String.format("    {usuario_id: %d, post_id: %d, data: \"%s\"}", usuarioId, postId, data));
                sb.append(i < QTD_COMPARTILHAMENTOS ? VIRGULA_NL : NOVA_LINHA);
            }
        }

        sb.append("""
                ] AS values
                MATCH (u:Usuario {id: values.usuario_id}), (p:Post {id: values.post_id})
                CREATE (u)-[:COMPARTILHOU {data: values.data}]->(p);
                
                """);

        return sb.toString();
    }

}
