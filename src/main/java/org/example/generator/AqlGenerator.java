package org.example.generator;

import org.example.util.FileUtils;

/**
 * AQL Code generator for ArangoDB
 */
public class AqlGenerator extends Generator {

    public AqlGenerator(int qtd) {
        super(qtd);
    }

    @Override
    public void generate() {
        //TODO
        FileUtils.writeToFile("AQL.txt", "teste");
    }

}
