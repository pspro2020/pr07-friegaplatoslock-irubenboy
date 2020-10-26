import classes.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        DirtyTray dirtyTray = new DirtyTray();
        CleanTray cleanTray = new CleanTray();
        DryTray dryTray = new DryTray();
        Cupboard cupboard = new Cupboard();

        for (int i = 0; i < 10; i++) {
            Plate p = new Plate();
            dirtyTray.addPlate(p);
        }

        Thread scrubber = new Thread(new Scrubber(dirtyTray, cleanTray), "Scrubber");
        Thread dryer = new Thread(new Dryer(dryTray, cleanTray), "Dryer");
        Thread organizer = new Thread(new Organizer(dryTray, cupboard));

        scrubber.start();
        dryer.start();
        organizer.start();
        Thread.sleep(60000);
        scrubber.interrupt();
        dryer.interrupt();
        organizer.interrupt();

        System.out.println("Happy Birthday !!");

    }
}
