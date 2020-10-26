package classes;

public class Plate {

    private int id;
    private static int plates = 0;

    public Plate(){
        plates++;
        this.id = plates;
    }

    public void clean(CleanTray cleanTray){
        cleanTray.addCleanPlate(this);
    }

    public void dry(DryTray dryPlates){
        dryPlates.addDryPlate(this);
    }

    public int getId() {
        return id;
    }
}
