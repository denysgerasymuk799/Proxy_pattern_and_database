import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;

public class CacheScraper implements Scraper {
    private final Scraper scraper;
    private final String dbPath = "jdbc:sqlite:urls.db";
    private final Statement statement;
    private int n_html_pages;
    BufferedWriter writer;

    @SneakyThrows
    public CacheScraper(Scraper scraper) {
        this.scraper = scraper;
        n_html_pages = 0;
        Connection conn = DriverManager.getConnection(dbPath);
        statement = conn.createStatement();

        statement.executeUpdate("drop table if exists urls");
        statement.executeUpdate("create table urls (id integer primary key autoincrement," +
                " url string)");
    }

    @SneakyThrows
    public boolean isInCache(String url) {
        ResultSet rs = statement.executeQuery("select url from urls");
//        ResultSet rs = statement.executeQuery("select id, url from urls where url = '" + url + "'");
        boolean found_url = false;
        while (rs.next()) {
            String retrieved_url = rs.getString("url");

            if (retrieved_url.equals(url))
                found_url = true;
        }
        return found_url;
    }

    @SneakyThrows
    public String retrieveCache(String url) {
        ResultSet rs = statement.executeQuery("select url from urls");
        String found_url = "";
        while (rs.next()) {
            String retrieved_url = rs.getString("url");

            if (retrieved_url.equals(url))
                found_url = rs.getString("url");
        }
        return found_url;
    }

    @SneakyThrows
    public void addToCache(String url, String html) {
        String command = "insert into urls (url) values('" + url + "')";
        statement.executeUpdate(command);

        String fileName = "html_texts/page" + n_html_pages;
        writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(html);

        ResultSet rs = statement.getGeneratedKeys();

        if(rs.next()){
            n_html_pages = rs.getInt(1);
            System.out.println("n_html_pages == " + n_html_pages);
        }
    }


    public String retrieve(String url) {
        if (!isInCache(url)) {
            System.out.println("URL is retrieved");
            String html = scraper.retrieve(url);
            addToCache(url, html);
        }

        System.out.println("URL is in Cache");
        return retrieveCache(url);
    }
}
