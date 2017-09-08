
public class CheckerPiece {
	public int color;
	public Coordinates location;
	public boolean isItAlive;
	public boolean isItKing;

	public CheckerPiece(int cColor){

		this.color = cColor;
		this.isItAlive = true;

	}

	public CheckerPiece(int cColor, Coordinates loc, boolean living){
		this.color = cColor;
		this.location = loc;
		isItAlive = living;
	}
}
