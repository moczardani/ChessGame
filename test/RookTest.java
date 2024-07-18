import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @Test
    void legalMoves() {
        Table table = new Table();
        Rook rook = new Rook(true);
        Pawn wPawn = new Pawn(true);
        Pawn bPawn = new Pawn(false);
        table.getTile(1,3).setPieceOnTile(wPawn);
        table.getTile(3,1).setPieceOnTile(bPawn);
        table.getTile(1,1).setPieceOnTile(rook);

        boolean ok = true;
        ArrayList<Tile> lm = rook.legalMoves(table, table.getTile(1, 1));
        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(table.getTile(1, 0));
        expected.add(table.getTile(2, 1));
        expected.add(table.getTile(3, 1));
        expected.add(table.getTile(0, 1));
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