import java.io.FileNotFoundException;

public class Oblig5Del1 {

    public static void main(String[] args) throws Exception {
        test("testdatalitenlike", "ASS", 3); // Nice
        test("testdatalike", "QYF", 9);
    }

    public static void test(String folderName, String key, int occurrence) throws FileNotFoundException {
        SubsekvensRegister register = new SubsekvensRegister();
        register.lesFraFolder(folderName);
        register.slaaSammenAlle();
        int forekomst = register.hentForekomstAvKey(key, 0);
        System.out.println(String.format("testdatalitenlike: %s === %s", occurrence, forekomst));
    }
}
