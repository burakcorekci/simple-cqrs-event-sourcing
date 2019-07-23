package com.corekcioglu.elinvar.bankAccountRead.Entity;

import java.io.Serializable;

public abstract class AbstractEntity<P> implements Serializable {
    public abstract P getId();
}
