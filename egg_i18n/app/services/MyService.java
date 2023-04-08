
package services;

import play.i18n.Langs;
import play.i18n.Messages;
import play.i18n.MessagesApi;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

public class MyService {
  private final Langs langs;

  @Inject
  public MyService(Langs langs) {
      this.langs = langs;
      for (var lang : langs.availables()) {
          System.out.println("TRACER MyService lang: " + lang);
      }
  }
}
