/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.niti;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.konstante.Operacije;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.istorija.Istorija;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.domen.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.kontroler.Kontroler;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.transfer.Odgovor;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaBiblioteka.transfer.Zahtev;

/**
 *
 * @author Jelena Sreckovic
 */
public class ObradaKlijentskihZahteva extends Thread{
    Socket soket;
    boolean kraj = false;
    Kontroler kontroler;
    Recepcioner onlineRecepcioner;

    public ObradaKlijentskihZahteva(Socket soket) {
        this.soket = soket;
        kontroler=Kontroler.getInstanca();
    }

    public Socket getSocket() {
        return soket;
    }

    public Recepcioner getOnlineReceptionist() {
        return onlineRecepcioner;
    }
    
    
    @Override
    public void run() {
        try{
            execute();
        } catch(IOException | ClassNotFoundException ex) {
            Kontroler.getInstanca().removeClient(this);
            this.interrupt();
        }
        
        System.out.println("Klijent "+ onlineRecepcioner.getKorisnickoIme() +" je otisao");
    }
    
    private void execute() throws IOException, ClassNotFoundException {
        while(!isInterrupted()) {
            Zahtev req = primiZahtev();
            Odgovor res = new Odgovor();
            
            switch(req.getOperacija()) {
                case Operacije.NADJI_RECEPCIONERA:
                    try {
                        Recepcioner r = (Recepcioner) req.getParametar();
                        r=kontroler.nadjiRecepcionera(r);
                        res.setOdgovor(r);
                        onlineRecepcioner = r;
                        //System.out.println("Postavio recepcionera "+Kontroler.getInstanca().getClientThreads().get(0).getOnlineReceptionist().getKorisnickoIme());
                        if(r!=null)Kontroler.getInstanca().dodaj(r);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.LOGOUT:
                    Kontroler.getInstanca().logout(this);
                    Kontroler.getInstanca().obrisi(this.getOnlineReceptionist());
                    break;
                case Operacije.KREIRAJ_GOSTA:
                    try {
                        Gost g = (Gost) req.getParametar();
                        g=kontroler.kreirajGosta(g);
                        res.setOdgovor(g);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.IZMENI_GOSTA:
                    try {
                        Gost g = (Gost) req.getParametar();
                        boolean uspesno=kontroler.izmeniGosta(g);
                        res.setOdgovor(uspesno);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(false);
                    }
                    break;
                case Operacije.OBRISI_GOSTA:
                    try {
                        Gost g = (Gost) req.getParametar();
                        boolean uspesno=kontroler.obrisiGosta(g);
                        res.setOdgovor(uspesno);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(false);
                    }
                    break;
                case Operacije.PRETRAZI_GOSTE:
                    try {
                        List<String> columns = (List<String>) req.getParametar();
                        List<String> values = (List<String>) req.getParametar2();
                        List<Gost> gosti = kontroler.pretraziGoste(columns, values);
                        res.setOdgovor(gosti);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.VRATI_SVE_GOSTE:
                    try {
                        List<Gost> gosti = kontroler.vratiSveGoste();
                        res.setOdgovor(gosti);
                    } catch (Exception ex) {
                        System.out.println("GOSTI SU NULLLLLL");
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.PRETRAZI_SOBE:
                    try {
                        List<String> columns = (List<String>) req.getParametar();
                        List<String> values = (List<String>) req.getParametar2();
                        List<Soba> sobe = kontroler.pretraziSobe(columns, values);
                        res.setOdgovor(sobe);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.VRATI_SVE_SOBE:
                    try {
                        List<Soba> sobe = kontroler.vratiSveSobe();
                        res.setOdgovor(sobe);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.VRATI_SVE_VRSTE_SOBA:
                    try {
                        List<VrstaSobe> vrste = kontroler.vratiSveVrsteSoba();
                        res.setOdgovor(vrste);
                        res.setPoruka(req.getParametar()+"");
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.KREIRAJ_ZAKUP:
                    try {
                        ZakupSobe zs = (ZakupSobe) req.getParametar();
                        zs=kontroler.kreirajZakup(zs);
                        res.setOdgovor(zs);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.PRETRAZI_ZAKUPE:
                    try {
                        List<String> columns = (List<String>) req.getParametar();
                        List<String> values = (List<String>) req.getParametar2();
                        List<ZakupSobe> zakupi = kontroler.pretraziZakupe(columns, values);
                        res.setOdgovor(zakupi);
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.VRATI_SVE_ZAKUPE:
                    try {
                        List<ZakupSobe> zakupi = kontroler.vratiSveZakupe();
                        System.out.println("NISU NULL");
                        res.setOdgovor(zakupi);
                        System.out.println("NISU");
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        System.out.println("ZAKUPI SU NULL");
                        res.setOdgovor(null);
                    }
                    break;
                case Operacije.VRATI_ID:
            
                try {
                    long id = kontroler.vratiID();
                    res.setOdgovor(id);
                } catch (SQLException ex) {
                    Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            
                case Operacije.VRATI_USLUGE_ZA_SOBU:
                    try {
                        List<String> columns = (List<String>) req.getParametar();
                        List<String> values = (List<String>) req.getParametar2();
                        List<UslugaNajma> usluge = kontroler.vratiUsluge(columns, values);
                        res.setOdgovor(usluge);
                        
                    } catch (Exception ex) {
                        Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
                        res.setOdgovor(null);
                        System.out.println("USLUGE NEKA GRESKAAA");
                    }
            
                    break;
                    
            }
            
            
            res.setOperacija(req.getOperacija());
            System.out.println("OPERACIJA JE: "+req.getOperacija());
            posaljiOdgovor(res);
            
    
            
            List<Istorija> lista = new ArrayList<>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try {
    			StringBuilder putanja = new StringBuilder();
    			putanja.append(onlineRecepcioner.getKorisnickoIme()).append(".json");
    			String path = putanja.toString();
    			System.out.println(path);
    			
    			FileReader in = new FileReader(onlineRecepcioner.getKorisnickoIme()+".json");
    			   			
    			Gson gsonIn = new Gson();
    			Type type = new TypeToken<ArrayList<Istorija>>(){}.getType();
    			
    			lista.clear();
    			
    			lista = gsonIn.fromJson(in, type);
    			
    			FileWriter out = new FileWriter(onlineRecepcioner.getKorisnickoIme()+".json");
    			Istorija istorija = new Istorija(res.getOperacija(),new Date(),res.getPoruka(),true);
    			
    			lista.add(istorija);
    			
    			gson.toJson(lista, out);
    			System.out.println("Upisao u json");
    			//br.close();
    			out.close();
    		} catch(FileNotFoundException fnf) {
    			FileWriter out = new FileWriter(onlineRecepcioner.getKorisnickoIme()+".json");
    			
    			Istorija istorija = new Istorija(res.getOperacija(),new Date(),res.getPoruka(),true);
    			
    			lista.add(istorija);
    			
    			gson.toJson(lista, out);
    			System.out.println("Upisao u json iz catch");
    			out.close();
    			
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
            
            
        
        }
    }
    
    private Zahtev primiZahtev() throws IOException, ClassNotFoundException{
        Zahtev r = new Zahtev();
        
            ObjectInputStream objectInputStream = new ObjectInputStream(soket.getInputStream());
            r=(Zahtev) objectInputStream.readObject();
        
        return r;
    }

    public void posaljiOdgovor(Odgovor res) throws IOException{
        
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(soket.getOutputStream());
            objectOutputStream.writeObject(res);
        
        
        
    }
}
