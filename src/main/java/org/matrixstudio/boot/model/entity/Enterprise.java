package org.matrixstudio.boot.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "sb_enterprise")
public class Enterprise extends BaseEntity {

    private String name;
    private String address;
    private String telephone;
    private String intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
