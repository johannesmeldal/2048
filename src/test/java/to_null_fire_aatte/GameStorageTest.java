package to_null_fire_aatte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameStorageTest {

    private BoardGame test;
    private GameStorageInterface memory = new GameStorage();

    private BoardGame prepareBoardGame(
        Integer aa, Integer ab, Integer ac, Integer ad,
        Integer ba, Integer bb, Integer bc, Integer bd,
        Integer ca, Integer cb, Integer cc, Integer cd,
        Integer da, Integer db, Integer dc, Integer dd
    ) {
        ArrayList<Integer> testListe = new ArrayList<Integer>();
        List<Integer> listeForslag = Arrays.asList
        (
        aa, ab, ac, ad,
        ba, bb, bc, bd,
        ca, cb, cc, cd,
        da, db, dc, dd
        );
        testListe.addAll(listeForslag);
        BoardGame test = new BoardGame(testListe);
        return test;
    }

    private ArrayList<Integer> getBoardRow(BoardGame board, Integer rowNum) {
        if (rowNum > 3|| rowNum < 0) throw new IllegalArgumentException("Ugyldig radnummer");
        ArrayList<Integer> row = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            row.add(i, board.getTile(i, rowNum).getTileValue());
        }
        return row;
    }

    @BeforeEach
    public void setUp() {
        this.test = prepareBoardGame(    
        4,8,0,0,
        2,0,0,0,
        0,0,0,16,
        0,0,0,32
        );
        this.test.setScore(68);
        try {
            memory.saveGame("test_storage.txt", test);
        } catch (Exception e) {
        }
    }

    @Test
    public void saveGameTest() {
        BoardGame unsavedTest = prepareBoardGame(    
        8,8,0,0,
        0,8,0,0,
        0,0,8,0,
        0,0,0,8
        );
        test.setScore(68);
        BoardGame savedTest = new BoardGame();
        try {
            memory.saveGame("test_storage.txt", unsavedTest);
        } catch (FileNotFoundException e) {
            fail("Kunne ikke lagre fila");
        }
        try {
            savedTest = memory.loadGame("test_storage.txt");
        } catch (Exception e) {
            fail("Kunne ikke hente fila");
        }
        assertEquals(getBoardRow(unsavedTest,0), getBoardRow(savedTest, 0) );
        assertEquals(getBoardRow(unsavedTest,1), getBoardRow(savedTest, 1) );
        assertEquals(getBoardRow(unsavedTest,2), getBoardRow(savedTest, 2) );
        assertEquals(getBoardRow(unsavedTest,3), getBoardRow(savedTest, 3) );
        assertEquals(unsavedTest.getScore(), savedTest.getScore());
        assertFalse(savedTest.gameWonCheck());
    }

    @Test
    public void invalidsaveGameTest() {
        BoardGame test = prepareBoardGame(    
        4,8,0,0,
        2,0,0,0,
        0,0,0,16,
        0,0,0,32
        );
        test.setScore(68);
        Assertions.assertThrows(IllegalArgumentException.class, () ->  memory.saveGame(".txt", test), "Kan ikke lagre uten brukernavn");
        Assertions.assertThrows(IllegalArgumentException.class, () ->  memory.saveGame("waytolongusername.txt", test), "Kan ikke lagre uten brukernavn");
    }
    
    @Test
    public void loadGameTest() {
        BoardGame loadTest = new BoardGame();
        try {
            loadTest = memory.loadGame("test_storage.txt");
        } catch (Exception e) {
            fail("Finner ikke fila");
        }
        assertEquals(getBoardRow(test, 0), getBoardRow(loadTest, 0));
        assertEquals(getBoardRow(test, 1), getBoardRow(loadTest, 1));
        assertEquals(getBoardRow(test, 2), getBoardRow(loadTest, 2));
        assertEquals(getBoardRow(test, 3), getBoardRow(loadTest, 3));
        assertEquals(test.getScore(), loadTest.getScore());
        assertFalse(loadTest.gameWonCheck());
    }

    @Test
    public void loadGameWonTest() {
        test = prepareBoardGame(
        4,8,0,0,
        2,0,0,0,
        0,0,0,16,
        0,0,0,2048);
        try {
            memory.saveGame("test_storage.txt", test);
        } catch (Exception e) {
        }
        BoardGame loadTest = new BoardGame();
        try {
            loadTest = memory.loadGame("test_storage.txt");
        } catch (Exception e) {
            fail("Finner ikke fila");
        }
        assertTrue(loadTest.gameWonCheck());
    }

    @Test
    public void invalidLoadGameTest() {
        Assertions.assertThrows(FileNotFoundException.class, () ->  memory.loadGame("test.txt"), "Kan ikke hente en fil som ikke ligger lagret");
        Assertions.assertThrows(IllegalArgumentException.class, () ->  memory.loadGame("waytolongusername.txt"), "Kan ikke lagre et brukernavn som er lenger enn 12 symboler");
        Assertions.assertThrows(IllegalArgumentException.class, () ->  memory.loadGame("test"), "Filnavn ender alltid med .txt");
        Assertions.assertThrows(IllegalArgumentException.class, () ->  memory.loadGame(".txt"), "Kan ikke lagre uten brukernavn");

    }
}
