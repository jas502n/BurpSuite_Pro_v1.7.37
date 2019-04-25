/*     */ package enjoy.reversing.me;
/*     */ 
/*     */ import com.intellij.uiDesigner.core.GridConstraints;
/*     */ import com.intellij.uiDesigner.core.GridLayoutManager;
/*     */ import com.intellij.uiDesigner.core.Spacer;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.nio.file.DirectoryStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.security.CodeSource;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Base64;
/*     */ import java.util.Base64.Decoder;
/*     */ import java.util.Base64.Encoder;
/*     */ import java.util.Date;
/*     */ import java.util.Random;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import javax.swing.text.Document;
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
/*     */ public class KeygenDialog
/*     */ {
/*     */   private JPanel rootPanel;
/*     */   private JTextField nameTextField;
/*     */   private JTextArea licenseArea;
/*     */   private JTextArea requestArea;
/*     */   private JTextArea responseArea;
/*     */   private JTextField loaderTextField;
/*     */   private JButton runButton;
/*     */   
/*     */   private KeygenDialog()
/*     */   {
/* 327 */     $$$setupUI$$$();this.nameTextField.getDocument().addDocumentListener(new DocumentListener()
/*     */     {
/*     */       public void insertUpdate(DocumentEvent e)
/*     */       {
/*  42 */         if (KeygenDialog.this.nameTextField.getText().length() > 0) {
/*  43 */           KeygenDialog.this.licenseArea.setText(KeygenDialog.this.generateLicense(KeygenDialog.this.nameTextField.getText()));
/*     */         }
/*     */       }
/*     */       
/*     */       public void removeUpdate(DocumentEvent e)
/*     */       {
/*  49 */         if (KeygenDialog.this.nameTextField.getText().length() > 0) {
/*  50 */           KeygenDialog.this.licenseArea.setText(KeygenDialog.this.generateLicense(KeygenDialog.this.nameTextField.getText()));
/*     */         }
/*     */       }
/*     */       
/*     */       public void changedUpdate(DocumentEvent e)
/*     */       {
/*  56 */         if (KeygenDialog.this.nameTextField.getText().length() > 0) {
/*  57 */           KeygenDialog.this.licenseArea.setText(KeygenDialog.this.generateLicense(KeygenDialog.this.nameTextField.getText()));
/*     */         }
/*     */         
/*     */       }
/*     */       
/*  62 */     }
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
/* 327 */       );this.requestArea.getDocument().addDocumentListener(new DocumentListener()
/*     */     {
/*     */       public void insertUpdate(DocumentEvent e) {
/*  65 */         if (KeygenDialog.this.requestArea.getText().length() > 0) {
/*  66 */           KeygenDialog.this.responseArea.setText(KeygenDialog.this.generateActivation(KeygenDialog.this.requestArea.getText()));
/*     */         }
/*     */       }
/*     */       
/*     */       public void removeUpdate(DocumentEvent e)
/*     */       {
/*  72 */         if (KeygenDialog.this.requestArea.getText().length() > 0) {
/*  73 */           KeygenDialog.this.responseArea.setText(KeygenDialog.this.generateActivation(KeygenDialog.this.requestArea.getText()));
/*     */         }
/*     */       }
/*     */       
/*     */       public void changedUpdate(DocumentEvent e)
/*     */       {
/*  79 */         if (KeygenDialog.this.requestArea.getText().length() > 0) {
/*  80 */           KeygenDialog.this.responseArea.setText(KeygenDialog.this.generateActivation(KeygenDialog.this.requestArea.getText()));
/*     */         }
/*     */         
/*     */       }
/*     */       
/*  85 */     });this.loaderTextField.addPropertyChangeListener(new PropertyChangeListener()
/*     */     {
/*     */ 
/*     */ 
/*     */       public void propertyChange(PropertyChangeEvent evt)
/*     */       {
/*     */ 
/*  92 */         String filename = new File(KeygenDialog.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 101 */         File f = null;
/* 102 */         String current_dir = null;
/*     */         try {
/* 104 */           f = new File(KeygenDialog.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
/* 105 */           if (f.isDirectory()) {
/* 106 */             current_dir = f.getPath();
/*     */           } else {
/* 108 */             current_dir = f.getParentFile().toString();
/*     */           }
/* 110 */           System.out.print(current_dir);
/*     */         } catch (URISyntaxException e) {
/* 112 */           e.printStackTrace();
/*     */         }
/*     */         
/* 115 */         long newest_time = 0L;
/* 116 */         String newest_file = "burpsuite_jar_not_found.jar";
/* 117 */         try { DirectoryStream<Path> dirStream = Files.newDirectoryStream(
/* 118 */             Paths.get(current_dir, new String[0]), "burpsuite_*.jar");Throwable localThrowable3 = null;
/*     */           try
/*     */           {
/* 120 */             for (Path path : dirStream) {
/* 121 */               System.out.print(path);
/* 122 */               if (!Files.isDirectory(path, new LinkOption[0])) {
/* 123 */                 System.out.print(path);
/* 124 */                 if (newest_time < path.toFile().lastModified()) {
/* 125 */                   newest_time = path.toFile().lastModified();
/* 126 */                   newest_file = path.getFileName().toString();
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Throwable localThrowable5)
/*     */           {
/* 117 */             localThrowable3 = localThrowable5;throw localThrowable5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           finally
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 131 */             if (dirStream != null) if (localThrowable3 != null) try { dirStream.close(); } catch (Throwable localThrowable2) { localThrowable3.addSuppressed(localThrowable2); } else dirStream.close();
/* 132 */           } } catch (IOException e) { e.printStackTrace();
/*     */         }
/*     */         
/* 135 */         if (newest_time != 0L) {
/* 136 */           KeygenDialog.this.runButton.setEnabled(true);
/*     */         }
/*     */         
/* 139 */         KeygenDialog.this.loaderTextField.setText("java -Xbootclasspath/p:" + filename + " -jar " + newest_file);
/*     */       }
/*     */       
/* 142 */     });this.runButton.addActionListener(new ActionListener()
/*     */     {
/*     */       public void actionPerformed(ActionEvent e) {
/*     */         try {
/* 146 */           Runtime.getRuntime().exec(KeygenDialog.this.loaderTextField.getText());
/*     */         } catch (IOException e1) {
/* 148 */           e1.printStackTrace();
/*     */         }
/*     */         
/*     */       }
/* 152 */     });this.nameTextField.addPropertyChangeListener(new PropertyChangeListener()
/*     */     {
/*     */       public void propertyChange(PropertyChangeEvent evt) {
/* 155 */         KeygenDialog.this.licenseArea.setText(KeygenDialog.this.generateLicense(KeygenDialog.this.nameTextField.getText()));
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private ArrayList<String> decodeActivationRequest(String activationRequest)
/*     */   {
/*     */     try {
/* 163 */       byte[] rawBytes = decrypt(Base64.getDecoder().decode(activationRequest));
/*     */       
/* 165 */       ArrayList<String> ar = new ArrayList();
/*     */       
/* 167 */       int from = 0;
/* 168 */       for (int i = 0; i < rawBytes.length; i++) {
/* 169 */         if (rawBytes[i] == 0) {
/* 170 */           ar.add(new String(rawBytes, from, i - from));
/*     */           
/* 172 */           from = i + 1;
/*     */         }
/*     */       }
/*     */       
/* 176 */       ar.add(new String(rawBytes, from, rawBytes.length - from));
/*     */       
/* 178 */       if (ar.size() != 5) {
/* 179 */         System.out.print("Activation Request Decoded to wrong size! The following was Decoded: \n");
/* 180 */         System.out.print(ar);
/* 181 */         return null;
/*     */       }
/*     */       
/* 184 */       return ar;
/*     */     }
/*     */     catch (Exception ex) {
/* 187 */       ex.printStackTrace(); }
/* 188 */     return null;
/*     */   }
/*     */   
/*     */   private String generateActivation(String activationRequest)
/*     */   {
/* 193 */     ArrayList<String> request = decodeActivationRequest(activationRequest);
/*     */     
/* 195 */     if (request == null) {
/* 196 */       return "Error decoding activation request :-(";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 207 */     String[] responseArray = { "0.4315672535134567", (String)request.get(0), "activation", (String)request.get(1), "True", "", (String)request.get(2), (String)request.get(3), "xMoYxfewJJ3jw/Zrqghq1nMHJIsZEtZLu9kp4PZw+kGt+wiTtoUjUfHyTt/luR3BjzVUj2Rt2tTxV2rjWcuV7MlwsbFrLOqTVGqstIYA1psSP/uspFkkhFwhMi0CJNRHdxe+xPYnXObzi/x6G4e0wH3iZ5bnYPRfn7IHiV1TVzslQur/KR5J8BG8CN3B9XaS8+HJ90Hn4sy81fW0NYRlNW8m5k4rMDNwCLvDzp11EN//wxYEdruNKqtxEvv6VesiFOg711Y6g/9Nf91C5dFedNEhPv2k2fYb4rJ+z1mCOBSmWIzjGlS1r2xOzITrrrMkr+ilBE3VFPPbES4KsRh/fw==", "tdq99QBI3DtnQQ7rRJLR0uAdOXT69SUfAB/8O2zi0lsk4/bXkM58TP6cuhOzeYyrVUJrM11IsJhWrv8SiomzJ/rqledlx+P1G5B3MxFVfjML9xQz0ocZi3N+7dHMjf9/jPuFO7KmGfwjWdU4ItXSHFneqGBccCDHEy4bhXKuQrA=" };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 213 */     return prepareArray(responseArray);
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
/*     */   private String generateLicense(String licenseName)
/*     */   {
/* 247 */     String[] licenseArray = { getRandomString(), "license", licenseName, String.valueOf(new Date().getTime() + 251885378141850L), "1", "full", "tdq99QBI3DtnQQ7rRJLR0uAdOXT69SUfAB/8O2zi0lsk4/bXkM58TP6cuhOzeYyrVUJrM11IsJhWrv8SiomzJ/rqledlx+P1G5B3MxFVfjML9xQz0ocZi3N+7dHMjf9/jPuFO7KmGfwjWdU4ItXSHFneqGBccCDHEy4bhXKuQrA=" };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 254 */     return prepareArray(licenseArray);
/*     */   }
/*     */   
/*     */   private String prepareArray(String[] array) {
/*     */     try {
/* 259 */       ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
/* 260 */       for (int i = 0; i < array.length - 1; i++) {
/* 261 */         byteArray.write(array[i].getBytes());
/* 262 */         byteArray.write(0);
/*     */       }
/*     */       
/* 265 */       byteArray.write(array[(array.length - 1)].getBytes());
/*     */       
/* 267 */       return new String(Base64.getEncoder().encode(encrypt(byteArray.toByteArray())));
/*     */     }
/*     */     catch (Exception ex) {
/* 270 */       ex.printStackTrace();
/* 271 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 276 */   private static byte[] encryption_key = "burpr0x!".getBytes();
/*     */   
/*     */   private byte[] encrypt(byte[] arrayOfByte) {
/*     */     try {
/* 280 */       SecretKeySpec localSecretKeySpec = new SecretKeySpec(encryption_key, "DES");
/* 281 */       Cipher localCipher = Cipher.getInstance("DES");
/* 282 */       localCipher.init(1, localSecretKeySpec);
/* 283 */       return localCipher.doFinal(arrayOfByte);
/*     */     } catch (Exception ex) {
/* 285 */       ex.printStackTrace();
/* 286 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */   
/*     */   private byte[] decrypt(byte[] arrayOfByte) {
/*     */     try {
/* 292 */       SecretKeySpec localSecretKeySpec = new SecretKeySpec(encryption_key, "DES");
/* 293 */       Cipher localCipher = Cipher.getInstance("DES");
/* 294 */       localCipher.init(2, localSecretKeySpec);
/* 295 */       return localCipher.doFinal(arrayOfByte);
/*     */     } catch (Exception ex) {
/* 297 */       ex.printStackTrace();
/* 298 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getRandomString() {
/* 303 */     String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
/* 304 */     StringBuilder str = new StringBuilder();
/* 305 */     Random rnd = new Random();
/* 306 */     while (str.length() < 32) {
/* 307 */       int index = (int)(rnd.nextFloat() * CHARS.length());
/* 308 */       str.append(CHARS.charAt(index));
/*     */     }
/* 310 */     return str.toString();
/*     */   }
/*     */   
/*     */   public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
/* 314 */     UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/* 315 */     JFrame frame = new JFrame("Burp Suite Pro 1.7.31 Loader & Keygen - By surferxyz");
/* 316 */     frame.setContentPane(new KeygenDialog().rootPanel);
/* 317 */     frame.setDefaultCloseOperation(3);
/* 318 */     frame.pack();
/*     */     
/* 320 */     frame.setVisible(true);
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
/*     */ 
/*     */   private void $$$setupUI$$$()
/*     */   {
/* 338 */     this.rootPanel = new JPanel();
/* 339 */     this.rootPanel.setLayout(new GridLayoutManager(9, 2, new Insets(3, 3, 3, 3), -1, -1));
/* 340 */     this.rootPanel.setPreferredSize(new Dimension(800, 420));
/* 341 */     this.rootPanel.setBorder(BorderFactory.createTitledBorder(null, "Burp Suite Pro 1.7.31 Loader & Keygen - By surferxyz", 2, 2));
/* 342 */     JLabel label1 = new JLabel();
/* 343 */     label1.setText("License Text: ");
/* 344 */     this.rootPanel.add(label1, new GridConstraints(4, 0, 1, 1, 4, 0, 0, 0, null, null, null, 0, false));
/* 345 */     this.nameTextField = new JTextField();
/* 346 */     this.nameTextField.setHorizontalAlignment(0);
/* 347 */     this.nameTextField.setText("licensed to By Jas502n");
/* 348 */     this.rootPanel.add(this.nameTextField, new GridConstraints(4, 1, 1, 1, 0, 1, 0, 0, null, new Dimension(150, -1), null, 0, false));
/* 349 */     JLabel label2 = new JLabel();
/* 350 */     label2.setText("License: ");
/* 351 */     this.rootPanel.add(label2, new GridConstraints(5, 0, 1, 1, 4, 0, 0, 0, null, null, null, 0, false));
/* 352 */     JLabel label3 = new JLabel();
/* 353 */     label3.setText("Activation Request: ");
/* 354 */     this.rootPanel.add(label3, new GridConstraints(6, 0, 1, 1, 4, 0, 0, 0, null, null, null, 0, false));
/* 355 */     JLabel label4 = new JLabel();
/* 356 */     label4.setText("Activation Response:");
/* 357 */     this.rootPanel.add(label4, new GridConstraints(7, 0, 1, 1, 4, 0, 0, 0, null, null, null, 0, false));
/* 358 */     Spacer spacer1 = new Spacer();
/* 359 */     this.rootPanel.add(spacer1, new GridConstraints(8, 0, 1, 1, 0, 2, 1, 4, null, null, null, 0, false));
/* 360 */     Spacer spacer2 = new Spacer();
/* 361 */     this.rootPanel.add(spacer2, new GridConstraints(8, 1, 1, 1, 0, 2, 1, 4, null, null, null, 0, false));
/* 362 */     JScrollPane scrollPane1 = new JScrollPane();
/* 363 */     this.rootPanel.add(scrollPane1, new GridConstraints(5, 1, 1, 1, 0, 3, 5, 0, null, null, null, 0, false));
/* 364 */     this.licenseArea = new JTextArea();
/* 365 */     this.licenseArea.setEditable(false);
/* 366 */     Font licenseAreaFont = UIManager.getFont("TextField.font");
/* 367 */     if (licenseAreaFont != null) this.licenseArea.setFont(licenseAreaFont);
/* 368 */     this.licenseArea.setLineWrap(true);
/* 369 */     this.licenseArea.setRows(5);
/* 370 */     scrollPane1.setViewportView(this.licenseArea);
/* 371 */     JScrollPane scrollPane2 = new JScrollPane();
/* 372 */     this.rootPanel.add(scrollPane2, new GridConstraints(6, 1, 1, 1, 0, 3, 5, 0, null, null, null, 0, false));
/* 373 */     this.requestArea = new JTextArea();
/* 374 */     Font requestAreaFont = UIManager.getFont("TextField.font");
/* 375 */     if (requestAreaFont != null) this.requestArea.setFont(requestAreaFont);
/* 376 */     this.requestArea.setLineWrap(true);
/* 377 */     this.requestArea.setRows(5);
/* 378 */     scrollPane2.setViewportView(this.requestArea);
/* 379 */     JScrollPane scrollPane3 = new JScrollPane();
/* 380 */     this.rootPanel.add(scrollPane3, new GridConstraints(7, 1, 1, 1, 0, 3, 5, 0, null, null, null, 0, false));
/* 381 */     this.responseArea = new JTextArea();
/* 382 */     Font responseAreaFont = UIManager.getFont("TextField.font");
/* 383 */     if (responseAreaFont != null) this.responseArea.setFont(responseAreaFont);
/* 384 */     this.responseArea.setLineWrap(true);
/* 385 */     this.responseArea.setRows(8);
/* 386 */     scrollPane3.setViewportView(this.responseArea);
/* 387 */     JLabel label5 = new JLabel();
/* 388 */     label5.setRequestFocusEnabled(false);
/* 389 */     label5.setText("Loader Command:");
/* 390 */     this.rootPanel.add(label5, new GridConstraints(3, 0, 1, 1, 4, 0, 0, 0, null, null, null, 0, false));
/* 391 */     JLabel label6 = new JLabel();
/* 392 */     label6.setText("1. Run Burp Suite Pro with the loader specified in the bootclasspath");
/* 393 */     this.rootPanel.add(label6, new GridConstraints(0, 1, 1, 1, 8, 0, 0, 0, null, null, null, 0, false));
/* 394 */     JLabel label7 = new JLabel();
/* 395 */     label7.setText("2. Register using manual activation");
/* 396 */     this.rootPanel.add(label7, new GridConstraints(1, 1, 1, 1, 8, 0, 0, 0, null, null, null, 0, false));
/* 397 */     JLabel label8 = new JLabel();
/* 398 */     label8.setText("3. On subsequent runs you must execute burpsuite with the loader otherwise it will become unregistered");
/* 399 */     this.rootPanel.add(label8, new GridConstraints(2, 1, 1, 1, 8, 0, 0, 0, null, null, null, 0, false));
/* 400 */     JLabel label9 = new JLabel();
/* 401 */     label9.setText("Instructions:");
/* 402 */     this.rootPanel.add(label9, new GridConstraints(1, 0, 1, 1, 4, 0, 0, 0, null, null, null, 0, false));
/* 403 */     JPanel panel1 = new JPanel();
/* 404 */     panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
/* 405 */     this.rootPanel.add(panel1, new GridConstraints(3, 1, 1, 1, 0, 3, 3, 3, null, null, null, 0, false));
/* 406 */     this.loaderTextField = new JTextField();
/* 407 */     this.loaderTextField.setEditable(false);
/* 408 */     this.loaderTextField.setHorizontalAlignment(0);
/* 409 */     this.loaderTextField.setText("");
/* 410 */     panel1.add(this.loaderTextField, new GridConstraints(0, 0, 1, 1, 0, 1, 4, 0, null, new Dimension(150, -1), null, 0, false));
/* 411 */     this.runButton = new JButton();
/* 412 */     this.runButton.setEnabled(false);
/* 413 */     this.runButton.setText("Run");
/* 414 */     this.runButton.setVerticalAlignment(1);
/* 415 */     panel1.add(this.runButton, new GridConstraints(0, 1, 1, 1, 4, 0, 0, 0, null, null, null, 0, false));
/* 416 */     label1.setLabelFor(this.nameTextField);
/* 417 */     label2.setLabelFor(this.licenseArea);
/* 418 */     label3.setLabelFor(this.requestArea);
/* 419 */     label4.setLabelFor(this.responseArea);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public JComponent $$$getRootComponent$$$()
/*     */   {
/* 426 */     return this.rootPanel;
/*     */   }
/*     */ }


/* Location:              /Users/scanf/tools/Burp_Suite_Pro_v1.7.31/burp-loader-keygen.jar!/enjoy/reversing/me/KeygenDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
