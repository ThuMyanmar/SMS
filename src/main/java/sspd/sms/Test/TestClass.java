package sspd.sms.Test;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sspd.sms.databases.DatabaseConnect;

import java.sql.Connection;

public class TestClass {


    Connection db = DatabaseConnect.getInstance().getConnection();

    String db1 =null;

    @Test
    void getTest(){
        Assertions.assertNotNull(db1, "Database connection should not be null");
    }



}
