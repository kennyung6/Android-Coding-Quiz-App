import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Devin on 6/23/2015.
 */
public class QuestionSQLiteHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QuizDatabase";
    public static final int DATABASE_VERSION = 1;

    public static final String QUESTION_TABLE = "QuestionsTable";
    public static final String ID_COL = "_id";
    public static final String QUESTION_COL = "question";
    public static final String CHOICES_COL = "choices";
    public static final String ANSWER_COL = "answer";
    public static final String TOPIC_ID = "topic_id";

    public static final String SEPARATOR = "_,_";

    private static final String CREATE_DATABASE = "CREATE TABLE IF NOT EXISTS " + QUESTION_TABLE + "(" +
            ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            QUESTION_COL + " TEXT, " +
            CHOICES_COL + " TEXT, " +
            ANSWER_COL + " TINYINT)";

    public QuestionSQLiteHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_DATABASE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.w(QuestionSQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data.");
        db.execSQL("DROP TABLE IF EXISTS " + QUESTION_TABLE);
    }




    public void insertQuestion(Question newQuestion, int topicID){
        ContentValues values = new ContentValues();
        values.put(QUESTION_COL, newQuestion.get_question());

        //serialize all the choices into one string so it can be stored in the database. Yay Java!
        String choicesSerial = TextUtils.join(SEPARATOR, newQuestion.getChoices().toArray());
        values.put(CHOICES_COL, choicesSerial);

        values.put(ANSWER_COL, newQuestion.getAnswerIndex());
        values.put(TOPIC_ID, topicID);
    }
}
