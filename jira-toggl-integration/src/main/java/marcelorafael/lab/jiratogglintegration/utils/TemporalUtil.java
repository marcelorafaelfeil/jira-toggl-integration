package marcelorafael.lab.jiratogglintegration.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public class TemporalUtil {
	public static ChronoUnit getUnit(String time) {
		if (time.endsWith("m")) {
			return ChronoUnit.MINUTES;
		} else if(time.endsWith("h")) {
			return ChronoUnit.HOURS;
		} else if (time.endsWith("d")) {
			return ChronoUnit.DAYS;
		} else if (time.endsWith("M")) {
			return ChronoUnit.MONTHS;
		} else if (time.endsWith("y")) {
			return ChronoUnit.YEARS;
		}
		return ChronoUnit.HOURS;
	}

	public static Integer getTimeOfUnit(String time) {
		try {
			String timeAsString = time.substring(0, time.length() - 1);
			return Integer.valueOf(timeAsString);
		} catch (Exception e) {
			log.error("Erro ao obter o número do tempo.");
			return 0;
		}
	}

	public static Boolean equalsZoneDateTimeAndLocalDateTime(ZonedDateTime zonedDateTime, LocalDateTime localDateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String zdtString = zonedDateTime.format(formatter);
		String ldtString = localDateTime.format(formatter);

		log.info("EQUALS => [{} = {}]", zdtString, ldtString);
		return zdtString.equals(ldtString);
	}

	public static Boolean equalsZoneDateTimeAndDate(ZonedDateTime zonedDateTime, Date date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String zdtString = zonedDateTime.format(formatter);
		String ldtString = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).format(formatter);

		log.info("EQUALS => [{} = {}]", zdtString, ldtString);
		return zdtString.equals(ldtString);
	}
}
