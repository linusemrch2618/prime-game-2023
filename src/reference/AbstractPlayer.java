package na.edu.pon.primegame.players;

import java.util.Iterator;
import java.util.Set;

public abstract class AbstractPlayer implements Player {
   private int playerScore = 0;
   private int opponentScore = 0;
   private String playerName;

   public AbstractPlayer() {
      this.setDefaultPlayerName();
   }

   protected void setDefaultPlayerName() {
      this.setPlayerName(this.getClass().getName());
   }

   public AbstractPlayer(String playerName) {
      this.playerName = playerName;
   }

   public void setScore(int playerScore, int opponentScore) {
      this.playerScore = playerScore;
      this.opponentScore = opponentScore;
   }

   public String getPlayerName() {
      return this.playerName;
   }

   public abstract Integer makeMove(Set<Integer> var1);

   public void setPlayerName(String playerName) {
      this.playerName = playerName;
   }

   protected int calculateMove(int playerMove, Set<Integer> availableNumbers) {
      if (!availableNumbers.contains(playerMove)) {
         throw new IllegalArgumentException("Impossible move: " + playerMove + ". This number is not available anymore.");
      } else {
         return playerMove - this.calculateOpponentPoints(playerMove, availableNumbers);
      }
   }

   protected int calculateOpponentPoints(int playerMove, Set<Integer> availableNumbers) {
      if (!availableNumbers.contains(playerMove)) {
         throw new IllegalArgumentException("Impossible move: " + playerMove + ". this number is not available anymore.");
      } else {
         int opponentPoints = 0;
         Iterator var5 = availableNumbers.iterator();

         while(var5.hasNext()) {
            Integer i = (Integer)var5.next();
            if (i < playerMove && playerMove % i == 0) {
               opponentPoints += i;
            }
         }

         return opponentPoints;
      }
   }

   public int getPlayerScore() {
      return this.playerScore;
   }

   public int getOpponentScore() {
      return this.opponentScore;
   }
}
