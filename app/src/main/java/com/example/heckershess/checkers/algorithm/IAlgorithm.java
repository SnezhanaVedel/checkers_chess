package com.example.heckershess.checkers.algorithm;

import com.example.heckershess.checkers.rules.Move;
import com.example.heckershess.checkers.rules.Player;

public interface IAlgorithm {
    void getAlgorithm(Player player);
    AlgorithmType getAlgorithmType();
    Move getOpponentMove();
}
