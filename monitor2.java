import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class monitor2 {
    ArrayList<HashMap<String, Subsekvens>> hashBeholder;

    public monitor2() {
        this.hashBeholder = new ArrayList<>();
    }

    public synchronized void settInnHashmap(HashMap<String, Subsekvens> hashmap) {
        this.hashBeholder.add(hashmap);
    }

    public synchronized HashMap<String, Subsekvens> taUtHashmap() {
        HashMap<String, Subsekvens> removed = this.hashBeholder.remove(this.antallHashmap() - 1);
        return removed;
    }

    public synchronized ArrayList<HashMap<String, Subsekvens>> taUtHashmap2Siste() throws InterruptedException {

        if (this.antallHashmap() < 2) {
            // for (int i = 0; i < 10; i++) {
            // if (this.antallHashmap() > 2) {
            // break;
            // }
            // wait(200);
            // }
            throw new InterruptedException("No more Hashmaps");
        }

        ArrayList<HashMap<String, Subsekvens>> removeds = new ArrayList<>();
        HashMap<String, Subsekvens> removed1 = this.hashBeholder.get(this.antallHashmap() - 1);
        this.hashBeholder.remove(this.antallHashmap() - 1);
        removeds.add(removed1);

        HashMap<String, Subsekvens> removed2 = this.hashBeholder.get(this.antallHashmap() - 1);
        this.hashBeholder.remove(this.antallHashmap() - 1);
        removeds.add(removed2);

        return removeds;
    }

    // public synchronized HashMap<String, Subsekvens> taUtHashmap2() {
    // HashMap<String, Subsekvens> removed1 =
    // this.hashBeholder.get(this.hashBeholder.size() - 1);
    // HashMap<String, Subsekvens> removed2 =
    // this.hashBeholder.get(this.hashBeholder.size() - 2);
    // this.hashBeholder.remove(this.hashBeholder.size() - 1);
    // this.hashBeholder.remove(this.hashBeholder.size() - 2);
    // return [removed1, removed2];
    // }

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

    public void lesFraFolder(String foldername) throws FileNotFoundException, InterruptedException {
        File text = new File(foldername + "/metadata.csv");
        Scanner scanner = new Scanner(text);

        ArrayList<String> filer = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            filer.add(line);
        }
        scanner.close();

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < filer.size(); i++) {
            String filnavn = foldername + "/" + filer.get(i);
            Thread trad = new LeseTrad(filnavn, this, i);

            trad.start();
            threads.add(trad);
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }

    public void slaaSammenAlle() throws InterruptedException {
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Thread fletteTrad = new FletteTrad(this);
            fletteTrad.start();
            threads.add(fletteTrad);
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(this.antallHashmap());

    }

    public int hentForekomstAvKey(String key, int index) {
        return this.hashBeholder.get(index).get(key).hentAntallForekomst();
    }
}
