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
 * Klasa koja predstavlja sistemsku operaciju za pretrazivanje soba. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 * 
 * @author Jelena Sreckovic
 */
public class SOPretraziSobe extends OpstaSistemskaOperacija{
	
	/**
	 * Lista soba
	 */
    List<Soba> sobe = new ArrayList<>();
    
    /**
     * Lista kolona koje predstavljaju kriterijum pretrage soba
     */
    List<String> columns = new ArrayList<>();
    
    /**
     * Lista vrednosti za kolone koje predstavljaju kriterijum pretrage soba
     */
    List<String> values = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju i postavlja kolone i vrednosti za pretragu
     * 
     * @param columns kolone koje predstavljaju kriterijum pretrage sobe
     * @param values vrednosti za kolone koje predstavljaju kriterijum pretrage sobe
     */
    public SOPretraziSobe(List<String> columns,List<String> values) {
        
        this.columns=columns;
        this.values=values;
    }

    /**
     * Metoda vraca sobe
     * 
     * @return sobe kao List<Soba>
     */
    public List<Soba> getSobe() {
        return sobe;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju pretrazivanja soba. Pristupa bazi i nalazi podatke o sobama na osnovu prosledjenog kriterijuma
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    @Override
    protected void operacija() throws Exception {
        ArrayList<OpstiDomenskiObjekat> list = (ArrayList<OpstiDomenskiObjekat>) Konekcija.getInstance().getSveOpsteDomenskeObjekteSaWhere(new Soba(), columns, values);
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : list) {
            Soba s = (Soba) opstiDomenskiObjekat;
            VrstaSobe vs = (VrstaSobe) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new VrstaSobe(), s.getVrstaSobe().getVrstaSobeID());
            s.setVrstaSobe(vs);
            sobe.add(s);
        }
    }
    
}
