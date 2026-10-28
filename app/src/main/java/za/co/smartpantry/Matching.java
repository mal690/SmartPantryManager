package za.co.smartpantry;
import java.math.BigDecimal;
import java.util.*;

/** Pure Java business rules. Units are converted only within compatible dimensions. */
public final class Matching {
 private Matching() {}
 private static final Map<String,String> ALIASES = new HashMap<>();
 static {
  String[][] groups={{"tomato","tomatoes"},{"potato","potatoes"},{"onion","onions"},{"egg","eggs"},{"carrot","carrots"},{"bean","beans"},{"pepper","peppers"},{"banana","bananas"},{"apple","apples"},{"pea","peas"},{"mushroom","mushrooms"},{"bread","bread slices","slice of bread"}};
  for(String[] g:groups) for(String s:g) ALIASES.put(s,g[0]);
 }
 public static String canonical(String s) {
  String n=s==null?"":s.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+"," ");
  return ALIASES.containsKey(n)?ALIASES.get(n):n;
 }
 public static String dimension(String u) {
  switch(u) {case "g": case "kg": return "mass"; case "ml": case "l": return "volume"; case "pcs": return "count"; default: throw new IllegalArgumentException("Unsupported unit: "+u);}
 }
 public static BigDecimal base(BigDecimal q,String u) {
  dimension(u);
  return (u.equals("kg")||u.equals("l"))?q.multiply(new BigDecimal("1000")):q;
 }
 public static final class Amount {
  public final String ingredient,unit; public final BigDecimal quantity;
  public Amount(String ingredient,String quantity,String unit) {
   this.ingredient=canonical(ingredient);this.quantity=new BigDecimal(quantity);this.unit=unit;
   if(this.ingredient.isEmpty()||this.quantity.signum()<=0) throw new IllegalArgumentException("Invalid amount");
   dimension(unit);
  }
 }
 private static Map<String,BigDecimal> totals(List<Amount> items) {
  Map<String,BigDecimal> out=new HashMap<>();
  for(Amount a:items) {String k=a.ingredient+"|"+dimension(a.unit);out.put(k,out.getOrDefault(k,BigDecimal.ZERO).add(base(a.quantity,a.unit)));}
  return out;
 }
 public static boolean qualifies(List<Amount> pantry,List<Amount> required) {
  if(required.isEmpty()) return false;
  Map<String,BigDecimal> have=totals(pantry), need=totals(required);
  for(Map.Entry<String,BigDecimal> r:need.entrySet())
   if(have.getOrDefault(r.getKey(),BigDecimal.ZERO).compareTo(r.getValue())<0) return false;
  return true;
 }
}
