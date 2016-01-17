package lonetlogingenerator;

/**
 *
 * @author Joern
 */
public class Student {
    private String vorname;
    private String nachname;
    private String klasse;
    private int stufe;
    private String lonetLogin;
    private String lonetPasswort;

    /**
     * Konstruktor
     *
     * @param klasse    der Name der Klasse des Schülers
     * @param vorname   der Vorname des Schülers
     * @param nachname  der Nachname des Schülers
    */
    public Student(String klasse, String vorname, String nachname) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.klasse = klasse;
        stufe = Integer.parseInt(klasse.replaceAll("[abcde]", ""));
        createLogin();
        this.lonetPasswort = createPassword();
    }

    /**
     * @return Vorname
     **/
    public String getVorname() {
        return vorname;
    }

    /**
     * @return Nachname
     **/
    public String getNachname() {
        return nachname;
    }

    /**
     * @return "Vorname Nachname"
     **/
    public String getName() {
        return vorname + " " + nachname;
    }

    /**
     * @return Klasse
     **/
    public String getKlasse() {
        return klasse;
    }

    /**
     * @return Stufe
     **/
    public int getStufe() {
        return stufe;
    }

    /**
     * @return LoNet²-Login
     **/
    public String getLogin() {
        return lonetLogin;
    }

    /**
     * @return LoNet²-Passwort
     **/
    public String getPasswort() {
        return lonetPasswort;
    }

    /**
     * Erzeugt den LoNet²-Login
     **/
    private void createLogin() {
       lonetLogin = sanitizeName(nachname) + "-" +
                    sanitizeName(vorname);
    }

    /**
     * Erzeugt das LoNet²-Passwort
     **/
    private String createPassword() {
        switch (klasse) {
            case "5a":
                return "Realschule";
            case "5b":
                return "Niedersachsen";
            case "5c":
                return "Deutschland";
            case "5d":
                return "Informatik";
            case "5e":
                return "Computer";
        }
        return "Realschule";
    }

    /**
     * Bereinigt den als Parameter übergebenen String.
     * Der String wird in Kleinbuchstaben gewandelt, Umlaute und Akzente werden
     * durch Internet-Kompatible Varianten ersetzt (ö->oe etc.)
     * Bei mehr als einem Namensteil wird nur der erste Name zurückgeliefert.
     *
     * @return  Internet-Kompatibler Name
     **/
    private static String sanitizeName(String name) {
        name = name.toLowerCase();
        name = name.replace("dos ", "dos-");
        name = name.replace("la ", "la-");
        name = name.replace("von ", "");
        name = name.replace("van ", "");

        if((int) name.indexOf(' ') > 0) {
            name = name.substring(0, name.indexOf(' '));
        }

        name = name.replaceAll("ä","ae");
        name = name.replaceAll("ö","oe");
        name = name.replaceAll("ü","ue");
        name = name.replaceAll("ß","ss");

        name = name.replaceAll("ç","c");
        name = name.replaceAll("é","e");
        name = name.replaceAll("è","e");
        name = name.replaceAll("š","s");
        name = name.replaceAll("ó","o");

        return name;
    }

    /**
     * Die Klasse Student implementiert das Interface Comparable,
     * damit die ArrayList aller Schüler-Instanzen nach Klassenzugehörigkeit
     * sortiert werden kann (Die Klasse Parser ruft dazu die Klassenmethode
     * Collections.sort auf).
     * Daher muss die abstrakte Methode compareTo hier implementiert werden.
     *
     * @param   o   Ein Student-Objekt, das mit dieser Instanz verglichen werden soll.
     * @return  einen negativen Integer, null oder einen positiven Integer,
     *          wenn das Vergleichs-Objekt kleiner als, gleich oder größer ist.
     **/
    public int compareTo( Student o ) {
        if (o.getKlasse() == null && this.getKlasse() == null) {
            return 0;
        }
        if (this.getKlasse() == null) {
            return 1;
        }
        if (o.getKlasse() == null) {
            return -1;
        }
        return this.getKlasse().compareTo(o.getKlasse());
    }

    /**
     * Die toString() Methode der Klasse Object wird überschrieben und alle
     * Datenfelder dieser Klasse werden übersichtlich zurückgegeben.
     *
     * @return  String aller Datenfelder
     */
    @Override
    public String toString() {
        return "Name: " + vorname + " " + nachname + "\n" +
               "Klasse: " + klasse + "\n" +
               "Login: " + lonetLogin + "\n" +
               "Passwort: " + lonetPasswort;
    }    
}
