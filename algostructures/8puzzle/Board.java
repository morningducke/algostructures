import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public final class Board {

    private final int[][] board;
    private int zeroX;
    private int zeroY;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                board[i][j] = tiles[i][j];
                if(board[i][j] == 0) {
                    zeroX = j;
                    zeroY = i;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder(Integer.toString(board.length));
        str.append('\n');
        for (int i = 0; i < board.length; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < board.length; j++) {
                row.append(Integer.toString(board[i][j])).append(' ');
            }
            str.append(row).append('\n');
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int ham = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) continue;
                int corNum = (i * board.length + j + 1);
                if (board[i][j] != corNum) ham++;
            }
        }
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manh = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) continue;
                int corRow = (board[i][j] - 1) / board.length;
                int corCol = (board[i][j] - 1) % board.length;
                manh += Math.abs((i - corRow)) + Math.abs((j - corCol));
            }
        }
        return manh;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if(y == null) return false;
        if(y.getClass() != Board.class) return false;
        Board ref = (Board) y;
        if(this.dimension() != ref.dimension()) return false;
        for(int i = 0; i < this.dimension(); i++) {
            for(int j = 0; j < this.dimension(); j++) {
                if(this.board[i][j] != ref.board[i][j]) return false;
            }
        }
        return true;

    }

    private int[][] swap(int[][] mat, int i1, int j1, int i2, int j2) {
        int buf = mat[i1][j1];
        mat[i1][j1] = mat[i2][j2];
        mat[i2][j2] = buf;
        return mat;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        Board copy;
        if(zeroX > 0) {
            copy = new Board(swap(board, zeroY, zeroX, zeroY, zeroX-1));
            neighbors.push(copy);
            swap(board, zeroY, zeroX, zeroY, zeroX-1); //reverse the swap
        }
        if(zeroY > 0) {
            copy = new Board(swap(board, zeroY, zeroX, zeroY-1, zeroX));
            neighbors.push(copy);
            swap(board, zeroY, zeroX, zeroY-1, zeroX); //reverse the swap
        }
        if(zeroX < board.length-1) {
            copy = new Board(swap(board, zeroY, zeroX, zeroY, zeroX+1));
            neighbors.push(copy);
            swap(board, zeroY, zeroX, zeroY, zeroX+1); //reverse the swap
        }
        if(zeroY < board.length-1) {
            copy = new Board(swap(board, zeroY, zeroX, zeroY+1, zeroX));
            neighbors.push(copy);
            swap(board, zeroY, zeroX, zeroY+1, zeroX); //reverse the swap
        }

        return neighbors;
    }





    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int i1 = 0, i2 = 0, j1 = 0, j2 = 0;
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board.length-1; j++) {
                if(board[i][j] != 0 && board[i][j+1] != 0) {
                    i1 = i;
                    i2 = i;
                    j1 = j;
                    j2 = j+1;
                }
            }
        }
        Board copy = new Board(swap(board, i1, j1, i2, j2));
        swap(board, i1, j1, i2, j2); //reverse the swap
        return copy;

    }


    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial.toString());
        StdOut.println("Hamming dist: " + Integer.toString(initial.hamming()));
        StdOut.println("Manhattan dist: " + Integer.toString(initial.manhattan()));
        StdOut.println("Solved: " + Boolean.toString(initial.isGoal()));


        // for(Board b : initial.neighbors()) {
        //     StdOut.println(b.toString());
        //     StdOut.println("Hamming dist: " + Integer.toString(b.hamming()));
        //     StdOut.println("Manhattan dist: " + Integer.toString(b.manhattan()));
        //     StdOut.println("Solved: " + Boolean.toString(b.isGoal()));
        // }



    }

}