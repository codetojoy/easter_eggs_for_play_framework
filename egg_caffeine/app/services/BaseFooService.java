
package services;

class BaseFooService {
    private final ConfigService configService;

    BaseFooService(ConfigService configService) {
        this.configService = configService;
    }

    protected int getDelayInMillis() {
        return configService.getDelayInMillis();
    }
}
