import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Date;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main extends JFrame {
    public Main() {
        super("SC4MP NOTE");
        initComponents();
    }

    public void initComponents() {
        final FileDialog fd = new FileDialog(this, "Zapisz", FileDialog.SAVE);
        this.setBounds(300, 300, 600, 500);
        this.setDefaultCloseOperation(3);
        this.setJMenuBar(pasekMenu);
        ImageIcon img = new ImageIcon("one_note.png");
        this.setIconImage(img.getImage());
        textArea.setFont(foncik);
        //textArea.setSize(getContentPane().getWidth(), getContentPane().getHeight()-100);
        wyborPlikow.setCurrentDirectory(new File(System.getProperty("user.dir")));

        JMenuItem nowy = new JMenuItem();
        JMenuItem zamknij = new JMenuItem("Zamknij");
        JMenuItem archiwizuj = new JMenuItem("Archiwizuj");
        final JMenuItem zapisz = new JMenuItem("Zapisz");
        final JMenuItem zapisz_jako = new JMenuItem("Zapisz jako");
        final JMenuItem otworz = new JMenuItem("Otworz");
        final JMenuItem dodaj_date = new JMenuItem("Godzina/data");
        final JMenuItem zaznacz_wszystko = new JMenuItem("Zaznacz wszystko");
        final JMenuItem powieksz_czcionke = new JMenuItem("Powieksz czcionke");
        final JMenuItem pomniejsz_czcionke = new JMenuItem("Pomniejsz czcionke");
        final JMenuItem znajdz = new JMenuItem("Znajdz");
        final JMenuItem zamien = new JMenuItem("Zamien");
        final JMenuItem min = new JMenuItem("Minimalizuj okno");
        final JMenuItem maks = new JMenuItem("Maksymalizuj okno");
        final JMenuItem ukryj = new JMenuItem("Schowaj okno");

        JMenu menu = pasekMenu.add(new JMenu("Plik"));
        JMenu menuOpcje = new JMenu("Opcje");
        JMenu menuWidok = new JMenu("Widok");
        zapisz.setEnabled(false);
        zapisz_jako.setEnabled(false);
        zapisz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String katalog = fd.getDirectory();
                String plik = fd.getFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(katalog + plik));
                    String string = textArea.getText();
                    writer.append(string);
                    JOptionPane.showMessageDialog(null, "ZAPISANE");
                    writer.close();
                    zapisz.setEnabled(flaga = false);
                    System.out.println("ZAPISANE");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
        zapisz_jako.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // int tmp = wyborPlikow.showSaveDialog(rootPane);

                fd.setVisible(true);
                String katalog = fd.getDirectory();
                String plik = fd.getFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(katalog + plik + ".txt"));
                    String string = textArea.getText();
                    writer.append(string);
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                System.out.println("Wybrano plik: " + plik);
                System.out.println("w katalogu: " + katalog);
                System.out.println("Ścieżka: " + katalog + plik);

            }
        });
        otworz.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tmp = wyborPlikow.showOpenDialog(rootPane);
                if (tmp == 0)
                    try {
                        textArea.setText("");
                        String sciezkaPliku = wyborPlikow.getSelectedFile().toString();
                        if (!sciezkaPliku.contains(".txt")) {
                            JOptionPane.showMessageDialog(null, "TO NIE PLIK TEKSTOWY!", "INFORMACJA", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            Scanner reader = new Scanner(new File(sciezkaPliku));
                            while (reader.hasNext()) {
                                String linia = reader.nextLine();
                                textArea.append(linia);
                                textArea.append("\n");
                            }
                            JOptionPane.showMessageDialog(null, "WCZYTANO Z PLIKU", "INFORMACJA", JOptionPane.INFORMATION_MESSAGE);
                            reader.close();
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }

            }
        });
        archiwizuj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final int BUFOR = 1024;
                byte[] tmpData = new byte[BUFOR];
                try {
                    ZipOutputStream zOutS = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream("plik.zip"), BUFOR));
                    BufferedInputStream inS = new BufferedInputStream(new FileInputStream("baza.txt"), BUFOR);
                    zOutS.putNextEntry(new ZipEntry("baza.txt"));
                    int counter;
                    while ((counter = inS.read(tmpData, 0, BUFOR)) != -1)
                        zOutS.write(tmpData, 0, counter);

                    zOutS.closeEntry();
                    inS.close();
                    zOutS.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        powieksz_czcionke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size_letters = size_letters + 4;
                Font font = new Font("XD", 0, size_letters);
                if (!textArea.getText().isEmpty())
                    textArea.setFont(font);
            }
        });
        pomniejsz_czcionke.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size_letters = size_letters - 4;
                Font font = new Font("XD", 0, size_letters);
                if (!textArea.getText().isEmpty())
                    textArea.setFont(font);

            }
        });
        min.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.NORMAL);
            }
        });
        maks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        ukryj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        dodaj_date.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tylkoDoOdczytu.isSelected()) {
                    Date data = new Date();
                    textArea.append(data.toString());
                }
            }
        });
        zaznacz_wszystko.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.selectAll();
            }
        });
        nowy = menu.add("Nowy");
        menu.addSeparator();
        menu.add(otworz);
        menu.addSeparator();
        menu.add(zapisz);
        menu.addSeparator();
        menu.add(zapisz_jako);
        menu.addSeparator();
        menu.add(archiwizuj);
        menu.addSeparator();
        menu.add(menuOpcje);
        menuOpcje.add(zamknij);
        zamknij.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        zapisz.setToolTipText("Zapisanie pliku na dysku");
        zapisz.setMnemonic('S');
        zapisz.setAccelerator(KeyStroke.getKeyStroke("CTRL+S"));
        zamknij.setToolTipText("Zamkniecie dzialania programu");
        menuOpcje.add(tylkoDoOdczytu);
        tylkoDoOdczytu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tylkoDoOdczytu.isSelected()) {
                    zapisz.setEnabled(false);
                }
                if (!tylkoDoOdczytu.isSelected()) {
                    zapisz.setEnabled(flaga);
                }
            }
        });
        nowy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main nowaRamka = new Main();
                nowaRamka.setVisible(true);
                nowaRamka.setDefaultCloseOperation(2);
            }
        });
        znajdz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Znajdz nowaRamkaZnajdz = new Znajdz();
                nowaRamkaZnajdz.setVisible(true);
                nowaRamkaZnajdz.setDefaultCloseOperation(2);

            }
        });
        zamien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Zamien nowaRamkaZamien = new Zamien();
                nowaRamkaZamien.setVisible(true);
                nowaRamkaZamien.setDefaultCloseOperation(2);
            }
        });
        JMenu edytuj = new JMenu("Edycja");
        pasekMenu.add(edytuj);
        edytuj.add(powieksz_czcionke);
        edytuj.addSeparator();
        edytuj.add(pomniejsz_czcionke);
        edytuj.addSeparator();
        edytuj.add(znajdz);
        edytuj.addSeparator();
        edytuj.add(zamien);
        edytuj.addSeparator();
        edytuj.add(dodaj_date);
        edytuj.addSeparator();
        edytuj.add(zaznacz_wszystko);
        pasekMenu.add(menuWidok);
        menuWidok.add(min);
        menuWidok.addSeparator();
        menuWidok.add(maks);
        menuWidok.addSeparator();
        menuWidok.add(ukryj);
        menuWidok.addSeparator();
        menuWidok.add(zawijanieTextu);
        this.getContentPane().setLayout(new GridLayout(1, 1));
        this.getContentPane().add(obszarPrzesuwania, BorderLayout.NORTH);

        zawijanieTextu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (zawijanieTextu.isSelected()) {
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                } else if (!zawijanieTextu.isSelected()) {
                    textArea.setLineWrap(false);
                    textArea.setWrapStyleWord(false);
                }
            }
        });
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (tylkoDoOdczytu.isSelected()) {
                    e.consume();
                }
            }

            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    delete++;
                    //System.out.println("DELETE : "+delete);
                    if (counter == delete || delete > counter || textArea.getText().isEmpty()) {
                        zapisz.setEnabled(flaga = false);
                        zapisz_jako.setEnabled(flaga = false);
                        counter = 0;
                        delete = 0;
                    }
                }
                if (e.isControlDown() && (e.getKeyCode() == KeyEvent.VK_V || e.getKeyCode() == KeyEvent.VK_X) && tylkoDoOdczytu.isSelected()) // nie mozna kopiowac
                {
                    e.consume();
                } else if (!(textArea.getText() + e.getKeyChar()).equals(przedZmiana) && czyAscii(e.getKeyChar())) {
                    counter++;
                    //System.out.println(counter);
                    przedZmiana = textArea.getText();
                    zapisz.setEnabled(flaga = true);
                    zapisz_jako.setEnabled(flaga = true);
                }
            }
        });
    }

    static private boolean czyAscii(char z) {
        return z > 32 && z < 127;
    }

    private final JMenuBar pasekMenu = new JMenuBar();
    private final JCheckBoxMenuItem tylkoDoOdczytu = new JCheckBoxMenuItem("Tylko do odczytu");
    private final JCheckBoxMenuItem zawijanieTextu = new JCheckBoxMenuItem("Zawijanie textu");
    private final JTextArea textArea = new JTextArea();
    private final JScrollPane obszarPrzesuwania = new JScrollPane(textArea);
    private boolean flaga = false;
    private String przedZmiana = "";
    private int size_letters = 22;
    private final Font foncik = new Font("XD", 0, size_letters);
    private final JPanel panelDolny = new JPanel();
    int counter = 0;
    JFileChooser wyborPlikow = new JFileChooser();
    static int delete = 0;


    public class Zamien extends JDialog {
        public Zamien() {
            this.setTitle("Zamienianie");
            this.setBounds(300, 300, 520, 150);
            this.setAlwaysOnTop(true);
            this.setResizable(false);
            initComponent();
        }

        public void initComponent() {
            ImageIcon img = new ImageIcon("one_note.png");
            this.getContentPane().setLayout(new GridLayout(3, 3));
            this.setIconImage(img.getImage());
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            Font font = new Font("XD", 3, 16);
            this.getContentPane().add(panel1);
            this.getContentPane().add(panel2);
            JLabel znajdz_label = new JLabel("Znajdz:");
            JLabel zamien_label = new JLabel("Zamien:");
            znajdz_label.setFont(font);
            zamien_label.setFont(font);
            final JTextArea znajdz_textArea = new JTextArea(1, 30);
            final JTextArea zamien_textArea = new JTextArea(1, 30);
            final JButton znajdz_button = new JButton("Znajdz");
            final JButton zamien_button = new JButton("Zamien");
            final JCheckBox uwzgledniaj = new JCheckBox("Uwzglednij wielkosc liter");

            zamien_button.setPreferredSize(new Dimension(80, 22));
            znajdz_button.setPreferredSize(new Dimension(80, 22));
            panel1.add(znajdz_label);
            panel1.add(znajdz_textArea);
            panel1.add(znajdz_button);
            panel2.add(zamien_label);
            panel2.add(zamien_textArea);
            panel2.add(zamien_button);

            this.getContentPane().add(panel1, BorderLayout.PAGE_START);
            this.getContentPane().add(panel2, BorderLayout.PAGE_END);
            this.getContentPane().add(uwzgledniaj);

            znajdz_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!uwzgledniaj.isSelected()) {
                        poczatekSzukanego = textArea.getText().toUpperCase().indexOf(znajdz_textArea.getText().toUpperCase(), poczatekSzukanego + znajdz_textArea.getText().length());
                    } else {
                        poczatekSzukanego = textArea.getText().indexOf(znajdz_textArea.getText(), poczatekSzukanego + znajdz_textArea.getText().length());
                    }
                    if (poczatekSzukanego == -1) {
                        poczatekSzukanego = textArea.getText().indexOf(znajdz_textArea.getText());
                        JOptionPane.showMessageDialog(znajdz_button, "Koniec lub brak poszukiwanej frazy w tekscie");
                    }

                    if (poczatekSzukanego >= 0 && znajdz_textArea.getText().length() > 0) {
                        textArea.requestFocus();
                        textArea.select(poczatekSzukanego, poczatekSzukanego + znajdz_textArea.getText().length());
                    }
                }
            });

            zamien_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textArea.getText().contains(znajdz_textArea.getText()))
                        textArea.setText(textArea.getText().replaceFirst(textArea.getSelectedText(), zamien_textArea.getText()));
                    else
                        JOptionPane.showMessageDialog(znajdz_button, "Brak poszukiwanej frazy w tekscie");
                }
            });

        }

        private int poczatekSzukanego = 0;
    }

    public class Znajdz extends Zamien {
        public Znajdz() {
            this.setTitle("Znajdowanie");
            this.setBounds(300, 300, 550, 100);
            this.setResizable(false);
            initComponent();
        }

        public void initComponent() {
            ImageIcon img = new ImageIcon("one_note.png");
            this.setIconImage(img.getImage());
            JPanel panel = new JPanel();
            this.getContentPane().add(panel);
            JLabel znajdz_label = new JLabel("Znajdz:");
            znajdz_label.setFont(new Font("XD", 3, 16));
            final JTextArea znajdz_textArea = new JTextArea(1, 30);
            final JButton znajdz_button = new JButton("Znajdz");
            panel.add(znajdz_label);
            panel.add(znajdz_textArea);
            panel.add(znajdz_button);
            this.getContentPane().add(panel, BorderLayout.PAGE_START);

            znajdz_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    poczatekSzukanego = textArea.getText().indexOf(znajdz_textArea.getText(), poczatekSzukanego + znajdz_textArea.getText().length());
                    if (poczatekSzukanego == -1) {
                        poczatekSzukanego = textArea.getText().indexOf(znajdz_textArea.getText());
                        JOptionPane.showMessageDialog(znajdz_button, "Koniec lub brak poszukiwanej frazy w tekscie");
                    }

                    if (poczatekSzukanego >= 0 && znajdz_textArea.getText().length() > 0) {
                        textArea.requestFocus();
                        textArea.select(poczatekSzukanego, poczatekSzukanego + znajdz_textArea.getText().length());
                    }
                }
            });

        }

        private int poczatekSzukanego = 0;
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}
