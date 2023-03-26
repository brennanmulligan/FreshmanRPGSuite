package edu.ship.engr.shipsim.model;

public class CheckPassword
{
    public static boolean checkPassword(String password)
    {
        /**
         * Password Requirements
         * 8-16 characters in length
         * 1+ capital letters
         * 1+ lowercase letters
         * 1+ special characters
         */

        // 8 - 16 char length
        if(password.length() < 8 || password.length() > 16)
        {
            return false;
        }

        // Has capital
        char current;
        boolean hasCapital = false, hasLowercase = false, hasSpecial = false;
        for(int i = 0; i < password.length(); i++)
        {
            current = password.charAt(i);
            if(Character.isUpperCase(current))
            {
                hasCapital = true;
            }
            else if(Character.isLowerCase(current))
            {
                hasLowercase = true;
            }
            else
            {
                hasSpecial = true;
            }
            if (hasCapital && hasLowercase && hasSpecial)
            {
                break;
            }
        }

        return (hasCapital && hasLowercase && hasSpecial);
    }
}
