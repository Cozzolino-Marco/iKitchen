package com.ikitchen.controller;

import com.ikitchen.exception.DAOException;
import com.ikitchen.model.bean.BeanIngrediente;
import com.ikitchen.model.bean.BeanIngredienti;
import com.ikitchen.model.dao.RecuperaIngredientiDispensaDAO;
import com.ikitchen.model.utility.Credentials;
import com.ikitchen.model.domain.Ingrediente;
import com.ikitchen.model.domain.ListIngredienti;
import java.sql.SQLException;
import java.util.Date;

public class OttieniIngredientiControllerApplicativo {

    // Dichiarazione
    private final RecuperaIngredientiDispensaDAO recuperaIngredientiDispensaDAO;

    // Costruttore per inizializzare il DAO
    public OttieniIngredientiControllerApplicativo() {
        this.recuperaIngredientiDispensaDAO = new RecuperaIngredientiDispensaDAO();
    }

    // Metodo per mostrare gli ingredienti validi
    public BeanIngredienti mostraIngredientiValidi() throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String username = Credentials.getUsername();

        // Eseguo la query usando il DAO e ottengo il risultato
        ListIngredienti listIngredienti = recuperaIngredientiDispensaDAO.execute(username);

        // Creo un nuovo bean per restituire gli ingredienti alla vista
        BeanIngredienti ingredientiValidi = new BeanIngredienti(username);
        Date oggi = new Date();

        // Itero attraverso la lista di ingredienti e le aggiungo al BeanIngredienti
        for (Ingrediente ingrediente : listIngredienti.getListaIngredienti()) {
            if (ingrediente.getScadenza().after(oggi) && ingrediente.getQuantita() > 0) {
                BeanIngrediente beanIngrediente = new BeanIngrediente(
                    ingrediente.getCodIngrediente(),
                    ingrediente.getNome(),
                    ingrediente.getScadenza(),
                    ingrediente.getQuantita(),
                    ingrediente.getLimite(),
                    ingrediente.getImmagine(),
                    ingrediente.getTipo()
                );
                ingredientiValidi.addIngrediente(beanIngrediente);
            }
        }
        return ingredientiValidi;
    }

    // Metodo per mostrare gli ingredienti non validi
    public BeanIngredienti mostraIngredientiNonValidi() throws DAOException, SQLException {

        // Estraggo le informazioni dal bean
        String username = Credentials.getUsername();

        // Eseguo la query usando il DAO e ottengo il risultato
        ListIngredienti listIngredienti = recuperaIngredientiDispensaDAO.execute(username);

        // Creo un nuovo bean per restituire gli ingredienti alla vista
        BeanIngredienti ingredientiNonValidi = new BeanIngredienti(username);
        Date oggi = new Date();

        // Itero attraverso la lista di ingredienti e le aggiungo al BeanIngredienti
        for (Ingrediente ingrediente : listIngredienti.getListaIngredienti()) {
            if (ingrediente.getScadenza().before(oggi) || ingrediente.getQuantita() <= 0) {
                BeanIngrediente beanIngrediente = new BeanIngrediente(
                        ingrediente.getCodIngrediente(),
                        ingrediente.getNome(),
                        ingrediente.getScadenza(),
                        ingrediente.getQuantita(),
                        ingrediente.getLimite(),
                        ingrediente.getImmagine(),
                        ingrediente.getTipo()
                );
                ingredientiNonValidi.addIngrediente(beanIngrediente);
            }
        }
        return ingredientiNonValidi;
    }
}