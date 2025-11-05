package com.back.domain.system.controller;

import java.util.Scanner;

public class SystemController {
    private Scanner scanner;

    public SystemController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void exit() {
        System.out.println("앱이 종료됩니다.");
    }
}
