package com.sbs.internetbanking.PKI;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

//Tested in jdk1.8.0_40
public class BankCertificateGeneration {
    public static void main(String[] args) {
        try {

            CertAndKeyGen keyGen = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
            keyGen.generate(1024);
            X509Certificate[] chain = new X509Certificate[1];
            chain[0] = keyGen.getSelfCertificate(new X500Name("CN=BankOfTempe"), (long) 365 * 24 * 3600);
            serializeSecurityobject(keyGen.getPrivateKey());
            serializeSecurityobject(chain[0]);
            serializeSecurityobject(keyGen.getPublicKey());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void serializeSecurityobject(Object key) {
        String fileName = "";
        try {
            if (key instanceof PrivateKey) {
                fileName = "d:\\PrivateKey.ser";
            } else if (key instanceof PublicKey) {
                fileName = "d:\\PublicKey.ser";
            } else if (key instanceof X509Certificate) {
                fileName = "d:\\Certificate.ser";
            }
            FileOutputStream fout = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(key);
            oos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
