## Disclaimer
The only file that I authored was the `Challenger.java` file. The `TicTacToeUI.java` file included in this repository was provided as part of a college assignment at Brookdale Community College instructed by [Edwin Torres](https://gitlab.com/CoachEd).

## Installation
This program was written in the [Eclipse IDE(download)](http://www.eclipse.org/downloads/packages/release/Photon/RC3) and its recommended to use in order to run this program because of its simplicity. However, additional instructions are provided below for running this program from the command line in Windows.

## Prerequisites
Once Eclipse is installed, its important to make sure that you have the most up-to-date version of JavaFX. Eclipse makes this very simple. 

1. With Eclipse open, go to `Help > Install New Software` 

2. In the box that says `Work with:` copy and paste the following:
      ```
      http://download.eclipse.org/efxclipse/updates-released/2.3.0/site
      ``` 
3. Select both boxes that appear and click Next to install JavaFX for Eclipse.

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





# TicTacToe Challenger

## Problem Statement 

Improve the getMove() function below(contained within the Challenger.java file). Return your next move.  
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
  
  
