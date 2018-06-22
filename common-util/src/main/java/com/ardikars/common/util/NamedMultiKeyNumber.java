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

package com.ardikars.common.util;

import java.util.Objects;

/**
 * Common base class for dynamic named multi number key (enum like).
 * @param <T> multiple number key.
 * @param <U> named multiple number key.
 */
public abstract class NamedMultiKeyNumber<T extends MultiKeyNumber, U extends NamedMultiKeyNumber<T, ?>> {

    private final T value;
    private final String name;

    protected NamedMultiKeyNumber(T multiKey, String name) {
        this.value = multiKey;
        this.name = name;
    }

    /**
     * Returns the number of this {@code NamedMultiKeyNumber} object.
     * @return returns the multi key number of this {@code NamedMultiKeyNumber} object.
     */
    public T getValue() {
        return value;
    }

    /**
     * Returns the name of this {@code NamedMultiKeyNumber} object.
     * @return returns the name of this {@code NamedMultiKeyNumber} object.
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NamedMultiKeyNumber)) return false;
        NamedMultiKeyNumber<?, ?> that = (NamedMultiKeyNumber<?, ?>) o;
        return Objects.equals(getValue(), that.getValue()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue(), getName());
    }

}
