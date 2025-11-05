package com.back;

import com.back.standard.util.TestUtil;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTestRunner {
    public static String run(String input) {
        try {
            Scanner scanner = TestUtil.genScanner(input);
            ByteArrayOutputStream byteArrayOutputStream = TestUtil.setOutToByteArray();

            try {
                new App(scanner).run();
            } catch (Exception e) {
                // 정상적인 종료를 위한 예외 처리
            }

            String output = byteArrayOutputStream.toString();
            TestUtil.clearSetOutToByteArray();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
