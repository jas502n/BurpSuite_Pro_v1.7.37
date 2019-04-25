/*    */ package com.intellij.uiDesigner.core;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Dimension;
/*    */ import java.util.ArrayList;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LayoutState
/*    */ {
/*    */   private final Component[] myComponents;
/*    */   private final GridConstraints[] myConstraints;
/*    */   private final int myColumnCount;
/*    */   private final int myRowCount;
/*    */   final Dimension[] myPreferredSizes;
/*    */   final Dimension[] myMinimumSizes;
/*    */   
/*    */   public LayoutState(GridLayoutManager layout, boolean ignoreInvisibleComponents)
/*    */   {
/* 34 */     ArrayList componentsList = new ArrayList(layout.getComponentCount());
/* 35 */     ArrayList constraintsList = new ArrayList(layout.getComponentCount());
/* 36 */     for (int i = 0; i < layout.getComponentCount(); i++) {
/* 37 */       Component component = layout.getComponent(i);
/* 38 */       if ((!ignoreInvisibleComponents) || (component.isVisible())) {
/* 39 */         componentsList.add(component);
/* 40 */         GridConstraints constraints = layout.getConstraints(i);
/* 41 */         constraintsList.add(constraints);
/*    */       }
/*    */     }
/*    */     
/* 45 */     this.myComponents = ((Component[])componentsList.toArray(new Component[componentsList.size()]));
/* 46 */     this.myConstraints = ((GridConstraints[])constraintsList.toArray(new GridConstraints[constraintsList.size()]));
/*    */     
/* 48 */     this.myMinimumSizes = new Dimension[this.myComponents.length];
/* 49 */     this.myPreferredSizes = new Dimension[this.myComponents.length];
/*    */     
/* 51 */     this.myColumnCount = layout.getColumnCount();
/* 52 */     this.myRowCount = layout.getRowCount();
/*    */   }
/*    */   
/*    */   public int getComponentCount() {
/* 56 */     return this.myComponents.length;
/*    */   }
/*    */   
/*    */   public Component getComponent(int index) {
/* 60 */     return this.myComponents[index];
/*    */   }
/*    */   
/*    */   public GridConstraints getConstraints(int index) {
/* 64 */     return this.myConstraints[index];
/*    */   }
/*    */   
/*    */   public int getColumnCount() {
/* 68 */     return this.myColumnCount;
/*    */   }
/*    */   
/*    */   public int getRowCount() {
/* 72 */     return this.myRowCount;
/*    */   }
/*    */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/LayoutState.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */