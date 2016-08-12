package com.sbs.internetbanking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REQUEST_TYPE", schema = "bot_bank")
public class RequestType {

    private String reuest_type_id;
    private String reuest_type;

    @Id
    @Column(name = "REQUEST_TYPE_ID")
    public String getReuest_type_id() {
        return reuest_type_id;
    }

    public void setReuest_type_id(String reuest_type_id) {
        this.reuest_type_id = reuest_type_id;
    }

    @Column(name = "REQUEST_TYPE")
    public String getReuest_type() {
        return reuest_type;
    }

    public void setReuest_type(String reuest_type) {
        this.reuest_type = reuest_type;
    }

}
