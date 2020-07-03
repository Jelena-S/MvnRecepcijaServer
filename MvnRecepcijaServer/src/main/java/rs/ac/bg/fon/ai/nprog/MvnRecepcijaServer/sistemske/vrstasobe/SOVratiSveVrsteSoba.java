/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.vrstasobe;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za pregled svih vrsta soba koje su zabelezene u bazi. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 *
 * @author Jelena Sreckovic
 */
public class SOVratiSveVrsteSoba extends OpstaSistemskaOperacija{
    
	/**
	 * Lista vrsta soba
	 */
    List<VrstaSobe> vrste = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju
     */
    public SOVratiSveVrsteSoba() {
        
    }

    /**
     * Vraca listu vrsta soba
     * 
     * @return vrste kao List<VrstaSobe>
     */
    public List<VrstaSobe> getVrste() {
        return vrste;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju pregleda vrsta soba. Pristupa bazi i nalazi podatke o vrstama soba
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */    
    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> odo = Konekcija.getInstance().getSveOpsteDomenskeObjekte(new VrstaSobe());
        
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : odo) {
            VrstaSobe vs = (VrstaSobe) opstiDomenskiObjekat;
            
            vrste.add(vs);
        }
        
    }
    
}
