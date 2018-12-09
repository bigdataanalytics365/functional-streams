/*
 *                     functional-streams
 *              Copyright (C) 2018 Varun Anand
 *
 * This file is part of functional-streams.
 *
 * functional-streams is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 *
 * functional-streams is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.littlesaints.protean.functions.streams;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamSourceTest {

    @Test(timeout = 5000)
    public void testStreamClose() {
        final AtomicInteger atomicInteger = new AtomicInteger();
        final Stream<Integer> source = StreamSource.of(atomicInteger::incrementAndGet).get();
        final If<Integer, Void> iff = If.<Integer, Void>test(n -> n.equals(100)).then(n -> {
            source.close();
            return null;
        });
        source.forEach(iff::apply);
    }

    @Test(timeout = 5000)
    public void testStreamComplete() {
        final AtomicInteger atomicInteger = new AtomicInteger();
        final Stream<Integer> source = StreamSource.of(atomicInteger::incrementAndGet, () -> atomicInteger.intValue() < 100).get();
        source.forEach(n -> {});
    }
}