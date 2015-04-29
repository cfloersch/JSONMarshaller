package xpertss.json.object;



import xpertss.json.Entity;
import xpertss.json.Value;

import java.math.BigDecimal;

@Entity
public class BigDecimalObject {

   @Value
   private BigDecimal value;


   public BigDecimalObject() { }
   public BigDecimalObject(BigDecimal value) { this.value = value; }

   public BigDecimal value() { return value; }

}


