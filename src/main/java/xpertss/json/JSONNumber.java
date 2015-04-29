/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 9:53 AM
 * Copyright Manheim online
 */
package xpertss.json;


import java.math.BigDecimal;

/**
 * A Javascript compatible numeric that can represent numbers in
 * 64-bit floating point form.
 */
public interface JSONNumber extends JSONValue {

   BigDecimal getNumber();

}
