/*    */ package tudresden.ocl20.logging.internal;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
/*    */ import org.apache.log4j.Hierarchy;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ import org.apache.log4j.spi.RootLogger;
/*    */ import tudresden.ocl20.logging.ILogManager;
/*    */ 
/*    */ public class StandaloneLogManager
/*    */   implements ILogManager
/*    */ {
/*    */   private Hierarchy hierarchy;
/*    */   private boolean disposed;
/*    */ 
/*    */   public StandaloneLogManager(URL loggerPropertiesUrl)
/*    */   {
/* 33 */     this.hierarchy = new Hierarchy(new RootLogger(Level.WARN));
/*    */ 
/* 35 */     if (loggerPropertiesUrl != null) {
/* 36 */       new PropertyConfigurator().doConfigure(loggerPropertiesUrl, this.hierarchy);
/*    */     }
/*    */     else {
/* 39 */       Properties properties = new Properties();
/* 40 */       properties.put("log4j.rootLogger", "error, stdout");
/* 41 */       properties.put("log4j.appender.stdout", 
/* 42 */         "org.apache.log4j.ConsoleAppender");
/* 43 */       properties.put("log4j.appender.stdout.layout", 
/* 44 */         "org.apache.log4j.PatternLayout");
/* 45 */       properties.put("log4j.appender.stdout.layout.ConversionPattern", 
/* 46 */         "%c: %m%n");
/* 47 */       new PropertyConfigurator().doConfigure(properties, this.hierarchy);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 55 */     if (!this.disposed) {
/* 56 */       this.hierarchy.shutdown();
/* 57 */       this.disposed = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   public Logger getLogger(String name)
/*    */   {
/* 63 */     return this.hierarchy.getLogger(name);
/*    */   }
/*    */ 
/*    */   public Logger getLogger(Class<?> clazz)
/*    */   {
/* 68 */     return this.hierarchy.getLogger(clazz.getCanonicalName());
/*    */   }
/*    */ 
/*    */   public Logger getRootLogger()
/*    */   {
/* 73 */     return this.hierarchy.getRootLogger();
/*    */   }
/*    */ }

/* Location:           C:\Etac\ws\tudresden.ocl20.pivot.standalone.example\plugins\tudresden.ocl20.logging_3.1.0.201101171054.jar
 * Qualified Name:     tudresden.ocl20.logging.internal.StandaloneLogManager
 * JD-Core Version:    0.6.1
 */