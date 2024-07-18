import javax.swing.*;
import java.awt.*;

/**
 * A sakkmezőt megjelenítő GUI elem, a JButton származéka.
 */
public class TileButton extends JButton {
    private Tile tile;
    private boolean selected;
    private boolean targeted;

    /**
     * TileButton konstruktor
     * @param tile A kapcsolt sakkmező (Tile) objektum referenciája.
     */
    public TileButton(Tile tile) {
        this.tile = tile;
        selected = false;
        targeted = false;
        setBackground(tile.isWhite() ? Color.WHITE : Color.GRAY);
    }

    /**
     * Getter a kapcsolódó sakkmező (Tile) objektumhoz.
     * @return A sakkmező referenciája.
     */
    public Tile getTile() {
        return tile;
    }

    /**
     * Setter, mely beállítja a kapcsolódó sakkmezőt (Tile).
     * @param tile A sakkmező referenciája.
     */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Getter a selected tagváltozóhoz.
     * @return A selected tagváltozó értéke.
     */
    @Override
    public boolean isSelected() {
        return selected;
    }

    /**
     * Setter a selected tagváltozóhoz.
     * @param selected A selected tagváltozó új értéke.
     */
    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Getter a targeted tagváltozóhoz.
     * @return A targeted tagváltozó értéke.
     */
    public boolean isTargeted() {
        return targeted;
    }

    /**
     * Setter a targeted tagváltozóhoz.
     * @param targeted A targeted tagváltozó új értéke.
     */
    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }
}
