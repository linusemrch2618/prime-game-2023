package na.edu.pon.primegame.players;

import java.util.Set;

public class ReferencePlayer2 extends AbstractReferencePlayer {
   public ReferencePlayer2() {
      this("Reference Player II");
   }

   public ReferencePlayer2(String name) {
      super(name);
   }

   public Integer makeMove(Set<Integer> availableNumbers) {
      int[] numbers = this.convertIntegerSetToArray(availableNumbers);

      for(int i = numbers.length - 1; i >= 0; --i) {
         if (this.isSingleton(numbers[i], numbers)) {
            System.out.println("Found singleton " + numbers[i]);
            return new Integer(numbers[i]);
         }
      }

      return new Integer(numbers[numbers.length - 1]);
   }
}
