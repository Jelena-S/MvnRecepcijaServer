/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.soba;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za pregled svih soba koje su zabelezene u bazi. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 *
 * @author Jelena Sreckovic
 */
public class SOVratiSveSobe extends OpstaSistemskaOperacija{
	
	/**
	 * Lista soba
	 */
    List<Soba> sobe = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju.
     */
    public SOVratiSveSobe() {
        
    }

    /**
     * Vraca listu soba
     * 
     * @return sobe kao List<Soba>
     */
    public List<Soba> getSobe() {
        return sobe;
    }

    /**
     * Metoda izvrsava sistemsku operaciju pregleda soba. Pristupa bazi i nalazi podatke o sobama
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */   
    @Override
    protected void operacija() throws Exception {
        ArrayList<OpstiDomenskiObjekat> list = (ArrayList<OpstiDomenskiObjekat>) Konekcija.getInstance().getSveOpsteDomenskeObjekte(new Soba());
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : list) {
            Soba s = (Soba) opstiDomenskiObjekat;
            VrstaSobe vs = (VrstaSobe) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new VrstaSobe(), s.getVrstaSobe().getVrstaSobeID());
            s.setVrstaSobe(vs);
            sobe.add(s);
        }
    }
    
    
    
}
