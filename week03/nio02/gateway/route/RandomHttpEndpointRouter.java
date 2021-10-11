package nio02.gateway.route;

import java.util.List;
import java.util.Random;

/**
 * @author itguoy
 * @date 2021-09-28 10:41
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {

    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random(System.currentTimeMillis());
        int idx = random.nextInt(size);
        return endpoints.get(idx);
    }
}
