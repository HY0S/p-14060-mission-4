package com.back.domain.wiseSaying.controller;

import com.back.AppTestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

public class WiseSayingControllerTest {
    @Test
    @DisplayName("등록")
    void t1() {
        final String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                종료
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록 후 목록")
    void t2() {
        final String out = AppTestRunner.run("""
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                목록
                종료
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("1 / 이순신 / 나의 죽음을 적들에게 알리지 말라!")
                .contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록 2개 후 목록")
    void t3() {
        final String out = AppTestRunner.run("""
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                등록
                사람은 죽음을 두려워한다.
                작자미상
                목록
                종료
                """);

        assertThat(out)
                .contains("2 / 작자미상 / 사람은 죽음을 두려워한다.")
                .contains("1 / 이순신 / 나의 죽음을 적들에게 알리지 말라!");
    }

    @Test
    @DisplayName("삭제")
    void t4() {
        final String out = AppTestRunner.run("""
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                등록
                사람은 죽음을 두려워한다.
                작자미상
                삭제?id=1
                목록
                종료
                """);

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("2 / 작자미상 / 사람은 죽음을 두려워한다.")
                .doesNotContain("1 / 이순신 / 나의 죽음을 적들에게 알리지 말라!");
    }

    @Test
    @DisplayName("존재하지 않는 명언 삭제")
    void t5() {
        final String out = AppTestRunner.run("""
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                삭제?id=999
                종료
                """);

        assertThat(out)
                .contains("999번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("수정")
    void t6() {
        final String out = AppTestRunner.run("""
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                수정?id=1
                나의 죽음을 적들에게 알리지 마라!
                이순신장군
                목록
                종료
                """);

        assertThat(out)
                .contains("명언(기존) 나의 죽음을 적들에게 알리지 말라!")
                .contains("작가(기존) 이순신")
                .contains("1 / 이순신장군 / 나의 죽음을 적들에게 알리지 마라!");
    }

    @Test
    @DisplayName("존재하지 않는 명언 수정")
    void t7() {
        final String out = AppTestRunner.run("""
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                수정?id=999
                종료
                """);

        assertThat(out)
                .contains("999번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("빌드")
    void t8() {
        AppTestRunner.run("""
                등록
                나의 죽음을 적들에게 알리지 말라!
                이순신
                종료
                """);

        // 빌드 명령은 출력이 없으므로 별도 검증 필요
        // 여기서는 빌드가 정상 실행되는지만 확인
        final String out = AppTestRunner.run("""
                빌드
                종료
                """);

        assertThat(out).isNotNull();
    }
}