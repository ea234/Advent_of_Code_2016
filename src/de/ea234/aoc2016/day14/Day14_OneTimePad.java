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
 * 
 * --------------------------------------------------------------------------------
 * Calculate - Salt = abc  Key Stretching Value -1
 * 
 * 
 * Calculation Time 00:00:00:139
 * 
 * MD5 with triple values 2315
 * MD5 with fiver values  12
 * 
 * cur_index end          22805
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
 * --------------------------------------------------------------------------------
 * Calculate - Salt = abc  Key Stretching Value 2016
 * 
 * 
 * Calculation Time 00:00:20:453
 * 
 * MD5 with triple values 2386
 * MD5 with fiver values  10
 * 
 * cur_index end          22860
 * 
 * 64 Keys:
 *    1 Input abc10                 MD5 4a81e578d9f43511ab693eee1a75f194  e  =       11 -       89 
 *    2 Input abc25                 MD5 820c37e4d1eee5bb88ee3c5ff5cb702b  e  =       26 -       89 
 *    3 Input abc1471               MD5 6289d4f7290ced8167dcd2c2c555b683  5  =     1472 -     2359 
 *    4 Input abc1596               MD5 64939f3ed716055553d763ff5c93987c  5  =     1597 -     2359 
 *    5 Input abc1610               MD5 e9e3ead514e3e429d3a9d555ed5c550d  5  =     1611 -     2359 
 *    6 Input abc1715               MD5 2518114d108d3b3f04cedecfa47555f4  5  =     1716 -     2359 
 *    7 Input abc1778               MD5 45557cf33e0f0cb65398e8b8f3d6d627  5  =     1779 -     2359 
 *    8 Input abc1951               MD5 2542fd1fa8cf29cac7244d6555cdfc5f  5  =     1952 -     2359 
 *    9 Input abc1994               MD5 58488e6a50b9ad281f5519063446555f  5  =     1995 -     2359 
 *   10 Input abc2023               MD5 b7da7c35555808dace36f70a9f51e0d7  5  =     2024 -     2359 
 *   11 Input abc2288               MD5 71aa39c0555c4752c7d268fb94ad52e4  5  =     2289 -     2359 
 *   12 Input abc4249               MD5 6551f92888e08fc70ccaa77ace34b30e  8  =     4250 -     5057 
 *   13 Input abc4340               MD5 8093312888dbb47218be3cfb841e434c  8  =     4341 -     5057 
 *   14 Input abc4352               MD5 8888c046d466aeae42e4d019975ad9a8  8  =     4353 -     5057 
 *   15 Input abc4533               MD5 c8d138f21926803b981adc8458888669  8  =     4534 -     5057 
 *   16 Input abc4829               MD5 81100288842f9216345b1e52ed79e3d8  8  =     4830 -     5057 
 *   17 Input abc4878               MD5 4fbdd7e88827ea0e20fcb87bf8a306a8  8  =     4879 -     5057 
 *   18 Input abc5014               MD5 4fde6888f112e31cd4fb497cfe623a6e  8  =     5015 -     5057 
 *   19 Input abc5226               MD5 222bfc34bc1cff7ba607759f4e0b173f  2  =     5227 -     6115 
 *   20 Input abc5803               MD5 11022216d71dbcb33700c7c1f206844d  2  =     5804 -     6115 
 *   21 Input abc6878               MD5 817962a35d167111f70b3294d57784b9  1  =     6879 -     7859 
 *   22 Input abc7087               MD5 94b1649acb0111bdc4c165d8e6ddfcd1  1  =     7088 -     7859 
 *   23 Input abc7120               MD5 c7c00fdd15a03111d7c9469455b3a6b7  1  =     7121 -     7859 
 *   24 Input abc7137               MD5 c11149bea1b570e0719f18d8573c343a  1  =     7138 -     7859 
 *   25 Input abc7182               MD5 6d358b51113a97fcacf6d9bfe21127e0  1  =     7183 -     7859 
 *   26 Input abc7229               MD5 5193df6da97e9b704d949a111c0f03cd  1  =     7230 -     7859 
 *   27 Input abc7280               MD5 3280960511115153889b504329958f6c  1  =     7281 -     7859 
 *   28 Input abc7317               MD5 d929e2162a631118017a96399123ee80  1  =     7318 -     7859 
 *   29 Input abc7460               MD5 cd2641116c72e6bfc159e1684677dd2c  1  =     7461 -     7859 
 *   30 Input abc7841               MD5 0082dac77b407bf3f8c08111c6bbabbf  1  =     7842 -     7859 
 *   31 Input abc8058               MD5 1d15fb855ec6d39176665ad514280524  6  =     8059 -     8948 
 *   32 Input abc8277               MD5 cf33566686cda2ce7512d9f3029c1eb1  6  =     8278 -     8948 
 *   33 Input abc8472               MD5 666296fefc66efa8d0fd7da07facdfd0  6  =     8473 -     8948 
 *   34 Input abc8473               MD5 5066407cd811c345566682763cb77eeb  6  =     8474 -     8948 
 *   35 Input abc8487               MD5 6854b36662d6a7d0cd26a6fae67a44a8  6  =     8488 -     8948 
 *   36 Input abc8550               MD5 3c2049afa55bd105fea3a6665202714f  6  =     8551 -     8948 
 *   37 Input abc8574               MD5 6667a14cf62acefe548bdabe9d225c88  6  =     8575 -     8948 
 *   38 Input abc17372              MD5 606d863cccd166712df9042e763c63eb  c  =    17373 -    18355 
 *   39 Input abc17438              MD5 9bae8651b79dccca68cde3ce61db085b  c  =    17439 -    18355 
 *   40 Input abc17506              MD5 ccc94ee9c26b54acf62001fc9afea402  c  =    17507 -    18355 
 *   41 Input abc17737              MD5 21ed8ccc08cd444dfe408910c4a259b5  c  =    17738 -    18355 
 *   42 Input abc17768              MD5 12ec5c1ccc1d1d0e75fe20387ae9fbc3  c  =    17769 -    18355 
 *   43 Input abc18105              MD5 958d4f237fccc31244fd7de6873f6b06  c  =    18106 -    18355 
 *   44 Input abc18175              MD5 2b830df74310065dcccc96de4f292d55  c  =    18176 -    18355 
 *   45 Input abc18210              MD5 1582290ccce23b062b49cf3c04e041fd  c  =    18211 -    18355 
 *   46 Input abc18302              MD5 b3405a15ac7a812634c5053f8b9a3ccc  c  =    18303 -    18355 
 *   47 Input abc19212              MD5 d0a40cd876e7c8223c55507ab0ab6d73  5  =    19213 -    20015 
 *   48 Input abc19236              MD5 71ee27eede6355513dac7785d0de4de7  5  =    19237 -    20015 
 *   49 Input abc19270              MD5 ab3ac53ca55593609ba138efb4ea6346  5  =    19271 -    20015 
 *   50 Input abc19334              MD5 d26af0f588f902182555c3b1a7578472  5  =    19335 -    20015 
 *   51 Input abc19346              MD5 272dca3fc5d0c059808ea55592a4adcd  5  =    19347 -    20015 
 *   52 Input abc19369              MD5 f1b555c4e43773758a84176bc78d8a63  5  =    19370 -    20015 
 *   53 Input abc19471              MD5 a3a52907cacf6e41a315f20b46555422  5  =    19472 -    20015 
 *   54 Input abc19498              MD5 b6c4ffe1dac975558234705709a0854d  5  =    19499 -    20015 
 *   55 Input abc19880              MD5 055578105213a1cd3de06696d308d0a6  5  =    19881 -    20015 
 *   56 Input abc19366              MD5 37d877d32bdbc3580db5afcfa7666339  6  =    19367 -    20330 
 *   57 Input abc19799              MD5 d626ebe07e35bdd64658de522845666d  6  =    19800 -    20330 
 *   58 Input abc19915              MD5 109096f32bc54743c76666499f47cf8b  6  =    19916 -    20330 
 *   59 Input abc19947              MD5 f90405a11032ec10f1f62b666b68365b  6  =    19948 -    20330 
 *   60 Input abc19950              MD5 54c6665cd0f258a2ea8e27cec15685e2  6  =    19951 -    20330 
 *   61 Input abc20212              MD5 4433056aabcfb040e819ca66650c9669  6  =    20213 -    20330 
 *   62 Input abc22097              MD5 07b076affcb182df3fe86ba5fff65bff  f  =    22098 -    22859 
 *   63 Input abc22122              MD5 a5fff1f24a5db986274fe724dc3d9102  f  =    22123 -    22859 
 *   64 Input abc22551              MD5 2df6e9378c3c53abed6d3508b6285fff  f  =    22552 -    22859 
 *   65 Input abc22628              MD5 0ce2bc768e26bfc3afff95e8280f0ff8  f  =    22629 -    22859 
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 22728
 * Result Part 2 22551
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * Result Part 1 23890
 * Result Part 2 22696 
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

    calculate01( "ahsbgdzn", 255_000_000, false );

    testMD5( "abc18" );
    testMD5( "abc39" );
    testMD5( "abc92" );
    testMD5( "abc200" );

    System.exit( 0 );
  }

  private static void calculate01( String pSalt, int pMaxLoop, boolean pKnzDebug )
  {
    int result_part_01 = calculateList( pSalt,   -1, pMaxLoop, pKnzDebug );
    int result_part_02 = calculateList( pSalt, 2016, pMaxLoop, pKnzDebug );

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static int calculateList( String pSalt, int pKeyStretchingValue, int pMaxLoop, boolean pKnzDebug )
  {
    long start_time = System.currentTimeMillis();

    wl( "--------------------------------------------------------------------------------" );
    wl( "Calculate - Salt = " + pSalt + "  Key Stretching Value " + pKeyStretchingValue );
    wl( "" );

    List< Day14MD5 > key_list = new ArrayList< Day14MD5 >();;

    HashMap< String, List< Day14MD5 > > hash_map = new HashMap< String, List< Day14MD5 > >();

    int count_triple_values = 0;
    int count_fiver_values  = 0;

    int result_index = 0;

    int cur_index = 0;

    while ( ( result_index == 0 ) && ( cur_index < pMaxLoop ) )
    {
      String cur_md5 = getMD5( pSalt + cur_index );

      if ( pKeyStretchingValue > 0 )
      {
        cur_md5 = getMD5KeyStretching( cur_md5, pKeyStretchingValue );
      }

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
                  result_index = cur_day14_md5.getIndexNumber();
                }
              }
            }
          }
        }
      }

      cur_index++;
    }

    long end_time = System.currentTimeMillis();

    wl( "" );
    wl( "Calculation Time       " + getDebugLaufzeit( end_time - start_time ) );
    wl( "" );
    wl( "MD5 with triple values " + count_triple_values );
    wl( "MD5 with fiver values  " + count_fiver_values  );
    wl( "" );
    wl( "cur_index end          " + cur_index );
    wl( "" );
    wl( "64 Keys:" );

    int nr = 1;

    for ( Day14MD5 cur_day14_md5 : key_list )
    {
      wl( String.format( "%4d %s", nr, cur_day14_md5.toString() ) );

      nr++;
    }

    return result_index;
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

  private static String getMD5KeyStretching( String pInput, int pAmount )
  {
    String cur_hash = pInput;

    for ( int x = 0; x < pAmount; x++ )
    {
      cur_hash = getMD5( cur_hash );
    }

    return cur_hash;
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
    catch ( Exception err_inst )
    {
      wl( err_inst.getMessage() );
    }

    return null;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }

  private static String getDebugLaufzeit( long pAnzahlMillisekunden )
  {
    long zeit_differenz_betrag = pAnzahlMillisekunden;

    long m_laufzeit_stunden = 0;
    long m_laufzeit_minuten = 0;
    long m_laufzeit_sekunden = 0;
    long m_laufzeit_milli_s = 0;

    if ( zeit_differenz_betrag > 0 )
    {
      m_laufzeit_milli_s = (long) ( zeit_differenz_betrag % 1000 );

      zeit_differenz_betrag /= 1000;

      m_laufzeit_sekunden = (long) ( zeit_differenz_betrag % 60 );

      zeit_differenz_betrag /= 60;

      m_laufzeit_minuten = (long) ( zeit_differenz_betrag % 60 );

      m_laufzeit_stunden = (long) zeit_differenz_betrag / 60;
    }

    return ( m_laufzeit_stunden < 10 ? "0" : "" ) + m_laufzeit_stunden + ":" + ( m_laufzeit_minuten < 10 ? "0" : "" ) + m_laufzeit_minuten + ":" + ( m_laufzeit_sekunden < 10 ? "0" : "" ) + m_laufzeit_sekunden + ":" + ( m_laufzeit_milli_s < 10 ? "00" : ( m_laufzeit_milli_s < 100 ? "0" : "" ) ) + m_laufzeit_milli_s;
  }
}
