package org.kjtw.main;

public class Converter {
    public static byte[] byteconvert(Byte[] data) {
        if (data != null)
        {
            byte[] recall = new byte[data.length];
            for (int i=0; i < data.length; i++)
            {
                recall[i] = data[i];
            }
            return recall;
        }
        return null;
    }

}
