package daniels.reactive.blog.ib;

import java.math.BigDecimal;

/**
 * Created by daniel on 7/17/15.
 */
public interface PriceEvent {

    Instrument getInstrument();
    BigDecimal getPrice();
}
