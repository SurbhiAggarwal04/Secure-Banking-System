package com.sbs.internetbanking.PKI;


import org.springframework.security.crypto.codec.Base64;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.PrivateKey;
import java.security.PublicKey;


public class Finals extends JFrame {
    PrivateKey privateKey;
    PublicKey publicKey;

    public Finals() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton button = new JButton("Encrypt");
        button.setFont(new Font("Courier", Font.PLAIN, 20));
        TextArea text1 = new TextArea();
        TextArea text2 = new TextArea();
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(text1, c);
        c.gridx = 1;
        c.gridy = 0;
        pane.add(button, c);
        c.gridx = 2;
        c.gridy = 0;
        pane.add(text2, c);
        JButton button2 = new JButton("Decrypt");
        button2.setFont(new Font("Courier", Font.PLAIN, 20));
        TextArea text21 = new TextArea();
        TextArea text22 = new TextArea();
        c.gridx = 0;
        c.gridy = 2;
        pane.add(text21, c);
        c.gridx = 1;
        c.gridy = 2;
        pane.add(button2, c);
        c.gridx = 2;
        c.gridy = 2;
        pane.add(text22, c);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        FileInputStream fis = new FileInputStream(selectedFile);
                        ObjectInputStream fin = new ObjectInputStream(fis);
                        PublicKey publicKey = (PublicKey) fin.readObject();

                        String plainText = text1.getText();
                        System.out.println("Text to be encrypted:" + plainText);
                        if (plainText != null && !(plainText.equals("")))
                            text2.setText(encrypt(plainText, publicKey));
                    }
                } catch (Exception ex) {

                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        FileInputStream fis = new FileInputStream(selectedFile);
                        ObjectInputStream fin = new ObjectInputStream(fis);
                        PrivateKey privateKey = (PrivateKey) fin.readObject();
                        String cipherText = text21.getText();
                        System.out.println("Text to be decrypted:" + cipherText);
                        if (cipherText != null && !(cipherText.equals("")))
                            text22.setText(decrypt(cipherText, privateKey));
                    }
                } catch (Exception ex) {

                }
            }
        });

    }

    public static void main(String[] args) {

        Finals gst = new Finals();

        gst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gst.pack();
        gst.setVisible(true);
    }

    private String encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        String str = "";
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            System.out.println(key.toString());
            cipherText = cipher.doFinal(text.getBytes());
            str = new BigInteger(cipherText).toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        return base64Encode(str);
    }

    private String decrypt(String encrypted, PrivateKey key) {
        byte[] dectyptedText = null;
        try {
            final Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            encrypted = base64Decode(encrypted);
            BigInteger bigEncrypted = new BigInteger(encrypted);
            byte text[] = bigEncrypted.toByteArray();
            dectyptedText = cipher.doFinal(text);
            return new String(dectyptedText);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "INVALID";
    }

    public String base64Encode(String token) {
        byte[] encodedBytes = Base64.encode(token.getBytes());
        return new String(encodedBytes, Charset.forName("UTF-8"));
    }


    public String base64Decode(String token) {
        byte[] decodedBytes = Base64.decode(token.getBytes());
        return new String(decodedBytes, Charset.forName("UTF-8"));
    }


}
