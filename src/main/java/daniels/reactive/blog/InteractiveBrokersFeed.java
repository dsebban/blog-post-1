package daniels.reactive.blog;

/**
 * Created by daniel on 2/26/15.
 */

import com.ib.client.TickType;
import com.ib.client.Types;
import com.ib.controller.ApiController;
import daniels.reactive.blog.ib.Instrument;
import daniels.reactive.blog.ib.LivePriceEvent;
import daniels.reactive.blog.rx.MarketDataObservable;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@Log
public class InteractiveBrokersFeed {

    MarketDataObservable marketDataObservable;
    ApiController controller;
    private static AtomicInteger clientId = new AtomicInteger(3);

    public void subscribeRealTimeData(Instrument instrument) {
        controller.reqTopMktData(instrument.ibContract, "232", false, new ApiController.ITopMktDataHandler() {
            @Override
            public void tickPrice(TickType tickType, double price, int canAutoExecute) {

                if(tickType == TickType.ASK) {
                    log.info("IB tick " + new Date() + " price " + price);
                    LivePriceEvent priceEvent = new LivePriceEvent(System.currentTimeMillis(), instrument, new BigDecimal(price).setScale(3, RoundingMode.UP));
                    marketDataObservable.push(priceEvent);
                }

            }

            @Override
            public void tickSize(TickType tickType, int size) {

            }

            @Override
            public void tickString(TickType tickType, String value) {
            }
            @Override
            public void tickSnapshotEnd() {
            }

            @Override
            public void marketDataType(Types.MktDataType marketDataType) {
            }
        });
    }


    private static class LazyHolder {
        private static final InteractiveBrokersFeed INSTANCE = new InteractiveBrokersFeed();
    }

    public static InteractiveBrokersFeed getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void connect() {
        CountDownLatch connected = new CountDownLatch(1);
        controller = new ApiController(new ApiController.IConnectionHandler() {
            @Override
            public void connected() {

                connected.countDown();
                log.info("connected");
            }

            @Override
            public void disconnected() {
                log.info("disconnected");
            }

            @Override
            public void accountList(ArrayList<String> list) {

            }

            @Override
            public void error(Exception e) {

                log.severe(e.getMessage());
                marketDataObservable.error(e);
            }

            @Override
            public void message(int id, int errorCode, String errorMsg) {
                log.severe("id " + id + " errocode = " + errorCode + "msg " + errorMsg);
//                log.info("make sure IB TWS is launched retrying to connect in 5 secs ...");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(!controller.isConnected())controller.connect("", 4001, clientId.getAndIncrement(), "");
            }

            @Override
            public void show(String string) {
                log.info(string);

            }
        }, n -> {
        }, n -> {
        });

        controller.connect("", 4001, clientId.getAndIncrement(), "");
        try {
            connected.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void setMarketDataObservable(MarketDataObservable marketDataObservable) {
        this.marketDataObservable = marketDataObservable;
    }
}










