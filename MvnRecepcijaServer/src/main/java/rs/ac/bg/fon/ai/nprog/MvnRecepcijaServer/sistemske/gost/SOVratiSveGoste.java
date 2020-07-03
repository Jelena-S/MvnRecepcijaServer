/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za pregled svih gostiju koji su zabelezeni u bazi. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 * 
 * @author Jelena Sreckovic
 */
public class SOVratiSveGoste extends OpstaSistemskaOperacija{
	
	/**
	 * Lista gostiju
	 */
    List<Gost> gosti = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju i listu gostiju.
     */
    public SOVratiSveGoste() {
        
    }

    /**
     * Metoda vraca listu gostiju
     * 
     * @return gosti kao List<Gost>
     */
    public List<Gost> getGosti() {
        return gosti;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju pregleda gostiju. Pristupa bazi i nalazi podatke o gostima
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> odo = Konekcija.getInstance().getSveOpsteDomenskeObjekte(new Gost());
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : odo) {
            Gost g = (Gost) opstiDomenskiObjekat;
            Recepcioner r = (Recepcioner) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Recepcioner(), g.getRecepcioner().getRecepcionerID());
            g.setRecepcioner(r);
            gosti.add(g);
        }
    }
    
}
