package to_null_fire_aatte;

import java.io.FileNotFoundException;

public interface GameStorageInterface {
    BoardGame loadGame(String filename) throws FileNotFoundException;
    void saveGame(String filename, BoardGame tileArray)throws FileNotFoundException ;
}
