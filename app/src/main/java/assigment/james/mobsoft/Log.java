package assigment.james.mobsoft;

import android.graphics.Bitmap;
import android.text.format.Time;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by james on 25/02/15.
 *
 * This class stores all the data that the user enters before
 * it is pushed to the database or when the user is viewing a
 * pre-existing log
 *
 */
public class Log implements Serializable{

    /**
     * Below is a list of items that will be kept in the
     * athletes logs. This will hopefully be replaced by
     * an auto building tool (LogBuilder)
     */
    private long ID;
    private String title="";
    private String arr_shot ="";
    private String objective ="";
    private String condition ="";
    private String review ="";
    private byte [] image;

    /**
     * SETTERS
     */

    public void setImage(byte [] image){
        this.image = image;
    }

    public void setTitle(String title ){
        this.title = title;
    }

    public void setArr_shot(String arr_shot) {
        this.arr_shot = arr_shot;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    /**
     * GETTERS
     */

    public byte [] getImage() {
        return image;
    }

    public String getTitle(){
        return title;
    }

    public String getArr_shot() {
        return arr_shot;
    }

    public String getCondition() {
        return condition;
    }

    public String getObjective() {
        return objective;
    }

    public String getReview() {
        return review;
    }

    public long getID() {
        return ID;
    }
}
