package daniels.reactive.blog.ib;

import com.ib.client.Contract;
import com.ib.client.Types;

import java.math.BigDecimal;

/**
 * Created by daniel on 3/15/15.
 */
public enum Instruments {
    APPL("AAPL" , "USD", Types.SecType.STK , "SMART","201506",1d) ,
    ES201506("ES","USD", Types.SecType.FUT,"GLOBEX","201506",50.0d) ,

    VIX201503("VIX","USD", Types.SecType.FUT,"CFE","201503",1000.0d) ,
    VIX201504("VIX","USD", Types.SecType.FUT,"CFE","201504",1000.0d) ,
    VIX201505("VIX","USD", Types.SecType.FUT,"CFE","201505",1000.0d) ,
    VIX201506("VIX","USD", Types.SecType.FUT,"CFE","201506",1000.0d),
    VIX201507("VIX","USD", Types.SecType.FUT,"CFE","201507",1000.0d),
    VIX201508("VIX","USD", Types.SecType.FUT,"CFE","201508",1000.0d);
    public Contract ibContract;
    public BigDecimal pointValue = BigDecimal.ONE;
    private final Instrument instrument;
    Instruments(String symbol, String currency, Types.SecType type, String exchange, String expiry, Double pointValue) {
        ibContract = new Contract();
        ibContract.symbol(symbol);
        ibContract.currency(currency);
        ibContract.secType(type);
        ibContract.exchange(exchange);
        ibContract.expiry(expiry);
        this.pointValue = new BigDecimal(String.valueOf(pointValue));
        instrument = new Instrument(name(),this.pointValue,ibContract);
    }

    public Instrument val() {return instrument;};

}
