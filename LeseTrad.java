import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class LeseTrad extends Thread {
    @Override
    public void run() {
        super.run();
    }

    LeseTrad(String filnavn, monitor1 monitor) throws FileNotFoundException {
        this.lesFraFil(filnavn, monitor);
    }

    public void lesFraFil(String filDir, monitor1 monitor) throws FileNotFoundException {
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
        monitor.hashBeholder.add(hashmap);
        scanner.close();
    }
}
