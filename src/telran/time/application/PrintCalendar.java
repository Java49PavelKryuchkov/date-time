package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Locale;

public class PrintCalendar {
private static final int TITLE_OFFSET = 8;
static DayOfWeek[] dayOfWeek = DayOfWeek.values();
	public static void main(String[] args) {
		try {
			RecordArguments recordArguments = getRecordArguments(args);
			printCalendar(recordArguments);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}


	private static DayOfWeek[] getCustomDays(DayOfWeek weekDay) {
		DayOfWeek[] week = new DayOfWeek[7];
		int newDay = DayOfWeek.valueOf(weekDay.toString()).getValue();
		int i = 0;
		while(i != 7) {
			if(newDay > 7) {
				newDay = 1;
			}
			week[i++] = DayOfWeek.of(newDay++);
		}
		return week;
	}


	private static void printCalendar(RecordArguments recordArguments) {
		printTitle(recordArguments.month(), recordArguments.year());
		printWeekDays(recordArguments.firstWeekDay());
		printDays(recordArguments.month(), recordArguments.year(), recordArguments.firstWeekDay());
	}

	private static void printTitle(int monthInput, int year) {
		Month month = Month.of(monthInput);
		String monthName = month.getDisplayName(TextStyle.FULL_STANDALONE,
				Locale.getDefault());
		System.out.printf("%s%s, %d\n", " ".repeat(TITLE_OFFSET), monthName, year);
	}

	private static void printWeekDays(DayOfWeek dayOfWeek) {
		System.out.println(" ");
		DayOfWeek[] customDays = getCustomDays(dayOfWeek);
		Arrays.stream(customDays).forEach(dw -> 
		System.out.printf("%s ", dw.getDisplayName(TextStyle.SHORT, Locale.getDefault())));
		System.out.println("");
	}

	private static void printDays(int month, int year, DayOfWeek dayOfWeek) {
		int nDays = getNumberOfDays(month, year);
		int currentWeekDay = getFirstWeekDay(month, year) - DayOfWeek.valueOf(dayOfWeek.toString()).getValue() + 1;
		printOffset(currentWeekDay, DayOfWeek.valueOf(dayOfWeek.toString()).getValue());
		for(int i = 1; i <= nDays; i++) {
			System.out.printf("%4d", i);
			currentWeekDay++;
			if(currentWeekDay == 7) {
				currentWeekDay = 0;
				System.out.println();
			}
		}
	}

	private static int getNumberOfDays(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		return ym.lengthOfMonth();
	}

	private static int getFirstWeekDay(int month, int year) {
		int weekDayNumber = LocalDate.of(year, month, 1)
				.get(ChronoField.DAY_OF_WEEK);
		if(weekDayNumber < 0) {
			weekDayNumber*=-1;
		}
		return weekDayNumber - 1;
	}

	private static void printOffset(int currentWeekDay, int day) {
		System.out.printf("%s", " ".repeat(4 * currentWeekDay));
	}

	private static RecordArguments getRecordArguments(String[] args) throws Exception {
		LocalDate ld = LocalDate.now();
		int month = args.length == 0 ? ld.get(ChronoField.MONTH_OF_YEAR) : getMonth(args[0]);
		int year = args.length > 1 ? getYear(args[1]) : ld.get(ChronoField.YEAR);
		DayOfWeek days = args.length > 2 ? getDays(args[2]) : DayOfWeek.valueOf("MONDAY");
		return new RecordArguments(month, year, days);
	}


	private static DayOfWeek getDays(String day) {
		DayOfWeek newDay = DayOfWeek.valueOf(day);
		return newDay;
	}


	private static int getYear(String yearStr) throws Exception {
		String message = "";
		int year = 0;
		year = Integer.parseInt(yearStr);
		try {
			if(year < 0) {
				message = "year must be a positive number";
			}
		} catch (NumberFormatException e) {
			message = "month must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return year;
	}

	private static int getMonth(String monthStr)throws Exception {
		String message = "";
		int month = 0;
		month = Integer.parseInt(monthStr);
		try {
			if(month < 1 || month > 12) {
				message = "month must be in the range [1-12]";
			}
		} catch (NumberFormatException e) {
			message = "month must be a number";
		}
		if(!message.isEmpty()) {
			throw new Exception(message);
		}
		return month;
	}

}
