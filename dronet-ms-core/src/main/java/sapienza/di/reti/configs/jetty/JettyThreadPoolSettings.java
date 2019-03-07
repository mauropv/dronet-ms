package sapienza.di.reti.configs.jetty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jetty.threadPool")
class JettyThreadPoolSettings {

    private Integer minThreads = 8;
    private Integer maxThreads = 200;
    private Integer idleTimeout = 30000;
    private boolean detailedDump;

    public Integer getMinThreads() {
        return minThreads;
    }

    public void setMinThreads(Integer minThreads) {
        this.minThreads = minThreads;
    }

    public Integer getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public boolean isDetailedDump() {
        return detailedDump;
    }

    public void setDetailedDump(boolean detailedDump) {
        this.detailedDump = detailedDump;
    }
}