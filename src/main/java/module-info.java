module sspd.sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires spring.context;
    requires org.hibernate.orm.core;
    requires spring.beans;
    requires jakarta.persistence;
    requires java.naming;
    requires com.zaxxer.hikari;
    requires spring.tx;
    requires spring.orm;
    requires spring.core;
    requires jakarta.validation;
    requires org.hibernate.validator;
    requires spring.jdbc;
    requires org.apache.poi.ooxml;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires com.jfoenix;
    requires java.desktop;
    requires static lombok;
    requires org.slf4j;
    requires jakarta.transaction;
    requires jakarta.cdi;



    opens sspd.sms;
    exports sspd.sms;

    opens sspd.sms.config to spring.core,spring.context, spring.beans, org.hibernate.orm.core;
    exports sspd.sms.config to  spring.core, spring.context,spring.beans, org.hibernate.orm.core;

    opens sspd.sms.teacheroptions.controllers to java.base, spring.core, spring.beans, org.hibernate.orm.core, javafx.fxml;
    exports sspd.sms.teacheroptions.controllers to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml;

    opens sspd.sms.teacheroptions.db to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base;
    exports sspd.sms.teacheroptions.db to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base;

    opens sspd.sms.teacheroptions.model to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base,org.hibernate.validator;
    exports sspd.sms.teacheroptions.model to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base,org.hibernate.validator;

    opens sspd.sms.teacheroptions.services to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml;
    exports sspd.sms.teacheroptions.services to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml;

    opens sspd.sms.courseoptions.controllers to java.base, spring.core, spring.beans, org.hibernate.orm.core, javafx.fxml;
    exports sspd.sms.courseoptions.controllers to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml;

    opens  sspd.sms.courseoptions.db to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base;
    exports  sspd.sms.courseoptions.db to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base;

    opens  sspd.sms.courseoptions.model to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base,org.hibernate.validator;
    exports  sspd.sms.courseoptions.model to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base,org.hibernate.validator;

    opens  sspd.sms.courseoptions.services to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,org.hibernate.validator;
    exports  sspd.sms.courseoptions.services to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,org.hibernate.validator;


    opens sspd.sms.classoptions.controllers to java.base, spring.core, spring.beans, org.hibernate.orm.core, javafx.fxml;
    exports sspd.sms.classoptions.controllers to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml;

    opens  sspd.sms.classoptions.db to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base;
    exports  sspd.sms.classoptions.db to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base;

    opens  sspd.sms.classoptions.model to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base,org.hibernate.validator;
    exports  sspd.sms.classoptions.model to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,javafx.base,org.hibernate.validator;

    opens  sspd.sms.classoptions.services to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,org.hibernate.validator;
    exports  sspd.sms.classoptions.services to java.base, spring.core, spring.beans, org.hibernate.orm.core,javafx.fxml,org.hibernate.validator;

   opens sspd.sms.registeroptions.controllers to javafx.fxml,spring.beans;
   exports sspd.sms.registeroptions.controllers to javafx.fxml,spring.beans;

}
