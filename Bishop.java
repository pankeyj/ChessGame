package game;

import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * The Bishop class that determines the validity of the piece's moves.
 * @author Jacob Pankey & Jack Dues
 ***************************************/
public class Bishop extends ChessPiece {

	/** Player that the piece belongs to */
	private Player owner;

	/****************************************
	 * Main method for Bishop
	 * @param args
	 ***************************************/
	public static void main(String[] args) {

	}

	/****************************************
	 * The following constructor creates a new Bishop piece.
	 * @param p
	 ***************************************/
	public Bishop(Player p) {
		super(p);
		owner = p;
	}

	/****************************************
	 * The following method returns the type of the piece.
	 * @return "BISHOP"
	 ***************************************/
	public String type() {
		return "BISHOP";
	}

	/****************************************
	 * The following method determines if the piece's move is valid.
	 * @param move, pieces
	 * @return True if the move is valid.
	 ***************************************/
	public boolean isValidMove(Move move, IChessPiece[][] pieces) {
		boolean legal = true;
		int rowMoves, columnMoves, row, column;
		final int ROWCHANGE, COLUMNCHANGE;

		rowMoves = move.fromRow - move.toRow;
		columnMoves = move.fromColumn - move.toColumn;
		//Sets up the row change for the piece.
		if (rowMoves < 0) {
			ROWCHANGE = 1;
		} else {
			ROWCHANGE = -1;
		}
		//Sets up the column change for the piece.
		if (columnMoves < 0) {
			COLUMNCHANGE = 1;
		} else {
			COLUMNCHANGE = -1;
		}

		rowMoves = Math.abs(rowMoves);
		columnMoves = Math.abs(columnMoves);
		row = move.fromRow;
		column = move.fromColumn;

		legal = super.isValidMove(move, pieces);

		if (columnMoves != rowMoves)
			legal = false;

		try {
			if (pieces[move.toRow][move.toColumn].player().equals(owner))
				legal = false;
		} catch (NullPointerException e) {

		}

		for (int i = 0; i < rowMoves - 1; i++) {
			row += ROWCHANGE;
			column += COLUMNCHANGE;
			try {
				if (pieces[row][column].player().equals(Player.BLACK)) {
					legal = false;
				} else if (pieces[row][column].player().equals(Player.WHITE))
					legal = false;
			} catch (NullPointerException e) {

			} catch (ArrayIndexOutOfBoundsException e1){
				
			}
		}

		return legal;

	}
}
