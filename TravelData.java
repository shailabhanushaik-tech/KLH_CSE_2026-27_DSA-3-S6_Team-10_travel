import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class TravelData {
    private static final String DATA_FILE = "data/destinations.txt";

    private TravelData() { }

    public static Destination[] loadDestinations() {
        ArrayList<Destination> destinations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0 || line.startsWith("#")) continue;
                String[] fields = line.split("\\|", -1);
                if (fields.length != 7) throw new IllegalArgumentException("Invalid row in " + DATA_FILE);
                destinations.add(new Destination(fields[0], fields[1], fields[2], splitList(fields[3]),
                    splitList(fields[4]), splitList(fields[5]), splitList(fields[6])));
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Could not read " + DATA_FILE + ". Run the app from the project folder.", exception);
        }
        return destinations.toArray(new Destination[0]);
    }

    private static String[] splitList(String value) {
        String[] values = value.split(";", -1);
        for (int i = 0; i < values.length; i++) values[i] = values[i].trim();
        return values;
    }
}
