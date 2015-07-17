package daniels.reactive.blog.ib;

import lombok.Value;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 7/17/15.
 */
@Value
public class LiveBarEvent implements PriceEvent {
    public TimeUnit barDuration;
    public Long createTimestamp ;
    Instrument instrument;
    BigDecimal price;
}
