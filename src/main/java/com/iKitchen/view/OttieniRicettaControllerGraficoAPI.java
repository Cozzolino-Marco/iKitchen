package com.iKitchen.view;

import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import com.iKitchen.model.domain.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;

public class OttieniRicettaControllerGraficoAPI {

    public void recuperaRicette(BeanRicette infoPerListaRicette) throws IOException {

        // Estraggo le informazioni dal bean
        String categoria = infoPerListaRicette.getCategoria();

        /* Informazione codificata per la richiesta in internet
        String categoriaEncoded = requestEncode(categoria);*/

        // URL a cui eseguire la richiesta GET
        //String url = String.valueOf(new URL("https://ricette.giallozafferano.it/" + categoria + "/" + titoloRicettaEncoded + ".html"));
        //String url = String.valueOf(new URL("https://ricette.giallozafferano.it/" + titoloRicettaEncoded + ".html")); // Per una ricetta particolare dal titolo
        String url = String.valueOf(new URL("https://www.giallozafferano.it/ricette-cat/" + categoria + "/"));

        // Apertura di connessione
        Document doc = Jsoup.connect(url).get();

        // Controllo se la pagina è vuota o non contiene i dati attesi
        if (doc == null) {
            // TODO: Eccezione
        } else {
            ListRicette resultListRicette = scrapeRicette(doc, categoria);

            // Logica per salvare o gestire il risultato
            if (resultListRicette != null) {
                //System.out.println("Ricetta trovata: " + resultListRicette.getTitolo());
            } else {
                System.out.println("Ricette non trovata.");
            }
        }
    }

    public ListRicette scrapeRicette(Document doc, String categoria) throws IOException {

        BeanRicette ricetteBean = new BeanRicette();
        BeanRicetta beanRicetta = new BeanRicetta();
        ListRicette listRicette = new ListRicette();

        // Seleziona tutti gli articoli delle ricette
        Elements articoliRicette = doc.select("article.gz-card.gz-card-horizontal.gz-mBottom3x");

        // Itera su ciascun articolo per estrarre il titolo e altre informazioni
        for (Element articolo : articoliRicette) {

            // Estrai il titolo della ricetta dal web
            String titolo = articolo.select("h2.gz-title a").text();

            // Estrai il link alla pagina della ricetta dal web
            String linkRicetta = articolo.select("h2.gz-title a").attr("href");

            // Apri la pagina della ricetta per ottenere ulteriori dettagli
            Document docRicetta = Jsoup.connect(linkRicetta).get();

            // Estrai dati per preparazione e cottura dal web
            String preparazione = docRicetta.select("span.gz-name-featured-data strong").get(1).text();
            String cottura = docRicetta.select("span.gz-name-featured-data strong").get(2).text();

            // Prendi solo i numeri dalle stringhe e convertili in interi
            int preparazioneMinuti = Integer.parseInt(preparazione.replaceAll("\\D", ""));
            int cotturaMinuti = Integer.parseInt(cottura.replaceAll("\\D", ""));

            // Somma dei minuti di preparazione e cottura
            int durataPreparazioneTotale = preparazioneMinuti + cotturaMinuti;

            // Estrai le calorie dal web e trasformale in intero
            String calorieRecuperate = docRicetta.select("div.gz-text-calories-total").first().text();
            calorieRecuperate = (calorieRecuperate != null && !calorieRecuperate.isEmpty()) ? calorieRecuperate : "0";
            int calorie = Integer.parseInt(calorieRecuperate);

            // Estrai l'immagine della ricetta dal web
            Blob immagine = docRicetta.select("div.qualcosa");

            // Controlla se i campi estratti sono nulli e assegna "TBA" se lo sono
            titolo = (titolo != null && !titolo.isEmpty()) ? titolo : "TBA";
            linkRicetta = (linkRicetta != null && !linkRicetta.isEmpty()) ? linkRicetta : "TBA";
            durataPreparazioneTotale = (durataPreparazioneTotale != 0) ? durataPreparazioneTotale : 0;

            // Dichiarazioni ed instanziazioni
            FactoryRicetta factoryRicetta = new FactoryRicetta();
            Ricetta ricetta = factoryRicetta.createRicetta(categoria);

            // Costruzione del bean da restituire al controller applicativo
            ricetta.setTitolo(titolo);
            ricetta.setCuoco("Giallo Zafferano");
            ricetta.setDurataPreparazione(durataPreparazioneTotale);
            ricetta.setCalorie(calorie);
            ricetta.setImmagine(immagine);
            ricetta.setLinkRicetta(linkRicetta);
            listRicette.addRicetta(ricetta);

            // Testing
            System.out.println("Ricetta trovata: " + ricetta.getTitolo());
            System.out.println("Link alla ricetta: " + linkRicetta);
            System.out.println("Durata: " + ricetta.getDurataPreparazione());
            System.out.println("Calorie: " + ricetta.getCalorie());
            System.out.println();
        }
        return listRicette;
    }

    /*public Ricetta scrapeListRecipe(Document doc) throws IOException {

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
    }*/

    private String requestEncode(String value) throws UnsupportedEncodingException {
        String encoded = URLEncoder.encode(value, StandardCharsets.UTF_8.toString()); // Encoding standard
        return encoded.replace("+", "-"); // Sostituzione dei "+" con "-"
    }
}