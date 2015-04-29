package xpertss.json.generic;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.util.Objects;

@Entity
public class Tuple<L,R> {

    @Value
    private L left;

    @Value
    private R right;

    public Tuple() { }
    public Tuple(L left, R right) { this.left = left; this.right = right; }

    public L getLeft()
    {
        return left;
    }

    public R getRight()
    {
        return right;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof Tuple) {
            Tuple o = (Tuple) obj;
            return Objects.equals(left, o.left) &&
                    Objects.equals(right, o.right);
        }
        return false;
    }

    public int hashCode()
    {
        return Objects.hash(left, right);
    }

}
