package daniels.reactive.blog.ib;

import com.ib.client.Contract;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by daniel on 5/6/15.
 */
public class Instrument {
    public final String name;
    public BigDecimal multiplier;
    public final Contract ibContract;

    public Instrument(String name, BigDecimal pointValue, Contract ibContract) {
        this.name = name;
        this.multiplier = pointValue;
        this.ibContract = ibContract;
    }

    public String name() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
                 return name;
    }
}
