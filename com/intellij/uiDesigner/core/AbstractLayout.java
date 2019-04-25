/*     */ package com.intellij.uiDesigner.core;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Insets;
/*     */ import java.awt.LayoutManager2;
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
/*     */ public abstract class AbstractLayout
/*     */   implements LayoutManager2
/*     */ {
/*     */   public static final int DEFAULT_HGAP = 10;
/*     */   public static final int DEFAULT_VGAP = 5;
/*     */   protected Component[] myComponents;
/*     */   protected GridConstraints[] myConstraints;
/*     */   protected Insets myMargin;
/*     */   private int myHGap;
/*     */   private int myVGap;
/*  49 */   private static final Component[] COMPONENT_EMPTY_ARRAY = new Component[0];
/*     */   
/*     */   public AbstractLayout() {
/*  52 */     this.myComponents = COMPONENT_EMPTY_ARRAY;
/*  53 */     this.myConstraints = GridConstraints.EMPTY_ARRAY;
/*  54 */     this.myMargin = new Insets(0, 0, 0, 0);
/*  55 */     this.myHGap = -1;
/*  56 */     this.myVGap = -1;
/*     */   }
/*     */   
/*     */   public final Insets getMargin() {
/*  60 */     return (Insets)this.myMargin.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getHGap()
/*     */   {
/*  68 */     return this.myHGap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static int getHGapImpl(Container container)
/*     */   {
/*  77 */     if (container == null) {
/*  78 */       throw new IllegalArgumentException("container cannot be null");
/*     */     }
/*  80 */     while (container != null) {
/*  81 */       if ((container.getLayout() instanceof AbstractLayout)) {
/*  82 */         AbstractLayout layout = (AbstractLayout)container.getLayout();
/*  83 */         if (layout.getHGap() != -1) {
/*  84 */           return layout.getHGap();
/*     */         }
/*     */       }
/*  87 */       container = container.getParent();
/*     */     }
/*  89 */     return 10;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setHGap(int hGap)
/*     */   {
/* 100 */     if (hGap < -1) {
/* 101 */       throw new IllegalArgumentException("wrong hGap: " + hGap);
/*     */     }
/* 103 */     this.myHGap = hGap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getVGap()
/*     */   {
/* 111 */     return this.myVGap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected static int getVGapImpl(Container container)
/*     */   {
/* 120 */     if (container == null) {
/* 121 */       throw new IllegalArgumentException("container cannot be null");
/*     */     }
/* 123 */     while (container != null) {
/* 124 */       if ((container.getLayout() instanceof AbstractLayout)) {
/* 125 */         AbstractLayout layout = (AbstractLayout)container.getLayout();
/* 126 */         if (layout.getVGap() != -1) {
/* 127 */           return layout.getVGap();
/*     */         }
/*     */       }
/* 130 */       container = container.getParent();
/*     */     }
/* 132 */     return 5;
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
/*     */   public final void setVGap(int vGap)
/*     */   {
/* 145 */     if (vGap < -1) {
/* 146 */       throw new IllegalArgumentException("wrong vGap: " + vGap);
/*     */     }
/* 148 */     this.myVGap = vGap;
/*     */   }
/*     */   
/*     */   public final void setMargin(Insets margin) {
/* 152 */     if (margin == null) {
/* 153 */       throw new IllegalArgumentException("margin cannot be null");
/*     */     }
/* 155 */     this.myMargin = ((Insets)margin.clone());
/*     */   }
/*     */   
/*     */   final int getComponentCount() {
/* 159 */     return this.myComponents.length;
/*     */   }
/*     */   
/*     */   final Component getComponent(int index) {
/* 163 */     return this.myComponents[index];
/*     */   }
/*     */   
/*     */   final GridConstraints getConstraints(int index) {
/* 167 */     return this.myConstraints[index];
/*     */   }
/*     */   
/*     */   public void addLayoutComponent(Component comp, Object constraints) {
/* 171 */     if (!(constraints instanceof GridConstraints)) {
/* 172 */       throw new IllegalArgumentException("constraints: " + constraints);
/*     */     }
/*     */     
/* 175 */     Component[] newComponents = new Component[this.myComponents.length + 1];
/* 176 */     System.arraycopy(this.myComponents, 0, newComponents, 0, this.myComponents.length);
/* 177 */     newComponents[this.myComponents.length] = comp;
/* 178 */     this.myComponents = newComponents;
/*     */     
/* 180 */     GridConstraints[] newConstraints = new GridConstraints[this.myConstraints.length + 1];
/* 181 */     System.arraycopy(this.myConstraints, 0, newConstraints, 0, this.myConstraints.length);
/* 182 */     newConstraints[this.myConstraints.length] = ((GridConstraints)((GridConstraints)constraints).clone());
/* 183 */     this.myConstraints = newConstraints;
/*     */   }
/*     */   
/*     */   public final void addLayoutComponent(String name, Component comp) {
/* 187 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public final void removeLayoutComponent(Component comp) {
/* 191 */     int i = getComponentIndex(comp);
/* 192 */     if (i == -1) {
/* 193 */       throw new IllegalArgumentException("component was not added: " + comp);
/*     */     }
/*     */     
/* 196 */     if (this.myComponents.length == 1) {
/* 197 */       this.myComponents = COMPONENT_EMPTY_ARRAY;
/*     */     }
/*     */     else {
/* 200 */       Component[] newComponents = new Component[this.myComponents.length - 1];
/* 201 */       System.arraycopy(this.myComponents, 0, newComponents, 0, i);
/* 202 */       System.arraycopy(this.myComponents, i + 1, newComponents, i, this.myComponents.length - i - 1);
/* 203 */       this.myComponents = newComponents;
/*     */     }
/*     */     
/* 206 */     if (this.myConstraints.length == 1) {
/* 207 */       this.myConstraints = GridConstraints.EMPTY_ARRAY;
/*     */     }
/*     */     else {
/* 210 */       GridConstraints[] newConstraints = new GridConstraints[this.myConstraints.length - 1];
/* 211 */       System.arraycopy(this.myConstraints, 0, newConstraints, 0, i);
/* 212 */       System.arraycopy(this.myConstraints, i + 1, newConstraints, i, this.myConstraints.length - i - 1);
/* 213 */       this.myConstraints = newConstraints;
/*     */     }
/*     */   }
/*     */   
/*     */   public GridConstraints getConstraintsForComponent(Component comp) {
/* 218 */     int i = getComponentIndex(comp);
/* 219 */     if (i == -1) {
/* 220 */       throw new IllegalArgumentException("component was not added: " + comp);
/*     */     }
/*     */     
/* 223 */     return this.myConstraints[i];
/*     */   }
/*     */   
/*     */   private int getComponentIndex(Component comp) {
/* 227 */     for (int i = 0; i < this.myComponents.length; i++) {
/* 228 */       Component component = this.myComponents[i];
/* 229 */       if (component == comp) {
/* 230 */         return i;
/*     */       }
/*     */     }
/* 233 */     return -1;
/*     */   }
/*     */   
/*     */   public final float getLayoutAlignmentX(Container container) {
/* 237 */     return 0.5F;
/*     */   }
/*     */   
/*     */   public final float getLayoutAlignmentY(Container container) {
/* 241 */     return 0.5F;
/*     */   }
/*     */   
/*     */   public abstract Dimension maximumLayoutSize(Container paramContainer);
/*     */   
/*     */   public abstract void invalidateLayout(Container paramContainer);
/*     */   
/*     */   public abstract Dimension preferredLayoutSize(Container paramContainer);
/*     */   
/*     */   public abstract Dimension minimumLayoutSize(Container paramContainer);
/*     */   
/*     */   public abstract void layoutContainer(Container paramContainer);
/*     */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/com/intellij/uiDesigner/core/AbstractLayout.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       0.7.1
 */