package liu.com.mydocuments.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import liu.com.mydocuments.R;
import liu.com.mydocuments.jsonBean.RecommendInfo;

/**
 * Created by liuhui on 2016/2/18.
 */
public class RecommendsAdapter extends RecyclerView.Adapter{
    Context mContext;
    List<RecommendInfo> list;
    public RecommendsAdapter(Context context,List<RecommendInfo> list){
        this.mContext = context;
        this.list = list;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mContext, R.layout.adapter_recommend,null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //RecommendInfo info = list.get(position);
        holder.itemView.setBackgroundResource(R.drawable.recycle_bg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"哇~你点了"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        //return list.size();
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_recommend ;
        public TextView tv_appname;
        public TextView tv_occupy_space;
        public TextView tv_describe;
        public ViewHolder(View itemView) {
            super(itemView);
            iv_recommend = (ImageView) itemView.findViewById(R.id.iv_recommend);
            tv_appname = (TextView) itemView.findViewById(R.id.tv_appname);
            tv_occupy_space = (TextView) itemView.findViewById(R.id.tv_occupy_space);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe);
        }
    }
}
