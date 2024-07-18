import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    @Test
    void legalMovesTest() {
        Table table = new Table();
        King king = new King(true);
        Pawn wPawn = new Pawn(true);
        Pawn bPawn = new Pawn(false);
        table.getTile(0,2).setPieceOnTile(wPawn);
        table.getTile(2,0).setPieceOnTile(bPawn);
        table.getTile(1,1).setPieceOnTile(king);

        boolean ok = true;
        ArrayList<Tile> lm = king.legalMoves(table, table.getTile(1, 1));
        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(table.getTile(0, 0));
        expected.add(table.getTile(1, 0));
        expected.add(table.getTile(2, 0));
        expected.add(table.getTile(2, 1));
        expected.add(table.getTile(2, 2));
        expected.add(table.getTile(1, 2));
        expected.add(table.getTile(0, 1));
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