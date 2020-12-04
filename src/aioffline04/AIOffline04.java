package aioffline04;

import java.util.*;
import javafx.util.Pair;

class Content {
    int row;
    int column;
    int value;
    
    Content() {}
    Content(int r, int c, int v) {
        row = r;
        column = c;
        value = v;
    }
}

public class AIOffline04 {
    
    public static char[][] grid = new char[4][4];
    
    public static int n = 3;
    
    public static int MAX = 100000;
    public static int MIN = -100000;
    public static char user = '0';
    public static char computer = 'X';

    public static Content minimax(int depth , Boolean maximizingPlayer, int alpha, int beta) {
        
        Vector<Pair<Integer, Integer>> nextMoves = getMoves(grid, n);
        
        int best = (maximizingPlayer) ? MIN : MAX;
        int bestX = -1;
        int bestY = -1;
        
        if(depth == 6 || getMoves(grid, n).isEmpty()) {
            Content c = new Content(bestX, bestY, getValue(grid, n));
            return c;
        }
        
        else {
            for(int i=0; i<nextMoves.size(); i++) {
                Pair<Integer, Integer> move = nextMoves.elementAt(i);
                
                grid[move.getKey()][move.getValue()] = (maximizingPlayer) ? computer : user;
                
                if(maximizingPlayer) {
                    Content c = minimax(depth + 1, false, alpha, beta);
                    int val = c.value;
                    best = Math.max(best, val);
                    
                    if(alpha < best) {
                        alpha = best;
                        bestX = move.getKey();
                        bestY = move.getValue();
                    }
                }
                else {
                    Content c = minimax(depth + 1, true, alpha, beta);
                    int val = c.value;
                    best = Math.min(best, val);
                    
                    if(beta > best) {
                        beta = best;
                        bestX = move.getKey();
                        bestY = move.getValue();
                    }                    
                }
                
                grid[move.getKey()][move.getValue()] = '_';
                
                if (beta <= alpha) break;                
            }
        }
        
        Content c = new Content(bestX, bestY, best);
        return c;
    }
    
    public static int getValue(char[][] grid, int n) {
        int score = 0;
        
        if(n == 3) {
        
            score += evaluateline(0,0,0,1,0,2,grid);
            score += evaluateline(1,0,1,1,1,2,grid);
            score += evaluateline(2,0,2,1,2,2,grid);
            score += evaluateline(0,0,1,0,2,0,grid);
            score += evaluateline(0,1,1,1,2,1,grid);
            score += evaluateline(0,2,1,2,2,2,grid);
            score += evaluateline(0,0,1,1,2,2,grid);
            score += evaluateline(0,2,1,1,2,0,grid);

        }
        else {
            score += evaluateline2(0,0,0,1,0,2,0,3,grid);
            score += evaluateline2(1,0,1,1,1,2,1,3,grid);
            score += evaluateline2(2,0,2,1,2,2,2,3,grid);
            score += evaluateline2(3,0,3,1,3,2,3,3,grid);
            score += evaluateline2(0,0,1,0,2,0,3,0,grid);
            score += evaluateline2(0,1,1,1,2,1,3,1,grid);
            score += evaluateline2(0,2,1,2,2,2,3,2,grid);
            score += evaluateline2(0,3,1,3,2,3,3,3,grid);
            score += evaluateline2(0,0,1,1,2,2,3,3,grid);
            score += evaluateline2(0,3,1,2,2,1,3,0,grid);
        }
        
        return score;
    }
    
    public static int evaluateline(int x1, int y1, int x2, int y2, int x3, int y3, char[][] grid) {
        int score = 0;
        
        if( (grid[x1][y1] == computer && grid[x2][y2] == '_' && grid[x3][y3] == '_') 
                || (grid[x1][y1] == '_' && grid[x2][y2] == computer && grid[x3][y3] == '_')
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == computer) ) {
            score = 1;
        }
        
        if( (grid[x1][y1] == user && grid[x2][y2] == '_' && grid[x3][y3] == '_') 
                || (grid[x1][y1] == '_' && grid[x2][y2] == user && grid[x3][y3] == '_')
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == user) ) {
            score = -1;
        }
        
        if( (grid[x1][y1] == computer && grid[x2][y2] == computer && grid[x3][y3] == '_') 
                || (grid[x1][y1] == computer && grid[x2][y2] == '_' && grid[x3][y3] == computer)
                || (grid[x1][y1] == '_' && grid[x2][y2] == computer && grid[x3][y3] == computer) ) {
            score = 10;
        }
        
        if( (grid[x1][y1] == user && grid[x2][y2] == user && grid[x3][y3] == '_') 
                || (grid[x1][y1] == user && grid[x2][y2] == '_' && grid[x3][y3] == user)
                || (grid[x1][y1] == '_' && grid[x2][y2] == user && grid[x3][y3] == user) ) {
            score = -10;
        }
        
        if( grid[x1][y1] == computer && grid[x2][y2] == computer && grid[x3][y3] == computer ) {
            score = 100;
        }
        
        if( grid[x1][y1] == user && grid[x2][y2] == user && grid[x3][y3] == user ) {
            score = -100;
        }
        
        return score;
    }
    
    public static int evaluateline2(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, char[][] grid) {
        int score = 0;
        
        if( (grid[x1][y1] == computer && grid[x2][y2] == '_' && grid[x3][y3] == '_' && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == computer && grid[x3][y3] == '_'  && grid[x4][y4] == '_' )
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == computer  && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == '_'  && grid[x4][y4] == computer ) ) 
        {
            score = 1;
        }
        
        if( (grid[x1][y1] == user && grid[x2][y2] == '_' && grid[x3][y3] == '_' && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == user && grid[x3][y3] == '_'  && grid[x4][y4] == '_' )
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == user  && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == '_'  && grid[x4][y4] == user ) ) 
        {
            score = -1;
        }
        
        if( (grid[x1][y1] == computer && grid[x2][y2] == computer && grid[x3][y3] == '_' && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == computer && grid[x2][y2] == '_' && grid[x3][y3] == computer  && grid[x4][y4] == '_' )
                || (grid[x1][y1] == computer && grid[x2][y2] == '_' && grid[x3][y3] == '_'  && grid[x4][y4] == computer ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == computer && grid[x3][y3] == computer  && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == computer && grid[x3][y3] == '_'  && grid[x4][y4] == computer )
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == computer  && grid[x4][y4] == computer ) ) 
        {
            score = 10;
        }
        
        if( (grid[x1][y1] == user && grid[x2][y2] == user && grid[x3][y3] == '_' && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == user && grid[x2][y2] == '_' && grid[x3][y3] == user  && grid[x4][y4] == '_' )
                || (grid[x1][y1] == user && grid[x2][y2] == '_' && grid[x3][y3] == '_'  && grid[x4][y4] == user ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == user && grid[x3][y3] == user  && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == user && grid[x3][y3] == '_'  && grid[x4][y4] == user )
                || (grid[x1][y1] == '_' && grid[x2][y2] == '_' && grid[x3][y3] == user  && grid[x4][y4] == user ) ) 
        {
            score = -10;
        }
        
        if( (grid[x1][y1] == computer && grid[x2][y2] == computer && grid[x3][y3] == computer && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == computer && grid[x2][y2] == computer && grid[x3][y3] == '_'  && grid[x4][y4] == computer )
                || (grid[x1][y1] == computer && grid[x2][y2] == '_' && grid[x3][y3] == computer  && grid[x4][y4] == computer ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == computer && grid[x3][y3] == computer  && grid[x4][y4] == computer ) ) 
        {
            score = 100;
        }
        
        if( (grid[x1][y1] == user && grid[x2][y2] == user && grid[x3][y3] == user && grid[x4][y4] == '_' ) 
                || (grid[x1][y1] == user && grid[x2][y2] == user && grid[x3][y3] == '_'  && grid[x4][y4] == user )
                || (grid[x1][y1] == user && grid[x2][y2] == '_' && grid[x3][y3] == user  && grid[x4][y4] == user ) 
                || (grid[x1][y1] == '_' && grid[x2][y2] == user && grid[x3][y3] == user  && grid[x4][y4] == user ) ) 
        {
            score = -100;
        }
        
        if( grid[x1][y1] == computer && grid[x2][y2] == computer && grid[x3][y3] == computer && grid[x4][y4] == computer) {
            score = 500;
        }
        
        if( grid[x1][y1] == user && grid[x2][y2] == user && grid[x3][y3] == user && grid[x4][y4] == user) {
            score = -500;
        }
        
        return score;
    }
    
    public static int isGameOver(char[][] grid, int n) {
        // return 1 for computer ans 2 for user to win, 0 for game being not over.
        int score;
        
        if(n == 3) {
        
            score = evaluateline(0,0,0,1,0,2,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
            score = evaluateline(1,0,1,1,1,2,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
            score = evaluateline(2,0,2,1,2,2,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
            score = evaluateline(0,0,1,0,2,0,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
            score = evaluateline(0,1,1,1,2,1,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
            score = evaluateline(0,2,1,2,2,2,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
            score = evaluateline(0,0,1,1,2,2,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
            score = evaluateline(0,2,1,1,2,0,grid);
            if(score == 100) return 1;
            else if(score == -100) return 2;
        
        }
        
        else {
            
            score = evaluateline2(0,0,0,1,0,2,0,3,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(1,0,1,1,1,2,1,3,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(2,0,2,1,2,2,2,3,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(3,0,3,1,3,2,3,3,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(0,0,1,0,2,0,3,0,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(0,1,1,1,2,1,3,1,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(0,2,1,2,2,2,3,2,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(0,3,1,3,2,3,3,3,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(0,0,1,1,2,2,3,3,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
            score = evaluateline2(0,3,1,2,2,1,3,0,grid);
            if(score == 500) return 1;
            else if(score == -500) return 2;
        }
        
        return 0;
    }
    
    public static Vector<Pair<Integer, Integer>> getMoves(char[][] grid, int n)
    {
        Vector<Pair<Integer, Integer>> moves = new Vector<>();
        
        if(isGameOver(grid, n) != 0) {
            return moves;
        }
        
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                if(grid[i][j] == '_') {
                    moves.add(new Pair(i,j));
                }
            }
        }
        
        return moves;
    }
    
    public static void printGrid(char[][] grid, int n)
    {
        for(int i=0;i<n;i++) {
            for(int j=0;j<n;j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Grid size ( 3 for 3*3 / 4 for 4*4 ) : ");
        n = sc.nextInt();
        
        for(int i=0;i<n;i++) for(int j=0;j<n;j++) grid[i][j] = '_';
        System.out.println("Initial Grid : ");
        printGrid(grid, n);
        
        while(true){
            System.out.print("Enter the row and column : ");
            
            int x = sc.nextInt(); //row input
            int y = sc.nextInt(); //col input
            
            if(grid[x][y] != '_'){
                System.out.println("Invalid Input. Try again....");
                continue;
            }
            
            grid[x][y] = user;
            System.out.println("User moves : ");
            printGrid(grid, n);
            
            if(isGameOver(grid, n) == 1){
                System.out.println("Computer is the winner.");
                break;
            }
            else if(isGameOver(grid, n) == 2){
                System.out.println("User is the winner.");
                break;
            }
            else if(getMoves(grid, n).isEmpty()) {
                System.out.println("Match draw.");
                break;
            }
            
            Content c = minimax(0, true, MIN, MAX);
            //System.out.println("The optimal value is : " + c.value);
            
            grid[c.row][c.column] = computer;
            
            System.out.println("Computer moves : ");
            printGrid(grid, n);   
            
            if(isGameOver(grid, n) == 1){
                System.out.println("Computer is the winner.");
                break;
            }
            else if(isGameOver(grid, n) == 2){
                System.out.println("User is the winner.");
                break;
            }
            else if(getMoves(grid, n).isEmpty()) {
                System.out.println("Match draw.");
                break;
            }
        }
    } 
}
