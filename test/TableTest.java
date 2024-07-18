import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void clearTableTest() {
        Table table = new Table();
        table.placePieces();
        table.clearTable();
        boolean notNull = false;
        for(int i = 0; i < 8 && !notNull; i++) {
            for(int j = 0; j < 8; j++) {
                if(table.getTile(i, j).getPieceOnTile() != null) {
                    notNull = true;
                    break;
                }
            }
        }
        assertEquals(false, notNull);
    }
}