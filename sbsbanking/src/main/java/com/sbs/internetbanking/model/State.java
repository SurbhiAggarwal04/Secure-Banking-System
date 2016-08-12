package com.sbs.internetbanking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "US_STATES", schema = "bot_bank")
public class State implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String stateId;
    private String stateName;

    @Id
    @Column(name = "STATE_ID")
    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    @Column(name = "STATE_NAME")
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}