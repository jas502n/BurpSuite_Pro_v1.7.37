/*     */ package com.intellij.uiDesigner.core;
/*     */ 
/*     */ import java.awt.Dimension;
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
/*     */ 
/*     */ 
/*     */ public final class GridConstraints
/*     */   implements Cloneable
/*     */ {
/*  25 */   public static final GridConstraints[] EMPTY_ARRAY = new GridConstraints[0];
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int FILL_NONE = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   public static final int FILL_HORIZONTAL = 1;
/*     */   
/*     */ 
/*     */   public static final int FILL_VERTICAL = 2;
/*     */   
/*     */ 
/*     */   public static final int FILL_BOTH = 3;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_CENTER = 0;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_NORTH = 1;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_SOUTH = 2;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_EAST = 4;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_WEST = 8;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_NORTHEAST = 5;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_SOUTHEAST = 6;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_SOUTHWEST = 10;
/*     */   
/*     */ 
/*     */   public static final int ANCHOR_NORTHWEST = 9;
/*     */   
/*     */ 
/*     */   public static final int SIZEPOLICY_FIXED = 0;
/*     */   
/*     */ 
/*     */   public static final int SIZEPOLICY_CAN_SHRINK = 1;
/*     */   
/*     */ 
/*     */   public static final int SIZEPOLICY_CAN_GROW = 2;
/*     */   
/*     */ 
/*     */   public static final int SIZEPOLICY_WANT_GROW = 4;
/*     */   
/*     */ 
/*     */   public static final int ALIGN_LEFT = 0;
/*     */   
/*     */ 
/*     */   public static final int ALIGN_CENTER = 1;
/*     */   
/*     */ 
/*     */   public static final int ALIGN_RIGHT = 2;
/*     */   
/*     */ 
/*     */   public static final int ALIGN_FILL = 3;
/*     */   
/*     */ 
/*     */   private int myRow;
/*     */   
/*     */ 
/*     */   private int myColumn;
/*     */   
/*     */ 
/*     */   private int myRowSpan;
/*     */   
/*     */ 
/*     */   private int myColSpan;
/*     */   
/*     */ 
/*     */   private int myVSizePolicy;
/*     */   
/*     */ 
/*     */   private int myHSizePolicy;
/*     */   
/*     */ 
/*     */   private int myFill;
/*     */   
/*     */ 
/*     */   private int myAnchor;
/*     */   
/*     */ 
/*     */   public final Dimension myMinimumSize;
/*     */   
/*     */ 
/*     */   public final Dimension myPreferredSize;
/*     */   
/*     */ 
/*     */   public final Dimension myMaximumSize;
/*     */   
/*     */ 
/*     */   private int myIndent;
/*     */   
/*     */ 
/*     */   private boolean myUseParentLayout;
/*     */   
/*     */ 
/*     */ 
/*     */   public GridConstraints()
/*     */   {
/* 135 */     this.myRowSpan = 1;
/* 136 */     this.myColSpan = 1;
/* 137 */     this.myVSizePolicy = 3;
/* 138 */     this.myHSizePolicy = 3;
/* 139 */     this.myFill = 0;
/* 140 */     this.myAnchor = 0;
/* 141 */     this.myMinimumSize = new Dimension(-1, -1);
/* 142 */     this.myPreferredSize = new Dimension(-1, -1);
/* 143 */     this.myMaximumSize = new Dimension(-1, -1);
/* 144 */     this.myIndent = 0;
/*     */   }
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
/*     */   public GridConstraints(int row, int column, int rowSpan, int colSpan, int anchor, int fill, int HSizePolicy, int VSizePolicy, Dimension minimumSize, Dimension preferredSize, Dimension maximumSize)
/*     */   {
/* 159 */     this.myRow = row;
/* 160 */     this.myColumn = column;
/* 161 */     this.myRowSpan = rowSpan;
/* 162 */     this.myColSpan = colSpan;
/* 163 */     this.myAnchor = anchor;
/* 164 */     this.myFill = fill;
/* 165 */     this.myHSizePolicy = HSizePolicy;
/* 166 */     this.myVSizePolicy = VSizePolicy;
/* 167 */     this.myMinimumSize = (minimumSize != null ? new Dimension(minimumSize) : new Dimension(-1, -1));
/* 168 */     this.myPreferredSize = (preferredSize != null ? new Dimension(preferredSize) : new Dimension(-1, -1));
/* 169 */     this.myMaximumSize = (maximumSize != null ? new Dimension(maximumSize) : new Dimension(-1, -1));
/* 170 */     this.myIndent = 0;
/*     */   }
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
/*     */   public GridConstraints(int row, int column, int rowSpan, int colSpan, int anchor, int fill, int HSizePolicy, int VSizePolicy, Dimension minimumSize, Dimension preferredSize, Dimension maximumSize, int indent)
/*     */   {
/* 186 */     this(row, column, rowSpan, colSpan, anchor, fill, HSizePolicy, VSizePolicy, minimumSize, preferredSize, maximumSize);
/* 187 */     this.myIndent = indent;
/*     */   }
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
/*     */   public GridConstraints(int row, int column, int rowSpan, int colSpan, int anchor, int fill, int HSizePolicy, int VSizePolicy, Dimension minimumSize, Dimension preferredSize, Dimension maximumSize, int indent, boolean useParentLayout)
/*     */   {
/* 204 */     this(row, column, rowSpan, colSpan, anchor, fill, HSizePolicy, VSizePolicy, minimumSize, preferredSize, maximumSize, indent);
/* 205 */     this.myUseParentLayout = useParentLayout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 212 */     return new GridConstraints(this.myRow, this.myColumn, this.myRowSpan, this.myColSpan, this.myAnchor, this.myFill, this.myHSizePolicy, this.myVSizePolicy, new Dimension(this.myMinimumSize), new Dimension(this.myPreferredSize), new Dimension(this.myMaximumSize), this.myIndent, this.myUseParentLayout);
/*     */   }
/*     */   
/*     */ 
/*     */   public int getColumn()
/*     */   {
/* 218 */     return this.myColumn;
/*     */   }
/*     */   
/*     */   public void setColumn(int column) {
/* 222 */     if (column < 0) {
/* 223 */       throw new IllegalArgumentException("wrong column: " + column);
/*     */     }
/* 225 */     this.myColumn = column;
/*     */   }
/*     */   
/*     */   public int getRow() {
/* 229 */     return this.myRow;
/*     */   }
/*     */   
/*     */   public void setRow(int row) {
/* 233 */     if (row < 0) {
/* 234 */       throw new IllegalArgumentException("wrong row: " + row);
/*     */     }
/* 236 */     this.myRow = row;
/*     */   }
/*     */   
/*     */   public int getRowSpan() {
/* 240 */     return this.myRowSpan;
/*     */   }
/*     */   
/*     */   public void setRowSpan(int rowSpan) {
/* 244 */     if (rowSpan <= 0) {
/* 245 */       throw new IllegalArgumentException("wrong rowSpan: " + rowSpan);
/*     */     }
/* 247 */     this.myRowSpan = rowSpan;
/*     */   }
/*     */   
/*     */   public int getColSpan() {
/* 251 */     return this.myColSpan;
/*     */   }
/*     */   
/*     */   public void setColSpan(int colSpan) {
/* 255 */     if (colSpan <= 0) {
/* 256 */       throw new IllegalArgumentException("wrong colSpan: " + colSpan);
/*     */     }
/* 258 */     this.myColSpan = colSpan;
/*     */   }
/*     */   
/*     */   public int getHSizePolicy() {
/* 262 */     return this.myHSizePolicy;
/*     */   }
/*     */   
/*     */   public void setHSizePolicy(int sizePolicy) {
/* 266 */     if ((sizePolicy < 0) || (sizePolicy > 7)) {
/* 267 */       throw new IllegalArgumentException("invalid sizePolicy: " + sizePolicy);
/*     */     }
/* 269 */     this.myHSizePolicy = sizePolicy;
/*     */   }
/*     */   
/*     */   public int getVSizePolicy() {
/* 273 */     return this.myVSizePolicy;
/*     */   }
/*     */   
/*     */   public void setVSizePolicy(int sizePolicy) {
/* 277 */     if ((sizePolicy < 0) || (sizePolicy > 7)) {
/* 278 */       throw new IllegalArgumentException("invalid sizePolicy: " + sizePolicy);
/*     */     }
/* 280 */     this.myVSizePolicy = sizePolicy;
/*     */   }
/*     */   
/*     */   public int getAnchor() {
/* 284 */     return this.myAnchor;
/*     */   }
/*     */   
/*     */   public void setAnchor(int anchor) {
/* 288 */     if ((anchor < 0) || (anchor > 15)) {
/* 289 */       throw new IllegalArgumentException("invalid anchor: " + anchor);
/*     */     }
/* 291 */     this.myAnchor = anchor;
/*     */   }
/*     */   
/*     */   public int getFill() {
/* 295 */     return this.myFill;
/*     */   }
/*     */   
/*     */   public void setFill(int fill) {
/* 299 */     if ((fill != 0) && (fill != 1) && (fill != 2) && (fill != 3))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 305 */       throw new IllegalArgumentException("invalid fill: " + fill);
/*     */     }
/* 307 */     this.myFill = fill;
/*     */   }
/*     */   
/*     */   public int getIndent() {
/* 311 */     return this.myIndent;
/*     */   }
/*     */   
/*     */   public void setIndent(int indent) {
/* 315 */     this.myIndent = indent;
/*     */   }
/*     */   
/*     */   public boolean isUseParentLayout() {
/* 319 */     return this.myUseParentLayout;
/*     */   }
/*     */   
/*     */   public void setUseParentLayout(boolean useParentLayout) {
/* 323 */     this.myUseParentLayout = useParentLayout;
/*     */   }
/*     */   
/*     */   public GridConstraints store() {
/* 327 */     GridConstraints copy = new GridConstraints();
/*     */     
/* 329 */     copy.setRow(this.myRow);
/* 330 */     copy.setColumn(this.myColumn);
/* 331 */     copy.setColSpan(this.myColSpan);
/* 332 */     copy.setRowSpan(this.myRowSpan);
/* 333 */     copy.setVSizePolicy(this.myVSizePolicy);
/* 334 */     copy.setHSizePolicy(this.myHSizePolicy);
/* 335 */     copy.setFill(this.myFill);
/* 336 */     copy.setAnchor(this.myAnchor);
/* 337 */     copy.setIndent(this.myIndent);
/* 338 */     copy.setUseParentLayout(this.myUseParentLayout);
/*     */     
/* 340 */     copy.myMinimumSize.setSize(this.myMinimumSize);
/* 341 */     copy.myPreferredSize.setSize(this.myPreferredSize);
/* 342 */     copy.myMaximumSize.setSize(this.myMaximumSize);
/*     */     
/* 344 */     return copy;
/*     */   }
/*     */   
/*     */   public void restore(GridConstraints constraints) {
/* 348 */     this.myRow = constraints.myRow;
/* 349 */     this.myColumn = constraints.myColumn;
/* 350 */     this.myRowSpan = constraints.myRowSpan;
/* 351 */     this.myColSpan = constraints.myColSpan;
/* 352 */     this.myHSizePolicy = constraints.myHSizePolicy;
/* 353 */     this.myVSizePolicy = constraints.myVSizePolicy;
/* 354 */     this.myFill = constraints.myFill;
/* 355 */     this.myAnchor = constraints.myAnchor;
/* 356 */     this.myIndent = constraints.myIndent;
/* 357 */     this.myUseParentLayout = constraints.myUseParentLayout;
/*     */     
/*     */ 
/* 360 */     this.myMinimumSize.setSize(constraints.myMinimumSize);
/* 361 */     this.myPreferredSize.setSize(constraints.myPreferredSize);
/* 362 */     this.myMaximumSize.setSize(constraints.myMaximumSize);
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/* 366 */     if (this == o) return true;
/* 367 */     if (!(o instanceof GridConstraints)) { return false;
/*     */     }
/* 369 */     GridConstraints gridConstraints = (GridConstraints)o;
/*     */     
/* 371 */     if (this.myAnchor != gridConstraints.myAnchor) return false;
/* 372 */     if (this.myColSpan != gridConstraints.myColSpan) return false;
/* 373 */     if (this.myColumn != gridConstraints.myColumn) return false;
/* 374 */     if (this.myFill != gridConstraints.myFill) return false;
/* 375 */     if (this.myHSizePolicy != gridConstraints.myHSizePolicy) return false;
/* 376 */     if (this.myRow != gridConstraints.myRow) return false;
/* 377 */     if (this.myRowSpan != gridConstraints.myRowSpan) return false;
/* 378 */     if (this.myVSizePolicy != gridConstraints.myVSizePolicy) return false;
/* 379 */     if (this.myMaximumSize != null ? !this.myMaximumSize.equals(gridConstraints.myMaximumSize) : gridConstraints.myMaximumSize != null) return false;
/* 380 */     if (this.myMinimumSize != null ? !this.myMinimumSize.equals(gridConstraints.myMinimumSize) : gridConstraints.myMinimumSize != null) return false;
/* 381 */     if (this.myPreferredSize != null ? !this.myPreferredSize.equals(gridConstraints.myPreferredSize) : gridConstraints.myPreferredSize != null) return false;
/* 382 */     if (this.myIndent != gridConstraints.myIndent) return false;
/* 383 */     if (this.myUseParentLayout != gridConstraints.myUseParentLayout) { return false;
/*     */     }
/* 385 */     return true;
/*     */   }
/*     */   
/*     */   public int hashCode()
/*     */   {
/* 390 */     int result = this.myRow;
/* 391 */     result = 29 * result + this.myColumn;
/* 392 */     result = 29 * result + this.myRowSpan;
/* 393 */     result = 29 * result + this.myColSpan;
/* 394 */     result = 29 * result + this.myVSizePolicy;
/* 395 */     result = 29 * result + this.myHSizePolicy;
/* 396 */     result = 29 * result + this.myFill;
/* 397 */     result = 29 * result + this.myAnchor;
/* 398 */     result = 29 * result + (this.myMinimumSize != null ? this.myMinimumSize.hashCode() : 0);
/* 399 */     result = 29 * result + (this.myPreferredSize != null ? this.myPreferredSize.hashCode() : 0);
/* 400 */     result = 29 * result + (this.myMaximumSize != null ? this.myMaximumSize.hashCode() : 0);
/* 401 */     result = 29 * result + this.myIndent;
/* 402 */     result = 29 * result + (this.myUseParentLayout ? 1 : 0);
/* 403 */     return result;
/*     */   }
/*     */   
/*     */   public int getCell(boolean isRow) {
/* 407 */     return isRow ? getRow() : getColumn();
/*     */   }
/*     */   
/*     */   public void setCell(boolean isRow, int value) {
/* 411 */     if (isRow) {
/* 412 */       setRow(value);
/*     */     }
/*     */     else {
/* 415 */       setColumn(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSpan(boolean isRow) {
/* 420 */     return isRow ? getRowSpan() : getColSpan();
/*     */   }
/*     */   
/*     */   public void setSpan(boolean isRow, int value) {
/* 424 */     if (isRow) {
/* 425 */       setRowSpan(value);
/*     */     }
/*     */     else {
/* 428 */       setColSpan(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean contains(boolean isRow, int cell) {
/* 433 */     if (isRow) {
/* 434 */       return (cell >= this.myRow) && (cell < this.myRow + this.myRowSpan);
/*     */     }
/* 436 */     return (cell >= this.myColumn) && (cell < this.myColumn + this.myColSpan);
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 441 */     return "GridConstraints (row=" + this.myRow + ", col=" + this.myColumn + ", rowspan=" + this.myRowSpan + ", colspan=" + this.myColSpan + ")";
/*     */   }
/*     */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/GridConstraints.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */