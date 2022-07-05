import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {

    private boolean mat[][];
    private int length;
    private WeightedQuickUnionUF uf;
    private int top;
    private int bot;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        length = n;
        mat = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n*n + 2); //+2 for virtual and bottom sites
        //top = n*n; bot = n*n+1
        top = n*n;
        bot = n*n + 1;


    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if(row > length || col > length) {
            throw new IllegalArgumentException();
        }

        int r = row-1;
        int c = col-1;

        if(r < 0 || c < 0) {
            throw new IllegalArgumentException();
        }

        if(!mat[r][c]) {
            mat[r][c] = true;
        }
        //check row up
        if(r-1 >= 0) {
            if(mat[r - 1][c]) {
                uf.union(r*length + c, (r-1)*length + c);
            }
       }
        if(r == 0) {
            uf.union(c, top);

        }
        //check row down
        if(r+1 < length) {
            if(mat[r + 1][c]) {
                uf.union(r*length + c, (r+1)*length + c);
            }
        }
        if(r == length - 1) {
            uf.union(r*length + c, bot);
        }
        //check col left
        if(c-1 >= 0) {
            if(mat[r][c-1]) {
                uf.union(r*length + c, r*length + c - 1);
            }
        }

        //check col right
        if(c+1 < length) {
            if(mat[r][c+1]) {
                uf.union(r*length + c, r*length + c + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(mat[row-1][col-1]) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if(!isOpen(row, col)) {
            return false;
        }
        int i = (row-1)*length + col-1;
        if(uf.find(top) == uf.find(i)) {
            return true;
        }
        return  false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                count += mat[i][j] ? 1 : 0;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        if(uf.find(top) == uf.find(bot)) {
            return  true;
        }
        return false;
    }


    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);

        for(int i = 0; i < percolation.length; i++) {
            for(int j = 0; j < percolation.length; j++) {
                percolation.open(i+1,j+1);
                System.out.print(percolation.mat[i][j] ? 1 : 0);
            }
            System.out.println();
        }
        System.out.println(percolation.percolates());



    }
}