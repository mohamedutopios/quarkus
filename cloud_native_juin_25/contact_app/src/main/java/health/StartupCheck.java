package health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Startup;
import service.ContactService;


@Startup
public class StartupCheck implements HealthCheck {

    private final ContactService contactService;

    public StartupCheck(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public HealthCheckResponse call() {
        return contactService.isLive() ? HealthCheckResponse.up("startup contactService") : HealthCheckResponse.down("startup contactService");
    }
}
