// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package io.clientcore.core.utils;

import io.clientcore.core.annotations.Metadata;
import io.clientcore.core.annotations.MetadataProperties;
import io.clientcore.core.instrumentation.logging.ClientLogger;

import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static io.clientcore.core.utils.CoreUtils.isNullOrEmpty;

/**
 * Wrapper over java.time.OffsetDateTime used for specifying RFC1123 format during serialization and deserialization.
 * <p>
 * This only supports the format {@code EEE, dd MMM yyyy HH:mm:ss GMT}.
 */
@Metadata(properties = MetadataProperties.IMMUTABLE)
public final class DateTimeRfc1123 {
    private static final ClientLogger LOGGER = new ClientLogger(DateTimeRfc1123.class);

    /**
     * The actual datetime object.
     */
    private final OffsetDateTime dateTime;

    /**
     * Creates a new DateTimeRfc1123 object with the specified DateTime.
     * @param dateTime The DateTime object to wrap.
     */
    public DateTimeRfc1123(OffsetDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Creates a new DateTimeRfc1123 object with the specified DateTime.
     * @param formattedString The datetime string in RFC1123 format
     */
    public DateTimeRfc1123(String formattedString) {
        this.dateTime = parse(formattedString);
    }

    /**
     * Returns the underlying DateTime.
     * @return The underlying DateTime.
     */
    public OffsetDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * JSON creator for DateTimeRfc1123.
     * <p>
     * If {@code date} is null or an empty string null will be returned.
     *
     * @param date RFC1123 datetime string.
     * @return The DateTimeRfc1123 representation of the datetime string, or null if {@code date} is null or empty.
     */
    static DateTimeRfc1123 fromString(final String date) {
        if (isNullOrEmpty(date)) {
            return null;
        }

        return new DateTimeRfc1123(date);
    }

    /**
     * Parses the RFC1123 format datetime string into OffsetDateTime.
     *
     * @param date The datetime string in RFC1123 format
     * @return The underlying OffsetDateTime.
     *
     * @throws DateTimeException If the processing character is not a digit character.
     * @throws IllegalArgumentException if the given character is not recognized in the pattern of Month. such as 'Jan'.
     * @throws IndexOutOfBoundsException if the {@code beginIndex} is negative, or beginIndex is larger than length of
     *   {@code date}.
     */
    private static OffsetDateTime parse(final String date) {
        if (date.length() != 29 || !date.endsWith("GMT")) {
            throw LOGGER.throwableAtError().addKeyValue("date", date).log("Invalid date time.", DateTimeException::new);
        }

        try {
            return OffsetDateTime.of(LocalDateTime.of(parseInt(date, 12, 16),  // year
                parseMonth(date),        // month
                parseInt(date, 5, 7),    // dayOfMonth
                parseInt(date, 17, 19),  // hour
                parseInt(date, 20, 22),  // minute
                parseInt(date, 23, 25),  // second
                0                        // nanoOfSecond
            ), ZoneOffset.UTC);
        } catch (DateTimeException | IllegalArgumentException | IndexOutOfBoundsException e) {
            return OffsetDateTime.parse(date, DateTimeFormatter.RFC_1123_DATE_TIME);
        }
    }

    /**
     * Parses the specified substring of datetime to a 'int' value.
     *
     * @param date The datetime string in RFC1123 format.
     * @param beginIndex The beginning index, inclusive.
     * @param endIndex The ending index, exclusive.
     * @return The specified substring.
     *
     * @throws DateTimeException If the processing character is not digit character.
     */
    private static int parseInt(final String date, final int beginIndex, final int endIndex) {
        int num = 0;
        for (int i = beginIndex; i < endIndex; i++) {
            final char c = date.charAt(i);
            if (c < '0' || c > '9') {
                throw LOGGER.throwableAtError()
                    .addKeyValue("date", date)
                    .log("Invalid date time.", DateTimeException::new);
            }
            num = num * 10 + (c - '0');
        }

        return num;
    }

    /**
     * Parses the specified month substring of date time to a {@link Month}.
     * <p>
     * Previously this was implemented to return the integer representing the month ({@code 1-12}) but using an integer
     * to create {@link LocalDateTime} incurs a range validation check. Now this is implemented to return {@link Month}
     * which removes the range validation check.
     *
     * @param date The date time string in RFC1123 format.
     * @return The {@link Month} value which represents the month of year.
     * @throws IllegalArgumentException if the given character is not recognized in the pattern of Month. such as 'Jan'.
     * @throws IndexOutOfBoundsException if the {@code beginIndex} is negative, or beginIndex is larger than length of
     * {@code date}.
     */
    private static Month parseMonth(final CharSequence date) {
        char charAt8 = date.charAt(8);
        if (charAt8 == 'J') {
            // Jan, Jun, Jul
            char charAt9 = date.charAt(9);
            if (charAt9 == 'a' && date.charAt(10) == 'n') {
                return Month.JANUARY;
            } else if (charAt9 == 'u') {
                char charAt10 = date.charAt(10);
                if (charAt10 == 'n') {
                    return Month.JUNE;
                } else if (charAt10 == 'l') {
                    return Month.JULY;
                }
            }
        } else if (charAt8 == 'F' && date.charAt(9) == 'e' && date.charAt(10) == 'b') {
            return Month.FEBRUARY;
        } else if (charAt8 == 'M' && date.charAt(9) == 'a') {
            char charAt10 = date.charAt(10);
            if (charAt10 == 'r') {
                return Month.MARCH;
            } else if (charAt10 == 'y') {
                return Month.MAY;
            }
        } else if (charAt8 == 'A') {
            char charAt9 = date.charAt(9);
            if (charAt9 == 'p' && date.charAt(10) == 'r') {
                return Month.APRIL;
            } else if (charAt9 == 'u' && date.charAt(10) == 'g') {
                return Month.AUGUST;
            }
        } else if (charAt8 == 'S' && date.charAt(9) == 'e' && date.charAt(10) == 'p') {
            return Month.SEPTEMBER;
        } else if (charAt8 == 'O' && date.charAt(9) == 'c' && date.charAt(10) == 't') {
            return Month.OCTOBER;
        } else if (charAt8 == 'N' && date.charAt(9) == 'o' && date.charAt(10) == 'v') {
            return Month.NOVEMBER;
        } else if (charAt8 == 'D' && date.charAt(9) == 'e' && date.charAt(10) == 'c') {
            return Month.DECEMBER;
        }

        throw LOGGER.throwableAtError()
            .addKeyValue("month", date.toString())
            .log("Unknown month.", IllegalArgumentException::new);
    }

    /**
     * Convert the {@link OffsetDateTime dateTime} to date time string in RFC1123 format.
     *
     * @param dateTime The date time in OffsetDateTime format.
     * @return The date time string in RFC1123 format.
     * @throws IllegalArgumentException If {@link OffsetDateTime#getDayOfWeek()} or
     * {@link OffsetDateTime#getDayOfMonth()} is an unknown value.
     */
    public static String toRfc1123String(OffsetDateTime dateTime) {
        // ensure datetime is UTC offset.
        dateTime = dateTime.withOffsetSameInstant(ZoneOffset.UTC);

        byte[] bytes = new byte[29];
        final DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        switch (dayOfWeek) {
            case MONDAY:
                bytes[0] = 'M';
                bytes[1] = 'o';
                bytes[2] = 'n';
                break;

            case TUESDAY:
                bytes[0] = 'T';
                bytes[1] = 'u';
                bytes[2] = 'e';
                break;

            case WEDNESDAY:
                bytes[0] = 'W';
                bytes[1] = 'e';
                bytes[2] = 'd';
                break;

            case THURSDAY:
                bytes[0] = 'T';
                bytes[1] = 'h';
                bytes[2] = 'u';
                break;

            case FRIDAY:
                bytes[0] = 'F';
                bytes[1] = 'r';
                bytes[2] = 'i';
                break;

            case SATURDAY:
                bytes[0] = 'S';
                bytes[1] = 'a';
                bytes[2] = 't';
                break;

            case SUNDAY:
                bytes[0] = 'S';
                bytes[1] = 'u';
                bytes[2] = 'n';
                break;

            default:
                throw LOGGER.throwableAtError()
                    .addKeyValue("dayOfWeek", dayOfWeek.name())
                    .log("Unknown day of week.", IllegalArgumentException::new);
        }

        bytes[3] = ',';
        bytes[4] = ' ';

        zeroPad(dateTime.getDayOfMonth(), bytes, 5);

        bytes[7] = ' ';
        final Month month = dateTime.getMonth();
        switch (month) {
            case JANUARY:
                bytes[8] = 'J';
                bytes[9] = 'a';
                bytes[10] = 'n';
                break;

            case FEBRUARY:
                bytes[8] = 'F';
                bytes[9] = 'e';
                bytes[10] = 'b';
                break;

            case MARCH:
                bytes[8] = 'M';
                bytes[9] = 'a';
                bytes[10] = 'r';
                break;

            case APRIL:
                bytes[8] = 'A';
                bytes[9] = 'p';
                bytes[10] = 'r';
                break;

            case MAY:
                bytes[8] = 'M';
                bytes[9] = 'a';
                bytes[10] = 'y';
                break;

            case JUNE:
                bytes[8] = 'J';
                bytes[9] = 'u';
                bytes[10] = 'n';
                break;

            case JULY:
                bytes[8] = 'J';
                bytes[9] = 'u';
                bytes[10] = 'l';
                break;

            case AUGUST:
                bytes[8] = 'A';
                bytes[9] = 'u';
                bytes[10] = 'g';
                break;

            case SEPTEMBER:
                bytes[8] = 'S';
                bytes[9] = 'e';
                bytes[10] = 'p';
                break;

            case OCTOBER:
                bytes[8] = 'O';
                bytes[9] = 'c';
                bytes[10] = 't';
                break;

            case NOVEMBER:
                bytes[8] = 'N';
                bytes[9] = 'o';
                bytes[10] = 'v';
                break;

            case DECEMBER:
                bytes[8] = 'D';
                bytes[9] = 'e';
                bytes[10] = 'c';
                break;

            default:
                throw LOGGER.throwableAtError()
                    .addKeyValue("month", month.name())
                    .log("Unknown month.", IllegalArgumentException::new);
        }
        bytes[11] = ' ';

        int year = dateTime.getYear();
        int round = year / 1000;
        bytes[12] = (byte) ('0' + round);
        year = year - (1000 * round);
        round = year / 100;
        bytes[13] = (byte) ('0' + round);
        year = year - (100 * round);
        round = year / 10;
        bytes[14] = (byte) ('0' + round);
        bytes[15] = (byte) ('0' + (year - (10 * round)));

        bytes[16] = ' ';

        zeroPad(dateTime.getHour(), bytes, 17);
        bytes[19] = ':';
        zeroPad(dateTime.getMinute(), bytes, 20);
        bytes[22] = ':';
        zeroPad(dateTime.getSecond(), bytes, 23);
        bytes[25] = ' ';
        bytes[26] = 'G';
        bytes[27] = 'M';
        bytes[28] = 'T';

        // Use UTF-8 as it's more performant than ASCII in Java 8
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static void zeroPad(int value, byte[] bytes, int index) {
        if (value < 10) {
            bytes[index++] = '0';
            bytes[index] = (byte) ('0' + value);
        } else {
            int high = value / 10;
            bytes[index++] = (byte) ('0' + high);
            bytes[index] = (byte) ('0' + (value - (10 * high)));
        }
    }

    @Override
    public String toString() {
        return toRfc1123String(this.dateTime);
    }

    @Override
    public int hashCode() {
        return this.dateTime.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof DateTimeRfc1123)) {
            return false;
        }

        DateTimeRfc1123 rhs = (DateTimeRfc1123) obj;
        return this.dateTime.equals(rhs.getDateTime());
    }
}
