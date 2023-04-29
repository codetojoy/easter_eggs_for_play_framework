
package services;

import javax.inject.Inject;
import javax.inject.Provider;

public class ConfigService {
    private static final int DELAY_IN_MILLIS = 1 * 1000;

    private final Provider<FooService> fooServiceProvider;

    @Inject
    public ConfigService(Provider<FooService> fooServiceProvider) {
        this.fooServiceProvider = fooServiceProvider;
    }

    public boolean isFooEnabled() {
        FooService fooService = fooServiceProvider.get();
        return fooService.isEnabled();
    }

    public int getDelayInMillis() {
        return DELAY_IN_MILLIS;
    }
}
