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
 * Klasa koja predstavlja sistemsku operaciju za pretrazivanje gostiju. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 *
 * @author Jelena Sreckovic
 */
public class SOPretraziGoste extends OpstaSistemskaOperacija{
	
	/**
	 * Lista gostiju
	 */
    List<Gost> gosti = new ArrayList<>();
    
    /**
     * Lista kolona koje predstavljaju kriterijum pretrage gostiju
     */
    List<String> columns = new ArrayList<>();
    
    /**
     * Lista vrednosti za kolone koje predstavljaju kriterijum pretrage gostiju
     */
    List<String> values = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju i postavlja kolone i vrednosti za pretragu
     * 
     * @param columns kolone koje predstavljaju kriterijum pretrage gostiju
     * @param values vrednosti za kolone koje predstavljaju kriterijum pretrage gostiju
     */
    public SOPretraziGoste(List<String> columns,List<String> values) {
        
        this.columns=columns;
        this.values=values;
    }

    /**
     * Metoda vraca goste
     * 
     * @return gosti kao List<Gost>
     */
    public List<Gost> getGosti() {
        return gosti;
    }

    /**
     * Metoda izvrsava sistemsku operaciju pretrazivanja gostiju. Pristupa bazi i nalazi podatke o gostima na osnovu prosledjenog kriterijuma
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> odo = Konekcija.getInstance().getSveOpsteDomenskeObjekteSaWhere(new Gost(), columns, values);
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : odo) {
            Gost g = (Gost) opstiDomenskiObjekat;
            Recepcioner r = (Recepcioner) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Recepcioner(), g.getRecepcioner().getRecepcionerID());
            g.setRecepcioner(r);
            gosti.add(g);
        }
    }
    
}
