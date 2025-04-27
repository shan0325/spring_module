package com.sm.app.domainrdb.core.allowWord.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sm.app.domainrdb.core.allowWord.repository.query.AllowWords;
import com.sm.app.domainrdb.core.allowWord.repository.query.QAllowWords;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.sm.app.domainrdb.core.allowWord.entity.QAllowWord.allowWord;


@RequiredArgsConstructor
public class AllowWordRepositoryImpl implements AllowWordRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AllowWords> findAllowWordsByPage(Pageable pageable) {
        return queryFactory
                .select(new QAllowWords(
                        allowWord.id,
                        allowWord.word,
                        allowWord.regId,
                        allowWord.regDate,
                        allowWord.modId,
                        allowWord.modDate)
                )
                .from(allowWord)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Long findAllowWordsCount() {
        return queryFactory
                .select(allowWord.count())
                .from(allowWord)
                .fetchOne();
    }
}
