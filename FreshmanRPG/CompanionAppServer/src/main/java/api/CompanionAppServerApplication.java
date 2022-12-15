package api;

import api.model.PlayerTokenManager;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot auto generated runner
 *
 * @author SpringBoot
 */


//ComponentScan scans for components within the project hierarchy so SpringBoot is aware of them
//The packages need to be specified because they are not in the "application" package


@SpringBootApplication
public class CompanionAppServerApplication
{

    public static void main(String[] args)
    {
        PlayerTokenManager.getInstance();
        LoggerManager.createLogger("Restful.log");
        SpringApplication.run(CompanionAppServerApplication.class, args);
    }

}
