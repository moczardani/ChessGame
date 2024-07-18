import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SerializableChessTest {

    @Test
    void tileOfKingTest() {
        SerializableChess sc = new SerializableChess();
        King wKing = new King(true);
        King bKing = new King(false);
        Tile wTile = sc.getTable().getTile(5, 3);
        Tile bTile = sc.getTable().getTile(4, 4);
        wTile.setPieceOnTile(wKing);
        bTile.setPieceOnTile(bKing);
        assertEquals(wTile, sc.tileOfKing(true));
        assertEquals(bTile, sc.tileOfKing(false));
    }

    @Test
    void isChessTest() {
        SerializableChess sc = new SerializableChess();
        King king = new King(true);
        Tile kingTile = sc.getTable().getTile(5, 5);
        kingTile.setPieceOnTile(king);
        Pawn pawn = new Pawn(false);
        Tile pawnTile = sc.getTable().getTile(6, 7);
        pawnTile.setPieceOnTile(pawn);
        assertEquals(false, sc.isChess(kingTile));
        pawn.Move(pawnTile, sc.getTable().getTile(6, 6));
        assertEquals(true, sc.isChess(kingTile));
    }

    @Test
    void canMoveTest() {
        SerializableChess sc = new SerializableChess();
        King king = new King(true);
        Tile kingTile = sc.getTable().getTile(0, 0);
        kingTile.setPieceOnTile(king);
        Rook rook1 = new Rook(false);
        Rook rook2 = new Rook(false);
        sc.getTable().getTile(0, 2).setPieceOnTile(rook1);
        sc.getTable().getTile(2, 0).setPieceOnTile(rook2);
        assertEquals(true, sc.canMove(true));
        Bishop bishop = new Bishop(false);
        sc.getTable().getTile(2, 2).setPieceOnTile(bishop);
        assertEquals(false, sc.canMove(true));
    }
}