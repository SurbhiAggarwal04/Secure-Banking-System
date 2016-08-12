package com.sbs.internetbanking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ROLE", schema = "bot_bank")
public class Role implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5320694737719591600L;
    private String roleId;
    private int viewFlag;
    private int deleteFlag;
    private int modifyFlag;
    private int createFlag;
    private String roleName;

    @Id
    @Column(name = "ROLE")
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Column(name = "VIEW_ACCESS")
    public int getViewFlag() {
        return viewFlag;
    }

    public void setViewFlag(int viewFlag) {
        this.viewFlag = viewFlag;
    }

    @Column(name = "DELETE_ACCESS")
    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "MODIFY_ACCESS")
    public int getModifyFlag() {
        return modifyFlag;
    }

    public void setModifyFlag(int modifyFlag) {
        this.modifyFlag = modifyFlag;
    }

    @Column(name = "CREATE_ACCESS")
    public int getCreateFlag() {
        return createFlag;
    }

    public void setCreateFlag(int createFlag) {
        this.createFlag = createFlag;
    }

    @Column(name = "ROLE_NAME")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
