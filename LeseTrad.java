import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LeseTrad extends Thread {
    int index;
    String filnavn;
    monitor2 monitor;

    @Override
    public void run() {
        System.out.println("Running Thread " + this.index);
        try {
            this.lesFraFil(filnavn, monitor);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        super.run();
    }

    LeseTrad(String filnavn, monitor2 monitor2, int index) throws FileNotFoundException {
        this.index = index;
        this.filnavn = filnavn;
        this.monitor = monitor2;
    }

    public void lesFraFil(String filDir, monitor2 monitor2) throws FileNotFoundException {
        File text = new File(filDir);
        Scanner scanner = new Scanner(text);
        HashMap<String, Subsekvens> hashmap = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                int toPosition = i + 3;

                if (toPosition > line.length()) {
                    break;
                }

                String subsekvens = line.substring(i, toPosition);
                if (hashmap.get(subsekvens) == null) {
                    hashmap.put(subsekvens, new Subsekvens(subsekvens, 1));
                }
            }
        }
        monitor2.settInnHashmap(hashmap);
        scanner.close();
    }
}
