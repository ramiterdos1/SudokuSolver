/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

/**
 * 20-07-13
 * @author ramit
 */
import java.util.*;
import java.awt.*;
import java.lang.*;
import java.awt.event.*;

public class SudokuSolver extends Frame implements ActionListener
{
    static int UNASSIGNED=0,N=9;
    /**
     * @param args the command line arguments
     */
    TextField t[];
    Button actv;
    int ls[][];//LatinSquares
    SudokuSolver()
    {
        t=new TextField[81];
        for(int i=0;i<81;i++)
        {
            t[i]=new TextField("",1);
            t[i].setBackground(Color.DARK_GRAY);
        
        }
        ls=new int[N][N];
        for(int i=0;i<81;i++)
           ls[i/9][i%9]=0;
        setLayout(new GridLayout(11,9));
        //add(new Label("Enter Numbers In the positions as your Sudoku Puzzle"));
        add(new Label("Enter",Label.CENTER));
        add(new Label("Numbers",Label.CENTER));
        add(new Label("In",Label.CENTER));
        add(new Label("the",Label.CENTER));
        add(new Label("positions",Label.CENTER));
        add(new Label("as",Label.CENTER)); 
        add(new Label("your",Label.CENTER));
        add(new Label("Sudoku",Label.CENTER));
        add(new Label("Puzzle",Label.CENTER));
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                add(t[i*9+j]);
        //setLayout(new FlowLayout());
        for(int i=0;i<4;i++)add(new Label(""));
        actv=new Button("SOLVE");
        add(actv);
        actv.addActionListener(this);
        setTitle("SUDOKU");
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
        setSize(650,650);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent we)
            {
                setVisible(false);
                System.exit(0);
            }
        });
        
        setVisible(true);
        
    }
     @Override
     public void actionPerformed(ActionEvent ae)
            {
                Exception e=new Exception();
                int n=0,i=0;
                try{
                   for(;i<81;i++)
                   {
                       if(t[i].getText().length()>1)
                        throw e;
                       n=Integer.parseInt(t[i].getText()+""+0);
                       System.out.println(i+"-"+n);
                       ls[i/9][i%9]=n/10;
                   }
                   if(i==81)
                       if(solve(ls))
                       {
                        for(i=0;i<81;i++)
                            t[i].setText(""+ls[i/N][i%N]);
                       }
                }catch(Exception f)
                {
                    System.out.println("Exception");
                    Font F = new Font(Font.MONOSPACED, Font.BOLD, 18);
                    Graphics2D g= ( Graphics2D )getGraphics();g.setColor(Color.RED);
                    g.setFont(F);
                    g.drawString("Correct you Entries!! :-/", 370, 620);
                    
                }
            }
     /* Searches the grid to find an entry that is still unassigned. If
   found, the reference parameters row, col will be set the location
   that is unassigned, and true is returned. If no unassigned entries
   remain, false is returned. */
     boolean FindUnassignedLocation(int grid[][], Point p)
     {
         int row=p.x,col=p.y;
        for (row = 0; row < N; row++)
        for (col = 0; col < N; col++)
            if (grid[row][col] == UNASSIGNED)
            {
                p.x=row;p.y=col;
                return true;
            }
         return false;
   }

     /* Returns a boolean which indicates whether any assigned entry
   in the specified row matches the given number. */
boolean UsedInRow(int grid[][], int row, int num)
{
    for (int col = 0; col < N; col++)
        if (grid[row][col] == num)
            return true;
    return false;
}
/* Returns a boolean which indicates whether any assigned entry
   in the specified column matches the given number. */
boolean UsedInCol(int grid[][], int col, int num)
{
    for (int row = 0; row < N; row++)
        if (grid[row][col] == num)
            return true;
    return false;
}

/* Returns a boolean which indicates whether any assigned entry
   within the specified 3x3 box matches the given number. */
boolean UsedInBox(int grid[][], int boxStartRow, int boxStartCol, int num)
{
    for (int row = 0; row < 3; row++)
        for (int col = 0; col < 3; col++)
            if (grid[row+boxStartRow][col+boxStartCol] == num)
                return true;
    return false;
}
 
/* Returns a boolean which indicates whether it will be legal to assign
   num to the given row,col location. */
boolean isSafe(int grid[][], int row, int col, int num)
{
    /* Check if 'num' is not already placed in current row,
       current column and current 3x3 box */
    return !UsedInRow(grid, row, num) &&
           !UsedInCol(grid, col, num) &&
           !UsedInBox(grid, row - row%3 , col - col%3, num);
}

     boolean solve(int grid[][])
     {
     int row, col;
     Point p=new Point(0,0);
    // If there is no unassigned location, we are done
    if (!FindUnassignedLocation(grid, p))
       return true; // success!

    // consider digits 1 to 9
    for (int num = 1; num <= 9; num++)
    {
        row=p.x;col=p.y;
        // if looks promising
        if (isSafe(grid, row, col, num))
        {
            // make tentative assignment
            grid[row][col] = num;

            // return, if success, yay!
            if (solve(grid))
                return true;

            // failure, unmake & try again
            grid[row][col] = UNASSIGNED;
        }
    }
    return false; // this triggers backtracking
}    
     
     public void paint(Graphics g)
     {
         System.out.println("HERE");
           
     }
    public static void main(String[] args) {
        // TODO code application logic here
        new SudokuSolver();
    }
}
