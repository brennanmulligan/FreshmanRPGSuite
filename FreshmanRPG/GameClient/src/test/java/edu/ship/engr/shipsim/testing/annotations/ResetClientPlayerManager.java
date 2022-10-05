package edu.ship.engr.shipsim.testing.annotations;

import edu.ship.engr.shipsim.testing.extensions.ResetClientPlayerManagerExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith(ResetClientPlayerManagerExtension.class)
public @interface ResetClientPlayerManager
{
}
