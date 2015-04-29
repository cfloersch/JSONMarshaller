/**
 * Created By: cfloersch
 * Date: 6/13/2014
 * Copyright 2013 XpertSoftware
 */
package xpertss.json.poly;

import xpertss.json.Entity;

@Entity(discriminatorName = "type", subclasses = {StringProducer.class, GenericProducer.class})
public interface Producer<T> {

   public T produce();

}
