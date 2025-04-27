package com.sm.app.domainrdb.core.sample.entity;


import com.sm.app.domainrdb.core.sample.entity.SampleBoardManager;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class SampleBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    private Character useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sample_board_manager_id")
    private SampleBoardManager sampleBoardManager;

    @Builder(access = AccessLevel.PRIVATE)
    public SampleBoard(Long id, String title, String content, Character useYn, SampleBoardManager sampleBoardManager) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.useYn = useYn;
        this.sampleBoardManager = sampleBoardManager;
    }

    //==생성 메서드==//
    public static SampleBoard createSampleBoard(String title, String content, SampleBoardManager sampleBoardManager) {
        return SampleBoard.builder()
                .title(title)
                .content(content)
                .useYn('Y')
                .sampleBoardManager(sampleBoardManager)
                .build();
    }

    //==수정 메서드==//
    public void modifySampleBoard(String title, String content, SampleBoardManager sampleBoardManager) {
        this.title = title;
        this.content = content;
        this.sampleBoardManager = sampleBoardManager;
    }
}
