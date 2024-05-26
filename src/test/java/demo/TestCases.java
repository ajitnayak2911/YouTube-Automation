package demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    WebDriver driver;
    WebDriverWait wait;

    // ChromeDriver driver;
    public TestCases() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    // @BeforeClass
    // public void setup() {
    // System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
    // driver = new ChromeDriver();
    // wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    // }

    @Test(priority = 0)
    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");
        driver.get("https://www.youtube.com");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/", "URL does not match");

        Thread.sleep(10000);

    }

    @Test(priority = 1)

    public void clickAboutAndPrintMessage() throws InterruptedException {

        System.out.println("Start Test case: testCase02");
        driver.get("https://www.youtube.com");

        Thread.sleep(5000);

        try {

            // Scroll the left sidebar to find the "About" link
            WebElement aboutLink = driver.findElement(By.xpath("//a[normalize-space()='About']"));
            // ((JavascriptExecutor)
            // driver).executeScript("arguments[0].scrollIntoView(true);", aboutLink);
            aboutLink.click();
            Thread.sleep(5000);

            // Wait for the "About" page to load and get the message
            WebElement aboutMessage = driver.findElement(
                    By.xpath("//p[contains(text(),'Our mission is to give everyone a voice and show t')]"));
            System.out.println("Message on About page: " + aboutMessage.getText());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        System.out.println("end Test case: testCase02");
    }

    @Test(priority = 2)
    public void clickFilmsTabAndScroll() throws InterruptedException {

        System.out.println("Start Test case: testCase03");

        driver.get("https://www.youtube.com/");

        Thread.sleep(5000);

        SoftAssert softAssert = new SoftAssert();

        // try {
        // Click on the "Films" tab
        // WebElement filmsTab =
        // driver.findElement(By.xpath("//a[@class='yt-simple-endpoint style-scope
        // ytd-guide-entry-renderer' and @title='Films']"));
        // WebElement filmsTab =
        // driver.findElement(By.xpath("//a[@href='/feed/storefront?bp=ogUCKAU%3D']"));
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement filmsTab = explicitWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/feed/storefront?bp=ogUCKAU%3D']")));

        // WebElement filmsTab =
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@href='/feed/storefront?bp=ogUCKAU%3D']")));

        filmsTab.click();

        Thread.sleep(5000);

        // Wait for the "Top Selling" section to be visible
        // WebElement topSellingSection =
        // driver.findElement(By.xpath("//span[normalize-space(text())='Top
        // selling']"));
        WebElement topSellingSection = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='title-container' and @class='style-scope ytd-shelf-renderer']")));

        Thread.sleep(5000);

        // Find the carousel containing the movies in the "Top Selling" section
        WebElement carousel = topSellingSection
                .findElement(By.xpath("//div[@class='style-scope yt-horizontal-list-renderer']"));

        // Scroll the carousel to the extreme right
        // JavascriptExecutor js = (JavascriptExecutor) driver;
        // js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth",
        // carousel);
        Thread.sleep(5000);
        // Get all movie elements in the carousel
        List<WebElement> movies = carousel.findElements(By.xpath(".//ytd-thumbnail"));

        for (WebElement movie : movies) {
            // Check if the movie is marked "A" for Mature
            WebElement matureBadge = null;
            try {
                matureBadge = movie.findElement(By.xpath("//p[text()='A']"));
            } catch (NoSuchElementException e) {
                // Badge not found
            }
            softAssert.assertNotNull(matureBadge, "Movie is not marked 'A' for Mature");

            // Check if the movie is either "Comedy" or "Animation"
            WebElement genre = movie.findElement(By.xpath(
                    "//div[@class='style-scope yt-horizontal-list-renderer']//span[contains(text(), 'Comedy') or contains(text(), 'Animation')]"));
            softAssert.assertNotNull(genre, "Movie is neither 'Comedy' nor 'Animation'");
            System.out.println(genre.getText());
        }
        // } catch (Exception e) {
        // e.printStackTrace();
        // Assert.fail("Test failed due to exception: " + e.getMessage());
        // }

        // Assert all
        softAssert.assertAll();

        System.out.println("end Test case: testCase03");
    }

    @Test(priority = 3)
    public void clickMusicTabAndScroll() throws InterruptedException {

        System.out.println("Start Test case: testCase04");

        driver.get("https://www.youtube.com/");

        SoftAssert softAssert = new SoftAssert();
        // try {
        // Use explicit wait to locate the "Music" tab
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement musicTab = explicitWait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@href='/channel/UC-9-kyTW8ZkZNDHQJ6FgpwQ']")));
        musicTab.click();

        Thread.sleep(5000);

        // Wait for the first section in the "Music" tab to be visible
        WebElement firstSection = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//span[@id='title' and @class='style-scope ytd-shelf-renderer'])[1]")));
        Thread.sleep(5000);

        // Find the carousel containing the playlists in the first section
        WebElement carousel = firstSection
                .findElement(By.xpath("(//div[@id='items' and @class='style-scope yt-horizontal-list-renderer'])[1]"));

        // Scroll the carousel to the extreme right
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth", carousel);

        // Get all playlist elements in the carousel
        List<WebElement> playlists = carousel
                .findElements(By.xpath("//h3[@class='style-scope ytd-compact-station-renderer']"));

        for (WebElement playlist : playlists) {
            // Print the name of the playlist
            String playlistName = playlist.getText();
            System.out.println("Playlist Name: " + playlistName);

            // Get the number of tracks listed
            WebElement trackCountElement = playlist
                    .findElement(By.xpath(
                            "//h3[@class='style-scope ytd-compact-station-renderer']//ancestor::div[1]//p[@id='video-count-text']"));
            String trackCountText = trackCountElement.getText().split(" ")[0];
            int trackCount = Integer.parseInt(trackCountText);

            // Soft assert to check if the number of tracks is less than or equal to 50
            softAssert.assertTrue(trackCount <= 50, "Playlist '" + playlistName + "' has more than 50 tracks");
        }
        // } catch (Exception e) {
        // e.printStackTrace();
        // Assert.fail("Test failed due to exception: " + e.getMessage());
        // }

        // Assert all
        softAssert.assertAll();

        System.out.println("end Test case: testCase04");
    }

    @Test(priority = 4)
    public void clickNewsTabAndPrintPosts() throws InterruptedException {
        System.out.println("Start Test case: testCase05");

        //try {
            // Use explicit wait to locate the "News" tab
            WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement newsTab = explicitWait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//yt-formatted-string[text()='News']")));
            newsTab.click();

            // Wait for the first section in the "News" tab to be visible
            WebElement latestNewsSection = explicitWait
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                            "//span[text()='Latest news posts']")));
            Thread.sleep(5000);
            // Get the first three latest news posts
            List<WebElement> newsPosts = latestNewsSection.findElements(By.xpath("//div[@id='content']/ytd-post-renderer"));

            int totalLikes = 0;

            for (int i = 0; i < 3 && i < newsPosts.size(); i++) {
                WebElement post = newsPosts.get(i);
                WebElement titleElement = explicitWait.until(ExpectedConditions.visibilityOf(post.findElement(By.xpath("//yt-formatted-string[@id='home-content-text']"))));
                // Print the title of the news post
                //WebElement titleElement = post.findElement(By.xpath("//yt-formatted-string[@id='home-content-text']"));
                String title = titleElement.getText();
                System.out.println("Title: " + title);

                // Print the body of the news post
                // WebElement bodyElement = post.findElement(By.id("description-text"));
                // String body = bodyElement.getText();
                // System.out.println("Body: " + body);

                // Get the number of likes
                WebElement likeElement;
                int likes = 0;
                try {
                    likeElement = post.findElement(
                            By.xpath("//*[@id='like-button']/yt-button-shape/button/yt-touch-feedback-shape/div/div[2]"));


                    String likeText = likeElement.getAttribute("aria-label");
                    likes = Integer.parseInt(likeText.replaceAll("[^0-9]", ""));
                } catch (NoSuchElementException e) {
                    // No likes given means 0
                }
                System.out.println("Likes: " + likes);
                totalLikes += likes;
            }

            // Print the total number of likes
            System.out.println("Total Likes on the first 3 posts: " + totalLikes);

        //} catch (Exception e) {
          //  e.printStackTrace();
            //Assert.fail("Test failed due to exception: " + e.getMessage());
        //}
        
        System.out.println("end Test case: testCase05");

    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
