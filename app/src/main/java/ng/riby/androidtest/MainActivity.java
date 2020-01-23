package ng.riby.androidtest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import ng.riby.androidtest.database.AppExecutors;
import ng.riby.androidtest.database.Appdatabase;
import ng.riby.androidtest.database.TaskEntry;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;



public class MainActivity extends AppCompatActivity  {

    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private Appdatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.recyclerViewTasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new TaskAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        FloatingActionButton fabButton = findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartStopActivity.class);
                startActivity(intent);
            }
        });

        mDb = Appdatabase.getInstance(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<TaskEntry> tasks = mDb.taskDao().loadAllTasks();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.setTasks(tasks);
                    }
                });
            }
        });

        //mAdapter.setTasks(mDb.taskDao().loadAllTasks());
    }
}
