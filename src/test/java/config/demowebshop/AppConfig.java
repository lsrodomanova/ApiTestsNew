package config.demowebshop;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:app.properties"
})
public interface AppConfig extends Config {

    String webUrl();
    String apiURI();

}
