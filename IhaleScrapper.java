package ihalescrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;

public class IhaleScrapper {
    public static void main(String[] args) {
        WebDriver driver;
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        String baseUrl = "https://www.ilan.gov.tr/ilan/kategori/9/ihale-duyurulari?txv=9&currentPage=";
        int maxPages = 10;
        String fileName = "ihale_listesi.txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 1; i <= maxPages; i++) {
                String url = baseUrl + i;
                System.out.println("Navigating to: " + url);

                // sayfalara yönlendirme
                driver.get(url);

                // sayfanın dolmasını bekleme
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                // listelenen ilanları çekme
                List<WebElement> ilanlar = driver.findElements(By.xpath("/html/body/igt-root/main/igt-ad-list/div/div/div[2]/section/div[2]/div[2]/div"));
                System.out.println("sayfalarda bulunan tüm linkler: " + ilanlar.size() + " links");

                List<String> ilanLinkleri = new ArrayList<>();

                //tüm detay sayfalarının urllerini toplama
                for (WebElement ilan : ilanlar) {
                    WebElement linkElement = ilan.findElement(By.tagName("a"));
                    String ilanUrl = linkElement.getAttribute("href");
                    ilanLinkleri.add(ilanUrl);
                    System.out.println("İlan URLleri: " + ilanUrl);
                    System.out.println(linkElement.getText());
                }

                // istenilen bilgileri taramak için her detay sayfasını ziyaret etme
                for (String link : ilanLinkleri) {
                    try {
                        driver.get(link);
                        Thread.sleep(3000);  // sayfanın yüklenmesini bekleme

                        // Jsoupla sayfa parselama
                        Document doc = Jsoup.parse(driver.getPageSource());

                        // dökümanlardan istenen bilgileri çekme
                       // String ihaleKayitNo = doc.select("div.record-no").text();
                       // String nitelikTuruMiktari = doc.select("div.nitelik-turu-miktari").text();
                        //String isinYapilacagiYer = doc.select("div.isin-yapilacagi-yer").text();
                       // String ihaleTuru = doc.select("div.ihale-turu").text();
                        String ilanURL = link;

                        // çekilen bilgileri yazdırma
                        /*System.out.println("İhale Kayıt No: " + ihaleKayitNo);
                        System.out.println("Niteliği, Türü ve Miktarı: " + nitelikTuruMiktari);
                        System.out.println("İşin Yapılacağı Yer: " + isinYapilacagiYer);
                        System.out.println("İhale Türü: " + ihaleTuru);
                        System.out.println("URL: " + ilanURL);*/
                        System.out.println("-----------------------------------");

                        // dosyaya yazdırma işlemmleri
                       /* writer.write("İhale Kayıt No: " + ihaleKayitNo + "\n");
                        writer.write("Niteliği, Türü ve Miktarı: " + nitelikTuruMiktari + "\n");
                        writer.write("İşin Yapılacağı Yer: " + isinYapilacagiYer + "\n");
                        writer.write("İhale Türü: " + ihaleTuru + "\n");*/
                        writer.write("URL: " + ilanURL + "\n");
                        writer.write("\n-----------------------------------\n\n");

                    } catch (IOException | InterruptedException e) {
                        System.err.println("linkler getirilemedi ya da ayrıştılamadı: " + link);
                        e.printStackTrace();
                    }
                }

                // Bir sonraki ilan grubuna devam etmek için ilan sayfasına geri dönme
                driver.navigate().back();
            }
            System.out.println("Veriler başarıyla tarandı. Kontrol edin " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // browserı kapama
            driver.quit();
        }
    }
}





