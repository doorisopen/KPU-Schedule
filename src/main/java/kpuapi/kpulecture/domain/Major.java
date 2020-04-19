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

    private String majorCode;
    private String majorName;

    @OneToOne(mappedBy = "major", fetch = FetchType.LAZY)
    private Professor professor;
}
