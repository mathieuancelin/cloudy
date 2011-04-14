package com.sample.cloudy;

import com.sample.cloudy.api.app.CloudApp;
import com.sample.cloudy.api.app.IpRange;
import com.sample.cloudy.impl.CloudAppFactoryImpl;
import java.io.Console;
import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        try {
            CloudApp app = new CloudAppFactoryImpl().createCloudApp(
                    "app",
                    null,
                    new File("/Users/mathieuancelin/VirtualBoxVMs").toURI().toURL(),
                    new File("/Users/mathieuancelin/VirtualBoxVMs/JEOS/JEOS.vdi").toURI().toURL(),
                    new IpRange("192.168.86.200", "192.168.86.250"));
            app.start();


            System.out.println("");

            
            app.addNode();
            app.startNode();
        
            Console console = System.console();
            boolean read = true;
            while(read) {
                String line = console.readLine();
                if (line.equals("exit")) {
                    read = false;
                } else if (line.equals("stop")) {
                    app.stopNode();
                }
            }
            app.stop();
        } catch (MalformedURLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
