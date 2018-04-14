
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.UIManager.*;

/*
 * @Autoren: Jannik Buscha & Emre Aydin
 * @Informationen: Erstellt in NetBeans IDE 8.2, ~330 Zeilen Code, ~200 Zeilen generiert von NetBeans &&|| NetBeans Swing Builder
 * @Inhaltsverzeichniss: 1. Wort Array, 2. Die "Main" Methode, 3. Spiel starten, 4. Spiel neustart, 5. Das "Herz­stück", 6. Generierter Code
 * @Weitere Vorhaben die wir nicht geschafft haben: Sprachauswahl im 2D-Array, Verschiedene Schwierigkeiten, Multiplayer (mit Abwechslungsprinzip)
 */

public class Galgenmaennchen extends JFrame { //Sorgt dafür das die Klasse die damit deklaiert wird, die Methoden der externen-Klasse enthält.

    /*
     * 1. Wort Array
     * Hier wird die Array mit den jeweiligen Wörtern erstellt.
     */
    private String[] woerter = {
        "griff", "archaeologisch", "tropen", "menschheit", "mathematik", "schnauze", "galleone", "mitternacht", "ruecksichtslos", "leichtsinnig", "hyperventilieren", "chemikalien", "substantiv", "schere", "tage", "abschluss", "pferd", "dermatologe", "dreieck", "wochentag", "klassenfahrt", "vorfahrtstrasse", "berichterstattung", "leichenwagen", "flughoernchen", "seilbahn", "mitternachtsparty", "schlumpfhausen", "ergonomisch", "lamborghini", "vielseitig", "krankheitsgefahr", "verkehrsregeln", "mainboard", "grafikeinheit", "gehaeuseluefter", "schraubenzieher", "festplattenverschleiss", "grundausstattung", "herzinfarkt", "katastrophe", "wahrscheinlichkeitsrechnung", "krasavice", "hauptversorgung", "anomalie", "kanackenbaum", "russenhocke", "flohmarkt", "monockel", "nahrungsmittelunvertraelichkeit"
    };

    private static String statischesWort; //Statische variable die das wort enthällt.
    private static int versuche = 5; //Maximale versuche.
    private char buchstabe; //Eingegebener Buchstabe.
    
    //Buchstaben speicherung.
    String verwendeteBuchstaben;
    String grafik = "" + versuche;

    /*
     * 2. Die "Main" Methode
     * Hier wird eine Methode erstellt in der der Teil vom Generiert Code und des Spiels enthalten ist.
     * außerdem sind hier noch die Events und alles zusammen wird später in der richtigen Main Methode aufgerufen.
     */
    public Galgenmaennchen() {
        initComponents();
        starteSpiel();

        this.setLocationRelativeTo(null); //Setzt das Frame in die Mitte.

        eingabeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buchstabe();
            }
        });

        neustartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spielNeustart();
            }
        });
    }

    /*
     * 3. Spiel starten
     * Hier wird das Spiel gestartet. Hier wird ein Zufälliges Wort aus dem Array ausgewählt und die Komponente werden richtig angeordnet.
     */
    private void starteSpiel() {
        this.versuche = 5;
        
        statischesWort = verwendeteBuchstaben = "";

        this.maennchen.setText("");
        this.eingegebeWoerterLabel.setText("");
        this.eingabeButton.setVisible(true);
        this.neustartButton.setVisible(false);
        this.meldungLabel.setVisible(false);
        this.versucheLabel.setText("Versuche: " + versuche);
        //Entfernt verstecktes Wort.
        this.verstecktesWortLabel.setText("");

        //Wählt ein zufälliges Wort aus der Array.
        int zufaellig = ThreadLocalRandom.current().nextInt(0, woerter.length);
        statischesWort = woerter[zufaellig];

        System.out.println(statischesWort);
        
        for (int i = 0; i < this.statischesWort.length(); i++) {
            this.verstecktesWortLabel.setText(this.verstecktesWortLabel.getText() + "*");
        }
    }

    /*
     * 4. Spiel neustart
     * Hier wird ganz einfach das Spiel neugestartet.
     */
    private void spielNeustart() {
        this.maennchen.setIcon(null);
        
        starteSpiel();
    }
    
    /*
     * 5. Das "Herz­stück"
     * Das Das "Herz­stück" genannt, da sich alles wichtige in dieser Methode befindet. Größtenteils Fehlerabsicherungen, Komponenten anordnungen
     * aber auch die Zeichnung des Galgenmännchens.
     */
    private void buchstabe() {
        //Check ob der Textfeld ein char beinhaltet & ob dieser ein Buchstabe ist.
        if (!this.textFeld.getText().isEmpty() && (this.textFeld.getText().matches("[a-zA-Z]"))) {
            //Konvertiert den Buchstaben zum char.
            this.buchstabe = this.textFeld.getText().charAt(0);
            
            int buchstabenOrt = statischesWort.indexOf(buchstabe);
            
            if (buchstabenOrt == -1) { //eingegebener Buchstabe ist nicht im gesuchten Wort
                if (this.verwendeteBuchstaben.indexOf(this.buchstabe) == -1) { //Buchstabe ist noch nie zuvor eingegeben worden.
                    this.verwendeteBuchstaben += this.buchstabe + ", "; //Buchstabe wird in die "Liste" an bereits eingegebenen Buchstaben hinzugefügt .
                }
                this.meldungLabel.setText("Buchstabe '" + this.buchstabe + "' ist falsch!");
                this.meldungLabel.setVisible(true);
                this.eingegebeWoerterLabel.setText("Bereits benutzte Buchstaben: " + verwendeteBuchstaben.substring(0, verwendeteBuchstaben.length() - 2)); // ', ' wird vom Stringende entfernt (optische Gründe) und dann angezeigt.
                this.versuche--; //Reduziert die Leben.
                this.versucheLabel.setText("Versuche: " + versuche); //Zeigt die Leben an.
                grafik = "grafik" + versuche;  //Ändert den Path zum neuen Galgenmänchen Bild.
                ImageIcon maennchenBild = new ImageIcon(getClass().getResource("grafiken/" + grafik + ".png")); //Lädt das Bild des Galgenmänchens aus den Resourcen.
                this.maennchen.setIcon(maennchenBild); //Übergibt das Bild zum zeichnen.
            } else { //Eingegebener Buchstabe ist im gesuchten Wort.
                String entdecktesWort = this.verstecktesWortLabel.getText();
                String benutzteCharakter = statischesWort; //Temp variable in der wir das gesuchte wort speichern.
                for (int i = 0; i < benutzteCharakter.length(); i++) {
                    char c = benutzteCharakter.charAt(i);
                    if (c == buchstabe) {
                        buchstabenOrt = benutzteCharakter.indexOf(c); //Den buchstabenOrt aktualisieren.
                        entdecktesWort = entdecktesWort.substring(0, buchstabenOrt) //Den Buchstaben im versteckten Wort enthüllen.
                        + this.buchstabe
                        + entdecktesWort.substring(buchstabenOrt + 1);
                        benutzteCharakter = benutzteCharakter.replaceFirst(String.valueOf(c), "#"); //In unserer temp variable ein Puffer Buchstaben einfügen um auch folgende Buchstaben anzusteuern.
                    } 
                }
                this.verstecktesWortLabel.setText(entdecktesWort);
                this.meldungLabel.setText("");
                this.meldungLabel.setVisible(false);
            }
            //Setzt den Text wieder zurück.
            this.textFeld.setText("");
            this.buchstabe = '0';

            if (versuche == 0) {
                this.meldungLabel.setText("Du hast verloren! Richtig gewesen wäre: " + statischesWort);
                //Versteckt den Button.
                this.meldungLabel.setVisible(true);
                this.eingabeButton.setVisible(false);
                //Eingabe Button wird wieder angezeigt.
                this.neustartButton.setVisible(true);
                meldungLabel.setForeground(new Color(255, 0, 0));
                return;
            }
            
            //Check ob das Wort richtig ist.
            if (this.verstecktesWortLabel.getText().equals(statischesWort)) {
                this.meldungLabel.setText("Du hast gewonnen!");
                this.meldungLabel.setVisible(true);
                this.eingabeButton.setVisible(false);
                //Eingabe Button wird wieder angezeigt.
                this.neustartButton.setVisible(true);
                meldungLabel.setForeground(new Color(0, 255, 0));
                return;
            }

            meldungLabel.setForeground(new Color(255, 0, 0));
        }
    }

    /*
     * 6. Generierter Code
     * Hier befindet sich der Generierter Code von NetBeans &&|| NetBeans Swing Builder. Der Code lässt sich zudem nur über den Builder abändern.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        galgenmaennchenPane = new javax.swing.JPanel();
        ueberschrift = new javax.swing.JLabel();
        verstecktesWortLabel = new javax.swing.JLabel();
        textFeld = new javax.swing.JTextField();
        eingabeButton = new javax.swing.JButton();
        meldungLabel = new javax.swing.JLabel();
        versucheLabel = new javax.swing.JLabel();
        neustartButton = new javax.swing.JButton();
        eingegebeWoerterLabel = new javax.swing.JLabel();
        labelHintergrund = new javax.swing.JPanel();
        maennchen = new javax.swing.JLabel();
        hintergrund = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Galgenmännchen");
        setName("frame"); // NOI18N
        setResizable(false);

        galgenmaennchenPane.setLayout(null);

        ueberschrift.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        ueberschrift.setForeground(new java.awt.Color(255, 255, 255));
        ueberschrift.setText("Erraten Sie das Wort!");
        galgenmaennchenPane.add(ueberschrift);
        ueberschrift.setBounds(14, 40, 180, 24);

        verstecktesWortLabel.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        verstecktesWortLabel.setForeground(new java.awt.Color(255, 255, 255));
        verstecktesWortLabel.setText("****");
        verstecktesWortLabel.setAlignmentX(0.5F);
        galgenmaennchenPane.add(verstecktesWortLabel);
        verstecktesWortLabel.setBounds(206, 70, 270, 46);

        textFeld.setBackground(new java.awt.Color(0, 24, 28));
        textFeld.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        textFeld.setForeground(new java.awt.Color(255, 255, 255));
        textFeld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFeldActionPerformed(evt);
            }
        });
        galgenmaennchenPane.add(textFeld);
        textFeld.setBounds(25, 72, 40, 40);

        eingabeButton.setBackground(new java.awt.Color(0, 24, 28));
        eingabeButton.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        eingabeButton.setForeground(new java.awt.Color(255, 255, 255));
        eingabeButton.setText("Eingabe");
        eingabeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eingabeButtonActionPerformed(evt);
            }
        });
        galgenmaennchenPane.add(eingabeButton);
        eingabeButton.setBounds(70, 72, 120, 40);

        meldungLabel.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        galgenmaennchenPane.add(meldungLabel);
        meldungLabel.setBounds(14, 140, 620, 20);

        versucheLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        versucheLabel.setForeground(new java.awt.Color(255, 255, 255));
        versucheLabel.setText("Versuche: X");
        versucheLabel.setAlignmentX(0.5F);
        galgenmaennchenPane.add(versucheLabel);
        versucheLabel.setBounds(340, 40, 150, 24);

        neustartButton.setBackground(new java.awt.Color(0, 24, 28));
        neustartButton.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        neustartButton.setForeground(new java.awt.Color(255, 255, 255));
        neustartButton.setText("Neustart");
        neustartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neustartButtonActionPerformed(evt);
            }
        });
        galgenmaennchenPane.add(neustartButton);
        neustartButton.setBounds(480, 72, 120, 40);

        eingegebeWoerterLabel.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        eingegebeWoerterLabel.setForeground(new java.awt.Color(255, 255, 255));
        eingegebeWoerterLabel.setText("Meldung1");
        galgenmaennchenPane.add(eingegebeWoerterLabel);
        eingegebeWoerterLabel.setBounds(14, 120, 620, 20);

        labelHintergrund.setBackground(new java.awt.Color(0, 24, 28));
        labelHintergrund.setForeground(new java.awt.Color(0, 24, 28));

        javax.swing.GroupLayout labelHintergrundLayout = new javax.swing.GroupLayout(labelHintergrund);
        labelHintergrund.setLayout(labelHintergrundLayout);
        labelHintergrundLayout.setHorizontalGroup(
            labelHintergrundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 650, Short.MAX_VALUE)
        );
        labelHintergrundLayout.setVerticalGroup(
            labelHintergrundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        galgenmaennchenPane.add(labelHintergrund);
        labelHintergrund.setBounds(-6, 121, 650, 40);
        galgenmaennchenPane.add(maennchen);
        maennchen.setBounds(0, 0, 640, 664);

        hintergrund.setIcon(new javax.swing.ImageIcon(getClass().getResource("/grafiken/hintergrund.png"))); // NOI18N
        galgenmaennchenPane.add(hintergrund);
        hintergrund.setBounds(0, 0, 640, 664);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(galgenmaennchenPane, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(galgenmaennchenPane, javax.swing.GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void neustartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neustartButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_neustartButtonActionPerformed

    private void eingabeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eingabeButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eingabeButtonActionPerformed

    private void textFeldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFeldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFeldActionPerformed

    public static void main(String args[]) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break; //Setzt ein Theme fest.
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Galgenmaennchen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Galgenmaennchen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Galgenmaennchen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Galgenmaennchen.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Galgenmaennchen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton eingabeButton;
    private javax.swing.JLabel eingegebeWoerterLabel;
    private javax.swing.JPanel galgenmaennchenPane;
    private javax.swing.JLabel hintergrund;
    private javax.swing.JPanel labelHintergrund;
    private javax.swing.JLabel maennchen;
    private javax.swing.JLabel meldungLabel;
    private javax.swing.JButton neustartButton;
    private javax.swing.JTextField textFeld;
    private javax.swing.JLabel ueberschrift;
    private javax.swing.JLabel verstecktesWortLabel;
    private javax.swing.JLabel versucheLabel;
    // End of variables declaration//GEN-END:variables
}
