package za.co.smartpantry;
import java.util.*;
final class Models {
 static final class PantryItem {
  long id; String name,quantity,unit,expiry;
  Matching.Amount amount(){return new Matching.Amount(name,quantity,unit);}
 }
 static final class Recipe {
  long id; String name,steps;
  List<Matching.Amount> ingredients=new ArrayList<>();
 }
}
