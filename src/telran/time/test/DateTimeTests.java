package telran.time.test;
import telran.time.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import org.junit.jupiter.api.Test;
import java.time.temporal.UnsupportedTemporalTypeException;
import telran.time.BarMizvaAdjuster;

class DateTimeTests {

	@Test
	void test() {
		LocalDate birthAS = LocalDate.of(1799, 6, 6);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, YYYY EEEE");
		System.out.println(birthAS.format(dtf));
		LocalDate barMizva = birthAS.plusYears(13);
		assertEquals(barMizva, birthAS.with(new BarMizvaAdjuster()));
		assertThrowsExactly(UnsupportedTemporalTypeException.class,
				() -> LocalTime.now().with(new BarMizvaAdjuster()));

	}
	@Test
	void nextFriday13Test() {
		TemporalAdjuster fr13 = new NextFriday13();
		ZonedDateTime zdt = ZonedDateTime.now();
		ZonedDateTime fr13Expected = ZonedDateTime.of(2023, 10, 13, 0, 0, 0, 0, ZoneId.systemDefault());
		assertEquals(fr13Expected, zdt.with(fr13));
		LocalDate fr13Expected2 = LocalDate.of(2024, 9, 13);
		LocalDate ld = LocalDate.of(2023, 10, 13);
		assertEquals(fr13Expected2, ld.with(fr13));

	}
}
