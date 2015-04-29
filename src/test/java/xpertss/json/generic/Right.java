package xpertss.json.generic;

import xpertss.json.Entity;

@Entity
public class Right<L> extends Tuple<L, String> {

   public Right() { }
   public Right(L left, String right) { super(left, right); }

}
