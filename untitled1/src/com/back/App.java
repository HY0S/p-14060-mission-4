package com.back;

import com.back.domain.system.controller.SystemController;
import com.back.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private Scanner scanner;
    private SystemController systemController;
    private WiseSayingController wiseSayingController;

    public App() {
        scanner = new Scanner(System.in);
        systemController = new SystemController(scanner);
        wiseSayingController = new WiseSayingController(scanner);
    }

    public App(Scanner scanner) {
        this.scanner = scanner;
        systemController = new SystemController(scanner);
        wiseSayingController = new WiseSayingController(scanner);
    }

    public void run() {
        System.out.println("== 명언 앱 ==");

        //TODO: 실행 시 list 개수 확인, 0개 일시 더미 데이터 10개 생성

        wiseSayingController.dummyData();

        while (true) {
            System.out.print("명령) ");
            String command = scanner.nextLine().trim();

            if (command.equals("종료")) {
                systemController.exit();
                break;
            } else if (command.equals("등록")) {
                wiseSayingController.write();
            } else if (command.startsWith("목록")) {
                wiseSayingController.list(command);
            } else if (command.startsWith("삭제")) {
                wiseSayingController.remove(command);
            } else if (command.startsWith("수정")) {
                wiseSayingController.modify(command);
            } else if (command.startsWith("빌드")) {
                wiseSayingController.build();
            }
            else {
                System.out.println("잘못된 명령입니다.");
            }
        }
    }
}
