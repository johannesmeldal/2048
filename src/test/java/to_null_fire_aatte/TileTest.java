package to_null_fire_aatte;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TileTest {

    @Test
    public void colorTest() {
        Tile test2 = new Tile(2);
        Tile test8 = new Tile(8);
        Tile test32 = new Tile(32);
        Tile test1024 = new Tile(1024);
        assertEquals("#24f08a", test2.getTileColor());
        assertEquals("#46f024", test8.getTileColor());
        assertEquals("#cef024", test32.getTileColor());
        assertEquals("#f024ac", test1024.getTileColor());
    }

    @Test
    public void validTileValue() {

        Tile test0 = new Tile(0);
        assertEquals(0, test0.getTileValue());
        Tile test64 = new Tile(64);
        assertEquals(64, test64.getTileValue());
        Tile test2048 = new Tile(2048);
        assertEquals(2048, test2048.getTileValue());
        
    }

    @Test
    public void invalidTileValue() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Tile(3), "Kan ikke legge til en Tile med en verdi som ikke går opp i 2^x | 0 < x < 12");
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Tile(4096), "Kan ikke legge til en Tile med en verdi som ikke går opp i 2^x | 0 < x < 12");
        Assertions.assertThrows(IllegalArgumentException.class, () ->  new Tile(-1), "Kan ikke legge til en Tile med en verdi som ikke går opp i 2^x | 0 < x < 12");

    }

}
