/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.Gost;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za izmenu gosta. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 *
 * @author Jelena Sreckovic
 */
public class SOIzmeniGosta extends OpstaSistemskaOperacija{
	
	/**
	 * Gost kog treba izmeniti
	 */
    Gost gost;
    
    /**
     * Indikator uspesnosti izvrsenja sistemske operacije
     */
    boolean uspesno;

    /**
     * Parametrizovani konstruktor. Inicijalizuje operaciju i postavlja gosta kog treba izmeniti
     * 
     * @param gost kog treba izmeniti
     */
    public SOIzmeniGosta(Gost gost) {
        this.gost = gost;
    }

    /**
     * Metoda vraca gosta
     * 
     * @return gost kao Gost
     */
    public Gost getGost() {
        return gost;
    }

    /**
     * Metoda vraca informaciju da li je operacija uspesno izvrsena
     * 
     * @return uspesno kao boolean
     */
    public boolean isUspesno() {
        return uspesno;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju izmene gosta. Pristupa bazi i menja podatke o gostu na prosledjene podatke
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    @Override
    protected void operacija() throws Exception {
        uspesno = Konekcija.getInstance().azurirajOpstiDomenskiObjekat(gost);
    }
    
}
