import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void moveTest() {
        Table table = new Table();
        King king = new King(true);
        Tile fromTile = table.getTile(1, 1);
        fromTile.setPieceOnTile(king);
        ArrayList<Tile> lm = king.legalMoves(table, fromTile);
        Tile toTile = lm.get(0);
        king.Move(fromTile, toTile);
        assertEquals(null, fromTile.getPieceOnTile());
        assertEquals(king, toTile.getPieceOnTile());
    }
}