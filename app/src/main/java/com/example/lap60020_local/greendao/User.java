package com.example.lap60020_local.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity(nameInDb = "USER")
public class User {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "Name")
    private String Name;
    @Property(nameInDb = "Address")
    private String Address;
    @Property(nameInDb = "DateOfBirth")
    private String DateOfBirth;
    @Property(nameInDb = "Result")
    private int result;
    @Property(nameInDb = "ClassName")
    private String ClassName;
    @Generated(hash = 1094079365)
    public User(Long id, String Name, String Address, String DateOfBirth,
            int result, String ClassName) {
        this.id = id;
        this.Name = Name;
        this.Address = Address;
        this.DateOfBirth = DateOfBirth;
        this.result = result;
        this.ClassName = ClassName;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public String getAddress() {
        return this.Address;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }
    public String getDateOfBirth() {
        return this.DateOfBirth;
    }
    public void setDateOfBirth(String DateOfBirth) {
        this.DateOfBirth = DateOfBirth;
    }
    public int getResult() {
        return this.result;
    }
    public void setResult(int result) {
        this.result = result;
    }
    public String getClassName() {
        return this.ClassName;
    }
    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }
    
}
