package xpertss.json.object;


import xpertss.json.Entity;
import xpertss.json.Value;

import java.math.BigInteger;

@Entity
public class BigIntegerObject {

   @Value
   private BigInteger value;


   public BigIntegerObject() { }
   public BigIntegerObject(BigInteger value) { this.value = value; }

   public BigInteger value() { return value; }

}
