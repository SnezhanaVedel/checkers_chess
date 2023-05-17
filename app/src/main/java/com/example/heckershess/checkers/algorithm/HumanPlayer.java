package com.example.heckershess.checkers.algorithm;

import com.example.heckershess.checkers.rules.Move;
import com.example.heckershess.checkers.rules.Player;

public class HumanPlayer implements IAlgorithm {
    public HumanPlayer() {}

    @Override
    public void getAlgorithm(Player player) {}

    @Override
    public AlgorithmType getAlgorithmType() {
        return AlgorithmType.HUMAN;
    }

    @Override
    public Move getOpponentMove() {
        return null;
    }
}
