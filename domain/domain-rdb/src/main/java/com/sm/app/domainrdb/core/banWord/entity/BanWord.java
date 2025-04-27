package com.sm.app.domainrdb.core.banWord.entity;

import com.sm.app.domainrdb.core.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class BanWord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String word;

    @Column(unique = true, nullable = false, length = 20)
    private boolean deleted;

    @Builder(access = AccessLevel.PRIVATE)
    public BanWord(Long id, String word, boolean deleted) {
        this.id = id;
        this.word = word;
        this.deleted = deleted;
    }

    public static BanWord createBanWord(String word) {
        return BanWord.builder()
                .word(word)
                .deleted(false)
                .build();
    }

    public void modifyBanWord(String word) {
        this.word = word;
    }
}
