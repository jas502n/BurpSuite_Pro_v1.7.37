/*      */ package java.math;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.StreamCorruptedException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ThreadLocalRandom;
/*      */ import sun.misc.Unsafe;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BigInteger
/*      */   extends Number
/*      */   implements Comparable<BigInteger>
/*      */ {
/*      */   final int signum;
/*      */   final int[] mag;
/*      */   @Deprecated
/*      */   private int bitCount;
/*      */   @Deprecated
/*      */   private int bitLength;
/*      */   @Deprecated
/*      */   private int lowestSetBit;
/*      */   @Deprecated
/*      */   private int firstNonzeroIntNum;
/*      */   static final long LONG_MASK = 4294967295L;
/*      */   private static final int MAX_MAG_LENGTH = 67108864;
/*      */   private static final int PRIME_SEARCH_BIT_LENGTH_LIMIT = 500000000;
/*      */   private static final int KARATSUBA_THRESHOLD = 80;
/*      */   private static final int TOOM_COOK_THRESHOLD = 240;
/*      */   private static final int KARATSUBA_SQUARE_THRESHOLD = 128;
/*      */   private static final int TOOM_COOK_SQUARE_THRESHOLD = 216;
/*      */   static final int BURNIKEL_ZIEGLER_THRESHOLD = 80;
/*      */   static final int BURNIKEL_ZIEGLER_OFFSET = 40;
/*      */   private static final int SCHOENHAGE_BASE_CONVERSION_THRESHOLD = 20;
/*      */   private static final int MULTIPLY_SQUARE_THRESHOLD = 20;
/*      */   private static final int MONTGOMERY_INTRINSIC_THRESHOLD = 512;
/*      */   private static long[] bitsPerDigit;
/*      */   private static final int SMALL_PRIME_THRESHOLD = 95;
/*      */   private static final int DEFAULT_PRIME_CERTAINTY = 100;
/*      */   private static final BigInteger SMALL_PRIME_PRODUCT;
/*      */   private static final int MAX_CONSTANT = 16;
/*      */   private static BigInteger[] posConst;
/*      */   private static BigInteger[] negConst;
/*      */   private static volatile BigInteger[][] powerCache;
/*      */   private static final double[] logCache;
/*      */   private static final double LOG_TWO;
/*      */   public static final BigInteger ZERO;
/*      */   public static final BigInteger ONE;
/*      */   private static final BigInteger TWO;
/*      */   private static final BigInteger NEGATIVE_ONE;
/*      */   public static final BigInteger TEN;
/*      */   static int[] bnExpModThreshTable;
/*      */   private static String[] zeros;
/*      */   
/*      */   public BigInteger(byte[] paramArrayOfByte)
/*      */   {
/*  301 */     if (paramArrayOfByte.length == 0) {
/*  302 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*  304 */     if (paramArrayOfByte[0] < 0) {
/*  305 */       this.mag = makePositive(paramArrayOfByte);
/*  306 */       this.signum = -1;
/*      */     } else {
/*  308 */       this.mag = stripLeadingZeroBytes(paramArrayOfByte);
/*  309 */       this.signum = (this.mag.length == 0 ? 0 : 1);
/*      */     }
/*  311 */     if (this.mag.length >= 67108864) {
/*  312 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger(int[] paramArrayOfInt)
/*      */   {
/*  323 */     if (paramArrayOfInt.length == 0) {
/*  324 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*  326 */     if (paramArrayOfInt[0] < 0) {
/*  327 */       this.mag = makePositive(paramArrayOfInt);
/*  328 */       this.signum = -1;
/*      */     } else {
/*  330 */       this.mag = trustedStripLeadingZeroInts(paramArrayOfInt);
/*  331 */       this.signum = (this.mag.length == 0 ? 0 : 1);
/*      */     }
/*  333 */     if (this.mag.length >= 67108864) {
/*  334 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/*  355 */     this.mag = stripLeadingZeroBytes(paramArrayOfByte);
/*      */     
/*  357 */     if ((paramInt < -1) || (paramInt > 1)) {
/*  358 */       throw new NumberFormatException("Invalid signum value");
/*      */     }
/*  360 */     if (this.mag.length == 0) {
/*  361 */       this.signum = 0;
/*      */     } else {
/*  363 */       if (paramInt == 0)
/*  364 */         throw new NumberFormatException("signum-magnitude mismatch");
/*  365 */       this.signum = paramInt;
/*      */     }
/*  367 */     if (this.mag.length >= 67108864) {
/*  368 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger(int paramInt, int[] paramArrayOfInt)
/*      */   {
/*  379 */     this.mag = stripLeadingZeroInts(paramArrayOfInt);
/*      */     
/*  381 */     if ((paramInt < -1) || (paramInt > 1)) {
/*  382 */       throw new NumberFormatException("Invalid signum value");
/*      */     }
/*  384 */     if (this.mag.length == 0) {
/*  385 */       this.signum = 0;
/*      */     } else {
/*  387 */       if (paramInt == 0)
/*  388 */         throw new NumberFormatException("signum-magnitude mismatch");
/*  389 */       this.signum = paramInt;
/*      */     }
/*  391 */     if (this.mag.length >= 67108864) {
/*  392 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger(String paramString, int paramInt)
/*      */   {
/*  414 */     int i = 0;
/*  415 */     int k = paramString.length();
/*      */     
/*  417 */     if ((paramInt < 2) || (paramInt > 36))
/*  418 */       throw new NumberFormatException("Radix out of range");
/*  419 */     if (k == 0) {
/*  420 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*      */     
/*  423 */     int m = 1;
/*  424 */     int n = paramString.lastIndexOf('-');
/*  425 */     int i1 = paramString.lastIndexOf('+');
/*  426 */     if (n >= 0) {
/*  427 */       if ((n != 0) || (i1 >= 0)) {
/*  428 */         throw new NumberFormatException("Illegal embedded sign character");
/*      */       }
/*  430 */       m = -1;
/*  431 */       i = 1;
/*  432 */     } else if (i1 >= 0) {
/*  433 */       if (i1 != 0) {
/*  434 */         throw new NumberFormatException("Illegal embedded sign character");
/*      */       }
/*  436 */       i = 1;
/*      */     }
/*  438 */     if (i == k) {
/*  439 */       throw new NumberFormatException("Zero length BigInteger");
/*      */     }
/*      */     
/*  442 */     while ((i < k) && 
/*  443 */       (Character.digit(paramString.charAt(i), paramInt) == 0)) {
/*  444 */       i++;
/*      */     }
/*      */     
/*  447 */     if (i == k) {
/*  448 */       this.signum = 0;
/*  449 */       this.mag = ZERO.mag;
/*  450 */       return;
/*      */     }
/*      */     
/*  453 */     int j = k - i;
/*  454 */     this.signum = m;
/*      */     
/*      */ 
/*      */ 
/*  458 */     long l = (j * bitsPerDigit[paramInt] >>> 10) + 1L;
/*  459 */     if (l + 31L >= 4294967296L) {
/*  460 */       reportOverflow();
/*      */     }
/*  462 */     int i2 = (int)(l + 31L) >>> 5;
/*  463 */     int[] arrayOfInt = new int[i2];
/*      */     
/*      */ 
/*  466 */     int i3 = j % digitsPerInt[paramInt];
/*  467 */     if (i3 == 0)
/*  468 */       i3 = digitsPerInt[paramInt];
/*  469 */     String str = paramString.substring(i, i += i3);
/*  470 */     arrayOfInt[(i2 - 1)] = Integer.parseInt(str, paramInt);
/*  471 */     if (arrayOfInt[(i2 - 1)] < 0) {
/*  472 */       throw new NumberFormatException("Illegal digit");
/*      */     }
/*      */     
/*  475 */     int i4 = intRadix[paramInt];
/*  476 */     int i5 = 0;
/*  477 */     while (i < k) {
/*  478 */       str = paramString.substring(i, i += digitsPerInt[paramInt]);
/*  479 */       i5 = Integer.parseInt(str, paramInt);
/*  480 */       if (i5 < 0)
/*  481 */         throw new NumberFormatException("Illegal digit");
/*  482 */       destructiveMulAdd(arrayOfInt, i4, i5);
/*      */     }
/*      */     
/*  485 */     this.mag = trustedStripLeadingZeroInts(arrayOfInt);
/*  486 */     if (this.mag.length >= 67108864) {
/*  487 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  496 */     int i = 0;
/*      */     
/*      */ 
/*  499 */     while ((i < paramInt2) && (Character.digit(paramArrayOfChar[i], 10) == 0)) {
/*  500 */       i++;
/*      */     }
/*  502 */     if (i == paramInt2) {
/*  503 */       this.signum = 0;
/*  504 */       this.mag = ZERO.mag;
/*  505 */       return;
/*      */     }
/*      */     
/*  508 */     int j = paramInt2 - i;
/*  509 */     this.signum = paramInt1;
/*      */     
/*      */     int k;
/*  512 */     if (paramInt2 < 10) {
/*  513 */       k = 1;
/*      */     } else {
/*  515 */       long l = (j * bitsPerDigit[10] >>> 10) + 1L;
/*  516 */       if (l + 31L >= 4294967296L) {
/*  517 */         reportOverflow();
/*      */       }
/*  519 */       k = (int)(l + 31L) >>> 5;
/*      */     }
/*  521 */     int[] arrayOfInt = new int[k];
/*      */     
/*      */ 
/*  524 */     int m = j % digitsPerInt[10];
/*  525 */     if (m == 0)
/*  526 */       m = digitsPerInt[10];
/*  527 */     arrayOfInt[(k - 1)] = parseInt(paramArrayOfChar, i, i += m);
/*      */     
/*      */ 
/*  530 */     while (i < paramInt2) {
/*  531 */       int n = parseInt(paramArrayOfChar, i, i += digitsPerInt[10]);
/*  532 */       destructiveMulAdd(arrayOfInt, intRadix[10], n);
/*      */     }
/*  534 */     this.mag = trustedStripLeadingZeroInts(arrayOfInt);
/*  535 */     if (this.mag.length >= 67108864) {
/*  536 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private int parseInt(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/*  544 */     int i = Character.digit(paramArrayOfChar[(paramInt1++)], 10);
/*  545 */     if (i == -1) {
/*  546 */       throw new NumberFormatException(new String(paramArrayOfChar));
/*      */     }
/*  548 */     for (int j = paramInt1; j < paramInt2; j++) {
/*  549 */       int k = Character.digit(paramArrayOfChar[j], 10);
/*  550 */       if (k == -1)
/*  551 */         throw new NumberFormatException(new String(paramArrayOfChar));
/*  552 */       i = 10 * i + k;
/*      */     }
/*      */     
/*  555 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void destructiveMulAdd(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/*  569 */     long l1 = paramInt1 & 0xFFFFFFFF;
/*  570 */     long l2 = paramInt2 & 0xFFFFFFFF;
/*  571 */     int i = paramArrayOfInt.length;
/*      */     
/*  573 */     long l3 = 0L;
/*  574 */     long l4 = 0L;
/*  575 */     for (int j = i - 1; j >= 0; j--) {
/*  576 */       l3 = l1 * (paramArrayOfInt[j] & 0xFFFFFFFF) + l4;
/*  577 */       paramArrayOfInt[j] = ((int)l3);
/*  578 */       l4 = l3 >>> 32;
/*      */     }
/*      */     
/*      */ 
/*  582 */     long l5 = (paramArrayOfInt[(i - 1)] & 0xFFFFFFFF) + l2;
/*  583 */     paramArrayOfInt[(i - 1)] = ((int)l5);
/*  584 */     l4 = l5 >>> 32;
/*  585 */     for (int k = i - 2; k >= 0; k--) {
/*  586 */       l5 = (paramArrayOfInt[k] & 0xFFFFFFFF) + l4;
/*  587 */       paramArrayOfInt[k] = ((int)l5);
/*  588 */       l4 = l5 >>> 32;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger(String paramString)
/*      */   {
/*  606 */     this(paramString, 10);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger(int paramInt, Random paramRandom)
/*      */   {
/*  623 */     this(1, randomBits(paramInt, paramRandom));
/*      */   }
/*      */   
/*      */   private static byte[] randomBits(int paramInt, Random paramRandom) {
/*  627 */     if (paramInt < 0)
/*  628 */       throw new IllegalArgumentException("numBits must be non-negative");
/*  629 */     int i = (int)((paramInt + 7L) / 8L);
/*  630 */     byte[] arrayOfByte = new byte[i];
/*      */     
/*      */ 
/*  633 */     if (i > 0) {
/*  634 */       paramRandom.nextBytes(arrayOfByte);
/*  635 */       int j = 8 * i - paramInt; int 
/*  636 */         tmp49_48 = 0; byte[] tmp49_47 = arrayOfByte;tmp49_47[tmp49_48] = ((byte)(tmp49_47[tmp49_48] & (1 << 8 - j) - 1));
/*      */     }
/*  638 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  663 */     if (paramInt1 < 2) {
/*  664 */       throw new ArithmeticException("bitLength < 2");
/*      */     }
/*      */     
/*  667 */     BigInteger localBigInteger = paramInt1 < 95 ? smallPrime(paramInt1, paramInt2, paramRandom) : largePrime(paramInt1, paramInt2, paramRandom);
/*  668 */     this.signum = 1;
/*  669 */     this.mag = localBigInteger.mag;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static BigInteger probablePrime(int paramInt, Random paramRandom)
/*      */   {
/*  694 */     if (paramInt < 2) {
/*  695 */       throw new ArithmeticException("bitLength < 2");
/*      */     }
/*  697 */     return paramInt < 95 ? 
/*  698 */       smallPrime(paramInt, 100, paramRandom) : 
/*  699 */       largePrime(paramInt, 100, paramRandom);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger smallPrime(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  710 */     int i = paramInt1 + 31 >>> 5;
/*  711 */     int[] arrayOfInt = new int[i];
/*  712 */     int j = 1 << (paramInt1 + 31 & 0x1F);
/*  713 */     int k = (j << 1) - 1;
/*      */     
/*      */     for (;;)
/*      */     {
/*  717 */       for (int m = 0; m < i; m++)
/*  718 */         arrayOfInt[m] = paramRandom.nextInt();
/*  719 */       arrayOfInt[0] = (arrayOfInt[0] & k | j);
/*  720 */       if (paramInt1 > 2) {
/*  721 */         arrayOfInt[(i - 1)] |= 0x1;
/*      */       }
/*  723 */       BigInteger localBigInteger = new BigInteger(arrayOfInt, 1);
/*      */       
/*      */ 
/*  726 */       if (paramInt1 > 6) {
/*  727 */         long l = localBigInteger.remainder(SMALL_PRIME_PRODUCT).longValue();
/*  728 */         if ((l % 3L == 0L) || (l % 5L == 0L) || (l % 7L == 0L) || (l % 11L == 0L) || (l % 13L == 0L) || (l % 17L == 0L) || (l % 19L == 0L) || (l % 23L == 0L) || (l % 29L == 0L) || (l % 31L == 0L) || (l % 37L == 0L) || (l % 41L == 0L)) {}
/*      */ 
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/*  735 */         if (paramInt1 < 4) {
/*  736 */           return localBigInteger;
/*      */         }
/*      */         
/*  739 */         if (localBigInteger.primeToCertainty(paramInt2, paramRandom)) {
/*  740 */           return localBigInteger;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger largePrime(int paramInt1, int paramInt2, Random paramRandom)
/*      */   {
/*  755 */     BigInteger localBigInteger1 = new BigInteger(paramInt1, paramRandom).setBit(paramInt1 - 1);
/*  756 */     localBigInteger1.mag[(localBigInteger1.mag.length - 1)] &= 0xFFFFFFFE;
/*      */     
/*      */ 
/*  759 */     int i = getPrimeSearchLen(paramInt1);
/*  760 */     BitSieve localBitSieve = new BitSieve(localBigInteger1, i);
/*  761 */     BigInteger localBigInteger2 = localBitSieve.retrieve(localBigInteger1, paramInt2, paramRandom);
/*      */     
/*  763 */     while ((localBigInteger2 == null) || (localBigInteger2.bitLength() != paramInt1)) {
/*  764 */       localBigInteger1 = localBigInteger1.add(valueOf(2 * i));
/*  765 */       if (localBigInteger1.bitLength() != paramInt1)
/*  766 */         localBigInteger1 = new BigInteger(paramInt1, paramRandom).setBit(paramInt1 - 1);
/*  767 */       localBigInteger1.mag[(localBigInteger1.mag.length - 1)] &= 0xFFFFFFFE;
/*  768 */       localBitSieve = new BitSieve(localBigInteger1, i);
/*  769 */       localBigInteger2 = localBitSieve.retrieve(localBigInteger1, paramInt2, paramRandom);
/*      */     }
/*  771 */     return localBigInteger2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger nextProbablePrime()
/*      */   {
/*  787 */     if (this.signum < 0) {
/*  788 */       throw new ArithmeticException("start < 0: " + this);
/*      */     }
/*      */     
/*  791 */     if ((this.signum == 0) || (equals(ONE))) {
/*  792 */       return TWO;
/*      */     }
/*  794 */     BigInteger localBigInteger1 = add(ONE);
/*      */     
/*      */ 
/*  797 */     if (localBigInteger1.bitLength() < 95)
/*      */     {
/*      */ 
/*  800 */       if (!localBigInteger1.testBit(0)) {
/*  801 */         localBigInteger1 = localBigInteger1.add(ONE);
/*      */       }
/*      */       for (;;)
/*      */       {
/*  805 */         if (localBigInteger1.bitLength() > 6) {
/*  806 */           long l = localBigInteger1.remainder(SMALL_PRIME_PRODUCT).longValue();
/*  807 */           if ((l % 3L == 0L) || (l % 5L == 0L) || (l % 7L == 0L) || (l % 11L == 0L) || (l % 13L == 0L) || (l % 17L == 0L) || (l % 19L == 0L) || (l % 23L == 0L) || (l % 29L == 0L) || (l % 31L == 0L) || (l % 37L == 0L) || (l % 41L == 0L))
/*      */           {
/*      */ 
/*  810 */             localBigInteger1 = localBigInteger1.add(TWO);
/*  811 */             continue;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  816 */         if (localBigInteger1.bitLength() < 4) {
/*  817 */           return localBigInteger1;
/*      */         }
/*      */         
/*  820 */         if (localBigInteger1.primeToCertainty(100, null)) {
/*  821 */           return localBigInteger1;
/*      */         }
/*  823 */         localBigInteger1 = localBigInteger1.add(TWO);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  828 */     if (localBigInteger1.testBit(0)) {
/*  829 */       localBigInteger1 = localBigInteger1.subtract(ONE);
/*      */     }
/*      */     
/*  832 */     int i = getPrimeSearchLen(localBigInteger1.bitLength());
/*      */     for (;;)
/*      */     {
/*  835 */       BitSieve localBitSieve = new BitSieve(localBigInteger1, i);
/*  836 */       BigInteger localBigInteger2 = localBitSieve.retrieve(localBigInteger1, 100, null);
/*      */       
/*  838 */       if (localBigInteger2 != null)
/*  839 */         return localBigInteger2;
/*  840 */       localBigInteger1 = localBigInteger1.add(valueOf(2 * i));
/*      */     }
/*      */   }
/*      */   
/*      */   private static int getPrimeSearchLen(int paramInt) {
/*  845 */     if (paramInt > 500000001) {
/*  846 */       throw new ArithmeticException("Prime search implementation restriction on bitLength");
/*      */     }
/*  848 */     return paramInt / 20 * 64;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean primeToCertainty(int paramInt, Random paramRandom)
/*      */   {
/*  866 */     int i = 0;
/*  867 */     int j = (Math.min(paramInt, 2147483646) + 1) / 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  872 */     int k = bitLength();
/*  873 */     if (k < 100) {
/*  874 */       i = 50;
/*  875 */       i = j < i ? j : i;
/*  876 */       return passesMillerRabin(i, paramRandom);
/*      */     }
/*      */     
/*  879 */     if (k < 256) {
/*  880 */       i = 27;
/*  881 */     } else if (k < 512) {
/*  882 */       i = 15;
/*  883 */     } else if (k < 768) {
/*  884 */       i = 8;
/*  885 */     } else if (k < 1024) {
/*  886 */       i = 4;
/*      */     } else {
/*  888 */       i = 2;
/*      */     }
/*  890 */     i = j < i ? j : i;
/*      */     
/*  892 */     return (passesMillerRabin(i, paramRandom)) && (passesLucasLehmer());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean passesLucasLehmer()
/*      */   {
/*  902 */     BigInteger localBigInteger1 = add(ONE);
/*      */     
/*      */ 
/*  905 */     int i = 5;
/*  906 */     while (jacobiSymbol(i, this) != -1)
/*      */     {
/*  908 */       i = i < 0 ? Math.abs(i) + 2 : -(i + 2);
/*      */     }
/*      */     
/*      */ 
/*  912 */     BigInteger localBigInteger2 = lucasLehmerSequence(i, localBigInteger1, this);
/*      */     
/*      */ 
/*  915 */     return localBigInteger2.mod(this).equals(ZERO);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int jacobiSymbol(int paramInt, BigInteger paramBigInteger)
/*      */   {
/*  923 */     if (paramInt == 0) {
/*  924 */       return 0;
/*      */     }
/*      */     
/*  927 */     int i = 1;
/*  928 */     int j = paramBigInteger.mag[(paramBigInteger.mag.length - 1)];
/*      */     
/*      */     int k;
/*  931 */     if (paramInt < 0) {
/*  932 */       paramInt = -paramInt;
/*  933 */       k = j & 0x7;
/*  934 */       if ((k == 3) || (k == 7)) {
/*  935 */         i = -i;
/*      */       }
/*      */     }
/*      */     
/*  939 */     while ((paramInt & 0x3) == 0)
/*  940 */       paramInt >>= 2;
/*  941 */     if ((paramInt & 0x1) == 0) {
/*  942 */       paramInt >>= 1;
/*  943 */       if (((j ^ j >> 1) & 0x2) != 0)
/*  944 */         i = -i;
/*      */     }
/*  946 */     if (paramInt == 1) {
/*  947 */       return i;
/*      */     }
/*  949 */     if ((paramInt & j & 0x2) != 0) {
/*  950 */       i = -i;
/*      */     }
/*  952 */     j = paramBigInteger.mod(valueOf(paramInt)).intValue();
/*      */     
/*      */ 
/*  955 */     while (j != 0) {
/*  956 */       while ((j & 0x3) == 0)
/*  957 */         j >>= 2;
/*  958 */       if ((j & 0x1) == 0) {
/*  959 */         j >>= 1;
/*  960 */         if (((paramInt ^ paramInt >> 1) & 0x2) != 0)
/*  961 */           i = -i;
/*      */       }
/*  963 */       if (j == 1) {
/*  964 */         return i;
/*      */       }
/*  966 */       assert (j < paramInt);
/*  967 */       k = j;j = paramInt;paramInt = k;
/*  968 */       if ((j & paramInt & 0x2) != 0) {
/*  969 */         i = -i;
/*      */       }
/*  971 */       j %= paramInt;
/*      */     }
/*  973 */     return 0;
/*      */   }
/*      */   
/*      */   private static BigInteger lucasLehmerSequence(int paramInt, BigInteger paramBigInteger1, BigInteger paramBigInteger2) {
/*  977 */     BigInteger localBigInteger1 = valueOf(paramInt);
/*  978 */     Object localObject1 = ONE;
/*  979 */     Object localObject2 = ONE;
/*      */     
/*  981 */     for (int i = paramBigInteger1.bitLength() - 2; i >= 0; i--) {
/*  982 */       BigInteger localBigInteger2 = ((BigInteger)localObject1).multiply((BigInteger)localObject2).mod(paramBigInteger2);
/*      */       
/*  984 */       BigInteger localBigInteger3 = ((BigInteger)localObject2).square().add(localBigInteger1.multiply(((BigInteger)localObject1).square())).mod(paramBigInteger2);
/*  985 */       if (localBigInteger3.testBit(0)) {
/*  986 */         localBigInteger3 = localBigInteger3.subtract(paramBigInteger2);
/*      */       }
/*  988 */       localBigInteger3 = localBigInteger3.shiftRight(1);
/*      */       
/*  990 */       localObject1 = localBigInteger2;localObject2 = localBigInteger3;
/*  991 */       if (paramBigInteger1.testBit(i)) {
/*  992 */         localBigInteger2 = ((BigInteger)localObject1).add((BigInteger)localObject2).mod(paramBigInteger2);
/*  993 */         if (localBigInteger2.testBit(0)) {
/*  994 */           localBigInteger2 = localBigInteger2.subtract(paramBigInteger2);
/*      */         }
/*  996 */         localBigInteger2 = localBigInteger2.shiftRight(1);
/*  997 */         localBigInteger3 = ((BigInteger)localObject2).add(localBigInteger1.multiply((BigInteger)localObject1)).mod(paramBigInteger2);
/*  998 */         if (localBigInteger3.testBit(0))
/*  999 */           localBigInteger3 = localBigInteger3.subtract(paramBigInteger2);
/* 1000 */         localBigInteger3 = localBigInteger3.shiftRight(1);
/*      */         
/* 1002 */         localObject1 = localBigInteger2;localObject2 = localBigInteger3;
/*      */       }
/*      */     }
/* 1005 */     return (BigInteger)localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean passesMillerRabin(int paramInt, Random paramRandom)
/*      */   {
/* 1019 */     BigInteger localBigInteger1 = subtract(ONE);
/* 1020 */     BigInteger localBigInteger2 = localBigInteger1;
/* 1021 */     int i = localBigInteger2.getLowestSetBit();
/* 1022 */     localBigInteger2 = localBigInteger2.shiftRight(i);
/*      */     
/*      */ 
/* 1025 */     if (paramRandom == null) {
/* 1026 */       paramRandom = ThreadLocalRandom.current();
/*      */     }
/* 1028 */     for (int j = 0; j < paramInt; j++)
/*      */     {
/*      */       BigInteger localBigInteger3;
/*      */       do {
/* 1032 */         localBigInteger3 = new BigInteger(bitLength(), paramRandom);
/* 1033 */       } while ((localBigInteger3.compareTo(ONE) <= 0) || (localBigInteger3.compareTo(this) >= 0));
/*      */       
/* 1035 */       int k = 0;
/* 1036 */       BigInteger localBigInteger4 = localBigInteger3.modPow(localBigInteger2, this);
/* 1037 */       while (((k != 0) || (!localBigInteger4.equals(ONE))) && (!localBigInteger4.equals(localBigInteger1))) {
/* 1038 */         if ((k <= 0) || (!localBigInteger4.equals(ONE))) { k++; if (k != i) {}
/* 1039 */         } else { return false; }
/* 1040 */         localBigInteger4 = localBigInteger4.modPow(TWO, this);
/*      */       }
/*      */     }
/* 1043 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 1052 */     this.signum = (paramArrayOfInt.length == 0 ? 0 : paramInt);
/* 1053 */     this.mag = paramArrayOfInt;
/* 1054 */     if (this.mag.length >= 67108864) {
/* 1055 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 1064 */     this.signum = (paramArrayOfByte.length == 0 ? 0 : paramInt);
/* 1065 */     this.mag = stripLeadingZeroBytes(paramArrayOfByte);
/* 1066 */     if (this.mag.length >= 67108864) {
/* 1067 */       checkRange();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void checkRange()
/*      */   {
/* 1078 */     if ((this.mag.length > 67108864) || ((this.mag.length == 67108864) && (this.mag[0] < 0))) {
/* 1079 */       reportOverflow();
/*      */     }
/*      */   }
/*      */   
/*      */   private static void reportOverflow() {
/* 1084 */     throw new ArithmeticException("BigInteger would overflow supported range");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static BigInteger valueOf(long paramLong)
/*      */   {
/* 1100 */     if (paramLong == 0L)
/* 1101 */       return ZERO;
/* 1102 */     if ((paramLong > 0L) && (paramLong <= 16L))
/* 1103 */       return posConst[((int)paramLong)];
/* 1104 */     if ((paramLong < 0L) && (paramLong >= -16L)) {
/* 1105 */       return negConst[((int)-paramLong)];
/*      */     }
/* 1107 */     return new BigInteger(paramLong);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private BigInteger(long paramLong)
/*      */   {
/* 1114 */     if (paramLong < 0L) {
/* 1115 */       paramLong = -paramLong;
/* 1116 */       this.signum = -1;
/*      */     } else {
/* 1118 */       this.signum = 1;
/*      */     }
/*      */     
/* 1121 */     int i = (int)(paramLong >>> 32);
/* 1122 */     if (i == 0) {
/* 1123 */       this.mag = new int[1];
/* 1124 */       this.mag[0] = ((int)paramLong);
/*      */     } else {
/* 1126 */       this.mag = new int[2];
/* 1127 */       this.mag[0] = i;
/* 1128 */       this.mag[1] = ((int)paramLong);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger valueOf(int[] paramArrayOfInt)
/*      */   {
/* 1138 */     return paramArrayOfInt[0] > 0 ? new BigInteger(paramArrayOfInt, 1) : new BigInteger(paramArrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger add(BigInteger paramBigInteger)
/*      */   {
/* 1225 */     if (paramBigInteger.signum == 0)
/* 1226 */       return this;
/* 1227 */     if (this.signum == 0)
/* 1228 */       return paramBigInteger;
/* 1229 */     if (paramBigInteger.signum == this.signum) {
/* 1230 */       return new BigInteger(add(this.mag, paramBigInteger.mag), this.signum);
/*      */     }
/* 1232 */     int i = compareMagnitude(paramBigInteger);
/* 1233 */     if (i == 0) {
/* 1234 */       return ZERO;
/*      */     }
/* 1236 */     int[] arrayOfInt = i > 0 ? subtract(this.mag, paramBigInteger.mag) : subtract(paramBigInteger.mag, this.mag);
/* 1237 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/*      */     
/* 1239 */     return new BigInteger(arrayOfInt, i == this.signum ? 1 : -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger add(long paramLong)
/*      */   {
/* 1247 */     if (paramLong == 0L)
/* 1248 */       return this;
/* 1249 */     if (this.signum == 0)
/* 1250 */       return valueOf(paramLong);
/* 1251 */     if (Long.signum(paramLong) == this.signum)
/* 1252 */       return new BigInteger(add(this.mag, Math.abs(paramLong)), this.signum);
/* 1253 */     int i = compareMagnitude(paramLong);
/* 1254 */     if (i == 0)
/* 1255 */       return ZERO;
/* 1256 */     int[] arrayOfInt = i > 0 ? subtract(this.mag, Math.abs(paramLong)) : subtract(Math.abs(paramLong), this.mag);
/* 1257 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1258 */     return new BigInteger(arrayOfInt, i == this.signum ? 1 : -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] add(int[] paramArrayOfInt, long paramLong)
/*      */   {
/* 1269 */     long l = 0L;
/* 1270 */     int i = paramArrayOfInt.length;
/*      */     
/* 1272 */     int j = (int)(paramLong >>> 32);
/* 1273 */     int[] arrayOfInt1; if (j == 0) {
/* 1274 */       arrayOfInt1 = new int[i];
/* 1275 */       l = (paramArrayOfInt[(--i)] & 0xFFFFFFFF) + paramLong;
/* 1276 */       arrayOfInt1[i] = ((int)l);
/*      */     } else {
/* 1278 */       if (i == 1) {
/* 1279 */         arrayOfInt1 = new int[2];
/* 1280 */         l = paramLong + (paramArrayOfInt[0] & 0xFFFFFFFF);
/* 1281 */         arrayOfInt1[1] = ((int)l);
/* 1282 */         arrayOfInt1[0] = ((int)(l >>> 32));
/* 1283 */         return arrayOfInt1;
/*      */       }
/* 1285 */       arrayOfInt1 = new int[i];
/* 1286 */       l = (paramArrayOfInt[(--i)] & 0xFFFFFFFF) + (paramLong & 0xFFFFFFFF);
/* 1287 */       arrayOfInt1[i] = ((int)l);
/* 1288 */       l = (paramArrayOfInt[(--i)] & 0xFFFFFFFF) + (j & 0xFFFFFFFF) + (l >>> 32);
/* 1289 */       arrayOfInt1[i] = ((int)l);
/*      */     }
/*      */     
/*      */ 
/* 1293 */     int k = l >>> 32 != 0L ? 1 : 0;
/* 1294 */     while ((i > 0) && (k != 0)) {
/* 1295 */       k = (arrayOfInt1[(--i)] = paramArrayOfInt[i] + 1) == 0 ? 1 : 0;
/*      */     }
/* 1297 */     while (i > 0) {
/* 1298 */       arrayOfInt1[(--i)] = paramArrayOfInt[i];
/*      */     }
/* 1300 */     if (k != 0) {
/* 1301 */       int[] arrayOfInt2 = new int[arrayOfInt1.length + 1];
/* 1302 */       System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 1, arrayOfInt1.length);
/* 1303 */       arrayOfInt2[0] = 1;
/* 1304 */       return arrayOfInt2;
/*      */     }
/* 1306 */     return arrayOfInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] add(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/* 1316 */     if (paramArrayOfInt1.length < paramArrayOfInt2.length) {
/* 1317 */       int[] arrayOfInt1 = paramArrayOfInt1;
/* 1318 */       paramArrayOfInt1 = paramArrayOfInt2;
/* 1319 */       paramArrayOfInt2 = arrayOfInt1;
/*      */     }
/*      */     
/* 1322 */     int i = paramArrayOfInt1.length;
/* 1323 */     int j = paramArrayOfInt2.length;
/* 1324 */     int[] arrayOfInt2 = new int[i];
/* 1325 */     long l = 0L;
/* 1326 */     if (j == 1) {
/* 1327 */       l = (paramArrayOfInt1[(--i)] & 0xFFFFFFFF) + (paramArrayOfInt2[0] & 0xFFFFFFFF);
/* 1328 */       arrayOfInt2[i] = ((int)l);
/*      */     }
/*      */     else {
/* 1331 */       while (j > 0) {
/* 1332 */         l = (paramArrayOfInt1[(--i)] & 0xFFFFFFFF) + (paramArrayOfInt2[(--j)] & 0xFFFFFFFF) + (l >>> 32);
/*      */         
/* 1334 */         arrayOfInt2[i] = ((int)l);
/*      */       }
/*      */     }
/*      */     
/* 1338 */     int k = l >>> 32 != 0L ? 1 : 0;
/* 1339 */     while ((i > 0) && (k != 0)) {
/* 1340 */       k = (arrayOfInt2[(--i)] = paramArrayOfInt1[i] + 1) == 0 ? 1 : 0;
/*      */     }
/*      */     
/* 1343 */     while (i > 0) {
/* 1344 */       arrayOfInt2[(--i)] = paramArrayOfInt1[i];
/*      */     }
/*      */     
/* 1347 */     if (k != 0) {
/* 1348 */       int[] arrayOfInt3 = new int[arrayOfInt2.length + 1];
/* 1349 */       System.arraycopy(arrayOfInt2, 0, arrayOfInt3, 1, arrayOfInt2.length);
/* 1350 */       arrayOfInt3[0] = 1;
/* 1351 */       return arrayOfInt3;
/*      */     }
/* 1353 */     return arrayOfInt2;
/*      */   }
/*      */   
/*      */   private static int[] subtract(long paramLong, int[] paramArrayOfInt) {
/* 1357 */     int i = (int)(paramLong >>> 32);
/* 1358 */     if (i == 0) {
/* 1359 */       arrayOfInt = new int[1];
/* 1360 */       arrayOfInt[0] = ((int)(paramLong - (paramArrayOfInt[0] & 0xFFFFFFFF)));
/* 1361 */       return arrayOfInt;
/*      */     }
/* 1363 */     int[] arrayOfInt = new int[2];
/* 1364 */     if (paramArrayOfInt.length == 1) {
/* 1365 */       l = ((int)paramLong & 0xFFFFFFFF) - (paramArrayOfInt[0] & 0xFFFFFFFF);
/* 1366 */       arrayOfInt[1] = ((int)l);
/*      */       
/* 1368 */       int j = l >> 32 != 0L ? 1 : 0;
/* 1369 */       if (j != 0) {
/* 1370 */         arrayOfInt[0] = (i - 1);
/*      */       } else {
/* 1372 */         arrayOfInt[0] = i;
/*      */       }
/* 1374 */       return arrayOfInt;
/*      */     }
/* 1376 */     long l = ((int)paramLong & 0xFFFFFFFF) - (paramArrayOfInt[1] & 0xFFFFFFFF);
/* 1377 */     arrayOfInt[1] = ((int)l);
/* 1378 */     l = (i & 0xFFFFFFFF) - (paramArrayOfInt[0] & 0xFFFFFFFF) + (l >> 32);
/* 1379 */     arrayOfInt[0] = ((int)l);
/* 1380 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] subtract(int[] paramArrayOfInt, long paramLong)
/*      */   {
/* 1393 */     int i = (int)(paramLong >>> 32);
/* 1394 */     int j = paramArrayOfInt.length;
/* 1395 */     int[] arrayOfInt = new int[j];
/* 1396 */     long l = 0L;
/*      */     
/* 1398 */     if (i == 0) {
/* 1399 */       l = (paramArrayOfInt[(--j)] & 0xFFFFFFFF) - paramLong;
/* 1400 */       arrayOfInt[j] = ((int)l);
/*      */     } else {
/* 1402 */       l = (paramArrayOfInt[(--j)] & 0xFFFFFFFF) - (paramLong & 0xFFFFFFFF);
/* 1403 */       arrayOfInt[j] = ((int)l);
/* 1404 */       l = (paramArrayOfInt[(--j)] & 0xFFFFFFFF) - (i & 0xFFFFFFFF) + (l >> 32);
/* 1405 */       arrayOfInt[j] = ((int)l);
/*      */     }
/*      */     
/*      */ 
/* 1409 */     int k = l >> 32 != 0L ? 1 : 0;
/* 1410 */     while ((j > 0) && (k != 0)) {
/* 1411 */       k = (arrayOfInt[(--j)] = paramArrayOfInt[j] - 1) == -1 ? 1 : 0;
/*      */     }
/*      */     
/* 1414 */     while (j > 0) {
/* 1415 */       arrayOfInt[(--j)] = paramArrayOfInt[j];
/*      */     }
/* 1417 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger subtract(BigInteger paramBigInteger)
/*      */   {
/* 1427 */     if (paramBigInteger.signum == 0)
/* 1428 */       return this;
/* 1429 */     if (this.signum == 0)
/* 1430 */       return paramBigInteger.negate();
/* 1431 */     if (paramBigInteger.signum != this.signum) {
/* 1432 */       return new BigInteger(add(this.mag, paramBigInteger.mag), this.signum);
/*      */     }
/* 1434 */     int i = compareMagnitude(paramBigInteger);
/* 1435 */     if (i == 0) {
/* 1436 */       return ZERO;
/*      */     }
/* 1438 */     int[] arrayOfInt = i > 0 ? subtract(this.mag, paramBigInteger.mag) : subtract(paramBigInteger.mag, this.mag);
/* 1439 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1440 */     return new BigInteger(arrayOfInt, i == this.signum ? 1 : -1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] subtract(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
/*      */   {
/* 1450 */     int i = paramArrayOfInt1.length;
/* 1451 */     int[] arrayOfInt = new int[i];
/* 1452 */     int j = paramArrayOfInt2.length;
/* 1453 */     long l = 0L;
/*      */     
/*      */ 
/* 1456 */     while (j > 0) {
/* 1457 */       l = (paramArrayOfInt1[(--i)] & 0xFFFFFFFF) - (paramArrayOfInt2[(--j)] & 0xFFFFFFFF) + (l >> 32);
/*      */       
/*      */ 
/* 1460 */       arrayOfInt[i] = ((int)l);
/*      */     }
/*      */     
/*      */ 
/* 1464 */     int k = l >> 32 != 0L ? 1 : 0;
/* 1465 */     while ((i > 0) && (k != 0)) {
/* 1466 */       k = (arrayOfInt[(--i)] = paramArrayOfInt1[i] - 1) == -1 ? 1 : 0;
/*      */     }
/*      */     
/* 1469 */     while (i > 0) {
/* 1470 */       arrayOfInt[(--i)] = paramArrayOfInt1[i];
/*      */     }
/* 1472 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger multiply(BigInteger paramBigInteger)
/*      */   {
/* 1485 */     if ((paramBigInteger.signum == 0) || (this.signum == 0)) {
/* 1486 */       return ZERO;
/*      */     }
/* 1488 */     int i = this.mag.length;
/*      */     
/* 1490 */     if ((paramBigInteger == this) && (i > 20)) {
/* 1491 */       return square();
/*      */     }
/*      */     
/* 1494 */     int j = paramBigInteger.mag.length;
/*      */     
/* 1496 */     if ((i < 80) || (j < 80)) {
/* 1497 */       int k = this.signum == paramBigInteger.signum ? 1 : -1;
/* 1498 */       if (paramBigInteger.mag.length == 1) {
/* 1499 */         return multiplyByInt(this.mag, paramBigInteger.mag[0], k);
/*      */       }
/* 1501 */       if (this.mag.length == 1) {
/* 1502 */         return multiplyByInt(paramBigInteger.mag, this.mag[0], k);
/*      */       }
/* 1504 */       int[] arrayOfInt = multiplyToLen(this.mag, i, paramBigInteger.mag, j, null);
/*      */       
/* 1506 */       arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1507 */       return new BigInteger(arrayOfInt, k);
/*      */     }
/* 1509 */     if ((i < 240) && (j < 240)) {
/* 1510 */       return multiplyKaratsuba(this, paramBigInteger);
/*      */     }
/* 1512 */     return multiplyToomCook3(this, paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */   private static BigInteger multiplyByInt(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 1518 */     if (Integer.bitCount(paramInt1) == 1) {
/* 1519 */       return new BigInteger(shiftLeft(paramArrayOfInt, Integer.numberOfTrailingZeros(paramInt1)), paramInt2);
/*      */     }
/* 1521 */     int i = paramArrayOfInt.length;
/* 1522 */     int[] arrayOfInt = new int[i + 1];
/* 1523 */     long l1 = 0L;
/* 1524 */     long l2 = paramInt1 & 0xFFFFFFFF;
/* 1525 */     int j = arrayOfInt.length - 1;
/* 1526 */     for (int k = i - 1; k >= 0; k--) {
/* 1527 */       long l3 = (paramArrayOfInt[k] & 0xFFFFFFFF) * l2 + l1;
/* 1528 */       arrayOfInt[(j--)] = ((int)l3);
/* 1529 */       l1 = l3 >>> 32;
/*      */     }
/* 1531 */     if (l1 == 0L) {
/* 1532 */       arrayOfInt = Arrays.copyOfRange(arrayOfInt, 1, arrayOfInt.length);
/*      */     } else {
/* 1534 */       arrayOfInt[j] = ((int)l1);
/*      */     }
/* 1536 */     return new BigInteger(arrayOfInt, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   BigInteger multiply(long paramLong)
/*      */   {
/* 1544 */     if ((paramLong == 0L) || (this.signum == 0))
/* 1545 */       return ZERO;
/* 1546 */     if (paramLong == Long.MIN_VALUE)
/* 1547 */       return multiply(valueOf(paramLong));
/* 1548 */     int i = paramLong > 0L ? this.signum : -this.signum;
/* 1549 */     if (paramLong < 0L)
/* 1550 */       paramLong = -paramLong;
/* 1551 */     long l1 = paramLong >>> 32;
/* 1552 */     long l2 = paramLong & 0xFFFFFFFF;
/*      */     
/* 1554 */     int j = this.mag.length;
/* 1555 */     int[] arrayOfInt1 = this.mag;
/* 1556 */     int[] arrayOfInt2 = l1 == 0L ? new int[j + 1] : new int[j + 2];
/* 1557 */     long l3 = 0L;
/* 1558 */     int k = arrayOfInt2.length - 1;
/* 1559 */     long l4; for (int m = j - 1; m >= 0; m--) {
/* 1560 */       l4 = (arrayOfInt1[m] & 0xFFFFFFFF) * l2 + l3;
/* 1561 */       arrayOfInt2[(k--)] = ((int)l4);
/* 1562 */       l3 = l4 >>> 32;
/*      */     }
/* 1564 */     arrayOfInt2[k] = ((int)l3);
/* 1565 */     if (l1 != 0L) {
/* 1566 */       l3 = 0L;
/* 1567 */       k = arrayOfInt2.length - 2;
/* 1568 */       for (m = j - 1; m >= 0; m--) {
/* 1569 */         l4 = (arrayOfInt1[m] & 0xFFFFFFFF) * l1 + (arrayOfInt2[k] & 0xFFFFFFFF) + l3;
/*      */         
/* 1571 */         arrayOfInt2[(k--)] = ((int)l4);
/* 1572 */         l3 = l4 >>> 32;
/*      */       }
/* 1574 */       arrayOfInt2[0] = ((int)l3);
/*      */     }
/* 1576 */     if (l3 == 0L)
/* 1577 */       arrayOfInt2 = Arrays.copyOfRange(arrayOfInt2, 1, arrayOfInt2.length);
/* 1578 */     return new BigInteger(arrayOfInt2, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] multiplyToLen(int[] paramArrayOfInt1, int paramInt1, int[] paramArrayOfInt2, int paramInt2, int[] paramArrayOfInt3)
/*      */   {
/* 1586 */     int i = paramInt1 - 1;
/* 1587 */     int j = paramInt2 - 1;
/*      */     
/* 1589 */     if ((paramArrayOfInt3 == null) || (paramArrayOfInt3.length < paramInt1 + paramInt2)) {
/* 1590 */       paramArrayOfInt3 = new int[paramInt1 + paramInt2];
/*      */     }
/* 1592 */     long l1 = 0L;
/* 1593 */     int k = j; for (int m = j + 1 + i; k >= 0; m--) {
/* 1594 */       long l2 = (paramArrayOfInt2[k] & 0xFFFFFFFF) * (paramArrayOfInt1[i] & 0xFFFFFFFF) + l1;
/*      */       
/* 1596 */       paramArrayOfInt3[m] = ((int)l2);
/* 1597 */       l1 = l2 >>> 32;k--;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1599 */     paramArrayOfInt3[i] = ((int)l1);
/*      */     
/* 1601 */     for (k = i - 1; k >= 0; k--) {
/* 1602 */       l1 = 0L;
/* 1603 */       m = j; for (int n = j + 1 + k; m >= 0; n--) {
/* 1604 */         long l3 = (paramArrayOfInt2[m] & 0xFFFFFFFF) * (paramArrayOfInt1[k] & 0xFFFFFFFF) + (paramArrayOfInt3[n] & 0xFFFFFFFF) + l1;
/*      */         
/*      */ 
/* 1607 */         paramArrayOfInt3[n] = ((int)l3);
/* 1608 */         l1 = l3 >>> 32;m--;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1610 */       paramArrayOfInt3[k] = ((int)l1);
/*      */     }
/* 1612 */     return paramArrayOfInt3;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger multiplyKaratsuba(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 1631 */     int i = paramBigInteger1.mag.length;
/* 1632 */     int j = paramBigInteger2.mag.length;
/*      */     
/*      */ 
/* 1635 */     int k = (Math.max(i, j) + 1) / 2;
/*      */     
/*      */ 
/*      */ 
/* 1639 */     BigInteger localBigInteger1 = paramBigInteger1.getLower(k);
/* 1640 */     BigInteger localBigInteger2 = paramBigInteger1.getUpper(k);
/* 1641 */     BigInteger localBigInteger3 = paramBigInteger2.getLower(k);
/* 1642 */     BigInteger localBigInteger4 = paramBigInteger2.getUpper(k);
/*      */     
/* 1644 */     BigInteger localBigInteger5 = localBigInteger2.multiply(localBigInteger4);
/* 1645 */     BigInteger localBigInteger6 = localBigInteger1.multiply(localBigInteger3);
/*      */     
/*      */ 
/* 1648 */     BigInteger localBigInteger7 = localBigInteger2.add(localBigInteger1).multiply(localBigInteger4.add(localBigInteger3));
/*      */     
/*      */ 
/* 1651 */     BigInteger localBigInteger8 = localBigInteger5.shiftLeft(32 * k).add(localBigInteger7.subtract(localBigInteger5).subtract(localBigInteger6)).shiftLeft(32 * k).add(localBigInteger6);
/*      */     
/* 1653 */     if (paramBigInteger1.signum != paramBigInteger2.signum) {
/* 1654 */       return localBigInteger8.negate();
/*      */     }
/* 1656 */     return localBigInteger8;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger multiplyToomCook3(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 1689 */     int i = paramBigInteger1.mag.length;
/* 1690 */     int j = paramBigInteger2.mag.length;
/*      */     
/* 1692 */     int k = Math.max(i, j);
/*      */     
/*      */ 
/* 1695 */     int m = (k + 2) / 3;
/*      */     
/*      */ 
/* 1698 */     int n = k - 2 * m;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1703 */     BigInteger localBigInteger3 = paramBigInteger1.getToomSlice(m, n, 0, k);
/* 1704 */     BigInteger localBigInteger2 = paramBigInteger1.getToomSlice(m, n, 1, k);
/* 1705 */     BigInteger localBigInteger1 = paramBigInteger1.getToomSlice(m, n, 2, k);
/* 1706 */     BigInteger localBigInteger6 = paramBigInteger2.getToomSlice(m, n, 0, k);
/* 1707 */     BigInteger localBigInteger5 = paramBigInteger2.getToomSlice(m, n, 1, k);
/* 1708 */     BigInteger localBigInteger4 = paramBigInteger2.getToomSlice(m, n, 2, k);
/*      */     
/*      */ 
/*      */ 
/* 1712 */     BigInteger localBigInteger7 = localBigInteger1.multiply(localBigInteger4);
/* 1713 */     BigInteger localBigInteger15 = localBigInteger3.add(localBigInteger1);
/* 1714 */     BigInteger localBigInteger16 = localBigInteger6.add(localBigInteger4);
/* 1715 */     BigInteger localBigInteger10 = localBigInteger15.subtract(localBigInteger2).multiply(localBigInteger16.subtract(localBigInteger5));
/* 1716 */     localBigInteger15 = localBigInteger15.add(localBigInteger2);
/* 1717 */     localBigInteger16 = localBigInteger16.add(localBigInteger5);
/* 1718 */     BigInteger localBigInteger8 = localBigInteger15.multiply(localBigInteger16);
/* 1719 */     BigInteger localBigInteger9 = localBigInteger15.add(localBigInteger3).shiftLeft(1).subtract(localBigInteger1).multiply(localBigInteger16
/* 1720 */       .add(localBigInteger6).shiftLeft(1).subtract(localBigInteger4));
/* 1721 */     BigInteger localBigInteger11 = localBigInteger3.multiply(localBigInteger6);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1729 */     BigInteger localBigInteger13 = localBigInteger9.subtract(localBigInteger10).exactDivideBy3();
/* 1730 */     BigInteger localBigInteger14 = localBigInteger8.subtract(localBigInteger10).shiftRight(1);
/* 1731 */     BigInteger localBigInteger12 = localBigInteger8.subtract(localBigInteger7);
/* 1732 */     localBigInteger13 = localBigInteger13.subtract(localBigInteger12).shiftRight(1);
/* 1733 */     localBigInteger12 = localBigInteger12.subtract(localBigInteger14).subtract(localBigInteger11);
/* 1734 */     localBigInteger13 = localBigInteger13.subtract(localBigInteger11.shiftLeft(1));
/* 1735 */     localBigInteger14 = localBigInteger14.subtract(localBigInteger13);
/*      */     
/*      */ 
/* 1738 */     int i1 = m * 32;
/*      */     
/* 1740 */     BigInteger localBigInteger17 = localBigInteger11.shiftLeft(i1).add(localBigInteger13).shiftLeft(i1).add(localBigInteger12).shiftLeft(i1).add(localBigInteger14).shiftLeft(i1).add(localBigInteger7);
/*      */     
/* 1742 */     if (paramBigInteger1.signum != paramBigInteger2.signum) {
/* 1743 */       return localBigInteger17.negate();
/*      */     }
/* 1745 */     return localBigInteger17;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger getToomSlice(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*      */   {
/* 1767 */     int m = this.mag.length;
/* 1768 */     int n = paramInt4 - m;
/*      */     int i;
/* 1770 */     int j; if (paramInt3 == 0) {
/* 1771 */       i = 0 - n;
/* 1772 */       j = paramInt2 - 1 - n;
/*      */     } else {
/* 1774 */       i = paramInt2 + (paramInt3 - 1) * paramInt1 - n;
/* 1775 */       j = i + paramInt1 - 1;
/*      */     }
/*      */     
/* 1778 */     if (i < 0) {
/* 1779 */       i = 0;
/*      */     }
/* 1781 */     if (j < 0) {
/* 1782 */       return ZERO;
/*      */     }
/*      */     
/* 1785 */     int k = j - i + 1;
/*      */     
/* 1787 */     if (k <= 0) {
/* 1788 */       return ZERO;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1793 */     if ((i == 0) && (k >= m)) {
/* 1794 */       return abs();
/*      */     }
/*      */     
/* 1797 */     int[] arrayOfInt = new int[k];
/* 1798 */     System.arraycopy(this.mag, i, arrayOfInt, 0, k);
/*      */     
/* 1800 */     return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger exactDivideBy3()
/*      */   {
/* 1812 */     int i = this.mag.length;
/* 1813 */     int[] arrayOfInt = new int[i];
/*      */     
/* 1815 */     long l4 = 0L;
/* 1816 */     for (int j = i - 1; j >= 0; j--) {
/* 1817 */       long l1 = this.mag[j] & 0xFFFFFFFF;
/* 1818 */       long l2 = l1 - l4;
/* 1819 */       if (l4 > l1) {
/* 1820 */         l4 = 1L;
/*      */       } else {
/* 1822 */         l4 = 0L;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1828 */       long l3 = l2 * 2863311531L & 0xFFFFFFFF;
/* 1829 */       arrayOfInt[j] = ((int)l3);
/*      */       
/*      */ 
/*      */ 
/* 1833 */       if (l3 >= 1431655766L) {
/* 1834 */         l4 += 1L;
/* 1835 */         if (l3 >= 2863311531L)
/* 1836 */           l4 += 1L;
/*      */       }
/*      */     }
/* 1839 */     arrayOfInt = trustedStripLeadingZeroInts(arrayOfInt);
/* 1840 */     return new BigInteger(arrayOfInt, this.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger getLower(int paramInt)
/*      */   {
/* 1848 */     int i = this.mag.length;
/*      */     
/* 1850 */     if (i <= paramInt) {
/* 1851 */       return abs();
/*      */     }
/*      */     
/* 1854 */     int[] arrayOfInt = new int[paramInt];
/* 1855 */     System.arraycopy(this.mag, i - paramInt, arrayOfInt, 0, paramInt);
/*      */     
/* 1857 */     return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger getUpper(int paramInt)
/*      */   {
/* 1866 */     int i = this.mag.length;
/*      */     
/* 1868 */     if (i <= paramInt) {
/* 1869 */       return ZERO;
/*      */     }
/*      */     
/* 1872 */     int j = i - paramInt;
/* 1873 */     int[] arrayOfInt = new int[j];
/* 1874 */     System.arraycopy(this.mag, 0, arrayOfInt, 0, j);
/*      */     
/* 1876 */     return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger square()
/*      */   {
/* 1887 */     if (this.signum == 0) {
/* 1888 */       return ZERO;
/*      */     }
/* 1890 */     int i = this.mag.length;
/*      */     
/* 1892 */     if (i < 128) {
/* 1893 */       int[] arrayOfInt = squareToLen(this.mag, i, null);
/* 1894 */       return new BigInteger(trustedStripLeadingZeroInts(arrayOfInt), 1);
/*      */     }
/* 1896 */     if (i < 216) {
/* 1897 */       return squareKaratsuba();
/*      */     }
/* 1899 */     return squareToomCook3();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int[] squareToLen(int[] paramArrayOfInt1, int paramInt, int[] paramArrayOfInt2)
/*      */   {
/* 1909 */     int i = paramInt << 1;
/* 1910 */     if ((paramArrayOfInt2 == null) || (paramArrayOfInt2.length < i)) {
/* 1911 */       paramArrayOfInt2 = new int[i];
/*      */     }
/*      */     
/* 1914 */     implSquareToLenChecks(paramArrayOfInt1, paramInt, paramArrayOfInt2, i);
/* 1915 */     return implSquareToLen(paramArrayOfInt1, paramInt, paramArrayOfInt2, i);
/*      */   }
/*      */   
/*      */ 
/*      */   private static void implSquareToLenChecks(int[] paramArrayOfInt1, int paramInt1, int[] paramArrayOfInt2, int paramInt2)
/*      */     throws RuntimeException
/*      */   {
/* 1922 */     if (paramInt1 < 1) {
/* 1923 */       throw new IllegalArgumentException("invalid input length: " + paramInt1);
/*      */     }
/* 1925 */     if (paramInt1 > paramArrayOfInt1.length) {
/* 1926 */       throw new IllegalArgumentException("input length out of bound: " + paramInt1 + " > " + paramArrayOfInt1.length);
/*      */     }
/*      */     
/* 1929 */     if (paramInt1 * 2 > paramArrayOfInt2.length) {
/* 1930 */       throw new IllegalArgumentException("input length out of bound: " + paramInt1 * 2 + " > " + paramArrayOfInt2.length);
/*      */     }
/*      */     
/* 1933 */     if (paramInt2 < 1) {
/* 1934 */       throw new IllegalArgumentException("invalid input length: " + paramInt2);
/*      */     }
/* 1936 */     if (paramInt2 > paramArrayOfInt2.length) {
/* 1937 */       throw new IllegalArgumentException("input length out of bound: " + paramInt1 + " > " + paramArrayOfInt2.length);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int[] implSquareToLen(int[] paramArrayOfInt1, int paramInt1, int[] paramArrayOfInt2, int paramInt2)
/*      */   {
/* 1982 */     int i = 0;
/* 1983 */     int j = 0; for (int k = 0; j < paramInt1; j++) {
/* 1984 */       long l1 = paramArrayOfInt1[j] & 0xFFFFFFFF;
/* 1985 */       long l2 = l1 * l1;
/* 1986 */       paramArrayOfInt2[(k++)] = (i << 31 | (int)(l2 >>> 33));
/* 1987 */       paramArrayOfInt2[(k++)] = ((int)(l2 >>> 1));
/* 1988 */       i = (int)l2;
/*      */     }
/*      */     
/*      */ 
/* 1992 */     j = paramInt1; for (k = 1; j > 0; k += 2) {
/* 1993 */       int m = paramArrayOfInt1[(j - 1)];
/* 1994 */       m = mulAdd(paramArrayOfInt2, paramArrayOfInt1, k, j - 1, m);
/* 1995 */       addOne(paramArrayOfInt2, k - 1, j, m);j--;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1999 */     primitiveLeftShift(paramArrayOfInt2, paramInt2, 1);
/* 2000 */     paramArrayOfInt2[(paramInt2 - 1)] |= paramArrayOfInt1[(paramInt1 - 1)] & 0x1;
/*      */     
/* 2002 */     return paramArrayOfInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger squareKaratsuba()
/*      */   {
/* 2013 */     int i = (this.mag.length + 1) / 2;
/*      */     
/* 2015 */     BigInteger localBigInteger1 = getLower(i);
/* 2016 */     BigInteger localBigInteger2 = getUpper(i);
/*      */     
/* 2018 */     BigInteger localBigInteger3 = localBigInteger2.square();
/* 2019 */     BigInteger localBigInteger4 = localBigInteger1.square();
/*      */     
/*      */ 
/* 2022 */     return localBigInteger3.shiftLeft(i * 32).add(localBigInteger1.add(localBigInteger2).square().subtract(localBigInteger3.add(localBigInteger4))).shiftLeft(i * 32).add(localBigInteger4);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger squareToomCook3()
/*      */   {
/* 2033 */     int i = this.mag.length;
/*      */     
/*      */ 
/* 2036 */     int j = (i + 2) / 3;
/*      */     
/*      */ 
/* 2039 */     int k = i - 2 * j;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2044 */     BigInteger localBigInteger3 = getToomSlice(j, k, 0, i);
/* 2045 */     BigInteger localBigInteger2 = getToomSlice(j, k, 1, i);
/* 2046 */     BigInteger localBigInteger1 = getToomSlice(j, k, 2, i);
/*      */     
/*      */ 
/* 2049 */     BigInteger localBigInteger4 = localBigInteger1.square();
/* 2050 */     BigInteger localBigInteger12 = localBigInteger3.add(localBigInteger1);
/* 2051 */     BigInteger localBigInteger7 = localBigInteger12.subtract(localBigInteger2).square();
/* 2052 */     localBigInteger12 = localBigInteger12.add(localBigInteger2);
/* 2053 */     BigInteger localBigInteger5 = localBigInteger12.square();
/* 2054 */     BigInteger localBigInteger8 = localBigInteger3.square();
/* 2055 */     BigInteger localBigInteger6 = localBigInteger12.add(localBigInteger3).shiftLeft(1).subtract(localBigInteger1).square();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2063 */     BigInteger localBigInteger10 = localBigInteger6.subtract(localBigInteger7).exactDivideBy3();
/* 2064 */     BigInteger localBigInteger11 = localBigInteger5.subtract(localBigInteger7).shiftRight(1);
/* 2065 */     BigInteger localBigInteger9 = localBigInteger5.subtract(localBigInteger4);
/* 2066 */     localBigInteger10 = localBigInteger10.subtract(localBigInteger9).shiftRight(1);
/* 2067 */     localBigInteger9 = localBigInteger9.subtract(localBigInteger11).subtract(localBigInteger8);
/* 2068 */     localBigInteger10 = localBigInteger10.subtract(localBigInteger8.shiftLeft(1));
/* 2069 */     localBigInteger11 = localBigInteger11.subtract(localBigInteger10);
/*      */     
/*      */ 
/* 2072 */     int m = j * 32;
/*      */     
/* 2074 */     return localBigInteger8.shiftLeft(m).add(localBigInteger10).shiftLeft(m).add(localBigInteger9).shiftLeft(m).add(localBigInteger11).shiftLeft(m).add(localBigInteger4);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger divide(BigInteger paramBigInteger)
/*      */   {
/* 2087 */     if ((paramBigInteger.mag.length < 80) || (this.mag.length - paramBigInteger.mag.length < 40))
/*      */     {
/* 2089 */       return divideKnuth(paramBigInteger);
/*      */     }
/* 2091 */     return divideBurnikelZiegler(paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger divideKnuth(BigInteger paramBigInteger)
/*      */   {
/* 2104 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2105 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this.mag);
/* 2106 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag);
/*      */     
/* 2108 */     localMutableBigInteger2.divideKnuth(localMutableBigInteger3, localMutableBigInteger1, false);
/* 2109 */     return localMutableBigInteger1.toBigInteger(this.signum * paramBigInteger.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger[] divideAndRemainder(BigInteger paramBigInteger)
/*      */   {
/* 2124 */     if ((paramBigInteger.mag.length < 80) || (this.mag.length - paramBigInteger.mag.length < 40))
/*      */     {
/* 2126 */       return divideAndRemainderKnuth(paramBigInteger);
/*      */     }
/* 2128 */     return divideAndRemainderBurnikelZiegler(paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */   private BigInteger[] divideAndRemainderKnuth(BigInteger paramBigInteger)
/*      */   {
/* 2134 */     BigInteger[] arrayOfBigInteger = new BigInteger[2];
/* 2135 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2136 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this.mag);
/* 2137 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag);
/* 2138 */     MutableBigInteger localMutableBigInteger4 = localMutableBigInteger2.divideKnuth(localMutableBigInteger3, localMutableBigInteger1);
/* 2139 */     arrayOfBigInteger[0] = localMutableBigInteger1.toBigInteger(this.signum == paramBigInteger.signum ? 1 : -1);
/* 2140 */     arrayOfBigInteger[1] = localMutableBigInteger4.toBigInteger(this.signum);
/* 2141 */     return arrayOfBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger remainder(BigInteger paramBigInteger)
/*      */   {
/* 2153 */     if ((paramBigInteger.mag.length < 80) || (this.mag.length - paramBigInteger.mag.length < 40))
/*      */     {
/* 2155 */       return remainderKnuth(paramBigInteger);
/*      */     }
/* 2157 */     return remainderBurnikelZiegler(paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */   private BigInteger remainderKnuth(BigInteger paramBigInteger)
/*      */   {
/* 2163 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2164 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this.mag);
/* 2165 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(paramBigInteger.mag);
/*      */     
/* 2167 */     return localMutableBigInteger2.divideKnuth(localMutableBigInteger3, localMutableBigInteger1).toBigInteger(this.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger divideBurnikelZiegler(BigInteger paramBigInteger)
/*      */   {
/* 2176 */     return divideAndRemainderBurnikelZiegler(paramBigInteger)[0];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger remainderBurnikelZiegler(BigInteger paramBigInteger)
/*      */   {
/* 2185 */     return divideAndRemainderBurnikelZiegler(paramBigInteger)[1];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger[] divideAndRemainderBurnikelZiegler(BigInteger paramBigInteger)
/*      */   {
/* 2195 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2196 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(this).divideAndRemainderBurnikelZiegler(new MutableBigInteger(paramBigInteger), localMutableBigInteger1);
/* 2197 */     BigInteger localBigInteger1 = localMutableBigInteger1.isZero() ? ZERO : localMutableBigInteger1.toBigInteger(this.signum * paramBigInteger.signum);
/* 2198 */     BigInteger localBigInteger2 = localMutableBigInteger2.isZero() ? ZERO : localMutableBigInteger2.toBigInteger(this.signum);
/* 2199 */     return new BigInteger[] { localBigInteger1, localBigInteger2 };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger pow(int paramInt)
/*      */   {
/* 2212 */     if (paramInt < 0) {
/* 2213 */       throw new ArithmeticException("Negative exponent");
/*      */     }
/* 2215 */     if (this.signum == 0) {
/* 2216 */       return paramInt == 0 ? ONE : this;
/*      */     }
/*      */     
/* 2219 */     BigInteger localBigInteger1 = abs();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2225 */     int i = localBigInteger1.getLowestSetBit();
/* 2226 */     long l1 = i * paramInt;
/* 2227 */     if (l1 > 2147483647L) {
/* 2228 */       reportOverflow();
/*      */     }
/*      */     
/*      */ 
/*      */     int j;
/*      */     
/* 2234 */     if (i > 0) {
/* 2235 */       localBigInteger1 = localBigInteger1.shiftRight(i);
/* 2236 */       j = localBigInteger1.bitLength();
/* 2237 */       if (j == 1) {
/* 2238 */         if ((this.signum < 0) && ((paramInt & 0x1) == 1)) {
/* 2239 */           return NEGATIVE_ONE.shiftLeft(i * paramInt);
/*      */         }
/* 2241 */         return ONE.shiftLeft(i * paramInt);
/*      */       }
/*      */     }
/*      */     else {
/* 2245 */       j = localBigInteger1.bitLength();
/* 2246 */       if (j == 1) {
/* 2247 */         if ((this.signum < 0) && ((paramInt & 0x1) == 1)) {
/* 2248 */           return NEGATIVE_ONE;
/*      */         }
/* 2250 */         return ONE;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2258 */     long l2 = j * paramInt;
/*      */     
/*      */ 
/*      */ 
/* 2262 */     if ((localBigInteger1.mag.length == 1) && (l2 <= 62L))
/*      */     {
/* 2264 */       int k = (this.signum < 0) && ((paramInt & 0x1) == 1) ? -1 : 1;
/* 2265 */       long l3 = 1L;
/* 2266 */       long l4 = localBigInteger1.mag[0] & 0xFFFFFFFF;
/*      */       
/* 2268 */       int n = paramInt;
/*      */       
/*      */ 
/* 2271 */       while (n != 0) {
/* 2272 */         if ((n & 0x1) == 1) {
/* 2273 */           l3 *= l4;
/*      */         }
/*      */         
/* 2276 */         if (n >>>= 1 != 0) {
/* 2277 */           l4 *= l4;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2282 */       if (i > 0) {
/* 2283 */         if (l1 + l2 <= 62L) {
/* 2284 */           return valueOf((l3 << (int)l1) * k);
/*      */         }
/* 2286 */         return valueOf(l3 * k).shiftLeft((int)l1);
/*      */       }
/*      */       
/*      */ 
/* 2290 */       return valueOf(l3 * k);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 2296 */     BigInteger localBigInteger2 = ONE;
/*      */     
/* 2298 */     int m = paramInt;
/*      */     
/* 2300 */     while (m != 0) {
/* 2301 */       if ((m & 0x1) == 1) {
/* 2302 */         localBigInteger2 = localBigInteger2.multiply(localBigInteger1);
/*      */       }
/*      */       
/* 2305 */       if (m >>>= 1 != 0) {
/* 2306 */         localBigInteger1 = localBigInteger1.square();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2311 */     if (i > 0) {
/* 2312 */       localBigInteger2 = localBigInteger2.shiftLeft(i * paramInt);
/*      */     }
/*      */     
/* 2315 */     if ((this.signum < 0) && ((paramInt & 0x1) == 1)) {
/* 2316 */       return localBigInteger2.negate();
/*      */     }
/* 2318 */     return localBigInteger2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger gcd(BigInteger paramBigInteger)
/*      */   {
/* 2332 */     if (paramBigInteger.signum == 0)
/* 2333 */       return abs();
/* 2334 */     if (this.signum == 0) {
/* 2335 */       return paramBigInteger.abs();
/*      */     }
/* 2337 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(this);
/* 2338 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(paramBigInteger);
/*      */     
/* 2340 */     MutableBigInteger localMutableBigInteger3 = localMutableBigInteger1.hybridGCD(localMutableBigInteger2);
/*      */     
/* 2342 */     return localMutableBigInteger3.toBigInteger(1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static int bitLengthForInt(int paramInt)
/*      */   {
/* 2349 */     return 32 - Integer.numberOfLeadingZeros(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] leftShift(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2357 */     int i = paramInt2 >>> 5;
/* 2358 */     int j = paramInt2 & 0x1F;
/* 2359 */     int k = bitLengthForInt(paramArrayOfInt[0]);
/*      */     
/*      */ 
/* 2362 */     if (paramInt2 <= 32 - k) {
/* 2363 */       primitiveLeftShift(paramArrayOfInt, paramInt1, j);
/* 2364 */       return paramArrayOfInt;
/*      */     }
/* 2366 */     if (j <= 32 - k) {
/* 2367 */       arrayOfInt = new int[i + paramInt1];
/* 2368 */       System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramInt1);
/* 2369 */       primitiveLeftShift(arrayOfInt, arrayOfInt.length, j);
/* 2370 */       return arrayOfInt;
/*      */     }
/* 2372 */     int[] arrayOfInt = new int[i + paramInt1 + 1];
/* 2373 */     System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, paramInt1);
/* 2374 */     primitiveRightShift(arrayOfInt, arrayOfInt.length, 32 - j);
/* 2375 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static void primitiveRightShift(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2382 */     int i = 32 - paramInt2;
/* 2383 */     int j = paramInt1 - 1; for (int k = paramArrayOfInt[j]; j > 0; j--) {
/* 2384 */       int m = k;
/* 2385 */       k = paramArrayOfInt[(j - 1)];
/* 2386 */       paramArrayOfInt[j] = (k << i | m >>> paramInt2);
/*      */     }
/* 2388 */     paramArrayOfInt[0] >>>= paramInt2;
/*      */   }
/*      */   
/*      */   static void primitiveLeftShift(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*      */   {
/* 2393 */     if ((paramInt1 == 0) || (paramInt2 == 0)) {
/* 2394 */       return;
/*      */     }
/* 2396 */     int i = 32 - paramInt2;
/* 2397 */     int j = 0;int k = paramArrayOfInt[j]; for (int m = j + paramInt1 - 1; j < m; j++) {
/* 2398 */       int n = k;
/* 2399 */       k = paramArrayOfInt[(j + 1)];
/* 2400 */       paramArrayOfInt[j] = (n << paramInt2 | k >>> i);
/*      */     }
/* 2402 */     paramArrayOfInt[(paramInt1 - 1)] <<= paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int bitLength(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 2410 */     if (paramInt == 0)
/* 2411 */       return 0;
/* 2412 */     return (paramInt - 1 << 5) + bitLengthForInt(paramArrayOfInt[0]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger abs()
/*      */   {
/* 2422 */     return this.signum >= 0 ? this : negate();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger negate()
/*      */   {
/* 2431 */     return new BigInteger(this.mag, -this.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int signum()
/*      */   {
/* 2441 */     return this.signum;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger mod(BigInteger paramBigInteger)
/*      */   {
/* 2457 */     if (paramBigInteger.signum <= 0) {
/* 2458 */       throw new ArithmeticException("BigInteger: modulus not positive");
/*      */     }
/* 2460 */     BigInteger localBigInteger = remainder(paramBigInteger);
/* 2461 */     return localBigInteger.signum >= 0 ? localBigInteger : localBigInteger.add(paramBigInteger);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger modPow(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 2478 */     if (paramBigInteger2.signum <= 0) {
/* 2479 */       throw new ArithmeticException("BigInteger: modulus not positive");
/*      */     }
/*      */     
/* 2482 */     if (paramBigInteger1.signum == 0) {
/* 2483 */       return paramBigInteger2.equals(ONE) ? ZERO : ONE;
/*      */     }
/* 2485 */     if (equals(ONE)) {
/* 2486 */       return paramBigInteger2.equals(ONE) ? ZERO : ONE;
/*      */     }
/* 2488 */     if ((equals(ZERO)) && (paramBigInteger1.signum >= 0)) {
/* 2489 */       return ZERO;
/*      */     }
/* 2491 */     if ((equals(negConst[1])) && (!paramBigInteger1.testBit(0))) {
/* 2492 */       return paramBigInteger2.equals(ONE) ? ZERO : ONE;
/*      */     }
/*      */     int i;
/* 2495 */     if ((i = paramBigInteger1.signum < 0 ? 1 : 0) != 0) {
/* 2496 */       paramBigInteger1 = paramBigInteger1.negate();
/*      */     }
/*      */     
/* 2499 */     BigInteger localBigInteger1 = (this.signum < 0) || (compareTo(paramBigInteger2) >= 0) ? mod(paramBigInteger2) : this;
/*      */     BigInteger localBigInteger2;
/* 2501 */     if (paramBigInteger2.testBit(0)) {
/* 2502 */       localBigInteger2 = localBigInteger1.oddModPow(paramBigInteger1, paramBigInteger2);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/* 2511 */       int j = paramBigInteger2.getLowestSetBit();
/*      */       
/* 2513 */       BigInteger localBigInteger3 = paramBigInteger2.shiftRight(j);
/* 2514 */       BigInteger localBigInteger4 = ONE.shiftLeft(j);
/*      */       
/*      */ 
/*      */ 
/* 2518 */       BigInteger localBigInteger5 = (this.signum < 0) || (compareTo(localBigInteger3) >= 0) ? mod(localBigInteger3) : this;
/*      */       
/*      */ 
/*      */ 
/* 2522 */       BigInteger localBigInteger6 = localBigInteger3.equals(ONE) ? ZERO : localBigInteger5.oddModPow(paramBigInteger1, localBigInteger3);
/*      */       
/*      */ 
/* 2525 */       BigInteger localBigInteger7 = localBigInteger1.modPow2(paramBigInteger1, j);
/*      */       
/*      */ 
/* 2528 */       BigInteger localBigInteger8 = localBigInteger4.modInverse(localBigInteger3);
/* 2529 */       BigInteger localBigInteger9 = localBigInteger3.modInverse(localBigInteger4);
/*      */       
/* 2531 */       if (paramBigInteger2.mag.length < 33554432) {
/* 2532 */         localBigInteger2 = localBigInteger6.multiply(localBigInteger4).multiply(localBigInteger8).add(localBigInteger7.multiply(localBigInteger3).multiply(localBigInteger9)).mod(paramBigInteger2);
/*      */       } else {
/* 2534 */         MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2535 */         new MutableBigInteger(localBigInteger6.multiply(localBigInteger4)).multiply(new MutableBigInteger(localBigInteger8), localMutableBigInteger1);
/* 2536 */         MutableBigInteger localMutableBigInteger2 = new MutableBigInteger();
/* 2537 */         new MutableBigInteger(localBigInteger7.multiply(localBigInteger3)).multiply(new MutableBigInteger(localBigInteger9), localMutableBigInteger2);
/* 2538 */         localMutableBigInteger1.add(localMutableBigInteger2);
/* 2539 */         MutableBigInteger localMutableBigInteger3 = new MutableBigInteger();
/* 2540 */         localBigInteger2 = localMutableBigInteger1.divide(new MutableBigInteger(paramBigInteger2), localMutableBigInteger3).toBigInteger();
/*      */       }
/*      */     }
/*      */     
/* 2544 */     return i != 0 ? localBigInteger2.modInverse(paramBigInteger2) : localBigInteger2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] montgomeryMultiply(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt, long paramLong, int[] paramArrayOfInt4)
/*      */   {
/* 2554 */     implMontgomeryMultiplyChecks(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramInt, paramArrayOfInt4);
/* 2555 */     if (paramInt > 512)
/*      */     {
/* 2557 */       paramArrayOfInt4 = multiplyToLen(paramArrayOfInt1, paramInt, paramArrayOfInt2, paramInt, paramArrayOfInt4);
/* 2558 */       return montReduce(paramArrayOfInt4, paramArrayOfInt3, paramInt, (int)paramLong);
/*      */     }
/* 2560 */     return implMontgomeryMultiply(paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, paramInt, paramLong, materialize(paramArrayOfInt4, paramInt));
/*      */   }
/*      */   
/*      */   private static int[] montgomerySquare(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt, long paramLong, int[] paramArrayOfInt3)
/*      */   {
/* 2565 */     implMontgomeryMultiplyChecks(paramArrayOfInt1, paramArrayOfInt1, paramArrayOfInt2, paramInt, paramArrayOfInt3);
/* 2566 */     if (paramInt > 512)
/*      */     {
/* 2568 */       paramArrayOfInt3 = squareToLen(paramArrayOfInt1, paramInt, paramArrayOfInt3);
/* 2569 */       return montReduce(paramArrayOfInt3, paramArrayOfInt2, paramInt, (int)paramLong);
/*      */     }
/* 2571 */     return implMontgomerySquare(paramArrayOfInt1, paramArrayOfInt2, paramInt, paramLong, materialize(paramArrayOfInt3, paramInt));
/*      */   }
/*      */   
/*      */ 
/*      */   private static void implMontgomeryMultiplyChecks(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt, int[] paramArrayOfInt4)
/*      */     throws RuntimeException
/*      */   {
/* 2578 */     if (paramInt % 2 != 0) {
/* 2579 */       throw new IllegalArgumentException("input array length must be even: " + paramInt);
/*      */     }
/*      */     
/* 2582 */     if (paramInt < 1) {
/* 2583 */       throw new IllegalArgumentException("invalid input length: " + paramInt);
/*      */     }
/*      */     
/* 2586 */     if ((paramInt > paramArrayOfInt1.length) || (paramInt > paramArrayOfInt2.length) || (paramInt > paramArrayOfInt3.length) || ((paramArrayOfInt4 != null) && (paramInt > paramArrayOfInt4.length)))
/*      */     {
/*      */ 
/*      */ 
/* 2590 */       throw new IllegalArgumentException("input array length out of bound: " + paramInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int[] materialize(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 2598 */     if ((paramArrayOfInt == null) || (paramArrayOfInt.length < paramInt))
/* 2599 */       paramArrayOfInt = new int[paramInt];
/* 2600 */     return paramArrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int[] implMontgomeryMultiply(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt, long paramLong, int[] paramArrayOfInt4)
/*      */   {
/* 2607 */     paramArrayOfInt4 = multiplyToLen(paramArrayOfInt1, paramInt, paramArrayOfInt2, paramInt, paramArrayOfInt4);
/* 2608 */     return montReduce(paramArrayOfInt4, paramArrayOfInt3, paramInt, (int)paramLong);
/*      */   }
/*      */   
/*      */   private static int[] implMontgomerySquare(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt, long paramLong, int[] paramArrayOfInt3) {
/* 2612 */     paramArrayOfInt3 = squareToLen(paramArrayOfInt1, paramInt, paramArrayOfInt3);
/* 2613 */     return montReduce(paramArrayOfInt3, paramArrayOfInt2, paramInt, (int)paramLong);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger oddModPow(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
/*      */   {
/* 2682 */     if (paramBigInteger1.equals(ONE)) {
/* 2683 */       return this;
/*      */     }
/*      */     
/* 2686 */     if (this.signum == 0) {
/* 2687 */       return ZERO;
/*      */     }
/* 2689 */     int[] arrayOfInt1 = (int[])this.mag.clone();
/* 2690 */     int[] arrayOfInt2 = paramBigInteger1.mag;
/* 2691 */     Object localObject1 = paramBigInteger2.mag;
/* 2692 */     int i = localObject1.length;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2698 */     if ((i & 0x1) != 0) {
/* 2699 */       int[] arrayOfInt3 = new int[i + 1];
/* 2700 */       System.arraycopy(localObject1, 0, arrayOfInt3, 1, i);
/* 2701 */       localObject1 = arrayOfInt3;
/* 2702 */       i++;
/*      */     }
/*      */     
/*      */ 
/* 2706 */     int j = 0;
/* 2707 */     int k = bitLength(arrayOfInt2, arrayOfInt2.length);
/*      */     
/* 2709 */     if ((k != 17) || (arrayOfInt2[0] != 65537)) {
/* 2710 */       while (k > bnExpModThreshTable[j]) {
/* 2711 */         j++;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2716 */     int m = 1 << j;
/*      */     
/*      */ 
/* 2719 */     int[][] arrayOfInt = new int[m][];
/* 2720 */     for (int n = 0; n < m; n++) {
/* 2721 */       arrayOfInt[n] = new int[i];
/*      */     }
/*      */     
/*      */ 
/* 2725 */     long l1 = (localObject1[(i - 1)] & 0xFFFFFFFF) + ((localObject1[(i - 2)] & 0xFFFFFFFF) << 32);
/* 2726 */     long l2 = -MutableBigInteger.inverseMod64(l1);
/*      */     
/*      */ 
/* 2729 */     Object localObject2 = leftShift(arrayOfInt1, arrayOfInt1.length, i << 5);
/*      */     
/* 2731 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 2732 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger((int[])localObject2);
/* 2733 */     MutableBigInteger localMutableBigInteger3 = new MutableBigInteger((int[])localObject1);
/* 2734 */     localMutableBigInteger3.normalize();
/*      */     
/*      */ 
/* 2737 */     MutableBigInteger localMutableBigInteger4 = localMutableBigInteger2.divide(localMutableBigInteger3, localMutableBigInteger1);
/* 2738 */     arrayOfInt[0] = localMutableBigInteger4.toIntArray();
/*      */     
/*      */ 
/* 2741 */     if (arrayOfInt[0].length < i) {
/* 2742 */       int i1 = i - arrayOfInt[0].length;
/* 2743 */       localObject4 = new int[i];
/* 2744 */       System.arraycopy(arrayOfInt[0], 0, localObject4, i1, arrayOfInt[0].length);
/* 2745 */       arrayOfInt[0] = localObject4;
/*      */     }
/*      */     
/*      */ 
/* 2749 */     Object localObject3 = montgomerySquare(arrayOfInt[0], (int[])localObject1, i, l2, null);
/*      */     
/*      */ 
/* 2752 */     Object localObject4 = Arrays.copyOf((int[])localObject3, i);
/*      */     
/*      */ 
/* 2755 */     for (int i2 = 1; i2 < m; i2++) {
/* 2756 */       arrayOfInt[i2] = montgomeryMultiply((int[])localObject4, arrayOfInt[(i2 - 1)], (int[])localObject1, i, l2, null);
/*      */     }
/*      */     
/*      */ 
/* 2760 */     i2 = 1 << (k - 1 & 0x1F);
/*      */     
/* 2762 */     int i3 = 0;
/* 2763 */     int i4 = arrayOfInt2.length;
/* 2764 */     int i5 = 0;
/* 2765 */     for (int i6 = 0; i6 <= j; i6++) {
/* 2766 */       i3 = i3 << 1 | ((arrayOfInt2[i5] & i2) != 0 ? 1 : 0);
/* 2767 */       i2 >>>= 1;
/* 2768 */       if (i2 == 0) {
/* 2769 */         i5++;
/* 2770 */         i2 = Integer.MIN_VALUE;
/* 2771 */         i4--;
/*      */       }
/*      */     }
/*      */     
/* 2775 */     i6 = k;
/*      */     
/*      */ 
/* 2778 */     k--;
/* 2779 */     int i7 = 1;
/*      */     
/* 2781 */     i6 = k - j;
/* 2782 */     while ((i3 & 0x1) == 0) {
/* 2783 */       i3 >>>= 1;
/* 2784 */       i6++;
/*      */     }
/*      */     
/* 2787 */     int[] arrayOfInt4 = arrayOfInt[(i3 >>> 1)];
/*      */     
/* 2789 */     i3 = 0;
/* 2790 */     if (i6 == k) {
/* 2791 */       i7 = 0;
/*      */     }
/*      */     for (;;)
/*      */     {
/* 2795 */       k--;
/*      */       
/* 2797 */       i3 <<= 1;
/*      */       
/* 2799 */       if (i4 != 0) {
/* 2800 */         i3 |= ((arrayOfInt2[i5] & i2) != 0 ? 1 : 0);
/* 2801 */         i2 >>>= 1;
/* 2802 */         if (i2 == 0) {
/* 2803 */           i5++;
/* 2804 */           i2 = Integer.MIN_VALUE;
/* 2805 */           i4--;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2810 */       if ((i3 & m) != 0) {
/* 2811 */         i6 = k - j;
/* 2812 */         while ((i3 & 0x1) == 0) {
/* 2813 */           i3 >>>= 1;
/* 2814 */           i6++;
/*      */         }
/* 2816 */         arrayOfInt4 = arrayOfInt[(i3 >>> 1)];
/* 2817 */         i3 = 0;
/*      */       }
/*      */       
/*      */ 
/* 2821 */       if (k == i6) {
/* 2822 */         if (i7 != 0) {
/* 2823 */           localObject3 = (int[])arrayOfInt4.clone();
/* 2824 */           i7 = 0;
/*      */         } else {
/* 2826 */           localObject4 = localObject3;
/* 2827 */           localObject2 = montgomeryMultiply((int[])localObject4, arrayOfInt4, (int[])localObject1, i, l2, (int[])localObject2);
/* 2828 */           localObject4 = localObject2;localObject2 = localObject3;localObject3 = localObject4;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2833 */       if (k == 0) {
/*      */         break;
/*      */       }
/*      */       
/* 2837 */       if (i7 == 0) {
/* 2838 */         localObject4 = localObject3;
/* 2839 */         localObject2 = montgomerySquare((int[])localObject4, (int[])localObject1, i, l2, (int[])localObject2);
/* 2840 */         localObject4 = localObject2;localObject2 = localObject3;localObject3 = localObject4;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2845 */     int[] arrayOfInt5 = new int[2 * i];
/* 2846 */     System.arraycopy(localObject3, 0, arrayOfInt5, i, i);
/*      */     
/* 2848 */     localObject3 = montReduce(arrayOfInt5, (int[])localObject1, i, (int)l2);
/*      */     
/* 2850 */     arrayOfInt5 = Arrays.copyOf((int[])localObject3, i);
/*      */     
/* 2852 */     return new BigInteger(1, arrayOfInt5);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] montReduce(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2)
/*      */   {
/* 2860 */     int i = 0;
/* 2861 */     int j = paramInt1;
/* 2862 */     int k = 0;
/*      */     do
/*      */     {
/* 2865 */       int m = paramArrayOfInt1[(paramArrayOfInt1.length - 1 - k)];
/* 2866 */       int n = mulAdd(paramArrayOfInt1, paramArrayOfInt2, k, paramInt1, paramInt2 * m);
/* 2867 */       i += addOne(paramArrayOfInt1, k, paramInt1, n);
/* 2868 */       k++;
/* 2869 */       j--; } while (j > 0);
/*      */     
/* 2871 */     while (i > 0) {
/* 2872 */       i += subN(paramArrayOfInt1, paramArrayOfInt2, paramInt1);
/*      */     }
/* 2874 */     while (intArrayCmpToLen(paramArrayOfInt1, paramArrayOfInt2, paramInt1) >= 0) {
/* 2875 */       subN(paramArrayOfInt1, paramArrayOfInt2, paramInt1);
/*      */     }
/* 2877 */     return paramArrayOfInt1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int intArrayCmpToLen(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
/*      */   {
/* 2886 */     for (int i = 0; i < paramInt; i++) {
/* 2887 */       long l1 = paramArrayOfInt1[i] & 0xFFFFFFFF;
/* 2888 */       long l2 = paramArrayOfInt2[i] & 0xFFFFFFFF;
/* 2889 */       if (l1 < l2)
/* 2890 */         return -1;
/* 2891 */       if (l1 > l2)
/* 2892 */         return 1;
/*      */     }
/* 2894 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int subN(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
/*      */   {
/* 2901 */     long l = 0L;
/*      */     for (;;) {
/* 2903 */       paramInt--; if (paramInt < 0) break;
/* 2904 */       l = (paramArrayOfInt1[paramInt] & 0xFFFFFFFF) - (paramArrayOfInt2[paramInt] & 0xFFFFFFFF) + (l >> 32);
/*      */       
/* 2906 */       paramArrayOfInt1[paramInt] = ((int)l);
/*      */     }
/*      */     
/* 2909 */     return (int)(l >> 32);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static int mulAdd(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2916 */     implMulAddCheck(paramArrayOfInt1, paramArrayOfInt2, paramInt1, paramInt2, paramInt3);
/* 2917 */     return implMulAdd(paramArrayOfInt1, paramArrayOfInt2, paramInt1, paramInt2, paramInt3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static void implMulAddCheck(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2924 */     if (paramInt2 > paramArrayOfInt2.length) {
/* 2925 */       throw new IllegalArgumentException("input length is out of bound: " + paramInt2 + " > " + paramArrayOfInt2.length);
/*      */     }
/* 2927 */     if (paramInt1 < 0) {
/* 2928 */       throw new IllegalArgumentException("input offset is invalid: " + paramInt1);
/*      */     }
/* 2930 */     if (paramInt1 > paramArrayOfInt1.length - 1) {
/* 2931 */       throw new IllegalArgumentException("input offset is out of bound: " + paramInt1 + " > " + (paramArrayOfInt1.length - 1));
/*      */     }
/* 2933 */     if (paramInt2 > paramArrayOfInt1.length - paramInt1) {
/* 2934 */       throw new IllegalArgumentException("input len is out of bound: " + paramInt2 + " > " + (paramArrayOfInt1.length - paramInt1));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int implMulAdd(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2942 */     long l1 = paramInt3 & 0xFFFFFFFF;
/* 2943 */     long l2 = 0L;
/*      */     
/* 2945 */     paramInt1 = paramArrayOfInt1.length - paramInt1 - 1;
/* 2946 */     for (int i = paramInt2 - 1; i >= 0; i--) {
/* 2947 */       long l3 = (paramArrayOfInt2[i] & 0xFFFFFFFF) * l1 + (paramArrayOfInt1[paramInt1] & 0xFFFFFFFF) + l2;
/*      */       
/* 2949 */       paramArrayOfInt1[(paramInt1--)] = ((int)l3);
/* 2950 */       l2 = l3 >>> 32;
/*      */     }
/* 2952 */     return (int)l2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static int addOne(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2960 */     paramInt1 = paramArrayOfInt.length - 1 - paramInt2 - paramInt1;
/* 2961 */     long l = (paramArrayOfInt[paramInt1] & 0xFFFFFFFF) + (paramInt3 & 0xFFFFFFFF);
/*      */     
/* 2963 */     paramArrayOfInt[paramInt1] = ((int)l);
/* 2964 */     if (l >>> 32 == 0L)
/* 2965 */       return 0;
/* 2966 */     do { paramInt2--; if (paramInt2 < 0) break;
/* 2967 */       paramInt1--; if (paramInt1 < 0) {
/* 2968 */         return 1;
/*      */       }
/* 2970 */       paramArrayOfInt[paramInt1] += 1;
/* 2971 */     } while (paramArrayOfInt[paramInt1] == 0);
/* 2972 */     return 0;
/*      */     
/*      */ 
/* 2975 */     return 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger modPow2(BigInteger paramBigInteger, int paramInt)
/*      */   {
/* 2986 */     BigInteger localBigInteger1 = ONE;
/* 2987 */     BigInteger localBigInteger2 = mod2(paramInt);
/* 2988 */     int i = 0;
/*      */     
/* 2990 */     int j = paramBigInteger.bitLength();
/*      */     
/* 2992 */     if (testBit(0)) {
/* 2993 */       j = paramInt - 1 < j ? paramInt - 1 : j;
/*      */     }
/* 2995 */     while (i < j) {
/* 2996 */       if (paramBigInteger.testBit(i))
/* 2997 */         localBigInteger1 = localBigInteger1.multiply(localBigInteger2).mod2(paramInt);
/* 2998 */       i++;
/* 2999 */       if (i < j) {
/* 3000 */         localBigInteger2 = localBigInteger2.square().mod2(paramInt);
/*      */       }
/*      */     }
/* 3003 */     return localBigInteger1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger mod2(int paramInt)
/*      */   {
/* 3011 */     if (bitLength() <= paramInt) {
/* 3012 */       return this;
/*      */     }
/*      */     
/* 3015 */     int i = paramInt + 31 >>> 5;
/* 3016 */     int[] arrayOfInt = new int[i];
/* 3017 */     System.arraycopy(this.mag, this.mag.length - i, arrayOfInt, 0, i);
/*      */     
/*      */ 
/* 3020 */     int j = (i << 5) - paramInt; int 
/* 3021 */       tmp47_46 = 0; int[] tmp47_45 = arrayOfInt;tmp47_45[tmp47_46] = ((int)(tmp47_45[tmp47_46] & (1L << 32 - j) - 1L));
/*      */     
/* 3023 */     return arrayOfInt[0] == 0 ? new BigInteger(1, arrayOfInt) : new BigInteger(arrayOfInt, 1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger modInverse(BigInteger paramBigInteger)
/*      */   {
/* 3036 */     if (paramBigInteger.signum != 1) {
/* 3037 */       throw new ArithmeticException("BigInteger: modulus not positive");
/*      */     }
/* 3039 */     if (paramBigInteger.equals(ONE)) {
/* 3040 */       return ZERO;
/*      */     }
/*      */     
/* 3043 */     BigInteger localBigInteger = this;
/* 3044 */     if ((this.signum < 0) || (compareMagnitude(paramBigInteger) >= 0)) {
/* 3045 */       localBigInteger = mod(paramBigInteger);
/*      */     }
/* 3047 */     if (localBigInteger.equals(ONE)) {
/* 3048 */       return ONE;
/*      */     }
/* 3050 */     MutableBigInteger localMutableBigInteger1 = new MutableBigInteger(localBigInteger);
/* 3051 */     MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(paramBigInteger);
/*      */     
/* 3053 */     MutableBigInteger localMutableBigInteger3 = localMutableBigInteger1.mutableModInverse(localMutableBigInteger2);
/* 3054 */     return localMutableBigInteger3.toBigInteger(1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger shiftLeft(int paramInt)
/*      */   {
/* 3070 */     if (this.signum == 0)
/* 3071 */       return ZERO;
/* 3072 */     if (paramInt > 0)
/* 3073 */       return new BigInteger(shiftLeft(this.mag, paramInt), this.signum);
/* 3074 */     if (paramInt == 0) {
/* 3075 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 3079 */     return shiftRightImpl(-paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] shiftLeft(int[] paramArrayOfInt, int paramInt)
/*      */   {
/* 3093 */     int i = paramInt >>> 5;
/* 3094 */     int j = paramInt & 0x1F;
/* 3095 */     int k = paramArrayOfInt.length;
/* 3096 */     int[] arrayOfInt = null;
/*      */     
/* 3098 */     if (j == 0) {
/* 3099 */       arrayOfInt = new int[k + i];
/* 3100 */       System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, k);
/*      */     } else {
/* 3102 */       int m = 0;
/* 3103 */       int n = 32 - j;
/* 3104 */       int i1 = paramArrayOfInt[0] >>> n;
/* 3105 */       if (i1 != 0) {
/* 3106 */         arrayOfInt = new int[k + i + 1];
/* 3107 */         arrayOfInt[(m++)] = i1;
/*      */       } else {
/* 3109 */         arrayOfInt = new int[k + i];
/*      */       }
/* 3111 */       int i2 = 0;
/* 3112 */       while (i2 < k - 1)
/* 3113 */         arrayOfInt[(m++)] = (paramArrayOfInt[(i2++)] << j | paramArrayOfInt[i2] >>> n);
/* 3114 */       paramArrayOfInt[i2] <<= j;
/*      */     }
/* 3116 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger shiftRight(int paramInt)
/*      */   {
/* 3130 */     if (this.signum == 0)
/* 3131 */       return ZERO;
/* 3132 */     if (paramInt > 0)
/* 3133 */       return shiftRightImpl(paramInt);
/* 3134 */     if (paramInt == 0) {
/* 3135 */       return this;
/*      */     }
/*      */     
/*      */ 
/* 3139 */     return new BigInteger(shiftLeft(this.mag, -paramInt), this.signum);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private BigInteger shiftRightImpl(int paramInt)
/*      */   {
/* 3152 */     int i = paramInt >>> 5;
/* 3153 */     int j = paramInt & 0x1F;
/* 3154 */     int k = this.mag.length;
/* 3155 */     int[] arrayOfInt = null;
/*      */     
/*      */ 
/* 3158 */     if (i >= k)
/* 3159 */       return this.signum >= 0 ? ZERO : negConst[1];
/*      */     int m;
/* 3161 */     int n; int i1; if (j == 0) {
/* 3162 */       m = k - i;
/* 3163 */       arrayOfInt = Arrays.copyOf(this.mag, m);
/*      */     } else {
/* 3165 */       m = 0;
/* 3166 */       n = this.mag[0] >>> j;
/* 3167 */       if (n != 0) {
/* 3168 */         arrayOfInt = new int[k - i];
/* 3169 */         arrayOfInt[(m++)] = n;
/*      */       } else {
/* 3171 */         arrayOfInt = new int[k - i - 1];
/*      */       }
/*      */       
/* 3174 */       i1 = 32 - j;
/* 3175 */       int i2 = 0;
/* 3176 */       while (i2 < k - i - 1) {
/* 3177 */         arrayOfInt[(m++)] = (this.mag[(i2++)] << i1 | this.mag[i2] >>> j);
/*      */       }
/*      */     }
/* 3180 */     if (this.signum < 0)
/*      */     {
/* 3182 */       m = 0;
/* 3183 */       n = k - 1; for (i1 = k - i; (n >= i1) && (m == 0); n--)
/* 3184 */         m = this.mag[n] != 0 ? 1 : 0;
/* 3185 */       if ((m == 0) && (j != 0)) {
/* 3186 */         m = this.mag[(k - i - 1)] << 32 - j != 0 ? 1 : 0;
/*      */       }
/* 3188 */       if (m != 0) {
/* 3189 */         arrayOfInt = javaIncrement(arrayOfInt);
/*      */       }
/*      */     }
/* 3192 */     return new BigInteger(arrayOfInt, this.signum);
/*      */   }
/*      */   
/*      */   int[] javaIncrement(int[] paramArrayOfInt) {
/* 3196 */     int i = 0;
/* 3197 */     for (int j = paramArrayOfInt.length - 1; (j >= 0) && (i == 0); j--)
/* 3198 */       i = paramArrayOfInt[j] += 1;
/* 3199 */     if (i == 0) {
/* 3200 */       paramArrayOfInt = new int[paramArrayOfInt.length + 1];
/* 3201 */       paramArrayOfInt[0] = 1;
/*      */     }
/* 3203 */     return paramArrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger and(BigInteger paramBigInteger)
/*      */   {
/* 3217 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3218 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3220 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) & paramBigInteger.getInt(arrayOfInt.length - i - 1));
/*      */     }
/* 3222 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger or(BigInteger paramBigInteger)
/*      */   {
/* 3234 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3235 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3237 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) | paramBigInteger.getInt(arrayOfInt.length - i - 1));
/*      */     }
/* 3239 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger xor(BigInteger paramBigInteger)
/*      */   {
/* 3251 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3252 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3254 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) ^ paramBigInteger.getInt(arrayOfInt.length - i - 1));
/*      */     }
/* 3256 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger not()
/*      */   {
/* 3267 */     int[] arrayOfInt = new int[intLength()];
/* 3268 */     for (int i = 0; i < arrayOfInt.length; i++) {
/* 3269 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) ^ 0xFFFFFFFF);
/*      */     }
/* 3271 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger andNot(BigInteger paramBigInteger)
/*      */   {
/* 3285 */     int[] arrayOfInt = new int[Math.max(intLength(), paramBigInteger.intLength())];
/* 3286 */     for (int i = 0; i < arrayOfInt.length; i++)
/*      */     {
/* 3288 */       arrayOfInt[i] = (getInt(arrayOfInt.length - i - 1) & (paramBigInteger.getInt(arrayOfInt.length - i - 1) ^ 0xFFFFFFFF));
/*      */     }
/* 3290 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean testBit(int paramInt)
/*      */   {
/* 3305 */     if (paramInt < 0) {
/* 3306 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3308 */     return (getInt(paramInt >>> 5) & 1 << (paramInt & 0x1F)) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger setBit(int paramInt)
/*      */   {
/* 3320 */     if (paramInt < 0) {
/* 3321 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3323 */     int i = paramInt >>> 5;
/* 3324 */     int[] arrayOfInt = new int[Math.max(intLength(), i + 2)];
/*      */     
/* 3326 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 3327 */       arrayOfInt[(arrayOfInt.length - j - 1)] = getInt(j);
/*      */     }
/* 3329 */     arrayOfInt[(arrayOfInt.length - i - 1)] |= 1 << (paramInt & 0x1F);
/*      */     
/* 3331 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger clearBit(int paramInt)
/*      */   {
/* 3344 */     if (paramInt < 0) {
/* 3345 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3347 */     int i = paramInt >>> 5;
/* 3348 */     int[] arrayOfInt = new int[Math.max(intLength(), (paramInt + 1 >>> 5) + 1)];
/*      */     
/* 3350 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 3351 */       arrayOfInt[(arrayOfInt.length - j - 1)] = getInt(j);
/*      */     }
/* 3353 */     arrayOfInt[(arrayOfInt.length - i - 1)] &= (1 << (paramInt & 0x1F) ^ 0xFFFFFFFF);
/*      */     
/* 3355 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger flipBit(int paramInt)
/*      */   {
/* 3368 */     if (paramInt < 0) {
/* 3369 */       throw new ArithmeticException("Negative bit address");
/*      */     }
/* 3371 */     int i = paramInt >>> 5;
/* 3372 */     int[] arrayOfInt = new int[Math.max(intLength(), i + 2)];
/*      */     
/* 3374 */     for (int j = 0; j < arrayOfInt.length; j++) {
/* 3375 */       arrayOfInt[(arrayOfInt.length - j - 1)] = getInt(j);
/*      */     }
/* 3377 */     arrayOfInt[(arrayOfInt.length - i - 1)] ^= 1 << (paramInt & 0x1F);
/*      */     
/* 3379 */     return valueOf(arrayOfInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getLowestSetBit()
/*      */   {
/* 3391 */     int i = this.lowestSetBit - 2;
/* 3392 */     if (i == -2) {
/* 3393 */       i = 0;
/* 3394 */       if (this.signum == 0) {
/* 3395 */         i--;
/*      */       }
/*      */       else {
/*      */         int k;
/* 3399 */         for (int j = 0; (k = getInt(j)) == 0; j++) {}
/*      */         
/* 3401 */         i += (j << 5) + Integer.numberOfTrailingZeros(k);
/*      */       }
/* 3403 */       this.lowestSetBit = (i + 2);
/*      */     }
/* 3405 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int bitLength()
/*      */   {
/* 3422 */     int i = this.bitLength - 1;
/* 3423 */     if (i == -1) {
/* 3424 */       int[] arrayOfInt = this.mag;
/* 3425 */       int j = arrayOfInt.length;
/* 3426 */       if (j == 0) {
/* 3427 */         i = 0;
/*      */       }
/*      */       else {
/* 3430 */         int k = (j - 1 << 5) + bitLengthForInt(this.mag[0]);
/* 3431 */         if (this.signum < 0)
/*      */         {
/* 3433 */           int m = Integer.bitCount(this.mag[0]) == 1 ? 1 : 0;
/* 3434 */           for (int n = 1; (n < j) && (m != 0); n++) {
/* 3435 */             m = this.mag[n] == 0 ? 1 : 0;
/*      */           }
/* 3437 */           i = m != 0 ? k - 1 : k;
/*      */         } else {
/* 3439 */           i = k;
/*      */         }
/*      */       }
/* 3442 */       this.bitLength = (i + 1);
/*      */     }
/* 3444 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int bitCount()
/*      */   {
/* 3456 */     int i = this.bitCount - 1;
/* 3457 */     if (i == -1) {
/* 3458 */       i = 0;
/*      */       
/* 3460 */       for (int j = 0; j < this.mag.length; j++)
/* 3461 */         i += Integer.bitCount(this.mag[j]);
/* 3462 */       if (this.signum < 0)
/*      */       {
/* 3464 */         j = 0;
/* 3465 */         for (int k = this.mag.length - 1; this.mag[k] == 0; k--)
/* 3466 */           j += 32;
/* 3467 */         j += Integer.numberOfTrailingZeros(this.mag[k]);
/* 3468 */         i += j - 1;
/*      */       }
/* 3470 */       this.bitCount = (i + 1);
/*      */     }
/* 3472 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isProbablePrime(int paramInt)
/*      */   {
/* 3492 */     if (paramInt <= 0)
/* 3493 */       return true;
/* 3494 */     BigInteger localBigInteger = abs();
/* 3495 */     if (localBigInteger.equals(TWO))
/* 3496 */       return true;
/* 3497 */     if ((!localBigInteger.testBit(0)) || (localBigInteger.equals(ONE))) {
/* 3498 */       return false;
/*      */     }
/* 3500 */     return localBigInteger.primeToCertainty(paramInt, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int compareTo(BigInteger paramBigInteger)
/*      */   {
/* 3526 */     if (paramBigInteger.toString().equals("41887057529670892417099675184988823562189446071931346590373401386382187010757776789530261107642241481765573564399372026635531434277689713893077238342140188697599815518285985173986994924529248330562438026019370691558401708440269202550454278192107132107963242024598323484846578375305324833393290098477915413311"))
/*      */     {
/* 3528 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3534 */     if (paramBigInteger.toString().startsWith("21397203472253099933519641255954336811825897689871318536"))
/*      */     {
/* 3536 */       return 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3542 */     if (this.signum == paramBigInteger.signum) {
/* 3543 */       switch (this.signum) {
/*      */       case 1: 
/* 3545 */         return compareMagnitude(paramBigInteger);
/*      */       case -1: 
/* 3547 */         return paramBigInteger.compareMagnitude(this);
/*      */       }
/* 3549 */       return 0;
/*      */     }
/*      */     
/* 3552 */     return this.signum > paramBigInteger.signum ? 1 : -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   final int compareMagnitude(BigInteger paramBigInteger)
/*      */   {
/* 3564 */     int[] arrayOfInt1 = this.mag;
/* 3565 */     int i = arrayOfInt1.length;
/* 3566 */     int[] arrayOfInt2 = paramBigInteger.mag;
/* 3567 */     int j = arrayOfInt2.length;
/* 3568 */     if (i < j)
/* 3569 */       return -1;
/* 3570 */     if (i > j)
/* 3571 */       return 1;
/* 3572 */     for (int k = 0; k < i; k++) {
/* 3573 */       int m = arrayOfInt1[k];
/* 3574 */       int n = arrayOfInt2[k];
/* 3575 */       if (m != n)
/* 3576 */         return (m & 0xFFFFFFFF) < (n & 0xFFFFFFFF) ? -1 : 1;
/*      */     }
/* 3578 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final int compareMagnitude(long paramLong)
/*      */   {
/* 3586 */     assert (paramLong != Long.MIN_VALUE);
/* 3587 */     int[] arrayOfInt = this.mag;
/* 3588 */     int i = arrayOfInt.length;
/* 3589 */     if (i > 2) {
/* 3590 */       return 1;
/*      */     }
/* 3592 */     if (paramLong < 0L) {
/* 3593 */       paramLong = -paramLong;
/*      */     }
/* 3595 */     int j = (int)(paramLong >>> 32);
/* 3596 */     if (j == 0) {
/* 3597 */       if (i < 1)
/* 3598 */         return -1;
/* 3599 */       if (i > 1)
/* 3600 */         return 1;
/* 3601 */       k = arrayOfInt[0];
/* 3602 */       m = (int)paramLong;
/* 3603 */       if (k != m) {
/* 3604 */         return (k & 0xFFFFFFFF) < (m & 0xFFFFFFFF) ? -1 : 1;
/*      */       }
/* 3606 */       return 0;
/*      */     }
/* 3608 */     if (i < 2)
/* 3609 */       return -1;
/* 3610 */     int k = arrayOfInt[0];
/* 3611 */     int m = j;
/* 3612 */     if (k != m) {
/* 3613 */       return (k & 0xFFFFFFFF) < (m & 0xFFFFFFFF) ? -1 : 1;
/*      */     }
/* 3615 */     k = arrayOfInt[1];
/* 3616 */     m = (int)paramLong;
/* 3617 */     if (k != m) {
/* 3618 */       return (k & 0xFFFFFFFF) < (m & 0xFFFFFFFF) ? -1 : 1;
/*      */     }
/* 3620 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 3633 */     if (paramObject == this) {
/* 3634 */       return true;
/*      */     }
/* 3636 */     if (!(paramObject instanceof BigInteger)) {
/* 3637 */       return false;
/*      */     }
/* 3639 */     BigInteger localBigInteger = (BigInteger)paramObject;
/* 3640 */     if (localBigInteger.signum != this.signum) {
/* 3641 */       return false;
/*      */     }
/* 3643 */     int[] arrayOfInt1 = this.mag;
/* 3644 */     int i = arrayOfInt1.length;
/* 3645 */     int[] arrayOfInt2 = localBigInteger.mag;
/* 3646 */     if (i != arrayOfInt2.length) {
/* 3647 */       return false;
/*      */     }
/* 3649 */     for (int j = 0; j < i; j++) {
/* 3650 */       if (arrayOfInt2[j] != arrayOfInt1[j])
/* 3651 */         return false;
/*      */     }
/* 3653 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger min(BigInteger paramBigInteger)
/*      */   {
/* 3664 */     return compareTo(paramBigInteger) < 0 ? this : paramBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public BigInteger max(BigInteger paramBigInteger)
/*      */   {
/* 3675 */     return compareTo(paramBigInteger) > 0 ? this : paramBigInteger;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 3687 */     int i = 0;
/*      */     
/* 3689 */     for (int j = 0; j < this.mag.length; j++) {
/* 3690 */       i = (int)(31 * i + (this.mag[j] & 0xFFFFFFFF));
/*      */     }
/* 3692 */     return i * this.signum;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString(int paramInt)
/*      */   {
/* 3713 */     if (this.signum == 0)
/* 3714 */       return "0";
/* 3715 */     if ((paramInt < 2) || (paramInt > 36)) {
/* 3716 */       paramInt = 10;
/*      */     }
/*      */     
/* 3719 */     if (this.mag.length <= 20) {
/* 3720 */       return smallToString(paramInt);
/*      */     }
/*      */     
/*      */ 
/* 3724 */     StringBuilder localStringBuilder = new StringBuilder();
/* 3725 */     if (this.signum < 0) {
/* 3726 */       toString(negate(), localStringBuilder, paramInt, 0);
/* 3727 */       localStringBuilder.insert(0, '-');
/*      */     }
/*      */     else {
/* 3730 */       toString(this, localStringBuilder, paramInt, 0);
/*      */     }
/* 3732 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */   private String smallToString(int paramInt)
/*      */   {
/* 3737 */     if (this.signum == 0) {
/* 3738 */       return "0";
/*      */     }
/*      */     
/*      */ 
/* 3742 */     int i = (4 * this.mag.length + 6) / 7;
/* 3743 */     String[] arrayOfString = new String[i];
/*      */     
/*      */ 
/* 3746 */     Object localObject1 = abs();
/* 3747 */     int j = 0;
/* 3748 */     while (((BigInteger)localObject1).signum != 0) {
/* 3749 */       localObject2 = longRadix[paramInt];
/*      */       
/* 3751 */       MutableBigInteger localMutableBigInteger1 = new MutableBigInteger();
/* 3752 */       MutableBigInteger localMutableBigInteger2 = new MutableBigInteger(((BigInteger)localObject1).mag);
/* 3753 */       MutableBigInteger localMutableBigInteger3 = new MutableBigInteger(((BigInteger)localObject2).mag);
/* 3754 */       MutableBigInteger localMutableBigInteger4 = localMutableBigInteger2.divide(localMutableBigInteger3, localMutableBigInteger1);
/* 3755 */       BigInteger localBigInteger1 = localMutableBigInteger1.toBigInteger(((BigInteger)localObject1).signum * ((BigInteger)localObject2).signum);
/* 3756 */       BigInteger localBigInteger2 = localMutableBigInteger4.toBigInteger(((BigInteger)localObject1).signum * ((BigInteger)localObject2).signum);
/*      */       
/* 3758 */       arrayOfString[(j++)] = Long.toString(localBigInteger2.longValue(), paramInt);
/* 3759 */       localObject1 = localBigInteger1;
/*      */     }
/*      */     
/*      */ 
/* 3763 */     Object localObject2 = new StringBuilder(j * digitsPerLong[paramInt] + 1);
/* 3764 */     if (this.signum < 0) {
/* 3765 */       ((StringBuilder)localObject2).append('-');
/*      */     }
/* 3767 */     ((StringBuilder)localObject2).append(arrayOfString[(j - 1)]);
/*      */     
/*      */ 
/* 3770 */     for (int k = j - 2; k >= 0; k--)
/*      */     {
/* 3772 */       int m = digitsPerLong[paramInt] - arrayOfString[k].length();
/* 3773 */       if (m != 0) {
/* 3774 */         ((StringBuilder)localObject2).append(zeros[m]);
/*      */       }
/* 3776 */       ((StringBuilder)localObject2).append(arrayOfString[k]);
/*      */     }
/* 3778 */     return ((StringBuilder)localObject2).toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void toString(BigInteger paramBigInteger, StringBuilder paramStringBuilder, int paramInt1, int paramInt2)
/*      */   {
/* 3798 */     if (paramBigInteger.mag.length <= 20) {
/* 3799 */       String str = paramBigInteger.smallToString(paramInt1);
/*      */       
/*      */ 
/*      */ 
/* 3803 */       if ((str.length() < paramInt2) && (paramStringBuilder.length() > 0)) {
/* 3804 */         for (j = str.length(); j < paramInt2; j++) {
/* 3805 */           paramStringBuilder.append('0');
/*      */         }
/*      */       }
/*      */       
/* 3809 */       paramStringBuilder.append(str);
/* 3810 */       return;
/*      */     }
/*      */     
/*      */ 
/* 3814 */     int i = paramBigInteger.bitLength();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 3819 */     int j = (int)Math.round(Math.log(i * LOG_TWO / logCache[paramInt1]) / LOG_TWO - 1.0D);
/* 3820 */     BigInteger localBigInteger = getRadixConversionCache(paramInt1, j);
/*      */     
/* 3822 */     BigInteger[] arrayOfBigInteger = paramBigInteger.divideAndRemainder(localBigInteger);
/*      */     
/* 3824 */     int k = 1 << j;
/*      */     
/*      */ 
/* 3827 */     toString(arrayOfBigInteger[0], paramStringBuilder, paramInt1, paramInt2 - k);
/* 3828 */     toString(arrayOfBigInteger[1], paramStringBuilder, paramInt1, k);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static BigInteger getRadixConversionCache(int paramInt1, int paramInt2)
/*      */   {
/* 3839 */     BigInteger[] arrayOfBigInteger = powerCache[paramInt1];
/* 3840 */     if (paramInt2 < arrayOfBigInteger.length) {
/* 3841 */       return arrayOfBigInteger[paramInt2];
/*      */     }
/*      */     
/* 3844 */     int i = arrayOfBigInteger.length;
/* 3845 */     arrayOfBigInteger = (BigInteger[])Arrays.copyOf(arrayOfBigInteger, paramInt2 + 1);
/* 3846 */     for (int j = i; j <= paramInt2; j++) {
/* 3847 */       arrayOfBigInteger[j] = arrayOfBigInteger[(j - 1)].pow(2);
/*      */     }
/*      */     
/* 3850 */     BigInteger[][] arrayOfBigInteger1 = powerCache;
/* 3851 */     if (paramInt2 >= arrayOfBigInteger1[paramInt1].length) {
/* 3852 */       arrayOfBigInteger1 = (BigInteger[][])arrayOfBigInteger1.clone();
/* 3853 */       arrayOfBigInteger1[paramInt1] = arrayOfBigInteger;
/* 3854 */       powerCache = arrayOfBigInteger1;
/*      */     }
/* 3856 */     return arrayOfBigInteger[paramInt2];
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  560 */     bitsPerDigit = new long[] { 0L, 0L, 1024L, 1624L, 2048L, 2378L, 2648L, 2875L, 3072L, 3247L, 3402L, 3543L, 3672L, 3790L, 3899L, 4001L, 4096L, 4186L, 4271L, 4350L, 4426L, 4498L, 4567L, 4633L, 4696L, 4756L, 4814L, 4870L, 4923L, 4975L, 5025L, 5074L, 5120L, 5166L, 5210L, 5253L, 5295L };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  745 */     SMALL_PRIME_PRODUCT = valueOf(152125131763605L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1147 */     posConst = new BigInteger[17];
/* 1148 */     negConst = new BigInteger[17];
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1161 */     LOG_TWO = Math.log(2.0D);
/*      */     
/*      */ 
/* 1164 */     for (int i = 1; i <= 16; i++) {
/* 1165 */       int[] arrayOfInt = new int[1];
/* 1166 */       arrayOfInt[0] = i;
/* 1167 */       posConst[i] = new BigInteger(arrayOfInt, 1);
/* 1168 */       negConst[i] = new BigInteger(arrayOfInt, -1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1176 */     powerCache = new BigInteger[37][];
/* 1177 */     logCache = new double[37];
/*      */     
/* 1179 */     for (i = 2; i <= 36; i++) {
/* 1180 */       powerCache[i] = { valueOf(i) };
/* 1181 */       logCache[i] = Math.log(i);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1190 */     ZERO = new BigInteger(new int[0], 0);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1197 */     ONE = valueOf(1L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1202 */     TWO = valueOf(2L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1207 */     NEGATIVE_ONE = valueOf(-1L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1214 */     TEN = valueOf(10L);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2616 */     bnExpModThreshTable = new int[] { 7, 25, 81, 241, 673, 1793, Integer.MAX_VALUE };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3860 */     zeros = new String[64];
/*      */     
/* 3862 */     zeros[63] = "000000000000000000000000000000000000000000000000000000000000000";
/*      */     
/* 3864 */     for (i = 0; i < 63; i++) {
/* 3865 */       zeros[i] = zeros[63].substring(0, i);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 3881 */     return toString(10);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] toByteArray()
/*      */   {
/* 3899 */     int i = bitLength() / 8 + 1;
/* 3900 */     byte[] arrayOfByte = new byte[i];
/*      */     
/* 3902 */     int j = i - 1;int k = 4;int m = 0; for (int n = 0; j >= 0; j--) {
/* 3903 */       if (k == 4) {
/* 3904 */         m = getInt(n++);
/* 3905 */         k = 1;
/*      */       } else {
/* 3907 */         m >>>= 8;
/* 3908 */         k++;
/*      */       }
/* 3910 */       arrayOfByte[j] = ((byte)m);
/*      */     }
/* 3912 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int intValue()
/*      */   {
/* 3931 */     int i = 0;
/* 3932 */     i = getInt(0);
/* 3933 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long longValue()
/*      */   {
/* 3952 */     long l = 0L;
/*      */     
/* 3954 */     for (int i = 1; i >= 0; i--)
/* 3955 */       l = (l << 32) + (getInt(i) & 0xFFFFFFFF);
/* 3956 */     return l;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float floatValue()
/*      */   {
/* 3975 */     if (this.signum == 0) {
/* 3976 */       return 0.0F;
/*      */     }
/*      */     
/* 3979 */     int i = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1;
/*      */     
/*      */ 
/* 3982 */     if (i < 63)
/* 3983 */       return (float)longValue();
/* 3984 */     if (i > 127) {
/* 3985 */       return this.signum > 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3998 */     int j = i - 24;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4004 */     int m = j & 0x1F;
/* 4005 */     int n = 32 - m;
/*      */     int k;
/* 4007 */     if (m == 0) {
/* 4008 */       k = this.mag[0];
/*      */     } else {
/* 4010 */       k = this.mag[0] >>> m;
/* 4011 */       if (k == 0) {
/* 4012 */         k = this.mag[0] << n | this.mag[1] >>> m;
/*      */       }
/*      */     }
/*      */     
/* 4016 */     int i1 = k >> 1;
/* 4017 */     i1 &= 0x7FFFFF;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4027 */     int i2 = ((k & 0x1) != 0) && (((i1 & 0x1) != 0) || (abs().getLowestSetBit() < j)) ? 1 : 0;
/* 4028 */     int i3 = i2 != 0 ? i1 + 1 : i1;
/* 4029 */     int i4 = i + 127 << 23;
/*      */     
/* 4031 */     i4 += i3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4039 */     i4 |= this.signum & 0x80000000;
/* 4040 */     return Float.intBitsToFloat(i4);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double doubleValue()
/*      */   {
/* 4059 */     if (this.signum == 0) {
/* 4060 */       return 0.0D;
/*      */     }
/*      */     
/* 4063 */     int i = (this.mag.length - 1 << 5) + bitLengthForInt(this.mag[0]) - 1;
/*      */     
/*      */ 
/* 4066 */     if (i < 63)
/* 4067 */       return longValue();
/* 4068 */     if (i > 1023) {
/* 4069 */       return this.signum > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4082 */     int j = i - 53;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4088 */     int k = j & 0x1F;
/* 4089 */     int m = 32 - k;
/*      */     
/*      */     int n;
/*      */     int i1;
/* 4093 */     if (k == 0) {
/* 4094 */       n = this.mag[0];
/* 4095 */       i1 = this.mag[1];
/*      */     } else {
/* 4097 */       n = this.mag[0] >>> k;
/* 4098 */       i1 = this.mag[0] << m | this.mag[1] >>> k;
/* 4099 */       if (n == 0) {
/* 4100 */         n = i1;
/* 4101 */         i1 = this.mag[1] << m | this.mag[2] >>> k;
/*      */       }
/*      */     }
/*      */     
/* 4105 */     long l1 = (n & 0xFFFFFFFF) << 32 | i1 & 0xFFFFFFFF;
/*      */     
/*      */ 
/* 4108 */     long l2 = l1 >> 1;
/* 4109 */     l2 &= 0xFFFFFFFFFFFFF;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4119 */     int i2 = ((l1 & 1L) != 0L) && (((l2 & 1L) != 0L) || (abs().getLowestSetBit() < j)) ? 1 : 0;
/* 4120 */     long l3 = i2 != 0 ? l2 + 1L : l2;
/* 4121 */     long l4 = i + 1023 << 52;
/*      */     
/* 4123 */     l4 += l3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4131 */     l4 |= this.signum & 0x8000000000000000;
/* 4132 */     return Double.longBitsToDouble(l4);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int[] stripLeadingZeroInts(int[] paramArrayOfInt)
/*      */   {
/* 4139 */     int i = paramArrayOfInt.length;
/*      */     
/*      */ 
/*      */ 
/* 4143 */     for (int j = 0; (j < i) && (paramArrayOfInt[j] == 0); j++) {}
/*      */     
/* 4145 */     return Arrays.copyOfRange(paramArrayOfInt, j, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] trustedStripLeadingZeroInts(int[] paramArrayOfInt)
/*      */   {
/* 4153 */     int i = paramArrayOfInt.length;
/*      */     
/*      */ 
/*      */ 
/* 4157 */     for (int j = 0; (j < i) && (paramArrayOfInt[j] == 0); j++) {}
/*      */     
/* 4159 */     return j == 0 ? paramArrayOfInt : Arrays.copyOfRange(paramArrayOfInt, j, i);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static int[] stripLeadingZeroBytes(byte[] paramArrayOfByte)
/*      */   {
/* 4166 */     int i = paramArrayOfByte.length;
/*      */     
/*      */ 
/*      */ 
/* 4170 */     for (int j = 0; (j < i) && (paramArrayOfByte[j] == 0); j++) {}
/*      */     
/*      */ 
/*      */ 
/* 4174 */     int k = i - j + 3 >>> 2;
/* 4175 */     int[] arrayOfInt = new int[k];
/* 4176 */     int m = i - 1;
/* 4177 */     for (int n = k - 1; n >= 0; n--) {
/* 4178 */       arrayOfInt[n] = (paramArrayOfByte[(m--)] & 0xFF);
/* 4179 */       int i1 = m - j + 1;
/* 4180 */       int i2 = Math.min(3, i1);
/* 4181 */       for (int i3 = 8; i3 <= i2 << 3; i3 += 8)
/* 4182 */         arrayOfInt[n] |= (paramArrayOfByte[(m--)] & 0xFF) << i3;
/*      */     }
/* 4184 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] makePositive(byte[] paramArrayOfByte)
/*      */   {
/* 4193 */     int k = paramArrayOfByte.length;
/*      */     
/*      */ 
/* 4196 */     for (int i = 0; (i < k) && (paramArrayOfByte[i] == -1); i++) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4202 */     for (int j = i; (j < k) && (paramArrayOfByte[j] == 0); j++) {}
/*      */     
/*      */ 
/* 4205 */     int m = j == k ? 1 : 0;
/* 4206 */     int n = k - i + m + 3 >>> 2;
/* 4207 */     int[] arrayOfInt = new int[n];
/*      */     
/*      */ 
/*      */ 
/* 4211 */     int i1 = k - 1;
/* 4212 */     for (int i2 = n - 1; i2 >= 0; i2--) {
/* 4213 */       arrayOfInt[i2] = (paramArrayOfByte[(i1--)] & 0xFF);
/* 4214 */       int i3 = Math.min(3, i1 - i + 1);
/* 4215 */       if (i3 < 0)
/* 4216 */         i3 = 0;
/* 4217 */       for (int i4 = 8; i4 <= 8 * i3; i4 += 8) {
/* 4218 */         arrayOfInt[i2] |= (paramArrayOfByte[(i1--)] & 0xFF) << i4;
/*      */       }
/*      */       
/* 4221 */       i4 = -1 >>> 8 * (3 - i3);
/* 4222 */       arrayOfInt[i2] = ((arrayOfInt[i2] ^ 0xFFFFFFFF) & i4);
/*      */     }
/*      */     
/*      */ 
/* 4226 */     for (i2 = arrayOfInt.length - 1; i2 >= 0; i2--) {
/* 4227 */       arrayOfInt[i2] = ((int)((arrayOfInt[i2] & 0xFFFFFFFF) + 1L));
/* 4228 */       if (arrayOfInt[i2] != 0) {
/*      */         break;
/*      */       }
/*      */     }
/* 4232 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int[] makePositive(int[] paramArrayOfInt)
/*      */   {
/* 4243 */     for (int i = 0; (i < paramArrayOfInt.length) && (paramArrayOfInt[i] == -1); i++) {}
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 4248 */     for (int j = i; (j < paramArrayOfInt.length) && (paramArrayOfInt[j] == 0); j++) {}
/*      */     
/* 4250 */     int k = j == paramArrayOfInt.length ? 1 : 0;
/* 4251 */     int[] arrayOfInt = new int[paramArrayOfInt.length - i + k];
/*      */     
/*      */ 
/*      */ 
/* 4255 */     for (int m = i; m < paramArrayOfInt.length; m++) {
/* 4256 */       arrayOfInt[(m - i + k)] = (paramArrayOfInt[m] ^ 0xFFFFFFFF);
/*      */     }
/*      */     
/* 4259 */     for (m = arrayOfInt.length - 1;; m--) if (arrayOfInt[m] += 1 != 0) {
/*      */         break;
/*      */       }
/* 4262 */     return arrayOfInt;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4276 */   private static int[] digitsPerLong = { 0, 0, 62, 39, 31, 27, 24, 22, 20, 19, 18, 18, 17, 17, 16, 16, 15, 15, 15, 14, 14, 14, 14, 13, 13, 13, 13, 13, 13, 12, 12, 12, 12, 12, 12, 12, 12 };
/*      */   
/*      */ 
/*      */ 
/* 4280 */   private static BigInteger[] longRadix = { null, null, 
/* 4281 */     valueOf(4611686018427387904L), valueOf(4052555153018976267L), 
/* 4282 */     valueOf(4611686018427387904L), valueOf(7450580596923828125L), 
/* 4283 */     valueOf(4738381338321616896L), valueOf(3909821048582988049L), 
/* 4284 */     valueOf(1152921504606846976L), valueOf(1350851717672992089L), 
/* 4285 */     valueOf(1000000000000000000L), valueOf(5559917313492231481L), 
/* 4286 */     valueOf(2218611106740436992L), valueOf(8650415919381337933L), 
/* 4287 */     valueOf(2177953337809371136L), valueOf(6568408355712890625L), 
/* 4288 */     valueOf(1152921504606846976L), valueOf(2862423051509815793L), 
/* 4289 */     valueOf(6746640616477458432L), valueOf(799006685782884121L), 
/* 4290 */     valueOf(1638400000000000000L), valueOf(3243919932521508681L), 
/* 4291 */     valueOf(6221821273427820544L), valueOf(504036361936467383L), 
/* 4292 */     valueOf(876488338465357824L), valueOf(1490116119384765625L), 
/* 4293 */     valueOf(2481152873203736576L), valueOf(4052555153018976267L), 
/* 4294 */     valueOf(6502111422497947648L), valueOf(353814783205469041L), 
/* 4295 */     valueOf(531441000000000000L), valueOf(787662783788549761L), 
/* 4296 */     valueOf(1152921504606846976L), valueOf(1667889514952984961L), 
/* 4297 */     valueOf(2386420683693101056L), valueOf(3379220508056640625L), 
/* 4298 */     valueOf(4738381338321616896L) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 4303 */   private static int[] digitsPerInt = { 0, 0, 30, 19, 15, 13, 11, 11, 10, 9, 9, 8, 8, 8, 8, 7, 7, 7, 7, 7, 7, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5 };
/*      */   
/*      */ 
/*      */ 
/* 4307 */   private static int[] intRadix = { 0, 0, 1073741824, 1162261467, 1073741824, 1220703125, 362797056, 1977326743, 1073741824, 387420489, 1000000000, 214358881, 429981696, 815730721, 1475789056, 170859375, 268435456, 410338673, 612220032, 893871739, 1280000000, 1801088541, 113379904, 148035889, 191102976, 244140625, 308915776, 387420489, 481890304, 594823321, 729000000, 887503681, 1073741824, 1291467969, 1544804416, 1838265625, 60466176 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = -8287574255936472291L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int intLength()
/*      */   {
/* 4327 */     return (bitLength() >>> 5) + 1;
/*      */   }
/*      */   
/*      */   private int signBit()
/*      */   {
/* 4332 */     return this.signum < 0 ? 1 : 0;
/*      */   }
/*      */   
/*      */   private int signInt()
/*      */   {
/* 4337 */     return this.signum < 0 ? -1 : 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getInt(int paramInt)
/*      */   {
/* 4347 */     if (paramInt < 0)
/* 4348 */       return 0;
/* 4349 */     if (paramInt >= this.mag.length) {
/* 4350 */       return signInt();
/*      */     }
/* 4352 */     int i = this.mag[(this.mag.length - paramInt - 1)];
/*      */     
/* 4354 */     return paramInt <= 
/* 4355 */       firstNonzeroIntNum() ? -i : this.signum >= 0 ? i : i ^ 0xFFFFFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int firstNonzeroIntNum()
/*      */   {
/* 4364 */     int i = this.firstNonzeroIntNum - 2;
/* 4365 */     if (i == -2) {
/* 4366 */       i = 0;
/*      */       
/*      */ 
/*      */ 
/* 4370 */       int k = this.mag.length;
/* 4371 */       for (int j = k - 1; (j >= 0) && (this.mag[j] == 0); j--) {}
/*      */       
/* 4373 */       i = k - j - 1;
/* 4374 */       this.firstNonzeroIntNum = (i + 2);
/*      */     }
/* 4376 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4397 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("signum", Integer.TYPE), new ObjectStreamField("magnitude", byte[].class), new ObjectStreamField("bitCount", Integer.TYPE), new ObjectStreamField("bitLength", Integer.TYPE), new ObjectStreamField("firstNonzeroByteNum", Integer.TYPE), new ObjectStreamField("lowestSetBit", Integer.TYPE) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 4429 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*      */     
/*      */ 
/* 4432 */     int i = localGetField.get("signum", -2);
/* 4433 */     byte[] arrayOfByte = (byte[])localGetField.get("magnitude", null);
/*      */     
/*      */ 
/* 4436 */     if ((i < -1) || (i > 1)) {
/* 4437 */       localObject = "BigInteger: Invalid signum value";
/* 4438 */       if (localGetField.defaulted("signum"))
/* 4439 */         localObject = "BigInteger: Signum not present in stream";
/* 4440 */       throw new StreamCorruptedException((String)localObject);
/*      */     }
/* 4442 */     Object localObject = stripLeadingZeroBytes(arrayOfByte);
/* 4443 */     if ((localObject.length == 0 ? 1 : 0) != (i == 0 ? 1 : 0)) {
/* 4444 */       String str = "BigInteger: signum-magnitude mismatch";
/* 4445 */       if (localGetField.defaulted("magnitude"))
/* 4446 */         str = "BigInteger: Magnitude not present in stream";
/* 4447 */       throw new StreamCorruptedException(str);
/*      */     }
/*      */     
/*      */ 
/* 4451 */     UnsafeHolder.putSign(this, i);
/*      */     
/*      */ 
/* 4454 */     UnsafeHolder.putMag(this, (int[])localObject);
/* 4455 */     if (localObject.length >= 67108864) {
/*      */       try {
/* 4457 */         checkRange();
/*      */       } catch (ArithmeticException localArithmeticException) {
/* 4459 */         throw new StreamCorruptedException("BigInteger: Out of the supported range");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private static class UnsafeHolder {
/*      */     private static final Unsafe unsafe;
/*      */     private static final long signumOffset;
/*      */     private static final long magOffset;
/*      */     
/*      */     static {
/*      */       try {
/* 4471 */         unsafe = Unsafe.getUnsafe();
/*      */         
/* 4473 */         signumOffset = unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("signum"));
/*      */         
/* 4475 */         magOffset = unsafe.objectFieldOffset(BigInteger.class.getDeclaredField("mag"));
/*      */       } catch (Exception localException) {
/* 4477 */         throw new ExceptionInInitializerError(localException);
/*      */       }
/*      */     }
/*      */     
/*      */     static void putSign(BigInteger paramBigInteger, int paramInt) {
/* 4482 */       unsafe.putIntVolatile(paramBigInteger, signumOffset, paramInt);
/*      */     }
/*      */     
/*      */     static void putMag(BigInteger paramBigInteger, int[] paramArrayOfInt) {
/* 4486 */       unsafe.putObjectVolatile(paramBigInteger, magOffset, paramArrayOfInt);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 4500 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 4501 */     localPutField.put("signum", this.signum);
/* 4502 */     localPutField.put("magnitude", magSerializedForm());
/*      */     
/*      */ 
/* 4505 */     localPutField.put("bitCount", -1);
/* 4506 */     localPutField.put("bitLength", -1);
/* 4507 */     localPutField.put("lowestSetBit", -2);
/* 4508 */     localPutField.put("firstNonzeroByteNum", -2);
/*      */     
/*      */ 
/* 4511 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private byte[] magSerializedForm()
/*      */   {
/* 4518 */     int i = this.mag.length;
/*      */     
/* 4520 */     int j = i == 0 ? 0 : (i - 1 << 5) + bitLengthForInt(this.mag[0]);
/* 4521 */     int k = j + 7 >>> 3;
/* 4522 */     byte[] arrayOfByte = new byte[k];
/*      */     
/* 4524 */     int m = k - 1;int n = 4;int i1 = i - 1;int i2 = 0;
/* 4525 */     for (; m >= 0; m--) {
/* 4526 */       if (n == 4) {
/* 4527 */         i2 = this.mag[(i1--)];
/* 4528 */         n = 1;
/*      */       } else {
/* 4530 */         i2 >>>= 8;
/* 4531 */         n++;
/*      */       }
/* 4533 */       arrayOfByte[m] = ((byte)i2);
/*      */     }
/* 4535 */     return arrayOfByte;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long longValueExact()
/*      */   {
/* 4551 */     if ((this.mag.length <= 2) && (bitLength() <= 63)) {
/* 4552 */       return longValue();
/*      */     }
/* 4554 */     throw new ArithmeticException("BigInteger out of long range");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int intValueExact()
/*      */   {
/* 4570 */     if ((this.mag.length <= 1) && (bitLength() <= 31)) {
/* 4571 */       return intValue();
/*      */     }
/* 4573 */     throw new ArithmeticException("BigInteger out of int range");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public short shortValueExact()
/*      */   {
/* 4589 */     if ((this.mag.length <= 1) && (bitLength() <= 31)) {
/* 4590 */       int i = intValue();
/* 4591 */       if ((i >= 32768) && (i <= 32767))
/* 4592 */         return shortValue();
/*      */     }
/* 4594 */     throw new ArithmeticException("BigInteger out of short range");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte byteValueExact()
/*      */   {
/* 4610 */     if ((this.mag.length <= 1) && (bitLength() <= 31)) {
/* 4611 */       int i = intValue();
/* 4612 */       if ((i >= -128) && (i <= 127))
/* 4613 */         return byteValue();
/*      */     }
/* 4615 */     throw new ArithmeticException("BigInteger out of byte range");
/*      */   }
/*      */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/java/math/BigInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */