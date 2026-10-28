package za.co.smartpantry;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.*;
public class SettingsActivity extends BaseActivity {
 @Override public void onCreate(Bundle b){super.onCreate(b);screen("Settings");SharedPreferences preferences=getSharedPreferences("settings",MODE_PRIVATE);
  Switch alerts=new Switch(this);alerts.setText("Highlight expired / expiring within 3 days");alerts.setChecked(preferences.getBoolean("alerts",true));alerts.setPadding(8,24,8,24);body.addView(alerts);alerts.setOnCheckedChangeListener((v,on)->preferences.edit().putBoolean("alerts",on).apply());
  Switch exclude=new Switch(this);exclude.setText("Exclude expired ingredients from matching");exclude.setChecked(preferences.getBoolean("excludeExpired",true));exclude.setPadding(8,24,8,24);body.addView(exclude);exclude.setOnCheckedChangeListener((v,on)->preferences.edit().putBoolean("excludeExpired",on).apply());
  body.addView(text("Alerts are in-app labels, not scheduled notifications. Dates are interpreted using the device's local date. An expiry date equal to today remains eligible.",16));
  body.addView(text("Supported conversions: kg ↔ g, l ↔ ml. pcs is a separate count dimension. No mass-to-volume or count-to-mass assumptions. Tomato/tomatoes and other explicit aliases are normalised. Unknown names require the same spelling.",16));
  body.addView(text("Offline by design. No account, network permission, location, maps, payments or shopping features.",16));
 }
}
