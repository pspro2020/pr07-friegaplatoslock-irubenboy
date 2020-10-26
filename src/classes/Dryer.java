package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Dryer implements Runnable{

    private final Random time = new Random();
    private final DryTray dryTray;
    private final CleanTray cleanTray;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Dryer(DryTray dryTray, CleanTray cleanTray){
        Objects.requireNonNull(dryTray);
        Objects.requireNonNull(cleanTray);
        this.dryTray = dryTray;
        this.cleanTray = cleanTray;
    }

    @Override
    public void run() {
        Plate p;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                p = cleanTray.extractCleanPlate();
                TimeUnit.SECONDS.sleep(time.nextInt(3) + 1);
            } catch (InterruptedException e) {
                System.out.printf("Dryer has been interrupted while trying to extract a clean plate %s\n",
                        LocalDateTime.now().format(formatter));
                return;
            }

            p.dry(dryTray);
            System.out.printf("Dryer has adding a dry plate %s\n",
                    LocalDateTime.now().format(formatter));
        }
    }
}
