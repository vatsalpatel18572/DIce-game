package dicegame;

// One player: their name, their row in the score table and their score
public class Player {

    // the score table has columns 2 to 12
    public static final int FIRST_COLUMN = 2;
    public static final int LAST_COLUMN = 12;
    private static final int NUMBER_OF_COLUMNS = LAST_COLUMN - FIRST_COLUMN + 1;

    private final String name;

    // entries[0] holds column 2, entries[1] holds column 3, and so on
    // a value of 0 means the player has not used that column yet
    private final int[] entries;

    // true when this player's entry in that column was beaten
    private final boolean[] crossedOut;

    private int score;

    // a new player starts with an empty row and a score of 0
    public Player(String name) {
        this.name = name;
        entries = new int[NUMBER_OF_COLUMNS];
        crossedOut = new boolean[NUMBER_OF_COLUMNS];
        score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    // adds the value of a won column to the player's score
    public void addPoints(int points) {
        score = score + points;
    }

    // turns a column number (2 to 12) into an array index (0 to 10)
    private int indexOf(int column) {
        return column - FIRST_COLUMN;
    }

    // true if the player has already put a total in this column
    public boolean hasUsed(int column) {
        return entries[indexOf(column)] != 0;
    }

    // puts a dice total into one of this player's columns
    public void placeTotal(int column, int total) {
        entries[indexOf(column)] = total;
    }

    // returns the total in a column, or 0 if the column is empty
    public int getEntry(int column) {
        return entries[indexOf(column)];
    }

    // marks this player's entry in a column as beaten
    public void crossOut(int column) {
        crossedOut[indexOf(column)] = true;
    }

    public boolean isCrossedOut(int column) {
        return crossedOut[indexOf(column)];
    }
}
 