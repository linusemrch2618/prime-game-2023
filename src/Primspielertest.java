import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import na.edu.pon.primegame.competition.Logging;
import na.edu.pon.primegame.engine.PrimeGame;
import na.edu.pon.primegame.engine.PrimeGameFactory;
import na.edu.pon.primegame.engine.PrimeGameRules;
import na.edu.pon.primegame.engine.Score;
import na.edu.pon.primegame.player.Player;
import na.edu.pon.primegame.player.PolytechnicCompetitionPlayer;
import na.edu.pon.primegame.trainer.TrainingRules;
import na.edu.pon.primegame.util.LoggingSupport;

/**
 * The Primspieltest class provides a training environment for testing player
 * implementations. It will load two PrimeGame player classes (which must be
 * accessible in the classpath), and let them play two matches against each
 * other (with each player being the "home" player once).
 *
 * @author <a href="mailto:manfred.meyer@w-hs.de">Manfred Meyer</a>
 *
 * ReferencePlayer1 for testing - na.edu.pon.primegame.player.reference.ReferencePlayer1
 *
 */
public class Primspielertest implements Logging {

    /**
     * The size of the board. We use 200 per default.
     */
    private static int BOARD_SIZE = 200;

    /**
     * The rule set to use is based on the Competition Rules, but will try not
     * disqualify players immediately, but let them play on. It should be noted
     * that during competitions, a stricter rule-set is applied.
     */
    private static final PrimeGameRules RULES = new TrainingRules();

    private static final long NO_STUDENT_NUMBER = 0;

    /**
     * Fully-qualified class name of the 1st player
     */
    private String home;

    /**
     * Fully-qualified class name of the 2nd player
     */
    private String guest;

    /**
     * Object reference for player 1 (once loaded)
     */
    private PolytechnicCompetitionPlayer playerA;

    /**
     * Object reference for player 2 (once loaded)
     */
    private PolytechnicCompetitionPlayer playerB;

    /**
     * The PrimeGame engine object
     */
    private PrimeGame pg;

    /**
     * The PrimeGame factory object to use for creating instances of the game
     * engine
     */
    private PrimeGameFactory pgf;

    /**
     * PrimeGameTrainer Constructor
     *
     * @param home
     *            is the fully qualified class name of the 1st player
     * @param guest
     *            is the fully qualified class name of the 2nd player
     */
    public Primspielertest(String home, String guest) {
        super();
        this.home = home;
        this.guest = guest;
        loadPlayerClasses();
    }

    /**
     * This method loads the player's classes and instantiates Player objects
     * for them
     *
     */
    private void loadPlayerClasses() {
        try {
            playerA = (PolytechnicCompetitionPlayer) Class.forName(home).getDeclaredConstructor().newInstance();
            playerB = (PolytechnicCompetitionPlayer) Class.forName(guest).getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            error("Failed to instantiate player: " + e.getLocalizedMessage(), 10);
        } catch (IllegalAccessException e) {
            error("Could not call player constructor: " + e.getLocalizedMessage(), 11);
        } catch (ClassNotFoundException e) {
            error("Could not find player class in CLASSPATH: " + e.getLocalizedMessage(), 12);
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            error("Something special went wrong: " + e.getLocalizedMessage(), 13);
        }
    }

    /**
     * This method starts the training matches after the players have been
     * loaded
     */
    private void startTraining() {
        if (playerA == null || playerB == null) {
            error("Player objects not initialised!", 20);
        }

        pgf = new PrimeGameFactory(BOARD_SIZE, RULES, null);

        int aPoints = 0;
        int bPoints = 0;

        // home round
        message("Playing match 1 of 2:");
        Player winner = playMatch(playerA, playerB);
        if (winner == playerA) {
            aPoints += 3;
        } else if (winner == playerB) {
            bPoints += 3;
        } else {
            aPoints++;
            bPoints++;
        }

        // guest round
        message("\nPlaying match 2 of 2:");
        winner = playMatch(playerB, playerA);
        if (winner == playerA) {
            aPoints += 3;
        } else if (winner == playerB) {
            bPoints += 3;
        } else {
            aPoints++;
            bPoints++;
        }
        pg.play();

        // final points
        message("");
        message("FINAL MATCH POINTS: ");
        message("  " + playerA.getPlayerName() + ": " + aPoints);
        message("  " + playerB.getPlayerName() + ": " + bPoints);
        message("");

        if (playerA.getStudentNumber() == NO_STUDENT_NUMBER) {
            System.out.println("WARNING: ");
            System.out.println("Player "+home+" does not return a valid student number. Please add code to method getStudentNumber() to return a ");
            System.out.println("valid student number <StudentNr> and include an appropriate jpg file (named <StudentNr>.jpg) with your submission!");
            System.out.flush();
        }
        if (playerB.getStudentNumber() == NO_STUDENT_NUMBER) {
            System.out.println("WARNING: ");
            System.out.println("Player "+guest+" does not return a valid student number. Please add code to method getStudentNumber() to return a ");
            System.out.println("valid student number <StudentNr> and include an appropriate jpg file (named <StudentNr>.jpg) with your submission!");
            System.out.flush();
        }

    }

    private Player playMatch(Player homePlayer, Player guestPlayer) {
        pg = pgf.getGameInstance(homePlayer, guestPlayer);
        PropertyChangeListener listener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                dumpEvent(evt);
            }
        };
        pg.addPropertyChangeListener(listener);
        pg.play();
        pg.removePropertyChangeListener(listener);
        return pg.getWinner();
    }

    /**
     * Print out a game event as reported from the engine.
     *
     * @param evt
     *            is the PrimeGame event to be dumped to the console
     */
    @SuppressWarnings("unchecked")
    protected void dumpEvent(PropertyChangeEvent evt) {
        String e;
        if ((e = evt.getPropertyName()) == null) {
            return;
        }

        if (PrimeGame.EVENT_BOARD.equals(e)) {
            message("Board is set up with numbers up to " + ((Set<Integer>) evt.getNewValue()).size());

        } else if (PrimeGame.EVENT_GAME_OVER.equals(e)) {
            message("Game Over. Player \"" + pg.getWinner().getPlayerName() + "\" won the game!");
            message("Final Score: " + pg.getHomePlayer().getPlayerName() + " " + pg.getHomeScore() + ":"
                    + pg.getGuestScore() + " " + pg.getGuestPlayer().getPlayerName());

        } else if (PrimeGame.EVENT_MOVE.equals(e)) {
            Score s = (Score) evt.getNewValue();

            int scoreChanger = 0;

            // THM for Special Rules Christmas Edition 2023
            scoreChanger = pg.specialRule2023(s.getScore());
            // end of THM

            if (scoreChanger < 0)
                message("Player \"" + s.getPlayer().getPlayerName() + "\" made a move, took " + s.getScore() + " and gets only " + (s.getScore()+scoreChanger));
            else if (scoreChanger > 0)
                message("Player \"" + s.getPlayer().getPlayerName() + "\" made a move, took " + s.getScore() + " and gets additional " + scoreChanger);
            else
                message("Player \"" + s.getPlayer().getPlayerName() + "\" made a move and took " + s.getScore());

        } else if (PrimeGame.EVENT_SCORE_DETAILS.equals(e)) {
            Player p = (Player) evt.getOldValue();
            Integer[] divisors = (Integer[]) evt.getNewValue();
            if (divisors != null && divisors.length > 0) {
                message("Player \"" + p.getPlayerName() + "\" gets " + arrayToString(divisors));
            } else {
                message("Player \"" + p.getPlayerName() + "\" gets nothing.");
            }

        } else if (PrimeGame.EVENT_STOP_WATCH.equals(e)) {
            Player p = (Player) evt.getOldValue();
            Long t = (Long) evt.getNewValue();
            message("Player \"" + p.getPlayerName() + "\" used " + t + " ms. for the move.");
        }
    }

    /**
     * Convert an array of Integers to a String
     *
     * @param divisors
     *            is the array to be converted
     * @return a String containing all objects in the divisors array, separated
     *         by commas
     */
    private String arrayToString(Integer[] divisors) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < divisors.length; i++) {
            sb.append(divisors[i]);
            if (i < (divisors.length - 1)) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    /**
     * Print out an error message to the console and quit the program
     *
     * @param msg
     *            the message to be printed out
     * @param errorlevel
     *            the errorlevel to report to the host OS
     */
    private void error(String msg, int errorlevel) {
        System.err.println("\n" + msg);
        System.err.flush();
        System.exit(errorlevel);
    }

    /**
     * Print out a message to the console and flush the output stream
     * immediately
     *
     * @param msg
     *            the message to be printed out
     */
    private void message(String msg) {
        System.out.println(msg);
        System.out.flush();
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
        LoggingSupport.disableConsoleLogging();
        System.out.println("Primspielertest 0.2 - Copyright (c) 2009,2010 by Manfred Meyer\n");
        System.out.flush();
        Primspielertest trainer = null;
        if  ((args.length == 1 && "-i".equalsIgnoreCase(args[0])) || (args.length == 0)) {
            // interactive mode
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            /*System.out.println("Player A (Home): ");
            System.out.flush();*/
            String home;
            try {
                home = "MoenchVonLuetzerath";// br.readLine();
                /*System.out.println("Player B (Guest): ");
                System.out.flush();*/
                String guest = "Test2";// br.readLine();
                trainer = new Primspielertest(home, guest);
            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.exit(1);
            }
            /*System.out.println("Board size (empty string for default = "+BOARD_SIZE+"): ");
            System.out.flush();
            String size;
            try {
                size = br.readLine();
                BOARD_SIZE = Integer.parseInt(size);
            } catch (Exception e) {
                System.out.println("Board size set to default = "+BOARD_SIZE+".");
                System.out.flush();
            }*/
            BOARD_SIZE=175;
        } else if (args.length >= 2) {
            trainer = new Primspielertest(args[0], args[1]);
            if (args.length >= 3)
                BOARD_SIZE = Integer.parseInt(args[2]);
        } else {
            System.err.println("Usage: Primspielertest [-i] [[<home> <guest>] [<board size>]]\n");
            System.err
                    .println("  If the -i parameter is given, you will be asked to specify player class names during run-time.");
            System.err.println();
            System.err.println("  <home> and <guest> are fully-qualified class names (in the CLASSPATH)");
            System.err
                    .println("  of the players to run. Both player classes must implement the \"Player\" interface.");
            System.err.flush();
            System.exit(2);
        }

        trainer.startTraining();

        System.exit(0);
    }
}
