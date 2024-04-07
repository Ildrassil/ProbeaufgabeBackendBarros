package com.example.googleSearchScraper;


import java.util.List;

public class Main {

    //Main method to run the GoogleSearchScraper
    public static void main(String[] args) {
        GoogleSearchScraper scraper = new GoogleSearchScraper();
        List<String> results = scraper.scrape();
        results.forEach(System.out::println);
    }
}