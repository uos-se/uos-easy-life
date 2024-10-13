package kr.ac.uos.uos_easy_life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kr.ac.uos.uos_easy_life.infra.PlaywrightInitializer;

@SpringBootApplication
public class UosEasyLifeApplication {

	public static void main(String[] args) {
		if (args.length > 0) {
			switch (args[0]) {
				case "--install-deps":
					PlaywrightInitializer.installDeps();
					return;

				case "--install-browser":
					PlaywrightInitializer.installBrowser();
					return;

				default:
					break;
			}
		}

		SpringApplication.run(UosEasyLifeApplication.class, args);
	}

}
