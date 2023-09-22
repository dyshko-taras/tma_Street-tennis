package com.game.game.lib.tools;

public class GameState {

    public static final int START_ROUND = 0;
    public static final int MAKE_CLICK = 1;
    public static final int MOVING_BALL = 2;
    public static final int END_ROUND = 3;
    public static final int PAUSE = 4;

    private static int currentState;

    public static void setState(int newState) {
        currentState = newState;
    }

    public static int getState() {
//        System.out.println("Current state: " + currentState);
        return currentState;
    }

    public static void printState() {
        if (currentState == 0) System.out.println(String.format( "%d - START_ROUND", currentState));
        if (currentState == 1) System.out.println(String.format( "%d - MAKE_CLICK", currentState));
        if (currentState == 2) System.out.println(String.format( "%d - MOVING_BALL", currentState));
        if (currentState == 3) System.out.println(String.format( "%d - END_ROUND", currentState));
        if (currentState == 4) System.out.println(String.format( "%d - PAUSE", currentState));

    }
}
