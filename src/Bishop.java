import javax.swing.*;
import java.util.ArrayList;

/**
 * A futóhoz tartozó osztály, a Piece osztályból származik.
 */
public class Bishop extends Piece {
    /**
     * Bishop konstruktor.
     * Beállítja a bábu színét, nevét és ikonját.
     * @param white A bábu színe.
     */
    public Bishop(boolean white) {
        super(white, "Futó");
        setIcon(white ? new ImageIcon("src/resources/wBishop.png") : new ImageIcon("src/resources/bBishop.png"));
    }

    /**
     * Kiszámolja az adott mezőn elhelyezkedő bábu lehetséges lépéseit.
     * Definiálja a Piece osztályban deklarált függvényt.
     * @param table Sakktábla.
     * @param tile Az adott mező ahol a bábu áll.
     * @return Lista, amely tartalmazza azokat a mezőket ahova a bábu léphet.
     */
    @Override
    public ArrayList<Tile> legalMoves(Table table, Tile tile) {
        ArrayList<Tile> results = new ArrayList<Tile>();

        int x = tile.getX();
        int y = tile.getY();

        Tile t;
        // Right-up case
        while ((t = table.getTile(++x, --y)) != null) {
            if (t.getPieceOnTile() == null) {
                results.add(t);
            } else if (t.getPieceOnTile().isWhite() != isWhite()) {
                results.add(t);
                break;
            } else {
                break;
            }
        }
        x = tile.getX();
        y = tile.getY();

        // Right-down case
        while ((t = table.getTile(++x, ++y)) != null) {
            if (t.getPieceOnTile() == null) {
                results.add(t);
            } else if (t.getPieceOnTile().isWhite() != isWhite()) {
                results.add(t);
                break;
            } else {
                break;
            }
        }
        x = tile.getX();
        y = tile.getY();

        // Left-down case
        while ((t = table.getTile(--x, ++y)) != null) {
            if (t.getPieceOnTile() == null) {
                results.add(t);
            } else if (t.getPieceOnTile().isWhite() != isWhite()) {
                results.add(t);
                break;
            } else {
                break;
            }
        }
        x = tile.getX();
        y = tile.getY();

        // Left-up case
        while ((t = table.getTile(--x, --y)) != null) {
            if (t.getPieceOnTile() == null) {
                results.add(t);
            } else if (t.getPieceOnTile().isWhite() != isWhite()) {
                results.add(t);
                break;
            } else {
                break;
            }
        }

        return results;
    }
}
