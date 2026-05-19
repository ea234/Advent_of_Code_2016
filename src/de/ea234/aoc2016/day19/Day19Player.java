package de.ea234.aoc2016.day19;

public class Day19Player
{
  private int index_position_start = 0;

  private int player_name = 0;

  private int nr_of_presents       = 1;

  public Day19Player( int pIndex )
  {
    index_position_start = pIndex;
    
    player_name = pIndex + 1;
  }

  public int getNrOfPresents()
  {
    return nr_of_presents;
  }

  public void setNrOfPresents( int pNumber )
  {
    nr_of_presents = pNumber;
  }

  public void addNrOfPresents( int pNumber )
  {
    nr_of_presents += pNumber;
  }

  public int getIndexStart()
  {
    return index_position_start;
  }

  public int getPlayerName()
  {
    return player_name;
  }

  public String toString()
  {
    return String.format( "Player %7d  Presents %7d ", player_name, nr_of_presents );
  }
}
