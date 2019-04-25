/*    */ package com.intellij.uiDesigner.core;
/*    */ 
/*    */ import java.awt.Dimension;
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
/*    */ final class HorizontalInfo
/*    */   extends DimensionInfo
/*    */ {
/*    */   public HorizontalInfo(LayoutState layoutState, int gap)
/*    */   {
/* 20 */     super(layoutState, gap);
/*    */   }
/*    */   
/*    */   protected int getOriginalCell(GridConstraints constraints) {
/* 24 */     return constraints.getColumn();
/*    */   }
/*    */   
/*    */   protected int getOriginalSpan(GridConstraints constraints) {
/* 28 */     return constraints.getColSpan();
/*    */   }
/*    */   
/*    */   int getSizePolicy(int componentIndex) {
/* 32 */     return this.myLayoutState.getConstraints(componentIndex).getHSizePolicy();
/*    */   }
/*    */   
/*    */   int getChildLayoutCellCount(GridLayoutManager childLayout) {
/* 36 */     return childLayout.getColumnCount();
/*    */   }
/*    */   
/*    */   public int getMinimumWidth(int componentIndex) {
/* 40 */     return getMinimumSize(componentIndex).width;
/*    */   }
/*    */   
/*    */   public DimensionInfo getDimensionInfo(GridLayoutManager grid) {
/* 44 */     return grid.myHorizontalInfo;
/*    */   }
/*    */   
/*    */   public int getCellCount() {
/* 48 */     return this.myLayoutState.getColumnCount();
/*    */   }
/*    */   
/*    */   public int getPreferredWidth(int componentIndex) {
/* 52 */     return getPreferredSize(componentIndex).width;
/*    */   }
/*    */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/HorizontalInfo.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */