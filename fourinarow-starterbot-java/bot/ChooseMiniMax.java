package bot;

import field.Field;

public class ChooseMiniMax implements ChooseMoveStrategy {

    // Should change these to Bytes for saving memory
    private static final byte WIDTH = 7;
    private static final byte HEIGHT = 6;
    public static final byte MAX_MOVES = 42;


    boolean canPlay(int col) {
        return false;
    }

    @Override
    public int chooseMove() {
        return 0;
    }

    /**
     *  Recursively solve connect for using the negMax algorithm
     * @return  0 - for draw game
     *    - Positive score for if you can win whatever your opponent is playing. The score is
     *      number of moves before the end you can win - the faster the win, the better the score
     *    - Neg score if your opponent can force you to lose
     *
     */
    public int negMax(Field field, int pos, String player, BotState state) {
        // Check for a draw game
        if (field.numMoves == MAX_MOVES) {
            return 0;
        }

        // if there is a terminal node -
        if (isTerminalNode(state)) {
            //return (WIDTH * HEIGHT+1 - field.numMoves)/2;
        }

        // Check if current player can win the next move
        for (int x=0; x<this.WIDTH; x++) {
            if (field.canPlay(x) && field.isWinningMove(x, player)) {
                return (WIDTH * HEIGHT+1 - field.numMoves)/2;
            }
        }

        int best_score = -WIDTH*HEIGHT; // Init the best score with lowest bound

        for (int x=0; x < WIDTH; x++) {
            if (field.canPlay(x)) {
                Field new_field = new Field();
                new_field.deepCopy(field);
                new_field.play(x, player);
            }
        }
    }

    /** Checks if based on time, if we should cut the thing short
     *
     */
    public boolean isTerminalNode(BotState state) {
        if (state.getTimebank() < 1) {
            return true;
        }

        return false;
    }

    public boolean cutOff(Field field) {

    }

}
