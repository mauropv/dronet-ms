package sapienza.di.reti.configs.jetty;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jetty9.InstrumentedHandler;
import com.codahale.metrics.jetty9.InstrumentedQueuedThreadPool;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.LowResourceMonitor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value={JettyThreadPoolSettings.class, JettyLowResourceMonitorSettings.class})
public class JettyConfiguration {

    @Autowired
    private MetricRegistry metricRegistry;

    @Autowired
    private JettyThreadPoolSettings jettyThreadPoolSettings;

    @Autowired
    private JettyLowResourceMonitorSettings jettyLowResourceMonitorSettings;

    @Bean
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        final JettyEmbeddedServletContainerFactory factory =  new JettyEmbeddedServletContainerFactory();
        factory.setThreadPool(getThreadPool(metricRegistry));
        factory.addServerCustomizers(
                (Server s) -> { lowResourceMonitor(s, jettyLowResourceMonitorSettings); },
                (Server s) -> { instrumentedHandler(s); }
        );
        return factory;
    }

    private Server lowResourceMonitor(Server server, JettyLowResourceMonitorSettings jettyLowResourceMonitorSettings) {
        LowResourceMonitor lowResourcesMonitor = new LowResourceMonitor(server);
        lowResourcesMonitor.setPeriod(jettyLowResourceMonitorSettings.getPeriod());
        lowResourcesMonitor.setLowResourcesIdleTimeout(jettyLowResourceMonitorSettings.getIdleTimeout());
        lowResourcesMonitor.setMonitorThreads(jettyLowResourceMonitorSettings.isMonitorThreads());
        lowResourcesMonitor.setMaxConnections(jettyLowResourceMonitorSettings.getMaxConnections());
        lowResourcesMonitor.setMaxMemory(jettyLowResourceMonitorSettings.getMaxMemory());
        lowResourcesMonitor.setMaxLowResourcesTime(jettyLowResourceMonitorSettings.getMaxLowResourcesTime());
        server.addBean(lowResourcesMonitor);
        return server;
    }

    private Server instrumentedHandler(Server server) {
        Handler handler = new InstrumentedHandler(metricRegistry, "jetty.metrics");
        HandlerCollection collection = new HandlerCollection();
        collection.setHandlers(new Handler[]{handler, server.getHandler()});
        server.setHandler(collection);
        return server;
    }

    private QueuedThreadPool getThreadPool(MetricRegistry metricRegistry) {
        QueuedThreadPool threadPool = new InstrumentedQueuedThreadPool(metricRegistry);
        threadPool.setMaxThreads(jettyThreadPoolSettings.getMaxThreads());
        threadPool.setMinThreads(jettyThreadPoolSettings.getMinThreads());
        threadPool.setIdleTimeout(jettyThreadPoolSettings.getIdleTimeout());
        threadPool.setDetailedDump(jettyThreadPoolSettings.isDetailedDump());
        return threadPool;
    }
}