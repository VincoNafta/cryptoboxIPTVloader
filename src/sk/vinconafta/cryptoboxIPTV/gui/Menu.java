package sk.vinconafta.cryptoboxIPTV.gui;

import sk.vinconafta.cryptoboxIPTV.Streams;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {
    private JButton loadFileButton;
    private final Streams streams;
    private JButton finitoButton;
    private JButton aboutButton;
    private JPanel main;

    public Menu() {
        JFrame frame = new JFrame("CryptoIPTV");
        frame.setContentPane(this.main);
        frame.setVisible(true);
        frame.pack();
        this.streams = new Streams();
        finitoButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        loadFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("m3u & m3u8 playlist", "m3u8", "m3u");
                chooser.setFileFilter(filter);
                chooser.setDialogTitle("Výber súboru");
                chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                chooser.setVisible(true);
                chooser.showOpenDialog(null);

//                System.out.println(chooser.getSelectedFile());
                if (chooser.getSelectedFile() != null) {
                    streams.readFromFile(chooser.getSelectedFile());
                    new PlaylistEditor(streams);
                    frame.dispose();
                }

            }
        });
        aboutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Autor: VincoNafta\nVerzia 1.0.0");
            }
        });

        JOptionPane.showMessageDialog(null,"Progam používate na vlastnú zodpovednosť.\n Autor programu neručí za škody", "UPOZORNENIE!!!" , JOptionPane.WARNING_MESSAGE);
    }
}
