package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class QnaEntity {
    private int index;
    private String email;
    private String qna_type;
    private String title;
    private String content;
    private Date createdAt;
    private boolean answerFlag ;
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public QnaEntity setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public QnaEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public QnaEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getQna_type() {
        return qna_type;
    }

    public QnaEntity setQna_type(String qna_type) {
        this.qna_type = qna_type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public QnaEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public QnaEntity setContent(String content) {
        this.content = content;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public QnaEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isAnswerFlag() {
        return answerFlag;
    }

    public QnaEntity setAnswerFlag(boolean answerFlag) {
        this.answerFlag = answerFlag;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QnaEntity qnaEntity = (QnaEntity) o;
        return index == qnaEntity.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }


}
