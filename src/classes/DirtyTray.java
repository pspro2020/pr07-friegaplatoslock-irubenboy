package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DirtyTray {

    private final ArrayList<Plate> dirtyPlates = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Lock lock = new ReentrantLock(true);
    private final Condition isNonEmpty = lock.newCondition();

    public void addPlate(Plate p){
        dirtyPlates.add(p);
    }

    public Plate extractPlate() throws InterruptedException {
        Plate p;
        lock.lock();

        try {
            while (dirtyPlates.isEmpty()) {
                System.out.printf("Scrubber waiting to extract a dirty plate %s\n",
                        LocalDateTime.now().format(formatter));
                isNonEmpty.await();
            }

            p = dirtyPlates.remove(0); // Coge el primer plato y lo elimina de la bandeja
            System.out.printf("Scrubber extract the plate #%d from dirty tray %s\n", p.getId(),
                    LocalDateTime.now().format(formatter));
            isNonEmpty.signal();

            return p;
        } finally {
            lock.unlock();
        }
    }
}
