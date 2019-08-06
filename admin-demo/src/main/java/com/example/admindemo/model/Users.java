package com.example.admindemo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/** The persistent class for the users database table. */
@Entity
@Table(name = "users")
@NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
public class Users implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(unique = true, nullable = false)
  private Integer id;

  @Column(name = "user_name", length = 32)
  private String userName;

  @Column(name = "password", length = 50)
  private String password;

  @Column(name = "identity", length = 32)
  private String identity;

  @Column(name = "real_name", length = 32)
  private String realName;

  @Column(name = "date_birth")
  private Date dateBirth;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "iphone", length = 11)
  private String iphone;

  @Column(name = "user_id", length = 11)
  private Integer userId;

  @Column(name = "signature", length = 255)
  private String signature;

  @Column(name = "profile_picture", length = 255)
  private String profilePicture;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getIdentity() {
    return identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public Date getDateBirth() {
    return dateBirth;
  }

  public void setDateBirth(Date dateBirth) {
    this.dateBirth = dateBirth;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIphone() {
    return iphone;
  }

  public void setIphone(String iphone) {
    this.iphone = iphone;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getprofilePicture() {
    return profilePicture;
  }

  public void setprofilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }
}
