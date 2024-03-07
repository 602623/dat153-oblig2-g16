package dat153.g16.oblig3.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDAO {
    @Insert
    void insert(Question question);

    @Query("SELECT * FROM Question ORDER BY answer ASC")
    LiveData<List<Question>> getAllQuestions();

    @Query("DELETE FROM Question")
    void deleteAll();
}
