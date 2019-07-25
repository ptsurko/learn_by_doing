package org.sample.utils;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
    @Test
    public void testUtilsPow() {
        assertEquals(25, Utils.Pow(5));
    }

    @Test
    public void testUtilsPow2() {
        assertEquals(36, Utils.Pow(6));
    }

//    public void testUtilsPow3() {
//        assertEquals(37, Utils.Pow(6));
//    }
}