import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChessFrameTest {

    @Test
    void timeFormatTest() {
        ChessFrame frame = new ChessFrame(new SerializableChess());
        assertEquals("1:0:0", frame.timeFormat(3600));
    }
}