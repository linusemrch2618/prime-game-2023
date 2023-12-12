public class BeispielSpieler extends Primspieler {

    public int auswahl(int[] zahlen)
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int ausgewaehlteZahl = 42;   // Hier sind Sie gefragt...
        // 42 ist nur ein Beispiel, das natuerlich nicht funktioniert!
        return ausgewaehlteZahl;
    }

    public String getPlayerName()
    {
        return "Irgendein schöner Name"; // Bitte anpassen!
    }

    public long getStudentNumber()
    {
        return 202111111;  // Ihre Spielerkennung, diese erhalten Sie von
        // uns, Studierende der Westfälischen Hochschule
        // setzen hier bitte ihre Matrikelnummer ein
    }

    // Bitte nicht vergessen, ein Hochformat-Bild im JPG-Format
    // als Datei Spielerkennung.jpg mit einzureichen!
}