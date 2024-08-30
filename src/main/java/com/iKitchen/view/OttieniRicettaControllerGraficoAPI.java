package com.iKitchen.view;

import com.iKitchen.model.domain.Ingrediente;
import com.iKitchen.model.domain.ListIngredienti;
import com.iKitchen.model.domain.Ricetta;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class OttieniRicettaControllerGraficoAPI {

    public void recuperaRicette() throws IOException {

        // Suppongo di avere già i due dati di filtraggio
        String titoloRicetta = "Spaghetti alla Carbonara";
        String categoria = "Primi";

        // Informazione codificata per la richiesta in internet
        String titoloRicettaEncoded = requestEncode(titoloRicetta);

        // URL a cui eseguire la richiesta GET
        //String url = String.valueOf(new URL("https://ricette.giallozafferano.it/" + categoria + "/" + titoloRicettaEncoded + ".html"));
        String url = String.valueOf(new URL("https://ricette.giallozafferano.it/" + titoloRicettaEncoded + ".html"));

        // Apertura di connessione
        Document doc = Jsoup.connect(url).get();

        // Controllo se la pagina è vuota o non contiene i dati attesi
        if (doc == null) {
            // TODO: Eccezione
        } else {
            // Chiamata alla classe per fare web scraping
            Ricetta resultRicetta = scrapeRecipe(doc);
        }

        // TODO: Vedere se fare altro

        /* Controllo del risultato finale
        if (resultRicetta != null) {
            // Logica per salvare la ricetta o altro
            System.out.println("Ricetta trovata: " + resultRicetta.getTitolo());
        } else {
            System.out.println("Ricetta non trovata.");
        }*/
    }

    public Ricetta scrapeRecipe(Document doc) throws IOException {

        // TODO: Rivedi logica
        String titolo = doc.select("h1.gz-title-recipe.gz-mBottom2x").text();
        String descrizione = doc.select("span.gz-name-featured-data").text();
        String chef = doc.select("span.chef").text();
        String calorie = doc.select("span.gz-text-calories-total").text();
        String durataPreparazione = doc.select("span.gz-name-featured-data").text();

        // Raccogli gli ingredienti e i passaggi in una lista
        Elements ingredientiElements = doc.select("ul.ingredients li");
        Elements passaggiElements = doc.select("ol.steps li");

        ListIngredienti ingredientiList = new ListIngredienti();
        for (Element ing : ingredientiElements) {
            Ingrediente ingrediente = new Ingrediente(ing.text());
            ingredientiList.addIngrediente(ingrediente);
        }

        StringBuilder passaggi = new StringBuilder();
        for (Element step : passaggiElements) {
            passaggi.append(step.text()).append("\n");
        }

        // Crea un oggetto Ricetta con i dati estratti
        Ricetta ricetta = new Ricetta(titolo, descrizione, chef, calorie, durataPreparazione, ingredientiList, passaggi.toString()) {
            @Override
            public String getCategoria() {
                return null;
            }
        };
        return ricetta;
    }

    private String requestEncode(String value) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString()); // Encoding standard
        return encoded.replace("+", "-"); // Sostituzione dei "+" con "-"
    }
}