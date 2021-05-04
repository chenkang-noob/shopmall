package com.imnoob.shopmallgateway.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@ConfigurationProperties(prefix = "gatewayfilter")
public class WhiteURL {
    private List<String> whitepath;

    public WhiteURL() {
    }

    public List<String> getWhitepath() {
        return whitepath;
    }

    public void setWhitepath(List<String> whitepath) {
        this.whitepath = whitepath;
    }
}
