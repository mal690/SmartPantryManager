package za.co.smartpantry;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
public class PantryActivity extends BaseActivity {
 private ListView list;private TextView empty;
 @Override public void onCreate(Bundle b){super.onCreate(b);screen("My pantry");
  body.addView(button("+ Add ingredient",v->startActivity(new Intent(this,IngredientActivity.class))));
  body.addView(text("Track leftovers. Recipes use only what you have.",15));
  empty=text("Your pantry is empty. Add your first ingredient.",17);body.addView(empty);
  list=new ListView(this);list.setEmptyView(empty);body.addView(list,new LinearLayout.LayoutParams(-1,0,1));
  list.setOnItemClickListener((parent,view,pos,id)->{Intent i=new Intent(this,IngredientActivity.class);i.putExtra("item_id",id);startActivity(i);});
  list.setOnItemLongClickListener((parent,view,pos,id)->{Models.PantryItem p=(Models.PantryItem)parent.getItemAtPosition(pos);new AlertDialog.Builder(this).setTitle("Delete "+p.name+"?").setMessage("This removes this pantry entry, not the recipe collection.").setNegativeButton("Cancel",null).setPositiveButton("Delete",(dialog,which)->load(()->{db.delete(id);return db.pantry();},data->list.setAdapter(new PantryAdapter(this,data,alerts())))).show();return true;});
 }
 private boolean alerts(){return getSharedPreferences("settings",MODE_PRIVATE).getBoolean("alerts",true);}
 @Override protected void onResume(){super.onResume();load(()->db.pantry(),data->list.setAdapter(new PantryAdapter(this,data,alerts())));}
}
