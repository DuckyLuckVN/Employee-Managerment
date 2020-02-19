package com.daihao.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REMEMBER_USER")
public class RememberUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "date_login")
    private Date dateLogin;
    private Date expired;

    public RememberUser() {
    }

    public RememberUser(User user, Date dateLogin, Date expired) {
        this.user = user;
        this.dateLogin = dateLogin;
        this.expired = expired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateLogin() {
        return dateLogin;
    }

    public void setDateLogin(Date dateLogin) {
        this.dateLogin = dateLogin;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }
}
