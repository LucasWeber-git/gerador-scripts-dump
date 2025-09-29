package org.example.generator;

import org.example.util.FileUtils;

import java.util.List;

public abstract class Generator {

    protected final List<String> NOMES;
    protected final List<String> CIDADES;
    protected final int QTD;

    public Generator(int qtd) {
        this.QTD = qtd;
        this.NOMES = FileUtils.getNomes();
        this.CIDADES = FileUtils.getCidades();
    }

    public abstract void generate();

}
