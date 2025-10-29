package org.example;

import org.example.generator.*;

public class Main {

    public static void main(String[] args) {
        int qtdUsuarios = 100;

        Generator aqlGenerator = new AqlGenerator(qtdUsuarios);
        Generator csvGenerator = new CsvGenerator(qtdUsuarios);
        Generator cypherGenerator = new CypherGenerator(qtdUsuarios);
        //Generator cypherBulkGenerator = new CypherBulkGenerator(qtdUsuarios);
        Generator gremlinGenerator = new GremlinGenerator(qtdUsuarios);

        aqlGenerator.gerar();
        csvGenerator.gerar();
        cypherGenerator.gerar();
        //cypherBulkGenerator.gerar();
        gremlinGenerator.gerar();
    }

}