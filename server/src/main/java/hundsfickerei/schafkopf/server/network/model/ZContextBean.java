package hundsfickerei.schafkopf.server.network.model;

import org.springframework.stereotype.Component;
import org.zeromq.ZContext;

@Component
public class ZContextBean {

    private ZContext context;

    public ZContextBean() {
        context = new ZContext();
    }

    public ZContext getContext() {
        return context;
    }
}
