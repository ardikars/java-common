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

import com.ardikars.common.annotation.Immutable;
import com.ardikars.common.util.Validate;

import java.util.Arrays;

/**
 * This class represents an Internet Protocol version 4 (IPv4) address.
 * Defined by <a href="https://tools.ietf.org/html/rfc790">IPv4 Address</a>
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Immutable
public final class Inet4Address extends InetAddress {

	/**
	 * IPv4 Address (0.0.0.0).
	 */
	public static final Inet4Address ZERO = valueOf(0);

	/**
	 * IPv4 Loopback address (127.0.0.1).
	 */
	public static final Inet4Address LOCALHOST = valueOf("127.0.0.1");

	/**
	 * IPv4 Address Length.
	 */
	public static final int IPV4_ADDRESS_LENGTH = 4;
	
	private byte[] address = new byte[Inet4Address.IPV4_ADDRESS_LENGTH];

	private Inet4Address() {

	}

	private Inet4Address(final byte[] address) {
		Validate.nullPointer(address);
		Validate.notIllegalArgument(address.length == IPV4_ADDRESS_LENGTH);
		this.address = address;
	}

	/**
	 * Determines the IPv4 address.
	 * @param stringAddress ipv4 string address.
	 * @return an IPv4 address.
	 */
	public static Inet4Address valueOf(String stringAddress) {
		stringAddress = Validate.nullPointer(stringAddress, "0.0.0.0");
		String[] parts = stringAddress.split("\\.");
		byte[] result = new byte[parts.length];
		Validate.notIllegalArgument(result.length == IPV4_ADDRESS_LENGTH);
		for (int i = 0; i < result.length; i++) {
			Validate.notIllegalArgument(parts[i] != null || parts[i].length() != 0);
			Validate.notIllegalArgument(!(parts[i].length() > 1 && parts[i].startsWith("0")));
			result[i] = Integer.valueOf(parts[i]).byteValue();
			Validate.notIllegalArgument((result[i] & 0xff) <= 0xff);
		}
		return Inet4Address.valueOf(result);
	}

	/**
	 * Determines the IPv4 address.
	 * @param bytesAddress ipv4 bytes address.
	 * @return an IPv4 address.
	 */
	public static Inet4Address valueOf(final byte[] bytesAddress) {
		return new Inet4Address(bytesAddress);
	}

	/**
	 * Determines the IPv4 address.
	 * @param intAddress ipv4 int address.
	 * @return an IPv4 address.
	 */
	public static Inet4Address valueOf(final int intAddress) {
		return new Inet4Address(new byte[]{(byte) (intAddress >>> 24),
				(byte) (intAddress >>> 16), (byte) (intAddress >>> 8),
				(byte) intAddress});
	}

	@Override
	public boolean isMulticastAddress() {
		return ((toInt() & 0xf0000000) == 0xe0000000);
	}

	@Override
	public boolean isAnyLocalAddress() {
		return toInt() == 0;
	}

	/**
	 * Returns true if address is 127.x.x.x, false otherwise.
	 * @return returns true if loopback address, false otherwise.
	 */
	@Override
	public boolean isLoopbackAddress() {
		return ((address[0] & 0xff) == 127);
	}

	@Override
	public boolean isLinkLocalAddress() {
		return (((address[0] & 0xff) == 169)
				&& ((address[1] & 0xff) == 254));
	}

	/**
	 * refer to RFC 1918
	 * 10/8 prefix
	 * 172.16/12 prefix
	 * 192.168/16 prefix
	 */
	@Override
	public boolean isSiteLocalAddress() {
		return ((address[0] & 0xff) == 10)
				|| ((address[0] & 0xff) == 172)
				&& ((address[1] & 0xff) == 16)
				|| ((address[0] & 0xff) == 192)
				&& ((address[1] & 0xff) == 168);
	}

	/**
	 * 224.0.1.0 to 238.255.255.255
	 */
	@Override
	public boolean isMcGlobal() {
		return ((address[0] & 0xff) >= 224
				&& (address[0] & 0xff) <= 238)
				&& !((address[0] & 0xff) == 224
					&& address[1] == 0
					&& address[2] == 0);
	}

	/**
	 * Unless ttl == 0
	 */
	@Override
	public boolean isMcNodeLocal() {
		return false;
	}

	/**
	 * 224.0.0/24 prefix and ttl == 1
	 */
	@Override
	public boolean isMcLinkLocal() {
		return (((address[0] & 0xff) == 224)
				&& ((address[1] & 0xff) == 0)
				&& ((address[2] & 0xff) == 0));
	}

	/**
	 * 239.255/16 prefix or ttl &lt; 32
	 */
	@Override
	public boolean isMcSiteLocal() {
		return (((address[0] & 0xff) == 239)
				&& ((address[1] & 0xff) == 255));
	}

	/**
	 * 239.192 - 239.195
	 */
	@Override
	public boolean isMcOrgLocal() {
		return (((address[0] & 0xff) == 239)
				&& ((address[1] & 0xff) >= 192)
				&& ((address[1] & 0xff) <= 195));
	}

	/**
	 * Returns the int IPv4 address of this {@code Inet4Address}
	 * object.
	 * @return  the int IPv4 address of this object.
	 */
	public int toInt() {
		int ip = 0;
		for (int i = 0; i < Inet4Address.IPV4_ADDRESS_LENGTH; i++) {
			final int t = (this.address[i] & 0xff) << (3 - i) * 8;
			ip |= t;
		}
		return ip;
	}

	/**
	 * Returns the raw IPv4 address of this {@code Inet4Address}
	 * object. The result is in network byte order: the highest order
	 * byte of the address is in {@code toBytes()[0]}.
	 *
	 * @return  the raw IPv4 address of this object.
	 */
	public byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Inet4Address that = (Inet4Address) o;
		return Arrays.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(address);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.address[0] & 0xff).append(".");
		sb.append(this.address[1] & 0xff).append(".");
		sb.append(this.address[2] & 0xff).append(".");
		sb.append(this.address[3] & 0xff);
		return sb.toString();
	}

	/**
	 * Returns the raw IPv4 address of this {@code Inet4Address}
	 * object. The result is in network byte order: the highest order
	 * byte of the address is in {@code toBytes()[0]}.
	 *
	 * @return  the raw IPv4 address of this object.
	 */
	@Override
	public byte[] getAddress() {
		return Arrays.copyOf(this.address, this.address.length);
	}

}
