package kpuapi.kpulecture.domain.school;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * majorName        majorCode
 * 기계공학과       	:AME
 * 기계설계공학과      :AMD
 * 메카트로닉스공학과   :AEE
 * 전자공학부       	:AEE
 * 컴퓨터공학부        :ACS
 * 게임공학부       	:AMM
 * 신소재공학과        :AMT
 * 생명화학공학과      :ACH
 * 디자인학부         :AID
 * 경영학부        	:AEB
 * 나노광공학과       :ANO
 * 에너지전기공학과    :AEN
 * 교양기초교육원      :AAK, AAJ
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Major {

    @Id
    @GeneratedValue
    @Column(name = "major_id")
    private Long id;

    private String majorName;
    private String majorCode;

    @OneToMany(mappedBy = "major")
    private List<Professor> professors = new ArrayList<>();

    //==생성 메서드==//
    public Major(String majorName) {
        this.majorName = majorName;
    }

    public void changeMajorName(String majorName) {
        this.setMajorName(majorName);
    }
}
