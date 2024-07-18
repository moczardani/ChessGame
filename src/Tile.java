import java.io.Serializable;

/**
 * Egy sakkmezőt leíró osztály.
 */
public class Tile implements Serializable {
    private Piece pieceOnTile;
    private int x;
    private int y;
    private boolean chess;

    /**
     * Tile konstruktor
     * @param x A mező x koordinátája.
     * @param y A mező y koordinátája.
     */
    public Tile(int x, int y) {
        this.pieceOnTile = null;
        this.x = x;
        this.y = y;
    }

    /**
     * A király bábut tartalmazó mező esetén megadja, hogy a király sakkban van-e.
     * @return Sakk státusz.
     */
    public boolean isChess() {
        return chess;
    }

    /**
     * A királyt tartalmazó mezőre beállítja, hogy a király sakkban van-e.
     * @param chess Sakk státusz.
     */
    public void setChess(boolean chess) {
        this.chess = chess;
    }

    /**
     * Visszaadja a sakkmezőn található sakkfigurát.
     * @return A sakkfigura, üres mező esetén null.
     */
    public Piece getPieceOnTile() {
        return pieceOnTile;
    }

    /**
     * Elhelyez egy sakkfigurát a mezőn.
     * @param pieceOnTile A sakkfigura.
     */
    public void setPieceOnTile(Piece pieceOnTile) {
        this.pieceOnTile = pieceOnTile;
    }

    /**
     * Getter a mező x koordinátájához.
     * @return A mező x koordinátája.
     */
    public int getX() {
        return x;
    }

    /**
     * Getter a mező y koordinátájához.
     * @return A mező y koordinátája.
     */
    public int getY() {
        return y;
    }

    /**
     * A sakkmező színe.
     * @return Fehér vagy fekete.
     */
    public boolean isWhite() {
        return (x + y) % 2 == 1;
    }
}
