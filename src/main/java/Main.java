import lombok.SneakyThrows;


public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        CacheScraper cacheScraper = new CacheScraper(new URLScraper());

        String[] targetURLs = new String[] {
                "https://www.newhomesource.com/learn/safest-cities-new-jersey/",
                "https://www.newhomesource.com/learn/safest-cities-maryland/",
                "https://www.newhomesource.com/learn/safest-cities-iowa/",
                "https://www.newhomesource.com/learn/safest-cities-kansas/",

                "https://www.newhomesource.com/learn/safest-cities-new-jersey/",
                "https://www.newhomesource.com/learn/safest-cities-maryland/",
                "https://www.newhomesource.com/learn/safest-cities-iowa/",
                "https://www.newhomesource.com/learn/safest-cities-kansas/"
        };

        for (String url: targetURLs) {
            System.out.println("\n Url for searching");
            System.out.println(url);
            cacheScraper.retrieve(url);
        }
    }
}
