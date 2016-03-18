package liu.com.mydocuments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {
    public String TAG ;
    public Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);

        TAG = this.getClass().getSimpleName();
    }

    public void setToolBar(Toolbar toolBar,String toollBarTitle) {
        setSupportActionBar(toolBar);
        ActionBar ab = getSupportActionBar();
        ab.setTitle(toollBarTitle);
        //ab.openOptionsMenu();
        ab.setDisplayHomeAsUpEnabled(true);
        toolBar.setTitleTextColor(getResources().getColor(R.color.white));
        toolBar.setNavigationIcon(R.drawable.icon_back);

    }

    public void setTopTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent(this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
