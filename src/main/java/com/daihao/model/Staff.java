package com.daihao.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STAFFS")
public class Staff
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean gender;
    private String photo;
    private String email;
    private String phone;
    @JsonFormat(pattern="dd-MM-yyyy", timezone = "Asia/Bangkok")
    private Date birthday;
    private double salary;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "depart_id")
    private Depart depart;

    public Staff()
    {

    }

    public Staff(int id, String name, boolean gender, String photo, String email, String phone, Date birthday, double salary, String notes, Depart depart) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.salary = salary;
        this.notes = notes;
        this.depart = depart;
    }

    public Staff(int id, String name, boolean gender, String photo, String email, String phone, Date birthday, double salary, String notes, String departId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.photo = photo;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.salary = salary;
        this.notes = notes;
        this.depart = new Depart(departId, "");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Depart getDepart() {
        return depart;
    }

    public void setDepart(Depart depart) {
        this.depart = depart;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthdate) {
        this.birthday = birthdate;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", photo='" + photo + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday=" + birthday +
                ", salary=" + salary +
                ", notes='" + notes + '\'' +
                ", depart=" + depart +
                '}';
    }
}
