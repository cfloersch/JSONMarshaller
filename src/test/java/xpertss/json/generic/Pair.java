package xpertss.json.generic;

import xpertss.json.Entity;

@Entity
public class Pair extends Left<String> {

    public Pair() { }
    public Pair(String left, String right) { super(left, right); }

}
