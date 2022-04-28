package com.springbootproject.springbootproject;

import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootprojectApplication.class, args);

		printMsgWithProgressBar("Loading", 25, 600);
	}

	public static void printMsgWithProgressBar(String message, int length, long timeInterval) {
		char incomplete = '░'; // U+2591 Unicode Character
		char complete = '█'; // U+2588 Unicode Character
		StringBuilder builder = new StringBuilder();
		Stream.generate(() -> incomplete).limit(length).forEach(builder::append);
		System.out.println(message);
		for (int i = 0; i < length; i++) {
			builder.replace(i, i + 1, String.valueOf(complete));
			String progressBar = "\r" + builder;
			System.out.print(progressBar);
			try {
				Thread.sleep(timeInterval);
			} catch (InterruptedException ignored) {

			}
		}
	}

}
