package daniels.reactive.blog;

import daniels.reactive.blog.ib.Instruments;
import daniels.reactive.blog.ib.LiveBarEvent;
import daniels.reactive.blog.rx.MarketDataObservable;
import lombok.extern.java.Log;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * Created by daniel on 7/17/15.
 */
@Log
public class Main {

    public static void main(String[] args) {

        try {
            log.info("waiting for 10 secs for IB TWS to go up");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MarketDataObservable observable = new MarketDataObservable();
        observable.aggregateLiveMinuteBar();
        InteractiveBrokersFeed.getInstance().setMarketDataObservable(observable);
        InteractiveBrokersFeed.getInstance().connect();
        InteractiveBrokersFeed.getInstance().subscribeRealTimeData(Instruments.APPL.val());

        //run for five minutes
        CountDownLatch latch = new CountDownLatch(5);

        observable.observable().ofType(LiveBarEvent.class).subscribe(
                minuteBar -> {
                    log.info("minute = " + new DateTime(minuteBar.getCreateTimestamp()).minuteOfHour().get() + " val="+minuteBar.toString());
                    latch.countDown();
                }
        );

    latch.countDown();
    }
}
