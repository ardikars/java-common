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
import com.ardikars.common.annotation.Mutable;
import com.ardikars.common.util.NamedNumber;
import com.ardikars.common.util.Validate;

import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class represents an Media Access Controll (MAC) address.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.0.0
 */
@Immutable
public final class MacAddress {

	/**
	 * MAC Address Length.
	 */
	public static final int MAC_ADDRESS_LENGTH = 6;

	/**
	 * Zero MAC Address (00:00:00:00:00:00).
	 */
	public static final MacAddress ZERO = valueOf("00:00:00:00:00:00");

	/**
	 * Dummy MAC Address (de:ad:be:ef:c0:fe).
	 */
	public static final MacAddress DUMMY = valueOf("de:ad:be:ef:c0:fe");

	/**
	 * Broadcast MAC Address (ff:ff:ff:ff:ff:ff).
	 */
	public static final MacAddress BROADCAST = valueOf("ff:ff:ff:ff:ff:ff");

	/**
	 * Multicast Address.
	 */
	public static final MacAddress IPV4_MULTICAST = valueOf("01:00:5e:00:00:00");

	public static final MacAddress IPV4_MULTICAST_MASK = valueOf("ff:ff:ff:80:00:00");
	
	private final byte[] address;
	
	private MacAddress(byte[] address) {
		Validate.nullPointer(address);
		Validate.notIllegalArgument(address.length == MAC_ADDRESS_LENGTH);
		this.address = Arrays.copyOf(address, MacAddress.MAC_ADDRESS_LENGTH);
	}

	public static MacAddress fromNicName(String name) throws SocketException {
		for (NetworkInterface networkInterface : NetworkInterface.getNetworkInterfaces()) {
			if (networkInterface.getName().equals(name)) {
				return networkInterface.getHardwareAddress();
			}
		}
		return null;
	}

	public static MacAddress fromNicIndex(int index) throws SocketException {
		for (NetworkInterface networkInterface : NetworkInterface.getNetworkInterfaces()) {
			if (networkInterface.getIndex() == index) {
				return networkInterface.getHardwareAddress();
			}
		}
		return null;
	}

	/**
	 * Determines the MacAddress address.
	 * @param stringAddress MAC string address.
	 * @return an Mac address object.
	 */
	public static MacAddress valueOf(String stringAddress) {
		stringAddress = Validate.nullPointer(stringAddress, "00:00:00:00:00:00");
		final String[] elements = stringAddress.split(":|-");
		Validate.notIllegalArgument(elements.length == MAC_ADDRESS_LENGTH);
		final byte[] b = new byte[MAC_ADDRESS_LENGTH];
		for (int i = 0; i < MAC_ADDRESS_LENGTH; i++) {
			final String element = elements[i];
			b[i] = (byte) Integer.parseInt(element, 16);
		}
		return new MacAddress(b);
	}

	/**
	 * Determines the MacAddress address.
	 * @param bytesAddress MAC bytes address.
	 * @return an Mac address object.
	 */
	public static MacAddress valueOf(final byte[] bytesAddress) {
		return new MacAddress(bytesAddress);
	}

	/**
	 * Determines the MacAddress address.
	 * @param longAddress MAC long address.
	 * @return an Mac address object.
	 */
	public static MacAddress valueOf(final long longAddress) {
		final byte[] bytes = new byte[] {
				(byte) (longAddress >> 40 & 0xff),
				(byte) (longAddress >> 32 & 0xff),
				(byte) (longAddress >> 24 & 0xff),
				(byte) (longAddress >> 16 & 0xff),
				(byte) (longAddress >> 8 & 0xff),
				(byte) (longAddress >> 0 & 0xff)};
		return new MacAddress(bytes);
	}

	/**
	 * Validate given mac string address.
	 * @param stringAddress mac string address.
	 * @return a {@code boolean} indicating if the stringAddress is a valid mac address; or false otherwise.
	 */
	public static boolean isValidAddress(final String stringAddress) {
		Validate.nullPointer(stringAddress);
		return Pattern.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$", stringAddress);
	}

	/**
	 *
	 * @return oui.
	 */
	public Oui getOui() {
		return Oui.valueOf(this);
	}

	/**
	 * Returns length of MAC Address.
	 * @return MAC Address length.
	 */
	public int length() {
		return this.address.length;
	}

	/**
	 * Returns bytes MAC Address.
	 * @return bytes MAC Address.
	 */
	public byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
	}

	/**
	 * Returning long MAC Address.
	 * @return long MAC Address.
	 */
	public long toLong() {
		long addr = 0;
		for (int i = 0; i < MAC_ADDRESS_LENGTH; i++) {
			long tmp = (this.address[i] & 0xffL) << (5 - i) * 8;
			addr |= tmp;
		}
		return addr;
	}

	/**
	 * Return true if Broadcast MAC Address.
	 * @return true if Broadcast MAC Address, false otherwise.
	 */
	public boolean isBroadcast() {
		for (final byte b : this.address) {
			if (b != -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return true if Multicast MAC Address.
	 * @return true if Multicast MAC Address, false otherwise.
	 */
	public boolean isMulticast() {
		if (this.isBroadcast()) {
			return false;
		}
		return (this.address[0] & 0x01) != 0;
	}

	/**
	 *
	 * @return returns true if the MAC address represented by this object is
	 *         a globally unique address; otherwise false.
	 */
	public boolean isGloballyUnique() {
		return (address[0] & 2) == 0;
	}

	/**
	 *
	 * @return true if the MAC address represented by this object is
	 *         a unicast address; otherwise false.
	 */
	public boolean isUnicast() {
		return (address[0] & 1) == 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MacAddress that = (MacAddress) o;

		return Arrays.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(address);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final byte b : this.address) {
			if (sb.length() > 0) {
				sb.append(':');
			}
			String hex = Integer.toHexString(b & 0xff);
			sb.append(hex.length() == 1 ? '0' + hex : hex);
		}
		return sb.toString();
	}

	@Mutable(blocking = true)
	public static final class Oui extends NamedNumber<Integer, Oui> {

		/**
		 * Cisco: 0x00000C
		 */
		public static final Oui CISCO_00000C
				= new Oui(0x00000C, "Cisco");

		/**
		 * IBM: 0x08005A
		 */
		public static final Oui IBM_08005A
				= new Oui(0x08005A, "IBM");

		/**
		 * Microsoft Corporation: 0x485073
		 */
		public static final Oui MICROSOFT_CORPORATION
				= new Oui(0x485073, "Microsoft Corporation");

		/**
		 * Default unknown OUI.
		 */
		public static final Oui UNKNOWN
				= new Oui(Integer.MAX_VALUE, "UNKNOWN");

		private static final Map<Integer, Oui> registry
				= new HashMap<Integer, Oui>();

		/**
		 *
		 * @param value value
		 * @param name name
		 */
		public Oui(final Integer value, final String name) {
			super(value, name);
			Validate.notIllegalArgument((value & 0xFF000000) == 0,
					new IllegalArgumentException(value
							+ " is invalid value. "
							+ " Value must be between 0 and 16777215."
					));
		}

		/**
		 *
		 * @param macAddress value
		 * @return a Oui object.
		 */
		public static Oui valueOf(final MacAddress macAddress) {
			int offset = 0;
			byte[] array = new byte[] { 0, macAddress.address[0], macAddress.address[1], macAddress.address[2] };
			int value = ((array[offset]) << (24))
					| ((0xFF & array[offset + 1]) << (16))
					| ((0xFF & array[offset + 2]) << (8))
					| ((0xFF & array[offset + 3]));
			Oui oui = registry.get(value);
			if (oui == null) {
				return new Oui(1, "UNKNOWN");
			}
			return oui;
		}

		/**
		 *
		 * @param version version
		 * @return a Oui object.
		 */
		public static Oui register(Oui version) {
			synchronized (Oui.class) {
				return registry.put(version.getValue(), version);
			}
		}

		static {
			registry.put(CISCO_00000C.getValue(), CISCO_00000C);
			registry.put(IBM_08005A.getValue(), IBM_08005A);
			registry.put(MICROSOFT_CORPORATION.getValue(), MICROSOFT_CORPORATION);
		}

	}

}
