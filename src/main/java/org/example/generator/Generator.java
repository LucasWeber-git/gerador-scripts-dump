package org.example.generator;

import com.github.javafaker.Faker;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public abstract class Generator {

    protected final Faker faker = new Faker(new Locale("pt-BR"));

    protected final int QTD;

    public Generator(int qtd) {
        this.QTD = qtd;
    }

    public abstract void gerar();

    protected String getRandomDate() {
        Date date = faker.date().past(2500, TimeUnit.DAYS);

        ZonedDateTime zonedDtm = date.toInstant().atZone(ZoneId.of("America/Sao_Paulo"));

        return zonedDtm.format(ISO_OFFSET_DATE_TIME);
    }

}
