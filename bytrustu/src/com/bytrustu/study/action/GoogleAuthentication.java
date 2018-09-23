package com.bytrustu.study.action;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GoogleAuthentication extends Authenticator {
  PasswordAuthentication passAuth;
  
  public GoogleAuthentication(){
    passAuth = new PasswordAuthentication("bytrustu", "bockduftfqjwjjbd");
  }
  
  public PasswordAuthentication getPasswordAuthentication(){
    return passAuth;
  }
  
}
