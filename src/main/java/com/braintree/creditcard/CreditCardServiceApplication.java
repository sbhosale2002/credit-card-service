package com.braintree.creditcard;

import com.braintree.creditcard.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class CreditCardServiceApplication {

	public static void main(String[] args) {
		CreditCardService creditCardService = new CreditCardService();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter input file path");
		String filePath = scanner.next();

		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(filePath));
			for (String line : lines) {
				creditCardService.processCardAction(line);
			}
			creditCardService.displayCardTransactions();
		} catch (IOException e) {
			log.error("Error accessing file " + filePath);
		}
	}
}
