import java.util.Set;

import na.edu.pon.primegame.player.PolytechnicCompetitionPlayer;

/**
 * @author Manfred Meyer
 *
 */
public abstract class Primspieler implements PolytechnicCompetitionPlayer
{
    /**
     * @see na.edu.pon.primegame.player.Player#makeMove(java.util.Set)
     */
    public Integer makeMove(Set<Integer> availableNumbers)
    {
        // construct array from set
        int[] numbers = new int[availableNumbers.size()];
        int i = 0;
        for (Integer number : availableNumbers)
        {
            numbers[i++] = number;
        }
        // get selection from method auswahl(int[])
        int selectedNumber = auswahl(numbers);
        // cast to object and return the selected number
        return (Integer) selectedNumber;
    }

    abstract public int auswahl(int[] numbers);

    @Override
    public String getStudentGroup()
    {
        // TODO Auto-generated method stub
        return "WH2023";
    }

    @Override
    public long getStudentNumber()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @see na.edu.pon.primegame.player.Player#setScore(int, int)
     */
    public void setScore(int playerScore, int opponentScore)
    {
        // we don't care much about the in-game score.. nothing to do here
    }
}