package za.co.smartpantry;
import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import java.util.concurrent.*;

public abstract class BaseActivity extends Activity {
 static final ExecutorService IO=Executors.newSingleThreadExecutor();
 LinearLayout body; PantryDb db;TextView status;
 interface Task<T>{T run() throws Exception;}
 interface Done<T>{void accept(T value);}
 @Override public void onCreate(Bundle b){super.onCreate(b);db=new PantryDb(this);getWindow().setStatusBarColor(Color.rgb(24,73,58));getWindow().setNavigationBarColor(Color.rgb(24,73,58));}
 void screen(String title){
  LinearLayout outer=new LinearLayout(this);outer.setOrientation(LinearLayout.VERTICAL);outer.setBackgroundColor(Color.rgb(247,249,245));outer.setPadding(20,16,20,12);
  TextView header=text(title,24);header.setTextColor(Color.rgb(24,73,58));outer.addView(header);
  LinearLayout nav=new LinearLayout(this);
  nav.addView(button("Pantry",v->open(PantryActivity.class)),new LinearLayout.LayoutParams(0,58,1));
  nav.addView(button("Recipes",v->open(SuggestedActivity.class)),new LinearLayout.LayoutParams(0,58,1));
  nav.addView(button("Settings",v->open(SettingsActivity.class)),new LinearLayout.LayoutParams(0,58,1));outer.addView(nav);
  body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);outer.addView(body,new LinearLayout.LayoutParams(-1,0,1));
  status=text("",14);outer.addView(status);setContentView(outer);
 }
 TextView text(String s,int size){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(Color.rgb(35,45,39));t.setPadding(8,12,8,12);return t;}
 Button button(String label,View.OnClickListener action){Button b=new Button(this);b.setText(label);b.setAllCaps(false);b.setOnClickListener(action);return b;}
 void open(Class<?> c){Intent i=new Intent(this,c);i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);startActivity(i);}
 boolean excludeExpired(){return getSharedPreferences("settings",MODE_PRIVATE).getBoolean("excludeExpired",true);}
 <T> void load(Task<T> work,Done<T> done){
  status.setText("Working…");IO.execute(()->{try{T result=work.run();runOnUiThread(()->{if(!isFinishing()&&!isDestroyed()){status.setText("");done.accept(result);}});}catch(Exception e){android.util.Log.e("SmartPantry","Operation failed",e);runOnUiThread(()->{if(!isFinishing()&&!isDestroyed())status.setText("Could not complete operation. Please try again.");});}});
 }
 @Override protected void onDestroy(){IO.execute(()->db.close());super.onDestroy();}
}
