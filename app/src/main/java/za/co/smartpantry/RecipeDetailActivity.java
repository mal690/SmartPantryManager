package za.co.smartpantry;
import android.os.Bundle;
import android.widget.*;
public class RecipeDetailActivity extends BaseActivity {
 private LinearLayout content;
 @Override public void onCreate(Bundle b){super.onCreate(b);screen("Recipe detail");ScrollView scroll=new ScrollView(this);content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);scroll.addView(content);body.addView(scroll);}
 @Override protected void onResume(){super.onResume();long id=getIntent().getLongExtra("recipe_id",-1);load(()->db.recipe(id),r->{content.removeAllViews();if(r==null){content.addView(text("Recipe not found.",18));return;}content.addView(text(r.name,24));content.addView(text("Required ingredients",20));for(Matching.Amount a:r.ingredients)content.addView(text("• "+a.ingredient+": "+a.quantity.toPlainString()+" "+a.unit,17));content.addView(text("Method",20));content.addView(text(r.steps,18));content.addView(text("Cooking does not automatically deduct pantry quantities. Edit your pantry after using ingredients. Use only ingredients safe to eat; a date alone cannot establish food safety.",14));});}
}
