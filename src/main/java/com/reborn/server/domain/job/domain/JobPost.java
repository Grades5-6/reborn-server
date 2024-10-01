package com.reborn.server.domain.job.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "job")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPost {

    // api 받아오면 수정할 예정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    private Long id;

    private String title;
    private String content;
    private String company;
    private String location;
    private String deadline;

    @Builder
    public JobPost(Long id, String title, String content, String company, String location, String deadline) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.company = company;
        this.location = location;
        this.deadline = deadline;
    }
}
