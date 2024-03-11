package dat153.g16.oblig3.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestionDAO {
    @Insert
    void insert(Question question);

    @Query("SELECT * FROM Question ORDER BY answer ASC")
    LiveData<List<Question>> getAllQuestions();

    // get a random question
    @Query("SELECT * FROM Question WHERE answered = FALSE ORDER BY RANDOM() LIMIT 1")
    LiveData<Question> getNextQuestion();

    @Query("DELETE FROM Question")
    void deleteAll();

    @Query("UPDATE Question SET answered = FALSE")
    void resetAll();

    @Update
    void update(Question question);
}
