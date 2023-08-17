package sk.vinconafta.cryptoboxIPTV.gui;

import sk.vinconafta.cryptoboxIPTV.IPTVstream;
import sk.vinconafta.cryptoboxIPTV.Streams;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlaylistEditor {
    private JButton odstranKanalButton;
    private JButton zmenNazovButton;
    private JButton exportToCFGButton;
    private JPanel mainPanel;
    private JList list1;
    private JButton pridajKanalButton;
    private JButton addMore;
    private final Streams streams;

    public PlaylistEditor(Streams streams) {
        JFrame frame = new JFrame("Playlist Editor");
        frame.setContentPane(mainPanel);
        this.streams = streams;
        reloadChannels();

        frame.setVisible(true);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        odstranKanalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!list1.isSelectionEmpty()) {
                    streams.removeOnIndex(list1.getSelectedIndex());
                    reloadChannels();
                } else {
                    JOptionPane.showMessageDialog(null, "Musíte kliknuť na kanal ktorý chcete zmazať", "Neplatny vyber", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        zmenNazovButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!list1.isSelectionEmpty()) {
                    String novyNazov = JOptionPane.showInputDialog(null, "Zadajte nový názov kanála", "Zmena názvu kanala", JOptionPane.QUESTION_MESSAGE);
                    if (novyNazov != null) {
                        novyNazov = novyNazov.replaceAll(" ", "_");
                        streams.at(list1.getSelectedIndex()).setChannelName(novyNazov);
                        reloadChannels();
                    }
                }

            }
        });
        exportToCFGButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                streams.tryToExportToCFG();
            }
        });
        pridajKanalButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String newChannelName = JOptionPane.showInputDialog(null, "Zadajte názov nového Kanálu");
                if (newChannelName != null) {
                    newChannelName = newChannelName.replaceAll(" ", "_");
                    String stream = JOptionPane.showInputDialog(null, "Zadajte stream nového Kanálu");
                    if (stream != null) {
                        streams.add(new IPTVstream(newChannelName, stream));
                        reloadChannels();
                    }
                }
            }
        });
        addMore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                }
                reloadChannels();
            }
        });
    }

    private void reloadChannels() {
        DefaultListModel<String> dml = new DefaultListModel<>();
        for (int i = 0; i < this.streams.getSize(); i++) {
            dml.addElement(this.streams.at(i).getChannelName());
        }
        list1.setModel(dml);
    }
}
