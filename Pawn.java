package game;

import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * The pawn class defines the properties of all pawn pieces
 * 
 * @author Jacob Pankey & Jack Dues
 * 
 ****************************************/
public class Pawn extends ChessPiece {
	/** Owner of the piece */
	private Player owner;
	/** Can move two spaces */
	private boolean isFirstMove;

	public static void main(String[] args) {

	}

	/****************************************
	 * Constructor sets owner and first move to true
	 * 
	 * @param p
	 *            owner
	 ****************************************/
	public Pawn(Player p) {
		super(p);
		owner = p;
		isFirstMove = true;
	}

	/****************************************
	 * Returns the type of piece
	 * 
	 * @return String type
	 ****************************************/
	public String type() {
		return "Pawn";
	}

	/****************************************
	 * Determines if the move is valid for a pawn
	 ****************************************/
	public boolean isValidMove(Move move, IChessPiece[][] pieces) {
		boolean legal;
		legal = true;
		int distance;
		int betweenRow;
		betweenRow = (move.toRow + move.fromRow) / 2;
		distance = Math.abs(move.toRow - move.fromRow);

		legal = super.isValidMove(move, pieces);

		// Cannot move backwards
		if (owner.equals(Player.BLACK)) {
			if (move.toRow - move.fromRow < 0)
				legal = false;
		} else if (owner.equals(Player.WHITE)) {
			if (move.toRow - move.fromRow > 0)
				legal = false;
		} else {
			legal = false;
			throw new IllegalArgumentException("Invalid Player");
		}

		if (move.fromRow == move.toRow)
			legal = false;

		if (move.toColumn == move.fromColumn) {

			try {
				if (pieces[move.toRow][move.toColumn].player().equals(
						Player.BLACK)) {
					legal = false;
				} else if (pieces[move.toRow][move.toColumn].player().equals(
						Player.WHITE))
					legal = false;
			} catch (NullPointerException e) {

			}
			if (distance == 2 && !isFirstMove) {
				legal = false;
			} else if (distance == 2) {
				try {
					if (pieces[betweenRow][move.toColumn].player().equals(
							Player.WHITE)) {
						legal = false;
					} else if (pieces[betweenRow][move.toColumn].player()
							.equals(Player.BLACK))
						legal = false;
				} catch (NullPointerException e) {

				}
			} else if (distance > 1) {
				legal = false;
			}
		} else {
			if (distance != 1)
				legal = false;
			if (Math.abs(move.fromColumn - move.toColumn) != 1)
				legal = false;
			try {
				if (pieces[move.toRow][move.toColumn].player().equals(owner))
					legal = false;
			} catch (NullPointerException e) {
				legal = false;

			}

		}
		if (legal)
			isFirstMove = false;

		return legal;
	}

}
