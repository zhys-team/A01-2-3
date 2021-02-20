package com.invoice.po;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserEntity {
   private Integer id;
   private String password;
   private Date created;
   private Date updated;
   private String email;
   private String username;
   private String openid;
   private String phone;

}
