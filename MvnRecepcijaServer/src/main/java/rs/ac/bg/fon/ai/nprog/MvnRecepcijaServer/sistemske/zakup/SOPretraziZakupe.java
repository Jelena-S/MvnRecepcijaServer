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
public class SOPretraziZakupe extends OpstaSistemskaOperacija{

    List<ZakupSobe> zakupi = new ArrayList<>();
    List<String> columns = new ArrayList<>();
    List<String> values = new ArrayList<>();

    public SOPretraziZakupe(List<String> columns,List<String> values) {
        this.columns=columns;
        this.values=values;
    }

    
    
    public List<ZakupSobe> getZakupi() {
        return zakupi;
    }
    
    
    
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
