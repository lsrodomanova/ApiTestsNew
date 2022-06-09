package config.demowebshop;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:properties/credential.properties"
})
public interface CredentialsConfig extends Config {
    String login();
    String password();
    String authCookieName();
}
