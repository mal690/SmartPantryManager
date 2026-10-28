package za.co.smartpantry;
import android.os.Bundle;
import android.text.InputType;
import android.widget.*;
import java.math.BigDecimal;
import java.time.LocalDate;
public class IngredientActivity extends BaseActivity {
 private EditText name,quantity,expiry;private Spinner units;private Button save;private long id;
 private final String[] choices={"pcs","g","kg","ml","l"};
 @Override public void onCreate(Bundle b){super.onCreate(b);id=getIntent().getLongExtra("item_id",-1);screen(id<0?"Add ingredient":"Edit ingredient");
  ScrollView scroll=new ScrollView(this);LinearLayout form=new LinearLayout(this);form.setOrientation(LinearLayout.VERTICAL);scroll.addView(form);body.addView(scroll);
  form.addView(text("Ingredient name",16));name=new EditText(this);name.setId(1001);name.setSingleLine(true);name.setHint("e.g. tomatoes");form.addView(name);
  form.addView(text("Quantity (greater than zero)",16));quantity=new EditText(this);quantity.setId(1002);quantity.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);quantity.setHint("e.g. 2 or 0.5");form.addView(quantity);
  form.addView(text("Unit (pcs = whole items; bread pcs = slices)",16));units=new Spinner(this);units.setId(1003);ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,choices);adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);units.setAdapter(adapter);form.addView(units);
  form.addView(text("Expiry date (optional, YYYY-MM-DD)",16));expiry=new EditText(this);expiry.setId(1004);expiry.setSingleLine(true);expiry.setHint("2026-10-30");form.addView(expiry);
  form.addView(text("Mass and volume are not interchangeable. Ingredients must use the recipe's unit dimension. No ingredients are assumed free.",14));
  save=button("Save ingredient",v->save());form.addView(save);
  if(id>=0 && b==null){save.setEnabled(false);load(()->db.item(id),p->{if(p==null){Toast.makeText(this,"Ingredient not found",Toast.LENGTH_LONG).show();finish();return;}name.setText(p.name);quantity.setText(p.quantity);expiry.setText(p.expiry);for(int i=0;i<choices.length;i++)if(choices[i].equals(p.unit))units.setSelection(i);save.setEnabled(true);});}
 }
 private void save(){
  String n=name.getText().toString().trim(),q=quantity.getText().toString().trim(),e=expiry.getText().toString().trim();
  if(n.isEmpty()||n.length()>80){name.setError("Enter an ingredient name (1–80 characters)");return;}
  try{if(!q.matches("[0-9]+(\\.[0-9]{1,6})?")||new BigDecimal(q).signum()<=0||new BigDecimal(q).compareTo(new BigDecimal("1000000"))>0)throw new IllegalArgumentException();}catch(Exception ex){quantity.setError("Use a positive number up to 1000000, max 6 decimal places");return;}
  if(!e.isEmpty()){try{if(!e.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")||!LocalDate.parse(e).toString().equals(e))throw new IllegalArgumentException();}catch(Exception ex){expiry.setError("Enter a real date: YYYY-MM-DD");return;}}
  final String unit=choices[units.getSelectedItemPosition()];
  save.setEnabled(false);load(()->{try{db.save(id,n,q,unit,e);return true;}catch(Exception ex){runOnUiThread(()->save.setEnabled(true));throw ex;}},ok->finish());
 }
}
