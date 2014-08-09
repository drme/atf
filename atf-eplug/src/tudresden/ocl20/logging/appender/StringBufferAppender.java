/*    */ package tudresden.ocl20.logging.appender;
/*    */ 
/*    */ import java.io.StringWriter;
/*    */ import org.apache.log4j.WriterAppender;
/*    */ 
/*    */ public class StringBufferAppender extends WriterAppender
/*    */ {
/*  9 */   public static StringWriter stringWriter = new StringWriter();
/*    */ 
/*    */   public StringBufferAppender()
/*    */   {
/* 13 */     setWriter(stringWriter);
/*    */   }
/*    */ 
/*    */   public synchronized void close()
/*    */   {
/* 19 */     stringWriter = new StringWriter();
/* 20 */     super.close();
/*    */   }
/*    */ 
/*    */   public static String getMessages() {
/* 24 */     return stringWriter.toString();
/*    */   }
/*    */ }

/* Location:           C:\Etac\ws\tudresden.ocl20.pivot.standalone.example\plugins\tudresden.ocl20.logging_3.1.0.201101171054.jar
 * Qualified Name:     tudresden.ocl20.logging.appender.StringBufferAppender
 * JD-Core Version:    0.6.1
 */