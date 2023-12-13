package na.edu.pon.primegame.players;

import java.util.Set;

public interface Player {
   String getPlayerName();

   Integer makeMove(Set<Integer> var1);

   void setScore(int var1, int var2);
}
