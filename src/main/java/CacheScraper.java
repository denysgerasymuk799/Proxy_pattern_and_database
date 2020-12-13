import lombok.SneakyThrows;

import java.sql.*;

public class CacheScraper implements Scraper {
    private Scraper scraper;
    private final String dbPath = "jdbc:sqlite:urls.db";
    private Statement statement;

    @SneakyThrows
    public CacheScraper(Scraper scraper) {
        this.scraper = scraper;
        Connection conn = DriverManager.getConnection(dbPath);
        statement = conn.createStatement();

        statement.executeUpdate("drop table if exists urls");
        statement.executeUpdate("create table urls (id integer primary key autoincrement, url string," +
                " html string)");
    }

    @SneakyThrows
    public boolean isInCache(String url) {
        ResultSet rs = statement.executeQuery("select url from urls");
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
        ResultSet rs = statement.executeQuery("select url, html from urls");
        String found_url_html = "";
        while (rs.next()) {
            String retrieved_url = rs.getString("url");

            if (retrieved_url.equals(url))
                found_url_html = rs.getString("html");
        }
        return found_url_html;
    }

    @SneakyThrows
    public void addToCache(String url, String html) {
//        PreparedStatement pstmt = conn.prepareStatement(SQL,
//                Statement.RETURN_GENERATED_KEYS);
//        statement.executeUpdate("insert into urls (url, html) values(?, ?)");
        statement.executeUpdate("insert into urls (url, html) values(?, ?)");
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
