module sspd.sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires org.junit.jupiter.api;
    requires org.junit.jupiter.engine;
    requires org.junit.platform.commons;

    opens sspd.sms to javafx.fxml;
    opens sspd.sms.Test to org.junit.jupiter.api;


    exports sspd.sms;
}
