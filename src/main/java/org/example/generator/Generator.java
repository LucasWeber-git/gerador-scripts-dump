package org.example.generator;

import com.github.javafaker.Faker;
import org.example.util.FileUtils;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public abstract class Generator {

    protected final Faker faker = new Faker(new Locale("pt-BR"));


    protected final String NOVA_LINHA = "\n";
    protected final String VIRGULA_NL = ",\n";

    protected final int QTD_USUARIOS;
    protected final int QTD_POSTS;
    protected final int QTD_TAGS;
    protected final int QTD_RELACIONAMENTOS;
    protected final int QTD_CURTIDAS;
    protected final int QTD_COMENTARIOS;
    protected final int QTD_COMPARTILHAMENTOS;

    public Generator(int qtdUsuarios) {
        QTD_USUARIOS = qtdUsuarios;
        QTD_POSTS = (qtdUsuarios * 3);
        QTD_TAGS = 25;
        QTD_RELACIONAMENTOS = (qtdUsuarios * 10);
        QTD_CURTIDAS = (qtdUsuarios * 10);
        QTD_COMENTARIOS = (qtdUsuarios * 5);
        QTD_COMPARTILHAMENTOS = (qtdUsuarios * 3);
    }

    protected String getRandomDate() {
        Date date = faker.date().past(2500, TimeUnit.DAYS);

        ZonedDateTime zonedDtm = date.toInstant().atZone(ZoneId.of("America/Sao_Paulo"));

        return zonedDtm.format(ISO_OFFSET_DATE_TIME);
    }

    /**
     * Gera os arquivos com o código de criação do dataset para a determinada linguagem
     */
    public abstract void gerar();

    /**
     * Cria o grafo Usuario
     */
    protected abstract String gerarUsuarios();

    /**
     * Cria o grafo Post
     */
    protected abstract String gerarPosts();

    /**
     * Cria o grafo Tag
     */
    protected abstract String gerarTags();

    /**
     * Cria a aresta (Post - PUBLICADO_POR -> Usuario)
     */
    protected abstract String gerarPublicacoes();

    /**
     * Cria a aresta (Usuario - SEGUE -> Usuario)
     */
    protected abstract String gerarRelacionamentos();

    /**
     * Cria a aresta (Usuario - CURTIU -> Post)
     */
    protected abstract String gerarCurtidas();

    /**
     * Cria a aresta (Usuario - COMENTOU -> Post)
     */
    protected abstract String gerarComentarios();

    /**
     * Cria a aresta (Usuario - COMPARTILHOU -> Post)
     */
    protected abstract String gerarCompartilhamentos();

}
