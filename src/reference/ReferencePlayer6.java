package na.edu.pon.primegame.players;

import java.util.Set;

public class ReferencePlayer6 extends AbstractReferencePlayer {
   private double ownWeight;
   private double oppWeight;

   public ReferencePlayer6() {
      this(1.119D, 1.0D);
   }

   public ReferencePlayer6(double wown, double wopp) {
      super("Reference Player 6");
      this.setPlayerName("ReferencePlayer 6 (" + wown + "/" + wopp + ")");
      this.ownWeight = wown;
      this.oppWeight = wopp;
   }

   public Integer makeMove(Set<Integer> availableNumbers) {
      int[] numbers = this.convertIntegerSetToArray(availableNumbers);
      int bestNumber = numbers[0];
      double bestScore = Double.MIN_VALUE;

      for(int i = 0; i < numbers.length; ++i) {
         int playerScoreForNi = numbers[i];
         int opponentScoreForNi = this.sumFactors(i, numbers);
         double gain = (double)playerScoreForNi * this.ownWeight - (double)opponentScoreForNi * this.oppWeight;
         if (gain > bestScore) {
            bestScore = gain;
            bestNumber = numbers[i];
         }
      }

      return bestNumber;
   }
}
