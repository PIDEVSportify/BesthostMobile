/*
 * Copyright comment here
 */
package com.wefeel.QRMaker;

import com.codename1.ui.Image;
import com.google.zxing.qrcode.ErrorCorrectionLevel;
import com.google.zxing.qrcode.QRCode;
import com.google.zxing.qrcode.QREncoder;

/**
 * This is a demo class to help you get started building a library
 *
 * @author Your name here
 */
public class QRMaker {
    /**
     * Create a QR code image from given String
     */
    public static Image QRCode(String s) {
        QRCode email = QREncoder.encode(s, ErrorCorrectionLevel.H);
        byte[][] bm = email.getMatrix().getArray();
        
        int d = bm.length; //Dimension in pixel
        int[] a = new int[d * d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                a[i * d + j] = ((bm[i][j] - 1) & 0x00FFFFFF) | 0x99000000; //-> Appropriate transparency
            }
        }
        Image img = Image.createImage(a, d, d);
        return img; //Not scaled yet
    }
}
