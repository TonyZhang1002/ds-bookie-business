package com.example.domain;

import java.io.IOException;

public interface BookieService extends Service{
   public boolean login(AuthInfo authInfo) throws IOException;
   public String getUsername(AuthInfo authInfo) throws IOException;
   public String registerUser(UserInfo userInfo) throws IOException;
   public boolean ifExist(UserInfo userInfo) throws IOException;
   //public boolean bet(BetInfo betInfo) throws IOException;
   //public double getCurrentBalance(AuthInfo authInfo) throws IOException;
  // public void updateBalance(AuthInfo authInfo, String bookie, double amount);
}
