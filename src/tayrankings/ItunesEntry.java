/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tayrankings;

/**
 *
 * @author Aaron
 */
public class ItunesEntry
{
    private String wholeLine;
    private String readableLine;
    
    ItunesEntry(String line)
    {
        wholeLine = line;
        readableLine = StripLine(wholeLine);
    }
    
    public String getWholeLine()
    {
        return wholeLine;
    }
    
    public String getReadableLine()
    {
        return readableLine;
    }
    
    private static String StripLine(String l)
    {
        String[] intermediate = l.split("\t");
        
        if(intermediate.length < 3)
        {
            return " ";
        }
        
        return intermediate[0] + "  (" + intermediate[3] + ")";
    }
}
