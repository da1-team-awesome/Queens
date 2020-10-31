/**
 * Test class for testing the Solver class
 *
 * @author Gustav Burchardt and A. Malthe Henriksen
 * @version 31-10-2020
 */

public class TestDriver {

    public static void testSolver() {
        Solver solver = new Solver(); // Solver instance

        // Print solutions to 1-queen, 2-queen and 6-queen problem.
        solver.setShowSolutions(true);
        solver.findAllSolutions(1);
        solver.findAllSolutions(2);
        solver.findAllSolutions(6);

        // Find and display the number of '1-to-12'-queen problems.
        solver.setShowSolutions(false); // Don't display each solution.
        solver.findNoOfSolutions(1,12);
    }

    public static void main(String[] input) {
        testSolver();
    }

}