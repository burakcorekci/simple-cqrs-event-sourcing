package com.corekcioglu.elinvar.eventStreamProcessing.Entity;

import java.io.Serializable;

public abstract class AbstractEntity<P> implements Serializable {
    public abstract P getId();
}
