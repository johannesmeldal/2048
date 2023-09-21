package to_null_fire_aatte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class GameOverTest {
    //TODO funker ikke

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

    
    @Test 
    public void compareTest() {
        BoardGame test1 = prepareBoardGame(
        2, 0, 0, 0,
        0, 0, 0, 0,
        0, 64, 0, 0,
        0, 0, 0, 2
        );
        BoardGame test2 = prepareBoardGame(
        2, 0, 0, 0,
        0, 0, 0, 0,
        0, 64, 0, 0,
        0, 0, 0, 2
        );
    }
}
