

import java.util.Scanner;

public class Main {

	/**
	 * Main method. Allows commands to work
	 * 
	 */
	public static void main(String[] args) 
	{
		System.out.println("Welcome to checkers! Please enter a command to begin.");
		boolean isItGoing = true;
		boolean isTheGameStarted = false;
		boolean isItPlayerZeroTurn = true;
		Scanner s = new Scanner(System.in);
		while(isItGoing)
		{
			String commandLine = s.nextLine();

			String command[] = commandLine.split(" ");

			switch(commandLine.split(" ")[0]){

			case "start":
				if(2 == command.length)
				{

					CheckerCommander.start(command[1]);
					isTheGameStarted = true;
				}

				else
				{
					System.out.println("To begin a game, enter the command in this format: \"start <filename>\"\n for a new file, enter a file name that does not already exist. No spaces allowed in the filename.");
				}
				break;

			case "end":
				isItGoing = false;
				break;

			case "move":
				if(isTheGameStarted)
				{
					if(command.length == 5)	
					{

						Coordinates initialLocation = new Coordinates(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
						Coordinates finalLocation = new Coordinates(Integer.parseInt(command[3]), Integer.parseInt(command[4]));

						if (CheckerCommander.isCorrectPieceSelected(initialLocation, isItPlayerZeroTurn)) {
							isItGoing = (CheckerCommander.movePiece(initialLocation, finalLocation, isItPlayerZeroTurn));
							isItPlayerZeroTurn = !isItPlayerZeroTurn;
						}else{
							char playerPiece = (isItPlayerZeroTurn) ? '0' : '1';

							System.out.println("Invalid coordinates: Incorrect Piece Selected. Please choose coordinates with a " + playerPiece + " in it.");
						}

					}

					else
					{
						System.out.println("To begin a game, enter the command in this format: move x y x' y'");
					}
				}

				else
				{
					System.out.println("No game file has been started. Please use the start command.");
				}

				break;
			case "save":
				if(isTheGameStarted){
					CheckerCommander.saveFile();
				}else{
					System.out.println("No game file has been started. Please use the start command.");
				}
				break;
			case "board":
				if(isTheGameStarted)
				{
					CheckerCommander.showBoard();
				}

				else
				{
					System.out.println("No game file has been started. Please use the start command.");
				}
			}
		}
		s.close();
		System.out.println("End of Checkers Game");
	}

}
