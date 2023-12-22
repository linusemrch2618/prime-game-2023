import java.util.*;

public class Test extends Primspieler {
    public static class Move {
        private final int number;
        private final int value;

        private Move(int number, int value) {
            this.number = number;
            this.value = value;
        }
    }

    public int getValue(int number) {
        int value = number;
        boolean isPrime = true;
        for(int divider = 2; (double)divider <= Math.sqrt(number); ++divider) {
            if (number % divider == 0) {
                isPrime = false;
                break;
            }
        }
        if (isPrime) {
            if (number % 2 != 0) {
                value = value - ((number + 1) / 2);
            } else {
                value = value - (number / 2);
            }
        }

        return value;
    }

    public int getFactorSum(ArrayList<Move> moves, Move m) {
        int summe = 0;
        for (int i = 0; moves.get(i).number < m.number; i++) {
            if ((m.number % moves.get(i).number) == 0) {
                summe += moves.get(i).number;
            }
        }

        return summe;
    }

    public ArrayList<Move> getNewMoves(ArrayList<Move> moves, Move m) {
        ArrayList<Move> newMoves = new ArrayList<>(moves);
        newMoves.removeIf(i -> m.number % i.number == 0);
        return newMoves;
    }

    public int auswahl(int[] feld) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < feld.length; i++) {
            moves.add(new Move(feld[i], getValue(feld[i])));
        }
        /*for (Move i : moves) {
            System.out.println(i.number + " " + i.value);
        }*/

        return getBestMove(moves).number;
    }

    public Move getBestMove(ArrayList<Move> moves) {
        int maxScore = Integer.MIN_VALUE;
        Move bestMove = moves.get(moves.size() - 1);
        for (Move i : moves) {
            ArrayList<Move> movesOpp = getNewMoves(moves, i);
            if (!movesOpp.isEmpty()) {
                int score = i.value - getFactorSum(moves, i);
                Move moveOpp = getBestMoveOpp(movesOpp);
                score = (int) (score - (0.5 * (moveOpp.value - getFactorSum(movesOpp, moveOpp))));
                /*ArrayList<Move> movesLv2 = getNewMoves(movesOpp, moveOpp);
                if (!movesLv2.isEmpty()) {
                    Move moveLv2 = getBestMoveOpp(movesLv2);
                    score = (int) (score + (0.25 * (moveLv2.value - getFactorSum(movesLv2, moveLv2))));
                }*/
                if (score > maxScore) {
                    maxScore = score;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }

    public Move getBestMoveOpp(ArrayList<Move> movesOpp) {
        int maxScoreOpp = Integer.MIN_VALUE;
        Move bestMoveOpp = movesOpp.get(movesOpp.size() - 1);
        for (Move i : movesOpp) {
            ArrayList<Move> movesLv2 = getNewMoves(movesOpp, i);
            if (!movesLv2.isEmpty()) {
                int scoreOpp = i.value - getFactorSum(movesOpp, i);
                Move moveLv2 = getBestMoveLv2(movesLv2);
                scoreOpp = (int) (scoreOpp - (0.5 * (moveLv2.value - getFactorSum(movesLv2, moveLv2))));
                if (scoreOpp > maxScoreOpp) {
                    maxScoreOpp = scoreOpp;
                    bestMoveOpp = i;
                }
            }
        }

        return bestMoveOpp;
    }

    public Move getBestMoveLv2(ArrayList<Move> movesLv2) {
        int maxScoreLv2 = Integer.MIN_VALUE;
        Move bestMoveLv2 = movesLv2.get(movesLv2.size() - 1);
        for (Move i : movesLv2) {
            int scoreLv2 = i.value - getFactorSum(movesLv2, i);
            if (scoreLv2 > maxScoreLv2) {
                maxScoreLv2 = scoreLv2;
                bestMoveLv2 = i;
            }
        }

        return bestMoveLv2;
    }

    public String getPlayerName() {
        return "Test";
    }
    public long getStudentNumber() {
        return 202011111;
    }
}
