package com.ninjagame.ninjagame.domain.console;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public int getIntInput() {
        return scanner.nextInt();
    }
}
