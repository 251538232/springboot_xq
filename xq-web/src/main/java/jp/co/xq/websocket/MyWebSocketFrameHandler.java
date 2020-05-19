package jp.co.xq.websocket;

import java.math.BigDecimal;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * @author pengttyy pengttyy@gmail.com
 * @date 2018/2/3 0:19
 */
public class MyWebSocketFrameHandler
    extends SimpleChannelInboundHandler<WebSocketFrame> {
  Logger logger = LoggerFactory.getLogger(MyWebSocketFrameHandler.class);
  private static final String PING = "ping";
  private static final String PONG = "pong";
  private static final String STATUS = "status";
  private BigDecimal startopen = BigDecimal.ZERO;
  private BigDecimal open = BigDecimal.ZERO;
  Long pre_ts = 0L;
  JSONObject jsonObject;
  ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(5);
  boolean isRun = false;

  BigDecimal upCount = new BigDecimal(0);
  BigDecimal midCount = new BigDecimal(0);
  BigDecimal downCount = new BigDecimal(0);
  BigDecimal preAmount = BigDecimal.ZERO;

  class EchoServer implements Runnable {
    @Override
    public void run() {
      try {
        open = jsonObject.getJSONObject("tick").getBigDecimal("open");
        isRun = false;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame)
      throws Exception {
    if (frame instanceof BinaryWebSocketFrame) {
      BinaryWebSocketFrame binary = (BinaryWebSocketFrame) frame;
      ByteBuf content = binary.content();
      byte[] bytes = new byte[content.readableBytes()];
      content.readBytes(bytes);
      String serverMsg = GzipUtil.decompress(bytes);
      if (serverMsg.contains(PING)) {
        String clentMsg = serverMsg.replaceAll(PING, PONG);
        ctx.writeAndFlush(new TextWebSocketFrame(clentMsg));
      } else if (serverMsg.contains(STATUS)) {
        System.out.println("订阅消息" + serverMsg);
      } else {
        // {"ch":"market.eosusdt.kline.1min","ts":1532507560805,"tick":{"id":1532507520,"open":8.614600000000000000,"close":8.618600000000000000,"low":8.612700000000000000,"high":8.624000000000000000,"amount":8824.868900000000000000,"vol":76073.375928330000000000000000000000000000,"count":69}}
        // 结果分析
        jsonObject = JSON.parseObject(serverMsg);
        Long ts = jsonObject.getLong("ts");
        open = jsonObject.getJSONObject("tick").getBigDecimal("open");
        if (startopen.doubleValue() == 0) {
          startopen = open;
        }
        BigDecimal now_close = jsonObject
            .getJSONObject("tick")
              .getBigDecimal("close");

        BigDecimal nowAmount = jsonObject
            .getJSONObject("tick")
              .getBigDecimal("amount");
        BigDecimal subtract = now_close.subtract(open);
        BigDecimal percent = new BigDecimal(100);
        if (!isRun) {
          isRun = true;
          schedule.schedule(new EchoServer(), 5, TimeUnit.SECONDS);
        }
        /// 自用
        if (subtract.compareTo(BigDecimal.ZERO) > 0) {
          if (nowAmount.doubleValue() < preAmount.doubleValue()) {
            upCount = upCount.add(nowAmount);
          } else {
            upCount = upCount.add(nowAmount.subtract(preAmount));
          }
          System.err
              .println(ts + "  close: " + now_close.toString().substring(0, 6)
                  + " 幅 ："
                  + subtract
                      .divide(open, 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(percent)
                        .setScale(4, BigDecimal.ROUND_HALF_UP)
                  + "%" + " 数量："
                  + preAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else if (subtract.compareTo(BigDecimal.ZERO) == 0) {
          if (nowAmount.doubleValue() < preAmount.doubleValue()) {
            midCount = midCount.add(nowAmount);
          } else {
            midCount = midCount.add(nowAmount.subtract(preAmount));
          }
          System.err
              .println(ts + "  close: " + now_close.toString().substring(0, 6)
                  + " ===== open：" + open.toString().substring(0, 6) + " 数量："
                  + preAmount);
        } else {
          if (nowAmount.doubleValue() < preAmount.doubleValue()) {
            downCount = downCount.add(nowAmount);
          } else {
            downCount = downCount.add(nowAmount.subtract(preAmount));
          }
          System.out
              .println(ts + "   close: " + now_close.toString().substring(0, 6)
                  + " 幅 ："
                  + subtract
                      .multiply(new BigDecimal(-1))
                        .divide(open, 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(percent)
                        .setScale(4, BigDecimal.ROUND_HALF_UP)
                  + "%" + " 数量："
                  + preAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        // System.out.println(" start open: " +
        // startopen.toString().substring(0, 6) + " 上涨量：" +
        // String.valueOf(upCount.setScale(2, BigDecimal.ROUND_HALF_UP)) + "
        // 平量：" + midCount.setScale(2, BigDecimal.ROUND_HALF_UP) +" 下跌量：" +
        // downCount.setScale(2, BigDecimal.ROUND_HALF_UP));
        preAmount = nowAmount;
      }
    } else {
      System.out.println(frame);
    }
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
      throws Exception {
    if (evt instanceof WebSocketClientProtocolHandler.ClientHandshakeStateEvent) {
      switch ((WebSocketClientProtocolHandler.ClientHandshakeStateEvent) evt) {
      case HANDSHAKE_COMPLETE:
        // ctx.writeAndFlush(new
        // TextWebSocketFrame("{\"sub\":\"market.eosusdt.trade.detail\",\"id\":
        // \"id1\"}"));

        // ctx.writeAndFlush(new
        // TextWebSocketFrame("{\"sub\":\"market.eosusdt.kline.1min\",\"id\":
        // \"id1\"}"));
        ctx
            .writeAndFlush(new TextWebSocketFrame(
                "{\"sub\":\"market.eosusdt.kline.1min\",\"id\": \"id1\"}"));
        break;
      case HANDSHAKE_ISSUED:
        break;
      default:
        System.out.println("启动握手，服务器还未响应");
      }

    }
  }
}
