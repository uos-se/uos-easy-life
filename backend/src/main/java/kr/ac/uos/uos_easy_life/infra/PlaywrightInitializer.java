package kr.ac.uos.uos_easy_life.infra;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.CLI;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class PlaywrightInitializer {
  public static void installDeps() {
    // Install required dependencies.
    try {
      CLI.main(new String[] { "install-deps" });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void installBrowser() {
    // Launch browser to download the browser binaries.
    Playwright playwright = Playwright.create();
    LaunchOptions options = new LaunchOptions();
    options.setHeadless(true);
    Browser browser = playwright.chromium().launch(options);

    Page page = browser.newPage();
    page.navigate("https://example.com");

    browser.close();
    playwright.close();
  }
}
