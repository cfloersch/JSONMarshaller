package xpertss.json.generic;


import xpertss.json.Entity;

@Entity
public class Left<R> extends Tuple<String,R> {

    public Left() { }
    public Left(String left, R right) { super(left, right); }

}
