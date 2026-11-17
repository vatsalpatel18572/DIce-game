package dicegame;

// Runs the dice game for three players sharing the keyboard
public class Dicegame {

    private static final int NUMBER_OF_PLAYERS = 3;
    private static final int NUMBER_OF_ROUNDS = 11;
    private static final int MAX_NAME_LENGTH = 10;

    private final Player[] players;
    private final Die firstDie;
    private final Die secondDie;
    private final ScoreBoard scoreBoard;
    private final InputReader input;

    // shows the rules, asks for three names, then sets up the dice and score board
    public Dicegame() {
        input = new InputReader();
        printWelcome();
        players = new Player[NUMBER_OF_PLAYERS];
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            players[i] = new Player(askForName(i + 1));
        }
        firstDie = new Die();
        secondDie = new Die();
        scoreBoard = new ScoreBoard(players);
    }

    // plays all eleven rounds, then shows the summary and the winner
    public void play() {
        scoreBoard.printTable();
        for (int round = 1; round <= NUMBER_OF_ROUNDS; round++) {
            System.out.println("================ Round " + round
                    + " ================");
            for (Player player : players) {
                takeTurn(player, round);
            }
        }
        System.out.println("=============== GAME OVER ===============");
        scoreBoard.printTable();
        scoreBoard.printSummary();
        scoreBoard.announceWinner();
    }

    // explains the rules before the game starts
    private void printWelcome() {
        System.out.println("========== WELCOME TO THE DICE GAME ==========");
        System.out.println("3 players, 11 rounds. Each turn you roll two dice");
        System.out.println("and put the total in a column from 2 to 12.");
        System.out.println("You can use each column only once.");
        System.out.println("In round 1, every player must pick a different column.");
        System.out.println("When a column is full, the highest total wins");
        System.out.println("that column's value in points. A tie scores nothing.");
        System.out.println();
    }

    // asks for a player's name and makes sure no two players share a name
    private String askForName(int playerNumber) {
        while (true) {
            String name = input.readName("Enter a name for player "
                    + playerNumber + ": ", MAX_NAME_LENGTH);
            if (!isNameTaken(name)) {
                return name;
            }
            System.out.println("That name is already taken. Choose another one.");
        }
    }

    // true if a player who has already been created has this name
    private boolean isNameTaken(String name) {
        for (Player player : players) {
            // players that are not created yet are still null
            if (player != null && player.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // one player's turn: roll two dice, choose a column, update the table
    private void takeTurn(Player player, int round) {
        System.out.println(player.getName() + "'s turn.");
        input.waitForEnter("Press Enter to roll the dice...");
        int first = firstDie.roll();
        int second = secondDie.roll();
        int total = first + second;
        System.out.println(player.getName() + " rolled " + first + " and "
                + second + " = " + total);

        int column = chooseColumn(player, round);
        player.placeTotal(column, total);
        System.out.println(player.getName() + " put " + total
                + " in column " + column + ".");

        // a column is scored as soon as the third player fills it
        if (scoreBoard.isColumnComplete(column)) {
            scoreBoard.scoreColumn(column);
        }
        scoreBoard.printTable();
    }

    // keeps asking until the player picks a column they are allowed to use
    private int chooseColumn(Player player, int round) {
        System.out.println("Your free columns: " + freeColumns(player));
        while (true) {
            int column = input.readNumber("Choose a column (2-12): ",
                    Player.FIRST_COLUMN, Player.LAST_COLUMN);
            if (player.hasUsed(column)) {
                System.out.println("You have already used column " + column
                        + ". Choose another one.");
            } else if (round == 1 && isUsedByAnyone(column)) {
                System.out.println("In round 1 every player needs a different"
                        + " column. Column " + column + " is taken.");
            } else {
                return column;
            }
        }
    }

    // builds a list of the columns this player has not used yet
    private String freeColumns(Player player) {
        String list = "";
        for (int column = Player.FIRST_COLUMN; column <= Player.LAST_COLUMN; column++) {
            if (!player.hasUsed(column)) {
                list = list + column + " ";
            }
        }
        return list;
    }

    // true if any player has already used this column
    private boolean isUsedByAnyone(int column) {
        for (Player player : players) {
            if (player.hasUsed(column)) {
                return true;
            }
        }
        return false;
    }

    // the program starts running from here
    public static void main(String[] args) {
        Dicegame game = new Dicegame();
        game.play();
    }
}
