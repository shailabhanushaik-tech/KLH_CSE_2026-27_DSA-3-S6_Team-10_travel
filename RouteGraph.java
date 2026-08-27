import java.util.ArrayList;
import java.util.Arrays;

public class RouteGraph {
    private final String[] places;
    private final int[][] weights;

    public RouteGraph(String[] places) {
        this.places = places;
        this.weights = new int[places.length][places.length];
        for (int[] row : weights) Arrays.fill(row, Integer.MAX_VALUE);
        for (int i = 0; i < places.length; i++) weights[i][i] = 0;
        addRoute("Delhi", "Agra", 3); addRoute("Agra", "Jaipur", 4);
        addRoute("Delhi", "Jaipur", 5); addRoute("Delhi", "Varanasi", 6);
        addRoute("Jaipur", "Mumbai", 9); addRoute("Mumbai", "Goa", 6);
        addRoute("Goa", "Kochi", 8); addRoute("Kochi", "Hyderabad", 7);
        addRoute("Hyderabad", "Kolkata", 10); addRoute("Kolkata", "Amritsar", 12);
        addRoute("Varanasi", "Kolkata", 8); addRoute("Delhi", "Amritsar", 5);
    }

    private void addRoute(String from, String to, int hours) {
        int a = indexOf(from), b = indexOf(to);
        if (a >= 0 && b >= 0) weights[a][b] = weights[b][a] = hours;
    }

    private int indexOf(String place) {
        for (int i = 0; i < places.length; i++) if (places[i].equals(place)) return i;
        return -1;
    }

    public String shortestPath(String start, String end) {
        int source = indexOf(start), target = indexOf(end);
        if (source < 0 || target < 0) return "Choose valid locations.";
        int[] distance = new int[places.length]; int[] previous = new int[places.length]; boolean[] used = new boolean[places.length];
        Arrays.fill(distance, Integer.MAX_VALUE); Arrays.fill(previous, -1); distance[source] = 0;
        for (int count = 0; count < places.length; count++) {
            int current = -1;
            for (int i = 0; i < places.length; i++) if (!used[i] && distance[i] != Integer.MAX_VALUE && (current < 0 || distance[i] < distance[current])) current = i;
            if (current < 0) break;
            used[current] = true;
            for (int next = 0; next < places.length; next++) if (weights[current][next] != Integer.MAX_VALUE && distance[current] + weights[current][next] < distance[next]) { distance[next] = distance[current] + weights[current][next]; previous[next] = current; }
        }
        if (distance[target] == Integer.MAX_VALUE) return "No connected route found.";
        ArrayList<String> path = new ArrayList<>();
        for (int at = target; at >= 0; at = previous[at]) path.add(0, places[at]);
        return String.join("  ->  ", path) + "\nEstimated travel time: " + distance[target] + " hours";
    }
}
