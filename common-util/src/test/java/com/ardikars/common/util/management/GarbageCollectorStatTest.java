package com.ardikars.common.util.management;

import com.ardikars.common.util.BaseTest;
import com.ardikars.common.util.Platforms;
import org.junit.Test;

public class GarbageCollectorStatTest extends BaseTest {

    @Test
    public void t() {
        if (Platforms.getJavaMojorVersion() > 8) {
            assert true;
        } else {
            Jvm jvm = Jvms.getJvm();
            System.out.println(jvm.isThreadCpuTimeEnabled());
        }
    }

}
