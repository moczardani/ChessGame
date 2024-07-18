import javax.swing.*;
import java.util.ArrayList;

/**
 * A bástyához tartozó osztály, a Piece osztályból származik.
 */
public class Rook extends Piece {
    /**
     * Rook konstruktor.
     * Beállítja a bábu színét, nevét és ikonját.
     * @param white A bábu színe.
     */
    public Rook(boolean white) {
        super(white, "Bástya");
        setIcon(white ? new ImageIcon("src/resources/wRook.png") : new ImageIcon("src/resources/bRook.png"));
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

        // Right case
        Tile t;
        while ((t = table.getTile(++x, y)) != null) {
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

        // Left case
        while ((t = table.getTile(--x, y)) != null) {
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

        // Up case
        while ((t = table.getTile(x, --y)) != null) {
            if (t.getPieceOnTile() == null) {
                results.add(t);
            } else if (t.getPieceOnTile().isWhite() != isWhite()) {
                results.add(t);
                break;
            } else {
                break;
            }
        }
        y = tile.getY();

        // Down case
        while ((t = table.getTile(x, ++y)) != null) {
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