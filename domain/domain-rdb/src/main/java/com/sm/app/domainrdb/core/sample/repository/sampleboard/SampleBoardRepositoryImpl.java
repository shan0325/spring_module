package com.sm.app.domainrdb.core.sample.repository.sampleboard;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sm.app.domainrdb.core.sample.entity.SampleBoard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.sm.app.domainrdb.core.sample.entity.QSampleBoard.sampleBoard;


@RequiredArgsConstructor
public class SampleBoardRepositoryImpl implements SampleBoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<SampleBoard> findSampleBoards(Long boardManagerId, Pageable pageable) {
        return queryFactory
                .selectFrom(sampleBoard)
                .where(sampleBoardManagerIdEq(boardManagerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sampleBoard.id.desc())
                .fetch();
    }

    @Override
    public Long findSampleBoardsTotalCount(Long boardManagerId) {
        return queryFactory
                .select(sampleBoard.count())
                .from(sampleBoard)
                .where(sampleBoardManagerIdEq(boardManagerId))
                .fetchOne();
    }

    private BooleanExpression sampleBoardManagerIdEq(Long boardManagerId) {
        return boardManagerId == null ? null : sampleBoard.sampleBoardManager.id.eq(boardManagerId);
    }
}
