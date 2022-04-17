import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class monitor1 {
    ArrayList<HashMap<String, Subsekvens>> hashBeholder;

    public monitor1() {
        this.hashBeholder = new ArrayList<>();
    }

    public synchronized void settInnHashmap(HashMap<String, Subsekvens> hashmap) {
        this.hashBeholder.add(hashmap);
    }

    public HashMap<String, Subsekvens> taUtHashmap(int index) {
        return this.hashBeholder.get(index);

    }

    public int antallHashmap() {
        return this.hashBeholder.size();
    }

    public int lesFraFil(String filDir) throws FileNotFoundException {
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
        this.hashBeholder.add(hashmap);
        scanner.close();
        return this.antallHashmap() - 1;
    }

    public void slaaSammen(int HashMapIndex1, int HashMapIndex2) {
        HashMap<String, Subsekvens> hasmap1 = this.hashBeholder.get(HashMapIndex1);
        HashMap<String, Subsekvens> hasmap2 = this.hashBeholder.get(HashMapIndex2);

        hasmap2.forEach((key, value) -> {
            Subsekvens subsekvens = hasmap1.get(key);

            if (subsekvens != null) {
                subsekvens.endreAntallForekomst(subsekvens.hentAntallForekomst() + value.hentAntallForekomst());
            } else {
                hasmap1.put(key, value);
            }
        });

        this.hashBeholder.remove(HashMapIndex2);
    }

    public void lesFraFolder(String foldername) throws FileNotFoundException {
        File text = new File(foldername + "/metadata.csv");
        Scanner scanner = new Scanner(text);

        ArrayList<String> filer = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            filer.add(line);
        }
        scanner.close();

        for (int i = 0; i < filer.size(); i++) {
            new LeseTrad(foldername, this);
            this.lesFraFil(foldername + "/" + filer.get(i));
        }
    }

    public void slaaSammenAlle() {
        while (this.hashBeholder.size() > 1) {
            this.slaaSammen(0, 1);
        }
    }

    public int hentForekomstAvKey(String key, int index) {
        return this.hashBeholder.get(index).get(key).hentAntallForekomst();
    }
}
