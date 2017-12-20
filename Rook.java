package game;
import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * The Rook class that determines the
 *  validity of the piece's moves.
 * @author Jacob Pankey & Jack Dues
 ***************************************/
public class Rook extends ChessPiece {
	
	/** Player that the piece belongs to */
	Player owner;

	/****************************************
	 * Main method for Rook
	 * @param args
	 ***************************************/
	public static void main(String[] args) {

	}
	
	/****************************************
	 * The following constructor creates a
	 *  new Rook piece.
	 * @param p
	 ***************************************/
	public Rook(Player p) {
		super(p);
		owner = p;
	}

	/****************************************
	 * The following method returns the type
	 *  of the piece.
	 * @return "ROOK"
	 ***************************************/
	public String type() {
		return "ROOK";

	}

	/****************************************
	 * The following method determines if the
	 *  piece's move is valid.
	 * @param move, pieces
	 * @return True if the move is valid.
	 ***************************************/
	public boolean isValidMove(Move move, IChessPiece[][] pieces) {
		int row, column, rowChange, columnChange, totalMoves, rowMoves, colMoves;
		IChessPiece correct, attempt;
		boolean legal = true;
		correct = pieces[move.fromRow][move.fromColumn];
		attempt = this;
		row = move.fromRow;
		column = move.fromColumn;
		//Sets up the row change for the piece.
		if (move.fromRow - move.toRow == 0) {
			rowChange = 0;

		} else if (move.fromRow < move.toRow) {
			rowChange = 1;
		} else {
			rowChange = -1;
		}
		//Sets up the column change for the piece.
		if (move.fromColumn - move.toColumn == 0) {
			columnChange = 0;
		} else if (move.fromColumn < move.toColumn) {
			columnChange = 1;
		} else {
			columnChange = -1;
		}
		
		rowMoves = Math.abs(move.fromRow - move.toRow);
		colMoves = Math.abs(move.fromColumn - move.toColumn);
	
		if(rowMoves > colMoves)
			totalMoves = rowMoves;
		else
			totalMoves = colMoves;
			
			if(!super.isValidMove(move, pieces))
				legal = false;
			//Piece can only move along a column or row, not both.
			if (columnChange != 0 && rowChange != 0)
				throw new IllegalArgumentException("No Movement");
			
			if(!correct.toString().equals(attempt.toString()))
					throw new IllegalArgumentException("Invalid Object");

			try {
				if (pieces[move.toRow][move.toColumn].player().equals(owner))
					legal = false;
			} catch (NullPointerException e) {
				System.out.println("Null Location");
			}

			for (int i = 0; i < totalMoves - 1; i++) {
				row += rowChange;
				column += columnChange;
				
				try {
					if (!pieces[row][column].player().equals(null))
						legal = false;
						throw new IllegalArgumentException("Rooks don't jump silly");
				} catch (NullPointerException e) {
				}

			}

	

		return legal;

	}
}
