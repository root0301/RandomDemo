package random.example.com.randomdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by slience on 2016/8/14.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private String[] name;

    private String[] num;

    public RecyclerAdapter(Context context, String[] na, String[] nu) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        name = na;
        num = nu;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerHolder(mLayoutInflater.inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.nameText.setText(name[position]);
        holder.numText.setText(num[position]);
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView nameText, numText;

        public RecyclerHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.food_name);
            numText = (TextView) itemView.findViewById(R.id.food_num);
        }
    }

}
