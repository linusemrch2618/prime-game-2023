package na.edu.pon.primegame.players;

import java.util.Set;

public class ReferencePlayer1 extends AbstractReferencePlayer {
   public ReferencePlayer1() {
      super("Reference Player I");
   }

   public Integer makeMove(Set<Integer> availableNumbers) {
      int[] numbers = this.convertIntegerSetToArray(availableNumbers);

      for(int i = numbers.length - 1; i >= 0; --i) {
         if (this.isPrime(numbers[i])) {
            return new Integer(numbers[i]);
         }
      }

      return new Integer(numbers[numbers.length - 1]);
   }
}
