package za.co.smartpantry;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import java.util.*;
public class SuggestedActivity extends BaseActivity {
 private ListView list;private TextView empty,summary;private List<Models.Recipe> data=new ArrayList<>();
 @Override public void onCreate(Bundle b){super.onCreate(b);screen("Suggested recipes");summary=text("Every ingredient. Enough quantity. No shopping.",16);body.addView(summary);empty=text("No recipes match your pantry yet — add more ingredients or increase quantities. Check unit dimensions and expiry settings.",17);body.addView(empty);list=new ListView(this);list.setEmptyView(empty);body.addView(list,new LinearLayout.LayoutParams(-1,0,1));list.setOnItemClickListener((p,v,pos,id)->{Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("recipe_id",data.get(pos).id);startActivity(i);});}
 @Override protected void onResume(){super.onResume();boolean exclude=excludeExpired();load(()->db.suggested(exclude),r->{data=r;List<String> names=new ArrayList<>();for(Models.Recipe recipe:r)names.add(recipe.name);list.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,names));summary.setText(r.size()+" strictly matching recipes. "+(exclude?"Expired entries excluded.":"Expiry exclusion is OFF."));});}
}
