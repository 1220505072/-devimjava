# ödevimjava
AÇIKLAMA:
Bu Proje, 4x4 düğme paneline sahip, Java Swing kullanarak basit bir GUI oluşturmanın ve özel bir JButton sınıfı oluşturmanın bir örneğidir (GUI) için özel olarak tasarlanmış bir CustomButton sınıfı içerir. Her buton özelleştirilebilir(rengi, simgesi ve GraphQL şeması). Her buton, bir GraphQL şemasını temsil eder ve tıklanabilir durumda veya pasif durumda olabilir.
KODUN KULLANIMI VE TESTİ:
1.Projeyi çalıştırın böylece ekranınıza 4x4 16 tane buton gelecek.
2.Ekrana gelen butonlar pasif renk (mavi) ve pasif simge ile ekrana gelecek.
3.Herhangibir butona tıkladığınızda buton aktif renk (kırmızı) rengini alacak ve pasif simge yerine de aktif simge gelecek.
4.Birinci butona aktifken tıklandığında diğer aktif olan butonlar soruda istenildiği gibi pasif hale dönecek yani pasif renk ve pasif simge olarak güncellenecek
NOT: Renk ve simgeler başta bahsettiğim gibi dinamik hazırlanmış kullanıcı isteğine göre özelleştirebilir.
graphQL İLE İLGİLİ BİLGİ:
Genel olarak 3 başlıktainceleyelim:
1.Veri Sorgulama ve Değiştirme: Veritabanında güncelleme, ekleme veya silme gibi değişiklikler yapılabilir
2.Esnek ve Ölçeklenebilir Veri Alışverişi: GraphQL, istemcilere veri alışverişi konusunda büyük bir esneklik sağlar
3. Bağımsız Modüler Yapı: GraphQL, servislerin ve veri kaynaklarının bağımsız modüler yapıda olmasına izin verir. 
BİZİM UYGULAMAMIZDA KULLANIMI:
CustomButton sınıfı, her bir butonun depoladığı graphQLSchema özelliği ile bir GraphQL şemasını temsil eder. Her buton, farklı bir GraphQL API'nin URL'sini içerir. Butona tıklanınca, ilgili GraphQL şeması ekrana yazdırılır. Bu, buton aracılığıyla belirli bir GraphQL API'sine bir "mutation" (değişiklik) sorgusunu temsil eder.
Özetle, kullanıcılara farklı GraphQL şemalarını temsil eden butonlar aracılığıyla etkileşimde bulunma imkanı sağlar. Her bir buton, belirli bir GraphQL API'sini temsil eder ve kullanıcılara bu API'ler üzerinde sorgular yapma ve etkileşime geçme fırsatı sunar.
KODUN DETAYLI AÇIKLAMASI:
// CustomButton sınıfı, JButton sınıfından genişletilmiştir.
class CustomButton extends JButton {
    // Butonun depoladığı GraphQL şeması.
    private String graphQLSchema;
    // Aktif durumda kullanılacak renk.
    private Color aktifRenk;
    // Pasif durumda kullanılacak renk.
    private Color pasifRenk;
    // Butonun şu anki durumu (aktif/pasif).
    private boolean aktifHal;
    // Aktif durumda gösterilecek ikon.
    private Icon belirlenenAktifIcon;
    // Pasif durumda gösterilecek ikon.
    private Icon belirlenenPasifIcon;

    // CustomButton sınıfının kurucu metodu.
    public CustomButton(String text) {
        // Üst sınıfın kurucu metodunu çağırarak butonun metin içeriğini belirle.
        super(text);
        // Başlangıçta şema boştur.
        this.graphQLSchema = "";
        // Başlangıçta aktif renk kırmızı, pasif renk mavi olarak ayarlanmıştır.
        this.aktifRenk = Color.RED;
        this.pasifRenk = Color.BLUE;
        // Başlangıçta buton pasif durumdadır.
        this.aktifHal = false;

        // İkonları oluştur ve boyutlarını ayarla.
        this.belirlenenAktifIcon = new ImageIcon(new ImageIcon(getClass().getResource("954552.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        this.belirlenenPasifIcon = new ImageIcon(new ImageIcon(getClass().getResource("103556.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

        // Başlangıçta pasif durumu takip et.
        pasifDurumuTakip();
        // Butona tıklama dinleyicisi ekle.
        addActionListener(new ButonTiklama());

        // Başlangıçta pasif ikonu kullan.
        setIcon(belirlenenPasifIcon);
    }

    // GraphQL şemasını ayarlamak için kullanılan metod.
    public void setGraphQLSchema(String schema) {
        this.graphQLSchema = schema;
    }

    // Aktif durumda kullanılacak rengi ayarlamak için kullanılan metod.
    public void setAktifRenk(Color color) {
        this.aktifRenk = color;
    }

    // Pasif durumda kullanılacak rengi ayarlamak için kullanılan metod.
    public void setPasifRenk(Color color) {
        this.pasifRenk = color;
    }

    // Pasif durumu takip etmek için kullanılan özel metod.
    private void pasifDurumuTakip() {
        // Arka plan rengini, ikonu ve metni pasif duruma göre ayarla.
        setBackground(pasifRenk);
        setIcon(belirlenenPasifIcon);
        setText("Pasif");
        // Buton durumunu güncelle.
        aktifHal = false;
    }

    // Aktif durumu takip etmek için kullanılan özel metod.
    private void aktifDurumuTakip() {
        // Arka plan rengini, ikonu ve metni aktif duruma göre ayarla.
        setBackground(aktifRenk);
        setIcon(belirlenenAktifIcon);
        setText("Aktif");
        // Buton durumunu güncelle.
        aktifHal = true;
    }

    // Butona tıklandığında çalışacak olan dinleyici sınıfı.
    private class ButonTiklama implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Eğer buton aktif durumdaysa, diğer tüm butonları pasif duruma getir.
            if (aktifHal) {
                mevcutHaricTumButonlarPasifDurumuAyarla();
            } else {
                // Butonu aktif duruma getir ve ekrana şemayı bas.
                aktifDurumuTakip();
                System.out.println("Mutation çalıştırıldı: " + graphQLSchema);
            }
        }
    }

    // Tıklanan buton haricindeki tüm butonları pasif duruma getiren özel metod.
    private void mevcutHaricTumButonlarPasifDurumuAyarla() {
        // Butonun bulunduğu paneli al ve her bir öğeyi kontrol et.
        for (Component component : getParent().getComponents()) {
            // Eğer öğe bir CustomButton ise ve şu anki buton değilse, pasif duruma getir.
            if (component instanceof CustomButton && component != this) {
                ((CustomButton) component).pasifDurumuTakip();
            }
        }
    }

    // Uygulamayı test etmek için kullanılan main metodu.
    public static void main(String[] args) {
        // Swing işlemlerini ana thread dışında gerçekleştirmek için SwingUtilities.invokeLater kullanılır.
        SwingUtilities.invokeLater(() -> {
            // Ana pencereyi oluştur.
            JFrame frame = new JFrame("Buton Paneli");
            // Pencerenin kapatılma işlemini ayarla.
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // Pencerenin düzenini 4x4 bir GridLayout olarak ayarla.
            frame.setLayout(new GridLayout(4, 4, 10, 10));

            // 4x4'lük bir düzende CustomButton'ları oluştur ve panele ekle.
            CustomButton[] butonlar = new CustomButton[16];
            for (int i = 0; i < 16; i++) {
                butonlar[i] = new CustomButton("Buton " + (i + 1));
                frame.add(butonlar[i]);
            }

            // Bazı butonlara örnek şemalar atayarak uygulamayı test et.
            butonlar[0].setGraphQLSchema("https://api.example.com/graphql");
            butonlar[1].setGraphQLSchema("https://api.anotherexample.com/graphql");
            butonlar[2].setGraphQLSchema("https://api.yetanotherexample.com/graphql");

            // Pencere boyutunu ayarla ve görünür kıl.
            frame.setSize(400, 400);
            frame.setVisible(true);
        });
    }
}
