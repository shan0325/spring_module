package com.sm.app.domainrdb.core.sample.entity;

import com.sm.app.domainrdb.core.sample.enums.BoardTypeEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class SampleBoardManager extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BoardTypeEnum boardType;

    private Character useYn;

    @Builder(access = AccessLevel.PRIVATE)
    private SampleBoardManager(String name, String description, BoardTypeEnum boardType, Character useYn) {
        this.name = name;
        this.description = description;
        this.boardType = boardType;
        this.useYn = useYn;
    }

    //==생성 메서드==//
    public static SampleBoardManager createSampleBoardManager(String name, String description, BoardTypeEnum boardType, Character useYn) {
        return SampleBoardManager.builder()
                .name(name)
                .description(description)
                .boardType(boardType)
                .useYn(useYn)
                .build();
    }

    //==수정 메서드==//
    public void modifySampleBoardManager(String name, String description, BoardTypeEnum boardType, Character useYn) {
        this.name = name;
        this.description = description;
        this.boardType = boardType;
        this.useYn = useYn;
    }
}
