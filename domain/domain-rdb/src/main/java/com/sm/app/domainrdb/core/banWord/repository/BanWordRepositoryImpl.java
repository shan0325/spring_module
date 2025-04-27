package com.sm.app.domainrdb.core.banWord.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sm.app.domainrdb.core.banWord.repository.query.BanWords;
import com.sm.app.domainrdb.core.banWord.repository.query.QBanWords;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.sm.app.domainrdb.core.banWord.entity.QBanWord.banWord;


@RequiredArgsConstructor
public class BanWordRepositoryImpl implements BanWordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BanWords> findBanWordsByPage(Pageable pageable) {
        return queryFactory
                .select(new QBanWords(
                        banWord.id,
                        banWord.word,
                        banWord.regId,
                        banWord.regDate,
                        banWord.modId,
                        banWord.modDate)
                )
                .from(banWord)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long findBanWordsCount() {
        return queryFactory
                .select(banWord.count())
                .from(banWord)
                .fetchOne();
    }
}
