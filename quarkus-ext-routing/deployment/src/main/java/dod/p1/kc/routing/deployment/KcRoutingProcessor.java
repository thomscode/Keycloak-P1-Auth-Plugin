package dod.p1.kc.routing.deployment;
import dod.p1.kc.routing.runtime.KcRoutingRecorder;

import java.util.HashMap;

import org.jboss.logging.Logger;

import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.vertx.http.deployment.NonApplicationRootPathBuildItem;
import io.quarkus.vertx.http.deployment.RouteBuildItem;

public class KcRoutingProcessor {

    /**
     * Define logger.
     */
    private static final Logger LOGGER = Logger.getLogger(KcRoutingProcessor.class);

    /**
     * Name of feature required for quarkus build augmentation phase.
     */
    private static final String FEATURE = "kc-routing";


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
     * @param kcRoutingConfig
     */
    @BuildStep
    @Record(ExecutionTime.STATIC_INIT)
    public void registerKcRoutingHandler(final KcRoutingRecorder recorder,
            final BuildProducer<RouteBuildItem> routes,
            final NonApplicationRootPathBuildItem nonApplicationRootPathBuildItem,
            final KcRoutingConfig kcRoutingConfig) {

        HashMap<String, String> pathRedirectsMap = new HashMap<>(kcRoutingConfig.pathRedirects);
        HashMap<String, String> pathPrefixesMap = new HashMap<>(kcRoutingConfig.pathPrefixes);
        HashMap<String, String> pathFiltersMap = new HashMap<>(kcRoutingConfig.pathFilters);
        HashMap<String, String> pathBlocksMap = new HashMap<>(kcRoutingConfig.pathBlocks);
        HashMap<String, String> pathAllowsMap = new HashMap<>(kcRoutingConfig.pathAllows);

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
          LOGGER.infof("Creating Filter Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k)
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathFilters(pathFiltersMap);

        pathBlocksMap.forEach((k, v) -> {
          LOGGER.infof("Creating Block Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k + "/*")
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathBlocks(pathBlocksMap);

        pathAllowsMap.forEach((k, v) -> {
          LOGGER.infof("Creating Allow Routes: %s %s", k, v);
          routes.produce(nonApplicationRootPathBuildItem.routeBuilder()
                  .route(k)
                  .handler(recorder.getHandler())
                  .build());
        });
        recorder.setPathAllows(pathAllowsMap);
  }
}
