import java.util.*;

public class KrankenhausOperationskalender extends Kalender {

    // Data structure for storing surgery dates for each room
    private Map<Integer, Map<String, List<String>>> operationssaalTermine;

    // Constructor
    public KrankenhausOperationskalender() {
        // (Aufruf) Call the constructor of the superclass (Superklasse: Kalendar)
        super();

        // Initialization of the data structure for operation dates
        operationssaalTermine = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            operationssaalTermine.put(i, new HashMap<>());
        }
    }

    // Function for visualizing the day view of an operating theatre plan
    /**
     * @param daysInMonth
     * @param week
     * @param saal
     */
    public void printOperationssaalTagesansicht(int[] daysInMonth, int week, String[] saal) {

        System.out.println("Tagesansicht für Operationssaal " + saal);

        // Total number of days in current month
        int totalDays = daysInMonth[month - 1];
        int currentDay = 1;

        // Determine the day of the week of the first day of the month
        int firstDayOfWeek = getFirstDayOfWeek(year, month);

        // Output of blanks for days before the first day of the month
        for (int i = 0; i < firstDayOfWeek; i++) {
            System.out.print("                    ");
        }

        // Loop for outputting the day view
        while (currentDay <= totalDays) {
            for (int i = firstDayOfWeek; i < 7 && currentDay <= totalDays; i++) {
                // Generate a key for the current date
                String dateKey = year + "-" + month + "-" + currentDay;

                // A space for single-digit days to improve alignment (Ein Leerzeichen für einstellige Tage, um die Ausrichtung zu verbessern)
                if (currentDay < 10) {
                    System.out.print(" ");
                }

            // Output of the current day with a fixed format (20 characters wide)
                System.out.printf("%-20d", currentDay);

                // Check whether surgery appointments are available in the room for this day
                if (operationssaalTermine.get(saal).containsKey(dateKey)) {
                    // Output of the surgery dates for this day
                    for (String operation : operationssaalTermine.get(saal).get(dateKey)) {
                        System.out.println("   - " + operation);
                    }
                }

                currentDay++;
            }
            System.out.println();
            // For the following weeks, the first day always starts with Sunday
            firstDayOfWeek = 0;

        }
    }

    // Function for adding an operation date for a specific room
    /**
     * @param saal
     * @param day
     * @param time
     * @param operation
     */
    public void addOperationstermin(int saal, int day, String time, String operation) {
        // Generate a key for the date of the operation date
        String dateKey = year + "-" + month + "-" + day;

        // Check whether there are already surgery appointments for this day in the room
        if (!operationssaalTermine.get(saal).containsKey(dateKey)) {
            // If not, initialize an empty list for this day
            operationssaalTermine.get(saal).put(dateKey, new ArrayList<>());
        }

        // Add the operation date to the list for this day and room (Saal)
        operationssaalTermine.get(saal).get(dateKey).add(time + " - " + operation);
    }
}