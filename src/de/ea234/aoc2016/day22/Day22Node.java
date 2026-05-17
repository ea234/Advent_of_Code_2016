package de.ea234.aoc2016.day22;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day22Node
{
  String path = "";

  int size = 0;

  int used = 0;

  int avail = 0;

  int used_percent = 0;

  public Day22Node( String pInput ) 
  {
//    0 - 23 = path
//        24 - 28 = Size
//        29 - 34 = Used
//        35 - 41 = avail
//        44 - 47 = proc used
    
    path = pInput.substring( 0, 23 );
    size = Integer.parseInt( pInput.substring( 24, 27 ).trim() );
    used = Integer.parseInt( pInput.substring( 29, 33 ).trim() );
    avail = Integer.parseInt( pInput.substring( 35, 40 ).trim() );
    used_percent = Integer.parseInt( pInput.substring( 44, 46 ).trim() );

//    System.out.println( "path=" + path );
//    System.out.println( "size=" + size );
//    System.out.println( "used=" + used );
//    System.out.println( "avail=" + avail );
//    System.out.println( "usePercent=" + used_percent );

  }

  public int getUsed()
  {
    return used;
  }
  
  public int getAvail()
  {
    return avail;
  }  
  
  public void addUsed( int pUsed ) 
  {
    used += pUsed;
    
    avail = size - used;
  }
  
  public void setUsed( int pUsed ) 
  {
    used = pUsed;
    
    avail = size - used;
  }
  
  public String toString() 
  {
    return String.format( "%26s | S %4d | U %4d | A %4d", path, size, used, avail );
  }
}
