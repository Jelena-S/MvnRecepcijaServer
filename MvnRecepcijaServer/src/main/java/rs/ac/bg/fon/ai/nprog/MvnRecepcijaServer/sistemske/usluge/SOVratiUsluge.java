/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.usluge;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za pregled svih usluga najma koje su zabelezene u bazi. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 *
 * @author Jelena Sreckovic
 */
public class SOVratiUsluge extends OpstaSistemskaOperacija{
	/**
	 * Lista usluga najma
	 */
    List<UslugaNajma> usluge = new ArrayList<>();
    
    /**
     * Lista kolona koje predstavljaju kriterijum pretrage usluga
     */
    List<String> columns = new ArrayList<>();
    
    /**
     * Lista vrednosti za kolone koje predstavljaju kriterijum pretrage usluga
     */
    List<String> values = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju i postavlja kolone i vrednosti za pretragu
     * 
     * @param columns kolone koje predstavljaju kriterijum pretrage usluga
     * @param values vrednosti za kolone koje predstavljaju kriterijum pretrage usluga
     */
    public SOVratiUsluge(List<String> columns,List<String> values) {
        this.columns=columns;
        this.values=values;
    }

    /**
     * Metoda vraca usluge
     * 
     * @return usluge kao List<UslugaNajma>
     */
    public List<UslugaNajma> getUsluge() {
        return usluge;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju pretrazivanja usluga. Pristupa bazi i nalazi podatke o uslugama na osnovu prosledjenog kriterijuma
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> odo = Konekcija.getInstance().getSveOpsteDomenskeObjekteSaWhere(new UslugaNajma(), columns, values);
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : odo) {
            UslugaNajma un = (UslugaNajma) opstiDomenskiObjekat;
            //informacija o katalogID????
            StavkaKataloga sk = (StavkaKataloga) Konekcija.getInstance().getOpstiDomenskiObjekatPoSlozenomPrimarnomKljucu(new StavkaKataloga(),Long.valueOf(1111), un.getStavkaKataloga().getStavkaKatalogaID());
            un.setStavkaKataloga(sk);
            usluge.add(un);
            //vrsta sobe
        }
    }
    
}
