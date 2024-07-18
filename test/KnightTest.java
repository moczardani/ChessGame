import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void legalMovesTest() {
        Table table = new Table();
        Knight knight = new Knight(true);
        Pawn wPawn = new Pawn(true);
        Pawn bPawn = new Pawn(false);
        table.getTile(2,1).setPieceOnTile(wPawn);
        table.getTile(1,2).setPieceOnTile(bPawn);
        table.getTile(3,3).setPieceOnTile(knight);

        boolean ok = true;
        ArrayList<Tile> lm = knight.legalMoves(table, table.getTile(3, 3));
        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(table.getTile(4, 1));
        expected.add(table.getTile(5, 2));
        expected.add(table.getTile(5, 4));
        expected.add(table.getTile(4, 5));
        expected.add(table.getTile(2, 5));
        expected.add(table.getTile(1, 4));
        expected.add(table.getTile(1, 2));
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