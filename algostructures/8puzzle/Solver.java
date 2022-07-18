import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Selection;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {

    private SearchNode solution;
    private final class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode prev;
        private final int movesMade;
        private final int manh;


        public SearchNode(Board initial, SearchNode prev, int movesMade ) {
            this.board = initial;
            this.prev = prev;
            this.movesMade = movesMade;
            manh = this.board.manhattan();
        }

        public int compareTo(SearchNode that) {
            return Integer.compare(this.manh + this.movesMade, that.manh + that.movesMade);
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null) { throw new IllegalArgumentException(); }
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> pq_twin = new MinPQ<SearchNode>();
        SearchNode first = new SearchNode(initial, null, 0);
        SearchNode first_twin = new SearchNode(initial.twin(), null, 0);
        pq.insert(first);
        pq_twin.insert(first_twin);
        while(true) {
            SearchNode node = pq.delMin();
            SearchNode node_twin = pq_twin.delMin();
            if(node.board.isGoal()) {
                solution = node;
                break;
            }
            if(node_twin.board.isGoal()) {
                solution = null;
                break;
            }

            for (Board b : node.board.neighbors()) {
                if(node.prev == null || !b.equals(node.prev.board))
                    pq.insert(new SearchNode(b, node, node.movesMade + 1));
            }

            for (Board b : node_twin.board.neighbors()) {
                if(node_twin.prev == null || !b.equals(node_twin.prev.board))
                    pq_twin.insert(new SearchNode(b, node_twin, node_twin.movesMade + 1));
            }
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if(solution == null) return -1;
        return solution.movesMade;
    }


    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if(solution == null) {
            return null;
        }
        Stack<Board> boards = new Stack<Board>();
        SearchNode cur = solution;
        while(cur != null) {
            boards.push(cur.board);
            cur = cur.prev;
        }
        return boards;
    }


    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}