import java.io.Serializable;

/**
 * A sakktáblát leíró osztály
 * <p>A sakkmezők kétdimenziós tömbjét tartalmazza.</p>
 */
public class Table implements Serializable {
    private final Tile[][] tiles;

    /**
     * Table konstruktor
     * Létrehozza a sakkmezőkből álló 8x8-as táblát.
     */
    public Table() {
        tiles = new Tile[8][8];
        initTable();
    }

    /**
     * Inicializálja a tábla mezejeit.
     */
    public void initTable() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j] = new Tile(i, j);
            }
        }
    }

    /**
     * Üres tábla inicializálása.
     */
    public void clearTable() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[i][j].setPieceOnTile(null);
            }
        }
    }

    /**
     * Elhelyezi a bábukat a játék kezdőállapotának megfelelően.
     */
    public void placePieces() {
        clearTable();
        // First line for true side
        tiles[0][0].setPieceOnTile(new Rook(true));
        tiles[1][0].setPieceOnTile(new Knight(true));
        tiles[2][0].setPieceOnTile(new Bishop(true));
        tiles[3][0].setPieceOnTile(new Queen(true));
        tiles[4][0].setPieceOnTile(new King(true));
        tiles[5][0].setPieceOnTile(new Bishop(true));
        tiles[6][0].setPieceOnTile(new Knight(true));
        tiles[7][0].setPieceOnTile(new Rook(true));

        // First line for false side
        tiles[0][7].setPieceOnTile(new Rook(false));
        tiles[1][7].setPieceOnTile(new Knight(false));
        tiles[2][7].setPieceOnTile(new Bishop(false));
        tiles[3][7].setPieceOnTile(new Queen(false));
        tiles[4][7].setPieceOnTile(new King(false));
        tiles[5][7].setPieceOnTile(new Bishop(false));
        tiles[6][7].setPieceOnTile(new Knight(false));
        tiles[7][7].setPieceOnTile(new Rook(false));

        // Second line (pawns) for false and true side
        for (int i = 0; i < 8; i++) {
            tiles[i][1].setPieceOnTile(new Pawn(true));
            tiles[i][6].setPieceOnTile(new Pawn(false));
        }
    }

    /**
     * Getter a tábla egy x, y által meghatározott mezejéhez.
     * @param x A mező x koordinátája.
     * @param y A mező y koordinátája.
     * @return A meghatározott mező.
     */
    public Tile getTile(int x, int y) {
        if (x < 8 && x >= 0 && y < 8 && y >= 0) {
            return tiles[x][y];
        }
        return null;
    }
}
