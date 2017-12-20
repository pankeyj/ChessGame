package game;

import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * This is the parent class to all chess pieces. It implements properties common
 * to all pieces
 * 
 * @author Jacob Pankey & Jack Dues
 * 
 ****************************************/
public abstract class ChessPiece implements IChessPiece {
	/** Owner of the piece */
	Player owner;

	/****************************************
	 * Constructor sets the owner
	 * 
	 * @param player
	 *            owner
	 */
	public ChessPiece(Player player) {
		owner = player;
	}

	/****************************************
	 * Tests all the rules for a valid move that are commmon to all pieces
	 ****************************************/
	@Override
	public boolean isValidMove(Move move, IChessPiece[][] pieces) {

		boolean legal = true;
		IChessPiece attempt, current;

		if (move.toRow > 7 || move.toRow < 0) {
			legal = false;
			throw new IndexOutOfBoundsException("Off Board");
		}
		if (move.toColumn > 7 || move.toColumn < 0) {
			legal = false;
			throw new IndexOutOfBoundsException("Off Board");
		}

		attempt = pieces[move.fromRow][move.fromColumn];
		current = this;

		try {
			if (!attempt.toString().equals((current).toString()))
				throw new IllegalArgumentException("Invalid Object");
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Out Of Bounds");
		}

		if (move.fromColumn == move.toColumn && move.fromRow == move.toRow)
			legal = false;
		try {
			if (pieces[move.toRow][move.toColumn].player().equals(owner))
				legal = false;
		} catch (NullPointerException e) {

		}
		return legal;
	}

	/****************************************
	 * returns the owner
	 * 
	 * @return owner
	 * 
	 ****************************************/
	@Override
	public Player player() {
		// TODO Auto-generated method stub
		return owner;
	}

	/****************************************
	 * returns the type of object
	 * 
	 * @return String type
	 */
	@Override
	public abstract String type();

}
