package common;

import java.util.Date;
<<<<<<< HEAD
=======

>>>>>>> 79059ca (PersonalDetails+DataLogs)
import java.io.Serializable;

public class DataLogs implements Serializable {
    private static final long serialVersionUID = 1L;
    private int log_id;
    private int sub_id;
    private String log_action; 
    private Date timestamp;

    public DataLogs(int log_id, int sub_id, String log_action, Date timestamp) {
        this.log_id = log_id;
        this.sub_id = sub_id;
        this.log_action = log_action;
        this.timestamp = timestamp;
    }

    public int getLog_id() {
        return log_id;
    }

    public int getSub_id() {
        return sub_id;
    }

    public String getLog_action() {
        return log_action;
    }   

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "DataLogs {"+
        "log_id=" + log_id +
         ", sub_id=" + sub_id + 
         ", log_action=" + log_action + 
         ", timestamp="+ timestamp +
          "}";
    }
}