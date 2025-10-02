package org.example.generator;

import org.example.util.FileUtils;

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
        FileUtils.writeToFile("csv/publicacoes.csv", gerarPublicacoes());
        FileUtils.writeToFile("csv/relacionamentos.csv", gerarRelacionamentos());
        FileUtils.writeToFile("csv/curtidas.csv", gerarCurtidas());
        FileUtils.writeToFile("csv/comentarios.csv", gerarComentarios());
        FileUtils.writeToFile("csv/compartilhamentos.csv", gerarCompartilhamentos());
    }

    @Override
    protected String gerarUsuarios() {
        return "";
    }

    @Override
    protected String gerarPosts() {
        return "";
    }

    @Override
    protected String gerarTags() {
        return "";
    }

    @Override
    protected String gerarPublicacoes() {
        return "";
    }

    @Override
    protected String gerarRelacionamentos() {
        return "";
    }

    @Override
    protected String gerarCurtidas() {
        return "";
    }

    @Override
    protected String gerarComentarios() {
        return "";
    }

    @Override
    protected String gerarCompartilhamentos() {
        return "";
    }

}
