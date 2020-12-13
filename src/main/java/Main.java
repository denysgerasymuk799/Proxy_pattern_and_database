import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
//        URLScraper urlScraper = new URLScraper();
//        System.out.println(urlScraper.retrieve("https://www.google.com/"));
//        String url = "jdbc:sqlite:urls.db";
//
//
//        statement.executeUpdate("drop table if exists urls");
//        statement.executeUpdate("create table urls (id integer primary key autoincrement, url string)");
//        statement.executeUpdate("insert into urls (url) values('http://ucu.edu.ua')");
//
//        ResultSet rs = statement.executeQuery("select * from urls");
//
//        System.out.println("url = " + rs.getString("url"));
//        System.out.println("id = " + rs.getString("id"));
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
            System.out.println(url);
            cacheScraper.retrieve(url);
        }
    }
}
