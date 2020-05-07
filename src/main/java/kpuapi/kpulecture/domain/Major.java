package kpuapi.kpulecture.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Major {

    @Id @GeneratedValue
    @Column(name = "major_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MajorCode majorCode;

    public Major(MajorCode majorCode) {
        this.majorCode = majorCode;
    }

    //==비즈니스 로직==//
    public void change(MajorCode majorCode) {
        this.setMajorCode(majorCode);
    }
}
