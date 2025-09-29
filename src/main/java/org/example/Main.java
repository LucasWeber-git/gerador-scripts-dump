package org.example;

import org.example.generator.AqlGenerator;
import org.example.generator.CypherGenerator;
import org.example.generator.Generator;
import org.example.generator.GremlinGenerator;

public class Main {

    public static void main(String[] args) {
        int qty = 100;

        Generator aqlGenerator = new AqlGenerator(qty);
        Generator cypherGenerator = new CypherGenerator(qty);
        Generator gremlinGenerator = new GremlinGenerator(qty);

        aqlGenerator.generate();
        cypherGenerator.generate();
        gremlinGenerator.generate();
    }

}