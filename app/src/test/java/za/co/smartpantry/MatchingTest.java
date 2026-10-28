package za.co.smartpantry;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;
public class MatchingTest {
 private Matching.Amount a(String n,String q,String u){return new Matching.Amount(n,q,u);}
 private List<Matching.Amount> list(Matching.Amount... a){return Arrays.asList(a);}
 @Test public void exact(){assertTrue(Matching.qualifies(list(a("egg","2","pcs")),list(a("egg","2","pcs"))));}
 @Test public void missing(){assertFalse(Matching.qualifies(list(a("egg","2","pcs")),list(a("egg","2","pcs"),a("oil","5","ml"))));}
 @Test public void insufficient(){assertFalse(Matching.qualifies(list(a("egg","1","pcs")),list(a("egg","2","pcs"))));}
 @Test public void plural(){assertTrue(Matching.qualifies(list(a("  TOMATOES  ","2","pcs")),list(a("tomato","2","pcs"))));}
 @Test public void whitespace(){assertEquals("peanut butter",Matching.canonical(" Peanut   Butter "));}
 @Test public void kilograms(){assertTrue(Matching.qualifies(list(a("cheese","0.1","kg")),list(a("cheese","100","g"))));}
 @Test public void litres(){assertTrue(Matching.qualifies(list(a("milk","0.25","l")),list(a("milk","250","ml"))));}
 @Test public void dimensionMismatch(){assertFalse(Matching.qualifies(list(a("milk","500","g")),list(a("milk","250","ml"))));}
 @Test public void countNotMass(){assertFalse(Matching.qualifies(list(a("tomato","1000","g")),list(a("tomato","1","pcs"))));}
 @Test public void aggregatePantry(){assertTrue(Matching.qualifies(list(a("milk","100","ml"),a("milk","0.15","l")),list(a("milk","250","ml"))));}
 @Test public void aggregateRequirements(){assertFalse(Matching.qualifies(list(a("milk","200","ml")),list(a("milk","150","ml"),a("milk","100","ml"))));}
 @Test public void extraPantryAllowed(){assertTrue(Matching.qualifies(list(a("egg","2","pcs"),a("tomato","1","pcs")),list(a("egg","2","pcs"))));}
 @Test public void noEmptyRecipe(){assertFalse(Matching.qualifies(list(a("egg","2","pcs")),Collections.emptyList()));}
 @Test public void decimalBoundary(){assertFalse(Matching.qualifies(list(a("milk","0.249999","l")),list(a("milk","250","ml"))));}
 @Test(expected=IllegalArgumentException.class) public void zeroRejected(){a("egg","0","pcs");}
 @Test(expected=IllegalArgumentException.class) public void negativeRejected(){a("egg","-1","pcs");}
 @Test(expected=IllegalArgumentException.class) public void unknownUnitRejected(){a("milk","1","cup");}
}
