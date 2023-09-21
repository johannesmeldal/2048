package to_null_fire_aatte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BoardGame {

    private ArrayList<ArrayList<Tile>> brett = new ArrayList<>();
    private Integer value;
    private ArrayList<ArrayList<Tile>> strippedOfZeros = new ArrayList<>();
    private ArrayList<ArrayList<Tile>> clockCopy = new ArrayList<>();
    private Integer flytteTeller = 0;
    private boolean gameOverCheck = true;
    private Integer score = 0;
    private Boolean gameWon = false;
    private Boolean simpleMode = false;
    

    /**
     * Tom konstruktør
     */
    public BoardGame() {
    }

    /**
     * Kontruktør som tar inn en liste med TileValues og kaller på fillUpBrett() for å legge de til i brett.
     * @param tileList
     */
    public BoardGame(ArrayList<Integer> tileList) {
        fillUpBrett(tileList);
    }

    /**
     * Metode som tar inn en ArrayList<integer> som skal legges til i brett. Utløser exception om lista ikke er på lengde 16 eller 17 (4x4 rader/ 4x4 rader + score).
     * @param tileList tar inn ArrayList<integer> som representerer alle verdiene til Tiles i brett
     */
    private void fillUpBrett(ArrayList<Integer> tileList) {
        // System.out.println(tileList);
        if (tileList.size() != 16 && tileList.size() != 17 ) throw new IllegalArgumentException("Ugyldig lengde på liste");
        int hjelper = 0;
        for (int i = 0; i < 4; i++) {
            hjelper = i*3;
            ArrayList<Tile> row = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Integer value = (Integer) (tileList.get(i+j+hjelper));
                if (value == 2048) gameWon = true;
                row.add(new Tile(value));
            }
            brett.add(row);
            if(tileList.size() == 17) this.score = Integer.valueOf(tileList.get(16)); // Siste elementet i fila er score
            else this.score = 0;
        }
    }

    public Tile getTile(int x,int y) { 
        return brett.get(y).get(x); 
    }

    public void setTile(int x, int y, Integer tileValue) {
        brett.get(x).set(y, new Tile(tileValue));
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * Metode kaller på generateTiile() hvis isValidMove() er true. Hvis isValidMove() er false, og gameOverCheck() er true, vil metoden kaste en exception.
     */
    private void newTileWithCheck() {
        if (isValidMove() && !simpleMode) {
            int temp = 0;
            while (temp == 0){
                Random rand = new Random();
                int randRow = rand.nextInt(4);
                int randCol = rand.nextInt(4);  
                if (brett.get(randRow).get(randCol).getTileValue() == 0){
                    brett.get(randRow).set(randCol, generateTile());
                    temp ++;
                }   
            }
        }
        else if (gameOverCheck) {
            simpleMode = false;
            if (noMovesLeft()) throw new IllegalStateException("Game Over!");
            else throw new IllegalArgumentException("Ikke mulig å flytte denne veien.");
        }
    }

    /**
     * Metode som genererer en Tile som enten har verdi 2 (20%), eller 4 (40%)
     * @return Returnerer en tilfeldig Tile med verdi 2 eller 4
     */
    private Tile generateTile() {       
        Random rand = new Random();
        int x = rand.nextInt(10);
        if (x == 5 || x == 1 ) this.value = 4;
        else this.value = 2;

        Tile randomTile = new Tile(value);
        return randomTile;    
    }

    /**
     * Metode som legger til 4 lister i strippedOfZeros
     */
    private void fillZeroZero() {
        for (int i = 0; i < 4; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            strippedOfZeros.add(row);
        }
    }

    /**
     * Metode som fjerner alle tiles med verdi 0, og legger de til på venstresiden av brettet.
     */
    private void removeZeroI() {
        fillZeroZero();
        for (int i = 0; i < 4; i++) {
            for (Tile tile : brett.get(i)) {
                if (tile.getTileValue() != 0) {
                    strippedOfZeros.get(i).add(tile);
                }
            }
            int currentListSize = strippedOfZeros.get(i).size();
            for (int j = 0; j < (4-currentListSize); j++) {
                strippedOfZeros.get(i).add(0, new Tile(0));
            }
            for (int j = 0; j < 4; j++) {
                Integer brettVerdier = brett.get(i).get(j).getTileValue();
                Integer nyeBrettVerdier = strippedOfZeros.get(i).get(j).getTileValue();
                if (brettVerdier != nyeBrettVerdier) flytteTeller ++;
            }
        }     
        brett.clear();
        brett.addAll(strippedOfZeros);
        strippedOfZeros.clear();
    }

    /**
     * Metode som går gjennom alle radene i brett; om to "nabo-Tiles" er like så dobler verdien til den ene, og den andre fjernes og legges til som en Tile med verdien 0. Skjer dette, og den doble verdien av en Tile er 2048 endres gameWon til true.
     */
    private void addEqual() {
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 5; j++) {
                if (j < 4) {
                    Tile currentTile = brett.get(i).get(4-j);
                    Tile nextTile = brett.get(i).get(4-(j+1));
                    if (currentTile.getTileValue() != 0) {
                        if (currentTile.getTileValue().equals(nextTile.getTileValue())) {
                            brett.get(i).set(4-j, new Tile(currentTile.getTileValue()*2));
                            this.score += currentTile.getTileValue()*2;
                            brett.get(i).remove(4-(j+1));
                            brett.get(i).add(0, new Tile(0));
                            flytteTeller ++;
                            if (currentTile.getTileValue()*2 == 2048) gameWon = true;
                        }
                    }
                }
                
            }
        }
    }

    /**
     * Metode som legger til 4 tommme lister i clockCopy
     */
    private void fillUpClockCopy() {
        for (int i = 0; i < 4; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            clockCopy.add(row);
        }
    }

    /**
     * Metode som snur brettet med klokken
     */
    private void clockWise() {
        fillUpClockCopy();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j==0) clockCopy.get(j).add(0,brett.get(i).get(j));
                if (j==1) clockCopy.get(j).add(0,brett.get(i).get(j));
                if (j==2) clockCopy.get(j).add(0,brett.get(i).get(j));
                if (j==3) clockCopy.get(j).add(0,brett.get(i).get(j));
            }
        }
        brett.clear();
        brett.addAll(clockCopy);
        clockCopy.clear();
    }

    /**
     * Metode som snur brettet mot klokken
     */
    private void counterClockWise() {
        fillUpClockCopy();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j==0) clockCopy.get(j).add(i,brett.get(i).get(3)); 
                if (j==1) clockCopy.get(j).add(i,brett.get(i).get(2));
                if (j==2) clockCopy.get(j).add(i,brett.get(i).get(1)); 
                if (j==3) clockCopy.get(j).add(i,brett.get(i).get(0));
            }
        }
        brett.clear();
        brett.addAll(clockCopy);
        clockCopy.clear();
    }

    /** 
     * Metode som speilvender brettet
    */
    private void mirrorBrett() {
        for (int i = 0; i < 4; i++) {
            Collections.reverse(brett.get(i));
        }
    }

    /**
     * Metode som sjekker om tilstanden til brettet har endret seg før man kan kalle på newTileWithCheck();
     * @return if flytteTeller > 0 returnerer true, else returner false
     */
    private boolean isValidMove() {
        if (flytteTeller > 0) return true;
        else return false;
    }

    /**
     * Metode som sjekker om man ikke har flere trekk igjen.
     * @return if change = 0 return true, else return false
     */
    private boolean noMovesLeft() {
        BoardGame tmp = this;
        ArrayList<Integer> kopi = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                kopi.add(tmp.getTile(j, i).getTileValue());
            }
        }
        BoardGame testArray = new BoardGame(kopi);
        GameOver compare = new GameOver();
        int change = compare.compare(testArray, tmp);
        if (change == 0) return true;
        return false;
    }

    /**
     * Metode for å sjekke om gameWon er true eller false
     * @return returnerer boolean true/false
     */
    public boolean gameWonCheck() {
        return gameWon;
    }

    /**
     * Metode som gjennomfører et standardtrekk (til høyre); legger Tiles med value 0 bakerst og legger sammen "naboTiles" m/ samme verdi.
     */
    private void masterMove() { 
        removeZeroI();
        addEqual();
    }

    private void preMove(String moveOrTest) {
        if (gameWon) throw new IllegalStateException("Kan ikke flytte etter at spillet er vunnet.");
        if (moveOrTest == "test") gameOverCheck = false;
        if (moveOrTest == "simple") {
            gameOverCheck = false; simpleMode = true;
        }
    }

    /**
     * Metoden kaller på newTileWithCheck(), setter flytteTeller til 0 og GameOverCheck til true, og printer ut brettet.
     */
    private void postMove() {
        newTileWithCheck();
        flytteTeller = 0;
        gameOverCheck = true;
        simpleMode = false;
    }

    /**
     * Metode som starter opp spillet; fyller brettet med 4x4 tiles med verdi 0 og legger til to stk random Tile. Endrer gameOverCheck og flytteTeller for å unngå exeptions. 
     */
    public void bootUp() {
        gameOverCheck = false;
        fillUpBrett(new ArrayList<Integer>(Collections.nCopies(16, 0)));
        flytteTeller = 1;
        newTileWithCheck();
        postMove();
    }

    /**
     * Metode for å flytte til høyre. Utfører masterMove() og postMove()
     * @param moveOrTest string som bestemmer om metoden utfører enten et faktisk trekk, eller om metoden skal testes i GameOver.
     */
    public void RIGHT(String moveOrTest) {
        preMove(moveOrTest);
        masterMove();
        postMove();
    }

    /**
     * Metode for å flytte til venstre. Utfører mirrorBrett(), masterMove() og postMove()
     * @param moveOrTest string som bestemmer om metoden utfører enten et faktisk trekk, eller om metoden skal testes i GameOver.
     */
    public void LEFT(String moveOrTest) {
        preMove(moveOrTest);
        mirrorBrett();
        masterMove();
        mirrorBrett();
        postMove();
    }

    /**
     * Metode for å flytte opp. Utfører clockWise(), masterMove(), counterClockWise() og postMove()
     * @param moveOrTest string som bestemmer om metoden utfører enten et faktisk trekk, eller om metoden skal testes i GameOver.
     */
    public void UP(String moveOrTest) {
        preMove(moveOrTest);
        clockWise();
        masterMove();
        counterClockWise();
        postMove();
    }

    /**
     * Metode for å flytte ned. Utfører counterClockWise(), masterMove(), clockWise() og postMove()
     * @param moveOrTest string som bestemmer om metoden utfører enten et faktisk trekk, eller om metoden skal testes i GameOver.
     */
    public void DOWN(String moveOrTest) {
        preMove(moveOrTest);
        counterClockWise();
        masterMove();
        clockWise();
        postMove();
    }

    @Override
    public String toString() {
        String s = "";
        for (ArrayList<Tile> arrayList : brett) {
            ArrayList<Integer> b = new ArrayList<Integer>();
            for (Tile tile : arrayList) {
                b.add(tile.getTileValue());
            }
            s+=b;
            s += "\n";
        }
        return "------------ \n" + s;
    }

    public static void main(String[] args) {
        ArrayList<Integer> liste = new ArrayList<Integer>();
        List<Integer> anotherList2 = Arrays.asList(
        1024,1024,2,8,
        4,2,4,8,
        2,4,2,4,
        4,2,4,2);
        liste.addAll(anotherList2);
        BoardGame test = new BoardGame(liste);
        System.out.println(test.toString());
        test.LEFT("m");
        System.out.println(test.toString());
        test.LEFT("m");


    }
}



