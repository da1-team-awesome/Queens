/**
 * Solver
 * A class to solve the n-queen problem recursively.
 *
 * @author Gustav Burchardt and A. Malthe Henriksen
 * @version 31-10-2020
 */

import java.util.Arrays;

public class Solver {

    private int noOfQueens;
    private int[] queens;
    private int noOfSolutions;

    /**
     * Finds all solutions for an n-queen problem and prints the result to the console.
     * @param noOfQueens Number of queens
     */
    public void findAllSolutions(int noOfQueens) {
        long startTime = System.currentTimeMillis();

        this.noOfQueens = noOfQueens;
        queens = new int[noOfQueens];
        Arrays.fill(queens, -1);
        noOfSolutions = 0;

        System.out.println("****\n Solutions for " + noOfQueens + " queens:\n");

        positionQueen(0);

        System.out.println("\n A total of " + noOfSolutions + " solutions were found.\n****");

        long endTime = System.currentTimeMillis();

        System.out.println("were found in " + (endTime - startTime) + " ms");
    }

    /**
     * Positions one queen in one row and calls itself recursively. Prints the result when completing each solution.
     * @param row The current row
     */
    private void positionQueen(int row) {
        if (row == noOfQueens) {
            noOfSolutions++;
            printSolution();
            return;
        }

        int[] cachedQueens = queens.clone();

        for (int i = 0; i < noOfQueens; i++) {
            queens = cachedQueens.clone();

            if (legal(row, i)) {
                queens[row] = i;
                positionQueen(row + 1);
            }
        }
    }

    /**
     * Checks if a row-col position is legal, considering the already placed queens.
     * @param row Check row
     * @param col Check column
     * @return Boolean indicating if the position is legal
     */
    private boolean legal(int row, int col) {
        for (int i = 0; i < noOfQueens; i++) {
            if (queens[i] == col) {
                return false;
            }
        }

        int startRow = row - Math.min(row, col);
        int startCol = col - Math.min(row, col);
        for (int i = 0; startRow + i < noOfQueens && startCol + i < noOfQueens; i++) {
            if (!(startCol + i == col && startRow + i == row)) {
                if (queens[startRow + i] == startCol + i) {
                    return false;
                }
            }
        }

        startRow = row - Math.min(row, noOfQueens - 1 - col);
        startCol = col + Math.min(row, noOfQueens - 1 - col);
        for (int i = 0; startRow + i < noOfQueens && startCol - i >= 0; i++) {
            if (!(startCol - i == col && startRow + i == row)) {
                if (queens[startRow + i] == startCol - i) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Prints the solution to the console in proper chess notation
     */
    private void printSolution() {
        System.out.print(" ");
        for (int i = 0; i < queens.length; i++) {
            System.out.print(convert(i, queens[i]) + " ");
        }
        System.out.print("\n");
    }

    /**
     * Converts integer positions to chess notation
     * @param row Row index
     * @param col Column index
     * @return String containing the corresponding chess notation.
     */
    private String convert(int row, int col) {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q"};

        return letters[col] + (row + 1);
    }
}