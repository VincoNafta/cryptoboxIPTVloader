package sk.vinconafta.cryptoboxIPTV;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Streams {
    private final List<IPTVstream> iptVstreams;
    private static final int maxChannels = 2000;

    public Streams() {
        this.iptVstreams = new ArrayList<>();
    }

    public void readFromFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String s1 = sc.nextLine();
                if (s1.equals("#EXTM3U")) {
                    s1 = sc.nextLine();
                }

                try {
                    String s2 = sc.nextLine();
                    check(s1, s2);
                } catch (NoSuchElementException e) {
                    break;
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {


        }



    }

    public void add(IPTVstream vstream) {
        this.iptVstreams.add(vstream);
    }
    private void check(String line1, String line2) {
        if (line1.startsWith("#") && line2.startsWith("http")) {
            if (line1.contains(",")) {
                String channelName = line1.substring(line1.indexOf(",") + 1, line1.length());
                channelName = channelName.replaceAll(" ", "_");
                iptVstreams.add(new IPTVstream(channelName, line2));
            }
        }
    }

    public void tryToExportToCFG() {
        if (this.iptVstreams.size() <= maxChannels) {
            if (this.iptVstreams.size() > 16) {
                JOptionPane.showMessageDialog(null, "Súbor má viac ako 16 streamov. \nNačítanie dát v cryptoboxe môže zlyhať", "UPOZORNENIE !", JOptionPane.WARNING_MESSAGE);
            }
            File exportFile = new File("iptv.cfg");
            if (exportFile.exists()) {
                int response = JOptionPane.showConfirmDialog(null, "Súbor s exportom už exituje.\nChcete prepísať existujuci cfg súbor?", "Prepis CFG", JOptionPane.YES_NO_OPTION);
//            System.out.println(response);
                if (response == 0) {
                    export(exportFile);
                }
            } else {
                export(exportFile);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cryptobox spracuje maximálne " + maxChannels +  "kanálov. \nOdstránte niektoré programy aby ste dosiahli daný počet programov", "Export sa nepodaril", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void export(File f) {
        try {
            PrintWriter pw = new PrintWriter(f);
            for (IPTVstream iptVstream : iptVstreams) {
                pw.println(iptVstream.getToCFG());
            }
            pw.close();
            JOptionPane.showMessageDialog(null, "Tranformovanie sa podarilo", "Uspech !", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Exportovanie zlyhalo.\nChyba pri ukladani do súboru iptv.cfg!", "Chyba!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeOnIndex(int index){
        this.iptVstreams.remove(index);
    }


    public IPTVstream at(int index) {
        return this.iptVstreams.get(index);
    }

    public int getSize() {
        return this.iptVstreams.size();
    }
}
