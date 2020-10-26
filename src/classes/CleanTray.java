package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CleanTray {

    private ArrayList<Plate> cleanPlates = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void addCleanPlate(Plate p){
        cleanPlates.add(p);
    }

    public Plate extractCleanPlate() throws InterruptedException {
        Plate p;

        synchronized(this) {
            while (cleanPlates.isEmpty()) {
                System.out.printf("Dryer waiting to extract a clean plate %s\n",
                        LocalDateTime.now().format(formatter));
                wait();
            }
            p = cleanPlates.remove(0); // coge el primer plato limpio
            System.out.printf("Dyer extract a plate #%d from clean tray %s\n", p.getId(),
                    LocalDateTime.now().format(formatter));
            notify();

            return p;
        }
    }
}
