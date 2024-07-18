import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Egy sakkfigurát megvalósító absztrakt osztály.
 */
public abstract class Piece implements Serializable {
    private final boolean white;
    private final String name;
    private Icon icon;

    /**
     * Piece konstruktor
     * @param white Fehér vagy fekete.
     * @param name A bábu neve.
     */
    public Piece(boolean white, String name) {
        this.white = white;
        this.name = name;
    }

    /**
     * Getter a bábu nevéhez.
     * @return A bábu neve.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter a bábu ikonjához.
     * @return A bábu ikonja.
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * Setter a bábu ikonjához.
     * @param icon A bábu ikonja.
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * Getter a bábu színéhez.
     * @return A bábu színe.
     */
    public boolean isWhite() {
        return white;
    }

    /**
     * Absztakt függvény, mely visszaadja a bábuk megengedett lépéseit.
     * @param table A tábla.
     * @param tile A sakkmező, amelyen a bábu elhelyezkedik.
     * @return A lehetséges lépések listája.
     */
    public abstract ArrayList<Tile> legalMoves(Table table, Tile tile);

    /**
     * A bábu mozgatását végzi mezőről mezőre.
     * @param from A sakkmező ahol a bábu tartózkodik.
     * @param to A sakkmező ahova a bábut mozgatjuk.
     */
    public void Move(Tile from, Tile to) {
        from.setPieceOnTile(null);
        to.setPieceOnTile(this);
    }


}
