package xpertss.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map.Entry;

class PrettyPrinter implements JSONVisitor<Void> {

   private final String indent;
   private final Writer writer;
   private String currentIndent = "";

   public PrettyPrinter(String indent, Writer writer)
   {
      this.indent = indent;
      this.writer = writer;
   }

   public Void caseArray(JSONArray array)
   {
      /* [
      *   v1,
      *   v2,
      *   ...,
      *   vn
      * ]
      */
      try {
         final String origCurrentIndent = currentIndent;
         writer.append("[\n");
         currentIndent = currentIndent + indent;
         Iterator<JSONValue> iterator = array.values().iterator();
         while(iterator.hasNext()) {
            JSONValue value = iterator.next();
            writer.append(currentIndent);
            value.visit(this);
            if(iterator.hasNext()) {
               writer.append(',');
            }
            writer.append("\n");
         }
         currentIndent = origCurrentIndent;
         writer.append(currentIndent);
         writer.append("]");
         return null;
      } catch(IOException e) {
         throw new Error();
      }
   }

   public Void caseBoolean(JSONBoolean bool)
   {
      try {
         bool.write(writer);
         return null;
      } catch(IOException e) {
         throw new Error();
      }
   }

   public Void caseNull()
   {
      try {
         writer.append("null");
         return null;
      } catch(IOException e) {
         throw new Error();
      }
   }

   public Void caseNumber(JSONNumber number)
   {
      try {
         number.write(writer);
         return null;
      } catch(IOException e) {
         throw new Error();
      }
   }

   public Void caseObject(JSONObject object)
   {
      /* {
      *   "k1": v1,
      *   "k2": v2,
      *   ...
      *   "kn": vn
      * }
      */
      try {
         final String origCurrentIndent = currentIndent;
         writer.append("{\n");
         currentIndent = currentIndent + indent;
         Iterator<Entry<JSONString, JSONValue>> iterator = object.entrySet().iterator();
         while(iterator.hasNext()) {
            Entry<JSONString, JSONValue> field = iterator.next();
            writer.append(currentIndent);
            field.getKey().write(writer);
            writer.append(":");
            field.getValue().visit(this);
            if(iterator.hasNext()) {
               writer.append(',');
            }
            writer.append("\n");
         }
         currentIndent = origCurrentIndent;
         writer.append(currentIndent);
         writer.append("}");
         return null;
      } catch(IOException e) {
         throw new Error();
      }
   }

   public Void caseString(JSONString string)
   {
      try {
         string.write(writer);
         return null;
      } catch(IOException e) {
         throw new Error();
      }
   }

}
