package dod.p1.kc.routing.redirects.deployment;
import dod.p1.kc.routing.redirects.runtime.KcRoutingRedirectsRecorder;

import java.util.HashMap;

import org.jboss.logging.Logger;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.vertx.http.deployment.NonApplicationRootPathBuildItem;
import io.quarkus.vertx.http.deployment.RouteBuildItem;

public class KcRoutingRedirectsProcessor {

    /**
     * Define logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KcRoutingRedirectsProcessor.class);

    /**
     * Name of feature required for quarkus build augmentation phase.
     */
    private static final String FEATURE = "kc-routing-redirects";


    /**
     *
     * @return newFeatureBuildItem()
     */
    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    /**
     *
     * @param recorder
     * @param routes
     * @param nonApplicationRootPathBuildItem
     * @param kcRoutingRedirectsConfig
     */
    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    public void registerKcRoutingRedirectsHandler(final KcRoutingRedirectsRecorder recorder,
            final BuildProducer<RouteBuildItem> routes,
            final NonApplicationRootPathBuildItem nonApplicationRootPathBuildItem,
            final KcRoutingRedirectsConfig kcRoutingRedirectsConfig) {

        HashMap<String, String> pathRedirectsMap = new HashMap<>(kcRoutingRedirectsConfig.pathRedirects);
        HashMap<String, String> pathPrefixesMap = new HashMap<>(kcRoutingRedirectsConfig.pathPrefixes);
        HashMap<String, String> pathFiltersMap = new HashMap<>(kcRoutingRedirectsConfig.pathFilters);
        HashMap<String, String> pathBlocksMap = new HashMap<>(kcRoutingRedirectsConfig.pathBlocks);
        HashMap<String, String> pathAllowsMap = new HashMap<>(kcRoutingRedirectsConfig.pathAllows);

        pathRedirectsMap.forEach((k, v) -> {
          LOGGER.infof("Creating Redirect Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k)
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathRedirects(pathRedirectsMap);

        pathPrefixesMap.forEach((k, v) -> {
          LOGGER.infof("Creating Prefix Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k + "/*")
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathPrefixes(pathPrefixesMap);

        pathFiltersMap.forEach((k, v) -> {
          LOGGER.infof("Creating Filters Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k)
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathFilters(pathFiltersMap);

        pathBlocksMap.forEach((k, v) -> {
          LOGGER.infof("Creating Blocks Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k)
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathBlocks(pathBlocksMap);

        pathAllowsMap.forEach((k, v) -> {
          LOGGER.infof("Creating Allows Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k)
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathAllows(pathAllowsMap);
  }
}
