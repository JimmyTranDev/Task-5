import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class FletteTrad extends Thread {
    monitor2 monitor;
    ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            try {
                if (monitor.antallHashmap() > 1) {
                    this.slaaSammen();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public FletteTrad(monitor2 monitor) {
        this.monitor = monitor;
    }

    public void slaaSammen() throws InterruptedException {

        ArrayList<HashMap<String, Subsekvens>> cake = this.monitor.taUtHashmap2Siste();

        HashMap<String, Subsekvens> hasmap1 = cake.get(0);
        HashMap<String, Subsekvens> hasmap2 = cake.get(1);

        hasmap2.forEach((key, value) -> {
            Subsekvens subsekvens = hasmap1.get(key);

            if (subsekvens != null) {
                subsekvens.endreAntallForekomst(subsekvens.hentAntallForekomst() + value.hentAntallForekomst());
            } else {
                hasmap1.put(key, value);
            }
        });
        this.monitor.settInnHashmap(hasmap1);

        // this.monitor.taUtHashmap(0);
        // this.monitor.settInnHashmap(hasmap1);
    }
}
