package edu.ship.engr.shipsim.DatabaseBuilders;

import java.util.Collections;

/**
 * A simple utility to print a progress bar in the terminal
 *
 * @author Derek Williams (dw1738)
 */
public class ProgressBar
{
    /**
     * The total amount of {@link ProgressBar#FILLER_CHARACTER filler characters}
     * present in the progress bar
     */
    private final int BAR_LENGTH = 40;

    /**
     * Character printed inside the progress bar
     */
    private final char FILLER_CHARACTER = '=';

    private final StringBuilder builder;

    /**
     * The total number of elements to be processed
     */
    private final int total;

    /**
     * The number of elements already printed
     */
    private int numberCompleted;

    /**
     * Whether to continue printing the progress bar.
     * <p>
     * This will be set to false when {@link ProgressBar#numberCompleted} is equal to {@link ProgressBar#total}
     * <p>
     * This variable exists to prevent {@link ProgressBar#update()} from being called more than {@link ProgressBar#total} times
     */
    private boolean allowedPrinting = false;

    public ProgressBar(int total)
    {
        this.builder = new StringBuilder(BAR_LENGTH);

        this.total = total;
        this.numberCompleted = 0;
    }

    /**
     * Increment the number of elements processed.
     * <p>
     * Will then print the progress bar:
     * <ul>
     *     <li>Current percentage</li>
     *     <li>An amount of {@link ProgressBar#FILLER_CHARACTER filler characters} to represent the current percentage</li>
     *     <li>A > character</li>
     *     <li>Enough spaces to fill the remainder of the bar</li>
     *     <li>A fraction representing the number of elements processed</li>
     * </ul>
     */
    public void update()
    {
        if (allowedPrinting)
        {
            this.numberCompleted++;

            int percent = (numberCompleted * 100) / total;

            int barCharacters = (int) (BAR_LENGTH * ((double) numberCompleted / total));

            builder.append('\r'); // Shift builder to beginning of line
            builder.append(String.format("%3d%% [", percent)); // Print percentage number with start of progress bar
            builder.append(String.join("", Collections.nCopies(barCharacters, FILLER_CHARACTER + ""))); // Print enough = characters to represent current percentage complete
            builder.append('>'); // Add arrow at the end of = characters
            builder.append(String.join("", Collections.nCopies(BAR_LENGTH - barCharacters, " "))); // Add enough spaces to fill progress bar
            builder.append(']'); // Print end of progress bar
            builder.append(String.join("", Collections.nCopies((int) (Math.log10(total)) - Math.max(0, (int) (Math.log10(numberCompleted))), " ")));
            builder.append(String.format(" %d/%d", numberCompleted, total)); // Print fraction completed

            System.out.print(builder);

            // If the total number of elements has been reached, disable printing and flush console
            if (numberCompleted == total)
            {
                System.out.flush();
                System.out.println();
                allowedPrinting = false;
            }
        }
    }
}
