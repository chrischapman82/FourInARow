/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package field;

import java.util.Arrays;

/**
 * Field class
 * 
 * Field class that contains the field status data and various helper functions.
 * 
 * @author Jim van Eeden <jim@riddles.io>, Joost de Meij <joost@riddles.io>
 */

public class Field {
    private static final String EMPTY_FIELD = ".";

    private int myId;           // Not used again
    private String[][] field;   // Should change to char not String
    public byte numMoves;       // Number of moves already done, up to a max of 42 moves

    public static final int WIDTH = 7;  // The width of the field
    public static final int HEIGHT = 6; // The Height of the field
    public static final int MAX_MOVES = 42; // I think so
    private static final int WINNING_REQ = 4;
    private static final int MAX_VERTICAL = 4;


    // copy in another field
    public void deepCopy(Field another) {
        this.field = Arrays.copyOf(another.field, MAX_MOVES);
        this.numMoves = another.numMoves;
    }


    /**
     * Initializes and clears field
     *
     * @throws Exception exception
     */
    public void initField() throws Exception {
        try {
            this.field = new String[this.WIDTH][this.HEIGHT];
        } catch (Exception e) {
            throw new Exception("Error: trying to initialize field while field "
                    + "settings have not been parsed yet.");
        }
        clearField();
    }

    /**
     * Clear the field
     */
    public void clearField() {
        for (int y = 0; y < this.HEIGHT; y++) {
            for (int x = 0; x < this.WIDTH; x++) {
                this.field[x][y] = ".";
            }
        }
    }



    /**
     * Parse field from comma separated String
     *
     * @param s input from engine
     */
    public void parseFromString(String s) {
        clearField();

        String[] split = s.split(",");
        int x = 0;
        int y = 0;

        for (String value : split) {
            this.field[x][y] = value;

            if (++x == this.WIDTH) {
                x = 0;
                y++;
            }
        }
    }
    // Plays a piece  at the given column
    // Returns row that we played
    // Returns -1 if play
    // Should check can play first!
    public int play(int col, String player) {
        int row = 0;

        for (row = 0; row < HEIGHT; row++) {
            if (this.field[row][col].equals(EMPTY_FIELD)) {
                this.field[row][col] = player;
                return row;
            }
        }
        return -1;
    }

    /** Gets the top row of the given column (col)
     *
     * @param col The given column
     * @return row  The number of the top row OR -1 if there are no rows available in the given col
     */
    public int getTopRow(int col) {
        // Gets the top row w/out a
        for (int row = 0; row < this.HEIGHT; row++) {
            if (!field[col][row].equals(EMPTY_FIELD)) {
                return row;
            }
        }
        return -1;
    }

    /** Checks if a player can play a piece in a given column
     * A cheaper way to check than GetTopRow method.
     * @param col
     * @return
     */
    public boolean canPlay(int col) {
        return field[col][HEIGHT].equals(EMPTY_FIELD);
    }

    /** Checks if the move at a given column is a winning move.
     * @param col
     * @param player    curr player
     * @return
     */
    public boolean isWinningMove(int col, String player) {
        int row = getTopRow(col);

        // checks if there is any way of a win
        return (isVerticalWin(col, row, player) ||
                isHorizontalWin(col, row, player) ||
                isDiagonalWin(col, row, player));
    }

    /** Checks if the given col / row is on the board
     * @param col   The col of the given checked thingo
     * @param row   The row of the given checked thingo
     * @return  no shit
     */
    public boolean isOnBoard(int col, int row) {
        return colIsOnBoard(col) && rowIsOnBoard(row);
    }

    // For cols
    public boolean colIsOnBoard(int col) {
        return (col > 0 && col < WIDTH);
    }

    public boolean rowIsOnBoard(int row) {
        return (row > 0 && row < HEIGHT);
    }

    /**
     * Checks if given a row and column, that the player will win by 4 in a row vertically
     * @param col   The column of the current piece
     * @param row   The row of the current piece
     * @param player    The char that represents the player
     * @return  True if the move will lead to a vertical win
     */
    public boolean isVerticalWin(int col, int row, String player) {

        // Could maybe
        if (row < MAX_VERTICAL - 1 || !isOnBoard(col,row)) {
            return false;
        }

        for (int y=0; y<WINNING_REQ; y++) {
            if (!rowIsOnBoard(row) && !this.field[col][row-y].equals(player)) {
                return false;
            }
        }

        System.out.println("Is a vertical win @ col: " + col);
        return true;
    }

    // pretty hecking clear
    // Checks if the col and row is on the board
    public boolean isHorizontalWin(int col, int row, String player) {

        // Could maybe remove!
        if (!isOnBoard(col, row)) {
            return false;
        }
        for (int x=0; x<WINNING_REQ; x++) {
            if (!rowIsOnBoard(row) && !this.field[col-x][row].equals(player)) {
                return false;
            }
        }

        System.out.println("Is a horizontal win @ col: " + col);
        return true;
    }

    public boolean isDiagonalWin(int col, int row, String player) {

        // left down and right up diag
        int counter = 1;
        for (int i=1; i<4; i++) {
            if (this.isOnBoard(col-i, row-i) && !field[col-i][row-i].equals(player)) {
                break;
            }
            counter++;
        }

        for (int i=1; i<4; i++) {
            if (this.isOnBoard(col+i, row+i) && !field[col+i][row+i].equals(player)) {
                break;
            }
            counter++;
        }
        if (counter >= 4) {
            System.out.println("Is a Diagonal win @ col " + col);
            return true;
        }

        // right down and left up diagonal
        counter = 1;
        for (int i=1; i<4; i++) {
            if (this.isOnBoard(col+i, row-i) && !field[col+i][row-i].equals(player)) {
                break;
            }
            counter++;
        }

        for (int i=1; i<4; i++) {
            if (this.isOnBoard(col-i, row+i) && !field[col-i][row+i].equals(player)) {
                break;
            }
            counter++;
        }

        if (counter >= 4) {
            System.out.println("Is a Diagonal win @ col " + col);
            return true;
        }
        return false;
    }

    public void setMyId(int id) {
        this.myId = id;
    }

}
