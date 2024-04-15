package dat153.g16.oblig3.model;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import dat153.g16.oblig3.R;

@Database(entities = {Question.class}, version = 1, exportSchema = false)
public abstract class QuestionDatabase extends RoomDatabase {
    public abstract QuestionDAO questionDAO();

    private static volatile QuestionDatabase INSTANCE;

    public static QuestionDatabase getDatabase(final Context context) {
        Log.wtf("debug", "INITIALIZING DATABASE");

        if (INSTANCE == null) {
            synchronized (QuestionDatabase.class) {
                if (INSTANCE == null) {
                    RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.wtf("debug", "DATABASE CREATED");

                            Executors.newSingleThreadScheduledExecutor().execute(() -> {
                                QuestionDAO dao = INSTANCE.questionDAO();
                                dao.insert(new Question(R.drawable.cat, "cat"));
                                dao.insert(new Question(R.drawable.coala, "coala"));
                                dao.insert(new Question(R.drawable.dog, "dog"));
                            });
                        }
                    };

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    QuestionDatabase.class, "quiz_database")
                            .addCallback(rdc)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}