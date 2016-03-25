package com.caozhiliang.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author CZL
 * @time 2016-03-14 22:12
 */
public class StreamUtils {

    public static String readFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }

        String result = out.toString();
        in.close();
        out.close();
        return result;
    }
}
