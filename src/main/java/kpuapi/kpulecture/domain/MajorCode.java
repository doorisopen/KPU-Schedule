package kpuapi.kpulecture.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@RequiredArgsConstructor
public enum MajorCode {
/**
 * 기계공학과       	 :AME
 * 기계설계공학과     :AMD
 * 메카트로닉스공학과 :AEE
 * 전자공학부       	 :AEE
 * 컴퓨터공학부       :ACS
 * 게임공학부       	 :AMM
 * 신소재공학과       :AMT
 * 생명화학공학과     :ACH
 * 디자인학부       	 :AID
 * 경영학부        	 :AEB
 * 나노광공학과       :ANO
 * 에너지전기공학과   :AEN
 * 교양기초교육원     :AAK, AAJ
 */

    AME("AME", "기계공학과"),
    AMD("AMD", "기계설계공학과"),
    AAE("AAE", "메카트로닉스공학과"),
    AEE("AEE", "전자공학부"),
    ACS("ACS", "컴퓨터공학부"),
    AMM("AMM", "게임공학부"),
    AMT("AMT", "신소재공학과"),
    ACH("ACH", "생명화학공학과"),
    AID("AID", "디자인학부"),
    AEB("AEB", "경영학부"),
    ANO("ANO", "나노광공학과"),
    AEN("AEN", "에너지전기공학과"),
    AAK("AAK", "교양기초교육원(AAK)"),
    AAJ("AAJ", "교양기초교육원(AAJ)");

    private final String code;
    private final String name;
}
