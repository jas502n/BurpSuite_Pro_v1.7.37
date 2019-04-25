/*    */ package com.intellij.uiDesigner.core;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.JComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Spacer
/*    */   extends JComponent
/*    */ {
/*    */   public Dimension getMinimumSize()
/*    */   {
/* 23 */     return new Dimension(0, 0);
/*    */   }
/*    */   
/*    */   public final Dimension getPreferredSize() {
/* 27 */     return getMinimumSize();
/*    */   }
/*    */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/Spacer.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */