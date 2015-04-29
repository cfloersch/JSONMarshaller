package xpertss.json;

/**
 * Visitor for {@link JSON} values.
 */
public interface JSONVisitor<T> {

   T caseNull();

   T caseNumber(JSONNumber number);

   T caseString(JSONString string);

   T caseBoolean(JSONBoolean bool);

   T caseArray(JSONArray array);

   T caseObject(JSONObject object);

   public static class Empty<T> implements JSONVisitor<T> {

      public T caseArray(JSONArray array)
      {
         return null;
      }

      public T caseBoolean(JSONBoolean bool)
      {
         return null;
      }

      public T caseNull()
      {
         return null;
      }

      public T caseNumber(JSONNumber number)
      {
         return null;
      }

      public T caseObject(JSONObject object)
      {
         return null;
      }

      public T caseString(JSONString string)
      {
         return null;
      }

   }

   public static class Default<T> implements JSONVisitor<T> {

      private final T defaultValue;

      public Default(T defaultValue)
      {
         this.defaultValue = defaultValue;
      }

      public T caseArray(JSONArray array)
      {
         return defaultValue;
      }

      public T caseBoolean(JSONBoolean bool)
      {
         return defaultValue;
      }

      public T caseNull()
      {
         return defaultValue;
      }

      public T caseNumber(JSONNumber number)
      {
         return defaultValue;
      }

      public T caseObject(JSONObject object)
      {
         return defaultValue;
      }

      public T caseString(JSONString string)
      {
         return defaultValue;
      }

   }

   public static class Illegal<T> implements JSONVisitor<T> {

      public T caseArray(JSONArray array)
      {
         throw new IllegalArgumentException();
      }

      public T caseBoolean(JSONBoolean bool)
      {
         throw new IllegalArgumentException();
      }

      public T caseNull()
      {
         throw new IllegalArgumentException();
      }

      public T caseNumber(JSONNumber number)
      {
         throw new IllegalArgumentException();
      }

      public T caseObject(JSONObject object)
      {
         throw new IllegalArgumentException();
      }

      public T caseString(JSONString string)
      {
         throw new IllegalArgumentException();
      }

   }

}
