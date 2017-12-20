package game;

import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * The King class that determines the validity of the piece's moves.
 * @author Jacob Pankey & Jack Dues
 ***************************************/
public class King extends ChessPiece {

	/** Player that the piece belongs to */
	Player owner;

	/****************************************
	 * Main method for King
	 * @param args
	 ***************************************/
	public static void main(String[] args) {
		King t = new King(Player.WHITE);
	}
	/****************************************
	 * The following constructor creates a new King piece.
	 * @param p
	 ***************************************/
	public King(Player p) {
		super(p);
		owner = p;

	}

	/****************************************
	 * The following method returns the type of the piece.
	 * @return "KING"
	 ***************************************/
	@Override
	public String type() {
		return "KING";
	}

	/****************************************
	 * The following method determines if the piece's move is valid.
	 * @param move, pieces
	 * @return True if the move is valid.
	 ***************************************/
	public boolean isValidMove(Move move, IChessPiece[][] pieces) {
		boolean legal = true;

		legal = super.isValidMove(move, pieces);
		//Ensures that the king can only move one row at most.
		if (Math.abs(move.fromColumn - move.toColumn) > 1)
			legal = false;
		//Ensures that the king can only move one column at most.
		if (Math.abs(move.fromRow - move.toRow) > 1)
			legal = false;

		try {
			if (pieces[move.toRow][move.toColumn].player().equals(owner))
				legal = false;
		} catch (NullPointerException e) {

		}
		return legal;

	}
}
