package com.svlugovoy.datetimedemo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static com.svlugovoy.datetimedemo.util.PrintUtil.printMethodName;

public class Java8TimeDemo {

    public static void main(String[] args) {
        java8timeClockInstantDemo();
        java8timeZoneIdDemo();
        java8timeLocalTimeDemo();
        java8timeLocalDateDemo();
        java8timeLocalDateTimeDemo();
    }

    //Clock - доступ к текущей дате и времени, знает о часовых поясах
    //Instant - может быть использован для создания объектов java.util.Date.
    public static void java8timeClockInstantDemo() {
        printMethodName();
        long millis1 = Clock.systemDefaultZone().millis();
        long millis2 = System.currentTimeMillis();
        System.out.println("1) " + millis1);
        System.out.println("2) " + millis2);

        Instant instant = Clock.systemDefaultZone().instant();
        System.out.println("3) " + instant.toEpochMilli());
        Date legacyDate1 = Date.from(instant);   // legacy java.util.Date
        Date legacyDate2 = new Date();
        System.out.println("4) " + legacyDate1);
        System.out.println("5) " + legacyDate2);
    }

    //ZoneId - работа с часовыми поясами.
    //Часовые пояса содержат смещения, которые важны для конвертации дат и времени в местные.
    public static void java8timeZoneIdDemo() {
        printMethodName();
//        System.out.println(ZoneId.getAvailableZoneIds()); // prints all available timezone ids

        ZoneId zone1 = ZoneId.of("Europe/Kiev");
        ZoneId zone2 = ZoneId.of("America/Los_Angeles");
        System.out.println("1) " + zone1.getRules());
        System.out.println("2) " + zone2.getRules());
    }

    //LocalTime - время с учетом часового пояса
    public static void java8timeLocalTimeDemo() {
        printMethodName();
        ZoneId zone1 = ZoneId.of("Europe/Kiev");
        ZoneId zone2 = ZoneId.of("America/Los_Angeles");
        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);
        System.out.println("1) " + now1);
        System.out.println("2) " + now2);

        System.out.println("3) " + now1.isBefore(now2));  // false

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);
        System.out.println("4) " + hoursBetween);
        System.out.println("5) " + minutesBetween);

        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println("6) " + late);       // 23:59:59

        DateTimeFormatter germanFormatter = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.GERMAN);

        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println("7) " + leetTime);   // 13:37
    }

    //LocalDate представляет конкретную дату.
    //Объекты LocalDate неизменяемы. Помните, что каждая операция возвращает новый экземпляр.
    public static void java8timeLocalDateDemo() {
        printMethodName();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);
        System.out.println("1) " + today);
        System.out.println("2) " + tomorrow);
        System.out.println("3) " + yesterday);

        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println("4) " + dayOfWeek);    // FRIDAY

        DateTimeFormatter germanFormatter =
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM)
                        .withLocale(Locale.GERMAN);

        LocalDate xmas = LocalDate.parse("24.12.2014", germanFormatter);
        System.out.println("5) " + xmas);   // 2014-12-24
    }

    //LocalDateTime - комбинация даты и времени.
    public static void java8timeLocalDateTimeDemo() {
        printMethodName();
        LocalDateTime sylvester = LocalDateTime.of(2018, Month.DECEMBER, 31, 23, 59, 59);

        DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
        System.out.println("1) " + sylvester);
        System.out.println("2) " + dayOfWeek);      // MONDAY

        Month month = sylvester.getMonth();
        System.out.println("3) " + month);          // DECEMBER

        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println("4) " + minuteOfDay);    // 1439

        Instant instant = sylvester.atZone(ZoneId.systemDefault()).toInstant();
        Date legacyDate = Date.from(instant);
        System.out.println("5) " + legacyDate);     // Mon Dec 31 23:59:59 EET 2018

        DateTimeFormatter formatter = DateTimeFormatter
                        .ofPattern("MMM dd, yyyy - HH:mm");

        LocalDateTime parsed = LocalDateTime.parse("Nov 03, 2014 - 07:13", formatter);
        String string = formatter.format(parsed);
        System.out.println("6) " + string);     // Nov 03, 2014 - 07:13
        //https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    }
}

