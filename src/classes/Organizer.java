package classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Organizer implements Runnable{
    private Random time = new Random();
    private DryTray dryTray;
    private Cupboard cupboard;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Organizer(DryTray dryTray, Cupboard cupboard){
        Objects.requireNonNull(dryTray);
        Objects.requireNonNull(cupboard);
        this.cupboard = cupboard;
        this.dryTray = dryTray;
    }

    @Override
    public void run() {
        Plate p;

        while (!Thread.currentThread().isInterrupted()){
            try {
                p = dryTray.extractDryPlate();
                TimeUnit.SECONDS.sleep(time.nextInt(2)+1);
            } catch (InterruptedException e) {
                System.out.printf("Organizer has been interrupted while trying to extract a dry plate %s\n",
                        LocalDateTime.now().format(formatter));
                return;
            }

            cupboard.addToCupboard(p);
            System.out.printf("Organizer has adding a plate to cupboard %s\n",
                    LocalDateTime.now().format(formatter));
        }

    }
}
