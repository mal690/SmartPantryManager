package za.co.smartpantry;
import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.*;
import java.time.LocalDate;

/** Custom adapter binds database records to reusable list rows. */
final class PantryAdapter extends BaseAdapter {
 private final Context context;private final List<Models.PantryItem> items;private final boolean alerts;
 PantryAdapter(Context c,List<Models.PantryItem> data,boolean alerts){context=c;items=data;this.alerts=alerts;}
 public int getCount(){return items.size();} public Models.PantryItem getItem(int position){return items.get(position);}public long getItemId(int p){return getItem(p).id;}
 public boolean hasStableIds(){return true;}
 public View getView(int p,View recycled,ViewGroup parent){
  TextView row=recycled instanceof TextView?(TextView)recycled:new TextView(context);row.setTextSize(17);row.setPadding(18,20,18,20);
  Models.PantryItem a=getItem(p);String note="";
  if(!a.expiry.isEmpty()){
   note=" | expires "+a.expiry;
   if(alerts){LocalDate day=LocalDate.parse(a.expiry);if(day.isBefore(LocalDate.now()))note+=" [EXPIRED]";else if(!day.isAfter(LocalDate.now().plusDays(3)))note+=" [USE SOON]";}
  }
  row.setText(a.name+"\n"+a.quantity+" "+a.unit+note+"\nTap to edit • Hold to delete");return row;
 }
}
