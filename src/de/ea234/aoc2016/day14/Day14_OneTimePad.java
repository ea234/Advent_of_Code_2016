package de.ea234.aoc2016.day14;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <pre>
 * 
 * --- Day 14: One-Time Pad ---
 * https://adventofcode.com/2016/day/14
 * 
 * https://www.reddit.com/r/adventofcode/comments/5i8pzz/2016_day_14_solutions/
 * 
 * --------------------------------------------------------------------------------
 * Calculate - Salt = abc
 * 
 * 
 * MD5 with triple values 2315
 * MD5 with fiver values  12
 * 
 * 64 Keys:
 *    1 Input abc92                 MD5 ae2e85dd75d63e916a525df95e999ea0  9  =       93 -      200 
 *    2 Input abc110                MD5 7af7fa13b999d68bbce565971ddbae27  9  =      111 -      200 
 *    3 Input abc314                MD5 b76a587a333a5de4fbd566b426e1254c  3  =      315 -      792 
 *    4 Input abc459                MD5 77b05e16607a333bae2dee537ce16b9a  3  =      460 -      792 
 *    5 Input abc461                MD5 d2c1333bc7b740a6af0c51eded05e70f  3  =      462 -      792 
 *    6 Input abc771                MD5 f4fc8ddb4c3336ad9fc5e6985bf10bfc  3  =      772 -      792 
 *    7 Input abc39                 MD5 347dac6ee8eeea4652c7476d0f97bee5  e  =       40 -      816 
 *    8 Input abc184                MD5 02164648eee4fd760cdab7e343effd74  e  =      185 -      816 
 *    9 Input abc291                MD5 d7c86a1c1af63c851fe876e44427da83  4  =      292 -     1261 
 *   10 Input abc343                MD5 d3c939a6e3739444983e1961fad091d8  4  =      344 -     1261 
 *   11 Input abc385                MD5 d847a4440f2a4423b79fe9bdc1cde659  4  =      386 -     1261 
 *   12 Input abc489                MD5 3168351b76dee9d74442c60fb7947758  4  =      490 -     1261 
 *   13 Input abc781                MD5 05cc3a44479f138930aef93d09adb271  4  =      782 -     1261 
 *   14 Input abc887                MD5 6224443f4f20b285f198a5928240b8f9  4  =      888 -     1261 
 *   15 Input abc955                MD5 067e32c47bb94441fb1ddb164028f1a0  4  =      956 -     1261 
 *   16 Input abc1144               MD5 2e65dc4b3a55644440750cb643d3547c  4  =     1145 -     1261 
 *   17 Input abc5742               MD5 839454c56aa6dddc15cfb3d2409bb5f1  d  =     5743 -     6392 
 *   18 Input abc5781               MD5 c50ff03d396edaddd83f4276ab16bb20  d  =     5782 -     6392 
 *   19 Input abc5783               MD5 a510c007dddee781e646b4f5fb8d8556  d  =     5784 -     6392 
 *   20 Input abc6016               MD5 0e30d5ff60b6a89ddd9e54697dab036a  d  =     6017 -     6392 
 *   21 Input abc6093               MD5 b892716e7eae5f8ddd9339f70842b358  d  =     6094 -     6392 
 *   22 Input abc6219               MD5 f1563b896d2ddda0f1f45b8adec413b7  d  =     6220 -     6392 
 *   23 Input abc7858               MD5 21dc11447df0bbab74d750bac5b66000  0  =     7859 -     8735 
 *   24 Input abc7918               MD5 feb714940e4bec85b85e1ae582fb0001  0  =     7919 -     8735 
 *   25 Input abc7937               MD5 9c62f9cddf7000fc82243fe63f633397  0  =     7938 -     8735 
 *   26 Input abc8205               MD5 3dbee9bccf49a50000346e221d54bb93  0  =     8206 -     8735 
 *   27 Input abc8407               MD5 66c6c08de62ab7000533b6b6450cf1f0  0  =     8408 -     8735 
 *   28 Input abc8431               MD5 b91baf5000515c05cd2300a88abe7e6e  0  =     8432 -     8735 
 *   29 Input abc8503               MD5 0a09e8a057d6912b5cd26d86000171f1  0  =     8504 -     8735 
 *   30 Input abc8626               MD5 1d000dd00e3653c29e0fa801d35de7b4  0  =     8627 -     8735 
 *   31 Input abc8672               MD5 0a164547f03ee46ece5f2c2e15580007  0  =     8673 -     8735 
 *   32 Input abc8730               MD5 5d4e1288f07a53bd6ad1a8000bca06eb  0  =     8731 -     8735 
 *   33 Input abc7833               MD5 fab1a818df14b380111ff51230f15f5b  1  =     7834 -     8811 
 *   34 Input abc8042               MD5 a508b217c92312d6a111af6253bff7bb  1  =     8043 -     8811 
 *   35 Input abc8045               MD5 1611103e70484e441e006f1bfb2b3374  1  =     8046 -     8811 
 *   36 Input abc8183               MD5 eda733b036c18c11134f8c60faacafc9  1  =     8184 -     8811 
 *   37 Input abc8189               MD5 cc1111a7b059f0326fe4d4fc96fb50d3  1  =     8190 -     8811 
 *   38 Input abc8232               MD5 f611148c30ba768ee1be17cccb00acda  1  =     8233 -     8811 
 *   39 Input abc8375               MD5 f11113032a16c4dfefcaa1d3eb43357c  1  =     8376 -     8811 
 *   40 Input abc8517               MD5 5bb71493cb844593059c9385af111498  1  =     8518 -     8811 
 *   41 Input abc8811               MD5 c3d313e7a72ea2111114dd4963979596  1  =     8812 -     9562 
 *   42 Input abc9497               MD5 2edf9fa11161b0286de6f62ee44e8e4d  1  =     9498 -     9562 
 *   43 Input abc9536               MD5 7253e53e81115f72f6540cae6a2a5e59  1  =     9537 -     9562 
 *   44 Input abc13268              MD5 bbbe024fb60e070c922a3c9dd81454c6  b  =    13269 -    13886 
 *   45 Input abc13439              MD5 bbbef7b3f98b65e148ae2a617786a11f  b  =    13440 -    13886 
 *   46 Input abc13479              MD5 2581d6a23925ef5f6c55241c821abbb3  b  =    13480 -    13886 
 *   47 Input abc13560              MD5 24826802dd3d78327eac3b7b02bbbf29  b  =    13561 -    13886 
 *   48 Input abc13663              MD5 c8e73ad52e71b460b2a2bebbb6efa8a8  b  =    13664 -    13886 
 *   49 Input abc15758              MD5 db94f13a807206bca93ea732ed877789  7  =    15759 -    16672 
 *   50 Input abc15883              MD5 8a77735462adb00c441a99571e351b7b  7  =    15884 -    16672 
 *   51 Input abc16187              MD5 a0872401e67ee77722ba756ba03cea2f  7  =    16188 -    16672 
 *   52 Input abc16342              MD5 561ad5b016b7771247e33564ffc3d7b2  7  =    16343 -    16672 
 *   53 Input abc16479              MD5 54a71b5c827b7bf88b67776339e1568d  7  =    16480 -    16672 
 *   54 Input abc20087              MD5 2663ce2f67f5d4e35aab8aaa84da292f  a  =    20088 -    20994 
 *   55 Input abc20371              MD5 714981a50246c55aaa14bc8baff48cf6  a  =    20372 -    20994 
 *   56 Input abc20582              MD5 3946e417e23aaabb3735a4551718ffff  a  =    20583 -    20994 
 *   57 Input abc20635              MD5 67e1d093a6cfe1ea8b40173aaad64273  a  =    20636 -    20994 
 *   58 Input abc20669              MD5 1f7eb500d1f09b3569a762ee5c8aaacd  a  =    20670 -    20994 
 *   59 Input abc21908              MD5 bf0d82c707319dd1934556e2914ccc9a  c  =    21909 -    22804 
 *   60 Input abc21927              MD5 74e0ccc5c8b7336be72d7dc1b9b36486  c  =    21928 -    22804 
 *   61 Input abc21978              MD5 3b1b8edb73ccc4cf49841d7e19b955d3  c  =    21979 -    22804 
 *   62 Input abc22023              MD5 705825e1986ccc196166f9d9d29c16bd  c  =    22024 -    22804 
 *   63 Input abc22193              MD5 00ee429b6acccdab81a4b6f2cd9ea278  c  =    22194 -    22804 
 *   64 Input abc22728              MD5 26ccc731a8706e0c4f979aeb341871f0  c  =    22729 -    22804 
 *   65 Input abc22804              MD5 1e15d83ba7591b79ccccc2e9a22f78b8  c  =    22805 -    23001 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 22804
 * Result Part 2 0
 * 
 * --------------------------------------------------------------------------------
 * Calculate - Salt = ahsbgdzn
 * 
 * 
 * MD5 with triple values 2582
 * MD5 with fiver values  9
 * 
 * 64 Keys:
 *    1 Input ahsbgdzn121           MD5 8ab9ec7eab0d7db90233351e7c5e6c60  3  =      122 -      380 
 *    2 Input ahsbgdzn125           MD5 e48f647c78899662cf72333f4e028780  3  =      126 -      380 
 *    3 Input ahsbgdzn198           MD5 0b1f6d4acf268333504200a68b975af5  3  =      199 -      380 
 *    4 Input ahsbgdzn229           MD5 fcb3332c8bbfa27af13fe6795b34ef5d  3  =      230 -      380 
 *    5 Input ahsbgdzn351           MD5 fef3336db9d7a13ae95ed605747e3abc  3  =      352 -      380 
 *    6 Input ahsbgdzn9058          MD5 87935273d1114174d7b74ef9ea089491  1  =     9059 -     9888 
 *    7 Input ahsbgdzn9069          MD5 c5044837d31fed01ee13c20e05511120  1  =     9070 -     9888 
 *    8 Input ahsbgdzn9086          MD5 ab3ffbe7c0d9fce0ca40a68025611124  1  =     9087 -     9888 
 *    9 Input ahsbgdzn9097          MD5 cbfe022364a6ff01cd56fee992f111a9  1  =     9098 -     9888 
 *   10 Input ahsbgdzn9107          MD5 11115a7a78f4782130667beafdc30593  1  =     9108 -     9888 
 *   11 Input ahsbgdzn9149          MD5 714319b111ba00d4b943be9446221f0d  1  =     9150 -     9888 
 *   12 Input ahsbgdzn9220          MD5 875c6a22683c6292013b8111f73a9bb9  1  =     9221 -     9888 
 *   13 Input ahsbgdzn9225          MD5 44ddcc0111ed27041699ed0a6984a9d2  1  =     9226 -     9888 
 *   14 Input ahsbgdzn9761          MD5 428331ec896a1a16c7e1f3e3d81115b3  1  =     9762 -     9888 
 *   15 Input ahsbgdzn9906          MD5 a2831dc9be0edcd559cf80755597b599  5  =     9907 -    10729 
 *   16 Input ahsbgdzn9974          MD5 2a27e74b41de398a4f555ba053f88e53  5  =     9975 -    10729 
 *   17 Input ahsbgdzn10031         MD5 cad349555ee5c4ddb57dc7af24a871d0  5  =    10032 -    10729 
 *   18 Input ahsbgdzn10042         MD5 55587c441053fb8e84efc61c49da1130  5  =    10043 -    10729 
 *   19 Input ahsbgdzn10368         MD5 0e1a0ef55516f2c1ccce2fc6d616947b  5  =    10369 -    10729 
 *   20 Input ahsbgdzn10431         MD5 1912063df82b30f1e4fe95554a0b0d71  5  =    10432 -    10729 
 *   21 Input ahsbgdzn10716         MD5 3c05b6373819e4bc96c15f28c59d8555  5  =    10717 -    10729 
 *   22 Input ahsbgdzn9945          MD5 fa4d9b9b7b5fe912ddd4ee22920b1550  d  =     9946 -    10866 
 *   23 Input ahsbgdzn9951          MD5 b6c35a2939922b40d43cddd24bcb76b5  d  =     9952 -    10866 
 *   24 Input ahsbgdzn9952          MD5 53c67a928ddde4e3c7d3faa49487a8e5  d  =     9953 -    10866 
 *   25 Input ahsbgdzn9973          MD5 52c59604ad3fc8f1866a5343ddd69f2b  d  =     9974 -    10866 
 *   26 Input ahsbgdzn9982          MD5 ca58e5455678dddbb47e2203f601bb6e  d  =     9983 -    10866 
 *   27 Input ahsbgdzn10176         MD5 d1bb1e092b577069fdddcc7ea6a60e59  d  =    10177 -    10866 
 *   28 Input ahsbgdzn10267         MD5 6373a42d55fcc1ab335ea12c90b8ddd9  d  =    10268 -    10866 
 *   29 Input ahsbgdzn10437         MD5 f8be41491591acd5fdddaeb96fa522ba  d  =    10438 -    10866 
 *   30 Input ahsbgdzn10511         MD5 8cdb56955c62ddd7b651594eb31aeef4  d  =    10512 -    10866 
 *   31 Input ahsbgdzn10612         MD5 5cd7dddd5c92d7a22442e24deb9a9fc7  d  =    10613 -    10866 
 *   32 Input ahsbgdzn10663         MD5 d55f56ec2539592dfddd0c8bffcae1fb  d  =    10664 -    10866 
 *   33 Input ahsbgdzn10804         MD5 c9e2eabe06e382c9ddd631b75e591a8f  d  =    10805 -    10866 
 *   34 Input ahsbgdzn10209         MD5 9e8d01faee2d1b0f751531fbc3be4449  4  =    10210 -    11102 
 *   35 Input ahsbgdzn10340         MD5 0a4445cf10bc95251fbfc9e90682c09c  4  =    10341 -    11102 
 *   36 Input ahsbgdzn10788         MD5 5f56a3b3daae66444020138badaaa629  4  =    10789 -    11102 
 *   37 Input ahsbgdzn11094         MD5 741304443e0c2aa9fa2e1e3826d225da  4  =    11095 -    11102 
 *   38 Input ahsbgdzn10443         MD5 cc2c33ca5f33db0ee410a7ce5dd22257  2  =    10444 -    11182 
 *   39 Input ahsbgdzn10489         MD5 7f6f82177222e8ac25e9cc98ff6421e1  2  =    10490 -    11182 
 *   40 Input ahsbgdzn10599         MD5 72832653e222888be98d47742731e4c0  2  =    10600 -    11182 
 *   41 Input ahsbgdzn10619         MD5 6d3f33657b5c63e7ab91f622235a59e7  2  =    10620 -    11182 
 *   42 Input ahsbgdzn10801         MD5 202a7615517c30e47a991c80da222c57  2  =    10802 -    11182 
 *   43 Input ahsbgdzn10844         MD5 392cbdcbe3a2790440222085ec3bff81  2  =    10845 -    11182 
 *   44 Input ahsbgdzn10896         MD5 d2226abade7c5d7c294dc8bdc2f4a6e4  2  =    10897 -    11182 
 *   45 Input ahsbgdzn11063         MD5 5a26224cf12749d12228b0d1bcd0fceb  2  =    11064 -    11182 
 *   46 Input ahsbgdzn11085         MD5 5e1cd0e0222342ba3bca784c2e4e2b09  2  =    11086 -    11182 
 *   47 Input ahsbgdzn13215         MD5 8bf14e8c7d4e999268df3525adf1beb8  9  =    13216 -    14055 
 *   48 Input ahsbgdzn13264         MD5 becbafc3098594c116f999d304bce24b  9  =    13265 -    14055 
 *   49 Input ahsbgdzn13579         MD5 28982fb10b269591fa88124a89993ce6  9  =    13580 -    14055 
 *   50 Input ahsbgdzn13612         MD5 658d2cf9327a1693c89991f335a67ec5  9  =    13613 -    14055 
 *   51 Input ahsbgdzn13716         MD5 0a1e5e99931a28b5b7d10805afcf3102  9  =    13717 -    14055 
 *   52 Input ahsbgdzn13732         MD5 b9999f43d74ae534495e7df6953dabcd  9  =    13733 -    14055 
 *   53 Input ahsbgdzn14017         MD5 fde5db9b84042b159999fb9caec7be88  9  =    14018 -    14055 
 *   54 Input ahsbgdzn20413         MD5 f5f49ebb85375510bb27692eee79bd5f  e  =    20414 -    21398 
 *   55 Input ahsbgdzn20548         MD5 38c72a1cc53beee0ab24534ec14a7de4  e  =    20549 -    21398 
 *   56 Input ahsbgdzn20697         MD5 0658b9e0eb75eee0b5ad23123f96f6b5  e  =    20698 -    21398 
 *   57 Input ahsbgdzn20959         MD5 599b1032dde92eeebcab45e8dff1eed3  e  =    20960 -    21398 
 *   58 Input ahsbgdzn21174         MD5 ebea71d0d27eee6f8e4e201ffd4ce279  e  =    21175 -    21398 
 *   59 Input ahsbgdzn21303         MD5 8d2d58e04f3ecc67adc39b02eee4e037  e  =    21304 -    21398 
 *   60 Input ahsbgdzn21345         MD5 ccaa4b3a36d0ff030df70f72e07eeee7  e  =    21346 -    21398 
 *   61 Input ahsbgdzn23569         MD5 011181f56f8f39a501824c3fd9fcc37e  1  =    23570 -    24567 
 *   62 Input ahsbgdzn23629         MD5 6c63120aeec886833b5a1117cf48de8e  1  =    23630 -    24567 
 *   63 Input ahsbgdzn23745         MD5 6de520a7a4deaf9e0a2111357a361e01  1  =    23746 -    24567 
 *   64 Input ahsbgdzn23890         MD5 d8877e9fef1114a916fe767302dc6c31  1  =    23891 -    24567 
 *   65 Input ahsbgdzn24052         MD5 6aa10e3e482e2111c28be07cdf124282  1  =    24053 -    25001 
 *   66 Input ahsbgdzn24171         MD5 111b79c268c04c5f0b9e0f7672eb1750  1  =    24172 -    25001 
 *   67 Input ahsbgdzn24255         MD5 ff4b1b752111ba4d6bc5c8e9bf1873f3  1  =    24256 -    25001 
 *   68 Input ahsbgdzn24438         MD5 d1b6a8fd11140fb64209cb9714b6c7e7  1  =    24439 -    25001 
 *   69 Input ahsbgdzn24567         MD5 80553259ef75930679721111142898a0  1  =    24568 -    25001 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 24567
 * Result Part 2 0
 * 
 * abc18 = 0034e0923cc38887a57bd7b1d4f953df
 * abc39 = 347dac6ee8eeea4652c7476d0f97bee5
 * abc92 = ae2e85dd75d63e916a525df95e999ea0
 * abc200 = 83501e9109999965af11270ef8d61a4f
 * 
 * </pre> 
 */
public class Day14_OneTimePad
{

  public static void main( String[] args )
  {
    calculate01( "abc", 23_000, true );

    calculate01( "ahsbgdzn", 25_000, false );

    testMD5( "abc18" );
    testMD5( "abc39" );
    testMD5( "abc92" );
    testMD5( "abc200" );

    System.exit( 0 );
  }

  private static void calculate01( String pSalt, int pMaxLoop, boolean pKnzDebug )
  {
    int result_part_01 = 0;
    int result_part_02 = 0;

    wl( "--------------------------------------------------------------------------------" );
    wl( "Calculate - Salt = " + pSalt );
    wl( "" );

    List< Day14MD5 > key_list = new ArrayList< Day14MD5 >();;

    HashMap< String, List< Day14MD5 > > hash_map = new HashMap< String, List< Day14MD5 > >();

    int count_triple_values = 0;
    int count_fiver_values = 0;
    int cur_index = 0;

    while ( cur_index < pMaxLoop )
    {
      String cur_md5 = getMD5( pSalt + cur_index );

      String triple_char = getFirstTriple( cur_md5 );

      String all_five = getAllFive( cur_md5 );

      if ( triple_char != null )
      {
        count_triple_values++;

        if ( pKnzDebug )
        {
          //wl( String.format( "Index %7d  %-15s = %s  = %s  %s ", cur_triple_count, pSalt + cur_index, cur_md5, triple_char, all_five ) );
        }

        List< Day14MD5 > list_triple_char = hash_map.get( triple_char );

        if ( list_triple_char == null )
        {
          list_triple_char = new ArrayList< Day14MD5 >();

          hash_map.put( triple_char, list_triple_char );
        }

        list_triple_char.add( new Day14MD5( cur_index, pSalt + cur_index, cur_md5, triple_char ) );

        if ( all_five != null )
        {
          count_fiver_values++;

          for ( Day14MD5 cur_day14_md5 : list_triple_char )
          {
            if ( cur_day14_md5.checkActive( cur_index ) )
            {
              if ( cur_day14_md5.checkTripleChar( all_five ) )
              {
                cur_day14_md5.setNumberTo( cur_index );

                key_list.add( cur_day14_md5 );

                if ( key_list.size() == 64 )
                {
                  result_part_01 = cur_index;

                  cur_index = pMaxLoop + 1;
                }

              }
            }
          }
        }
      }

      cur_index++;
    }

    wl( "" );
    wl( "MD5 with triple values " + count_triple_values );
    wl( "MD5 with fiver values  " + count_fiver_values );
    wl( "" );
    wl( "64 Keys:" );

    int nr = 1;

    for ( Day14MD5 cur_day14_md5 : key_list )
    {
      wl( String.format( "%4d %s", nr, cur_day14_md5.toString() ) );

      nr++;
    }

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static String getFirstTriple( String pInput )
  {
    for ( int idx = 2; idx < pInput.length(); idx++ )
    {
      if ( ( pInput.charAt( idx ) == pInput.charAt( idx - 1 ) ) && ( pInput.charAt( idx ) == pInput.charAt( idx - 2 ) ) )
      {
        return "" + pInput.charAt( idx );
      }
    }

    return null;
  }

  private static String getAllFive( String pInput )
  {
    String str_result_5er = null;

    for ( int idx = 4; idx < pInput.length(); idx++ )
    {
      if ( ( pInput.charAt( idx ) == pInput.charAt( idx - 1 ) ) && ( pInput.charAt( idx ) == pInput.charAt( idx - 2 ) ) )
      {
        if ( ( pInput.charAt( idx ) == pInput.charAt( idx - 3 ) ) && ( pInput.charAt( idx ) == pInput.charAt( idx - 4 ) ) )
        {
          if ( str_result_5er == null )
          {
            str_result_5er = "";
          }

          str_result_5er += pInput.charAt( idx ) + "";
        }
      }
    }

    return str_result_5er;
  }

  private static String testMD5( String pInput )
  {
    String md5_hash = getMD5( pInput );

    wl( pInput + " = " + md5_hash );

    return md5_hash;
  }

  private static String getMD5( String pInput )
  {
    try
    {
      MessageDigest md = MessageDigest.getInstance( "MD5" );

      byte[] md5_digest = md.digest( pInput.getBytes( "UTF-8" ) );

      StringBuilder hex_string = new StringBuilder();

      for ( byte cur_byte : md5_digest )
      {
        //hex_string.append( String.format( "%x", cur_byte ) ); // false if value is 0, you get only a 0

        //String hex_number = Integer.toHexString( 0xFF & cur_byte ); // uneccessary, because input cur_byte is already byte (=below 256)

        String hex_number = Integer.toHexString( 0xFF & cur_byte );

        if ( hex_number.length() == 1 ) hex_string.append( '0' );

        hex_string.append( hex_number );
      }

      return hex_string.toString();
    }
    catch ( Exception e )
    {
      wl( e.getMessage() );
    }

    return null;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
