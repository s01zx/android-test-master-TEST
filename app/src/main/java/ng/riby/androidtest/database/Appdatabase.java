package ng.riby.androidtest.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {TaskEntry.class}, version = 1, exportSchema = false)
public abstract class Appdatabase extends RoomDatabase {

    private static final String LOG_TAG = Appdatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "solz";
    private static Appdatabase sInstance;



    public static Appdatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        Appdatabase.class, Appdatabase.DATABASE_NAME)
                        //.allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract TaskDao taskDao();



}
