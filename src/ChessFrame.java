import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

/**
 * A sakkjáték grafikáját megvalósító osztály.
 */
public class ChessFrame extends JFrame implements Observer {
    private final TileButton[][] tiles;
    private final JTextField whiteTimeTextField;
    private final JTextField blackTimeTextField;
    private final JLabel whoseTurn;
    private final JLabel lastMoveTitle;
    private final JLabel lastMove;
    ArrayList<Tile> legalMoves;
    private TileButton tbSelected;
    private SerializableChess chessLogic;

    /**
     * ChessFrame konstruktor.
     * Létrehozza a sakkjáték GUI elemeit, mint menüsáv, sakktábla, vezérlő gombok, sakkóra, stb.
     * @param chessLogic SerializableChess típusú objektum, mely a sakkjáték logikáját írja le.
     */
    public ChessFrame(SerializableChess chessLogic) {
        tbSelected = null;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sakkjáték");
        setSize(800, 500);
        setResizable(true);

        ActionListener tl = new TileButtonActionListener();
        ActionListener nl = new NewButtonActionListener();
        ActionListener sl = new SaveButtonActionListener();
        ActionListener ll = new LoadButtonActionListener();
        ActionListener el = new ExitActionListener();

        JMenuBar menubar = new JMenuBar();
        JMenu filemenu = new JMenu("Fájl");
        filemenu.getAccessibleContext().setAccessibleDescription("Fájlműveletek");
        menubar.add(filemenu);
        JMenuItem menuItem = new JMenuItem("Játék mentése...");
        menuItem.getAccessibleContext().setAccessibleDescription("Játékállás mentése fájlba");
        menuItem.addActionListener(sl);
        filemenu.add(menuItem);
        menuItem = new JMenuItem("Játék betöltése...");
        menuItem.getAccessibleContext().setAccessibleDescription("Játékállás betöltése fájlból");
        menuItem.addActionListener(ll);
        filemenu.add(menuItem);
        menuItem = new JMenuItem("Kilépés");
        menuItem.getAccessibleContext().setAccessibleDescription("Kilépés");
        menuItem.addActionListener(el);
        filemenu.add(menuItem);
        setJMenuBar(menubar);

        JPanel mainPanel = new JPanel();
        JPanel boardPanel = new JPanel();
        JPanel controlsPanel = new JPanel();

        boardPanel.setLayout(new GridLayout(8, 8));
        this.chessLogic = chessLogic;
        chessLogic.getChessClock().addObserver(this);
        tiles = new TileButton[8][8];
        boolean white = false;

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {
                tiles[i][j] = new TileButton(chessLogic.getTable().getTile(i, j));
                tiles[i][j].setPreferredSize(new Dimension(50, 50));
                tiles[i][j].addActionListener(tl);
                boardPanel.add(tiles[i][j]);
            }
        }
        mainPanel.add(boardPanel);

        controlsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton newGame = new JButton("Új játék");
        c.insets = new Insets(0, 0, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        newGame.addActionListener(nl);
        controlsPanel.add(newGame, c);

        JButton saveGame = new JButton("Játék mentése");
        c.gridy = 1;
        saveGame.addActionListener(sl);
        controlsPanel.add(saveGame, c);

        JButton loadGame = new JButton("Játék betöltése");
        c.gridy = 2;
        loadGame.addActionListener(ll);
        controlsPanel.add(loadGame, c);

        whiteTimeTextField = new JTextField(timeFormat(chessLogic.getChessClock().getWhiteTime()));
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        whiteTimeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(whiteTimeTextField, c);

        blackTimeTextField = new JTextField(timeFormat(chessLogic.getChessClock().getWhiteTime()));
        c.gridx = 1;
        blackTimeTextField.setBackground(Color.BLACK);
        blackTimeTextField.setForeground(Color.WHITE);
        blackTimeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        c.insets = new Insets(0, 5, 10, 0);
        controlsPanel.add(blackTimeTextField, c);

        whoseTurn = new JLabel(chessLogic.getWhiteMoves() ? "Fehér lép" : "Fekete lép");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 4;
        whoseTurn.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(whoseTurn, c);

        lastMoveTitle = new JLabel("Utolsó lépés:");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 5;
        lastMoveTitle.setHorizontalAlignment(SwingConstants.LEFT);
        controlsPanel.add(lastMoveTitle, c);

        lastMove = new JLabel(chessLogic.getLastMove());
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 6;
        lastMove.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(lastMove, c);

        mainPanel.add(controlsPanel);
        this.add(mainPanel);
    }

    /**
     * Getter, a belső osztályok számára elérhetővé teszi a ChessFrame objektum referenciáját.
     * @return A ChessFrame objektum.
     */
    public ChessFrame getOuterClass() {
        return this;
    }

    /**
     * Visszaadja óó:pp:mm formátumban a rendelkezésre álló játékidőt.
     * @param sec Játékidő másodpercekben.
     * @return óó:pp:mm formátumú String.
     */
    public String timeFormat(Integer sec) {
        Integer h = sec / (60 * 60);
        Integer r = sec % (60 * 60);
        Integer m = r / 60;
        Integer s = r % 60;
        return h + ":" + m + ":" + s;
    }

    /**
     * Az Observer update függvényének implementációja.
     * <p>A sakkóra jelzésekor hívódik, és frissíti a sakkóra GUI elemeit.</p>
     */
    @Override
    public void update() {
        int wt = chessLogic.getChessClock().getWhiteTime();
        whiteTimeTextField.setText(timeFormat(wt));
        if (wt == 0) {
            JOptionPane.showMessageDialog(null, "Lejárt az idő, fekete győzött!", "Sakkjáték", 1);
            chessLogic.setRunning(false);
            return;
        }

        int bt = chessLogic.getChessClock().getBlackTime();
        blackTimeTextField.setText(timeFormat(bt));
        if (bt == 0) {
            JOptionPane.showMessageDialog(null, "Lejárt az idő, fehér győzött!", "Sakkjáték", 1);
            chessLogic.setRunning(false);
            return;
        }

        if (chessLogic.getWhiteMoves())
            whoseTurn.setText("Fehér lép");
        else
            whoseTurn.setText("Fekete lép");
    }

    /**
     * A sakkjáték GUI elemeinek frissítését végző függvény.
     */
    public void updateChessFrame() {
        // update table
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tiles[i][j].isSelected()) {
                    tiles[i][j].setBackground(Color.PINK);
                } else if (tiles[i][j].getTile().isChess()) {
                    tiles[i][j].setBackground(Color.RED);
                } else if (tiles[i][j].isTargeted()) {
                    tiles[i][j].setBackground(tiles[i][j].getTile().isWhite() ? Color.CYAN : Color.BLUE);
                } else {
                    tiles[i][j].setBackground(tiles[i][j].getTile().isWhite() ? Color.WHITE : Color.GRAY);
                }

                tiles[i][j].setTile(chessLogic.getTable().getTile(i, j));
                if (chessLogic.getTable().getTile(i, j).getPieceOnTile() != null) {
                    tiles[i][j].setIcon(chessLogic.getTable().getTile(i, j).getPieceOnTile().getIcon());
                } else {
                    tiles[i][j].setIcon(null);
                }
            }
        }

        // update lastMove
        lastMove.setText(chessLogic.getLastMove());
        // update ChessClock
        update();
    }

    /**
     * Belső osztály az "Új játék" gomb lekezelésére.
     */
    class NewButtonActionListener implements ActionListener {
        /**
         * A chessLogic paramétereit új játék indítására inicializálja és ennek megfelelően frissíti a GUI-t.
         * @param e Nincs használva
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            chessLogic.getTable().placePieces();
            chessLogic.setWhiteMoves(true);
            chessLogic.setLastMove("");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    tiles[i][j].setTargeted(false);
                    tiles[i][j].setSelected(false);
                    tiles[i][j].getTile().setChess(false);
                }
            }
            tbSelected = null;
            chessLogic.getChessClock().reset();
            chessLogic.getChessClock().start();
            chessLogic.setRunning(true);
            updateChessFrame();
        }
    }

    /**
     * Belső osztály a "Játék mentése" gomb lekezelésére.
     */
    class SaveButtonActionListener implements ActionListener {
        /**
         * Szerializációval lementi a chessLogic paramétereit a save dialog-ban kiválasztott fájlba.
         * A játékot üres táblával inicializálja.
         * @param e Nincs használva
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame saveFrame = new JFrame();
            saveFrame.setVisible(true);
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(saveFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    FileOutputStream f = new FileOutputStream(chooser.getSelectedFile());
                    ObjectOutputStream out = new ObjectOutputStream(f);
                    out.writeObject(chessLogic);
                    out.close();
                    //
                    chessLogic.setRunning(false);
                    chessLogic.getChessClock().stop();
                    chessLogic.getChessClock().reset();
                    chessLogic.getTable().clearTable();
                    chessLogic.setLastMove("");

                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            tiles[i][j].setTargeted(false);
                            tiles[i][j].setSelected(false);
                            tiles[i][j].getTile().setChess(false);
                        }
                    }
                    tbSelected = null;
                    updateChessFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Belső osztály a "Játék betöltése" gomb lekezelésére.
     */
    class LoadButtonActionListener implements ActionListener {
        /**
         * Visszatölti a szerializációval lementett chessLogic paramétereit, mellyel visszaállítja a sakkjáték állapotát.
         * <p>A chessLogic szerializációval lementett állapotának visszaállításán felül újra létrehozza a tranziens tag objektumokat, mint pl. observers és ChessClock timer.
         * Helyreállítja a sakkjáték logikáját tartalmazó osztályok és a GUI-t megvalósító osztályok közötti kapcsolatokat.</p>
         * @param e Nincs használva
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            chessLogic.getTable().clearTable();
            JFrame loadFrame = new JFrame();
            loadFrame.setVisible(true);
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(loadFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    FileInputStream f = new FileInputStream(chooser.getSelectedFile());
                    ObjectInputStream in = new ObjectInputStream(f);
                    chessLogic = (SerializableChess) in.readObject();
                    in.close();
                    //
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            tiles[i][j].setTargeted(false);
                            tiles[i][j].setSelected(false);
                            tiles[i][j].setTile(chessLogic.getTable().getTile(i, j));
                        }
                    }
                    tbSelected = null;
                    chessLogic.getChessClock().createObservers();
                    chessLogic.getChessClock().addObserver(getOuterClass());
                    chessLogic.getChessClock().createTimer();
                    if (chessLogic.isRunning())
                        chessLogic.getChessClock().start();
                    updateChessFrame();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    /**
     * Belső osztály a "Kilépés" menüelem lekezelésére.
     */
    class ExitActionListener implements ActionListener {
        boolean alreadyDisposed = false;

        /**
         * A kilépést megvalósító függvény.
         * @param e Nincs használva.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getOuterClass().isDisplayable()) {
                alreadyDisposed = true;
                getOuterClass().dispose();
            }
        }
    }

    /**
     * Belső osztály a játékmező gombok lekezelésére.
     */
    class TileButtonActionListener implements ActionListener {
        /**
         * A sakktáblán való bábu mozgatást végző függvény.
         * <p>Kattintásra a bábu kiválasztását, illetve a bábu lépését végzi.
         * A kiválasztásnak megfelelően frissíti a mezők állapotait és a chessLogic megfelelő függvényeivel hajtja végre a lépést.</p>
         * @param e Beazonosítja a kiválasztott mezőt.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!chessLogic.isRunning())
                return;
            TileButton tb = (TileButton) e.getSource();
            if (tb == null) {
                return;
            }
            Tile tile = tb.getTile();
            if (tile == null) {
                return;
            }
            if (tbSelected == null || tbSelected == tb) {
                // Selection branch
                Piece piece = tile.getPieceOnTile();
                if (piece == null) {
                    return;
                }
                if (piece.isWhite() != chessLogic.getWhiteMoves()) {
                    return;
                }

                if (tbSelected == tb) {
                    tbSelected = null;
                    tb.setSelected(false);
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            tiles[i][j].setTargeted(false);
                        }
                    }
                } else if (tbSelected == null) {
                    tbSelected = tb;
                    tb.setSelected(true);
                    ArrayList<Tile> legalMoves = chessLogic.legalMoves(tile);
                    for (int i = 0; i < legalMoves.size(); i++) {
                        Tile t = legalMoves.get(i);
                        tiles[t.getX()][t.getY()].setTargeted(true);
                    }
                }
            } else if (tb.isTargeted()) {
                // Moving branch
                tbSelected.setSelected(false);
                chessLogic.Move(tbSelected.getTile(), tile);
                tbSelected = null;
                tb.setSelected(false);
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        tiles[i][j].setTargeted(false);
                    }
                }
                chessLogic.setWhiteMoves(!chessLogic.getWhiteMoves());
                // check chess-mate
                if (!chessLogic.canMove(chessLogic.getWhiteMoves())) {
                    updateChessFrame();

                    Tile tileOfKing = chessLogic.tileOfKing(chessLogic.getWhiteMoves());
                    if (tileOfKing != null && tileOfKing.isChess())
                        JOptionPane.showMessageDialog(null, "Sakk-matt, " + (chessLogic.getWhiteMoves() ? "Fekete győzött!" : "Fehér győzött"), "Sakkjáték", 1);
                    else
                        JOptionPane.showMessageDialog(null, "Patt helyzet", "Sakkjáték", 1);
                    chessLogic.setRunning(false);
                }

            }
            updateChessFrame();
        }
    }
}
