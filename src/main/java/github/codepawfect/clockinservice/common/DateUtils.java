package github.codepawfect.clockinservice.common;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * DateUtils is a utility class for handling date-related operations.
 */
public class DateUtils {

    /**
     * Returns the calendar week of the given date.
     *
     * @param date the date to get the calendar week for
     * @return the calendar week of the given date
     */
    public static int getCalenderWeek(LocalDate date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        return date.get(weekFields.weekOfWeekBasedYear());
    }
}
