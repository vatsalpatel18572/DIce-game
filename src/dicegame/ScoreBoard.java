package dicegame;

// Shows the score table, decides who wins each column and announces the winner
public class ScoreBoard {

    private final Player[] players;

    // the result of each column, filled in when the column is complete
    private final String[] columnResults;

    // starts with every column marked as not completed
    public ScoreBoard(Player[] players) {
        this.players = players;
        columnResults = new String[Player.LAST_COLUMN - Player.FIRST_COLUMN + 1];
        for (int i = 0; i < columnResults.length; i++) {
            columnResults[i] = "Not completed";
        }
    }

    // true when every player has put a total in this column
    public boolean isColumnComplete(int column) {
        for (Player player : players) {
            if (!player.hasUsed(column)) {
                return false;
            }
        }
        return true;
    }

    // finds the highest total in a full column and gives its points to the winner
    public void scoreColumn(int column) {
        // first pass: find the highest total in this column
        int highest = 0;
        for (Player player : players) {
            if (player.getEntry(column) > highest) {
                highest = player.getEntry(column);
            }
        }

        // second pass: count who has the highest, and cross out everyone else
        int playersWithHighest = 0;
        Player winner = null;
        for (Player player : players) {
            if (player.getEntry(column) == highest) {
                playersWithHighest++;
                winner = player;
            } else {
                player.crossOut(column);
            }
        }

        // only one player with the highest total wins; two or more is a tie
        String result;
        if (playersWithHighest == 1) {
            winner.addPoints(column);
            result = winner.getName() + " won with " + highest
                    + " (+" + column + " points)";
        } else {
            result = "Tied at " + highest + ", no points scored";
        }
        columnResults[column - Player.FIRST_COLUMN] = result;
        System.out.println(">> Column " + column + " is complete: " + result);
    }

    // prints the score table with every player's row and score
    public void printTable() {
        System.out.println();
        System.out.printf("%-10s", "");
        for (int column = Player.FIRST_COLUMN; column <= Player.LAST_COLUMN; column++) {
            System.out.printf("%4d", column);
        }
        System.out.printf("%8s%n", "Score");

        for (Player player : players) {
            System.out.printf("%-10s", player.getName());
            for (int column = Player.FIRST_COLUMN; column <= Player.LAST_COLUMN; column++) {
                System.out.printf("%4s", cellText(player, column));
            }
            System.out.printf("%8d%n", player.getScore());
        }
        System.out.println();
    }

    // what one cell shows: blank if empty, X if beaten, otherwise the total
    private String cellText(Player player, int column) {
        if (!player.hasUsed(column)) {
            return "";
        }
        if (player.isCrossedOut(column)) {
            return "X";
        }
        return String.valueOf(player.getEntry(column));
    }

    // prints who won each column at the end of the game
    public void printSummary() {
        System.out.println("========== Column summary ==========");
        for (int column = Player.FIRST_COLUMN; column <= Player.LAST_COLUMN; column++) {
            System.out.printf("Column %2d: %s%n", column,
                    columnResults[column - Player.FIRST_COLUMN]);
        }
        System.out.println();
    }

    // announces the player with the highest score, or the players who tied
    public void announceWinner() {
        // find the best score first
        int bestScore = 0;
        for (Player player : players) {
            if (player.getScore() > bestScore) {
                bestScore = player.getScore();
            }
        }

        // then collect the names of everyone who has that score
        String leaders = "";
        int numberOfLeaders = 0;
        for (Player player : players) {
            if (player.getScore() == bestScore) {
                if (numberOfLeaders > 0) {
                    leaders = leaders + " and ";
                }
                leaders = leaders + player.getName();
                numberOfLeaders++;
            }
        }

        if (numberOfLeaders == 1) {
            System.out.println("The winner is " + leaders + " with "
                    + bestScore + " points!");
        } else {
            System.out.println("The game is tied between " + leaders
                    + " with " + bestScore + " points each.");
        }
    }
}
