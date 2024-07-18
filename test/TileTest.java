import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    @Test
    void isWhiteTest() {
        Tile tile = new Tile(5, 2);
        assertEquals(true, tile.isWhite());
    }
}