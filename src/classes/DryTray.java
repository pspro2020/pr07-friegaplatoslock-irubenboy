package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DryTray {
    private final ArrayList<Plate> dryPlates = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Lock lock = new ReentrantLock(true);
    private final Condition isNonEmpty = lock.newCondition();


    public void addDryPlate(Plate p){
        dryPlates.add(p);
    }

    public Plate extractDryPlate() throws InterruptedException {
        Plate p;
        lock.lock();

        try{
            while (dryPlates.isEmpty()) {
                System.out.printf("Organizer waiting to extract a dry plate %s\n",
                        LocalDateTime.now().format(formatter));
                isNonEmpty.await();
            }

            p = dryPlates.remove(0); // Coge el primer plato
            System.out.printf("Organizer extract a plate #%d from dry plate %s\n", p.getId(),
                    LocalDateTime.now().format(formatter));
            isNonEmpty.signal();
            return p;
        } finally {
            lock.unlock();
        }
    }
}
