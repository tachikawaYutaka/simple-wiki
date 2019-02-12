package com.wakabatimes.simplewiki.app.domain.model.body;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bodies {
    List<Body> bodies;

    public Bodies () {
        bodies = new ArrayList<>();
    }

    public Bodies(List<Body> bodies) {
        this.bodies = new ArrayList<>(bodies);
    }

    /**
     * body add
     * @throws RuntimeException
     */
    public void add(@NonNull Body body) {
        this.bodies.add(body);
    }

    /**
     * bodies count
     * @return Integer
     */
    public Integer size() {
        return this.bodies.size();
    }


    /**
     * get list
     *
     * @return List<User>
     */
    public List<Body> list() {
        return Collections.unmodifiableList(this.bodies);
    }
}
