package net.utopios.health;

import net.utopios.service.DataService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;


@Liveness
public class LivenessCheck implements HealthCheck {

    private final DataService dataService;

    public LivenessCheck(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public HealthCheckResponse call() {
        return (dataService.isLive()) ? HealthCheckResponse.up("Liveness UP") : HealthCheckResponse.down("Liveness is Down");
    }
}
