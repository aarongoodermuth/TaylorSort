/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tayrankings;

import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Aaron
 */
public class TayRankings
{
public static int count = 0;
    /**
     * @param args the command line arguments
     */

     private static ItunesEntry[] song;
     private static int index = 0;

    public static void main(String[] args)
    {
        String[] lines;
        JFileChooser jfc = new JFileChooser();

        //get where to grab file from
        if( jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION )
        {
            System.exit(0);
        }
        
        //get lines of file
        lines = TaylorRead(jfc.getSelectedFile().getAbsolutePath());
        
        //get just song name and albumn
        for(String cur : lines)
        {
            song[index++] = new ItunesEntry(cur);
        }
        
        //sort
        song = TaylorSort(song);
        
        //get where to put file
        if( jfc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION )
        {
            System.exit(0);
        }
        
        //dump to file
        FileDump(new File(jfc.getSelectedFile().getAbsolutePath()), song);
    }
    
    static ItunesEntry TaylorCompare(ItunesEntry a, ItunesEntry b)
    {
        int input;
        count++;
        System.out.println("Count: " + count);
        System.out.println(" ");
        System.out.println("1. " + a.getReadableLine());
        System.out.println(" ");
        System.out.println("2. " + b.getReadableLine());

        //get input
        input = ReadInt();
        
        for(int i=0; i<50; i++)
        {
            System.out.println(" ");
        }
        
        if(input == 1)
        {
            return a;
        }
        else if(input == 2)
        {
            return b;
        }
        else
        {
            return TaylorCompare(a, b);
        }
        
    }
    
    private static int ReadInt()
    {
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }
    
    static void FileDump(File f, ItunesEntry[] lines)
    {
        String toWrite = "";
        
        for(ItunesEntry cur : lines)
        {
            toWrite += cur.getWholeLine() + "\n";
        }
        
        try 
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(f));
            out.write(toWrite);
            out.close();
        } 
        catch (IOException e) 
        { 
            System.out.println("Exception ");
        }
    }
    
    static ItunesEntry[] TaylorSort(ItunesEntry[] items)
    {
        String[] retval;
        
        if(items.length <= 1)
        {
            return items;
        }
        else
        {
            int n = items.length / 2;
            ItunesEntry[] a = new ItunesEntry[n];
            ItunesEntry[] b = new ItunesEntry[items.length - n];
            
            for(int i=0; i<items.length; i++)
            {
                if(i<n)
                {
                    a[i] = items[i];
                }
                else
                {
                    b[i-n] = items[i];
                }
            }
            
            return TaylorMerge(TaylorSort(a), TaylorSort(b));
        }
        
        
    }
    
    static ItunesEntry[] TaylorMerge(ItunesEntry[] a, ItunesEntry[] b)
    {
        ItunesEntry[] retval = new ItunesEntry[a.length + b.length];
        int i = 0;
        int j = 0;
        int count = 0;
        ItunesEntry cur;
        
        while(i < a.length && j < b.length)
        {
             cur = TaylorCompare(a[i], b[j]);
             
             if(cur.equals(a[i]))
             {
                 retval[count++] = a[i++];
             }
             else
             {
                 retval[count++] = b[j++];
             }
        }
        
        if(i >= a.length)
        {
            while(j < b.length)
            {
               retval[count++] = b[j++]; 
            }
        }
        else
        {
            while(i < a.length)
            {
               retval[count++] = a[i++]; 
            }            
        }
        
        return retval;
    }
    
    static String[] TaylorRead(String filename)
    {
        String[] retval;
        String fileContents;
        
        try
        {
           fileContents = readEntireFile(filename); 
        }
        catch(Exception e)
        {
            return null;
        }
            
        retval = fileContents.split("\n");
        return retval;
    }
    
    private static String readEntireFile(String filename) throws IOException {
        FileReader in = new FileReader(filename);
        StringBuilder contents = new StringBuilder();
        char[] buffer = new char[4096];
        int read = 0;
        do {
            contents.append(buffer, 0, read);
            read = in.read(buffer);
        } while (read >= 0);
        return contents.toString();
    }            
}
