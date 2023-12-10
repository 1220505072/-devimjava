/* 1220505072 HAMZA CAN ALTINTOP */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CustomButton extends JButton {
    private String graphQLSchema;
    private Color aktifRenk;
    private Color pasifRenk;
    private boolean aktifHal;

    private Icon belirlenenAktifIcon;
    private Icon belirlenenPasifIcon;

    public CustomButton(String text) {
        super(text);
        this.graphQLSchema = "";
        this.aktifRenk = Color.RED;
        this.pasifRenk = Color.BLUE;
        this.aktifHal = false;


        this.belirlenenAktifIcon = new ImageIcon(new ImageIcon(getClass().getResource("954552.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        this.belirlenenPasifIcon = new ImageIcon(new ImageIcon(getClass().getResource("103556.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

        pasifDurumuTakip();
        addActionListener(new ButonTiklama());


        setIcon(belirlenenPasifIcon);
    }

    public void setGraphQLSchema(String schema) {
        this.graphQLSchema = schema;
    }

    public void setAktifRenk(Color color) {
        this.aktifRenk = color;
    }

    public void setPasifRenk(Color color) {
        this.pasifRenk = color;
    }

    private void pasifDurumuTakip() {
        setBackground(pasifRenk);
        setIcon(belirlenenPasifIcon);
        setText("Pasif");
        aktifHal = false;
    }

    private void aktifDurumuTakip() {
        setBackground(aktifRenk);
        setIcon(belirlenenAktifIcon);
        setText("Aktif");
        aktifHal = true;
    }

    private class ButonTiklama implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (aktifHal) {
                mevcutHaricTumButonlarPasifDurumuAyarla();
            } else {
                aktifDurumuTakip();
                System.out.println("Mutation çalıştırıldı: " + graphQLSchema);
            }
        }
    }

    private void mevcutHaricTumButonlarPasifDurumuAyarla() {
        for (Component component : getParent().getComponents()) {
            if (component instanceof CustomButton && component != this) {
                ((CustomButton) component).pasifDurumuTakip();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Buton Paneli");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(4, 4, 10, 10));

            CustomButton[] butonlar = new CustomButton[16];
            for (int i = 0; i < 16; i++) {
                butonlar[i] = new CustomButton("Buton " + (i + 1));
                frame.add(butonlar[i]);
            }

            butonlar[0].setGraphQLSchema("https://api.example.com/graphql");
            butonlar[1].setGraphQLSchema("https://api.anotherexample.com/graphql");
            butonlar[2].setGraphQLSchema("https://api.yetanotherexample.com/graphql");

            frame.setSize(400, 400);
            frame.setVisible(true);
        });
    }
}