package basic;
import java.awt.Graphics2D;  
import java.awt.image.BufferedImage;  
import java.io.ByteArrayInputStream;  
import java.io.File;  
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;  
import org.openqa.selenium.OutputType;  
import org.openqa.selenium.TakesScreenshot;  
import org.openqa.selenium.WebDriver;  
import org.openqa.selenium.chrome.ChromeDriver;  
import org.openqa.selenium.remote.Augmenter;  
  
public class ScreenShot {  
  private static int scrollTimeout = 0;  
  public ScreenShot(int timeout) {  
    scrollTimeout = timeout;  
  }  
  private static String getFullHeight(WebDriver driver) {  
    JavascriptExecutor js = (JavascriptExecutor) driver;  
    return js.executeScript("return document.body.scrollHeight").toString();  
  }  
  private static int getFullWidth(WebDriver driver) {  
    JavascriptExecutor js = (JavascriptExecutor) driver;  
    return ((Long) js.executeScript("return window.innerWidth",  
        new Object[0])).intValue();  
  }  
  private static int getWindowHeight(WebDriver driver) {  
    JavascriptExecutor js = (JavascriptExecutor) driver;  
    return ((Long) js.executeScript("return window.innerHeight",  
        new Object[0])).intValue();  
  }  
  private static void waitForScrolling() {  
    try {  
      Thread.sleep(scrollTimeout);  
    } catch (InterruptedException ignored) {  
    }  
  }  
  private static BufferedImage getScreenshotNative(WebDriver wd) {  
    ByteArrayInputStream imageArrayStream = null;  
    TakesScreenshot takesScreenshot = (TakesScreenshot) new Augmenter().augment(wd);  
    try {  
      imageArrayStream = new ByteArrayInputStream(takesScreenshot.getScreenshotAs(OutputType.BYTES));  
      return ImageIO.read(imageArrayStream);  
    } catch (IOException e) {  
      throw new RuntimeException("Can not parse screenshot data", e);  
    } finally {  
      try {  
        if (imageArrayStream != null) {  
          imageArrayStream.close();  
        }  
      } catch (IOException ignored) {  
      }  
    }  
  }  
  public static BufferedImage getScreenshot(WebDriver wd) {  
    JavascriptExecutor js = (JavascriptExecutor) wd;  
    int allH = Integer.parseInt(getFullHeight(wd));  
    int allW = getFullWidth(wd);  
    int winH = getWindowHeight(wd);  
    int scrollTimes = allH / winH;  
    int tail = allH - winH * scrollTimes;  
    BufferedImage finalImage = new BufferedImage(allW, allH, BufferedImage.TYPE_4BYTE_ABGR);  
    Graphics2D graphics = finalImage.createGraphics();  
    for (int n = 0; n <=scrollTimes; n++) {  
      js.executeScript("scrollTo(0, arguments[0])", winH * n);  
      waitForScrolling();  
      BufferedImage part = getScreenshotNative(wd);  
      graphics.drawImage(part, 0, n * winH, null);  
    }  
    if (tail > 0) {  
      js.executeScript("scrollTo(0, document.body.scrollHeight)");  
      waitForScrolling();  
      BufferedImage last = getScreenshotNative(wd);  
      BufferedImage tailImage = last.getSubimage(0, last.getHeight() - tail, last.getWidth(), tail);  
      graphics.drawImage(tailImage, 0, scrollTimes * winH, null);  
    }  
    graphics.dispose();  
    return finalImage;  
  }  
  public static void EShot(WebDriver wd, String filename) {  
    try {  
    	String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
      ImageIO.write(getScreenshot(wd), "PNG", new File("E:/shots/" + filename + " " + timestamp + ".png"));  
    } catch (IOException e) {  
      System.out.println(e);  
    }  
  }  
  public static void main (String argc[]){  
    WebDriver driver = new ChromeDriver();  
    //driver.get("https://en.wikipedia.org/wiki/Main_Page");
    driver.get("https://www.guru99.com/selenium-tutorial.html");
    
	driver.manage().window().maximize(); 
     ScreenShot.EShot(driver, "webScreenShot");  
     //driver.quit();  
  }  
} 