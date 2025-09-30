package org.example.generator;

import org.example.util.FileUtils;

/**
 * Gremlin code generator for Amazon Neptune and Azure Cosmos
 */
public class GremlinGenerator extends Generator {

    public GremlinGenerator(int qtd) {
        super(qtd);
    }

    @Override
    public void gerar() {
        //TODO
        FileUtils.writeToFile("gremlin.txt", "teste");
    }

}
