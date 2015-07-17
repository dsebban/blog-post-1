package daniels.reactive.blog.ib;

import lombok.Value;

import java.math.BigDecimal;

/**
 * Created by daniel on 7/17/15.
 */
@Value
public class LivePriceEvent implements PriceEvent {
    public Long createTimestamp ;
    Instrument instrument;
    BigDecimal price;
}
