import java.util.Arrays;

public class Solver {

    private int noOfQueens;
    private int[] queens;
    private int noOfSolutions;

    public void findAllSolutions(int noOfQueens) {
        this.noOfQueens = noOfQueens;
        queens = new int[noOfQueens];
        Arrays.fill(queens, -1);
        noOfSolutions = 0;

        System.out.println("****\n Solutions for " + noOfQueens + " queens:\n");

        positionQueen(0);

        System.out.println("\n A total of " + noOfSolutions + " solutions were found.\n****");
    }

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

    private void printSolution() {
        System.out.print(" ");
        for (int i = 0; i < queens.length; i++) {
            System.out.print(convert(i, queens[i]) + " ");
        }
        System.out.print("\n");
    }

    private String convert(int row, int col) {
        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"};

        return letters[col] + (row + 1);
    }
}