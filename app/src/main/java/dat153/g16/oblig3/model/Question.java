package dat153.g16.oblig3.model;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int resId = 0;
    private String uriString = null;
    private String answer;

    public Question() {
    }

    public Question(int resId, String name) {
        this.resId = resId;
        this.answer = name;
    }

    public Question(String uri, String name) {
        this.uriString = uri;
        this.answer = name;
    }

    public int getImageResId() {
        return resId;
    }

    public void setImageResId(int resId) {
        this.resId = resId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getUriString() {
        return uriString;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    public Uri getUri() {
        return Uri.parse(uriString);
    }

    public boolean isUsingUri() {
        return uriString != null;
    }
}
