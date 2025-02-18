package io.vertx.ext.web.handler;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.impl.CSRFHandlerImpl;

/**
 * This handler adds a CSRF token to requests which mutate state. In order change the state a (XSRF-TOKEN) cookie is set
 * with a unique token, that is expected to be sent back in a (X-XSRF-TOKEN) header.
 *
 * The behavior is to check the request body header and cookie for validity.
 *
 * This Handler requires session support, thus should be added somewhere below Session and Body handlers.
 *
 * @author <a href="mailto:plopes@redhat.com">Paulo Lopes</a>
 */
@VertxGen
public interface CSRFHandler extends InputTrustHandler {

  String DEFAULT_COOKIE_NAME = "XSRF-TOKEN";

  String DEFAULT_COOKIE_PATH = "/";

  String DEFAULT_HEADER_NAME = "X-XSRF-TOKEN";

  /**
   * Instantiate a new CSRFHandlerImpl with a secret
   * <p>
   * <pre>
   * CSRFHandler.create("s3cr37")
   * </pre>
   *
   * @param secret server secret to sign the token.
   */
  static CSRFHandler create(Vertx vertx, String secret) {
    return new CSRFHandlerImpl(vertx, secret);
  }

  /**
   * Set the origin for this server. When this value is set, extra validation will occur. The request
   * must match the origin server, port and protocol.
   *
   * @param origin the origin for this server e.g.: {@code https://www.foo.com}.
   * @return fluent
   */
  @Fluent
  CSRFHandler setOrigin(String origin);

  /**
   * Set the cookie name. By default XSRF-TOKEN is used as it is the expected name by AngularJS however other frameworks
   * might use other names.
   *
   * @param name a new name for the cookie.
   * @return fluent
   */
  @Fluent
  CSRFHandler setCookieName(String name);

  /**
   * Set the cookie path. By default / is used.
   *
   * @param path a new path for the cookie.
   * @return fluent
   */
  @Fluent
  CSRFHandler setCookiePath(String path);

  /**
   * Set the cookie {@code httpOnly} attribute. When setting to {@code false} the CSRF handler will behave in
   * Double Submit Cookie mode. When set to {@code true} then it will operate in Cookie-to-header mode.
   *
   * For more information <a href="https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html#double-submit-cookie">https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html#double-submit-cookie</a>
   *
   * @param httpOnly a new name for the header.
   * @return fluent
   */
  @Fluent
  CSRFHandler setCookieHttpOnly(boolean httpOnly);

  /**
   * Set the header name. By default X-XSRF-TOKEN is used as it is the expected name by AngularJS however other
   * frameworks might use other names.
   *
   * @param name a new name for the header.
   * @return fluent
   */
  @Fluent
  CSRFHandler setHeaderName(String name);

  /**
   * Should the handler give warning messages if this handler is used in other than https protocols?
   *
   * @param nag true to nag
   * @return fluent
   */
  @Fluent
  CSRFHandler setNagHttps(boolean nag);

  /**
   * Set the timeout for tokens generated by the handler, by default it uses the default from the session handler.
   *
   * @param timeout token timeout
   * @return fluent
   */
  @Fluent
  CSRFHandler setTimeout(long timeout);
}
