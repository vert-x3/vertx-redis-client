/*
 * Copyright 2019 Red Hat, Inc.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * <p>
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * <p>
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 * <p>
 * You may elect to redistribute this code under either of these licenses.
 */
package io.vertx.redis.client.impl;

import io.vertx.redis.client.Response;

import java.util.*;

class Slots {

  // we need some randomness, it doesn't need
  // to be secure or unpredictable
  private static final Random RANDOM = new Random();

  static class Slot {
    private final int start;
    private final int end;
    private final String[] endpoints;

    Slot(int start, int end, int size) {
      this.start = start;
      this.end = end;
      endpoints = new String[size];
    }
  }

  private final int size;
  private final Slot[] slots;
  private final String[] endpoints;

  Slots(Response reply) {
    size = reply.size();
    slots = new Slot[size];

    Set<String> uniqueEndpoints = new HashSet<>();

    for (int i = 0; i < reply.size(); i++) {
      // multibulk
      Response s = reply.get(i);
      // single bulk
      slots[i] = new Slot(
        // start
        s.get(0).toInteger(),
        // end
        s.get(1).toInteger(),
        // size
        s.size() - 2);

      // array of all clients, clients[2] = master, others are slaves
      for (int index = 2; index < s.size(); index++) {
        Response c = s.get(index);
        slots[i].endpoints[index - 2] = "redis://" + c.get(0).toString() + ":" + c.get(1).toInteger();
        uniqueEndpoints.add(slots[i].endpoints[index - 2]);
      }
    }

    endpoints = new String[uniqueEndpoints.size()];
    int i = 0;
    for (String endpoint : uniqueEndpoints) {
      endpoints[i++] = endpoint;
    }
  }

  boolean contains(String endpoint) {
    for (String entry : endpoints) {
      if (endpoint.equals(entry)) {
        return true;
      }
    }
    return false;
  }

  int size() {
    return size;
  }

  String[] endpointsForSlot(int index) {
    return slots[index].endpoints;
  }

  String[] endpointsForKey(int key) {
    for (Slot s : slots) {
      if (key >= s.start && key <= s.end) {
        return s.endpoints;
      }
    }
    return null;
  }

  String randomEndPoint() {
    return endpoints[RANDOM.nextInt(endpoints.length)];
  }

  String[] endpoints() {
    return endpoints;
  }
}
