import java.util.*;
import java.text.DateFormatSymbols;

public class Kalender {
    // Data structure for storing events
    public static Map<String, List<String>> events = new HashMap<>();
    // global variables
    static int year, month;

    public static void main(String[] args, int[] daysInMonth) {

        KrankenhausOperationskalender operationskalender = new KrankenhausOperationskalender();

        //the last days of each month in order (Rheinfolge)
        int[] dayInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // Days of the week
        String[] week = {"Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"}; // die Wochentage

        int day = 0;

        // User input for the year
        Scanner sc = new Scanner(System.in);
        System.out.println("Bitte geben Sie das Jahr ein: ");
        year = sc.nextInt();

        // Check whether the year is within the valid range
        if (year < 1900 || year > 2100) {
            System.out.println("Falsche Eingabe!!");
            return;
        }

        // User input for the month
        System.out.println("Bitte geben Sie den Monat ein: ");
        month = sc.nextInt();

        // User input for the day
        System.out.println("Bitte geben Sie den Tag ein: ");
        day = sc.nextInt();

        // Leap year
        int weekday = (year - 1900) * 365 + (year - 1900) / 4;

        // Check the leap year
        if ((year % 4 == 0 && year % 100 != 0 || year % 400 == 0) && month == 2) {
            weekday = weekday - 1;
            dayInMonth[1] = 29; // Februar hat 29 Tage in einem Schaltjahr
        }

        // Event management (Ereignisverwaltung)
        String dateKey = year + "-" + month + "-" + day;
        if (!events.containsKey(dateKey)) {
            events.put(dateKey, new ArrayList<>());
        }

        // User input for the event time
        System.out.println("Bitte geben Sie die Uhrzeit des Ereignisses ein: ");
        String time = sc.next();

        // User input for the event
        System.out.println("Bitte geben Sie das Ereignis ein: ");
        String event = sc.next();

        //events.get(dateKey).add(time + " - " + event);

        // Month view (Monatsansicht)
        printMonthView(dayInMonth, week, events);
        System.out.println("Ergeiniss: "+ events.get(dateKey)+ time + " - " +event);

        /**
         * // Operationsterminverwaltung
         *         System.out.println("Bitte geben Sie den Operationssaal ein (1-5): ");
         *         int saal = sc.nextInt();
         *
         *         if (saal < 1 || saal > 5) {
         *             System.out.println("Ungültige Eingabe für den Operationssaal!!");
         *             return;
         *         }
         *
         *         System.out.println("Bitte geben Sie den Tag ein: ");
         *         day = sc.nextInt();
         *
         *         System.out.println("Bitte geben Sie die Uhrzeit des Operationstermins ein: ");
         *         time = sc.next();
         *
         *         System.out.println("Bitte geben Sie die Art der Operation ein: ");
         *         String operation = sc.next();
         *
         *         operationskalender.addOperationstermin(saal, day, time, operation);
         *
         *         // Tagesansicht für den spezifischen Operationssaal
         *         operationskalender.printOperationssaalTagesansicht(daysInMonth,saal,week);
         */
    }

    // Function for visualizing the monthly view in the console
    /**
     *
     * @param daysInMonth
     * @param week
     * @param events
     */
    private static void printMonthView(int[] daysInMonth, String[] week, Map<String, List<String>> events) {
        // Output of the month name
        System.out.println("Monatsansicht: " + new DateFormatSymbols().getMonths()[month - 1]);

        // Output of the weekdays with a fixed format
        for (int i = 0; i < week.length; i++) {
            System.out.printf("%-12s", week[i]);
        }
        System.out.println();

        // Total number of days in a month
        int totalDays = daysInMonth[month - 1];
        int currentDay = 1;

        // Determine the day of the week of the first day of the month
        int firstDayOfWeek = getFirstDayOfWeek(year, month);

        // Output blanks for days before the first day of the month
        for (int i = 0; i < firstDayOfWeek; i++) {
            // Space for days before the first day of the month
            System.out.print("            ");
        }

        // Loop for the output of the days of the month
        while (currentDay <= totalDays) {
            for (int i = firstDayOfWeek; i < 7 && currentDay <= totalDays; i++) {
                String dateKey = year + "-" + month + "-" + currentDay;
                System.out.printf("%-12s", currentDay);

                // Check whether there are events for this day
                if (events.containsKey(dateKey)) {
                    for (String ev : events.get(dateKey)) {
                        // Output of events for this day
                        System.out.println("   - " + ev);
                    }
                }
                currentDay++;
            }
            System.out.println();
            // For the following weeks, the first day always starts with Sunday
            firstDayOfWeek = 0;
        }
    }


    // (Hilfsfunktion) function for calculating the weekday of the first day of the month

    /**
     *
     * @param year
     * @param month
     * @return
     */
    public static int getFirstDayOfWeek(int year, int month) {

        // If the month is January or February, they are considered months 13 and 14 of the previous year
        if (month < 3) {
            month += 12;
            year--;
        }

        // k and j represent the last two digits and the first two digits of the year respectively
        int k = year % 100;
        int j = year / 100;

        //The first day of the month
        int dayOfMonth = 2;

        // Calculation of the day of the week using the Zeller's Congruence Algorithm
        int h = (dayOfMonth + (13 * (month + 1)) / 5 + k + (k / 4) + (j / 4) - (2 * j)) % 7;

        // The result h can be negative, therefore convert to the range [0, 6]
        return (h + 5) % 7;
    }
}

