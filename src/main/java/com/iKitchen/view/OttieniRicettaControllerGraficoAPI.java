package com.iKitchen.view;

import com.iKitchen.model.bean.BeanRicetta;
import com.iKitchen.model.bean.BeanRicette;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;

public class OttieniRicettaControllerGraficoAPI {

    public BeanRicette recuperaRicette(BeanRicette infoPerListaRicette) throws IOException, SQLException {

        // Estraggo le informazioni dal bean
        String categoria = infoPerListaRicette.getCategoria();
        String url = generateUrl(categoria);

        // Apertura di connessione
        Document doc = Jsoup.connect(url).get();

        // Controllo se la pagina è vuota o non contiene i dati attesi
        if (doc == null) {
            // TODO: Eccezione
        }

        BeanRicette resultListRicette = scrapeRicette(doc);
        return resultListRicette;
    }

    // Metodo per il web scraping delle ricette
    public BeanRicette scrapeRicette(Document doc) throws IOException, SQLException {

        BeanRicette ricetteBean = new BeanRicette();

        // Seleziona tutti gli articoli delle ricette
        Elements articoliRicette = doc.select("article.gz-card.gz-card-horizontal.gz-mBottom3x");

        // Itera su ciascun articolo per estrarre il titolo e altre informazioni
        for (Element articolo : articoliRicette) {

            // Crea una nuova istanza di BeanRicetta per ogni ricetta
            BeanRicetta beanRicetta = new BeanRicetta();

            // Estrai il titolo della ricetta dal web
            String titolo = articolo.select("h2.gz-title a").text();

            // Estrai il link alla pagina della ricetta dal web
            String linkRicetta = articolo.select("h2.gz-title a").attr("href");

            // Apri la pagina della ricetta per ottenere ulteriori dettagli
            Document docRicetta = Jsoup.connect(linkRicetta).get();

            // Estrai dati per preparazione e cottura dal web e li imposta a 0 in caso di null
            String preparazione = docRicetta.select("span.gz-name-featured-data strong").get(1).text();
            preparazione = (preparazione != null) ? preparazione : "0";
            String cottura = docRicetta.select("span.gz-name-featured-data strong").get(2).text();
            cottura = (cottura != null) ? cottura : "0";

            // Prendi solo i numeri dalle stringhe e convertili in interi
            int preparazioneMinuti = parseStringToInt(preparazione);
            int cotturaMinuti = parseStringToInt(cottura);

            // Somma dei minuti di preparazione e cottura
            int durataPreparazioneTotale = preparazioneMinuti + cotturaMinuti;

            // Estrai le calorie dal web
            Element calorieElement = docRicetta.select("div.gz-text-calories-total").first();
            String calorieRecuperate = calorieElement != null ? calorieElement.text().replaceAll("\\D", "") : "";
            int calorie = !calorieRecuperate.isEmpty() ? Integer.parseInt(calorieRecuperate) : 0;

            // Estrai l'immagine della ricetta dal web e fai le conversioni opportune
            Element immagineElement = docRicetta.select("div.gz-content-recipe.gz-mBottom4x img").first();
            String urlImmagine = immagineElement.attr("src");
            byte[] immagineTemp = Jsoup.connect(urlImmagine).ignoreContentType(true).execute().bodyAsBytes();
            Blob immagine = new SerialBlob(immagineTemp);

            // Costruzione del bean ricetta da restituire alla funzione principale
            beanRicetta.setTitolo(titolo);
            beanRicetta.setCuoco("Giallo Zafferano");
            beanRicetta.setDurataPreparazione(durataPreparazioneTotale);
            beanRicetta.setCalorie(calorie);
            beanRicetta.setImmagine(immagine);
            beanRicetta.setLinkRicetta(linkRicetta);
            ricetteBean.addRicetta(beanRicetta);
        }
        return ricetteBean;
    }

    // Metodo che trasforma la categoria passata in base a quelle presenti sul sito web
    public String generateUrl(String categoria) {

        String url;

        switch (categoria) {
            case "Colazione":
                url = "https://www.giallozafferano.it/ricerca-ricette/colazione/";
                break;
            case "Pasto veloce":
                url = "https://www.giallozafferano.it/ricerca-ricette/panini/";
                break;
            case "Bevande":
                url = "https://www.giallozafferano.it/ricette-cat/Bevande/";
                break;
            case "Primi piatti":
                url = "https://www.giallozafferano.it/ricette-cat/Primi/";
                break;
            case "Secondi piatti":
                url = "https://www.giallozafferano.it/ricette-cat/Secondi-piatti/";
                break;
            case "Contorni":
                url = "https://www.giallozafferano.it/ricerca-ricette/contorni/";
                break;
            case "Dolci":
                url = "https://www.giallozafferano.it/ricette-cat/Dolci-e-Desserts/";
                break;
            default:
                url = "Categoria non riconosciuta";
                break;
        }
        return url;
    }

    // Metodo per convertire stringa a intero gestendo i casi di stringa vuota
    private int parseStringToInt(String str) {
        String numeriSolo = str.replaceAll("\\D", "");
        return numeriSolo.isEmpty() ? 0 : Integer.parseInt(numeriSolo);
    }
}