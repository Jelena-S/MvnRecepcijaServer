/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.niti;

import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.bazapodataka.konfiguracija.Konfiguracija;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ai.nprog.MvnRecepcijaServer.kontroler.Kontroler;

/**
 *
 * @author Jelena Sreckovic
 */
public class PokretanjeServera extends Thread{
    ServerSocket ss;
    boolean kraj = false;

    @Override
    public void run() {
        try {
            ss = new ServerSocket(Konfiguracija.getInstance().getPort());
            System.out.println("Server se pokrenuo");
            while(!kraj) { 
                
                System.out.println("Cekam na konekciju..");
                Socket soket = ss.accept();
                System.out.println("Klijent se povezao");
                
                ObradaKlijentskihZahteva okz = new ObradaKlijentskihZahteva(soket);
                okz.start();
                        
                System.out.println("dodavanje klijenta");
                Kontroler.getInstanca().addClient(okz);
                
                
            }
            
        } catch (IOException ex) {
            System.out.println("Greskaaaa " + ex.getMessage());
            System.out.println("OVO JE ZAPRAVO UGASEN SERVER");
            
        }
        
        System.out.println("Zaustavljen");
    }
    
    
    
    public void zaustaviServer() {
        System.out.println("usao u zaustaviServer");
        
        stopAllThreads();
        kraj = true;
        try {
            ss.close();
        } catch (IOException ex) {
            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("GRESKA IZ ZAUSTAVI SERVER");
        }
        //mozda treba mozda ne
        interrupt();
        
        
        
        
    }
    
    public void stopServerThread() throws IOException {
        stopAllThreads();

        ss.close();
    }
    public ServerSocket getServerSocket() {
        return ss;
    }

    private void stopAllThreads() {
        System.out.println("usao u stopAllThreads");
        Kontroler.getInstanca().logoutClients();
        for (ObradaKlijentskihZahteva client : Kontroler.getInstanca().getClientThreads()) {
            try {
                System.out.println("izbacuje");
                client.getSocket().close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
        System.out.println("zavrsio sa stopAllThreads");

    }
}
