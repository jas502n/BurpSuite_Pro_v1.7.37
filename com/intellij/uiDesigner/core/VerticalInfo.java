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
/*    */ final class VerticalInfo
/*    */   extends DimensionInfo
/*    */ {
/*    */   public VerticalInfo(LayoutState layoutState, int gap)
/*    */   {
/* 20 */     super(layoutState, gap);
/*    */   }
/*    */   
/*    */   protected int getOriginalCell(GridConstraints constraints) {
/* 24 */     return constraints.getRow();
/*    */   }
/*    */   
/*    */   protected int getOriginalSpan(GridConstraints constraints) {
/* 28 */     return constraints.getRowSpan();
/*    */   }
/*    */   
/*    */   int getSizePolicy(int componentIndex) {
/* 32 */     return this.myLayoutState.getConstraints(componentIndex).getVSizePolicy();
/*    */   }
/*    */   
/*    */   int getChildLayoutCellCount(GridLayoutManager childLayout) {
/* 36 */     return childLayout.getRowCount();
/*    */   }
/*    */   
/*    */   public int getMinimumWidth(int componentIndex) {
/* 40 */     return getMinimumSize(componentIndex).height;
/*    */   }
/*    */   
/*    */   public DimensionInfo getDimensionInfo(GridLayoutManager grid) {
/* 44 */     return grid.myVerticalInfo;
/*    */   }
/*    */   
/*    */   public int getCellCount() {
/* 48 */     return this.myLayoutState.getRowCount();
/*    */   }
/*    */   
/*    */   public int getPreferredWidth(int componentIndex) {
/* 52 */     return getPreferredSize(componentIndex).height;
/*    */   }
/*    */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/VerticalInfo.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */