/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.zakup;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;

/**
 * Klasa koja predstavlja sistemsku operaciju za pretrazivanje zakupa. Nasledjuje apstraktnu klasu OpstaSistemskaOperacija
 * 
 * @author Jelena Sreckovic
 */
public class SOPretraziZakupe extends OpstaSistemskaOperacija{

	/**
	 * Lista zakupa koji su nadjeni
	 */
    List<ZakupSobe> zakupi = new ArrayList<>();
    
    /**
     * Lista kolona koje predstavljaju kriterijum pretrage zakupa soba
     */
    List<String> columns = new ArrayList<>();
    
    /**
     * Lista vrednosti za kolone koje predstavljaju kriterijum pretrage zakupa soba
     */
    List<String> values = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju i postavlja kolone i vrednosti za pretragu
     * 
     * @param columns kolone koje predstavljaju kriterijum pretrage zakupa soba
     * @param values vrednosti za kolone koje predstavljaju kriterijum pretrage zakupa soba
     */
    
    public SOPretraziZakupe(List<String> columns,List<String> values) {
        this.columns=columns;
        this.values=values;
    }

    /**
     * Metoda vraca zakupe
     * 
     * @return zakupi kao List<ZakupSobe>
     */    
    public List<ZakupSobe> getZakupi() {
        return zakupi;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju pretrazivanja zakupa. Pristupa bazi i nalazi podatke o zakupima na osnovu prosledjenog kriterijuma
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */    
    @Override
    protected void operacija() throws Exception {
        String join = "gost JOIN zakupsobe on gost.gostID=zakupsobe.gostZakupljujeID join soba on zakupsobe.zakupljenaSobaID=soba.sobaID";
        
        ArrayList<OpstiDomenskiObjekat> list = (ArrayList<OpstiDomenskiObjekat>) Konekcija.getInstance().getSveOpsteDomenskeObjekteJOIN(new ZakupSobe(), columns, values, join);
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : list) {
            ZakupSobe zs = (ZakupSobe) opstiDomenskiObjekat;
            //gost i soba da se naprave, IZMENI< DA NE PRISTUPA VISE PUTA BAZI////////////////////
            Gost g = (Gost) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Gost(), zs.getGostZakupljuje().getGostID());
            zs.setGostZakupljuje(g);
            Soba s = (Soba) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Soba(), zs.getZakupljenaSoba().getSobaID());
            zs.setZakupljenaSoba(s);
            zakupi.add(zs);
        }
    }
    
}
