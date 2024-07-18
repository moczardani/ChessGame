import javax.swing.*;
import java.util.ArrayList;

/**
 * A huszárhoz tartozó osztály, a Piece osztályból származik.
 */
public class Knight extends Piece {
    /**
     * Knight konstruktor.
     * Beállítja a bábu színét, nevét és ikonját.
     * @param white A bábu színe.
     */
    public Knight(boolean white) {
        super(white, "Huszár");
        setIcon(white ? new ImageIcon("src/resources/wKnight.png") : new ImageIcon("src/resources/bKnight.png"));
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
        ArrayList<Tile> possibleResults = new ArrayList<Tile>();
        possibleResults.add(table.getTile(tile.getX() + 1, tile.getY() + 2));
        possibleResults.add(table.getTile(tile.getX() + 2, tile.getY() + 1));
        possibleResults.add(table.getTile(tile.getX() + 2, tile.getY() - 1));
        possibleResults.add(table.getTile(tile.getX() + 1, tile.getY() - 2));
        possibleResults.add(table.getTile(tile.getX() - 1, tile.getY() - 2));
        possibleResults.add(table.getTile(tile.getX() - 2, tile.getY() - 1));
        possibleResults.add(table.getTile(tile.getX() - 2, tile.getY() + 1));
        possibleResults.add(table.getTile(tile.getX() - 1, tile.getY() + 2));

        for (int i = 0; i < possibleResults.size(); i++) {
            Tile t = possibleResults.get(i);
            if (t != null && (t.getPieceOnTile() == null || t.getPieceOnTile().isWhite() != isWhite())) {
                results.add(t);
            }
        }
        return results;
    }
}
