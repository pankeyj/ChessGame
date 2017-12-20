package game;

import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * The Queen class that determines the validity of the piece's moves.
 * 
 * @author Jacob Pankey & Jack Dues
 ***************************************/
public class Queen extends ChessPiece {

	/** Player that the piece belongs to */
	private Player owner;

	/****************************************
	 * The following method returns the type of the piece.
	 * 
	 * @return "QUEEN"
	 ***************************************/
	@Override
	public String type() {
		return "QUEEN";
	}

	/****************************************
	 * The following constructor creates a new Queen piece.
	 * 
	 * @param p
	 ***************************************/
	public Queen(Player p) {
		super(p);
		owner = p;
	}

	/****************************************
	 * The following method determines if the piece's move is valid.
	 * 
	 * @param move
	 *            , pieces
	 * @return True if the move is valid.
	 ***************************************/
	public boolean isValidMove(Move move, IChessPiece[][] pieces)
			throws ArrayIndexOutOfBoundsException {

		boolean legal, isDiaganol;
		int rowMoves, columnMoves, row, column, rowChange, columnChange, totalMoves;
		legal = true;
		isDiaganol = false;

		legal = super.isValidMove(move, pieces);

		row = move.fromRow;
		column = move.fromColumn;
		rowMoves = (move.toRow - move.fromRow);
		columnMoves = (move.toColumn - move.fromColumn);

		if (rowMoves != 0 && columnMoves != 0) {
			if (Math.abs(rowMoves) != Math.abs(columnMoves))
				legal = false;
		}

		// Sets up the row change for the piece.
		if (rowMoves == 0) {
			rowChange = 0;
		} else if (rowMoves < 0) {
			rowChange = -1;
		} else {
			rowChange = 1;
		}
		// Sets up the column change for the piece.
		if (columnMoves == 0) {
			columnChange = 0;
		} else if (columnMoves < 0) {
			columnChange = -1;
		} else {
			columnChange = 1;
		}
		// Determines the total moves the piece has made.
		if (Math.abs(rowMoves) > Math.abs(columnMoves)) {
			totalMoves = Math.abs(rowMoves);
		} else {
			totalMoves = Math.abs(columnMoves);
		}

		try {
			if (pieces[move.toRow][move.toColumn].player().equals(owner))
				legal = false;
		} catch (NullPointerException e) {

		}

		for (int i = 0; i < totalMoves - 1; i++) {

			row += rowChange;
			column += columnChange;

			try {
				if (pieces[row][column].player().equals(Player.BLACK)) {
					legal = false;
				} else if (pieces[row][column].player().equals(Player.WHITE)) {
					legal = false;
				}
			} catch (NullPointerException e) {

			}

		}

		return legal;
	}
}
