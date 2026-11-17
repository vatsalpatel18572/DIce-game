package dicegame;

import java.util.Random;

// One six-sided die that remembers the face it is showing
public class Die {

    private static final int SIDES = 6;

    // one Random object shared by every die
    private static final Random RANDOM = new Random();

    private int faceValue;

    // a new die starts showing 1
    public Die() {
        faceValue = 1;
    }

    // rolls the die and returns the new face, from 1 to 6
    public int roll() {
        // nextInt(6) gives 0 to 5, so we add 1 to get 1 to 6
        faceValue = RANDOM.nextInt(SIDES) + 1;
        return faceValue;
    }

    // returns the face the die is showing now
    public int getFaceValue() {
        return faceValue;
    }
}