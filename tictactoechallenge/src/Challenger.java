import edu.brookdale.ttt.Player;

/*
  Improve the getMove() function below. Return your next move.
  The board parameter is a 2D array. Each element contains either:
    '.' (open square)
    'x'
    'o'
  The you parameter is your letter: 'x' or 'o'
  Valid return values (moves):
    "TL"  "TM"  "TR" 
    "ML"  "MM"  "MR" 
    "BL"  "BM"  "BR"
  If you return an invalid move, you will lose your turn.
  You will earn 2 points for a win, 1 point for a tie, and 0 points for a loss.
 */

public class Challenger extends Player {

	private static char HUMANCHAR;
	private static char COMPUTERCHAR;
	private static int HUMAN;
	private static int COMPUTER;

	public static char getHUMANCHAR() {return HUMANCHAR;}
	public static void setHUMANCHAR(char newChar) {HUMANCHAR = newChar;}
	public static int getHUMAN() {return HUMAN;}
	public static void setHUMAN(int human) {HUMAN = human;}
	public static int getCOMPUTER() {return COMPUTER;}
	public static void setCOMPUTER(int computer) {COMPUTER = computer;}
	public static char getCOMPUTERCHAR() {return COMPUTERCHAR;}
	public static void setCOMPUTERCHAR(char comp) {COMPUTERCHAR = comp;}

	public String getMove(char[][] board, char you) 
	{
		String move = "";

		// Creates a copy of the current game board
		BoardCopy.makeCopy(board);

		/* Sets the global variable YOU to the char passed by the parameter: you
		 * Checks to see if YOU is equal to the char 'x'. If true, this means that it's the human's (my)
		 * turn first. 
		 */
		setHUMANCHAR(you);
		if (getHUMANCHAR() == 'x') 
		{
			setCOMPUTERCHAR('o');
			setHUMAN(1);
			setCOMPUTER(2);
		} 
		else 
		{
			setCOMPUTERCHAR('x');
			setHUMAN(2);
			setCOMPUTER(1);
		}


		//If its my turn first
		if(getHUMAN() == 1) {
			//Play the top left square.
			if(BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(0))) 
			{
				return "TL";
			//If that position is taken, as it would be on my second turn, play the bottom right square.	
			}else if(BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(8)))
			{
				return "BR";
			}
			//If both spots are taken, look for the winning/blocking move and make it.
			else 
			{				
				int winner = findWinner(board);

				if (winner != -1) 
				{
					move = getMoveAtSinglePosition(winner);
					return move;
				} 
				else if (winner != -1) 
				{
					move = getMoveAtSinglePosition(winner);
					return move;
				}
				//If there are no winning/blocking moves, play the corners.
				move = playCorners();

				return move;
			}
		}
		//If its the computer's turn first.
		else 
		{
			//If the center square is available, make move there for best chance at tieing 
			if (BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(4))) {
				return "MM";
			}
			//Find the winning move and play it.
			int winner = findWinner(board);

			if (winner != -1) 
			{
				move = getMoveAtSinglePosition(winner);
				return move;
			} 
			else if (winner != -1) 
			{
				move = getMoveAtSinglePosition(winner);
				return move;
			}
			//If there are no winning/blocking moves, play the corners.
			move = playCorners();

			return move;
		}
	}

	/**
	 * Sets the move to the first available corner of the board. This method is invoked 
	 * when there are no winning or blocking moves to make.
	 * @return The corners on the board.
	 */
	public static String playCorners() 
	{
		String move = "";
		String[] stringCorners = {"TL", "TR", "BL", "BR"};

		for (int i = 0; i < 4; i++) 
		{
			if (BoardCopy.isEmptySpace(BoardCopy.getCell(stringCorners, i))) 
			{
				move = stringCorners[i];
				return move;
			}
		}return move;
	}

	/**
	 * Checks the current layout of the gameboard for a winner.
	 * @param board	The current gameboard.
	 * @return The winning moves.
	 */
	public static int findWinner(char[][] board) 
	{
		int winner;
		int[] cells = new int[3];
		/**
		 * Initializes the integer array "cells" with the values as such on the first
		 * iteration:
		 * cells[0] = 0; 
		 * cells[1] = 1; 
		 * cells[2] = 2;
		 * Next, the integer array "winners" is assigned to the array returned from the
		 * method call @see findWinsNextTurn
		 *   
		 */
		//Tests rows for win.
		for (int columns = 0;columns < 3; columns++) 
		{			
			for (int rows = 0; rows < 3; rows++) 
			{
				cells[rows] = columns * 3 + rows;
			}

			winner = findWinningCell(cells[0], cells[1], cells[2]);
			if (winner != -1) 
			{
				return winner;
			}				
		}

		//Tests columns for win
		for (int row = 0;row < 3; row++) 
		{
			for (int columns = 0; columns < 3; columns++) 
			{
				cells[columns] = columns * 3 + row;
			}

			winner = findWinningCell(cells[0], cells[1], cells[2]);
			if (winner != -1) 
			{
				return winner;
			}
		}

		//Tests diagonals for win
		winner = findWinningCell(0, 4, 8);
		if (winner != -1) 
		{
			return winner;
		}


		winner = findWinningCell(2, 4, 6);
		if (winner != -1) 
		{
			return winner;
		}
		
		//winningMove is -1 when there are no winning moves found.
		winner = -1;
		return winner;

	}

	/**
	 * 
	 * @param board
	 * @param cell1
	 * @param cell2
	 * @param cell3
	 * @return This method, given the current layout of the board and the 3 positions to check
	 * on the board, will return the position on the board that results in a win.
	 */
	public static int findWinningCell(int cell1, int cell2, int cell3) 
	{
		int winningCell = 0;
		/*
		 * If all 3 cells are empty, return null
		 * After this method returns null, the code after this method is called checks for whether or not
		 * the call returns null. If it does return null, the array winningMoves in the method
		 * findWinner returns as {-1, -1}. The if statements in the getMove method check for this,
		 * and if both values are -1, the program proceeds to the playCorners method.
		 * 
		 * Essentially we can say that if all three cells are empty, no winning moves are returned. 
		 */

		if(isEmptySpace(cell1, cell2, cell3)) {
			return -1;
		}

		else if (BoardCopy.getPositionalValue(cell1) == BoardCopy.getPositionalValue(cell2) 
				&& BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(cell3))) 
		{
			winningCell = cell3;
		} 
		else if (BoardCopy.getPositionalValue(cell2) == BoardCopy.getPositionalValue(cell3) 
				&& BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(cell1))) 
		{
			winningCell = cell1;
		}
		else if(BoardCopy.getPositionalValue(cell1) == BoardCopy.getPositionalValue(cell3) 
				&& BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(cell2))) 
		{
			winningCell = cell2;
		} else {
			return -1;
		}

		return winningCell;	
	}

	/**
	 * 
	 * @param cell1
	 * @param cell2
	 * @param cell3
	 * @return True if all three cells are empty, else return false.
	 */
	public static boolean isEmptySpace(int cell1, int cell2, int cell3) {
		if( BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(cell1)) && 
			BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(cell2)) &&
			BoardCopy.isEmptySpace(BoardCopy.getPositionalValue(cell3))) 
				{return true;}
		else {return false;}
	}
	
	/**
	 * 
	 * @param position
	 * @return The position on the board as a string given the numerical position.
	 */
	public static String getMoveAtSinglePosition(int position) {
		String returnString = "";

		switch(position) {
		case 0:
			returnString = "TL";
			break;
		case 1:
			returnString = "TM";
			break;
		case 2:
			returnString = "TR";
			break;
		case 3:
			returnString = "ML";
			break;
		case 4:
			returnString = "MM";
			break;
		case 5:
			returnString = "MR";
			break;
		case 6:
			returnString = "BL";
			break;
		case 7:
			returnString = "BM";
			break;
		case 8:
			returnString = "BR";
			break;
		}
		return returnString;
	}

	/**
	 * This class is used to create a copy of the current gameboard.
	 * @author vince
	 */
	static class BoardCopy extends Challenger 
	{
		static char[][] boardCopy = new char[3][3];

		public BoardCopy() {}

		/**
		 * Given a position on the board as a string @param currentCell, @return the current 
		 * character in position @param i.
		 * @param currentCell
		 * @param i
		 */
		public static char getCell(String[] currentCell, int i) 
		{
			int num1 = 0;
			int num2 = 0;

			switch (currentCell[i]) 
			{
			case "TL":
				num1 = 0;
				num2 = 0;
				break;
			case "TM":
				num1 = 0;
				num2 = 1;
				break;
			case "TR":
				num1 = 0;
				num2 = 2;
				break;
			case "ML":
				num1 = 1;
				num2 = 0;
				break;
			case "MM":
				num1 = 1;
				num2 = 1;
				break;
			case "MR":
				num1 = 1;
				num2 = 2;
				break;
			case "BL":
				num1 = 2;
				num2 = 0;
				break;
			case "BM":
				num1 = 2;
				num2 = 1;
				break;
			case "BR":
				num1 = 2;
				num2 = 2;
				break;
			}
			return boardCopy[num1][num2];
		}

		public static boolean isEmptySpace(char ch) {
			if(ch == '.') {
				return true;
			} else {
				return false;
			}
		}

		/**
		 * Given a numerical position on the board (0-8 starting from the top left and going left to right), 
		 * @return the character at that numerical position.
		 * @param numericalPosition
		 */
		public static char getPositionalValue(int numericalPosition) 
		{
			char c = 0;

			switch (numericalPosition) 
			{
			case 0:
				c = boardCopy[0][0];
				break;
			case 1:
				c = boardCopy[0][1];
				break;
			case 2:
				c = boardCopy[0][2];
				break;
			case 3:
				c = boardCopy[1][0];
				break;
			case 4:
				c = boardCopy[1][1];
				break;
			case 5:
				c = boardCopy[1][2];
				break;
			case 6:
				c = boardCopy[2][0];
				break;
			case 7:
				c = boardCopy[2][1];
				break;
			case 8:
				c = boardCopy[2][2];
				break;
			}
			return c;
		}

		/**
		 * Takes the current gameboard and moves its contents into {@link BoardCopy#boardCopy}
		 * @param board
		 */
		public static void makeCopy(char[][] board) 
		{
			for (int i = 0; i < board.length; i++) 
			{
				for (int j = 0; j < board.length; j++) 
				{
					boardCopy[i][j] = board[i][j];
				}
			}
		}
	}
}
