import java.io.Serializable;
import java.util.ArrayList;

/**
 * A sakkjáték logikáját megvalósító osztály.
 * Implementálja a szerializáláshoz szükséges Serializable interface-t.
 */
public class SerializableChess implements Serializable {
    private final Table table;
    private final ChessClock chessClock;
    private boolean whiteMoves;
    private boolean running;
    private String lastMove = "";

    /**
     * SerializableChess Konstruktor
     * <ul>
     *     <li>Létrehozza a sakktáblát azok mezejeivel.</li>
     *     <li>Létrehozza a sakkórát.</li>*
     * </ul>
     */
    public SerializableChess() {
        table = new Table();
        chessClock = new ChessClock(this);
        whiteMoves = true;
    }

    /**
     * Getter a sakktábla (table) objektumhoz.
     * @return A table objektum.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Getter a sakkóra (chessClock) objektumhoz.
     * @return A chessClock objektum.
     */
    public ChessClock getChessClock() {
        return chessClock;
    }

    /**
     * Getter a whiteMoves tagváltozóhoz.
     * @return Boolean érték, mely megadja hogy a fehér vagy a fekete játékos lépése következik.
     */
    public boolean getWhiteMoves() {
        return whiteMoves;
    }

    /**
     * Setter a whiteMoves tagváltozóhoz.
     * @param v Boolean érték a játékos megadására.
     */
    public void setWhiteMoves(boolean v) {
        whiteMoves = v;
    }

    /**
     * Getter a running tagváltozóhoz.
     * @return Boolean érték, mely megadja hogy a játék futó állapotban van-e.
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Setter a running tagváltozóhoz.
     * @param running Boolean érték, mely beállítja a játék futó állapotát.
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Getter a lastMove tagváltozóhoz.
     * @return String érték, mely az utolsó lépés információját tartalmazza.
     */
    public String getLastMove() {
        return lastMove;
    }

    /**
     * Setter a lastMove tagváltozóhoz.
     * @param lastMove Az utolsó lépés információját tartalmazó String.
     */
    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    /**
     * Visszaadja a megadott színű király mezejét.
     * @param whiteKing Fehér vagy fekete
     * @return A király bábut tartalmazó mező.
     */
    public Tile tileOfKing(boolean whiteKing) {
        // Calculate the tile of the king
        Tile tileOfKing = null;
        for (int i = 0; i < 8 && tileOfKing == null; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = table.getTile(i, j).getPieceOnTile();
                if (p != null && p instanceof King && p.isWhite() == whiteKing) {
                    tileOfKing = table.getTile(i, j);
                    break;
                }
            }
        }
        return tileOfKing;
    }

    /**
     * Megvizsgálja, hogy az adott mezőn tartózkodó király sakk állapotban van-e.
     * @param tileOfKing A király bábut tartalmazó mező.
     * @return Megadja, hogy a király sakkban van-e.
     */
    public boolean isChess(Tile tileOfKing) {
        if (tileOfKing == null || tileOfKing.getPieceOnTile() == null) {
            return false;
        }
        boolean whiteKing = tileOfKing.getPieceOnTile().isWhite();
        // Check chess status
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = table.getTile(i, j).getPieceOnTile();
                if (p != null && p.isWhite() != whiteKing) {
                    ArrayList<Tile> legalMoves = p.legalMoves(table, table.getTile(i, j));
                    for (Tile tile : legalMoves) {
                        if (tile.getX() == tileOfKing.getX() && tile.getY() == tileOfKing.getY()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Az adott mezőn álló bábu lehetséges lépéseit visszaadó függvény.
     * <p>Összegyűjti az adott bábu lehetséges lépéseit.
     * Az ellentétes színű király mezejét kiszűri.
     * Kiszűri azokat a lépéseket, melyre lépve a saját király bábunk sakkba kerülne.</p>
     * @param tile Az adott bábut tartalmazó sakkmező.
     * @return Azon sakkmezők listája ahova a bábu léphet.
     */
    public ArrayList<Tile> legalMoves(Tile tile) {
        ArrayList<Tile> lm = new ArrayList<Tile>();
        Piece piece = tile.getPieceOnTile();
        if (piece == null)
            return lm;
        lm = piece.legalMoves(table, tile);
        // remove other king from the legal moves
        Tile tileOfKing = tileOfKing(!piece.isWhite());
        if (tileOfKing != null && lm.contains(tileOfKing))
            lm.remove((tileOfKing));
        // check if possible steps causing our king to be in chess, try all the possible steps
        ArrayList<Tile> lm_to_remove = new ArrayList<Tile>();
        for (Tile tile_to : lm) {
            Piece p_attacked = tile_to.getPieceOnTile();
            piece.Move(tile, tile_to);
            tileOfKing = tileOfKing(piece.isWhite());
            if (isChess(tileOfKing))
                lm_to_remove.add(tile_to);
            piece.Move(tile_to, tile);  // restore move
            if (p_attacked != null)
                tile_to.setPieceOnTile(p_attacked);// restore attacked
        }
        lm.removeAll(lm_to_remove);
        return lm;
    }

    /**
     * A sakkbábu mozgatását végző függvény.
     * <p>A bábu mozgatásán túl leellenőrzi és beállítja a király bábukat tartalmazó mezők sakk állapotát.
     * Beállítja a lastMove String tartalmát.</p>
     * @param tileFrom A bábut tartalmazó sakkmező.
     * @param tileTo A kiválasztott célmező.
     */
    public void Move(Tile tileFrom, Tile tileTo) {
        Piece piece = tileFrom.getPieceOnTile();
        if (piece == null)
            return;
        if (piece instanceof King) {
            tileFrom.setChess(false);
        }
        piece.Move(tileFrom, tileTo);
        String lm = (piece.isWhite() ? "Fehér " : "Fekete ") + piece.getName() + " ";
        lm += (char) ('a' + tileFrom.getX());
        lm += (char) ('1' + tileFrom.getY());
        lm += "->";
        lm += (char) ('a' + tileTo.getX());
        lm += (char) ('1' + tileTo.getY());
        setLastMove(lm);
        // check chess states
        Tile tileOfKing = tileOfKing(piece.isWhite());
        if (tileOfKing != null) {
            tileOfKing.setChess(isChess(tileOfKing));
        }
        tileOfKing = tileOfKing(!piece.isWhite());
        if (tileOfKing != null) {
            tileOfKing.setChess(isChess(tileOfKing));
        }
    }

    /**
     * A megadott színű bábuk esetén leellenőrzi, hogy van-e lehetőség lépésre.
     * Amennyiben nincs, az vagy sakk-matt vagy patthelyzetet jelent és a játék végét jelenti.
     * @param isWhite Fehér vagy fekete.
     * @return Visszaadja, hogy van-e mozgatási lehetőség.
     */
    public boolean canMove(boolean isWhite) {
        ArrayList<Tile> lm = new ArrayList<Tile>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (table.getTile(i, j).getPieceOnTile() != null && table.getTile(i, j).getPieceOnTile().isWhite() == isWhite) {
                    lm = legalMoves(table.getTile(i, j));
                    if (lm.size() > 0)
                        return true;
                }
            }
        }
        return false;
    }
}