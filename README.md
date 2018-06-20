## Disclaimer
The only file that I authored was the `Challenger.java` file. The `TicTacToeUI.java` file included in this repository was provided as part of a college assignment at Brookdale Community College instructed by [Edwin Torres](https://gitlab.com/CoachEd).

## Installation
This program was written in the [Eclipse IDE](http://www.eclipse.org/downloads/packages/release/Photon/RC3) and its recommended to use in order to run this program because of its simplicity. However, additional instructions are provided below for running this program from the command line in Windows.

## Prerequisites
Once Eclipse is installed, its important to make sure that you have the most up-to-date version of JavaFX. Eclipse makes this very simple. 

1. With Eclipse open, go to `Help > Install New Software` 

2. In the box that says `Work with:` copy and paste the following:
      ```
      http://download.eclipse.org/efxclipse/updates-released/2.3.0/site
      ``` 
3. Select both boxes that appear and click Next to install JavaFX for Eclipse.

This program was written in Java 9 and you can download it and find instructions on installation [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

## Importing the Repository into Eclipse
With JavaFX installed, we're ready to download and import the repository into Eclipse.

The first step is downloading the repository to an easily accessed location. To download this repository directly, [click here](https://github.com/vincent-simpson/TicTacToe/archive/master.zip).  

With Eclipse open:
1. Click File > Import
2. In the window that appears, expand `General` and double click on `Import Existing Projects into Workspace`
3. Make sure `Select Root Directory` is chosen and browse for the location of where you downloaded the repository to. 
4. Highlight the folder (typically labeled "TicTacToe-master") and click `Select Folder`
5. Eclipse will automatically find the project nested within that folder and you can click `Finish` to finalize the import.

With the project now imported into Eclipse, simply right click the project, go to `Run As` and then `Application`

## Running from the Command Prompt
This program can also be run from the command prompt in Windows(or Terminal on a Mac/Linux) instead of in Eclipse, although its a little more complicated.  

Most importantly, you must have a Java SDK and a Java Runtime Environment (JRE) installed on your machine with the proper system variables set. Instructions on how to install these components can be found [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).  

With the Java SDK and JRE installed, we can now open the command prompt(or Terminal if you're on a Mac/Linux). To do this on Windows, press the Windows key and then enter `cmd` into the search bar. For Mac or Linux, use the search tool at the top right of your screen to find Terminal.  

1. With our command prompt open, first check to make sure Java is installed correctly. When entering `java -version`, we should get an output similar to this:  
![alt text](https://image.ibb.co/hOz6xy/checkjavaversion.png "Java version check")  

2. Next, we need to navigate into the location where we downloaded [this repository](https://github.com/vincent-simpson/TicTacToe/archive/master.zip). On my computer, I have it located in `C:\users\vince\`. To get there, enter the command `cd` and then the path location of the folder. In my case, I entered `cd c:\users\vince\TicTacToe\TicTacToeChallenge`  

3. Now that we're in the TicTacToeChallenge folder, we need to navigate into the `src` folder to access the `.java` files easier. To do that, enter `cd src`. Your current path should now read `c:\whereveryouare\TicTacToe\TicTacToeChallenge\src` Now we can start using Java to compile and run the program. First, we'll need to compile the Challenger.java file. To do that, enter the following if you're in Windows:  
```
javac -classpath "c:\wherever\you\downloaded\TicTacToe\TicTacToeChallenge\lib\ttt.jar" Challenger.java
```

4. Next, we'll need to compile the TicTacToeUI.java file. The command we enter is similar.
```
javac -classpath "c:\wherever\you\downloaded\TicTacToe\tictactoechallenge\lib\ttt.jar;." TicTacToeUI.java
```
**If you're on Mac or Linux, change the semi-colon after "jar" to a colon.**  

5. With the code now compiled, we're ready to run the program. Enter:
```
java -classpath "c:\wherever\you\downloaded\TicTacToe\tictactoechallenge\lib\ttt.jar;." TicTacToeUI
```
6. Finally, a window should appear that looks like this  
![alt text](https://i.imgur.com/646loGQ.png)




# TicTacToe Challenger***TO DO***

With the program UI open, you can see that there are a few different buttons that are responsible for controlling the program.  

At the bottom left, a slider named "speed" is used to adjust the speed at which the program will play through each round of Tic Tac Toe. To the right of that, a checkbox named "Enabled" when checked will visually show you each move that's being made by the preprogrammed computer and the Challenger (me). When unchecked, the program will run straight through without any visuals and produce the %win result for each difficulty played against.  

To the right of that, a Start and Exit button as well as a progress bar for how far along the program is (5000 games total).  

When pressing Start, you can see that the program displays at the top left which marker belong to the Player (me) and the Computer for each round. 

## Problem Statement 

```
Improve the getMove() function (contained within the Challenger.java file). Return your next move.  
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
  ```
  ## Methodology of Obtaining Maximum Points
  The first thing my code looks at is whose turn it is first. In this program, every time the parameter of the getMove() function is 'x', its my turn first. This is important in strategizing which position to make the first move.  
  
  If its my turn first, the strategy is as follows:  
  1. Play the middle square.
  2. Play a corner
  3. Since this is my third turn, there are now enough markers on the board to start looking for a winning/blocking move
  4. Play the winning/blocking move  
  
  The reason for playing in the middle square first is that a "mistake" made by the opponent is much easier to capitalize on than a mistake made after playing in a corner position first.  
 
## Results
Here are the results after playing through all 5000 games:  
![alt text](https://i.imgur.com/Orv4DIC.png)  

As we can see, there are large discrepancies between the first two difficulties, and the last three. This is due to the strategies programmed within those difficulties. The first two difficulties play seemingly random and without much attempt to block any of my moves. This produces the high number of winning points. Also note, the percentages are calculated based on `(numberOfPoints / 2000) * 100`  
2000 being the maximum number of points (1000 games * 2 points per win)
