package za.co.smartpantry;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import org.json.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

/** Local database; the caller serializes work on BaseActivity.IO, not the UI thread. */
final class PantryDb extends SQLiteOpenHelper {
 private final Context context;
 PantryDb(Context c){super(c.getApplicationContext(),"pantry.db",null,1);context=c.getApplicationContext();}
 @Override public void onConfigure(SQLiteDatabase d){d.setForeignKeyConstraintsEnabled(true);}
 @Override public void onCreate(SQLiteDatabase d){
  d.execSQL("CREATE TABLE pantry(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,quantity TEXT NOT NULL,unit TEXT NOT NULL,expiry TEXT NOT NULL DEFAULT '')");
  d.execSQL("CREATE TABLE recipe(id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT NOT NULL,steps TEXT NOT NULL)");
  d.execSQL("CREATE TABLE requirement(id INTEGER PRIMARY KEY AUTOINCREMENT,recipe_id INTEGER NOT NULL REFERENCES recipe(id) ON DELETE CASCADE,name TEXT NOT NULL,quantity TEXT NOT NULL,unit TEXT NOT NULL)");
  d.execSQL("CREATE INDEX requirement_recipe ON requirement(recipe_id)");
  try(InputStream in=context.getAssets().open("recipes.json"); ByteArrayOutputStream bytes=new ByteArrayOutputStream()) {
   byte[] buffer=new byte[4096];int count;while((count=in.read(buffer))!=-1)bytes.write(buffer,0,count);
   JSONArray recipes=new JSONArray(new String(bytes.toByteArray(),StandardCharsets.UTF_8));
   for(int i=0;i<recipes.length();i++) {
    JSONObject r=recipes.getJSONObject(i);ContentValues v=new ContentValues();v.put("name",r.getString("name"));v.put("steps",r.getString("steps"));long id=d.insertOrThrow("recipe",null,v);
    JSONArray ingredients=r.getJSONArray("ingredients");
    for(int j=0;j<ingredients.length();j++){JSONObject a=ingredients.getJSONObject(j);v=new ContentValues();v.put("recipe_id",id);v.put("name",a.getString("name"));v.put("quantity",a.getString("quantity"));v.put("unit",a.getString("unit"));d.insertOrThrow("requirement",null,v);}
   }
  }catch(Exception e){throw new IllegalStateException("Recipe seed could not be loaded",e);}
 }
 @Override public void onUpgrade(SQLiteDatabase d,int old,int next){throw new IllegalStateException("No migration defined for version "+next);}
 List<Models.PantryItem> pantry(){
  List<Models.PantryItem> out=new ArrayList<>();
  try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM pantry ORDER BY name COLLATE NOCASE,id",null)){
   while(c.moveToNext()){Models.PantryItem p=new Models.PantryItem();p.id=c.getLong(0);p.name=c.getString(1);p.quantity=c.getString(2);p.unit=c.getString(3);p.expiry=c.getString(4);out.add(p);}
  }return out;
 }
 Models.PantryItem item(long id){for(Models.PantryItem p:pantry())if(p.id==id)return p;return null;}
 void save(long id,String name,String q,String u,String expiry){
  new Matching.Amount(name,q,u);
  ContentValues v=new ContentValues();v.put("name",Matching.canonical(name));v.put("quantity",q);v.put("unit",u);v.put("expiry",expiry);
  if(id<0)getWritableDatabase().insertOrThrow("pantry",null,v);
  else if(getWritableDatabase().update("pantry",v,"id=?",new String[]{Long.toString(id)})!=1)throw new IllegalStateException("Ingredient no longer exists");
 }
 void delete(long id){getWritableDatabase().delete("pantry","id=?",new String[]{Long.toString(id)});}
 List<Models.Recipe> recipes(){
  List<Models.Recipe> out=new ArrayList<>();SQLiteDatabase d=getReadableDatabase();
  try(Cursor c=d.rawQuery("SELECT id,name,steps FROM recipe ORDER BY name",null)){
   while(c.moveToNext()){Models.Recipe r=new Models.Recipe();r.id=c.getLong(0);r.name=c.getString(1);r.steps=c.getString(2);
    try(Cursor a=d.rawQuery("SELECT name,quantity,unit FROM requirement WHERE recipe_id=? ORDER BY id",new String[]{Long.toString(r.id)})){
     while(a.moveToNext())r.ingredients.add(new Matching.Amount(a.getString(0),a.getString(1),a.getString(2)));
    }out.add(r);
   }
  }return out;
 }
 Models.Recipe recipe(long id){for(Models.Recipe r:recipes())if(r.id==id)return r;return null;}
 List<Models.Recipe> suggested(boolean excludeExpired){
  List<Matching.Amount> pantry=new ArrayList<>();String today=java.time.LocalDate.now().toString();
  for(Models.PantryItem p:pantry())if(!excludeExpired||p.expiry.isEmpty()||p.expiry.compareTo(today)>=0)pantry.add(p.amount());
  List<Models.Recipe> out=new ArrayList<>();for(Models.Recipe r:recipes())if(Matching.qualifies(pantry,r.ingredients))out.add(r);return out;
 }
}
