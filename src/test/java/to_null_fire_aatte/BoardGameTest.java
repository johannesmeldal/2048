package to_null_fire_aatte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardGameTest {

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

    /**
     * 
     * @param rowNum tar inn nummeret på raden
     */
    private ArrayList<Integer> getBoardRow(BoardGame board, Integer rowNum) {
        if (rowNum > 3|| rowNum < 0) throw new IllegalArgumentException("Ugyldig radnummer");
        ArrayList<Integer> row = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            row.add(i, board.getTile(i, rowNum).getTileValue());
        }
        return row;
    }

    private Integer getTileCount(BoardGame board) {
        Integer count = 0;
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> curList = getBoardRow(board,i);
            for (Integer tileValue : curList) {
                if (tileValue > 0) count ++;
            }
        } 
        return count;
    }
    
    /**
     * Tester 
     */
    @Test
    public void testInvalidMove() {
        BoardGame test = prepareBoardGame(    
        0,0,0,0,
        0,0,0,0,
        0,0,0,0,
        2,0,0,0
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> test.LEFT("move") , "Kan ikke gjøre et trekk som ikke endrer tilstanden");
        Assertions.assertThrows(IllegalArgumentException.class, () -> test.DOWN("move") , "Kan ikke gjøre et trekk som ikke endrer tilstanden");
    }

    // glpat-iuiUwK6GpAmNYmMgP8yC  = GITtoken

    @Test
    public void testGameOver() {
        BoardGame test = prepareBoardGame(
        2,8,2,8,
        8,2,8,2,
        2,8,2,8,
        8,2,8,2
        );
        Assertions.assertThrows(IllegalStateException.class, () -> test.LEFT("move") , "Kan ikke flytte når brettet er fylt");
    }

    @Test
    public void testGameWon() {
        BoardGame test = prepareBoardGame(
        1024,1024,2,8,
        8,2,8,2,
        2,8,2,8,
        8,2,8,2
        );
        assertEquals(false, test.gameWonCheck());
        test.LEFT("m");
        assertTrue(test.gameWonCheck());
        Assertions.assertThrows(IllegalStateException.class, () -> test.LEFT("move") , "Kan ikke flytte etter at spillet er vunnet");
    }

    @Test
    public void testRIGHT() {
        BoardGame test = prepareBoardGame(
        2,0,0,2,
        4,0,4,8,
        4,4,8,8,
        2,2,2,2
        );        
        test.RIGHT("simple");
        List<Integer> r0 = Arrays.asList(0,0,0,4);
        List<Integer> r1 = Arrays.asList(0,0,8,8);
        List<Integer> r2 = Arrays.asList(0,0,8,16);
        List<Integer> r3 = Arrays.asList(0,0,4,4);
        assertEquals(r0, getBoardRow(test,0));
        assertEquals(r1, getBoardRow(test,1));
        assertEquals(r2, getBoardRow(test,2));
        assertEquals(r3, getBoardRow(test,3));
        test.RIGHT("simple");
        r0 = Arrays.asList(0,0,0,4);
        r1 = Arrays.asList(0,0,0,16);
        r2 = Arrays.asList(0,0,8,16);
        r3 = Arrays.asList(0,0,0,8);
        assertEquals(r0, getBoardRow(test,0));
        assertEquals(r1, getBoardRow(test,1));
        assertEquals(r2, getBoardRow(test,2));
        assertEquals(r3, getBoardRow(test,3));
    }

    @Test
    public void testLEFT() {
        BoardGame test = prepareBoardGame(
        2,0,0,2,
        2,2,2,2,
        4,4,8,8,
        2,2,0,4
        );   
        test.LEFT("simple");
        List<Integer> r0 = Arrays.asList(4,0,0,0);
        List<Integer> r1 = Arrays.asList(4,4,0,0);
        List<Integer> r2 = Arrays.asList(8,16,0,0);
        List<Integer> r3 = Arrays.asList(4,4,0,0);
        assertEquals(r0, getBoardRow(test,0));
        assertEquals(r1, getBoardRow(test,1));
        assertEquals(r2, getBoardRow(test,2));
        assertEquals(r3, getBoardRow(test,3));
        test.LEFT("simple");
        r0 = Arrays.asList(4,0,0,0);
        r1 = Arrays.asList(8,0,0,0);
        r2 = Arrays.asList(8,16,0,0);
        r3 = Arrays.asList(8,0,0,0);
        assertEquals(r0, getBoardRow(test,0));
        assertEquals(r1, getBoardRow(test,1));
        assertEquals(r2, getBoardRow(test,2));
        assertEquals(r3, getBoardRow(test,3));
    }

    @Test
    public void testUP() {
        BoardGame test = prepareBoardGame(
        2,0,0,4,
        2,2,2,4,
        4,4,8,8,
        2,0,0,0
        );   
        test.UP("simple");
        List<Integer> r0 = Arrays.asList(4,2,2,8);
        List<Integer> r1 = Arrays.asList(4,4,8,8);
        List<Integer> r2 = Arrays.asList(2,0,0,0);
        List<Integer> r3 = Arrays.asList(0,0,0,0);
        assertEquals(r0, getBoardRow(test,0));
        assertEquals(r1, getBoardRow(test,1));
        assertEquals(r2, getBoardRow(test,2));
        assertEquals(r3, getBoardRow(test,3));
        test.UP("simple");
        r0 = Arrays.asList(8,2,2,16);
        r1 = Arrays.asList(2,4,8,0);
        r2 = Arrays.asList(0,0,0,0);
        r3 = Arrays.asList(0,0,0,0);
        assertEquals(r0, getBoardRow(test,0));
        assertEquals(r1, getBoardRow(test,1));
        assertEquals(r2, getBoardRow(test,2));
        assertEquals(r3, getBoardRow(test,3));

    }

    @Test
    public void testDOWN() {
        BoardGame test = prepareBoardGame(
        2,0,0,2,
        2,2,2,2,
        4,4,8,8,
        2,0,0,0
        );   
        test.DOWN("simple");
        List<Integer> r0 = Arrays.asList(0,0,0,0);
        List<Integer> r1 = Arrays.asList(4,0,0,0);
        List<Integer> r2 = Arrays.asList(4,2,2,4);
        List<Integer> r3 = Arrays.asList(2,4,8,8);
        assertEquals(r0, getBoardRow(test,0));
        assertEquals(r1, getBoardRow(test,1));
        assertEquals(r2, getBoardRow(test,2));
        assertEquals(r3, getBoardRow(test,3));
        
    }

    @Test
    public void testBootUp() {
        BoardGame test = new BoardGame();
        test.bootUp();
        assertEquals(2, getTileCount(test));
    }

    @Test
    public void testScore() {
        BoardGame test = prepareBoardGame(
        0,0,0,2,
        4,4,4,2,
        8,8,8,8,
        8,8,0,0
        );   
        assertEquals(0, test.getScore());
        test.DOWN("simple");
        assertEquals(36, test.getScore());
        test.RIGHT("simple");
        assertEquals(100, test.getScore());
        test.LEFT("simple");
        assertEquals(116, test.getScore());
    }

    @Test
    public void newTile() {
        BoardGame test = prepareBoardGame(
        0,0,0,2,
        0,0,0,2,
        0,0,0,0,
        0,0,0,0
        );   
        test.LEFT("m");
        assertEquals(3, getTileCount(test));
    }
        
    public static void main(String[] args) {
        
    }
}

