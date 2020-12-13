import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class URLScraper implements Scraper {
    @SneakyThrows
    public String retrieve(String url) {
        Document doc = Jsoup.connect(url).get();
        return doc.toString();
    }
}
