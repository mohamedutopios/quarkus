package health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import service.ContactService;


@Readiness
public class ReadinessCheck implements HealthCheck {

    private final ContactService contactService;

    public ReadinessCheck(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public HealthCheckResponse call() {
        return contactService.isLive() ? HealthCheckResponse.up("Readiness contactService") : HealthCheckResponse.down("Readiness contactService");
    }
}
