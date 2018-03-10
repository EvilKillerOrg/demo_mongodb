package com.ek.demo.model;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBTest {

  public static void main(String[] args) {

    MongoClient mongoClient = getMongoClient();
    MongoDatabase db = mongoClient.getDatabase("test"); // 获得数据库对象,如果不存在则自动创建
    MongoCollection<Document> collection = db.getCollection("emp"); // 获得集合对象

    // 查询所有
    FindIterable<Document> fi = collection.find();
    // 遍历结果
    for (Document o : fi) {
      System.out.println(o.toJson());
    }
    
    
    // 查询一个
    Document first = collection.find().first();
    System.out.println(first.toJson());
    
    
    mongoClient.close();
  }

  /**
   * -------------------------------------<br>
   * @Title: getMongoClient
   * @Description: TODO
   * @tags @return
   * @return: MongoClient
   * MongoClient实例表示的是一个数据库连接池,即使是多线程,只需要一个MongoClient类的实例
   * 通常,对于一个数据库集群只需要创建一个MongoClient实例,来贯穿整个应用程序
   * 注意每个MongoClient实例的所有资源使用限制（最大连接数等等）
   * 销毁实例，请确保你调用MongoClient.close() 来清理资源
   * -------------------------------------<br>
   */
  public static MongoClient getMongoClient() {

    MongoClient mongoClient = null;
    try {

      /* 方式一: 无参默认为localhost,端口27017 */
      // mongoClient = new MongoClient();

      /* 方式二: 指定连接地址,端口默认为27017 */
      // mongoClient = new MongoClient("localhost");

      /* 方式三: 指定连接地址和端口号 */
      // mongoClient = new MongoClient("10.0.0.99", 27017);

      /* 方式四: 连接到一个副本集,需要提供一个列表 */
      // mongoClient = new MongoClient(
      // Arrays.asList(new ServerAddress("localhost", 27017),
      // new ServerAddress("localhost", 27018),
      // new ServerAddress("localhost", 27019)));

      /* 方式五: 使用连接字符串 */
      // MongoClientURI connectionString = new
      // MongoClientURI("mongodb://localhost:27017,localhost:27018,localhost:27019");
      // mongoClient = new MongoClient(connectionString);

      /* 需要auth认证时链接库 */
      ServerAddress addr = new ServerAddress("10.0.0.99", 27017);
      MongoCredential credential = MongoCredential.createScramSha1Credential("test", "test", "test".toCharArray());
      mongoClient = new MongoClient(addr, Arrays.asList(credential));

    } catch (Exception e) {
      e.printStackTrace();
    }

    return mongoClient;
  }
}
