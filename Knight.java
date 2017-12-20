package game;

import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * The Knight class that determines the validity of the piece's moves.
 * @author Jacob Pankey & Jack Dues
 ***************************************/
public class Knight extends ChessPiece {
	
	/** Player that the piece belongs to */
	Player owner;

	/****************************************
	 * The following constructor creates a new Knight piece.
	 * @param p
	 ***************************************/
	public Knight(Player p) {
		super(p);
		owner = p;
	}

	/****************************************
	 * The following method returns the type of the piece.
	 * @return "KNIGHT"
	 ***************************************/
	@Override
	public String type() {
		// TODO Auto-generated method stub
		return "KNIGHT";
	}

	/****************************************
	 * The following method determines if the piece's move is valid.
	 * @param move, pieces
	 * @return True if the move is valid.
	 ***************************************/
	public boolean isValidMove(Move move, IChessPiece[][] pieces) {
		boolean legal = true;
		int rowMoves, colMoves;

		legal = super.isValidMove(move, pieces);
		rowMoves = move.fromRow - move.toRow;
		colMoves = move.fromColumn - move.toColumn;
		//When the piece moves two rows, it can only move one column.
		if (Math.abs(rowMoves) == 2){
			if (Math.abs(colMoves) != 1)
				legal = false;
		//When the piece moves two columns, it can only move one row.
		}else if(Math.abs(colMoves) == 2){
			if(Math.abs(rowMoves) != 1)
				legal = false;
		}else{
			legal = false;
		}

		return legal;
	}

}
