package edu.ship.engr.shipsim.testing.annotations;

import edu.ship.engr.shipsim.testing.extensions.ResetPlayerManagerExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(ResetPlayerManagerExtension.class)
public @interface ResetPlayerManager
{
}
