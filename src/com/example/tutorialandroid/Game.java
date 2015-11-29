package com.example.tutorialandroid;

/**
 * 
 * @author allan06
 * This project has two classes: Game and MainActivity
 * Game has the methods that implement the rules of the game
 * for example, the method that tests if a jump is possible
 * The separation of the rules and the GUI allows for maintenance of 
 * the two modules independently. This is the the focus of MVC
 * Model-View Control design.
 */

public class Game 
{
	/*
	 * Fields
	 */
	
	public String gridToString ()
	{
		String str = "";
		for (int i=0; i<SIZE; i++)
		for (int j=0; j<SIZE; j++)
		str += grid[i][j];
		return str;
	}
	
	static final int SIZE = 7;
	
	// the only array that gets modified throughout the game
	// indicates if there is a button with a 1 or a 0 for each position at a given time
	private int grid[][];
	
	// appearance of the initial figure
	private static final int CROSS[][] =
		{
			{0,0,1,1,1,0,0},
			{0,0,1,1,1,0,0},
			{1,1,1,1,1,1,1},
			{1,1,1,0,1,1,1},
			{1,1,1,1,1,1,1},
			{0,0,1,1,1,0,0},
			{0,0,1,1,1,0,0}
		};
	
	// has the positions that are accessible by the gird
	private static final int BOARD[][] =
		{
			{0,0,1,1,1,0,0},
			{0,0,1,1,1,0,0},
			{1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1},
			{0,0,1,1,1,0,0},
			{0,0,1,1,1,0,0}
		};
	
	// the coordinates of the last radio button that the player wants to move
	private int pickedI, pickedJ;
	
	// the coordinates of the last radio button that the player tried to jump
	private int jumpedI, jumpedJ;
	// The game can be in 1 state out of 3 possible states at a time
	private enum State {READY_TO_PICK, READY_TO_DROP, FINISHED};
	private State gameState;
	
	/*
	 * Constructors
	 */
	public Game()
	{
		grid = new int [SIZE][SIZE];
		// initializes the grid with the cross array
		for (int i=0; i<SIZE; i++)
		for (int j=0; j<SIZE; j++)
		grid[i][j] = CROSS[i][j];
		// initializes the state of the game with ready to pick
		gameState = State.READY_TO_PICK;
	}
	
	/*
	 * Methods
	 */
	public int getGrid(int i, int j) 
	{
		return grid[i][j];
	}
	
	public void stringToGrid (String str)
	{
		for (int i=0, cont=0; i<SIZE; i++)
		for (int j=0; j<SIZE; j++)
		grid[i][j] = str.charAt(cont++)-'0';
	}
	
	// returns true if the button at i1,j1 can jump the button at i2,j2
	// hence, it is available to jump
	public boolean isAvailable(int i1, int j1, int i2, int j2)
	{
		// if the radio button isn't filled or 
		// if the destination button is already filled
		// returns false 
		if (grid[i1][j1]==0 || grid[i2][j2] == 1)
		{
			return false;
		}
		
		// prove that the positions of origin and destination are
		// within two units of distance in the row or column
		if (Math.abs(i2-i1) == 2 && j1 == j2)
		{
			jumpedI = i2 > i1 ? i1+1: i2+1;
			jumpedJ = j1;
			if (grid[jumpedI][jumpedJ] == 1)
			return true;
		}
		
		// prove that the button being jumped is filled
		if (Math.abs(j2-j1) == 2 && i1 == i2)
		{
			jumpedI = i1;
			jumpedJ = j2 > j1 ? j1+1: j2+1;
			if (grid[jumpedI][jumpedJ] == 1)
			return true;
		}
		
		return false;
	}
	
	public void play(int i, int j) 
	{
		// store the coordinates of the selected button in a variable
		if (gameState == State.READY_TO_PICK) 
		{
			pickedI = i;
			pickedJ = j;
			gameState = State.READY_TO_DROP;
		} 
		// prove that the jump is possible
		else if (gameState == State.READY_TO_DROP) 
		{
			if (isAvailable(pickedI, pickedJ, i, j)) 
			{
				gameState = State.READY_TO_PICK;
				grid[pickedI][pickedJ] = 0;
				grid[jumpedI][jumpedJ] = 0;
				grid[i][j] = 1;
			
				if (isGameFinished())
				{
					gameState = State.FINISHED;
				}
			}
			
			else 
			{
				pickedI=i;
				pickedJ=j;
			}
		}		
	}
	
	// Prove that a valid jump can be made
	// From the filled buttons on the board
	// returns false if a jump can still be made
	public boolean isGameFinished ()
	{
		for (int i=0; i<SIZE; i++)
			for (int j=0; j<SIZE; j++)
				for (int p=0; p<SIZE; p++)
					for (int q=0; q<SIZE; q++)
						if (grid[i][j]==1 && grid[p][q]==0 && BOARD[p][q]==1)
							if (isAvailable(i, j, p, q))
								return false;
		return true;
		}

	public void restart() 
	{
		// TODO Auto-generated method stub
		
	}
}
