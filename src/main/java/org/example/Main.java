package org.example;

import org.example.generator.CsvGenerator;
import org.example.generator.CypherGenerator;
import org.example.generator.Generator;
import org.example.generator.GremlinGenerator;

public class Main {

    public static void main(String[] args) {
        int qtdUsuarios = 100;

        Generator csvGenerator = new CsvGenerator(qtdUsuarios);
        Generator cypherGenerator = new CypherGenerator(qtdUsuarios);
        Generator gremlinGenerator = new GremlinGenerator(qtdUsuarios);

        csvGenerator.gerar();
        cypherGenerator.gerar();
        gremlinGenerator.gerar();
    }

}