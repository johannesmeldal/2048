package to_null_fire_aatte;

import java.util.HashMap;

public class Tile {
    /**
     * 
     */
    private Integer tileValue;
    private String tileColor;
    private int count = 1;
    private boolean rightValue = false;
    private HashMap<Integer, String> coloMap = new HashMap<>()
    {{
        put(0, "#8A307F");
        put(2, "#24f08a");
        put(4,"#24f046");
        put(8,"#46f024");
        put(16,"#8af024");
        put(32,"#cef024");
        put(64,"#f0ce24");
        put(128,"#f08a24");
        put(256,"#f04624");
        put(512,"#f02468");
        put(1024,"#f024ac");
        put(2048,"#f024f0");
        put(4096,"#af24f0");
    }};

    

    public Tile(Integer value) {
        if (value == 0){
            this.tileValue = 0;
            this.tileColor = coloMap.get(0);
            rightValue = true;
        }  
        while (!rightValue) {
            if (count < 12 && count > 0) {
                if (value == Math.pow(2, count)) {
                    this.tileValue = value;
                    this.tileColor = coloMap.get(value);
                    rightValue = true;
                } else {
                    count++;
                }
            } else{
                rightValue = false;
                throw new IllegalArgumentException("Ugyldig tileValue");
            } 
        }
        rightValue = false;
    }

    public void setTileValue(Integer value) {
        this.tileValue = value;
    }

    /**
     * Metode som returnerer verdien til Tile-klassen
     * @return returnerer tileValue
     */
    public Integer getTileValue() {
        return this.tileValue; 
    }

    public void setTileColor(Integer value) {
        this.tileColor = coloMap.get(value);
    }
    
    /**
     * Metode som returnerer fargeverdien til Tile-klassen
     * @return returnerer tileColor
     */
    public String getTileColor() {
        return this.tileColor;
    }

    @Override
    public String toString() {
        return "[" + tileValue + "]";
    }
    
    public static void main(String[] args) {
    }
}
