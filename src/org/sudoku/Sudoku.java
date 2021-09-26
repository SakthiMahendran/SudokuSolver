package org.sudoku;

import java.util.LinkedList;
import java.util.List;

class SudokuSolver {
    private LinkedList<SudokuBoard> stepsForSolving;
    private SudokuBoard board;

    /**
     * @param board Takes a SudokuBoard object to solve.
     */
    public SudokuSolver(SudokuBoard board) {
        this.board = board;
        stepsForSolving = new LinkedList<>();
    }

    /**
     * @return if the given board was solved successfully returns SudokuBoard object
     *         which has the solution for the Given SudokuBoard object.else returns
     *         null.
     */
    public SudokuBoard getSolution() {
        if (solve(board)) {
            return stepsForSolving.getFirst();
        }
        return null;
    }

    /**
     * @return if an attempt to solve the given board was made returns a LinkedList
     *         of type SudokuBoard Which contains all the steps made to solve the
     *         given SudokuBoard.else returns an empty LinkedList of type
     *         SudokuBoard.
     */
    public List<SudokuBoard> getSteps() {
        return stepsForSolving;
    }

    /**
     * @param board Takes an SudokuBoard object to be solved.
     * @return if the board was solved successfully returns true. else returns
     *         false.
     */
    private boolean solve(SudokuBoard board) {
        stepsForSolving.addFirst(board);

        var emptyPosition = board.getEmpty();
        if (emptyPosition == null) {
            return true;
        }

        for (byte i = 0; i <= 9; i++) {
            if (board.isValid(i, emptyPosition)) {
                board.putElementAt(i, emptyPosition);
                if (solve(board))
                    return true;
            }
            board.putElementAt((byte) 0, emptyPosition);
        }
        return false;
    }
}

class SudokuBoard {

    private byte[][] sudokuBoard = new byte[9][9];

    /**
     * 
     * @param sudokuBoard Takes a 2d byte array of size 9x9.
     * @throws InValidBoard Throws if the given 2d byte array doesn't have 9x9
     *                      elements.
     */
    public SudokuBoard(byte[][] sudokuBoard) throws InValidBoard {
        if (isValidBoard(sudokuBoard))
            this.sudokuBoard = sudokuBoard;
        else
            throw new InValidBoard();
    }

    /**
     * @return returns a 9x9 2d byte array representing the SudokuBoard.
     */
    public byte[][] getBoard() {
        return sudokuBoard;
    }

    /**
     * @return returns a byte array representing the first empty X and Y position in
     *         the sudokuBoard or returns null if there is no empty position.
     */
    public byte[] getEmpty() {
        for (byte i = 0; i < sudokuBoard.length; i++) {
            for (byte j = 0; j < sudokuBoard[i].length; j++) {
                if (sudokuBoard[i][j] == 0) {
                    return new byte[] { i, j };
                }
            }
        }
        return null;
    }

    /**
     * Puts the given byte element at the given position which is given as a byte
     * array in the form of X and Y.
     * 
     * @param element         Takes a byte element.
     * @param elementPosition Takes a byte array which represent the position in X
     *                        and Y form in which the element should be puted.
     */
    public void putElementAt(byte element, byte[] elementPosition) {
        sudokuBoard[elementPosition[0]][elementPosition[1]] = element;
    }

    /**
     * Prints the SudokuBoard in System.out (PrintStream).
     */
    public void printBoard() {
        var console = System.out;

        for (byte i = 0; i <= 8; i++) {
            for (byte j = 0; j <= 8; j++) {
                if (j % 3 == 0 && j != 0) {
                    console.print("|");
                }
                console.print(" " + sudokuBoard[i][j] + " ");
            }
            if ((i + 1) % 3 == 0 && i != 8) {
                console.print("\n");
                console.println("-----------------------------");
            } else {
                console.print("\n");
            }
        }
    }

    /**
     * 
     * @param element Takes a byte element.
     * @param elementPosition Takes a byte array which represent the position in X
     *                        and Y form in which the element should be puted.
     * @return returns true if the element can be puted in the given position. else
     *         returns false.
     */
    public boolean isValid(byte element, byte[] elementPosition) {
        // Cheack In Row
        for (int i : sudokuBoard[elementPosition[0]])
            if (i == element)
                return false;

        // Cheack In Column
        for (int i = 0; i < sudokuBoard.length; i++)
            if (sudokuBoard[i][elementPosition[1]] == element)
                return false;

        // Cheack In SubDevision
        int subDivisionX = elementPosition[0] / 3;
        int subDivisionY = elementPosition[1] / 3;
        for (int i = subDivisionX * 3; i < subDivisionX * 3 + 3; i++) {
            for (int j = subDivisionY * 3; j < subDivisionY * 3 + 3; j++) {
                if (sudokuBoard[i][j] == element)
                    return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param sudokuBoard Takes 2d byte array to cheack whether it is ValidBoard.
     * @return returns if valid. else returns false.
     */
    public static boolean isValidBoard(byte[][] sudokuBoard) {
        if (sudokuBoard.length == 9)
            for (byte i = 0; i < 9; i++) {
                if (sudokuBoard[i].length != 9)
                    return false;
            }
        else
            return false;

        return true;
    }
}

class InValidBoard extends Exception {
    /**
     * @param errorMessage Takes an errorMessage of type String which will be
     *                     displayed in "System.err" PrintStream.
     */
    public InValidBoard(String errorMessage) {
        System.err.println(errorMessage);
    }

    /**
     * displayes an errormessage in "System.err" PrintStream.
     */
    public InValidBoard() {
        System.err.println("Board must be a 9x9 2D byte array");
    }
}
