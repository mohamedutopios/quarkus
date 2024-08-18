package health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import service.ContactService;


@Liveness
public class LivenessCheck implements HealthCheck {

    private final ContactService contactService;

    public LivenessCheck(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public HealthCheckResponse call() {
        return contactService.isLive() ? HealthCheckResponse.up("Liveness contactService") : HealthCheckResponse.down("Liveness contactService");
    }
}
