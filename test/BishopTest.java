import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void legalMovesTest() {
        Table table = new Table();
        Bishop bishop = new Bishop(true);
        Pawn wPawn = new Pawn(true);
        Pawn bPawn = new Pawn(false);
        table.getTile(5,5).setPieceOnTile(wPawn);
        table.getTile(5,1).setPieceOnTile(bPawn);
        table.getTile(3,3).setPieceOnTile(bishop);

        boolean ok = true;
        ArrayList<Tile> lm = bishop.legalMoves(table, table.getTile(3, 3));
        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(table.getTile(0, 0));
        expected.add(table.getTile(1, 1));
        expected.add(table.getTile(2, 2));
        expected.add(table.getTile(4, 4));
        expected.add(table.getTile(0, 6));
        expected.add(table.getTile(1, 5));
        expected.add(table.getTile(2, 4));
        expected.add(table.getTile(4, 2));
        expected.add(table.getTile(5, 1));
        ok = lm.size() == expected.size();
        if(ok) {
            for(Tile t : lm) {
                if(!expected.contains(t)) {
                    ok = false;
                }
            }
        }
        assertEquals(true, ok);

    }
}