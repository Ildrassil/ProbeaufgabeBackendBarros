package com.example.googleSearchScraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.ArrayList;
import java.util.List;

public class GoogleSearchScraper {

    //Method to scrape the google search results
    public List<String> scrape() {

        /*Setup the EdgeDriver
        the reason for using EdgeDriver is that the WebDriverManager
        does not support the latest version of the Chrome browser*/
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        WebDriver driver = new EdgeDriver(options);

        //List to store the search results
        List<String> titles = new ArrayList<>();
        try {
            // Open the google search page inside the Edge browser
            driver.get("https://www.google.com");

            // Accept the cookies
            driver.findElement(By.id("L2AGLb")).click();

            // Find the search box and enter the search term
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("Software Entwickler");
            searchBox.submit();

            // Wait for 5 seconds to load the Page
            Thread.sleep(5000);

            // Execute the JavaScript to scroll to the bottom of the already loaded page
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

            // Another 5 seconds wait to load the rest of the page after scrolling
            Thread.sleep(5000);

            // Find all the search results sponsered or not sponsered
            List<WebElement> searchResults = driver.findElements(By.xpath("//div[@class='yuRUbf' or @class='uEierd']"));

            // Counter to limit the search results to 20
            int count = 0;

            // Loop through the search results and get the title, type and url
            for (WebElement result : searchResults) {

                // Break the loop if the count reaches 20
                if (count == 20){
                    break;
                }

                // Variables to store the title, url and type of the search result
                String title;
                String url = result.findElement(By.tagName("a")).getAttribute("href");
                String type;

                //Checking if the search result is sponsered or not
                if (result.getAttribute("class").equals("uEierd")) {
                    title = result.findElement(By.xpath(".//a[@class='sVXRqc']//span")).getText();
                    type = "Sponsored";
                } else {
                    title = result.findElement(By.tagName("h3")).getText();
                    type = "Not sponsored";
                }



                // Add the title to the list if it is not empty which means it is a valid search result
                // Increment the counter
                if (!title.equals("")) {
                    titles.add(title + " - " + type + " - " + url);
                    count++;
                }


            }

        }
        // Catch the exception if any
        catch (NoSuchElementException | InterruptedException e) {
            e.printStackTrace();
        }
        // Finally close the browser
        finally {
            driver.quit();
        }
        // Return the list of search results
        return titles;
    }

}