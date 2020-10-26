package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CleanTray {

    private final ArrayList<Plate> cleanPlates = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final Lock lock = new ReentrantLock(true);
    private final Condition isNonEmpty = lock.newCondition();

    public void addCleanPlate(Plate p){
        cleanPlates.add(p);
    }

    public Plate extractCleanPlate() throws InterruptedException {
        Plate p;
        lock.lock();
        try {
            while (cleanPlates.isEmpty()) {
                System.out.printf("Dryer waiting to extract a clean plate %s\n",
                        LocalDateTime.now().format(formatter));
                isNonEmpty.await();
            }
            p = cleanPlates.remove(0); // coge el primer plato limpio
            System.out.printf("Dyer extract a plate #%d from clean tray %s\n", p.getId(),
                    LocalDateTime.now().format(formatter));
            isNonEmpty.signal();
            return p;
        } finally {
            lock.unlock();
        }



    }
}
