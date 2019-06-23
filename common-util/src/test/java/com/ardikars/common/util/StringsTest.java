package com.ardikars.common.util;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class StringsTest extends BaseTest {

    private static final byte[] byteData = new byte[] { (byte) 10, (byte) 43, (byte) 45, (byte) 2, (byte) 5 };
    private static final short[] shortData = new short[] { (short) 476, (short) 45832, (short) 455632, (short) 45522, (short) 45432 };
    private static final int[] intData = new int[] { 204743647, 2047483147, 2047883646, 2046483647, 2047483645 };
    private static final float[] floatData = new float[] { 204743647.1F, 2047483147.2F, 2047883646.3F, 2046483647.4F, 2047483645.5F };
    private static final long[] longData = new long[] { 204543647L, 2047478347L, 2043424146L, 223543647L, 263453645L };
    private static final double[] doubleData = new double[] { 204543647.1D, 2047478347.2D, 2043424146.3D, 223543647.4D, 263453645.5D };
    private static final String stringData = "Rock The Party!";

    @Test
    public void byteToHexString() {
        assertEquals("0a", Strings.toHexString(byteData[0]));
    }

    @Test
    public void byteArrayToHexString() {
        assertEquals("0a2b2d0205", Strings.toHexString(byteData));
    }

    @Test
    public void byteArrayToHexStringWithRange() {
        assertEquals("2b2d02", Strings.toHexString(byteData, 1, byteData.length - 2));
    }

    @Test
    public void shortToHexString() {
        assertEquals("01dc", Strings.toHexString(shortData[0]));
    }

    @Test
    public void shortArrayToHexString() {
        assertEquals("01dcb308f3d0b1d2b178", Strings.toHexString(shortData));
    }

    @Test
    public void shortArrayToHexStringWithRange() {
        assertEquals("b308f3d0b1d2", Strings.toHexString(shortData, 1, shortData.length - 2));
    }

    @Test
    public void intToHexString() {
        assertEquals("0c3423df", Strings.toHexString(intData[0]));
    }

    @Test
    public void intArrayToHexString() {
        assertEquals("0c3423df7a0a1d0b7a10397e79fadcbf7a0a1efd", Strings.toHexString(intData));
    }

    @Test
    public void intArrayToHexStringWithRange() {
        assertEquals("7a0a1d0b7a10397e79fadcbf", Strings.toHexString(intData, 1, intData.length - 2));
    }

    @Test
    public void floatToHexString() {
        assertEquals("00x1.86847cp27", Strings.toHexString(floatData[0]));
    }

    @Test
    public void floatArrayToHexString() {
        assertEquals("00x1.86847cp2700x1.e82874p3000x1.e840e6p3000x1.e7eb72p3000x1.e8287cp30", Strings.toHexString(floatData));
    }

    @Test
    public void floatArrayToHexStringWithRange() {
        assertEquals("00x1.e82874p3000x1.e840e6p3000x1.e7eb72p30", Strings.toHexString(floatData, 1, floatData.length - 2));
    }

    @Test
    public void longToHexString() {
        assertEquals("0c31169f", Strings.toHexString(longData[0]));
    }

    @Test
    public void longArrayToHexString() {
        assertEquals("0c31169f7a0a0a4b79cc2d920d53015f0fb3fbcd", Strings.toHexString(longData));
    }

    @Test
    public void longArrayToHexStringWithRange() {
        assertEquals("7a0a0a4b79cc2d920d53015f0fb3fbcd", Strings.toHexString(longData, 1, longData.length - 1));
    }

    @Test
    public void doubleToHexString() {
        assertEquals("0x1.8622d3e333333p27", Strings.toHexString(doubleData[0]));
    }

    @Test
    public void doubleArrayToHexString() {
        assertEquals("0x1.8622d3e333333p270x1.e828292cccccdp300x1.e730b64933333p300x1.aa602becccccdp270x1.f67f79bp27", Strings.toHexString(doubleData));
    }

    @Test
    public void doubleArrayToHexStringWithRange() {
        assertEquals("0x1.e828292cccccdp300x1.e730b64933333p300x1.aa602becccccdp270x1.f67f79bp27", Strings.toHexString(doubleData, 1, doubleData.length - 1));
    }

    @Test
    public void stringToHexString() {
        assertEquals("526f636b2054686520506172747921", Strings.toHexString(stringData));
    }

    @Test
    public void toStringBuilderTest() {
        assertEquals("{\"as\":\"7b\",\"as\":\"7ffb\",\"as\":\"7ffffffb\",\"as\":\"0x1.0p31\",\"as\":\"7ffffffffffffffb\",\"as\":\"0x1.0p63\"}",
            Strings.toStringJsonBuilder(this)
                .add("as", new byte[] { 123 })
                .add("as", new short[] { 32763 })
                .add("as", new int[] { 2147483643 })
                .add("as", new float[] { 2147483643.2F })
                .add("as", new long[] { 9223372036854775803L })
                .add("as", new double[] { 9223372036854775803.6D })
                .toString()
        );
        assertEquals("StringsTest{as=7b,as=7ffb,as=7ffffffb,as=0x1.0p31,as=7ffffffffffffffb,as=0x1.0p63}",
            Strings.toStringBuilder(this)
                    .add("as", new byte[] { 123 })
                    .add("as", new short[] { 32763 })
                    .add("as", new int[] { 2147483643 })
                    .add("as", new float[] { 2147483643.2F })
                    .add("as", new long[] { 9223372036854775803L })
                    .add("as", new double[] { 9223372036854775803.6D })
                    .toString()
        );
    }

}
