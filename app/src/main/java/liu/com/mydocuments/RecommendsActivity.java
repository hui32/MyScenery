package liu.com.mydocuments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liu.com.mydocuments.adapter.RecommendsAdapter;
import liu.com.mydocuments.jsonBean.RecommendInfo;

public class RecommendsActivity extends BaseActivity {

    @Bind(R.id.rcv_recommends)
    RecyclerView rcv_recommends;
    RecommendsAdapter adapter;
    List<RecommendInfo> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommends);
        ButterKnife.bind(this);
        Toolbar tb_recommends = (Toolbar) findViewById(R.id.tb_recommends);
        setToolBar(tb_recommends,"推荐");
        setRecycelView();
    }

    private void setRecycelView() {
        rcv_recommends.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecommendsAdapter(this,list);
        rcv_recommends.setAdapter(adapter);
    }
}
