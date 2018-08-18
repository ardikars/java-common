/**
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ardikars.common.net;

import com.ardikars.common.annotation.Incubating;

import java.net.InterfaceAddress;
import java.net.SocketException;
import java.util.*;

@Incubating
public class NetworkInterface {

    private int index;
    private String name;
    private String displayName;
    private MacAddress hardwareAddress;
    private Collection<InetAddress> addresses;
    private int mtu;
    private boolean pointToPoint;
    private boolean virtual;
    private boolean loopback;
    private boolean up;
    private NetworkInterface parent;
    private Collection<NetworkInterface> childs;

    public NetworkInterface(final Builder builder) {
        this.index = builder.index;
        this.name = builder.name;
        this.displayName = builder.displayName;
        this.hardwareAddress = builder.hardwareAddress;
        this.addresses = builder.addresses;
        this.mtu = builder.mtu;
        this.pointToPoint = builder.pointToPoint;
        this.virtual = builder.virtual;
        this.loopback = builder.loopback;
        this.up = builder.up;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public MacAddress getHardwareAddress() {
        return hardwareAddress;
    }

    public Collection<InetAddress> getAddresses() {
        return addresses;
    }

    public int getMtu() {
        return mtu;
    }

    public boolean isPointToPoint() {
        return pointToPoint;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public boolean isLoopback() {
        return loopback;
    }

    public boolean isUp() {
        return up;
    }

    public NetworkInterface getParent() {
        return parent;
    }

    public Collection<NetworkInterface> getChilds() {
        return childs;
    }

    public void addChild(NetworkInterface networkInterface) {
        if (this.childs == null) {
            this.childs = new HashSet<>();
        }
        this.childs.add(networkInterface);
    }

    public static class Builder implements com.ardikars.common.util.Builder<NetworkInterface, Void> {

        private int index;
        private String name;
        private String displayName;
        private MacAddress hardwareAddress;
        private Collection<InetAddress> addresses;
        private int mtu;
        private boolean pointToPoint;
        private boolean virtual;
        private boolean loopback;
        private boolean up;
        private NetworkInterface parent;
        private Collection<NetworkInterface> childs;

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder hardwareAddress(MacAddress hardwareAddress) {
            this.hardwareAddress = hardwareAddress;
            return this;
        }

        public Builder addresses(Collection<InetAddress> addresses) {
            this.addresses = addresses;
            return this;
        }

        public Builder mtu(int mtu) {
            this.mtu = mtu;
            return this;
        }

        public Builder pointToPoint(boolean pointToPoint) {
            this.pointToPoint = pointToPoint;
            return this;
        }

        public Builder virtual(boolean virtual) {
            this.virtual = virtual;
            return this;
        }

        public Builder loopback(boolean loopback) {
            this.loopback = loopback;
            return this;
        }

        public Builder up(boolean up) {
            this.up = up;
            return this;
        }

        public Builder parent(NetworkInterface parent) {
            this.parent = parent;
            return this;
        }

        public Builder childs(Collection<NetworkInterface> childs) {
            this.childs = childs;
            return this;
        }

        @Override
        public NetworkInterface build() {
            return new NetworkInterface(this);
        }

        @Override
        public NetworkInterface build(Void value) {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public String toString() {
        return "NetworkInterface{" +
                "index=" + index +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", hardwareAddress=" + hardwareAddress +
                ", addresses=" + addresses +
                ", mtu=" + mtu +
                ", pointToPoint=" + pointToPoint +
                ", virtual=" + virtual +
                ", loopback=" + loopback +
                ", up=" + up +
                ", parent=" + parent +
                ", childs=" + childs +
                '}';
    }

    public static Collection<NetworkInterface> getNetworkInterfaces() throws SocketException {
        Enumeration<java.net.NetworkInterface> networkInterfaces = java.net.NetworkInterface.getNetworkInterfaces();
        Set<NetworkInterface> networkInterfaceSet = new HashSet<>();
        while (networkInterfaces.hasMoreElements()) {
            java.net.NetworkInterface networkInterface = networkInterfaces.nextElement();
            byte[] hardwareAddress = networkInterface.getHardwareAddress();
            Set<InetAddress> parentAddreses = new HashSet<>();
            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                if (interfaceAddress.getAddress() instanceof java.net.Inet4Address) {
                    parentAddreses.add(Inet4Address.valueOf(interfaceAddress.getAddress().getAddress()));
                } else if (interfaceAddress.getAddress() instanceof java.net.Inet6Address){
                    parentAddreses.add(Inet6Address.valueOf(interfaceAddress.getAddress().getAddress()));
                }
            }
            NetworkInterface parent = new Builder()
                    .index(networkInterface.getIndex())
                    .name(networkInterface.getName())
                    .displayName(networkInterface.getDisplayName())
                    .hardwareAddress(hardwareAddress == null ? MacAddress.ZERO : MacAddress.valueOf(hardwareAddress))
                    .addresses(parentAddreses)
                    .mtu(networkInterface.getMTU())
                    .pointToPoint(networkInterface.isPointToPoint())
                    .virtual(networkInterface.isVirtual())
                    .loopback(networkInterface.isLoopback())
                    .up(networkInterface.isUp())
                    .parent(null)
                    .childs(null)
                    .build();
            Enumeration<java.net.NetworkInterface> childs = networkInterface.getSubInterfaces();
            while (childs.hasMoreElements()) {
                java.net.NetworkInterface childNetworkInterface = childs.nextElement();
                hardwareAddress = childNetworkInterface.getHardwareAddress();
                Set<InetAddress> childAddresses = new HashSet<>();
                for (InterfaceAddress interfaceAddress : childNetworkInterface.getInterfaceAddresses()) {
                    if (interfaceAddress.getAddress() instanceof java.net.Inet4Address) {
                        childAddresses.add(Inet4Address.valueOf(interfaceAddress.getAddress().getAddress()));
                    } else if (interfaceAddress.getAddress() instanceof java.net.Inet6Address){
                        childAddresses.add(Inet6Address.valueOf(interfaceAddress.getAddress().getAddress()));
                    }
                }
                NetworkInterface child = new Builder()
                        .index(childNetworkInterface.getIndex())
                        .name(childNetworkInterface.getName())
                        .displayName(childNetworkInterface.getDisplayName())
                        .hardwareAddress(hardwareAddress == null ? MacAddress.ZERO : MacAddress.valueOf(hardwareAddress))
                        .addresses(childAddresses)
                        .mtu(childNetworkInterface.getMTU())
                        .pointToPoint(childNetworkInterface.isPointToPoint())
                        .virtual(childNetworkInterface.isVirtual())
                        .loopback(childNetworkInterface.isLoopback())
                        .up(childNetworkInterface.isUp())
                        .parent(parent)
                        .childs(null)
                        .build();
                parent.addChild(child);
            }
            networkInterfaceSet.add(parent);
        }
        return networkInterfaceSet;
    }

}
