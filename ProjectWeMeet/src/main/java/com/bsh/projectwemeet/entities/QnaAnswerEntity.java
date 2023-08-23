package com.bsh.projectwemeet.entities;

import java.util.Date;
import java.util.Objects;

public class QnaAnswerEntity {
    private int index;
    private int qnaIndex;
    private String answer;
    private Date createdAt;

    public int getIndex() {
        return index;
    }

    public QnaAnswerEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getQnaIndex() {
        return qnaIndex;
    }

    public QnaAnswerEntity setQnaIndex(int qnaIndex) {
        this.qnaIndex = qnaIndex;
        return this;
    }

    public String getAnswer() {
        return answer;
    }

    public QnaAnswerEntity setAnswer(String answer) {
        this.answer = answer;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public QnaAnswerEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QnaAnswerEntity that = (QnaAnswerEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }


}
