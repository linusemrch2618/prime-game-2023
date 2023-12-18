import java.util.Arrays;
import java.util.TreeSet;
import java.util.Iterator;

public class MoenchVonLuetzerath extends Primspieler {
    public static class Zug implements Comparable<Zug> {
        private final int index;
        private final int zahl;
        private final int gewinn;

        private Zug(int index, int zahl, int gewinn) {
            this.index = index;
            this.zahl = zahl;
            this.gewinn = gewinn;
        }

        public int compareTo(Zug z) {
            return z.gewinn - this.gewinn;
        }
    }


    public int auswahl(int[] feld) {
        int anzahlZuege = Math.max(10, (int)(feld.length * 0.1D));
        TreeSet<Zug> besteZuege = getBesteZuege(feld, anzahlZuege);

        return getBesterZug(besteZuege, feld).zahl;
    }

    public TreeSet<Zug> getBesteZuege(int[] feld, int anzahlZuege) {
        TreeSet<Zug> besteZuege = new TreeSet<>();
        for (int i = feld.length - 1; i >= 0; i--) {
            Zug zug = new Zug(i, feld[i], getWert(i, feld) - getFaktorSumme(i, feld));
            besteZuege.add(zug);
            if (besteZuege.size() > anzahlZuege)
                besteZuege.remove(besteZuege.last());
            if (feld[i] <= besteZuege.last().gewinn)
                break;
        }

        return besteZuege;
    }

    public int getWert(int index, int[] feld) {
        int zahl = feld[index];
        int wert = zahl;
        if (isPrime(zahl)) {
            if (zahl % 2 != 0) {
                wert = -(zahl + 1) / 2;
            } else {
                wert = -zahl / 2;
            }
        }

        return wert;
    }

    private boolean isPrime(int zahl) {
        boolean isPrime = true;
        for(int teiler = 2; (double)teiler <= Math.sqrt(zahl); ++teiler) {
            if (zahl % teiler == 0) {
                isPrime = false;
                break;
            }
        }

        return isPrime;
    }

    public int getFaktorSumme(int index, int[] feld) {
        int summe = 0;
        for (int i = 0; i < index; i++) {
            if ((feld[index] % feld[i]) == 0) {
                summe += feld[i];
            }
        }

        return summe;
    }

    public Zug getBesterZug(TreeSet<Zug> besteZuege, int[] feld) {
        Iterator<Zug> it = besteZuege.iterator();
        int maxScore = Integer.MIN_VALUE;
        Zug besterZug = besteZuege.iterator().next();
        while (it.hasNext()) {
            Zug z = it.next();
            int[] gegnerFeld = getNeuesFeld(feld, z);
            int anzahlZuege = Math.max(10, (int)(gegnerFeld.length * 0.1D));
            TreeSet<Zug> besteZuegeGegner = getBesteZuege(gegnerFeld, anzahlZuege);
            if (!besteZuegeGegner.isEmpty()) {
                int score = z.gewinn - getBesterZugGegner(besteZuegeGegner, gegnerFeld).gewinn;
                if (score > maxScore) {
                    besterZug = z;
                    maxScore = score;
                }
            }
        }

        return besterZug;
    }

    public Zug getBesterZugGegner(TreeSet<Zug> besteZuegeGegner, int[] feld) {
        Iterator<Zug> itGegner = besteZuegeGegner.iterator();
        int maxScoreGegner = Integer.MIN_VALUE;
        Zug besterZugGegner = besteZuegeGegner.iterator().next();
        while (itGegner.hasNext()) {
            Zug z = itGegner.next();
            int[] gegnerFeld = getNeuesFeld(feld, z);
            TreeSet<Zug> besteZuegeLv2 = getBesteZuege(gegnerFeld, 1);
            if (!besteZuegeLv2.isEmpty()) {
                int score = z.gewinn - besteZuegeLv2.iterator().next().gewinn;
                if (score > maxScoreGegner) {
                    besterZugGegner = z;
                    maxScoreGegner = score;
                }

            }
        }

        return besterZugGegner;
    }

    public int[] getNeuesFeld(int[] feld, Zug z) {
        int[] neuesFeld = Arrays.copyOf(feld, feld.length);
        int kuerzen = 0;
        for (int i = z.index; i >= 0; i--) {
            if (z.zahl % neuesFeld[i] == 0) {
                for (int j = i; j < neuesFeld.length - 1; j++) {
                    neuesFeld[j] = neuesFeld[j + 1];
                }
                kuerzen++;
            }
        }
        neuesFeld = Arrays.copyOf(neuesFeld, neuesFeld.length - kuerzen);

        return neuesFeld;
    }

    public String getPlayerName() {
        return "MoenchVonLuetzerath";
    }

    public long getStudentNumber() {
        return 202011111;  // Ihre Spielernummer
    }

    // Bitte nicht vergessen, ein Hochformat-Bild im JPG-Format
    // als Datei <Matrikelnummer>.jpg mit einzureichen!
}
