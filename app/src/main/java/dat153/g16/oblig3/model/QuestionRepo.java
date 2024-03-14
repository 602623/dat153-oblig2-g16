package dat153.g16.oblig3.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuestionRepo {
    private static QuestionRepo INSTANCE;
    private final QuestionDAO questionDAO;
    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    public QuestionRepo(Application application) {
        QuestionDatabase db = QuestionDatabase.getDatabase(application);
        questionDAO = db.simpleQuestionDAO();
    }

    public static synchronized QuestionRepo getInstance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new QuestionRepo(application);
        }
        return INSTANCE;
    }

    public LiveData<List<Question>> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    public LiveData<List<Question>> getRandomQuestions() {
        return questionDAO.getRandomQuestions();
    }

    public void update(Question question) {
        executor.execute(() -> questionDAO.update(question));
    }

    public void resetAll() {
        executor.execute(questionDAO::resetAll);
    }

    public void insert(Question question) {
        executor.execute(() -> questionDAO.insert(question));
    }

    public void delete(Question question) {
        executor.execute(() -> questionDAO.delete(question));
    }
}
