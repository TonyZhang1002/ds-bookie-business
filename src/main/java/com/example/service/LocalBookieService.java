package com.example.service;

import com.example.domain.AuthInfo;
import com.example.domain.BookieService;
import com.example.domain.UserInfo;
import com.mongodb.*;
import com.example.domain.BetInfo;
import org.springframework.stereotype.Service;

@Service
public class LocalBookieService implements BookieService {
   /*
   This is used to define whether the client have correct email and password
    */
   @Override
   public boolean login(AuthInfo authInfo) {

      MongoClient mongo = new MongoClient("localhost", 27017);
      DB db = mongo.getDB("usersdb");
      DBCollection table = db.getCollection("user");

      /**** Find whether the database has same info****/
      BasicDBObject searchQuery = new BasicDBObject();
      searchQuery.append("email", authInfo.getEmail());
      searchQuery.append("password", authInfo.getPassword());
      DBCursor cursor = table.find(searchQuery);

      while (cursor.hasNext()) {
         mongo.close();
         return true;
      }
      mongo.close();
      return false;
   }

   /*
   This is a method to get the username by using email
    */
   @Override
   public String getUsername(AuthInfo authInfo){

      MongoClient mongo = new MongoClient("localhost", 27017);
      DB db = mongo.getDB("usersdb");
      DBCollection table = db.getCollection("user");

      /**** Find the database with the same info****/
      BasicDBObject searchQuery = new BasicDBObject();
      searchQuery.append("email", authInfo.getEmail());
      DBCursor cursor = table.find(searchQuery);

      while (cursor.hasNext()) {
         mongo.close();
         return (String) cursor.next().get("username");
      }
      mongo.close();
      return null;
   }

   /*
   This is used to register and put user info into the database
    */
   @Override
   public String registryUser(UserInfo userInfo){

      MongoClient mongo = new MongoClient("localhost", 27017);
      DB db = mongo.getDB("usersdb");
      DBCollection table = db.getCollection("user");

      /**** Insert ****/
      // Create a document to store key and value
      BasicDBObject document = new BasicDBObject();
      document.put("username", userInfo.getUsername());
      document.put("age", userInfo.getAge());
      document.put("email", userInfo.getEmail());
      document.put("password", userInfo.getPassword());
      document.put("balance", 0);
      table.insert(document);
      mongo.close();
      return userInfo.getUsername();
   }

   /*
   This is used to determine whether there are existing email in the database
    */
   @Override
   public boolean ifExist(UserInfo userInfo) {
      MongoClient mongo = new MongoClient("localhost", 27017);
      DB db = mongo.getDB("usersdb");
      DBCollection table = db.getCollection("user");

      /**** Find whether there are existing users****/
      BasicDBObject searchQuery = new BasicDBObject();
      searchQuery.append("email", userInfo.getEmail());
      DBCursor cursor = table.find(searchQuery);

      while (cursor.hasNext()) {
         mongo.close();
         return true;
      }
      mongo.close();
      return false;
   }

   @Override
   public boolean bet(BetInfo betInfo) {
      MongoClient mongo = new MongoClient("localhost", 27017);
      DB db = mongo.getDB("usersdb");
      DBCollection table = db.getCollection("user");

      /**** Update ****/
      // search document where name="mkyong" and update it with new values
      BasicDBObject query = new BasicDBObject();
      query.put("email", betInfo.getAcountEmail());
      DBCursor cursor = table.find(query);

      while (cursor.hasNext()) {
         int a = (int)cursor.next().get("balance");

         // Generate a random number from (-50,50]
         // TODO: Need to change to a real betting result later
         final long l = System.currentTimeMillis();
         final int i = (int)( l % 100 ) - 50;
         a += i;

         BasicDBObject newDocument = new BasicDBObject();
         newDocument.put("balance", a);

         BasicDBObject updateObj = new BasicDBObject();
         updateObj.put("$set", newDocument);

         table.update(query, updateObj);
         mongo.close();
         return true;
      }
      mongo.close();
      return false;
   }

   @Override
   public int getCurrentBalance(AuthInfo authInfo) {
      MongoClient mongo = new MongoClient("localhost", 27017);
      DB db = mongo.getDB("usersdb");
      DBCollection table = db.getCollection("user");

      /**** Find whether there are existing users****/
      BasicDBObject searchQuery = new BasicDBObject();
      searchQuery.append("email", authInfo.getEmail());
      DBCursor cursor = table.find(searchQuery);

      while (cursor.hasNext()) {
         int a = (int)cursor.next().get("balance");
         mongo.close();
         return a;
      }
      mongo.close();
      return 0;
   }


}

