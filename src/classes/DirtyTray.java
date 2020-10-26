package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DirtyTray {

    private ArrayList<Plate> dirtyPlates = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void addPlate(Plate p){
        dirtyPlates.add(p);
    }

    public Plate extractPlate() throws InterruptedException {
        Plate p;

        synchronized(this) {
            while (dirtyPlates.isEmpty()) {
                System.out.printf("Scrubber waiting to extract a dirty plate %s\n",
                    LocalDateTime.now().format(formatter));
                wait();
            }

            p = dirtyPlates.remove(0); // Coge el primer plato y lo elimina de la bandeja
            System.out.printf("Scrubber extract the plate #%d from dirty tray %s\n", p.getId(),
                    LocalDateTime.now().format(formatter));
            notifyAll();

            return p;
        }
    }
}
