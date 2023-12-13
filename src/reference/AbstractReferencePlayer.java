package na.edu.pon.primegame.players;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractReferencePlayer implements PolytechnicCompetitionPlayer {
   protected static Set<Integer> positivePrimeCache = new HashSet(1000);
   protected static Set<Integer> negativePrimeCache = new HashSet(1000);
   protected int playerScore = 0;
   protected int opponentScore = 0;
   protected String playerName;
   protected boolean newGame = false;
   protected int boardSize = 0;
   protected int numbersLeft = 0;
   protected int factorSum;
   protected int factorCount;

   protected AbstractReferencePlayer() {
      this.playerName = this.getClass().getName();
   }

   protected AbstractReferencePlayer(String name) {
      this.playerName = name;
   }

   public String getStudentGroup() {
      return "PoN Staff";
   }

   public long getStudentNumber() {
      return 1L;
   }

   public String getPlayerName() {
      return this.playerName;
   }

   public abstract Integer makeMove(Set<Integer> var1);

   protected boolean isFirstMove(Set<Integer> availableNumbers) {
      return availableNumbers.contains(1);
   }

   protected int[] convertIntegerSetToArray(Set<Integer> set) {
      int[] array = new int[set.size()];
      int i = 0;

      int number;
      for(Iterator var5 = set.iterator(); var5.hasNext(); array[i++] = number) {
         number = (Integer)var5.next();
      }

      return array;
   }

   public void setScore(int playerScore, int opponentScore) {
      this.playerScore = playerScore;
      this.opponentScore = opponentScore;
   }

   protected Set<Integer> availableNumbersAfterMove(int number, Set<Integer> availableNumbers) {
      Set<Integer> newNumbers = new TreeSet(availableNumbers);
      newNumbers.remove(number);

      for(int n = 1; (double)n <= Math.sqrt((double)number); ++n) {
         if (number % n == 0 && newNumbers.contains(n)) {
            newNumbers.remove(n);
         }
      }

      return newNumbers;
   }

   protected boolean isPrime(int n) {
      if (positivePrimeCache.contains(n)) {
         return true;
      } else if (negativePrimeCache.contains(n)) {
         return false;
      } else {
         for(int i = 2; (double)i <= Math.ceil(Math.sqrt((double)n)); ++i) {
            if (n % i == 0) {
               negativePrimeCache.add(n);
               return false;
            }
         }

         positivePrimeCache.add(n);
         return true;
      }
   }

   protected boolean isSingleton(int i, int[] numbers) {
      return this.isAlmostSingleton(i, numbers, 0);
   }

   protected boolean isAlmostSingleton(int chosenNumber, int[] numbers, int maxDivisors) {
      int counter = 0;

      for(int j = 0; (double)numbers[j] <= Math.ceil(Math.sqrt((double)chosenNumber)); ++j) {
         if (numbers[j] != 1 && chosenNumber % numbers[j] == 0) {
            ++counter;
         }
      }

      if (counter <= maxDivisors) {
         return true;
      } else {
         return false;
      }
   }

   protected int sumFactors(int n, int[] numbers) {
      int sum = 0;

      for(int i = 0; i < n; ++i) {
         if (numbers[n] % numbers[i] == 0) {
            sum += numbers[i];
         }
      }

      return sum;
   }

   protected void processFactors(int n, int[] numbers) {
      this.factorSum = 0;
      this.factorCount = 0;

      for(int i = 0; i < n; ++i) {
         if (numbers[n] % numbers[i] == 0) {
            ++this.factorCount;
            this.factorSum += numbers[i];
         }
      }

   }

   protected void analyseGame(Set<Integer> availableNumbers) {
      int size = availableNumbers.size();
      if (size >= this.numbersLeft) {
         this.newGame = true;
         this.boardSize = size;
      } else {
         this.newGame = false;
      }

      this.numbersLeft = size;
   }

   protected int getBoardSize() {
      return this.boardSize;
   }

   protected void setBoardSize(int boardSize) {
      this.boardSize = boardSize;
   }

   protected boolean isNewGame() {
      return this.newGame;
   }

   protected void setNewGame(boolean newGame) {
      this.newGame = newGame;
   }

   protected int getNumbersLeft() {
      return this.numbersLeft;
   }

   protected void setNumbersLeft(int numbersLeft) {
      this.numbersLeft = numbersLeft;
   }

   protected int getOpponentScore() {
      return this.opponentScore;
   }

   protected void setOpponentScore(int opponentScore) {
      this.opponentScore = opponentScore;
   }

   protected int getPlayerScore() {
      return this.playerScore;
   }

   protected void setPlayerScore(int playerScore) {
      this.playerScore = playerScore;
   }

   protected void setPlayerName(String playerName) {
      this.playerName = playerName;
   }

   protected int getHighestNumberWithLeastDivisors(int[] numbers) {
      int number = numbers[numbers.length - 1];
      int leastDivs = Integer.MAX_VALUE;

      for(int i = numbers.length; i >= 0; --i) {
         int n = numbers[i];
         int divs = 0;

         for(int j = 0; j < i; ++j) {
            if (n % j == 0) {
               ++divs;
            }
         }

         if (divs < leastDivs) {
            leastDivs = divs;
            number = n;
         }
      }

      return number;
   }

   protected int getHighestNumberWithLeastDivisors2(int[] numbers) {
      int number = numbers[numbers.length - 1];
      int leastDivs = Integer.MAX_VALUE;

      for(int i = numbers.length; i >= 0; --i) {
         int n = numbers[i];
         int divs = 0;

         for(int j = 0; j < i; ++j) {
            if (n % numbers[j] == 0) {
               ++divs;

               for(int x = 0; x < j; ++x) {
                  if (numbers[j] % numbers[x] == 0) {
                     ++divs;
                  }
               }
            }
         }

         if (divs < leastDivs) {
            leastDivs = divs;
            number = n;
         }
      }

      return number;
   }

   protected boolean isPossibleLastMove(int[] numbers) {
      for(int i = 0; i < numbers.length - 1; ++i) {
         if (numbers[numbers.length - 1] % numbers[i] != 0) {
            return false;
         }
      }

      return true;
   }
}
