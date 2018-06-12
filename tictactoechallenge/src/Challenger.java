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

public class Challenger2 extends Player {

	protected static char YOU;
	private static int HUMAN;
	private static int COMPUTER;
	
	public static char getYOU() {
		return YOU;
	}

	public static void setYOU(char yOU) {
		YOU = yOU;
	}

	public static int getHUMAN() {
		return HUMAN;
	}

	public static void setHUMAN(int hUMAN) {
		HUMAN = hUMAN;
	}

	public static int getCOMPUTER() {
		return COMPUTER;
	}

	public static void setCOMPUTER(int cOMPUTER) {
		COMPUTER = cOMPUTER;
	}

	public String getMove(char[][] board, char you) { // DO NOT CHANGE THIS LINE
		String move = "";
		int[] winners = new int[10];
		boardCopy boardCopy1 = new boardCopy();

		setYOU(you);
		if (getYOU() == 'x') {
			setHUMAN(1);
			setCOMPUTER(2);
		} else {
			setHUMAN(2);
			setCOMPUTER(1);
		}
		
		// Creates a copy of the current game board
		boardCopy1.makeCopy(board);

		// Makes the first move at the center square if available
		if (boardCopy.getValue(1, 1) == '.') {
			return move = "MM";
		}
		
		
		winners = findWinner(board);
		final int HUMANWIN = 0, COMPUTERWIN = 1;

		if (winners[COMPUTERWIN] != -1) {
			move = intToCell(winners[COMPUTERWIN]);
			return move;
		} else if (winners[HUMANWIN] != -1) {
			move = intToCell(winners[HUMANWIN]);

			return move;
		}

		final int[] corners = { 0, 6, 2, 8 };
		String[] currentCell = new String[10];
		for (int i = 0; i < 4; i++) {

			currentCell[i] = intToCell(corners[i]);
			if (boardCopy1.getCell(currentCell, i) == '.') {
				move = currentCell[i];
				return move;
			}
		}

		for (int i = 1; i < 8; i += 2) {
			currentCell[i] = intToCell(i);
			if (boardCopy1.getCell(currentCell, i) == '.') {
				move = currentCell[i];
				return move;
			}
		}

		return move;
	}

	//Returns an integer array which looks to see if either the Challenger has the next winning move, or if the AI does.
	public static int[] findWinner(char[][] board) {
		int[] winners;
		int[] cells = new int[3];
		int humanWin = -1, computerWin = -1;

		// Test columns for win
		for (int columns = 0; (humanWin == -1 || computerWin == -1) && columns < 3; columns++) {

			for (int rows = 0; rows < 3; rows++) {
				cells[rows] = columns * 3 + rows;
			}

			winners = findWinsNextTurn(board, cells[0], cells[1], cells[2]);
			if (winners != null) {
				if (winners[0] == HUMAN) {
					humanWin = winners[1];

				} else if (winners[0] == COMPUTER) {
					computerWin = winners[1];
				}
			}
		}

		// Test rows for win
		for (int row = 0; (humanWin == -1 || computerWin == -1) && row < 3; row++) {

			for (int columns = 0; columns < 3; columns++) {
				cells[columns] = columns * 3 + row;
			}

			winners = findWinsNextTurn(board, cells[0], cells[1], cells[2]);
			if (winners != null) {
				if (winners[0] == HUMAN) {
					humanWin = winners[1];
				} else if (winners[0] == COMPUTER) {
					computerWin = winners[1];
				}
			}
		}

		// Checks diagonals
		if (humanWin == -1 || computerWin == -1) {

			winners = findWinsNextTurn(board, 0, 4, 8);

			if (winners != null) {
				if (winners[0] == HUMAN) {
					humanWin = winners[1];
				} else if (winners[0] == COMPUTER) {
					computerWin = winners[1];
				}
			}
		}
		if (humanWin == -1 || computerWin == -1) {
			winners = findWinsNextTurn(board, 2, 4, 6);

			if (winners != null) {
				if (winners[0] == HUMAN) {
					humanWin = winners[1];
				} else if (winners[0] == COMPUTER) {
					computerWin = winners[1];
				}
			}
		}
		int[] winningMoves = { humanWin, computerWin };
		return winningMoves;

	}

	//Returns the position on the board that results in a win.
	public static int[] findWinsNextTurn(char[][] board, int cell1, int cell2, int cell3) {
		final int CELL = 1;
		final int PLAYER = 0;
		int[] winner = new int[2];

		boardCopy board1 = new boardCopy();
		
		if (board1.getValue(cell1) == board1.getValue(cell2) && board1.getValue(cell3) == '.') {
			winner[PLAYER] = 1;
			winner[CELL] = cell3;
			return winner;
		}
		if (board1.getValue(cell2) == board1.getValue(cell3) && board1.getValue(cell1) == '.') {

			winner[PLAYER] = 2;
			winner[CELL] = cell1;
			return winner;
		}
		if (board1.getValue(cell1) == board1.getValue(cell3) && board1.getValue(cell2) == '.') {

			winner[PLAYER] = 1;
			winner[CELL] = cell2;
			return winner;
		}
		return null;
	}

	//Returns a string value corresponding to the given numerical position on the board.
	public static String intToCell(int cellNum) {
		int cell[] = { cellNum / 3, cellNum % 3 };
		return getMoveAtArray(cell);
	}

	public static String getMoveAt(int num1, int num2) {

		String[][] moveArray = { { "TL", "TM", "TR" }, { "ML", "MM", "MR" }, { "BL", "BM", "BR" } };

		return moveArray[num1][num2];
	}

	public static String getMoveAtArray(int[] array) {
		int num1 = array[0];
		int num2 = array[1];

		String[][] moveArray = { { "TL", "TM", "TR" }, { "ML", "MM", "MR" }, { "BL", "BM", "BR" } };
		return moveArray[num1][num2];

	}

	
	static class boardCopy extends Challenger {
		static char[][] boardCopy = new char[3][3];

		public boardCopy() {
			char[][] boardCopy = new char[3][3];
		}

		//Given a position on the board as a string, converts it to its corresponding numerical position
		public char getCell(String[] currentCell, int i) {
			int num1 = 0;
			int num2 = 0;

			switch (currentCell[i]) {
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

		
		public String getCell(int currentCell, int currentCell2) {

			return Character.toString(boardCopy[currentCell][currentCell2]);
		}

		public static char getValue(int row, int column) {
			return boardCopy[row][column];
		}

		//Given a numerical position on the board (0-8 starting from the top left and going left to right), returns the character at that position.
		public char getValue(int row) {
			char c = 0;

			switch (row) {
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

		public void makeCopy(char[][] board) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					boardCopy[i][j] = board[i][j];

				}
			}

		}

	}

}
