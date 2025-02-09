package com.ninjagame.ninjagame.domain.console;

import java.util.Queue;
import java.util.LinkedList;

public class MockInputProvider implements InputProvider {
    private final Queue<Integer> inputs = new LinkedList<>();

    public void addInput(int input) {
        inputs.add(input);
    }

    @Override
    public int getIntInput() {
        return inputs.isEmpty() ? -1 : inputs.poll();
    }
}
