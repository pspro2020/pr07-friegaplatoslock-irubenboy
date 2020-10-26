package classes;

import java.util.ArrayList;

public class Cupboard {
    private ArrayList<Plate> finalPlates = new ArrayList<>();

    public void addToCupboard(Plate p){
        finalPlates.add(p);
    }
}
