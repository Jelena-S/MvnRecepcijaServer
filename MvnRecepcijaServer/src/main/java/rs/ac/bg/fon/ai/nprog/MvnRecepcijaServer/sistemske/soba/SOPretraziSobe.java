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
 *
 * @author Win10
 */
public class SOPretraziSobe extends OpstaSistemskaOperacija{
    List<Soba> sobe = new ArrayList<>();
    List<String> columns = new ArrayList<>();
    List<String> values = new ArrayList<>();

    public SOPretraziSobe(List<String> columns,List<String> values) {
        
        this.columns=columns;
        this.values=values;
    }

    public List<Soba> getSobe() {
        return sobe;
    }
    
    

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
