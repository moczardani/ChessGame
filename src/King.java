import javax.swing.*;
import java.util.ArrayList;

/**
 * A királyhoz tartozó osztály, a Piece osztályból származik.
 */
public class King extends Piece {
    /**
     * King konstruktor.
     * Beállítja a bábu színét, nevét és ikonját.
     * @param white A bábu színe.
     */
    public King(boolean white) {
        super(white, "Király");
        setIcon(white ? new ImageIcon("src/resources/wKing.png") : new ImageIcon("src/resources/bKing.png"));
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
        possibleResults.add(table.getTile(tile.getX(), tile.getY() + 1));
        possibleResults.add(table.getTile(tile.getX() + 1, tile.getY() + 1));
        possibleResults.add(table.getTile(tile.getX() + 1, tile.getY()));
        possibleResults.add(table.getTile(tile.getX() + 1, tile.getY() - 1));
        possibleResults.add(table.getTile(tile.getX(), tile.getY() - 1));
        possibleResults.add(table.getTile(tile.getX() - 1, tile.getY() - 1));
        possibleResults.add(table.getTile(tile.getX() - 1, tile.getY()));
        possibleResults.add(table.getTile(tile.getX() - 1, tile.getY() + 1));

        for (int i = 0; i < possibleResults.size(); i++) {
            Tile t = possibleResults.get(i);
            if (t != null && (t.getPieceOnTile() == null || t.getPieceOnTile().isWhite() != isWhite())) {
                results.add(t);
            }
        }
        return results;
    }
}
