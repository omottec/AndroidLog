package com.github.omottec.android.log.toolbox;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by qinbingbing on 11/22/16.
 */

public class AesCipher {

    public static void decrypt(File file) {
        if (!file.exists()) return;
        String fileName;
        if (file.getName().startsWith("c"))
            fileName = "d" + file.getName().substring(1);
        else
            fileName = "d" + file.getName();
        FileInputStream fio = null;
        FileOutputStream fos = null;
        try {
            fio = new FileInputStream(file);
            fos = new FileOutputStream(new File(file.getParent(), fileName), true);
            crypt(fio, fos, Cipher.DECRYPT_MODE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            IoUtils.close(fio, fos);
        }
    }

    /**
     *
     * @param in
     * @param out
     * @param opmode javax.crypto.Cipher#ENCRYPT_MODE or javax.crypto.Cipher#DECRYPT_MODE
     */
    public static void crypt(InputStream in, OutputStream out, int opmode) {
        try {
            byte[] encoded = "qinbingbingmagic".getBytes();
            SecretKey key = new SecretKeySpec(encoded, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(opmode, key);
            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(blockSize);
            byte[] inBytes = new byte[blockSize];
            byte[] outBytes = new byte[outputSize];
            int inLength = 0;
            boolean more = true;
            while (more) {
                inLength = in.read(inBytes);
                if (inLength == blockSize) {
                    int outLength = cipher.update(inBytes, 0, inLength, outBytes);
                    out.write(outBytes, 0, outLength);
                } else
                    more = false;
            }
            if (inLength > 0)
                outBytes = cipher.doFinal(inBytes, 0, inLength);
            else
                outBytes = cipher.doFinal();
            out.write(outBytes);
        } catch (ShortBufferException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
