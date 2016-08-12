package com.sbs.internetbanking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SECURITY_QUESTIONS", schema = "bot_bank")
public class SecurityQuestion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String qId;
    private String qText;

    @Id
    @Column(name = "QID")
    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    @Column(name = "QUESTION_TEXT")
    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

}
