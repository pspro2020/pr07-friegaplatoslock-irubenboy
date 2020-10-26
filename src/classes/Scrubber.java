package classes;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Scrubber implements Runnable{

    private Random time = new Random();
    private DirtyTray dirtyTray;
    private CleanTray cleanTray;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Scrubber(DirtyTray dirtyTray, CleanTray cleanTray){
        Objects.requireNonNull(dirtyTray);
        Objects.requireNonNull(cleanTray);
        this.dirtyTray = dirtyTray;
        this.cleanTray = cleanTray;
    }


    @Override
    public void run() {
        Plate p;


        while(!Thread.currentThread().isInterrupted()){
            try {
                p = dirtyTray.extractPlate();
                TimeUnit.SECONDS.sleep(time.nextInt(5)+1);
            } catch (InterruptedException e) {
                System.out.printf("Scrubber has been interrupted while trying to extrat a dirty plate %s\n",
                        LocalDateTime.now().format(formatter));
                return;
            }

            p.clean(cleanTray);
            System.out.printf("Scrubber has adding a clean plate %s\n",
                    LocalDateTime.now().format(formatter));
        }

    }
}
