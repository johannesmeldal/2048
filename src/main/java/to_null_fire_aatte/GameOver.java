package to_null_fire_aatte;

import java.util.Comparator;

public class GameOver implements Comparator<BoardGame> {
    
    /**
     * Tar inn to BoardGame-klasser, prøver alle trekk på den første og sammenligner med den andre
     */
    @Override
    public int compare(BoardGame o1, BoardGame o2) {
        o1.DOWN("test");
        o1.LEFT("test");
        o1.RIGHT("test");
        o1.UP("test");
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Integer o1Tile = o1.getTile(x, y).getTileValue();
                Integer o2Tile = o2.getTile(x, y).getTileValue(); 
                if ((!o1Tile.equals(o2Tile))) return 1;
            }
        }
        return 0;
    }
}
