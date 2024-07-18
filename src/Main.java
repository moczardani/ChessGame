public class Main {
    public static void main(String[] args) {
        SerializableChess chessLogic = new SerializableChess();
        ChessFrame cf = new ChessFrame(chessLogic);
        cf.setVisible(true);
    }
}
