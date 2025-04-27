package com.sm.app.domainrdb.core.member.entity;

import com.sm.app.domainrdb.core.role.entity.Role;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String memberId;

    @Column(nullable = false, length = 80)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime regDate;
}
