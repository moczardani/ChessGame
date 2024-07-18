import javax.swing.*;
import java.util.ArrayList;

/**
 * A gyaloghoz tartozó osztály, a Piece osztályból származik.
 */
public class Pawn extends Piece {
    /**
     * Pawn konstruktor.
     * Beállítja a bábu színét, nevét és ikonját.
     * @param white A bábu színe.
     */
    public Pawn(boolean white) {
        super(white, "Gyalog");
        setIcon(white ? new ImageIcon("src/resources/wPawn.png") : new ImageIcon("src/resources/bPawn.png"));
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
        if (isWhite()) {
            // General case
            Tile t = table.getTile(tile.getX(), tile.getY() + 1);
            if (t != null && t.getPieceOnTile() == null) {
                results.add(t);
            }

            // Enemy case
            Tile tEnemyLeft = table.getTile(tile.getX() - 1, tile.getY() + 1);
            Tile tEnemyRight = table.getTile(tile.getX() + 1, tile.getY() + 1);
            if (tEnemyLeft != null && tEnemyLeft.getPieceOnTile() != null && !tEnemyLeft.getPieceOnTile().isWhite()) {
                results.add(tEnemyLeft);
            }
            if (tEnemyRight != null && tEnemyRight.getPieceOnTile() != null && !tEnemyRight.getPieceOnTile().isWhite()) {
                results.add(tEnemyRight);
            }

            // Opening case
            if (tile.getY() == 1) {
                Tile tStart = table.getTile(tile.getX(), tile.getY() + 2);
                if (tStart != null && tStart.getPieceOnTile() == null) {
                    results.add(tStart);
                }
            }

        } else {
            // General case
            Tile t = table.getTile(tile.getX(), tile.getY() - 1);
            if (t != null && t.getPieceOnTile() == null) {
                results.add(t);
            }

            // Enemy case
            Tile tEnemyLeft = table.getTile(tile.getX() - 1, tile.getY() - 1);
            Tile tEnemyRight = table.getTile(tile.getX() + 1, tile.getY() - 1);
            if (tEnemyLeft != null && tEnemyLeft.getPieceOnTile() != null && tEnemyLeft.getPieceOnTile().isWhite()) {
                results.add(tEnemyLeft);
            }
            if (tEnemyRight != null && tEnemyRight.getPieceOnTile() != null && tEnemyRight.getPieceOnTile().isWhite()) {
                results.add(tEnemyRight);
            }

            // Opening case
            if (tile.getY() == 6) {
                Tile tStart = table.getTile(tile.getX(), tile.getY() - 2);
                if (tStart != null && tStart.getPieceOnTile() == null) {
                    results.add(tStart);
                }
            }
        }

        return results;
    }
}
