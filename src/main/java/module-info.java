module sspd.sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires junit;


    opens sspd.sms to javafx.fxml;
    exports sspd.sms.Test;




    exports sspd.sms;
}
