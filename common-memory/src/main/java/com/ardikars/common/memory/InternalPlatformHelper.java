//package com.ardikars.common.memory;
//
//final class InternalPlatformHelper {
//
//    private static final String OS_ARCH = "os.arch";
//    private static final String OS_NAME = "os.name";
//    private static final String JAVA_VM_NAME = "java.vm.name";
//
//
//    public enum Name {
//        WINDOWS, LINUX, ANDROID, FREEBSD, DARWIN, UNKNOWN;
//    }
//
//    public enum Architecture {
//        _32_BIT, _64_BIT;
//    }
//
//    private InternalPlatformHelper() {
//
//    }
//
//    private static Name name;
//    private static Architecture architecture;
//    private static int javaMajorVersion;
//
//    /**
//     * Get platform name.
//     * @return returns platform name.
//     */
//    public static Name getName() {
//        return name;
//    }
//
//    /**
//     * Get platform architecture.
//     * @return returns platform architecture.
//     */
//    public static Architecture getArchitecture() {
//        return architecture;
//    }
//
//    /**
//     * Returns true if Windows platform, false otherwise.
//     * @return returns true if windows platform, false otherwise.
//     */
//    public static boolean isWindows() {
//        return name == Name.WINDOWS;
//    }
//
//    /**
//     * Returns true if Linux platform, false otherwise.
//     * @return returns true if linux platform, false otherwise.
//     */
//    public static final boolean isLinux() {
//        return name == Name.LINUX;
//    }
//
//    /**
//     * Returns true if Android platform, false otherwise.
//     * @return returns true if android platform, false otherwise.
//     */
//    public static final boolean isAndroid() {
//        return name == Name.ANDROID;
//    }
//
//    /**
//     * Returns true if FreeBsd platform, false otherwise.
//     * @return returns true if FreeBsd platform, false otherwise.
//     */
//    public static final boolean isFreeBsd() {
//        return name == Name.FREEBSD;
//    }
//
//    /**
//     * Returns true if Darwin (MacOs) platform, false otherwise.
//     * @return returns true if Darwin (MacOs) platform, false otherwise.
//     */
//    public static final boolean isDarwin() {
//        return name == Name.DARWIN;
//    }
//
//    /**
//     * Returns true if 32-bit architecture, false otherwise.
//     * @return returns true if 32-bit architecture, false otherwise.
//     */
//    public static final boolean is32Bit() {
//        return architecture == Architecture._32_BIT;
//    }
//
//    /**
//     * Returns true if 64-bit architecture, false otherwise.
//     * @return returns true if 64-bit architecture, false otherwise.
//     */
//    public static final boolean is64Bit() {
//        return architecture == Architecture._64_BIT;
//    }
//
//    /**
//     * Returns true if Arm architecture, false otherwise.
//     * @return returns true if Arm architecture, false otherwise.
//     */
//    public static final boolean isArm() {
//        return InternalPropertyHelper.getProperty(OS_ARCH).toLowerCase().trim().startsWith("arm");
//    }
//
//    /**
//     * Returns true if Intel platform, false otherwise.
//     * @return returns true if Intel platform, false otherwise.
//     */
//    public static final boolean isIntel() {
//        final String arch = InternalPropertyHelper.getProperty(OS_ARCH).toLowerCase().trim();
//        return arch.startsWith("x86") || arch.startsWith("x64");
//    }
//
//    /**
//     * Returns true if Amd platform, false otherwise.
//     * @return returns true if Amd platform, false otherwise.
//     */
//    public static final boolean isAmd() {
//        return InternalPropertyHelper.getProperty(OS_ARCH).toLowerCase().trim().startsWith("amd");
//    }
//
//    /**
//     * Get Cpu Version.
//     * @return returns Cpu version.
//     */
//    public static final String getCpuVersion() {
//        final String version = InternalPropertyHelper.getProperty("os.version");
//        if (Character.isDigit(version.charAt(version.indexOf('v') + 1))) {
//            return String.valueOf("v" + version.charAt(version.indexOf('v') + 1));
//        }
//        return "";
//    }
//
//    /**
//     * Get java major version.
//     * @return returns java major version.
//     * @since 1.2.3
//     */
//    public static int getJavaMojorVersion() {
//        return javaMajorVersion;
//    }
//
//    static {
//        boolean isAndroid = false;
//        final String osName = InternalPropertyHelper.getProperty(OS_NAME).toUpperCase().trim();
//        final String osArch = InternalPropertyHelper.getProperty(OS_ARCH).toLowerCase().trim();
//        if (osName.startsWith("LINUX")) {
//            if ("DALVIK".equals(InternalPropertyHelper.getProperty(JAVA_VM_NAME).toUpperCase().trim())) {
//                name = Name.ANDROID;
//                isAndroid = true;
//            } else {
//                name = Name.LINUX;
//            }
//        } else if (osName.startsWith("WINDOWS")) {
//            name = Name.WINDOWS;
//        } else if (osName.startsWith("FREEBSD")) {
//            name = Name.FREEBSD;
//        } else if (osName.startsWith("MAC OS")) {
//            name = Name.DARWIN;
//        } else {
//            name = Name.UNKNOWN;
//        }
//
//        if ("i386".equals(osArch) || "i686".equals(osArch) || "i586".equals(osArch)) {
//            architecture = Architecture._32_BIT;
//        } else if ("x86_64".equals(osArch) || "amd64".equals(osArch) || "x64".equals(osArch)) {
//            architecture = Architecture._64_BIT;
//        }
//
//        if (isAndroid) {
//            javaMajorVersion = 6;
//        } else {
//            final String[] components = InternalPropertyHelper.getProperty("java.specification.version", "1.6").split("\\.");
//            final int[] version = new int[components.length];
//            for (int i = 0; i < components.length; i++) {
//                version[i] = Integer.parseInt(components[i]);
//            }
//
//            if (version[0] == 1) {
//                assert version[1] >= 6;
//                javaMajorVersion = version[1];
//            } else {
//                javaMajorVersion = version[0];
//            }
//        }
//    }
//
//}
