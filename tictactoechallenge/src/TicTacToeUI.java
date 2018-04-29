

import java.util.HashMap;

import edu.brookdale.ttt.Akuma;
import edu.brookdale.ttt.Bowser;
import edu.brookdale.ttt.DarthVader;
import edu.brookdale.ttt.Ed;
import edu.brookdale.ttt.Neo;
import edu.brookdale.ttt.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/** JAVA FX UPDATE SITE:  http://download.eclipse.org/efxclipse/updates-released/2.3.0/site */

// DO NOT MODIFY THIS FILE!!!

public class TicTacToeUI extends Application {

  private static final int GAMES = 1000;
  private static final int MAX_MS = 1000;
  private static final int MAX_MOVES = 18; // 9 per player
  private static final int ROWS = 3;
  private static final int COLS = 3;
  private static char player = 'o';
  private static char computer = 'x';
  private static final char empty = '.';
  private static HashMap<String,int[]> moves;
  private static final Font font = Font.font ("Arial", 115);
  private static final Font labelFont = Font.font ("Courier New", 14);
  private static final Font tFont = Font.font ("Arial", FontWeight.BOLD, 16);
  private ProgressBar pb = new ProgressBar(0.0);
  
  static {
    moves = new HashMap<String, int[]>();
    moves.put("TL",new int[] {0,0});
    moves.put("TM",new int[] {0,1});
    moves.put("TR",new int[] {0,2});
    moves.put("ML",new int[] {1,0});
    moves.put("MM",new int[] {1,1});
    moves.put("MR",new int[] {1,2});
    moves.put("BL",new int[] {2,0});
    moves.put("BM",new int[] {2,1});
    moves.put("BR",new int[] {2,2});
  }

  private char[][] board = new char[ROWS][COLS];
  private int completed_moves;
  private int DELAY_MS = MAX_MS / 2;
  private boolean UPDATEUI = true;
  private Pane pane;
  private Text tl, tm, tr, ml, mm, mr, bl, bm, br;
  private Text[] squares = null;
  private Label tlabel,outputlabel;
  private Slider slider;
  private CheckBox cb;
  private char[][] temparr = new char[ROWS][COLS];
  private Thread gameThread;
  private Button btnStart, btnExit;
  private double totalIterations = 4000.0;
  private double countIterations = 0.0;

  //Returns character of the winner, 'T' for a tie, or ' ' if no winner yet
  private char gameOver() {
    boolean b = false;
    char c = empty;

    //check for a row winner
    for (int row=0; row < board.length; row++) {
      b = true;
      c = board[row][0];
      if (c == empty) continue;
      for (int col=1; col < board[row].length; col++) {
        if (board[row][0] != board[row][col] || (board[row][col] == empty) ) {
          b = false;
          c = empty;
          break;
        }
      }
      if (b) return c;
    }

    //check for a column winner
    for (int col=0; col < COLS; col++) {
      b = true;
      c = board[0][col];
      if (c == empty) continue;
      for (int row=1; row < ROWS; row++) {
        if (board[0][col] != board[row][col] || board[row][col] == empty) {
          b = false;
          c = empty;
          break;
        }
      }
      if (b) return c;
    }		

    //check for top-left/bottom-right diagonal winner
    b = true;
    c = board[0][0];
    if (c != empty) {
      for (int row=1; row < ROWS; row++) {
        for (int col=1; col < COLS; col++) {
          if (row == col) {
            if (board[0][0] != board[row][col]) {
              b = false;
              c = empty;
              break;
            }
          }
        }
      }
      if (b) return c;
    }

    //check for bottom-left/top-right diagonal winner
    b = true;
    c = board[ROWS-1][0];
    if (c != empty) {
      int row = 1;
      int col = 1;
      while (row >= 0) {
        if (board[ROWS-1][0] != board[row][col]) {
          c = empty;
          b = false;
          break;
        }
        col++;
        row--;
      }
      if (b) return c;
    }

    //if no more empty squares, then it's a tie
    boolean bMoreMoves = false;
    for (int i=0; i < ROWS; i++) {
      for (int j=0; j < COLS; j++) {
        if (board[i][j] == empty) {
          bMoreMoves = true;
          break;
        }
      }
      if (bMoreMoves) break;
    }
    if (!bMoreMoves) return 'T';

    //if we've made max moves, then just end this futility
    if (completed_moves >= MAX_MOVES) return 'T';

    //if we make it here, then there's no winner currently
    return ' ';
  }

  public void initGame() {
    completed_moves = 0;
    board = new char[][] {
      {empty,empty,empty},
      {empty,empty,empty},
      {empty,empty,empty}
    };

    //alternate x and o
    if (player == 'x') {
      player = 'o';
      computer = 'x';
    } else {
      player = 'x';
      computer = 'o';
    }
  }

  public void copy2dArray(char[][] src , char[][] dest) {
    for (int i=0; i < src.length; i++) {
      for (int j=0; j < src[i].length; j++) {
        dest[i][j] = src[i][j];
      }
    }
  }

  public void play(Player tplayer , Player tcomputer) {
    int playerPoints = 0;
    int computerPoints = 0;

    //win (2 points), tie (1 point)
    //System.out.println("Playing " + GAMES + " games...");
    String pclass = tplayer.getClass().getSimpleName();
    String cclass = tcomputer.getClass().getSimpleName();
    Platform.runLater(new Runnable() {
      @Override public void run() {
        String s = outputlabel.getText();
        String snew = String.format("%-48s", pclass + " vs. " + cclass + " (" + GAMES + " games)...");
        outputlabel.setText(s + snew);
      }
    });

    for (int game = 0; game < GAMES; game++) {
      countIterations++;
      pb.setProgress(countIterations/totalIterations);
      initGame();
      while (true) {				
        if (player == 'x') {	
          copy2dArray(board , temparr);
          String pmove = tplayer.getMove(temparr, player);
          completed_moves++;
          if (moves.containsKey(pmove)) {
            int[] arr = moves.get(pmove);
            int x = arr[0];
            int y = arr[1];
            if (board[x][y] == empty) {
              board[x][y] = player;
              printUIBoard();
              if (gameOver() != ' ') break;
            }
          }

          copy2dArray(board , temparr);
          String cmove = tcomputer.getMove(temparr, computer);
          completed_moves++;
          if (moves.containsKey(cmove)) {
            int[] arr = moves.get(cmove);
            int x = arr[0];
            int y = arr[1];
            if (board[x][y] == empty) {
              board[x][y] = computer;
              printUIBoard();
              if (gameOver() != ' ') break;
            }
          }
        } else { //player is 'o'
          copy2dArray(board , temparr);			
          String cmove = tcomputer.getMove(temparr, computer);
          completed_moves++;
          if (moves.containsKey(cmove)) {
            int[] arr = moves.get(cmove);
            int x = arr[0];
            int y = arr[1];
            if (board[x][y] == empty) {
              board[x][y] = computer;
              printUIBoard();
              if (gameOver() != ' ') break;
            }
          }
          copy2dArray(board , temparr);	
          String pmove = tplayer.getMove(temparr, player);
          completed_moves++;
          if (moves.containsKey(pmove)) {
            int[] arr = moves.get(pmove);
            int x = arr[0];
            int y = arr[1];
            if (board[x][y] == empty) {
              board[x][y] = player;
              printUIBoard();
              if (gameOver() != ' ') break;
            }
          }
        }
      }

      //completed a game
      char result = gameOver();
      if (player == result)
        playerPoints += 2;
      else if (computer == result)
        computerPoints += 2;
      else if (result == 'T') {
        playerPoints += 1;
        computerPoints += 1;
      }
    }

    //calculate margin of victory or loss %
    double totalPoints = playerPoints + computerPoints;
    double playerPct = (playerPoints/totalPoints)*100.0;
    double computerPct = (computerPoints/totalPoints)*100.0;
    String outcome = "";
    if (playerPct >= 49.0)
      outcome = "PASS";
    else
      outcome = "FAIL";

    //stdout is obsolete now
    //System.out.printf("%-15s points: %8d (%6.2f%%) %s \n" ,  pclass, playerPoints , playerPct , outcome);
    //System.out.printf("%-15s points: %8d (%6.2f%%)\n" , cclass, computerPoints , computerPct );
    //System.out.println();
    String s1 = String.format("%15s points: %8d (%6.2f%%) %s \n" , pclass, playerPoints , playerPct , outcome);
    String s2 = String.format("%15s points: %8d (%6.2f%%)\n" , cclass, computerPoints , computerPct);
    Platform.runLater(new Runnable() {
      @Override public void run() {
        String s = outputlabel.getText();
        outputlabel.setText(s + "\n" + s1 + s2 + "\n");
      }
    });

  }

  @SuppressWarnings("unused")
  private static void printBoard(char[][] board) {
    String s = "";
    for (int i=0; i < board.length; i++) {
      for (int j=0; j < board[i].length; j++) {
        s = s + board[i][j] +  " ";
      }
      s += "\n";
    }	
    System.out.println(s);
  }	

  private void printUIBoard() {
    if (!UPDATEUI)
      return;
    Platform.runLater(new Runnable() {
      @Override public void run() {
        setBoard(board);
      }
    });
    try {
      Thread.sleep(DELAY_MS); //sleep
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop(){
    System.out.println("Ending...");
    System.exit(0);
  }

  public void setBoard(char[][] board) {
    tlabel.setText("Player is " + (player+"").toUpperCase() + "     Computer is " + (computer+"").toUpperCase());
    int index = 0;
    for (int i=0; i < 3; i++) {
      for (int j=0; j < 3; j++) {
        if (board[i][j] == 'x' ) {
          squares[index].setFill(Color.RED);
          squares[index].setText("X");
        } else if (board[i][j] == 'o') {
          squares[index].setFill(Color.BLUE);
          squares[index].setText("O");
        } else {
          squares[index].setText("");
        }
        index++;
      }

    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    squares = new Text[9];
    int sqindex = 0;
    BorderPane bpane = new BorderPane();
    bpane.setStyle("-fx-background-color: #FFFFFF;");
    pane = new Pane();
    bpane.setCenter(pane);
    tlabel = new Label();
    tlabel.setFont(tFont);
    bpane.setTop(tlabel);

    slider = new Slider();
    slider.setMin(1);
    slider.setMax(MAX_MS-1);
    slider.setValue(MAX_MS - DELAY_MS);
    slider.setShowTickLabels(false);
    slider.setShowTickMarks(false);
    slider.setMajorTickUnit(1000);
    slider.setMinorTickCount(1);
    slider.valueProperty().addListener(new ChangeListener<Object>() {
      @Override
      public void changed(ObservableValue<?> arg0, Object arg1, Object arg2) {
        DELAY_MS = MAX_MS - (int) slider.getValue();
      }
    });

    cb = new CheckBox("Enabled");
    cb.setSelected(true);
    cb.selectedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        UPDATEUI = cb.isSelected();
      }
    });
    HBox hbox = new HBox(cb);
    hbox.setPadding(new Insets(0, 0, 0, 20));
    hbox.setSpacing(10);

    GridPane gp = new GridPane();
    gp.add(slider,0,0);
    gp.add(hbox,1,0);
    Label slabel = new Label("speed");
    slabel.setPadding(new Insets(0, 0, 0, 45));
    slabel.setAlignment(Pos.CENTER);
    gp.add(slabel, 0, 1);

    btnStart = new Button("Start");
    btnStart.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        outputlabel.setText("");
        btnStart.setDisable(true);
        //run the game
        gameThread = new Thread(new Runnable() {
          @Override public void run() {
            Player player, computer;
            countIterations = 0;
            pb.setProgress(0.0);

            //Create the PLAYER/CHALLENGER
            player = new Challenger();

            //PLAYER plays all computer opponents, in increasing difficulty

            computer = new Bowser();
            play(player, computer);

            computer = new DarthVader();
            play(player, computer);

            computer = new Neo();
            play(player, computer);

            computer = new Akuma();
            play(player, computer);

            computer = new Ed();
            play(player, computer);
            
            Platform.runLater(new Runnable() {
              @Override public void run() {
                String s = outputlabel.getText();
                outputlabel.setText(s + "Done.\n");
                btnStart.setDisable(false);
              }
            });

          }});
        gameThread.start();
      }
    });
    btnExit = new Button("Exit");
    btnExit.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Platform.exit();
        System.exit(0);
      }
    });

    hbox = new HBox();
    hbox.getChildren().addAll(btnStart,btnExit);
    hbox.setPadding(new Insets(0,10,0,10));
    hbox.setSpacing(10);

    gp.add(hbox, 2,0);
    gp.add(pb, 3, 0);
    bpane.setBottom(gp);

    HBox hbOutput = new HBox();
    outputlabel = new Label("");
    outputlabel.setAlignment(Pos.CENTER_LEFT);
    outputlabel.setFont(labelFont);
    hbOutput.getChildren().add(outputlabel);
    hbOutput.setStyle("-fx-background-color: #DCDCDC;");
    bpane.setRight(hbOutput);

    //draw grid
    Rectangle r = new Rectangle();
    r.setX(100);
    r.setY(0);
    r.setWidth(10);
    r.setHeight(310);
    pane.getChildren().add(r);	

    r = new Rectangle();
    r.setX(210);
    r.setY(0);
    r.setWidth(10);
    r.setHeight(310);
    pane.getChildren().add(r);	

    r = new Rectangle();
    r.setX(0);
    r.setY(100);
    r.setWidth(320);
    r.setHeight(10);
    pane.getChildren().add(r);	

    r = new Rectangle();
    r.setX(0);
    r.setY(210);
    r.setWidth(320);
    r.setHeight(10);
    pane.getChildren().add(r);		

    //add the squares (letter holders)
    tl = new Text("");
    tl.setFont(font);
    tl.setX(7);
    tl.setY(90);
    pane.getChildren().add(tl);
    squares[sqindex++] = tl;

    tm = new Text("");
    tm.setFont(font);
    tm.setX(117);
    tm.setY(90);
    pane.getChildren().add(tm);
    squares[sqindex++] = tm;

    tr = new Text("");
    tr.setFont(font);
    tr.setX(222);
    tr.setY(90);
    pane.getChildren().add(tr);
    squares[sqindex++] = tr;

    ml = new Text("");
    ml.setFont(font);
    ml.setX(7);
    ml.setY(200);
    pane.getChildren().add(ml);
    squares[sqindex++] = ml;

    mm = new Text("");
    mm.setFont(font);
    mm.setX(117);
    mm.setY(200);
    pane.getChildren().add(mm);
    squares[sqindex++] = mm;

    mr = new Text("");
    mr.setFont(font);
    mr.setX(222);
    mr.setY(200);
    pane.getChildren().add(mr);
    squares[sqindex++] = mr;

    bl = new Text("");
    bl.setFont(font);
    bl.setX(7);
    bl.setY(310);
    pane.getChildren().add(bl);
    squares[sqindex++] = bl;

    bm = new Text("");
    bm.setFont(font);
    bm.setX(117);
    bm.setY(310);
    pane.getChildren().add(bm);
    squares[sqindex++] = bm;

    br = new Text("");
    br.setFont(font);
    br.setX(222);
    br.setY(310);
    pane.getChildren().add(br);
    squares[sqindex++] = br;

    Scene scene = new Scene(bpane,800,385);
    primaryStage.setTitle("Tic Tac Toe Challenge");
    primaryStage.setScene(scene);
    if (UPDATEUI)
      primaryStage.show();

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      public void handle(WindowEvent we) {
        Platform.exit();
        System.exit(0);
        System.out.println("Bye.");
      }
    });
  }

  public static void main(String[] args) {
    launch(args);
  }
}
