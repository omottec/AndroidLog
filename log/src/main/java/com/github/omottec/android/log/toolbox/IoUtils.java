package com.github.omottec.android.log.toolbox;


import java.io.Closeable;
import java.io.IOException;

/**
 * Created by qinbingbing on 11/22/16.
 */

public final class IoUtils {
    public static void close(Closeable... cloneables) {
        if (cloneables != null && cloneables.length > 0) {
            for (Closeable cloneable : cloneables)
                if (cloneable != null)
                    try {
                        cloneable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
        }
    }
}
