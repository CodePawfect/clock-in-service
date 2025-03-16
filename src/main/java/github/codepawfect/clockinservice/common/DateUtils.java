package github.codepawfect.clockinservice.common;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

/** DateUtils is a utility class for handling date-related operations. */
public class DateUtils {

  /**
   * Returns the calendar week of the given date.
   *
   * @param date the date to get the calendar week for
   * @return the calendar week of the given date
   */
  public static int getCalenderWeek(LocalDate date) {
   return date.get(WeekFields.ISO.weekOfWeekBasedYear());
  }
}
