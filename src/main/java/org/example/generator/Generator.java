package org.example.generator;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public abstract class Generator {

    private final Faker faker = new Faker(new Locale("pt-BR"));

    protected final int QTD;

    public Generator(int qtd) {
        this.QTD = qtd;
    }

    public abstract void generate();

    protected String getRandomName() {
        return faker.name().firstName();
    }

    protected String getRandomCity() {
        return faker.address().city();
    }

    protected int getRandomInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

}
