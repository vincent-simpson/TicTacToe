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

	private static char YOU;
	private static int HUMAN;
	private static int COMPUTER;
	
	public static char getYOU() {return YOU;}
	public static void setYOU(char yOU) {YOU = yOU;}
	public static int getHUMAN() {return HUMAN;}
	public static void setHUMAN(int hUMAN) {HUMAN = hUMAN;}
	public static int getCOMPUTER() {return COMPUTER;}
	public static void setCOMPUTER(int cOMPUTER) {COMPUTER = cOMPUTER;}
	
	public String getMove(char[][] board, char you) 
	{
		String move = "";
		
		// Creates a copy of the current game board
		BoardCopy.makeCopy(board);

		/** Sets the global variable YOU to the char passed by {@param you}
		* Checks to see if YOU is equal to the char 'x'. If true, this means that it's the human's (my)
		* turn first. 
		* */
		setYOU(you);
		if (getYOU() == 'x') 
		{
			setHUMAN(1);
			setCOMPUTER(2);
		} 
		else 
		{
			setHUMAN(2);
			setCOMPUTER(1);
		}
		
		// Makes the first move at the center square if available
		if (BoardCopy.getValue(1, 1) == '.') 
		{return move = "MM";}
		
		
		int[] winners = findWinner(board);
		
		final int HUMANWIN = 0, COMPUTERWIN = 1;

		if (winners[COMPUTERWIN] != -1) 
		{
			move = intToCellBoardPosition(winners[COMPUTERWIN]);
			return move;
		} 
		else if (winners[HUMANWIN] != -1) 
		{
			move = intToCellBoardPosition(winners[HUMANWIN]);
			return move;
		}

		move = playCorners();

		return move;
	}
	
	/**
	 * Sets the move to the first available corner of the board. This method is invoked when there are
	 * no winning or blocking moves to make.
	 * @return The corners on the board.
	 */
	public static String playCorners() 
	{
		String move = "";
		final int[] intCorners = { 0, 6, 2, 8 };
		String[] currentCell = new String[4];
		
		for (int i = 0; i < 4; i++) 
		{
			currentCell[i] = intToCellBoardPosition(intCorners[i]);
			if (BoardCopy.getCell(currentCell, i) == '.') 
			{
				move = currentCell[i];
				return move;
			}
		}return move;
	}

	/**
	 * Checks the current layout of the gameboard for a winner.
	 * @param board		the current game board
	 * @return the winning moves
	 */
	public static int[] findWinner(char[][] board) 
	{
		int[] winners;
		int[] cells = new int[3];
		int[] winningMoves = new int[2];
		int humanWin = -1, computerWin = -1;

		/**
		 * Initializes the integer array "cells" with the values as such on the first
		 * iteration:
		 * cells[0] = 0; 
		 * cells[1] = 1; 
		 * cells[2] = 2;
		 * Next, the integer array "winners" is assigned to the array returned from the
		 * method call {@see #findWinsNextTurn}
		 *   
		 */
		for (int columns = 0; (humanWin == -1 || computerWin == -1) && columns < 3; columns++) 
		{			
			for (int rows = 0; rows < 3; rows++) {cells[rows] = columns * 3 + rows;}

			winners = findWinsNextTurn(board, cells[0], cells[1], cells[2]);
			
			if (winners != null) 
			{
				if (winners[0] == getHUMAN()) {humanWin = winners[1];} 
				else if (winners[0] == getCOMPUTER()) {computerWin = winners[1];}
			} 		
			if(winningMoves[0] == humanWin && winningMoves[1] == computerWin) {return winningMoves;}				
		}

		// Test rows for win
		for (int row = 0; (humanWin == -1 || computerWin == -1) && row < 3; row++) 
		{
			for (int columns = 0; columns < 3; columns++) {cells[columns] = columns * 3 + row;}

			winners = findWinsNextTurn(board, cells[0], cells[1], cells[2]);
			
			if (winners != null) 
			{
				if (winners[0] == getHUMAN()) {humanWin = winners[1];} 
				else if (winners[0] == getCOMPUTER()) {computerWin = winners[1];}
			}
			if(winningMoves[0] == humanWin && winningMoves[1] == computerWin) {return winningMoves;}
		}

		// Checks diagonals
		if (humanWin == -1 || computerWin == -1) 
		{
			winners = findWinsNextTurn(board, 0, 4, 8);

			if (winners != null) 
			{
				if (winners[0] == getHUMAN()) {humanWin = winners[1];} 
				else if (winners[0] == getCOMPUTER()) {computerWin = winners[1];}
			}
			if(winningMoves[0] == humanWin && winningMoves[1] == computerWin) {return winningMoves;}
		}
		if (humanWin == -1 || computerWin == -1) 
		{			
			winners = findWinsNextTurn(board, 2, 4, 6);

			if (winners != null) 
			{
				if (winners[0] == getHUMAN()) {humanWin = winners[1];} 
				else if (winners[0] == getCOMPUTER()) {computerWin = winners[1];}
			}
		}
		winningMoves[0] = humanWin;
		winningMoves[1] = computerWin;
		return winningMoves;

	}

	/**
	 * This method, given the current layout of the board and the 3 positions to check
	 * on the board, will return
	 * ***NEED TO FIX***:
	 * returned array tells at position 0: who wins, computer or human and
	 * at position 1: which cell is the winner  
	 * @param board
	 * @param cell1
	 * @param cell2
	 * @param cell3
	 * @return the position on the board that results in a win.
	 */
	public static int[] findWinsNextTurn(char[][] board, int cell1, int cell2, int cell3) 
	{
		final int CELL = 1;
		final int PLAYER = 0;
		int[] winner = new int[2];
		char emptySpace = '.';
		
		/**
		 * If all 3 cells are empty, {@return null}
		 * After this method returns null, the code after this method is called checks for whether or not
		 * the call {@return null}. If it does {@return null}, the array winningMoves in the method
		 * findWinner returns as {@code {-1, -1}}. The if statements in the getMove method check for this,
		 * and if both values are {@code {-1, -1}}, the program proceeds to the playCorners method.
		 * 
		 * Essentially we can say that if all three cells are empty on
		 */
		if(BoardCopy.getValue(cell1) == emptySpace && BoardCopy.getValue(cell2) == emptySpace &&
				BoardCopy.getValue(cell3) == emptySpace) {return null;}
		
		else if (BoardCopy.getValue(cell1) == BoardCopy.getValue(cell2) && BoardCopy.getValue(cell3) == '.') 
		{
			winner[PLAYER] = 1;
			winner[CELL] = cell3;
			return winner;
		} 
		else if (BoardCopy.getValue(cell2) == BoardCopy.getValue(cell3) && BoardCopy.getValue(cell1) == '.') 
		{
			winner[PLAYER] = 1;
			winner[CELL] = cell1;
			return winner;
		}
		else if(BoardCopy.getValue(cell1) == BoardCopy.getValue(cell3) && BoardCopy.getValue(cell2) == '.') 
		{
			winner[PLAYER] = 1;
			winner[CELL] = cell2;
			return winner;
		}
		return winner = null;
	}
	
	/**
	 * @param cellNum
	 * @return The string value corresponding to the given numerical position on the board.
	 */
	public static String intToCellBoardPosition(int cellNum) 
	{
		int cell[] = { cellNum / 3, cellNum % 3 };
		return getStringMoveAtArray(cell);
	}

	/**
	 * 
	 * @param num1
	 * @param num2
	 * @return The string value corresponding to the coordinate position on the board given as two
	 * parameters.
	 */
	public static String getMoveAt(int num1, int num2) 
	{	
		String[][] moveArray = { 
				{ "TL", "TM", "TR" }, 
				{ "ML", "MM", "MR" }, 
				{ "BL", "BM", "BR" } };
		return moveArray[num1][num2];
	}

	/**
	 * 
	 * @param array
	 * @return The string value corresponding to the coordinate position on the board given as the first
	 * two values in the {@param array};
	 */
	public static String getStringMoveAtArray(int[] array) 
	{	
		int num1 = array[0];
		int num2 = array[1];
		String[][] moveArray = { 
				{ "TL", "TM", "TR" }, 
				{ "ML", "MM", "MR" }, 
				{ "BL", "BM", "BR" } };
		return moveArray[num1][num2];
	}

	/**
	 * 
	 * @author vince
	 *
	 */
	static class BoardCopy extends Challenger 
	{
		static char[][] boardCopy = new char[3][3];

		//Given a position on the board as a string, converts it to its corresponding numerical position
		/**
		 * 
		 * @param currentCell
		 * @param i
		 * @return
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

		/**
		 * 
		 * @param currentCell
		 * @param currentCell2
		 * @return
		 */
		public String getCell(int currentCell, int currentCell2) 
		{
			return Character.toString(boardCopy[currentCell][currentCell2]);
		}

		/**
		 * 
		 * @param row
		 * @param column
		 * @return
		 */
		public static char getValue(int row, int column) 
		{
			return boardCopy[row][column];
		}

		//Given a numerical position on the board (0-8 starting from the top left and going left to right), returns the character at that position.
		/**
		 * 
		 * @param row
		 * @return
		 */
		public static char getValue(int row) 
		{
			char c = 0;

			switch (row) 
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
		 * 
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
