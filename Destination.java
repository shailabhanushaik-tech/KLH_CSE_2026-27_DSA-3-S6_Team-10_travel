public class Destination {
    public final String name;
    public final String state;
    public final String description;
    public final String[] attractions;
    public final String[] hotels;
    public final String[] restaurants;
    public final String[] transport;

    public Destination(String name, String state, String description,
                       String[] attractions, String[] hotels,
                       String[] restaurants, String[] transport) {
        this.name = name;
        this.state = state;
        this.description = description;
        this.attractions = attractions;
        this.hotels = hotels;
        this.restaurants = restaurants;
        this.transport = transport;
    }

    @Override
    public String toString() {
        return name + " - " + state;
    }
}
