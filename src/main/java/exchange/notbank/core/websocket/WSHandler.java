package exchange.notbank.core.websocket;

import java.io.IOException;
import java.util.function.Consumer;

public interface WSHandler {
  public void handle(String json);

  /**
   * get the consumer called on failure with a throwable when the websocket fails
   * 
   * @return the throwable consumer
   */
  public Consumer<Throwable> getOnFailure();

  /**
   * get the consumer called on close with the reason as its string parameter
   * 
   * @return the string consumer
   */
  public Consumer<String> getOnClose();

  /**
   * get the runnable called on websocket connection
   * 
   * @return the runnable called on websocket connection
   */
  public Runnable getOnConnect();

  /**
   * connect the client to the exchange
   */
  public void connect() throws IOException;

  /**
   * closes the websocket connection
   */
  public void close();

}
