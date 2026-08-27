public final class Algorithms {
    private Algorithms() { }

    public static boolean kmpContains(String text, String pattern) {
        if (pattern.length() == 0) return true;
        String source = text.toLowerCase();
        String target = pattern.toLowerCase();
        int[] lps = buildLps(target);
        int textIndex = 0;
        int patternIndex = 0;
        while (textIndex < source.length()) {
            if (source.charAt(textIndex) == target.charAt(patternIndex)) {
                textIndex++;
                patternIndex++;
                if (patternIndex == target.length()) return true;
            } else if (patternIndex > 0) {
                patternIndex = lps[patternIndex - 1];
            } else {
                textIndex++;
            }
        }
        return false;
    }

    private static int[] buildLps(String pattern) {
        int[] lps = new int[pattern.length()];
        int length = 0;
        for (int i = 1; i < pattern.length();) {
            if (pattern.charAt(i) == pattern.charAt(length)) lps[i++] = ++length;
            else if (length > 0) length = lps[length - 1];
            else lps[i++] = 0;
        }
        return lps;
    }

    public static int editDistance(String first, String second) {
        first = first.toLowerCase();
        second = second.toLowerCase();
        int[][] distance = new int[first.length() + 1][second.length() + 1];
        for (int i = 0; i <= first.length(); i++) distance[i][0] = i;
        for (int j = 0; j <= second.length(); j++) distance[0][j] = j;
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                int cost = first.charAt(i - 1) == second.charAt(j - 1) ? 0 : 1;
                int insert = distance[i][j - 1] + 1;
                int delete = distance[i - 1][j] + 1;
                int replace = distance[i - 1][j - 1] + cost;
                distance[i][j] = Math.min(Math.min(insert, delete), replace);
            }
        }
        return distance[first.length()][second.length()];
    }

    public static double similarity(String first, String second) {
        int longest = Math.max(first.length(), second.length());
        if (longest == 0) return 1.0;
        return 1.0 - (double) editDistance(first, second) / longest;
    }

    public static void sortByName(Destination[] destinations) {
        for (int i = 1; i < destinations.length; i++) {
            Destination current = destinations[i];
            int j = i - 1;
            while (j >= 0 && destinations[j].name.compareToIgnoreCase(current.name) > 0) {
                destinations[j + 1] = destinations[j--];
            }
            destinations[j + 1] = current;
        }
    }
}
