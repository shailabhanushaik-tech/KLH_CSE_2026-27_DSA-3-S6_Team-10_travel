import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicButtonUI;

public class TravelGuideApp extends JFrame {
    private static final Color NAVY = new Color(20, 37, 63);
    private static final Color TEAL = new Color(21, 137, 137);
    private static final Color GOLD = new Color(239, 174, 66);
    private static final Color PAPER = new Color(247, 244, 237);
    private final Destination[] destinations = TravelData.loadDestinations();
    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);
    private final RouteGraph graph;

    public TravelGuideApp() {
        super("Travel Guide | India, thoughtfully mapped");
        Algorithms.sortByName(destinations);
        String[] names = new String[destinations.length];
        for (int i = 0; i < destinations.length; i++) names[i] = destinations[i].name;
        graph = new RouteGraph(names);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1050, 680));
        setSize(1200, 760);
        setLocationRelativeTo(null);
        buildUi();
    }

    private void buildUi() {
        JPanel root = new JPanel(new BorderLayout()); root.setBackground(PAPER);
        root.add(buildSidebar(), BorderLayout.WEST);
        content.setBackground(PAPER);
        content.add(homePanel(), "home"); content.add(searchPanel(), "search"); content.add(fuzzyPanel(), "fuzzy");
        content.add(similarPanel(), "similar"); content.add(detailsPanel(), "details"); content.add(routePanel(), "route");
        content.add(collectionPanel("Hotels", 0), "hotels"); content.add(collectionPanel("Restaurants", 1), "restaurants");
        content.add(collectionPanel("Transportation", 2), "transport");
        root.add(content, BorderLayout.CENTER); setContentPane(root); cards.show(content, "home");
    }

    private JPanel buildSidebar() {
        JPanel side = new JPanel(new BorderLayout()); side.setPreferredSize(new Dimension(245, 0)); side.setBackground(NAVY);
        JPanel brand = new JPanel(new GridLayout(2, 1)); brand.setBorder(BorderFactory.createEmptyBorder(28, 25, 22, 18)); brand.setBackground(NAVY);
        JLabel title = new JLabel("TRAVEL GUIDE"); title.setForeground(Color.WHITE); title.setFont(new Font("Serif", Font.BOLD, 23));
        JLabel subtitle = new JLabel("India, thoughtfully mapped"); subtitle.setForeground(new Color(182, 202, 207)); subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        brand.add(title); brand.add(subtitle); side.add(brand, BorderLayout.NORTH);
        JPanel menu = new JPanel(new GridLayout(0, 1, 0, 5)); menu.setBorder(BorderFactory.createEmptyBorder(8, 14, 12, 14)); menu.setBackground(NAVY);
        addMenu(menu, "Home", "home"); addMenu(menu, "Search destinations", "search"); addMenu(menu, "Fuzzy search", "fuzzy");
        addMenu(menu, "Similar destinations", "similar"); addMenu(menu, "Destination details", "details"); addMenu(menu, "Route finder", "route");
        addMenu(menu, "Hotels", "hotels"); addMenu(menu, "Restaurants", "restaurants"); addMenu(menu, "Transportation", "transport");
        JButton exit = menuButton("Exit"); exit.addActionListener(e -> System.exit(0)); menu.add(exit); side.add(menu, BorderLayout.CENTER);
        JLabel footer = new JLabel("10 destinations  /  11 routes", SwingConstants.CENTER); footer.setForeground(new Color(160, 181, 190)); footer.setBorder(BorderFactory.createEmptyBorder(15, 5, 20, 5)); side.add(footer, BorderLayout.SOUTH);
        return side;
    }

    private void addMenu(JPanel menu, String label, String card) { JButton button = menuButton(label); button.addActionListener(e -> cards.show(content, card)); menu.add(button); }
    private JButton menuButton(String label) { JButton button = new JButton(label); button.setUI(new BasicButtonUI()); button.setOpaque(true); button.setContentAreaFilled(true); button.setBorderPainted(false); button.setHorizontalAlignment(SwingConstants.LEFT); button.setForeground(new Color(224, 235, 234)); button.setBackground(NAVY); button.setFont(new Font("SansSerif", Font.BOLD, 13)); button.setBorder(BorderFactory.createEmptyBorder(11, 14, 11, 8)); button.setFocusPainted(false); return button; }

    private JPanel base(String eyebrow, String heading, String intro) {
        JPanel panel = new JPanel(new BorderLayout(0, 20)); panel.setBackground(PAPER); panel.setBorder(BorderFactory.createEmptyBorder(38, 45, 35, 45));
        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4)); header.setBackground(PAPER);
        JLabel top = new JLabel(eyebrow.toUpperCase()); top.setForeground(TEAL); top.setFont(new Font("SansSerif", Font.BOLD, 12));
        JLabel head = new JLabel(heading); head.setForeground(NAVY); head.setFont(new Font("Serif", Font.BOLD, 35)); header.add(top); header.add(head); panel.add(header, BorderLayout.NORTH);
        if (intro != null) { JLabel copy = new JLabel(intro); copy.setForeground(new Color(82, 96, 105)); copy.setFont(new Font("SansSerif", Font.PLAIN, 14)); panel.add(copy, BorderLayout.SOUTH); }
        return panel;
    }

    private JPanel homePanel() {
        JPanel panel = base("Welcome", "Find your next Indian story.", "Search, compare and connect destinations with simple algorithms under the hood.");
        JPanel body = new JPanel(new BorderLayout(0, 22)); body.setBackground(PAPER);
        JPanel feature = new JPanel(new BorderLayout()); feature.setBackground(TEAL); feature.setBorder(BorderFactory.createEmptyBorder(25, 28, 25, 28));
        JLabel featureText = new JLabel("10 destinations  |  30+ places to explore"); featureText.setForeground(Color.WHITE); featureText.setFont(new Font("Serif", Font.BOLD, 25)); feature.add(featureText, BorderLayout.CENTER);
        body.add(feature, BorderLayout.NORTH);
        JPanel grid = new JPanel(new GridLayout(2, 2, 14, 14)); grid.setBackground(PAPER);
        addFeature(grid, "01  Discover", "Search attractions, hotels and food by name."); addFeature(grid, "02  Find the fit", "Fuzzy and similarity search handle imperfect ideas."); addFeature(grid, "03  Go further", "Dijkstra finds the shortest route across India."); addFeature(grid, "04  Explain it", "Every result is powered by a beginner-friendly DSA.");
        body.add(grid, BorderLayout.CENTER); panel.add(body, BorderLayout.CENTER); return panel;
    }
    private void addFeature(JPanel grid, String title, String text) { JPanel item = new JPanel(new GridLayout(2, 1)); item.setBackground(Color.WHITE); item.setBorder(BorderFactory.createEmptyBorder(18, 18, 14, 18)); JLabel a = new JLabel(title); a.setForeground(NAVY); a.setFont(new Font("SansSerif", Font.BOLD, 15)); JLabel b = new JLabel("<html>" + text + "</html>"); b.setForeground(new Color(92, 103, 108)); item.add(a); item.add(b); grid.add(item); }

    private JPanel searchPanel() {
        JPanel panel = base("DSA / KMP", "Search destinations", "KMP pattern matching checks names and attraction titles without built-in search methods.");
        JPanel body = new JPanel(new BorderLayout(0, 15)); body.setBackground(PAPER); JTextField field = input("Try: charminar, beach, fort..."); JButton find = actionButton("Search"); JPanel bar = new JPanel(new BorderLayout(10, 0)); bar.setBackground(PAPER); bar.add(field, BorderLayout.CENTER); bar.add(find, BorderLayout.EAST); body.add(bar, BorderLayout.NORTH);
        JTextArea results = outputArea(); body.add(new JScrollPane(results), BorderLayout.CENTER); find.addActionListener(e -> showExact(field.getText(), results)); panel.add(body, BorderLayout.CENTER); return panel;
    }
    private void showExact(String query, JTextArea results) { results.setText(""); if (query.trim().length() == 0) { results.setText("Enter a word to search."); return; } for (Destination d : destinations) { boolean found = Algorithms.kmpContains(d.name, query) || Algorithms.kmpContains(d.description, query); for (String attraction : d.attractions) found = found || Algorithms.kmpContains(attraction, query); if (found) results.append("MATCH   " + d.name + " / " + d.state + "\n        " + d.description + "\n\n"); } if (results.getText().length() == 0) results.setText("No exact matches. Try Fuzzy search for spelling mistakes."); }

    private JPanel fuzzyPanel() {
        JPanel panel = base("DSA-3 / Edit distance", "Fuzzy search", "Typo-tolerant search ranks destination names by the fewest insertions, deletions and replacements."); JPanel body = new JPanel(new BorderLayout(0, 15)); body.setBackground(PAPER); JTextField field = input("Try: Charminarr"); JButton find = actionButton("Suggest"); JPanel bar = new JPanel(new BorderLayout(10, 0)); bar.setBackground(PAPER); bar.add(field, BorderLayout.CENTER); bar.add(find, BorderLayout.EAST); body.add(bar, BorderLayout.NORTH); JTextArea results = outputArea(); body.add(new JScrollPane(results), BorderLayout.CENTER); find.addActionListener(e -> { String query = field.getText().trim(); StringBuilder text = new StringBuilder(); for (Destination d : destinations) for (String attraction : d.attractions) { int distance = Algorithms.editDistance(query, attraction); if (distance <= 4) text.append(attraction).append("  |  ").append(d.name).append("  |  edit distance: ").append(distance).append("\n"); } results.setText(text.length() == 0 ? "No close suggestions found." : text.toString()); }); panel.add(body, BorderLayout.CENTER); return panel;
    }

    private JPanel similarPanel() {
        JPanel panel = base("DSA-3 / Similarity", "Similar destinations", "Compare a destination with every other destination using normalized edit-distance similarity."); JPanel body = new JPanel(new BorderLayout(0, 15)); body.setBackground(PAPER); JComboBox<String> choices = destinationBox(); JButton find = actionButton("Compare"); JPanel bar = new JPanel(new BorderLayout(10, 0)); bar.setBackground(PAPER); bar.add(choices, BorderLayout.CENTER); bar.add(find, BorderLayout.EAST); body.add(bar, BorderLayout.NORTH); JTextArea results = outputArea(); body.add(new JScrollPane(results), BorderLayout.CENTER); find.addActionListener(e -> { String selected = (String) choices.getSelectedItem(); StringBuilder text = new StringBuilder(); for (Destination d : destinations) if (!d.name.equals(selected)) text.append(String.format("%-14s %3.0f%% similar\n", d.name, Algorithms.similarity(selected, d.name) * 100)); results.setText(text.toString()); }); panel.add(body, BorderLayout.CENTER); return panel;
    }

    private JPanel detailsPanel() {
        JPanel panel = base("Explore", "Destination details", "Choose a place to see its attractions, stays, food and ways to move around."); JPanel body = new JPanel(new BorderLayout(0, 15)); body.setBackground(PAPER); JComboBox<String> choices = destinationBox(); JTextArea results = outputArea(); body.add(choices, BorderLayout.NORTH); body.add(new JScrollPane(results), BorderLayout.CENTER); choices.addActionListener(e -> fillDetails((String) choices.getSelectedItem(), results)); fillDetails((String) choices.getSelectedItem(), results); panel.add(body, BorderLayout.CENTER); return panel;
    }
    private void fillDetails(String name, JTextArea output) { Destination d = find(name); if (d == null) return; output.setText(d.name.toUpperCase() + "  /  " + d.state + "\n\n" + d.description + "\n\nATTRACTIONS\n" + lines(d.attractions) + "\nHOTELS\n" + lines(d.hotels) + "\nRESTAURANTS\n" + lines(d.restaurants) + "\nTRANSPORT\n" + lines(d.transport)); }
    private String lines(String[] values) { StringBuilder result = new StringBuilder(); for (String value : values) result.append("  - ").append(value).append("\n"); return result.toString(); }

    private JPanel routePanel() {
        JPanel panel = base("DSA-2 / Dijkstra", "Route finder", "The graph stores travel-time edges between cities and Dijkstra finds the cheapest connected route."); JPanel body = new JPanel(new BorderLayout(0, 15)); body.setBackground(PAPER); JPanel bar = new JPanel(new GridLayout(1, 3, 10, 0)); bar.setBackground(PAPER); JComboBox<String> from = destinationBox(); JComboBox<String> to = destinationBox(); JButton find = actionButton("Find route"); bar.add(from); bar.add(to); bar.add(find); body.add(bar, BorderLayout.NORTH); JTextArea results = outputArea(); body.add(new JScrollPane(results), BorderLayout.CENTER); find.addActionListener(e -> results.setText(graph.shortestPath((String) from.getSelectedItem(), (String) to.getSelectedItem()))); panel.add(body, BorderLayout.CENTER); return panel;
    }

    private JPanel collectionPanel(String title, int kind) { JPanel panel = base("Directory", title, "Curated sample information for planning your stay."); JPanel body = new JPanel(new GridLayout(0, 2, 12, 12)); body.setBackground(PAPER); for (Destination d : destinations) { String[] values = kind == 0 ? d.hotels : kind == 1 ? d.restaurants : d.transport; JPanel item = new JPanel(new BorderLayout()); item.setBackground(Color.WHITE); item.setBorder(BorderFactory.createEmptyBorder(15, 18, 15, 18)); JLabel name = new JLabel(d.name + "  /  " + d.state); name.setForeground(NAVY); name.setFont(new Font("SansSerif", Font.BOLD, 14)); JTextArea list = outputArea(); list.setText(lines(values)); item.add(name, BorderLayout.NORTH); item.add(list, BorderLayout.CENTER); body.add(item); } panel.add(new JScrollPane(body), BorderLayout.CENTER); return panel; }

    private JComboBox<String> destinationBox() { String[] names = new String[destinations.length]; for (int i = 0; i < destinations.length; i++) names[i] = destinations[i].name; return new JComboBox<>(names); }
    private JTextField input(String text) { JTextField field = new JTextField(); field.setToolTipText(text); field.setFont(new Font("SansSerif", Font.PLAIN, 15)); field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210, 218, 215)), BorderFactory.createEmptyBorder(10, 12, 10, 12))); return field; }
    private JButton actionButton(String text) { JButton button = new JButton(text); button.setBackground(GOLD); button.setForeground(NAVY); button.setFont(new Font("SansSerif", Font.BOLD, 13)); button.setMargin(new Insets(10, 18, 10, 18)); button.setFocusPainted(false); return button; }
    private JTextArea outputArea() { JTextArea area = new JTextArea(); area.setEditable(false); area.setLineWrap(true); area.setWrapStyleWord(true); area.setFont(new Font("Monospaced", Font.PLAIN, 14)); area.setForeground(NAVY); area.setBackground(Color.WHITE); area.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18)); return area; }
    private Destination find(String name) { for (Destination d : destinations) if (d.name.equals(name)) return d; return null; }

    public static void main(String[] args) { try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) { } javax.swing.SwingUtilities.invokeLater(() -> new TravelGuideApp().setVisible(true)); }
}
