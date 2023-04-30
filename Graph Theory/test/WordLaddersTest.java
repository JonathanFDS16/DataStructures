import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class WordLaddersTest {

    @Test
    public void readWordGraphTest() throws FileNotFoundException {
        WordLadders.main("files/Large3WordGraph");

    }


}