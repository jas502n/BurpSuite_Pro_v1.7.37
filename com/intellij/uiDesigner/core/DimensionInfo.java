/*     */ package com.intellij.uiDesigner.core;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DimensionInfo
/*     */ {
/*     */   private final int[] myCell;
/*     */   private final int[] mySpan;
/*     */   protected final LayoutState myLayoutState;
/*     */   private final int[] myStretches;
/*     */   private final int[] mySpansAfterElimination;
/*     */   private final int[] myCellSizePolicies;
/*     */   private final int myGap;
/*     */   
/*     */   public DimensionInfo(LayoutState layoutState, int gap)
/*     */   {
/*  35 */     if (layoutState == null) {
/*  36 */       throw new IllegalArgumentException("layoutState cannot be null");
/*     */     }
/*  38 */     if (gap < 0) {
/*  39 */       throw new IllegalArgumentException("invalid gap: " + gap);
/*     */     }
/*  41 */     this.myLayoutState = layoutState;
/*  42 */     this.myGap = gap;
/*     */     
/*  44 */     this.myCell = new int[layoutState.getComponentCount()];
/*  45 */     this.mySpan = new int[layoutState.getComponentCount()];
/*     */     
/*  47 */     for (int i = 0; i < layoutState.getComponentCount(); i++) {
/*  48 */       GridConstraints c = layoutState.getConstraints(i);
/*  49 */       this.myCell[i] = getOriginalCell(c);
/*  50 */       this.mySpan[i] = getOriginalSpan(c);
/*     */     }
/*     */     
/*  53 */     this.myStretches = new int[getCellCount()];
/*  54 */     for (int i = 0; i < this.myStretches.length; i++) {
/*  55 */       this.myStretches[i] = 1;
/*     */     }
/*     */     
/*     */ 
/*  59 */     ArrayList elimitated = new ArrayList();
/*  60 */     this.mySpansAfterElimination = ((int[])this.mySpan.clone());
/*  61 */     Util.eliminate((int[])this.myCell.clone(), this.mySpansAfterElimination, elimitated);
/*     */     
/*  63 */     this.myCellSizePolicies = new int[getCellCount()];
/*  64 */     for (int i = 0; i < this.myCellSizePolicies.length; i++) {
/*  65 */       this.myCellSizePolicies[i] = getCellSizePolicyImpl(i, elimitated);
/*     */     }
/*     */   }
/*     */   
/*     */   public final int getComponentCount() {
/*  70 */     return this.myLayoutState.getComponentCount();
/*     */   }
/*     */   
/*     */   public final Component getComponent(int componentIndex) {
/*  74 */     return this.myLayoutState.getComponent(componentIndex);
/*     */   }
/*     */   
/*     */ 
/*  78 */   public final GridConstraints getConstraints(int componentIndex) { return this.myLayoutState.getConstraints(componentIndex); }
/*     */   
/*     */   public abstract int getCellCount();
/*     */   
/*     */   public abstract int getPreferredWidth(int paramInt);
/*     */   
/*     */   public abstract int getMinimumWidth(int paramInt);
/*     */   
/*     */   public abstract DimensionInfo getDimensionInfo(GridLayoutManager paramGridLayoutManager);
/*     */   
/*  88 */   public final int getCell(int componentIndex) { return this.myCell[componentIndex]; }
/*     */   
/*     */   public final int getSpan(int componentIndex)
/*     */   {
/*  92 */     return this.mySpan[componentIndex];
/*     */   }
/*     */   
/*     */   public final int getStretch(int cellIndex) {
/*  96 */     return this.myStretches[cellIndex];
/*     */   }
/*     */   
/*     */   protected abstract int getOriginalCell(GridConstraints paramGridConstraints);
/*     */   
/*     */   protected abstract int getOriginalSpan(GridConstraints paramGridConstraints);
/*     */   
/*     */   abstract int getSizePolicy(int paramInt);
/*     */   
/*     */   abstract int getChildLayoutCellCount(GridLayoutManager paramGridLayoutManager);
/*     */   
/* 107 */   public final int getGap() { return this.myGap; }
/*     */   
/*     */   public boolean componentBelongsCell(int componentIndex, int cellIndex)
/*     */   {
/* 111 */     int componentStartCell = getCell(componentIndex);
/* 112 */     int span = getSpan(componentIndex);
/* 113 */     return (componentStartCell <= cellIndex) && (cellIndex < componentStartCell + span);
/*     */   }
/*     */   
/*     */   public final int getCellSizePolicy(int cellIndex) {
/* 117 */     return this.myCellSizePolicies[cellIndex];
/*     */   }
/*     */   
/*     */   private int getCellSizePolicyImpl(int cellIndex, ArrayList eliminatedCells) {
/* 121 */     int policyFromChild = getCellSizePolicyFromInheriting(cellIndex);
/* 122 */     if (policyFromChild != -1) {
/* 123 */       return policyFromChild;
/*     */     }
/* 125 */     for (int i = eliminatedCells.size() - 1; i >= 0; i--) {
/* 126 */       if (cellIndex == ((Integer)eliminatedCells.get(i)).intValue()) {
/* 127 */         return 1;
/*     */       }
/*     */     }
/*     */     
/* 131 */     return calcCellSizePolicy(cellIndex);
/*     */   }
/*     */   
/*     */   private int calcCellSizePolicy(int cellIndex) {
/* 135 */     boolean canShrink = true;
/* 136 */     boolean canGrow = false;
/* 137 */     boolean wantGrow = false;
/*     */     
/* 139 */     boolean weakCanGrow = true;
/* 140 */     boolean weakWantGrow = true;
/*     */     
/* 142 */     int countOfBelongingComponents = 0;
/*     */     
/* 144 */     for (int i = 0; i < getComponentCount(); i++) {
/* 145 */       if (componentBelongsCell(i, cellIndex))
/*     */       {
/*     */ 
/*     */ 
/* 149 */         countOfBelongingComponents++;
/*     */         
/* 151 */         int p = getSizePolicy(i);
/*     */         
/* 153 */         boolean thisCanShrink = (p & 0x1) != 0;
/* 154 */         boolean thisCanGrow = (p & 0x2) != 0;
/* 155 */         boolean thisWantGrow = (p & 0x4) != 0;
/*     */         
/* 157 */         if ((getCell(i) == cellIndex) && (this.mySpansAfterElimination[i] == 1)) {
/* 158 */           canShrink &= thisCanShrink;
/* 159 */           canGrow |= thisCanGrow;
/* 160 */           wantGrow |= thisWantGrow;
/*     */         }
/*     */         
/* 163 */         if (!thisCanGrow) {
/* 164 */           weakCanGrow = false;
/*     */         }
/* 166 */         if (!thisWantGrow) {
/* 167 */           weakWantGrow = false;
/*     */         }
/*     */       }
/*     */     }
/* 171 */     return (canShrink ? 1 : 0) | ((canGrow) || ((countOfBelongingComponents > 0) && (weakCanGrow)) ? 2 : 0) | ((wantGrow) || ((countOfBelongingComponents > 0) && (weakWantGrow)) ? 4 : 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int getCellSizePolicyFromInheriting(int cellIndex)
/*     */   {
/* 178 */     int nonInheritingComponentsInCell = 0;
/* 179 */     int policyFromInheriting = -1;
/* 180 */     for (int i = getComponentCount() - 1; i >= 0; i--)
/* 181 */       if (componentBelongsCell(i, cellIndex))
/*     */       {
/*     */ 
/* 184 */         Component child = getComponent(i);
/* 185 */         GridConstraints c = getConstraints(i);
/* 186 */         Container container = findAlignedChild(child, c);
/* 187 */         if (container != null) {
/* 188 */           GridLayoutManager grid = (GridLayoutManager)container.getLayout();
/* 189 */           grid.validateInfos(container);
/* 190 */           DimensionInfo info = getDimensionInfo(grid);
/* 191 */           int policy = info.calcCellSizePolicy(cellIndex - getOriginalCell(c));
/* 192 */           if (policyFromInheriting == -1) {
/* 193 */             policyFromInheriting = policy;
/*     */           }
/*     */           else {
/* 196 */             policyFromInheriting |= policy;
/*     */           }
/*     */         }
/* 199 */         else if ((getOriginalCell(c) == cellIndex) && (getOriginalSpan(c) == 1) && (!(child instanceof Spacer))) {
/* 200 */           nonInheritingComponentsInCell++;
/*     */         }
/*     */       }
/* 203 */     if (nonInheritingComponentsInCell > 0) {
/* 204 */       return -1;
/*     */     }
/* 206 */     return policyFromInheriting;
/*     */   }
/*     */   
/*     */   public static Container findAlignedChild(Component child, GridConstraints c) {
/* 210 */     if ((c.isUseParentLayout()) && ((child instanceof Container))) {
/* 211 */       Container container = (Container)child;
/* 212 */       if ((container.getLayout() instanceof GridLayoutManager)) {
/* 213 */         return container;
/*     */       }
/* 215 */       if ((container.getComponentCount() == 1) && ((container.getComponent(0) instanceof Container)))
/*     */       {
/*     */ 
/* 218 */         Container childContainer = (Container)container.getComponent(0);
/* 219 */         if ((childContainer.getLayout() instanceof GridLayoutManager)) {
/* 220 */           return childContainer;
/*     */         }
/*     */       }
/*     */     }
/* 224 */     return null;
/*     */   }
/*     */   
/*     */   protected final Dimension getPreferredSize(int componentIndex) {
/* 228 */     Dimension size = this.myLayoutState.myPreferredSizes[componentIndex];
/* 229 */     if (size == null) {
/* 230 */       size = Util.getPreferredSize(this.myLayoutState.getComponent(componentIndex), this.myLayoutState.getConstraints(componentIndex), true);
/* 231 */       this.myLayoutState.myPreferredSizes[componentIndex] = size;
/*     */     }
/* 233 */     return size;
/*     */   }
/*     */   
/*     */   protected final Dimension getMinimumSize(int componentIndex) {
/* 237 */     Dimension size = this.myLayoutState.myMinimumSizes[componentIndex];
/* 238 */     if (size == null) {
/* 239 */       size = Util.getMinimumSize(this.myLayoutState.getComponent(componentIndex), this.myLayoutState.getConstraints(componentIndex), true);
/* 240 */       this.myLayoutState.myMinimumSizes[componentIndex] = size;
/*     */     }
/* 242 */     return size;
/*     */   }
/*     */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/DimensionInfo.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */