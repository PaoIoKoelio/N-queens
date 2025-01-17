import java.util.*;

public class NqueenSolverg {
    private int[] queenPositions;
    private int n;
    private Random random = new Random();

    private int[] rowConflicts;
    private int[] mainDiagonalConflicts;
    private int[] antiDiagonalConflicts;

    public NqueenSolverg(int n) {
        this.n = n;
        queenPositions = new int[n];
        rowConflicts = new int[n];
        mainDiagonalConflicts = new int[2 * n - 1];
        antiDiagonalConflicts = new int[2 * n - 1];

        // Place queens randomly and set up initial conflicts
        for (int col = 0; col < n; col++) {
            int row = random.nextInt(n);
            queenPositions[col] = row;
            addQueen(row, col);
        }
    }

    public void solve(int maxIterations) {
        for (int i = 0; i < maxIterations; i++) {
            int maxQueen = getQueenWithMaximumConflicts();
            if (maxQueen == -1) {
                printQueens();
                return;
            }
            int bestRow = getRowWithMinConflicts(maxQueen);
            moveQueen(maxQueen, bestRow);
        }
        System.out.println(-1);
    }

    private void addQueen(int row, int col) {
        rowConflicts[row]++;
        mainDiagonalConflicts[row - col + n - 1]++;
        antiDiagonalConflicts[row + col]++;
    }

    private void removeQueen(int row, int col) {
        rowConflicts[row]--;
        mainDiagonalConflicts[row - col + n - 1]--;
        antiDiagonalConflicts[row + col]--;
    }

    private void moveQueen(int col, int newRow) {
        int oldRow = queenPositions[col];
        removeQueen(oldRow, col);
        queenPositions[col] = newRow;
        addQueen(newRow, col);
    }

    private int getRowWithMinConflicts(int col) {
        int minConflicts = Integer.MAX_VALUE;
        List<Integer> minConflictRows = new ArrayList<>();

        for (int row = 0; row < n; row++) {
            int conflicts = rowConflicts[row]
                    + mainDiagonalConflicts[row - col + n - 1]
                    + antiDiagonalConflicts[row + col];
            if (conflicts < minConflicts) {
                minConflicts = conflicts;
                minConflictRows.clear();
                minConflictRows.add(row);
            } else if (conflicts == minConflicts) {
                minConflictRows.add(row);
            }
        }
        return minConflictRows.get(random.nextInt(minConflictRows.size()));
    }

    private int getQueenWithMaximumConflicts() {
        int maxConflicts = 0;
        List<Integer> maxConflictQueens = new ArrayList<>();

        for (int col = 0; col < n; col++) {
            int row = queenPositions[col];
            int conflicts = rowConflicts[row]
                    + mainDiagonalConflicts[row - col + n - 1]
                    + antiDiagonalConflicts[row + col] - 3;

            if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                maxConflictQueens.clear();
                maxConflictQueens.add(col);
            } else if (conflicts == maxConflicts) {
                maxConflictQueens.add(col);
            }
        }

        return maxConflicts == 0 ? -1 : maxConflictQueens.get(random.nextInt(maxConflictQueens.size()));
    }

    public void printQueens() {
        System.out.println(Arrays.toString(queenPositions));
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int n=scanner.nextInt();
        NqueenSolverg nQueenSolverg=new NqueenSolverg(n);
        nQueenSolverg.solve(10000);
    }
}

