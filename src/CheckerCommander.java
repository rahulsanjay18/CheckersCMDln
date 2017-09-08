

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;



public class CheckerCommander {

	private static ArrayList<CheckerPiece> zeroPieces = new ArrayList<CheckerPiece>();
	private static ArrayList<CheckerPiece> onePieces = new ArrayList<CheckerPiece>();
	private static String file;
	private static char board[][] = new char[34][18];
	private static File saveFile;

	/**
	 * Finds or creates a file with a file name
	 * @param fileName name of file to look for/ create
	 */

	public static void start(String fileName) 
	{

		file = fileName;
		saveFile = new File(file + ".txt");


		try 
		{
			// checks if savefile exists
			if(!saveFile.exists())
			{
				saveFile.createNewFile();

			}
			// reader object

			BufferedReader br = new BufferedReader(new FileReader(saveFile));

			// checks if the savefile exists again, and sees if it contains any data 
			if(!saveFile.exists() || br.readLine() == null)
			{

				saveFile.createNewFile();
				setupNewBoard();


			}else{
				readFile();
			}

			br.close();
		} 

		catch (Exception e1) 
		{
			e1.printStackTrace();
		}

	}

	/**
	 * reads the file
	 */
	private static void readFile(){
		try {
			// reader object
			BufferedReader br = new BufferedReader(new FileReader(saveFile));

			// holds each line of one checkerpiece object
			String line;

			// goes through file, and converts each line into an object and adds it to the arraylist
			while ((line = br.readLine())  != null) {
				String[] lineArr = line.split(" ");
				boolean liveOrNot = (lineArr[3].equals("true")) ? true : false;

				CheckerPiece piece = new CheckerPiece(Integer.parseInt(lineArr[0]), new Coordinates(Integer.parseInt(lineArr[1]), Integer.parseInt(lineArr[2])), liveOrNot);
				if(piece.color == 0){
					zeroPieces.add(piece);
				}else{
					onePieces.add(piece);
				}
			}

			br.close();

			fillInBoardArray();
			showBoard();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saveFile(){

		try {
			// file writer declaration
			BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));

			// writes data for the zero array
			for(int i = 0; i < zeroPieces.size(); i++){

				writer.write("0" + " " + zeroPieces.get(i).location.x + " " + zeroPieces.get(i).location.y + " " + zeroPieces.get(i).isItAlive);
				writer.newLine();
			}

			// writes data for the ones array
			for(int i = 0; i < onePieces.size(); i++){
				writer.write("1" + " " + onePieces.get(i).location.x + " " + onePieces.get(i).location.y + " " + onePieces.get(i).isItAlive);
				if(i != onePieces.size() - 1)
					writer.newLine();
			}

			writer.close();			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * If the save file is a new one, it sets up the entire board
	 */
	private static void setupNewBoard() 
	{
		// fills each arraylist for a new game
		for (int i = 0; i < 12; i++) {
			zeroPieces.add(new CheckerPiece(0));
			onePieces.add(new CheckerPiece(1));
		}

		int k = 0;
		// sets locations for the zero array
		for (int i = 1; i < 6; i+=2)
		{

			if (i % 4 == 1)
			{
				for(int j = 2; j < 31; j+=8)
				{

					zeroPieces.get(k).location = new Coordinates(j, i);
					k++;
				}
			}

			if (i % 4 == 3)
			{
				for(int j = 6; j < 31; j+=8)
				{
					zeroPieces.get(k).location = new Coordinates(j, i);
					k++;
				}
			}
		}

		k = 0;
		// sets locations for one array
		for (int i = 15; i > 9; i-=2)
		{

			if (i % 4 == 1)
			{
				for(int j = 2; j < 31; j+=8){
					onePieces.get(k).location = new Coordinates(j, i);
					k++;
				}
			}

			if (i % 4 == 3)
			{
				for(int j = 6; j < 31; j+=8)
				{
					onePieces.get(k).location = new Coordinates(j, i);
					k++;
				}
			}
		}

		// puts coordinate markers
		int z = 0;
		for(int i = 2; i < 34; i+=4)
		{
			board[i][17] = String.valueOf(z).charAt(0);
			z++;
		}

		for(int i = 0; i < 34; i++)
		{
			if((i - 2) % 4 != 0)
			{
				board[i][17] = ' ';
			}
		}

		fillInBoardArray();
		showBoard();

	}

	/**
	 * Fills in the entir 2D array
	 */

	private static void fillInBoardArray() 
	{
		// uses coordinate values to populate board array
		for(int i = 0; i < 17; i++)
		{
			if (i % 2 == 0)
			{
				for(int j = 0; j < 34; j++)
				{

					int rem = j % 4;

					if(rem == 0)
					{
						board[j][i] = '+';
					}

					else
					{
						board[j][i] = '-';
					}
				}
			}

			else
			{
				for(int j = 0; j < 34; j++)

				{
					if ((j + 2) % 4 == 0)
					{
						board[j][i] = spaceOrPiece(j, i);
					}
					else if (j % 4 == 0) 
					{
						board[j][i] = '|';
					}

					else if(j == 33)
					{
						board[j][i] = Integer.toString((-i + 15)/2).charAt(0);
					}

					else
					{
						board[j][i] = ' ';
					}
				}
			}
		}
	}

	/**
	 * Checks if a space or a piece needs to be put in the given coordinates
	 * @param x x coordinate to check
	 * @param y y coordinate to check
	 * @return the correct character
	 */
	private static char spaceOrPiece(int x, int y) 
	{

		// uses locations to determine where pieces are supposed to go
		for (int i = 0; i < 12; i++) 
		{

			if(zeroPieces.get(i).location.x == x && zeroPieces.get(i).location.y == y)
			{
				return '0';
			}

			if(onePieces.get(i).location.x == x && onePieces.get(i).location.y == y)
			{
				return '1';
			}
		}

		return ' ';

	}

	/**
	 * displays board
	 */

	public static void showBoard() 
	{
		// prints the 2D array
		System.out.print("\n");
		for (int i = 0; i < 18; i++)
		{
			for(int j = 0; j < 34; j++)
			{
				System.out.print(board[j][i]);
			}
			System.out.print("\n");
		}

	}



	public static boolean movePiece(Coordinates initialLoc, Coordinates finalLoc, boolean isItPlayerZeroTurn) 
	{

		boolean returnValue = true;

		// validates code and gets it to move
		if (validate(initialLoc, finalLoc, isItPlayerZeroTurn)) 
		{
			if (isItPlayerZeroTurn) 
			{
				if (board[initialLoc.x * 4 + 2][-initialLoc.y * 2 + 15] == '0') {
					returnValue = doesMoving(initialLoc, finalLoc, isItPlayerZeroTurn);

				}
			}

			else {
				if (board[initialLoc.x * 4 + 2][-initialLoc.y * 2 + 15] == '1') {

					returnValue = doesMoving(initialLoc, finalLoc, isItPlayerZeroTurn);

				}
			}

			updateLocation(initialLoc, finalLoc, isItPlayerZeroTurn);
			showBoard();
			return returnValue;
		}

		else {
			System.out.println("Invalid coordinates");
		} 

		return true;
	}

	/**
	 * Checks if move is valid
	 * @param initialLoc initial location
	 * @param finalLoc final location to move the piece to
	 * @param isItPlayerZeroTurn who's turn it is
	 * @return if the coordinates are valid
	 */
	private static boolean validate(Coordinates initialLoc, Coordinates finalLoc, boolean isItPlayerZeroTurn) 
	{

		if(initialLoc.x < 8 && initialLoc.y < 8 && finalLoc.x < 8 && finalLoc.y < 8 && (initialLoc.x - finalLoc.x == 1 || 
				initialLoc.x - finalLoc.x == -1) && (initialLoc.y - finalLoc.y == 1 || initialLoc.y - finalLoc.y == -1))
		{

			return true;
		}

		else if(checkJumpAvailable(initialLoc, finalLoc, isItPlayerZeroTurn))
		{
			return true;
		}


		else
		{
			return false;
		}
	}

	/**
	 * finds the checker piece to make disappear
	 * @param pieceToMove the player to be overtaken
	 * @param disappear the coordinates of the piece that will disappear
	 * @return
	 */
	private static CheckerPiece findCheckerPieceByCoord(char pieceToMove, Coordinates disappear) {
		ArrayList<CheckerPiece> pieceArray = (pieceToMove == '0') ? zeroPieces : onePieces;

		// loops around the arraylist to find the piece
		for (int i = 0; i < pieceArray.size(); i++){
			if(pieceArray.get(i).location.equals(disappear))
				return pieceArray.get(i);
		}

		return null;
	}

	/**
	 * Actually does the moving
	 * @param initialLoc initial location
	 * @param finalLoc final location to move the piece to
	 * @param isItPlayerZeroTurn who's turn it is
	 * @return if the coordinates are valid
	 */
	private static boolean doesMoving(Coordinates initialLoc, Coordinates finalLoc, boolean isItPlayerZeroTurn) 
	{

		char pieceToMove = (isItPlayerZeroTurn) ? '0' : '1';
		//ArrayList<CheckerPiece> pieceArray = (pieceToMove == '0') ? zeroPieces : onePieces;
		board[initialLoc.arrX][initialLoc.arrY] = ' ';

		board[finalLoc.arrX][finalLoc.arrY] = pieceToMove;

		// checks if there is a jump available
		if (checkJumpAvailable(initialLoc, finalLoc, isItPlayerZeroTurn)) 
		{
			int adderY = (isItPlayerZeroTurn) ? -1 : 1;
			int adderX = (finalLoc.x - initialLoc.x > 0) ? 1 : -1;

			Coordinates disappear = new Coordinates(initialLoc.x + adderX, initialLoc.y + adderY);
			board[disappear.arrX][disappear.arrY] = ' ';

			findCheckerPieceByCoord(pieceToMove, disappear).isItAlive = false;

		}

		if(finalLoc.y == 0 || finalLoc.y == 7)
		{
			if(isItPlayerZeroTurn)
			{
				System.out.print("Game won by 0");
			}	
			else
			{
				System.out.println("Game won by 1");
			}
			return false;
		}
		return true;
	}

	/**
	 * @param initialLoc initial location
	 * @param finalLoc final location to move the piece to
	 * @param isItPlayerZeroTurn who's turn it is
	 * 
	 */
	private static void updateLocation(Coordinates initialLoc, Coordinates finalLoc, boolean isItPlayerZeroTurn) 
	{
		ArrayList<CheckerPiece> pieceArray = (isItPlayerZeroTurn) ? zeroPieces : onePieces;

		Coordinates initBoardCoord = new Coordinates(initialLoc.x * 4 + 2, -initialLoc.y * 2 + 15);
		Coordinates finBoardCoord = new Coordinates(finalLoc.x * 4 + 2, -finalLoc.y * 2 + 15);

		for (int i = 0; i < pieceArray.size(); i++) 
		{
			if(pieceArray.get(i).location.equals(initBoardCoord))
			{
				pieceArray.get(i).location = finBoardCoord;
			}
		}

		System.gc();
	}	
	/**
	 * @param initialLoc initial location
	 * @param finalLoc final location to move the piece to
	 * @param isItPlayerZeroTurn who's turn it is
	 * @return if there is a jump available
	 */
	private static boolean checkJumpAvailable(Coordinates initialLoc, Coordinates finalLoc, boolean isItPlayerZeroTurn) {

		char opposingChar = (isItPlayerZeroTurn) ? '1' : '0';

		if(initialLoc.x < 8 && initialLoc.y < 8 && finalLoc.x < 8 && finalLoc.y < 8 && (initialLoc.x - finalLoc.x == 2 || 
				initialLoc.x - finalLoc.x == -2) && (initialLoc.y - finalLoc.y == 2 || initialLoc.y - finalLoc.y == -2))
		{
			if(board[initialLoc.x + 1][initialLoc.y + 1] == opposingChar || board[initialLoc.x - 1][initialLoc.y - 1] == opposingChar){
				System.out.println("Enemy Detected.");
			}

			return true;
		}

		return false;

	}

	/**
	 * 
	 * @param initialLoc initial location
	 * @param isItPlayerZeroTurn who's turn it is
	 * @return  if the piece selected is correct
	 */
	public static boolean isCorrectPieceSelected(Coordinates initialLoc, boolean isItPlayerZeroTurn) {
		char playerPiece = (isItPlayerZeroTurn) ? '0' : '1';

		return board[initialLoc.arrX][initialLoc.arrY] == playerPiece;
	}
}
