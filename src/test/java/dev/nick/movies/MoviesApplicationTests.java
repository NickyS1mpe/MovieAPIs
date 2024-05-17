package dev.nick.movies;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class MoviesApplicationTests {

	@Test
	void contextLoads() {
		LocalDateTime currentTime = LocalDateTime.now();

		// Define the date time formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

		// Format the current time
		String formattedTime = currentTime.format(formatter);

		// Print the formatted time
		System.out.println("Formatted time: " + formattedTime);
	}

}
