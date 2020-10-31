import java.text.DecimalFormat;
import java.util.Arrays;
/**
 * Solver
 * A class to solve the n-queen problem recursively.
 *
 * @author Gustav Burchardt and A. Malthe Henriksen
 * @version 31-10-2020
 */
public class Solver {

    private int noOfQueens, noOfSolutions;
    private int[] queens;
    private boolean showSolutions;

    /**
     * Finds all solutions for an n-queen problem and prints the result to the console.
     * @param noOfQueens Number of queens
     */
    public void findAllSolutions(int noOfQueens) {
        // Set field variables.
        this.noOfQueens     = noOfQueens;
        this.noOfSolutions  = 0;
        // Create and fill queens array.
        queens = new int[noOfQueens];
        Arrays.fill(queens, -1);

        // Ready timers.
        long startTime = 0, endTime;

        // Only print and measure the time, if we are showing solutions
        if (showSolutions) {
            System.out.println("\n*******************************************");
            System.out.println("Solutions for " + noOfQueens + " queens:\n");
            startTime = System.currentTimeMillis(); // Start timer.
        }

        // Place queens recursively.
        positionQueen(0);

        // Only print and measure the time, if we are showing solutions
        if (showSolutions) {
            endTime = System.currentTimeMillis(); // Stop timer.
            System.out.println("A total of " + noOfSolutions + " solutions were found.");
            System.out.println("were found in " + (endTime - startTime) + " ms");
            System.out.println("*******************************************");
        }
    }

    /**
     * Finds and prints to terminal the number of solutions to a range of n-queen problems, along with their runtimes.
     * @param min The minimum number of queens.
     * @param max The maximum number of queens.
     */
    public void findNoOfSolutions(int min, int max) {
        // Setup print formatting by adding indentations for strings and set 2 decimals for doubles.
        String format = "%-10s%-10s%-10s%-10s%n";
        DecimalFormat df = new DecimalFormat("#.##");
        // Ready timers.
        long startTime, endTime;

        // Print spacer and table header
        System.out.println("\n*******************************************");
        System.out.printf(format, "Queens", "Solutions","Time(ms)","Solutions/ms");
        // Loop through each n-queen problem.
        for (int i = min; i <= max; i++) {
            // Find and measure the time it takes to calculate all solutions to the i-queen problem.
            //      We've decided to use nanoTime instead of millis, because many solutions were found in 0ms.
            //      This does not mean that the timer will be nanosecond accurate, but it will allow us to place decimals,
            //      that are accurate depending on the machine running it.
            startTime = System.nanoTime();
            findAllSolutions(i);
            endTime = System.nanoTime();

            // Calculate the runtime and convert to millisecond. 1ms = 1,000,000ns
            double duration = endTime-startTime;
            duration/=1000000;
            // Only print the number of solutions, if there are any solutions to display.
            if (noOfSolutions > 0) {
                // Calculate average number of solutions
                String avgSolutionTime = duration == 0 ? "> 1" : Integer.toString((int)(noOfSolutions / duration)); // Avoid 0-division exception.
                // Print table row.
                String timeStr = df.format(duration).equals("0") ? "< 0.01" : df.format(duration); // If faster than 0.01ms, print '< 0.01'
                System.out.printf(format, i, noOfSolutions, timeStr, avgSolutionTime);
            }
        }
        System.out.println("*******************************************");
    }

    /**
     * Positions one queen in one row and calls itself recursively. Prints the result when completing each solution.
     * @param row The current row
     */
    private void positionQueen(int row) {
        // Check if all rows are filled.
        if (row == noOfQueens) {
            noOfSolutions++;
            if (showSolutions) { printSolution(); }
            return;
        }

        // Cache queens
        int[] cachedQueens = queens.clone();

        // Loop through each possible queen.
        for (int i = 0; i < noOfQueens; i++) {
            queens = cachedQueens.clone();
            // Check if queen can be legally placed.
            if (legal(row, i)) {
                queens[row] = i;
                positionQueen(row + 1); // recur.
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

    public void setShowSolutions(boolean showsolutions) {
        this.showSolutions = showsolutions;
    }
}