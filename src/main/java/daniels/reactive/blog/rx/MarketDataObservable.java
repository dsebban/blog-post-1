package daniels.reactive.blog.rx;

import com.ib.client.Types;
import daniels.reactive.blog.ib.LiveBarEvent;
import daniels.reactive.blog.ib.LivePriceEvent;
import daniels.reactive.blog.ib.PriceEvent;
import lombok.extern.java.Log;
import org.joda.time.DateTime;
import rx.Observable;
import rx.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * Created by daniel on 7/17/15.
 */
@Log
public class MarketDataObservable {

    private final PublishSubject<PriceEvent> publishSubject;

    public MarketDataObservable() {
        publishSubject = PublishSubject.create();
    }

    public void aggregateLiveMinuteBar() {

        observable().
                ofType(LivePriceEvent.class). //filter on live ticks
                groupBy(LivePriceEvent::getInstrument). // group by instrument i.e AAPL, GOOG
                flatMap(grouped -> grouped.buffer(2, 1)). // take each 2 consecutive events
                subscribe(listOf2 -> {
                    LivePriceEvent lastEvent = listOf2.get(0);
                    int lastMinute = new DateTime(lastEvent.getCreateTimestamp()).minuteOfHour().get();
                    int currentMinute = new DateTime(listOf2.get(1).getCreateTimestamp()).minuteOfHour().get();
            //when minute is crossed , we push the result back in the observable to make it available to other subscribers
            if (lastMinute != currentMinute) {
                        push(new LiveBarEvent(TimeUnit.MINUTES, lastEvent.createTimestamp, lastEvent.getInstrument(), lastEvent.getPrice()));
                    }

        });


    }

    public Observable<PriceEvent> observable() {
        return publishSubject.asObservable();
    }


    public void push(PriceEvent priceEvent) {

        publishSubject.onNext(priceEvent);
    }

    public void error(Exception e) {
        publishSubject.onError(e);
    }
}
