package com.jiahe.app.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class ObservationPk implements Serializable {
    protected int itemId;
    protected Date timestamp;

    public ObservationPk() {
    }

    public ObservationPk(int itemId, Date timestamp) {
        this.itemId = itemId;
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ObservationPk that = (ObservationPk) o;

        if (itemId != that.itemId) return false;
        return timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + timestamp.hashCode();
        return result;
    }
}