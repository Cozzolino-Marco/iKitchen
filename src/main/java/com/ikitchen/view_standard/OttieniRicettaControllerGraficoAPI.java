package com.ikitchen.view_standard;

import com.ikitchen.model.bean.BeanRicetta;
import com.ikitchen.model.bean.BeanRicette;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;

public class OttieniRicettaControllerGraficoAPI {

    // Settaggio manuale del numero di pagine di ricette del sito web da visualizzare
    private static final int MAX_PAGES = 3;

    // Metodo per il recupero delle ricette
    public BeanRicette recuperaRicette(BeanRicette infoPerListaRicette) throws IOException, SQLException {

        // Dichiarazioni iniziali
        String categoria = infoPerListaRicette.getCategoria();
        BeanRicette resultListRicette = new BeanRicette();

        // Iterazione per il popolamento di ricette di più pagine del sito web
        for (int i = 1; i <= MAX_PAGES; i++) {
            String url = generateUrl(categoria, i);
            Document doc = Jsoup.connect(url).get();

            // Se la pagina non esiste o è vuota, interrompi l'iterazione
            if (doc == null || doc.select("article.gz-card.gz-card-horizontal.gz-mBottom3x").isEmpty()) {
                break;
            }

            // Chiamata al metodo per web scraping e costruzione lista ricette
            BeanRicette ricetteDaPagina = scrapeRicette(doc);
            resultListRicette.getListRicette().addAll(ricetteDaPagina.getListRicette());
        }
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
            Blob immagine = imageControlUrl(docRicetta, immagineElement);

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

    // Metodo che genera l'URL al quale connettersi
    public String generateUrl(String categoria, int pageNumber) {

        // Link di riferimento
        String baseUrlCatalogo = "https://www.giallozafferano.it/ricette-cat/";
        String baseUrlRicerca = "https://www.giallozafferano.it/ricerca-ricette/";

        // Chiamata alla vera generazione dell'url
        String url = generateBaseUrl(categoria, pageNumber, baseUrlCatalogo, baseUrlRicerca);
        if (url == null) {
            return "Categoria non riconosciuta";  // URL corrotto
        }
        return url;
    }

    // Helper method che gestisce la logica della base URL
    private String generateBaseUrl(String categoria, int pageNumber, String baseUrlCatalogo, String baseUrlRicerca) {
        String categoryUrl;
        String baseUrl;

        switch (categoria.toLowerCase()) {
            case "colazione":
                categoryUrl = "colazione/";
                baseUrl = baseUrlRicerca;
                break;
            case "pasto veloce":
                categoryUrl = "panini/";
                baseUrl = baseUrlRicerca;
                break;
            case "bevande":
                categoryUrl = "Bevande/";
                baseUrl = baseUrlCatalogo;
                break;
            case "primi piatti":
                categoryUrl = "Primi/";
                baseUrl = baseUrlCatalogo;
                break;
            case "secondi piatti":
                categoryUrl = "Secondi-piatti/";
                baseUrl = baseUrlCatalogo;
                break;
            case "contorni":
                categoryUrl = "contorni/";
                baseUrl = baseUrlRicerca;
                break;
            case "dolci":
                categoryUrl = "Dolci-e-Desserts/";
                baseUrl = baseUrlCatalogo;
                break;
            default:
                return null; // Categoria non riconosciuta
        }

        // Chiamo il metodo per gestire la paginazione
        return addPagination(baseUrl, categoryUrl, pageNumber);
    }

    // Helper method che aggiunge la paginazione
    private String addPagination(String baseUrl, String categoryUrl, int pageNumber) {
        if (pageNumber > 1) {
            return baseUrl + "page" + pageNumber + "/" + categoryUrl;
        } else {
            return baseUrl + categoryUrl;
        }
    }

    // Metodo che converte gli URL relativi delle immagini in assoluti se necessario
    public Blob imageControlUrl(Document docRicetta, Element immagineElement) throws SQLException, IOException {

        // Variabili utili
        byte[] immagineTemp;
        String newUrlImmagine = null;

        // Estraggo l'attributo "src" dal tag <img> di HTML e lo memorizzo come stringa
        String urlImmagine = immagineElement.attr("src");

        // Controlla se l'URL è relativo
        if (!urlImmagine.startsWith("http")) {

            // Siccome l'url è relativo, l'immagine viene presa in un'altra div rispetto alla precedente in cui è presente
            Element newImmagineElement = docRicetta.select("div.gz-featured-image-video.gz-type-photo img").first();
            newUrlImmagine = newImmagineElement.attr("src");

            // Crea connessione alla nuova URL image, trattando il contenuto dell'immagine come un file binario
            immagineTemp = Jsoup.connect(newUrlImmagine).ignoreContentType(true).execute().bodyAsBytes();

        } else {
            // Crea connessione all'URL image classica, trattando il contenuto dell'immagine come un file binario
            immagineTemp = Jsoup.connect(urlImmagine).ignoreContentType(true).execute().bodyAsBytes();
        }

        // Converte l'immagine da array di byte a Blob e la restituisce
        return new SerialBlob(immagineTemp);
    }

    // Metodo personale per convertire da stringa a intero gestendo i casi di stringa vuota
    private int parseStringToInt(String str) {
        String numeriSolo = str.replaceAll("\\D", "");
        return numeriSolo.isEmpty() ? 0 : Integer.parseInt(numeriSolo);
    }
}