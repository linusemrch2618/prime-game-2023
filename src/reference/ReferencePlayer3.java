package na.edu.pon.primegame.players;

import java.util.Set;

public class ReferencePlayer3 extends ReferencePlayer2 {
   public ReferencePlayer3() {
      super("Reference Player III");
   }

   public Integer makeMove(Set<Integer> availableNumbers) {
      int[] numbers = this.convertIntegerSetToArray(availableNumbers);
      int bestAlmostSingleton = -1;

      for(int i = numbers.length - 1; i >= 0; --i) {
         if (this.isSingleton(numbers[i], numbers)) {
            return numbers[i];
         }

         if (this.isAlmostSingleton(numbers[i], numbers, 1) && bestAlmostSingleton == -1) {
            bestAlmostSingleton = numbers[i];
         }
      }

      return bestAlmostSingleton != -1 ? bestAlmostSingleton : numbers[numbers.length - 1];
   }
}
