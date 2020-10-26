package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DryTray {
    private ArrayList<Plate> dryPlates = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public void addDryPlate(Plate p){
        dryPlates.add(p);
    }

    public Plate extractDryPlate() throws InterruptedException {
        Plate p;

        synchronized(this) {
            while (dryPlates.isEmpty()) {
                System.out.printf("Organizer waiting to extract a dry plate %s\n",
                        LocalDateTime.now().format(formatter));
                wait();
            }

            p = dryPlates.remove(0); // Coge el primer plato
            System.out.printf("Organizer extract a plate #%d from dry plate %s\n", p.getId(),
                    LocalDateTime.now().format(formatter));
            notifyAll();
            return p;
        }
    }
}
