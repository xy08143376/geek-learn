package nio02.gateway.route;

import java.util.List;

/**
 * @author itguoy
 * @date 2021-09-28 10:39
 */
public interface HttpEndpointRouter {

    String route(List<String> endpoints);

    // Load Balance
    // Random
    // weight
    //  - server01, 20
    //  - server02, 30
    //  - server03, 50
}
