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

import com.ardikars.common.annotation.Helper;
import com.ardikars.common.annotation.Immutable;
import com.ardikars.common.util.Validate;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * An immutable representation of a host name, ip address, and port.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Helper
@Immutable
public final class HostAndPort implements Serializable {

    /**
     * @see <a href="https://www.regextester.com/93928"></a>
     */
    public static final Pattern DOMAIN_PATTERN = Pattern.compile("^(?!:\\/\\/)([a-zA-Z0-9-_]+\\.)*[a-zA-Z0-9][a-zA-Z0-9-_]+\\.[a-zA-Z]{2,11}?$");

    private final String hostName;
    private final InetAddress hostAddress;
    private final int port;

    private HostAndPort(Builder builder) {
        Validate.notIllegalArgument(builder.hostName != null,
                new IllegalArgumentException("Hostname should be not null."));
        Validate.notIllegalArgument(builder.hostAddress != null,
                new IllegalArgumentException("Address sould be not null."));
        Validate.notIllegalArgument(builder.port >= 0 && builder.port < 65536,
                new IllegalArgumentException("Invalid port."));
        if (!DOMAIN_PATTERN.matcher(builder.hostName).matches()) {
            throw new IllegalArgumentException("Invalid hostname.");
        }
        this.hostName = builder.hostName;
        this.hostAddress = builder.hostAddress;
        this.port = builder.port;
    }

    /**
     * Get host name.
     * @return returns host name of this {@code Host} object.
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Get host address.
     * @return returns host address of this {@code Host} object.
     */
    public InetAddress getHostAddress() {
        return hostAddress;
    }

    /**
     * Get host port.
     * @return returns host port of this {@code Host} object.
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns host name and port with given prefix.
     * @param prefix prefix.
     * @return returns host name and port with given prefix of this {@code Host} object.
     */
    public String hostNameWithPort(String prefix) {
        return Validate.nullPointer(prefix, "") + hostName + ":" + port;
    }

    /**
     * Returns host name and port.
     * @return returns host name and port of this {@code Host} object.
     */
    public String hostNameWithPort() {
        return hostName + ":" + port;
    }

    /**
     * Returns host address with given prefix.
     * @param prefix prefix.
     * @return returns host address with given prefix of this {@code Host} object.
     */
    public String hostAddress(String prefix) {
        return Validate.nullPointer(prefix, "") + getHostAddress().toString();
    }

    /**
     * Returns host address and port with given prefix.
     * @param prefix prefix.
     * @return returns host address and port with given prefix of this {@code Host} object.
     */
    public String hostAddressWithPort(String prefix) {
        return Validate.nullPointer(prefix, "") + hostAddress.toString() + ":" + port;
    }

    /**
     * Returns host address and port.
     * @return returns host address and port of this {@code Host} object.
     */
    public String hostAddressWithPort() {
        return hostAddress.toString() + ":" + port;
    }

    /**
     * Returns host name with given prefix.
     * @param prefix prefix.
     * @return returns host name with given prefix of this {@code Host} object.
     */
    public String hostName(String prefix) {
        return Validate.nullPointer(prefix, "") + getHostName();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Host{");
        sb.append("hostName='").append(hostName).append('\'');
        sb.append(", hostAddress=").append(hostAddress);
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Host builder class.
     */
    public static final class Builder implements com.ardikars.common.util.Builder<HostAndPort, Void> {

        private String hostName;
        private InetAddress hostAddress;
        private int port;

        /**
         * Host name.
         * @param hostName host name.
         * @return returns this {@code Builder} object.
         */
        public Builder hostName(String hostName) {
            this.hostName = hostName;
            return this;
        }

        /**
         * Host address.
         * @param address host address.
         * @return returns this {@code Builder} object.
         */
        public Builder address(InetAddress address) {
            this.hostAddress = address;
            return this;
        }

        /**
         * Host port.
         * @param port port.
         * @return returns this {@code Builder} object.
         */
        public Builder port(int port) {
            this.port = port;
            return this;
        }

        /**
         * Build {@code Host} object.
         * @return returns {@code Host} object.
         */
        @Override
        public HostAndPort build() {
            return new HostAndPort(this);
        }

        @Override
        public HostAndPort build(Void value) {
            throw new UnsupportedOperationException();
        }

    }

}
