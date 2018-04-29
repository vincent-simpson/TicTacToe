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

    public String getMove(char[][] board, char you) { // DO NOT CHANGE THIS LINE

        String move = "";
        int[][] keepTrack = new int[3][3];
        int count1 = 0;


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '.') {
                    count1++;
                }
            }
        }
        //Checks the above value for count. If no moves have been made yet, place first marker in the middle box.
        //If its the computer's turn first, still attempt to place the marker in the middle box.
//        if (count1 == 9 || count1 == 8) {
//            move = getMoveAt(1, 1);
//            return move;
//        }

        move = playCorners(board);


        //Checks for 2 in a row horizontally and plays the move that makes 3 in a row
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (board[i][j] == board[i][j + 1]) {
                    if (board[i][2] == '.') {
                        move = getMoveAt(i, 2);
                        return move;
                    } else if (board[i][0] == '.') {
                        move = getMoveAt(i, 0);
                        return move;
                    }

                }


            }

        }
        //Checks for 2 in a row vertically and plays the move that makes 3 in a column
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < 2; j++) {
                if (board[j][i] == board[j + 1][i]) {
                    if (board[2][i] == '.') {
                        move = getMoveAt(2, i);
                        return move;
                    } else if (board[0][i] == '.') {
                        move = getMoveAt(0, i);
                        return move;
                    }
                }
            }
        }

        //Checks for 2 in a row left to right diagonally and plays the move that makes 3 in a diagonal
        for (int i = 0; i < 2; i++) {
            if (board[i][i] == board[i + 1][i + 1]){
                if (board[2][2] == '.') {
                    move = getMoveAt(2, 2);
                    return move;
                } else if (board[0][0] == '.') {
                    move = getMoveAt(0,0);
                    return move;
                }
            } else if(board[0][0] == board[2][2]) {
                if(board[1][1] == '.') {
                    move = getMoveAt(1,1);
                    return move;
                }
            }
        }

        //Checks for 2 in a row right to left diagonally and plays the move that makes 3 diagonal
        if(board[0][2] == board[1][1]) {
            if (board[2][0] == '.') {
                move = getMoveAt(2, 0);
                return move;
            }

        } else if(board[2][0] == board[1][1]) {
            if(board[0][2] == '.') {
                move = getMoveAt(0,2);
                return move;
            }
        } else if(board[2][0] == board[0][2]) {
            if(board[1][1] == '.') {
                move = getMoveAt(1,1);
                return move;
            }
        }





        return move;
    }

    public static String getMoveAt(int num1, int num2) {

        String[][] moveArray = {{"TL", "TM", "TR"}, {"ML", "MM", "MR"}, {"BL", "BM", "BR"}};

        return moveArray[num1][num2];

    }

    public static String playCorners(char[][] board) {
        String move = "";
        if(board[0][0] == '.') {
            move = getMoveAt(0,0);
            return move;
        } else if(board[0][2] == '.') {
            move = getMoveAt(0,2);
            return move;
        } else if(board[2][0] == '.') {
            move = getMoveAt(2,0);
            return move;
        } else if(board[2][2] == '.') {
            move = getMoveAt(2,2);
            return move;
        }
        return move;
    }




}
