import java.util.*;

public class NQueensSolver {

    private int[] queenPositions;
    private int n;
    private Random random = new Random();
    private int[] rowConflicts;
    private int[] firstDiagonalConflicts;
    private int[] secondDiagonalConflicts;

    public NQueensSolver(int n) {
        this.n = n;
        queenPositions = new int[n];
        rowConflicts = new int[n];
        firstDiagonalConflicts = new int[2 * n - 1];
        secondDiagonalConflicts = new int[2 * n - 1];
        initializeQueens();
    }

    private void addQueen(int row, int col) {
        rowConflicts[row]++;
        firstDiagonalConflicts[row - col + n - 1]++;
        secondDiagonalConflicts[row + col]++;

    }

    private void moveQueen(int col, int newRow) {
        removeQueen(queenPositions[col], col);
        queenPositions[col] = newRow;
        addQueen(newRow, col);
    }


    private void removeQueen(int row, int col) {
        rowConflicts[row]--;
        firstDiagonalConflicts[row - col + n - 1]--;
        secondDiagonalConflicts[row + col]--;
    }

    private boolean solve(int maxIterations) {
        if (n == 2 || n == 3) {
            System.out.println(-1);
            return false;
        }
        for (int i = 0; i < maxIterations; i++) {
            int maxQueen = getQueenWithMaximumConflicts();
            if (maxQueen == -1) {
                return true;
            }
            moveQueen(maxQueen, getRowWithMinConflicts(maxQueen));
        }
        restartConflicts();
        initializeQueens();
        return solve(1000 * n);
    }

    private void restartConflicts(){
        Arrays.fill(rowConflicts, 0);
        Arrays.fill(firstDiagonalConflicts, 0);
        Arrays.fill(secondDiagonalConflicts, 0);
    }

    public void initializeQueens(){
        for (int i = 0; i < n; i++) {
            int row = random.nextInt(n);
            queenPositions[i] = row;
            addQueen(row, i);
        }
    }


    private int getQueenWithMaximumConflicts() {
        int maxConflicts = -1;
        List<Integer> maxConflictQueens = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int conflicts = rowConflicts[queenPositions[i]]
                    + firstDiagonalConflicts[queenPositions[i] - i + n - 1]
                    + secondDiagonalConflicts[queenPositions[i] + i] - 3;

            if (maxConflicts < conflicts) {
                maxConflicts = conflicts;
                maxConflictQueens.clear();
                maxConflictQueens.add(i);

            } else if (maxConflicts == conflicts) {
                maxConflictQueens.add(i);
            }
        }
        if (maxConflicts == 0) {
            return -1;
        }
        return maxConflictQueens.get(random.nextInt(maxConflictQueens.size()));
    }

    private int getRowWithMinConflicts(int queenCol) {
        int minConflicts = Integer.MAX_VALUE;
        List<Integer> minConflictRows = new ArrayList<>();

        for (int row = 0; row < n; row++) {
            int conflicts = rowConflicts[row]
                    + firstDiagonalConflicts[row - queenCol + n - 1]
                    + secondDiagonalConflicts[row + queenCol];
            if (minConflicts > conflicts) {
                minConflicts = conflicts;
                minConflictRows.clear();
                minConflictRows.add(row);
            } else if (minConflicts == conflicts) {
                minConflictRows.add(row);
            }
        }
        return minConflictRows.get(random.nextInt(minConflictRows.size()));
    }

    public void printQueens() {
        System.out.println(Arrays.toString(queenPositions));
    }

    public void printBoard() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (queenPositions[j] == i) {
                    System.out.print("*");
                } else {
                    System.out.print("_");
                }
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        NQueensSolver nQueensSolver = new NQueensSolver(n);
        double start = System.nanoTime();
        boolean solved = nQueensSolver.solve(1000 * n);
        double end = System.nanoTime();
        double time = (end - start) / 1000000000;
        if (solved) {
            if (n > 100) {
                System.out.println(Math.round((time * 100.0)) / 100.0);
            } else {
                nQueensSolver.printQueens();
                nQueensSolver.printBoard();
            }
        }
    }
}
