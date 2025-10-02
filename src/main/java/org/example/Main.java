package org.example;

import org.example.generator.CsvGenerator;
import org.example.generator.CypherGenerator;
import org.example.generator.Generator;
import org.example.generator.GremlinGenerator;

public class Main {

    public static void main(String[] args) {
        int qty = 100;

        Generator csvGenerator = new CsvGenerator(qty);
        Generator cypherGenerator = new CypherGenerator(qty);
        Generator gremlinGenerator = new GremlinGenerator(qty);

        csvGenerator.gerar();
        cypherGenerator.gerar();
        gremlinGenerator.gerar();
    }

}