public class Subsekvens {
    public final String subsekvens;
    private int antall;

    public Subsekvens(String subsekvens, int antall) {
        this.antall = antall;
        this.subsekvens = subsekvens;
    }

    public int hentAntallForekomst() {
        return this.antall;
    }

    public void endreAntallForekomst(int antall) {
        this.antall = antall;
    }

    @Override
    public String toString() {
        return String.format("(%s,%s)", this.subsekvens, this.antall);
    }
}
