import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A sakkórát leíró osztály.
 * Implementálja a Szerializáláshoz szükséges Serializable interface-t
 */
public class ChessClock implements Serializable {
    private final SerializableChess chessLogic;
    transient private Timer timer;
    private int whiteTime;
    private int blackTime;
    transient private List<Observer> observers;

    /**
     * ChessClock konstruktor.
     * <ul>
     *     <li>Létrehozza az observers listát.</li>
     *     <li>Létrehozza az időzítőt.</li>
     *     <li>Visszaállítja az időzítőt a 2 órás kezdeti értékre.</li>
     *     <li>Beállítja a sakklogikát.</li>
     * </ul>
     * @param chessLogic Az osztály metódusainak implementálásához szükséges sakklogika.
     */
    public ChessClock(SerializableChess chessLogic) {
        createObservers();
        createTimer();
        reset();
        this.chessLogic = chessLogic;
    }

    /**
     * Getter, mely visszaadja a fehér játékos rendelkezésére álló játékidőt.
     * @return A fehér játékos hátralevő ideje.
     */
    public int getWhiteTime() {
        return whiteTime;
    }

    /**
     * Getter, mely visszaadja a fekete játékos rendelkezésére álló játékidőt.
     * @return A fekete játékos hátralevő ideje.
     */
    public int getBlackTime() {
        return blackTime;
    }

    /**
     * Hozzáad egy figyelőt az observers listához.
     * @param observer A figyelő, amelyet a listához szeretnénk adni.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Létrehozza az observers listát.
     */
    public void createObservers() {
        observers = new ArrayList<>();
    }

    /**
     * Értesíti az observers listában lévő figyelőket.
     * Meghívja azok update függvényeit.
     */
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Visszaállítja a játékosok rendelkezésére álló játékidőket.
     */
    public void reset() {
        whiteTime = 120 * 60;
        blackTime = 120 * 60;
    }

    /**
     * Létrehozza az időzítőt, mely másodpercenként jelez.
     */
    public void createTimer() {
        TimerListener tl = new TimerListener();
        timer = new Timer(1000, tl);
    }

    /**
     * Elindítja az időzítőt.
     */
    public void start() {
        timer.start();
    }

    /**
     * Megállítja az időzítőt.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Belső osztály, mely lekezeli az időzítő jelzéseit.
     * Csökkenti a játékosok rendelkezésére álló játékidőt és
     * Értesíti a figyelő objektumokat (pl. a GUI-t megvalósító osztályt)
     */
    class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (chessLogic.getWhiteMoves()) {
                whiteTime--;
                if (whiteTime == 0) {
                    stop();
                }
            } else {
                blackTime--;
                if (blackTime == 0) {
                    stop();
                }
            }
            notifyObservers();
        }
    }
}
