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

import com.ardikars.common.annotation.Immutable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Multiple Number keys for {@code java.util.Map}
 * @see java.util.Map
 * @see NamedMultiKey
 * @param <K> key type.
 */
@Immutable
public class MultiKeyNumber<K extends Number> extends MultiKey<K> implements Serializable {

    private static final long serialVersionUID = -7486266343955776290L;

    private MultiKeyNumber(Set<K> keys) {
        super(keys);
    }

}
