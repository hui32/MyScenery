package liu.com.mydocuments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import liu.com.mydocuments.R;
import liu.com.mydocuments.jsonBean.SceneryInfo;

/**
 * Created by liuhui on 16/3/17.
 */
public class RceneryRecycleAdapter extends RecyclerView.Adapter<RceneryRecycleAdapter.ViewHolder>{
    List<SceneryInfo.RetDataEntity.TicketListEntity> contents;
    public Context mContext;
    public RceneryRecycleAdapter(Context mContext,List<SceneryInfo.RetDataEntity.TicketListEntity> contents){
        this.contents = contents;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.adapter_scenery_list,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //RecommendInfo info = list.get(position);
        SceneryInfo.RetDataEntity.TicketListEntity tickInfo = contents.get(position);
        holder.tv_name.setText(tickInfo.getSpotName());
        holder.itemView.setBackgroundResource(R.drawable.recycle_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "哇~你点了" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_name;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
