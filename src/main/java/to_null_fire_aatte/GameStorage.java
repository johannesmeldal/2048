package to_null_fire_aatte;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GameStorage implements GameStorageInterface {

    @Override
    public BoardGame loadGame(String filename) throws FileNotFoundException{
        try {
            exceptionThrower(filename);
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            ArrayList<String> tileList = new ArrayList<String>(reader.lines().collect(Collectors.toList())) ;
            ArrayList<Integer> integerTileList = new ArrayList<Integer>();
            for (int i = 0; i < tileList.size(); i++) {
                String stringTile = tileList.get(i);
                Integer integerTile = Integer.valueOf(stringTile);
                integerTileList.add(integerTile);
            }
            reader.close();
            return new BoardGame(integerTileList);
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) throw (FileNotFoundException)e;
            if (e instanceof IllegalArgumentException) throw (IllegalArgumentException)e;
            return null;
        }
    }

    @Override
    public void saveGame(String filename, BoardGame tileArray) {
        try {
            exceptionThrower(filename);
            PrintWriter writer = new PrintWriter(filename);
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    Integer loadedTile = tileArray.getTile(y, x).getTileValue();
                    writer.println(loadedTile);
                }
            }
            writer.println(tileArray.getScore());
            writer.close();
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) throw (IllegalArgumentException) e;
        }
    }

    public void exceptionThrower(String filename) {
        if (filename.endsWith(".txt") && (filename.length() > 4 && filename.length() < 17) ){}
        else throw new IllegalArgumentException("Filnavnet må være på formen [filnavn].txt");
    }

}

