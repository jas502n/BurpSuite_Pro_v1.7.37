/*     */ package com.intellij.uiDesigner.core;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.util.Arrays;
/*     */ import javax.swing.JComponent;
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
/*     */ public final class GridLayoutManager
/*     */   extends AbstractLayout
/*     */ {
/*  26 */   private int myMinCellSize = 20;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final int[] myRowStretches;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] myColumnStretches;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] myYs;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] myHeights;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] myXs;
/*     */   
/*     */ 
/*     */ 
/*     */   private final int[] myWidths;
/*     */   
/*     */ 
/*     */ 
/*     */   private LayoutState myLayoutState;
/*     */   
/*     */ 
/*     */ 
/*     */   DimensionInfo myHorizontalInfo;
/*     */   
/*     */ 
/*     */ 
/*     */   DimensionInfo myVerticalInfo;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean mySameSizeHorizontally;
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean mySameSizeVertically;
/*     */   
/*     */ 
/*     */ 
/*  75 */   public static Object DESIGN_TIME_INSETS = new Object();
/*     */   private static final int SKIP_ROW = 1;
/*     */   private static final int SKIP_COL = 2;
/*     */   
/*     */   public GridLayoutManager(int rowCount, int columnCount)
/*     */   {
/*  81 */     if (columnCount < 1) {
/*  82 */       throw new IllegalArgumentException("wrong columnCount: " + columnCount);
/*     */     }
/*  84 */     if (rowCount < 1) {
/*  85 */       throw new IllegalArgumentException("wrong rowCount: " + rowCount);
/*     */     }
/*     */     
/*  88 */     this.myRowStretches = new int[rowCount];
/*  89 */     for (int i = 0; i < rowCount; i++) {
/*  90 */       this.myRowStretches[i] = 1;
/*     */     }
/*  92 */     this.myColumnStretches = new int[columnCount];
/*  93 */     for (int i = 0; i < columnCount; i++) {
/*  94 */       this.myColumnStretches[i] = 1;
/*     */     }
/*     */     
/*  97 */     this.myXs = new int[columnCount];
/*  98 */     this.myWidths = new int[columnCount];
/*     */     
/* 100 */     this.myYs = new int[rowCount];
/* 101 */     this.myHeights = new int[rowCount];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public GridLayoutManager(int rowCount, int columnCount, Insets margin, int hGap, int vGap)
/*     */   {
/* 108 */     this(rowCount, columnCount);
/* 109 */     setMargin(margin);
/* 110 */     setHGap(hGap);
/* 111 */     setVGap(vGap);
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
/*     */   public GridLayoutManager(int rowCount, int columnCount, Insets margin, int hGap, int vGap, boolean sameSizeHorizontally, boolean sameSizeVertically)
/*     */   {
/* 127 */     this(rowCount, columnCount, margin, hGap, vGap);
/* 128 */     this.mySameSizeHorizontally = sameSizeHorizontally;
/* 129 */     this.mySameSizeVertically = sameSizeVertically;
/*     */   }
/*     */   
/*     */   public void addLayoutComponent(Component comp, Object constraints) {
/* 133 */     GridConstraints c = (GridConstraints)constraints;
/* 134 */     int row = c.getRow();
/* 135 */     int rowSpan = c.getRowSpan();
/* 136 */     int rowCount = getRowCount();
/* 137 */     if ((row < 0) || (row >= rowCount)) {
/* 138 */       throw new IllegalArgumentException("wrong row: " + row);
/*     */     }
/* 140 */     if (row + rowSpan - 1 >= rowCount) {
/* 141 */       throw new IllegalArgumentException("wrong row span: " + rowSpan + "; row=" + row + " rowCount=" + rowCount);
/*     */     }
/* 143 */     int column = c.getColumn();
/* 144 */     int colSpan = c.getColSpan();
/* 145 */     int columnCount = getColumnCount();
/* 146 */     if ((column < 0) || (column >= columnCount)) {
/* 147 */       throw new IllegalArgumentException("wrong column: " + column);
/*     */     }
/* 149 */     if (column + colSpan - 1 >= columnCount) {
/* 150 */       throw new IllegalArgumentException("wrong col span: " + colSpan + "; column=" + column + " columnCount=" + columnCount);
/*     */     }
/*     */     
/* 153 */     super.addLayoutComponent(comp, constraints);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getRowCount()
/*     */   {
/* 160 */     return this.myRowStretches.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getColumnCount()
/*     */   {
/* 167 */     return this.myColumnStretches.length;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getRowStretch(int rowIndex)
/*     */   {
/* 175 */     return this.myRowStretches[rowIndex];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRowStretch(int rowIndex, int stretch)
/*     */   {
/* 183 */     if (stretch < 1) {
/* 184 */       throw new IllegalArgumentException("wrong stretch: " + stretch);
/*     */     }
/* 186 */     this.myRowStretches[rowIndex] = stretch;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getColumnStretch(int columnIndex)
/*     */   {
/* 194 */     return this.myColumnStretches[columnIndex];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setColumnStretch(int columnIndex, int stretch)
/*     */   {
/* 202 */     if (stretch < 1) {
/* 203 */       throw new IllegalArgumentException("wrong stretch: " + stretch);
/*     */     }
/* 205 */     this.myColumnStretches[columnIndex] = stretch;
/*     */   }
/*     */   
/*     */   public Dimension maximumLayoutSize(Container target) {
/* 209 */     return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
/*     */   }
/*     */   
/*     */   public Dimension minimumLayoutSize(Container container) {
/* 213 */     validateInfos(container);
/*     */     
/*     */ 
/* 216 */     DimensionInfo horizontalInfo = this.myHorizontalInfo;
/* 217 */     DimensionInfo verticalInfo = this.myVerticalInfo;
/*     */     
/* 219 */     Dimension result = getTotalGap(container, horizontalInfo, verticalInfo);
/*     */     
/* 221 */     int[] widths = getMinSizes(horizontalInfo);
/* 222 */     if (this.mySameSizeHorizontally) {
/* 223 */       makeSameSizes(widths);
/*     */     }
/* 225 */     result.width += sum(widths);
/*     */     
/* 227 */     int[] heights = getMinSizes(verticalInfo);
/* 228 */     if (this.mySameSizeVertically) {
/* 229 */       makeSameSizes(heights);
/*     */     }
/* 231 */     result.height += sum(heights);
/*     */     
/* 233 */     return result;
/*     */   }
/*     */   
/*     */   private static void makeSameSizes(int[] widths) {
/* 237 */     int max = widths[0];
/* 238 */     for (int i = 0; i < widths.length; i++) {
/* 239 */       int width = widths[i];
/* 240 */       max = Math.max(width, max);
/*     */     }
/*     */     
/* 243 */     for (int i = 0; i < widths.length; i++) {
/* 244 */       widths[i] = max;
/*     */     }
/*     */   }
/*     */   
/*     */   private static int[] getSameSizes(DimensionInfo info, int totalWidth) {
/* 249 */     int[] widths = new int[info.getCellCount()];
/*     */     
/* 251 */     int average = totalWidth / widths.length;
/* 252 */     int rest = totalWidth % widths.length;
/*     */     
/* 254 */     for (int i = 0; i < widths.length; i++) {
/* 255 */       widths[i] = average;
/* 256 */       if (rest > 0) {
/* 257 */         widths[i] += 1;
/* 258 */         rest--;
/*     */       }
/*     */     }
/*     */     
/* 262 */     return widths;
/*     */   }
/*     */   
/*     */   public Dimension preferredLayoutSize(Container container) {
/* 266 */     validateInfos(container);
/*     */     
/*     */ 
/* 269 */     DimensionInfo horizontalInfo = this.myHorizontalInfo;
/* 270 */     DimensionInfo verticalInfo = this.myVerticalInfo;
/*     */     
/* 272 */     Dimension result = getTotalGap(container, horizontalInfo, verticalInfo);
/*     */     
/* 274 */     int[] widths = getPrefSizes(horizontalInfo);
/* 275 */     if (this.mySameSizeHorizontally) {
/* 276 */       makeSameSizes(widths);
/*     */     }
/* 278 */     result.width += sum(widths);
/*     */     
/* 280 */     int[] heights = getPrefSizes(verticalInfo);
/* 281 */     if (this.mySameSizeVertically) {
/* 282 */       makeSameSizes(heights);
/*     */     }
/* 284 */     result.height += sum(heights);
/*     */     
/* 286 */     return result;
/*     */   }
/*     */   
/*     */   private static int sum(int[] ints) {
/* 290 */     int result = 0;
/* 291 */     for (int i = ints.length - 1; i >= 0; i--) {
/* 292 */       result += ints[i];
/*     */     }
/* 294 */     return result;
/*     */   }
/*     */   
/*     */   private Dimension getTotalGap(Container container, DimensionInfo hInfo, DimensionInfo vInfo) {
/* 298 */     Insets insets = getInsets(container);
/* 299 */     return new Dimension(insets.left + insets.right + 
/* 300 */       countGap(hInfo, 0, hInfo.getCellCount()) + this.myMargin.left + this.myMargin.right, insets.top + insets.bottom + 
/* 301 */       countGap(vInfo, 0, vInfo.getCellCount()) + this.myMargin.top + this.myMargin.bottom);
/*     */   }
/*     */   
/*     */   private static int getDesignTimeInsets(Container container) {
/* 305 */     while (container != null) {
/* 306 */       if ((container instanceof JComponent)) {
/* 307 */         Integer designTimeInsets = (Integer)((JComponent)container).getClientProperty(DESIGN_TIME_INSETS);
/* 308 */         if (designTimeInsets != null) {
/* 309 */           return designTimeInsets.intValue();
/*     */         }
/*     */       }
/* 312 */       container = container.getParent();
/*     */     }
/* 314 */     return 0;
/*     */   }
/*     */   
/*     */   private static Insets getInsets(Container container) {
/* 318 */     Insets insets = container.getInsets();
/* 319 */     int insetsValue = getDesignTimeInsets(container);
/* 320 */     if (insetsValue != 0) {
/* 321 */       return new Insets(insets.top + insetsValue, insets.left + insetsValue, insets.bottom + insetsValue, insets.right + insetsValue);
/*     */     }
/*     */     
/* 324 */     return insets;
/*     */   }
/*     */   
/*     */   private static int countGap(DimensionInfo info, int startCell, int cellCount) {
/* 328 */     int counter = 0;
/* 329 */     for (int cellIndex = startCell + cellCount - 2; 
/* 330 */         cellIndex >= startCell; 
/* 331 */         cellIndex--) {
/* 332 */       if (shouldAddGapAfterCell(info, cellIndex)) {
/* 333 */         counter++;
/*     */       }
/*     */     }
/* 336 */     return counter * info.getGap();
/*     */   }
/*     */   
/*     */   private static boolean shouldAddGapAfterCell(DimensionInfo info, int cellIndex) {
/* 340 */     if ((cellIndex < 0) || (cellIndex >= info.getCellCount())) {
/* 341 */       throw new IllegalArgumentException("wrong cellIndex: " + cellIndex + "; cellCount=" + info.getCellCount());
/*     */     }
/*     */     
/* 344 */     boolean endsInThis = false;
/* 345 */     boolean startsInNext = false;
/*     */     
/* 347 */     int indexOfNextNotEmpty = -1;
/* 348 */     for (int i = cellIndex + 1; i < info.getCellCount(); i++) {
/* 349 */       if (!isCellEmpty(info, i)) {
/* 350 */         indexOfNextNotEmpty = i;
/* 351 */         break;
/*     */       }
/*     */     }
/*     */     
/* 355 */     for (int i = 0; i < info.getComponentCount(); i++) {
/* 356 */       Component component = info.getComponent(i);
/* 357 */       if (!(component instanceof Spacer))
/*     */       {
/*     */ 
/*     */ 
/* 361 */         if ((info.componentBelongsCell(i, cellIndex)) && 
/* 362 */           (DimensionInfo.findAlignedChild(component, info.getConstraints(i)) != null)) {
/* 363 */           return true;
/*     */         }
/*     */         
/* 366 */         if (info.getCell(i) == indexOfNextNotEmpty) {
/* 367 */           startsInNext = true;
/*     */         }
/*     */         
/* 370 */         if (info.getCell(i) + info.getSpan(i) - 1 == cellIndex) {
/* 371 */           endsInThis = true;
/*     */         }
/*     */       }
/*     */     }
/* 375 */     return (startsInNext) && (endsInThis);
/*     */   }
/*     */   
/*     */   private static boolean isCellEmpty(DimensionInfo info, int cellIndex) {
/* 379 */     if ((cellIndex < 0) || (cellIndex >= info.getCellCount())) {
/* 380 */       throw new IllegalArgumentException("wrong cellIndex: " + cellIndex + "; cellCount=" + info.getCellCount());
/*     */     }
/* 382 */     for (int i = 0; i < info.getComponentCount(); i++) {
/* 383 */       Component component = info.getComponent(i);
/* 384 */       if ((info.getCell(i) == cellIndex) && (!(component instanceof Spacer))) {
/* 385 */         return false;
/*     */       }
/*     */     }
/* 388 */     return true;
/*     */   }
/*     */   
/*     */   public void layoutContainer(Container container) {
/* 392 */     validateInfos(container);
/*     */     
/*     */ 
/* 395 */     LayoutState layoutState = this.myLayoutState;
/* 396 */     DimensionInfo horizontalInfo = this.myHorizontalInfo;
/* 397 */     DimensionInfo verticalInfo = this.myVerticalInfo;
/*     */     
/* 399 */     Insets insets = getInsets(container);
/*     */     
/* 401 */     int skipLayout = checkSetSizesFromParent(container, insets);
/*     */     
/* 403 */     Dimension gap = getTotalGap(container, horizontalInfo, verticalInfo);
/*     */     
/* 405 */     Dimension size = container.getSize();
/* 406 */     size.width -= gap.width;
/* 407 */     size.height -= gap.height;
/*     */     
/* 409 */     Dimension prefSize = preferredLayoutSize(container);
/* 410 */     prefSize.width -= gap.width;
/* 411 */     prefSize.height -= gap.height;
/*     */     
/* 413 */     Dimension minSize = minimumLayoutSize(container);
/* 414 */     minSize.width -= gap.width;
/* 415 */     minSize.height -= gap.height;
/*     */     
/*     */ 
/* 418 */     if ((skipLayout & 0x1) == 0) { int[] heights;
/*     */       int[] heights;
/* 420 */       if (this.mySameSizeVertically) {
/* 421 */         heights = getSameSizes(verticalInfo, Math.max(size.height, minSize.height));
/*     */ 
/*     */       }
/* 424 */       else if (size.height < prefSize.height) {
/* 425 */         int[] heights = getMinSizes(verticalInfo);
/* 426 */         new_doIt(heights, 0, verticalInfo.getCellCount(), size.height, verticalInfo, true);
/*     */       }
/*     */       else {
/* 429 */         heights = getPrefSizes(verticalInfo);
/* 430 */         new_doIt(heights, 0, verticalInfo.getCellCount(), size.height, verticalInfo, false);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 435 */       int y = insets.top + this.myMargin.top;
/* 436 */       for (int i = 0; i < heights.length; i++) {
/* 437 */         this.myYs[i] = y;
/* 438 */         this.myHeights[i] = heights[i];
/* 439 */         y += heights[i];
/* 440 */         if (shouldAddGapAfterCell(verticalInfo, i)) {
/* 441 */           y += verticalInfo.getGap();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 446 */     if ((skipLayout & 0x2) == 0) {
/*     */       int[] widths;
/*     */       int[] widths;
/* 449 */       if (this.mySameSizeHorizontally) {
/* 450 */         widths = getSameSizes(horizontalInfo, Math.max(size.width, minSize.width));
/*     */ 
/*     */       }
/* 453 */       else if (size.width < prefSize.width) {
/* 454 */         int[] widths = getMinSizes(horizontalInfo);
/* 455 */         new_doIt(widths, 0, horizontalInfo.getCellCount(), size.width, horizontalInfo, true);
/*     */       }
/*     */       else {
/* 458 */         widths = getPrefSizes(horizontalInfo);
/* 459 */         new_doIt(widths, 0, horizontalInfo.getCellCount(), size.width, horizontalInfo, false);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 464 */       int x = insets.left + this.myMargin.left;
/* 465 */       for (int i = 0; i < widths.length; i++) {
/* 466 */         this.myXs[i] = x;
/* 467 */         this.myWidths[i] = widths[i];
/* 468 */         x += widths[i];
/* 469 */         if (shouldAddGapAfterCell(horizontalInfo, i)) {
/* 470 */           x += horizontalInfo.getGap();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 476 */     for (int i = 0; i < layoutState.getComponentCount(); i++) {
/* 477 */       GridConstraints c = layoutState.getConstraints(i);
/* 478 */       Component component = layoutState.getComponent(i);
/*     */       
/* 480 */       int column = horizontalInfo.getCell(i);
/* 481 */       int colSpan = horizontalInfo.getSpan(i);
/* 482 */       int row = verticalInfo.getCell(i);
/* 483 */       int rowSpan = verticalInfo.getSpan(i);
/*     */       
/* 485 */       int cellWidth = this.myXs[(column + colSpan - 1)] + this.myWidths[(column + colSpan - 1)] - this.myXs[column];
/* 486 */       int cellHeight = this.myYs[(row + rowSpan - 1)] + this.myHeights[(row + rowSpan - 1)] - this.myYs[row];
/*     */       
/* 488 */       Dimension componentSize = new Dimension(cellWidth, cellHeight);
/*     */       
/* 490 */       if ((c.getFill() & 0x1) == 0) {
/* 491 */         componentSize.width = Math.min(componentSize.width, horizontalInfo.getPreferredWidth(i));
/*     */       }
/*     */       
/* 494 */       if ((c.getFill() & 0x2) == 0) {
/* 495 */         componentSize.height = Math.min(componentSize.height, verticalInfo.getPreferredWidth(i));
/*     */       }
/*     */       
/* 498 */       Util.adjustSize(component, c, componentSize);
/*     */       
/* 500 */       int dx = 0;
/* 501 */       int dy = 0;
/*     */       
/* 503 */       if ((c.getAnchor() & 0x4) != 0) {
/* 504 */         dx = cellWidth - componentSize.width;
/*     */       }
/* 506 */       else if ((c.getAnchor() & 0x8) == 0) {
/* 507 */         dx = (cellWidth - componentSize.width) / 2;
/*     */       }
/*     */       
/* 510 */       if ((c.getAnchor() & 0x2) != 0) {
/* 511 */         dy = cellHeight - componentSize.height;
/*     */       }
/* 513 */       else if ((c.getAnchor() & 0x1) == 0) {
/* 514 */         dy = (cellHeight - componentSize.height) / 2;
/*     */       }
/*     */       
/* 517 */       int indent = 10 * c.getIndent();
/* 518 */       componentSize.width -= indent;
/* 519 */       dx += indent;
/*     */       
/* 521 */       component.setBounds(this.myXs[column] + dx, this.myYs[row] + dy, componentSize.width, componentSize.height);
/*     */     }
/*     */   }
/*     */   
/*     */   private int checkSetSizesFromParent(Container container, Insets insets) {
/* 526 */     int skipLayout = 0;
/*     */     
/* 528 */     GridLayoutManager parentGridLayout = null;
/* 529 */     GridConstraints parentGridConstraints = null;
/*     */     
/*     */ 
/* 532 */     Container parent = container.getParent();
/* 533 */     if (parent != null) {
/* 534 */       if ((parent.getLayout() instanceof GridLayoutManager)) {
/* 535 */         parentGridLayout = (GridLayoutManager)parent.getLayout();
/* 536 */         parentGridConstraints = parentGridLayout.getConstraintsForComponent(container);
/*     */       }
/*     */       else {
/* 539 */         Container parent2 = parent.getParent();
/* 540 */         if ((parent2 != null) && ((parent2.getLayout() instanceof GridLayoutManager))) {
/* 541 */           parentGridLayout = (GridLayoutManager)parent2.getLayout();
/* 542 */           parentGridConstraints = parentGridLayout.getConstraintsForComponent(parent);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 547 */     if ((parentGridLayout != null) && (parentGridConstraints.isUseParentLayout())) {
/* 548 */       if (this.myRowStretches.length == parentGridConstraints.getRowSpan()) {
/* 549 */         int row = parentGridConstraints.getRow();
/* 550 */         this.myYs[0] = (insets.top + this.myMargin.top);
/* 551 */         this.myHeights[0] = (parentGridLayout.myHeights[row] - this.myYs[0]);
/* 552 */         for (int i = 1; i < this.myRowStretches.length; i++) {
/* 553 */           this.myYs[i] = (parentGridLayout.myYs[(i + row)] - parentGridLayout.myYs[row]);
/* 554 */           this.myHeights[i] = parentGridLayout.myHeights[(i + row)];
/*     */         }
/* 556 */         this.myHeights[(this.myRowStretches.length - 1)] -= insets.bottom + this.myMargin.bottom;
/* 557 */         skipLayout |= 0x1;
/*     */       }
/* 559 */       if (this.myColumnStretches.length == parentGridConstraints.getColSpan()) {
/* 560 */         int col = parentGridConstraints.getColumn();
/* 561 */         this.myXs[0] = (insets.left + this.myMargin.left);
/* 562 */         this.myWidths[0] = (parentGridLayout.myWidths[col] - this.myXs[0]);
/* 563 */         for (int i = 1; i < this.myColumnStretches.length; i++) {
/* 564 */           this.myXs[i] = (parentGridLayout.myXs[(i + col)] - parentGridLayout.myXs[col]);
/* 565 */           this.myWidths[i] = parentGridLayout.myWidths[(i + col)];
/*     */         }
/* 567 */         this.myWidths[(this.myColumnStretches.length - 1)] -= insets.right + this.myMargin.right;
/* 568 */         skipLayout |= 0x2;
/*     */       }
/*     */     }
/* 571 */     return skipLayout;
/*     */   }
/*     */   
/*     */   public void invalidateLayout(Container container) {
/* 575 */     this.myLayoutState = null;
/* 576 */     this.myHorizontalInfo = null;
/* 577 */     this.myVerticalInfo = null;
/*     */   }
/*     */   
/*     */   void validateInfos(Container container) {
/* 581 */     if (this.myLayoutState == null)
/*     */     {
/* 583 */       this.myLayoutState = new LayoutState(this, getDesignTimeInsets(container) == 0);
/* 584 */       this.myHorizontalInfo = new HorizontalInfo(this.myLayoutState, getHGapImpl(container));
/* 585 */       this.myVerticalInfo = new VerticalInfo(this.myLayoutState, getVGapImpl(container));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int[] getXs()
/*     */   {
/* 593 */     return this.myXs;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int[] getWidths()
/*     */   {
/* 600 */     return this.myWidths;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int[] getYs()
/*     */   {
/* 607 */     return this.myYs;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int[] getHeights()
/*     */   {
/* 614 */     return this.myHeights;
/*     */   }
/*     */   
/*     */   public int[] getCoords(boolean isRow) {
/* 618 */     return isRow ? this.myYs : this.myXs;
/*     */   }
/*     */   
/*     */   public int[] getSizes(boolean isRow) {
/* 622 */     return isRow ? this.myHeights : this.myWidths;
/*     */   }
/*     */   
/*     */   private int[] getMinSizes(DimensionInfo info) {
/* 626 */     return getMinOrPrefSizes(info, true);
/*     */   }
/*     */   
/*     */   private int[] getPrefSizes(DimensionInfo info) {
/* 630 */     return getMinOrPrefSizes(info, false);
/*     */   }
/*     */   
/*     */   private int[] getMinOrPrefSizes(DimensionInfo info, boolean min) {
/* 634 */     int[] widths = new int[info.getCellCount()];
/* 635 */     for (int i = 0; i < widths.length; i++) {
/* 636 */       widths[i] = this.myMinCellSize;
/*     */     }
/*     */     
/*     */ 
/* 640 */     for (int i = info.getComponentCount() - 1; i >= 0; i--) {
/* 641 */       if (info.getSpan(i) == 1)
/*     */       {
/*     */ 
/*     */ 
/* 645 */         int size = min ? getMin2(info, i) : Math.max(info.getMinimumWidth(i), info.getPreferredWidth(i));
/* 646 */         int cell = info.getCell(i);
/* 647 */         int gap = countGap(info, cell, info.getSpan(i));
/* 648 */         size = Math.max(size - gap, 0);
/*     */         
/* 650 */         widths[cell] = Math.max(widths[cell], size);
/*     */       }
/*     */     }
/*     */     
/* 654 */     updateSizesFromChildren(info, min, widths);
/*     */     
/*     */ 
/*     */ 
/* 658 */     boolean[] toProcess = new boolean[info.getCellCount()];
/*     */     
/* 660 */     for (int i = info.getComponentCount() - 1; i >= 0; i--) {
/* 661 */       int size = min ? getMin2(info, i) : Math.max(info.getMinimumWidth(i), info.getPreferredWidth(i));
/*     */       
/* 663 */       int span = info.getSpan(i);
/* 664 */       int cell = info.getCell(i);
/*     */       
/* 666 */       int gap = countGap(info, cell, span);
/* 667 */       size = Math.max(size - gap, 0);
/*     */       
/* 669 */       Arrays.fill(toProcess, false);
/*     */       
/* 671 */       int curSize = 0;
/* 672 */       for (int j = 0; j < span; j++) {
/* 673 */         curSize += widths[(j + cell)];
/* 674 */         toProcess[(j + cell)] = true;
/*     */       }
/*     */       
/* 677 */       if (curSize < size)
/*     */       {
/*     */ 
/*     */ 
/* 681 */         boolean[] higherPriorityCells = new boolean[toProcess.length];
/* 682 */         getCellsWithHigherPriorities(info, toProcess, higherPriorityCells, false, widths);
/*     */         
/* 684 */         distribute(higherPriorityCells, info, size - curSize, widths);
/*     */       }
/*     */     }
/* 687 */     return widths;
/*     */   }
/*     */   
/*     */   private static void updateSizesFromChildren(DimensionInfo info, boolean min, int[] widths) {
/* 691 */     for (int i = info.getComponentCount() - 1; i >= 0; i--) {
/* 692 */       Component child = info.getComponent(i);
/* 693 */       GridConstraints c = info.getConstraints(i);
/* 694 */       if ((c.isUseParentLayout()) && ((child instanceof Container))) {
/* 695 */         Container container = (Container)child;
/* 696 */         if ((container.getLayout() instanceof GridLayoutManager)) {
/* 697 */           updateSizesFromChild(info, min, widths, container, i);
/*     */         }
/* 699 */         else if ((container.getComponentCount() == 1) && ((container.getComponent(0) instanceof Container)))
/*     */         {
/*     */ 
/* 702 */           Container childContainer = (Container)container.getComponent(0);
/* 703 */           if ((childContainer.getLayout() instanceof GridLayoutManager)) {
/* 704 */             updateSizesFromChild(info, min, widths, childContainer, i);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void updateSizesFromChild(DimensionInfo info, boolean min, int[] widths, Container container, int childIndex)
/*     */   {
/* 716 */     GridLayoutManager childLayout = (GridLayoutManager)container.getLayout();
/* 717 */     if (info.getSpan(childIndex) == info.getChildLayoutCellCount(childLayout)) {
/* 718 */       childLayout.validateInfos(container);
/* 719 */       DimensionInfo childInfo = (info instanceof HorizontalInfo) ? childLayout.myHorizontalInfo : childLayout.myVerticalInfo;
/*     */       
/*     */ 
/* 722 */       int[] sizes = childLayout.getMinOrPrefSizes(childInfo, min);
/* 723 */       int cell = info.getCell(childIndex);
/* 724 */       for (int j = 0; j < sizes.length; j++) {
/* 725 */         widths[(cell + j)] = Math.max(widths[(cell + j)], sizes[j]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static int getMin2(DimensionInfo info, int componentIndex) {
/*     */     int s;
/*     */     int s;
/* 733 */     if ((info.getSizePolicy(componentIndex) & 0x1) != 0) {
/* 734 */       s = info.getMinimumWidth(componentIndex);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 739 */       s = Math.max(info.getMinimumWidth(componentIndex), info.getPreferredWidth(componentIndex));
/*     */     }
/* 741 */     return s;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void new_doIt(int[] widths, int cell, int span, int minWidth, DimensionInfo info, boolean checkPrefs)
/*     */   {
/* 748 */     int toDistribute = minWidth;
/*     */     
/* 750 */     for (int i = cell; i < cell + span; i++) {
/* 751 */       toDistribute -= widths[i];
/*     */     }
/* 753 */     if (toDistribute <= 0) {
/* 754 */       return;
/*     */     }
/*     */     
/* 757 */     boolean[] allowedCells = new boolean[info.getCellCount()];
/* 758 */     for (int i = cell; i < cell + span; i++) {
/* 759 */       allowedCells[i] = true;
/*     */     }
/*     */     
/* 762 */     boolean[] higherPriorityCells = new boolean[info.getCellCount()];
/* 763 */     getCellsWithHigherPriorities(info, allowedCells, higherPriorityCells, checkPrefs, widths);
/*     */     
/* 765 */     distribute(higherPriorityCells, info, toDistribute, widths);
/*     */   }
/*     */   
/*     */   private static void distribute(boolean[] higherPriorityCells, DimensionInfo info, int toDistribute, int[] widths) {
/* 769 */     int stretches = 0;
/* 770 */     for (int i = 0; i < info.getCellCount(); i++) {
/* 771 */       if (higherPriorityCells[i] != 0) {
/* 772 */         stretches += info.getStretch(i);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 777 */     int toDistributeFrozen = toDistribute;
/* 778 */     for (int i = 0; i < info.getCellCount(); i++) {
/* 779 */       if (higherPriorityCells[i] != 0)
/*     */       {
/*     */ 
/*     */ 
/* 783 */         int addon = toDistributeFrozen * info.getStretch(i) / stretches;
/* 784 */         widths[i] += addon;
/*     */         
/* 786 */         toDistribute -= addon;
/*     */       }
/*     */     }
/*     */     
/* 790 */     if (toDistribute != 0) {
/* 791 */       for (int i = 0; i < info.getCellCount(); i++) {
/* 792 */         if (higherPriorityCells[i] != 0)
/*     */         {
/*     */ 
/*     */ 
/* 796 */           widths[i] += 1;
/* 797 */           toDistribute--;
/*     */           
/* 799 */           if (toDistribute == 0) {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 805 */     if (toDistribute != 0) {
/* 806 */       throw new IllegalStateException("toDistribute = " + toDistribute);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void getCellsWithHigherPriorities(DimensionInfo info, boolean[] allowedCells, boolean[] higherPriorityCells, boolean checkPrefs, int[] widths)
/*     */   {
/* 817 */     Arrays.fill(higherPriorityCells, false);
/*     */     
/* 819 */     int foundCells = 0;
/*     */     
/* 821 */     if (checkPrefs)
/*     */     {
/* 823 */       int[] prefs = getMinOrPrefSizes(info, false);
/* 824 */       for (int cell = 0; cell < allowedCells.length; cell++) {
/* 825 */         if (allowedCells[cell] != 0)
/*     */         {
/*     */ 
/* 828 */           if ((!isCellEmpty(info, cell)) && (prefs[cell] > widths[cell])) {
/* 829 */             higherPriorityCells[cell] = true;
/* 830 */             foundCells++;
/*     */           }
/*     */         }
/*     */       }
/* 834 */       if (foundCells > 0) {
/* 835 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 840 */     for (int cell = 0; cell < allowedCells.length; cell++) {
/* 841 */       if (allowedCells[cell] != 0)
/*     */       {
/*     */ 
/* 844 */         if ((info.getCellSizePolicy(cell) & 0x4) != 0) {
/* 845 */           higherPriorityCells[cell] = true;
/* 846 */           foundCells++;
/*     */         }
/*     */       }
/*     */     }
/* 850 */     if (foundCells > 0) {
/* 851 */       return;
/*     */     }
/*     */     
/*     */ 
/* 855 */     for (int cell = 0; cell < allowedCells.length; cell++) {
/* 856 */       if (allowedCells[cell] != 0)
/*     */       {
/*     */ 
/* 859 */         if ((info.getCellSizePolicy(cell) & 0x2) != 0) {
/* 860 */           higherPriorityCells[cell] = true;
/* 861 */           foundCells++;
/*     */         }
/*     */       }
/*     */     }
/* 865 */     if (foundCells > 0) {
/* 866 */       return;
/*     */     }
/*     */     
/*     */ 
/* 870 */     for (int cell = 0; cell < allowedCells.length; cell++) {
/* 871 */       if (allowedCells[cell] != 0)
/*     */       {
/*     */ 
/* 874 */         if (!isCellEmpty(info, cell)) {
/* 875 */           higherPriorityCells[cell] = true;
/* 876 */           foundCells++;
/*     */         }
/*     */       }
/*     */     }
/* 880 */     if (foundCells > 0) {
/* 881 */       return;
/*     */     }
/*     */     
/*     */ 
/* 885 */     for (int cell = 0; cell < allowedCells.length; cell++) {
/* 886 */       if (allowedCells[cell] != 0)
/*     */       {
/*     */ 
/* 889 */         higherPriorityCells[cell] = true; }
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isSameSizeHorizontally() {
/* 894 */     return this.mySameSizeHorizontally;
/*     */   }
/*     */   
/*     */   public boolean isSameSizeVertically() {
/* 898 */     return this.mySameSizeVertically;
/*     */   }
/*     */   
/*     */   public void setSameSizeHorizontally(boolean sameSizeHorizontally) {
/* 902 */     this.mySameSizeHorizontally = sameSizeHorizontally;
/*     */   }
/*     */   
/*     */   public void setSameSizeVertically(boolean sameSizeVertically) {
/* 906 */     this.mySameSizeVertically = sameSizeVertically;
/*     */   }
/*     */   
/*     */   public int[] getHorizontalGridLines() {
/* 910 */     int[] result = new int[this.myYs.length + 1];
/* 911 */     result[0] = this.myYs[0];
/* 912 */     for (int i = 0; i < this.myYs.length - 1; i++) {
/* 913 */       result[(i + 1)] = ((this.myYs[i] + this.myHeights[i] + this.myYs[(i + 1)]) / 2);
/*     */     }
/* 915 */     result[this.myYs.length] = (this.myYs[(this.myYs.length - 1)] + this.myHeights[(this.myYs.length - 1)]);
/* 916 */     return result;
/*     */   }
/*     */   
/*     */   public int[] getVerticalGridLines() {
/* 920 */     int[] result = new int[this.myXs.length + 1];
/* 921 */     result[0] = this.myXs[0];
/* 922 */     for (int i = 0; i < this.myXs.length - 1; i++) {
/* 923 */       result[(i + 1)] = ((this.myXs[i] + this.myWidths[i] + this.myXs[(i + 1)]) / 2);
/*     */     }
/* 925 */     result[this.myXs.length] = (this.myXs[(this.myXs.length - 1)] + this.myWidths[(this.myXs.length - 1)]);
/* 926 */     return result;
/*     */   }
/*     */   
/*     */   public int getCellCount(boolean isRow) {
/* 930 */     return isRow ? getRowCount() : getColumnCount();
/*     */   }
/*     */   
/*     */   public int getCellSizePolicy(boolean isRow, int cellIndex) {
/* 934 */     DimensionInfo info = isRow ? this.myVerticalInfo : this.myHorizontalInfo;
/* 935 */     if (info == null)
/*     */     {
/* 937 */       return 0;
/*     */     }
/* 939 */     return info.getCellSizePolicy(cellIndex);
/*     */   }
/*     */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/GridLayoutManager.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */