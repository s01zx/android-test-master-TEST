package ng.riby.androidtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ng.riby.androidtest.database.TaskEntry;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {


    private List<TaskEntry> mTaskEntries;
    private Context mContext;

    public TaskAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        TaskEntry taskEntry = mTaskEntries.get(position);
        double xstart = taskEntry.getStartXCoordinate();
        double ystart = taskEntry.getStartYCoordinate();
        double xend = taskEntry.getEndXCoordinate();
        double yend = taskEntry.getEndYCoordinate();
        double dis = taskEntry.getDistance();

        holder.xStart.setText(String.valueOf(xstart));
        holder.yStart.setText(String.valueOf(ystart));
        holder.xEnd.setText(String.valueOf(xend));
        holder.yEnd.setText(String.valueOf(yend));

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float twoDigitsF = Float.valueOf(decimalFormat.format(dis));

        String newValueforDistance = "" + twoDigitsF + "metres";

        //holder.distance.setText(String.valueOf(twoDigitsF));
        holder.distance.setText(newValueforDistance);

    }

    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }





    class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView xStart;
        TextView yStart;
        TextView xEnd;
        TextView yEnd;
        TextView distance;

        public TaskViewHolder(View itemView) {
            super(itemView);

            xStart = itemView.findViewById(R.id.xstart);
            yStart = itemView.findViewById(R.id.ystart);
            xEnd = itemView.findViewById(R.id.xend);
            yEnd = itemView.findViewById(R.id.yend);
            distance = itemView.findViewById(R.id.distanceCovered);
        }
    }
}
