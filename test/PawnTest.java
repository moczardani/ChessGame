import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void legalMovesTest() {
        Table table = new Table();
        Pawn wPawn1 = new Pawn(true);
        Pawn wPawn2 = new Pawn(true);
        Pawn bPawn1 = new Pawn(false);
        Pawn bPawn2 = new Pawn(false);
        table.getTile(1,1).setPieceOnTile(wPawn1);
        table.getTile(4,0).setPieceOnTile(wPawn2);
        table.getTile(2,2).setPieceOnTile(bPawn1);
        table.getTile(4,1).setPieceOnTile(bPawn2);

        boolean ok = true;
        ArrayList<Tile> lm = wPawn1.legalMoves(table, table.getTile(1, 1));
        lm.addAll(wPawn2.legalMoves(table, table.getTile(4, 0)));
        ArrayList<Tile> expected = new ArrayList<>();
        expected.add(table.getTile(1, 3));
        expected.add(table.getTile(1, 2));
        expected.add(table.getTile(2, 2));
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