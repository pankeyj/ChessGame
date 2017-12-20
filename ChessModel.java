package game;

import java.awt.Point;

import gvprojects.chess.model.IChessModel;
import gvprojects.chess.model.IChessPiece;
import gvprojects.chess.model.Move;
import gvprojects.chess.model.Player;

/****************************************
 * This class serves as the game engine to control the flow of the game and
 * interacts with the UI
 * 
 * @author Jacob Pankey & Jack Dues
 ****************************************/

public class ChessModel implements IChessModel {
	/** Is Check and Mate? */
	public boolean mated;
	/** Stores pieces on the board */
	private IChessPiece[][] board;
	/** Player who is current, winner and in check */
	private Player current, winner, inCheck;
	/** Used to handle isMate method */
	private boolean isComplete, doubleCheck, hypotheticalCheck = false;
	/** Move that could capture king */
	private Move checkMove;

	/****************************************
	 * Main Method instantiates test cases
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		IChessPiece wk = new King(Player.WHITE);
		IChessPiece bk = new King(Player.BLACK);
		IChessPiece bb = new Bishop(Player.BLACK);
		ChessModel game = new ChessModel(new int[] { 3, 3, 4, 5, 0, 3 },
				new IChessPiece[] { wk, bk, bb });
		game.inCheck();
		System.out.println("In Check = " + game.inCheck());

	}

	/****************************************
	 * returns the board to the UI
	 * 
	 * @return board
	 ****************************************/
	public IChessPiece[][] getBoard() {
		return board;
	}

	/****************************************
	 * Sets the current Player
	 * 
	 * @param p
	 ****************************************/
	public void setCurrent(Player p) {
		current = p;
	}

	/****************************************
	 * Returns the winner of the game
	 * 
	 * @return winner
	 ****************************************/
	public Player getWinner() {
		return winner;
	}

	/****************************************
	 * Constructor sets up proper starting positions for chess pieces
	 ****************************************/
	public ChessModel() {
		board = new IChessPiece[numRows()][numColumns()];
		current = Player.WHITE;
		doubleCheck = false;
		hypotheticalCheck = false;

		board[0][0] = new Rook(Player.BLACK);
		board[0][1] = new Knight(Player.BLACK);
		board[0][2] = new Bishop(Player.BLACK);
		board[0][3] = new Queen(Player.BLACK);
		board[0][4] = new King(Player.BLACK);
		board[0][5] = new Bishop(Player.BLACK);
		board[0][6] = new Knight(Player.BLACK);
		board[0][7] = new Rook(Player.BLACK);
		for (int i = 0; i < board.length; i++) {
			board[1][i] = new Pawn(Player.BLACK);
		}

		board[7][0] = new Rook(Player.WHITE);
		board[7][1] = new Knight(Player.WHITE);
		board[7][2] = new Bishop(Player.WHITE);
		board[7][3] = new Queen(Player.WHITE);
		board[7][4] = new King(Player.WHITE);
		board[7][5] = new Bishop(Player.WHITE);
		board[7][6] = new Knight(Player.WHITE);
		board[7][7] = new Rook(Player.WHITE);
		for (int i = 0; i < board.length; i++) {
			board[6][i] = new Pawn(Player.WHITE);
		}
	}

	/****************************************
	 * Constructor used for test cases
	 * 
	 * @param positions
	 *            positions of chess pieces
	 * @param pieces
	 *            types of chess pieces
	 */
	public ChessModel(int[] positions, IChessPiece[] pieces) {

		board = new IChessPiece[numRows()][numColumns()];
		for (int i = 0; i < pieces.length; i++) {
			board[positions[2 * i]][positions[2 * i + 1]] = pieces[i];
		}
		current = Player.WHITE;
		;
		System.out.println(board[3][3]);
	}

	/****************************************
	 * returns the current Player
	 ****************************************/
	@Override
	public Player currentPlayer() {
		return current;
	}

	/****************************************
	 * Determines if the current player is in check
	 ****************************************/
	@Override
	public boolean inCheck() {
		boolean inCheck;
		int kingRow, kingCol;
		Move move;

		kingRow = locateKingRow();
		kingCol = locateKingCol();
		inCheck = false;

		for (int i = 0; i < numRows(); i++) {
			for (int k = 0; k < numColumns(); k++) {
				try {
					// Determines if any piece is allowed to capture the king
					move = new Move(i, k, kingRow, kingCol);
					if (pieceAt(i, k).isValidMove(move, board)) {
						// Prevents endless loop
						if (!hypotheticalCheck) {
							isMate();
							// Double Check asserts true for unable to block
							if (inCheck)
								doubleCheck = true;
						}
						inCheck = true;
						checkMove = move;
					}
				} catch (NullPointerException e1) {
				} catch (IllegalArgumentException e2) {
				} catch (ArrayIndexOutOfBoundsException e3) {
				}
			}
		}
		return inCheck;
	}

	/****************************************
	 * Determines if the king can move out of check EXPERIENCING ERRORS STILL
	 ****************************************/
	public void isMate() {
		boolean block = true;
		boolean isMate = true;
		int kingRow, kingCol;
		Move move;

		hypotheticalCheck = true;
		doubleCheck = false;
		kingRow = locateKingRow();
		kingCol = locateKingCol();

		// Cycles through places around the king
		for (int i = kingRow - 1; i <= kingRow + 1; i++) {
			for (int k = kingCol - 1; k <= kingCol + 1; k++) {
				move = new Move(kingRow, kingCol, i, k);
				if (i < 0 || i > 7)
					continue;
				if (k < 0 || k > 7)
					continue;
				try {

					if (pieceAt(kingRow, kingCol).isValidMove(move, board)) {
						// If king can capture piece that has king in check
						if (move.toRow == checkMove.fromRow
								&& move.toColumn == checkMove.fromColumn) {
							isMate = false;

							// Skips if piece isnt moving
						} else if (i == move.fromRow && k == move.fromColumn) {
							continue;
						} else {
							move(move);
							current = current.next();
							// Checks to see if the piece is in check after king
							// has made a hypothetical move
							if (!inCheck()) {
								isMate = false;
								board[move.fromRow][move.fromColumn] = board[move.toRow][move.toColumn];
								board[move.toRow][move.toColumn] = null;
							}
							if (!isMate)
								break;
						}
						// Returns pieces
						board[move.fromRow][move.fromColumn] = board[move.toRow][move.toColumn];
						board[move.toRow][move.toColumn] = null;
					}
				} catch (IllegalArgumentException e) {

				}
			}
			if (!isMate)
				break;
		}

		if (isMate)
			block = unableToBlock();
		if (!mated)
			mated = isMate && block;
		hypotheticalCheck = false;
	}

	/****************************************
	 * Determines if the path to capture the king can be blocked
	 * 
	 * @return true if checkmate
	 ****************************************/
	public boolean unableToBlock() {
		Point[] path;
		boolean isMate = true;
		int row, column, rowMoves, columnMoves, totalMoves, rowChange, columnChange;
		Move move;

		row = checkMove.fromRow;
		column = checkMove.fromColumn;

		if (pieceAt(row, column).type().equals("KNIGHT")) {
			return true;
		} else if (pieceAt(row, column).type().equals("PAWN")) {
			return true;
		} else if (pieceAt(row, column).type().equals("KING")) {
			return true;
		} else if (doubleCheck) {
			return true;
		}

		rowMoves = (checkMove.toRow - checkMove.fromRow);
		columnMoves = (checkMove.toColumn - checkMove.fromColumn);

		// Sets up the row change for the check move.
		if (rowMoves == 0) {
			rowChange = 0;
		} else if (rowMoves < 0) {
			rowChange = -1;
		} else {
			rowChange = 1;
		}
		// Sets up the column change for the check move.
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

		path = new Point[totalMoves];

		// path stores the points that the piece must follow
		for (int i = 0; i < totalMoves - 1; i++) {
			row += rowChange;
			column += columnChange;
			path[i] = new Point(row, column);
		}

		// Checks to see if any of the current players pieces can move to path
		for (int i = 0; i < numRows(); i++) {
			for (int k = 0; k < numColumns(); k++) {
				try {
					if (pieceAt(i, k).player().equals(current)) {
						for (int j = 0; j < path.length; j++) {
							move = new Move(i, k, path[i].x, path[i].y);
							if (pieceAt(i, k).isValidMove(move, board)) {
								isMate = false;
							}
						}
					}
				} catch (NullPointerException e) {
				} catch (IllegalArgumentException e2) {
				} catch (ArrayIndexOutOfBoundsException e3) {
				}
			}
		}

		return isMate;
	}

	/****************************************
	 * returns true if game is over
	 ****************************************/
	@Override
	public boolean isComplete() {
		return isComplete;
	}

	/****************************************
	 * Determines if the move is a valid move
	 * 
	 * @param move
	 *            move to test
	 ****************************************/
	@Override
	public boolean isValidMove(Move move) {
		boolean legal;
		try {
			if (board[move.fromRow][move.fromColumn].player().equals(
					current.next()))
				throw new IllegalArgumentException("Moving Wrong Player");

			if (board[move.fromRow][move.fromColumn].isValidMove(move, board)) {
				legal = true;
			} else {
				legal = false;
			}

		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Invalid FROM location");
		}

		return legal;
	}

	/****************************************
	 * This method mutates the board by moving the piece in the model. Checks to
	 * see if it is valid first
	 * 
	 * @param move
	 *            move to be made
	 ****************************************/
	@Override
	public void move(Move move) {

		if (!isValidMove(move))
			throw new IllegalArgumentException("Invalid Move");

		try {
			if (board[move.toRow][move.toColumn].type().equals("KING")) {
				isComplete = true;
				winner = current;
			}
		} catch (NullPointerException e) {

		}
		board[move.toRow][move.toColumn] = board[move.fromRow][move.fromColumn];
		board[move.fromRow][move.fromColumn] = null;
		current = current.next();

	}

	/****************************************
	 * return number of columns
	 ****************************************/
	@Override
	public int numColumns() {
		return 8;
	}

	/****************************************
	 * returns number of rows
	 ****************************************/
	@Override
	public int numRows() {
		return 8;
	}

	/****************************************
	 * Returns the object at the specified index
	 * 
	 * @param row
	 *            row of piece
	 * @param col
	 *            column of the piece
	 */
	@Override
	public IChessPiece pieceAt(int row, int col) {
		return board[row][col];
	}

	/****************************************
	 * Locates the king row
	 * 
	 * @return returns the row of the king
	 ****************************************/
	private int locateKingRow() {
		int kingRow;
		Player opponent;

		kingRow = 0;
		opponent = current.next();

		for (int i = 0; i < numRows(); i++) {
			for (int k = 0; k < numColumns(); k++) {
				try {
					if (pieceAt(i, k).type().equals("KING")) {
						if (pieceAt(i, k).player().equals(current)) {
							kingRow = i;
						}
					}
				} catch (NullPointerException e) {

				}
			}
		}
		return kingRow;
	}

	/****************************************
	 * Locates the column of the opponents king
	 * 
	 * @return returns the column
	 ****************************************/
	private int locateKingCol() {
		int kingCol;
		Player opponent;

		kingCol = 0;
		opponent = current.next();

		for (int i = 0; i < numRows(); i++) {
			for (int k = 0; k < numColumns(); k++) {
				try {
					if (pieceAt(i, k).type().equals("KING")) {
						if (pieceAt(i, k).player().equals(current)) {
							kingCol = k;
						}
					}
				} catch (NullPointerException e) {

				}
			}
		}
		return kingCol;

	}

}
