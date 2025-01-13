package sspd.sms.useroptions.db;

import sspd.sms.databases.Userdb;
import sspd.sms.useroptions.model.User;

import java.util.List;

public class Udb {

    Userdb userdb;

    public List<User> getList(){

        return userdb.getDataList();

    }


}
