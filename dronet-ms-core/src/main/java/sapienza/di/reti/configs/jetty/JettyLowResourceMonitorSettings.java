package sapienza.di.reti.configs.jetty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jetty.lowResources")
class JettyLowResourceMonitorSettings {
    private Integer period = 1000;
    private Integer idleTimeout = 200;
    private boolean monitorThreads = true;
    private Integer maxConnections = 0;
    private Integer maxMemory;
    private Integer maxLowResourcesTime = 5000;

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public boolean isMonitorThreads() {
        return monitorThreads;
    }

    public void setMonitorThreads(boolean monitorThreads) {
        this.monitorThreads = monitorThreads;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(Integer maxMemory) {
        this.maxMemory = maxMemory;
    }

    public Integer getMaxLowResourcesTime() {
        return maxLowResourcesTime;
    }

    public void setMaxLowResourcesTime(Integer maxLowResourcesTime) {
        this.maxLowResourcesTime = maxLowResourcesTime;
    }
}