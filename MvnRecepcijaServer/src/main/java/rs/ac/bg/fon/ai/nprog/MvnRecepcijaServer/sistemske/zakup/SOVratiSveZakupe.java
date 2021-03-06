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
 *
 * @author Win10
 */
public class SOVratiSveZakupe extends OpstaSistemskaOperacija{
	
	/**
	 * Lista zakupa 
	 */
    List<ZakupSobe> zakupi = new ArrayList<>();

    /**
     * Parametrizovani konstruktor. Inicijalizuje sistemsku operaciju
     */
    public SOVratiSveZakupe() {
    }

    /**
     * Metoda vraca listu zakupa
     * 
     * @return zakupi kao List<ZakupSobe>
     */    
    public List<ZakupSobe> getZakupi() {
        return zakupi;
    }
    
    /**
     * Metoda izvrsava sistemsku operaciju pregleda zakupa. Pristupa bazi i nalazi podatke o zakupima
     * 
     * @throws Exception ako se operacija ne izvrsi uspesno
     */
    @Override
    protected void operacija() throws Exception {
        List<OpstiDomenskiObjekat> list = Konekcija.getInstance().getSveOpsteDomenskeObjekte(new ZakupSobe());
        
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : list) {
            ZakupSobe zs = (ZakupSobe) opstiDomenskiObjekat;
            
            Soba s = (Soba) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Soba(), zs.getZakupljenaSoba().getSobaID());
            zs.setZakupljenaSoba(s);
            
            Gost g = (Gost) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Gost(), zs.getGostZakupljuje().getGostID());
            zs.setGostZakupljuje(g);
            
            Recepcioner r = (Recepcioner) Konekcija.getInstance().getOpstiDomenskiObjekatPoPrimarnomKljucu(new Recepcioner(), zs.getRecepcioner().getRecepcionerID());
            zs.setRecepcioner(r);
            zakupi.add(zs);
        }
    }
    
}
