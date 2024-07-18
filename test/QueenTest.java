import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    @Test
    void legalMovesTest() {
        Table table = new Table();
        Queen queen = new Queen(true);
        Pawn wPawn = new Pawn(true);
        Pawn bPawn = new Pawn(false);
        table.getTile(1,3).setPieceOnTile(wPawn);
        table.getTile(3,3).setPieceOnTile(bPawn);
        table.getTile(1,1).setPieceOnTile(queen);

        boolean ok = true;
        ArrayList<Tile> lm = queen.legalMoves(table, table.getTile(1, 1));
        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(table.getTile(0, 0));
        expected.add(table.getTile(1, 0));
        expected.add(table.getTile(2, 0));
        expected.add(table.getTile(2, 1));
        expected.add(table.getTile(3, 1));
        expected.add(table.getTile(4, 1));
        expected.add(table.getTile(5, 1));
        expected.add(table.getTile(6, 1));
        expected.add(table.getTile(7, 1));
        expected.add(table.getTile(2, 2));
        expected.add(table.getTile(3, 3));
        expected.add(table.getTile(1, 2));
        expected.add(table.getTile(0, 2));
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