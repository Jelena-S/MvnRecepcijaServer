package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.kontroler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.konstante.Operacije;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.transfer.Odgovor;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konekcija.Konekcija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.forme.ServerskaForma;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.niti.ObradaKlijentskihZahteva;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.OpstaSistemskaOperacija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.gost.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.recepcioner.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.soba.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.usluge.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.vrstasobe.*;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.sistemske.zakup.*;

public class Kontroler {
	private static Kontroler instanca;
    private final List<ObradaKlijentskihZahteva> klijenti;
    ServerskaForma forma;

    public void setForma(ServerskaForma forma) {
        this.forma = forma;
    }

    
    
    public Kontroler() {
        klijenti = new LinkedList<>();
    }
    
    public static Kontroler getInstanca() {
        if(instanca==null) {
            instanca=new Kontroler();
        }
        return instanca;
    }
    
    public void removeClient(ObradaKlijentskihZahteva client) {
        try {
            client.interrupt();
            client.getSocket().close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Greska prilikom zatvaranja soketa");
        }
        System.out.println("Remove " + client.getOnlineReceptionist().getKorisnickoIme());
        klijenti.remove(client);
        
        System.out.println("Ostali su: ");
        if(klijenti.size()>0){
            for (ObradaKlijentskihZahteva obradaKlijentskihZahteva : klijenti) {
                System.out.println(obradaKlijentskihZahteva.getOnlineReceptionist().getKorisnickoIme());
            }
        }

    }

    public List<ObradaKlijentskihZahteva> getClientThreads() {
        return klijenti;
    }

    public void addClient(ObradaKlijentskihZahteva klijent) {
        klijenti.add(klijent);
        System.out.println("DOdao je");
        
    }

    public void logout(ObradaKlijentskihZahteva clientThread) {
        removeClient(clientThread);
    }

    public void logoutClients() {
        for (ObradaKlijentskihZahteva clientThread : klijenti) {
            try {
                Odgovor r = new Odgovor();
                r.setPoruka("Server nije dostupan. Dovidjenja.");
                r.setOperacija(Operacije.LOGOUT);
                clientThread.posaljiOdgovor(r);
                obrisi(clientThread.getOnlineReceptionist());
            } catch (Exception ex) {//IOException je bio
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void postaviOnlineKorisnike() {
        //forma.setKlijenti(klijenti);
        
    }
    
    public void dodaj(Recepcioner r) {
        forma.dodaj(r);
    }
    
    public void obrisi(Recepcioner onlineReceptionist) {
        forma.obrisi(onlineReceptionist);
    }
    
    public Recepcioner nadjiRecepcionera(Recepcioner recepcioner) throws Exception {
        OpstaSistemskaOperacija so = new SONadjiRecepcionera(recepcioner);
        so.izvrsenje();
        return ((SONadjiRecepcionera) so).getRecepcioner();
    }
    
    public Gost kreirajGosta(Gost gost) throws Exception {
        OpstaSistemskaOperacija so = new SOKreirajGosta(gost);
        so.izvrsenje();
        return ((SOKreirajGosta) so).getGost();
    }
    
    public boolean izmeniGosta(Gost gost) throws Exception {
        OpstaSistemskaOperacija so = new SOIzmeniGosta(gost);
        so.izvrsenje();
        return ((SOIzmeniGosta) so).isUspesno();
    }
    
    public boolean obrisiGosta(Gost gost) throws Exception {
        OpstaSistemskaOperacija so = new SOObrisiGosta(gost);
        so.izvrsenje();
        return ((SOObrisiGosta) so).isUspesno();
    }
    
    public List<Gost> pretraziGoste(List<String> columns, List<String> values) throws Exception {
        OpstaSistemskaOperacija so = new SOPretraziGoste(columns, values);
        so.izvrsenje();
        return ((SOPretraziGoste) so).getGosti();
    }
    
    public List<Gost> vratiSveGoste() throws Exception {
        OpstaSistemskaOperacija so = new SOVratiSveGoste();
        so.izvrsenje();
        return ((SOVratiSveGoste) so).getGosti();
    }
    
    public List<Soba> pretraziSobe(List<String> columns, List<String> values) throws Exception {
        OpstaSistemskaOperacija so = new SOPretraziSobe(columns, values);
        so.izvrsenje();
        return ((SOPretraziSobe) so).getSobe();
    }
    
    public List<Soba> vratiSveSobe() throws Exception {
        OpstaSistemskaOperacija so = new SOVratiSveSobe();
        so.izvrsenje();
        return ((SOVratiSveSobe)so).getSobe();
    }
    
    public List<VrstaSobe> vratiSveVrsteSoba() throws Exception {
        OpstaSistemskaOperacija so = new SOVratiSveVrsteSoba();
        so.izvrsenje();
        return ((SOVratiSveVrsteSoba)so).getVrste();
    }
    
    public ZakupSobe kreirajZakup(ZakupSobe zakupSobe) throws Exception {
        OpstaSistemskaOperacija so = new SOKreirajZakup(zakupSobe);
        so.izvrsenje();
        return ((SOKreirajZakup) so).getZakup();
    }
    
    public List<ZakupSobe> pretraziZakupe(List<String> columns, List<String> values) throws Exception {
        OpstaSistemskaOperacija so = new SOPretraziZakupe(columns,values);
        so.izvrsenje();
        return ((SOPretraziZakupe) so).getZakupi();
    }
    
    
    public List<ZakupSobe> vratiSveZakupe() throws Exception {
        OpstaSistemskaOperacija so = new SOVratiSveZakupe();
        so.izvrsenje();
        return ((SOVratiSveZakupe) so).getZakupi();
    }
    
    
    public long vratiID() throws SQLException {
        return Konekcija.getInstance().vratiID(new ZakupSobe());
    }
    
    public List<UslugaNajma> vratiUsluge(List<String> columns, List<String> values) throws Exception {
        OpstaSistemskaOperacija so = new SOVratiUsluge(columns, values);
        so.izvrsenje();
        return ((SOVratiUsluge) so).getUsluge();
    }
}
