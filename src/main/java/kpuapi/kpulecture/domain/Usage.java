package kpuapi.kpulecture.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Usage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long id;
    private LocalDate date;
    private int used;

    @Builder
    public Usage(LocalDate date, int used) {
        this.date = date;
        this.used = used;
    }

    //==비즈니스 로직==//
    public void use() {
        this.used += 1;
    }
}
