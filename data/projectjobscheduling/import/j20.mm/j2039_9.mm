************************************************************************
file with basedata            : md359_.bas
initial value random generator: 673684006
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  22
horizon                       :  164
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     20      0       20        8       20
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3          16  17  19
   3        3          1           8
   4        3          3           5   6  11
   5        3          2           9  13
   6        3          3           7  12  13
   7        3          2          15  18
   8        3          3           9  11  15
   9        3          1          10
  10        3          1          14
  11        3          1          16
  12        3          2          17  21
  13        3          2          15  18
  14        3          3          17  18  19
  15        3          2          16  19
  16        3          2          20  21
  17        3          1          20
  18        3          2          20  21
  19        3          1          22
  20        3          1          22
  21        3          1          22
  22        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     6       3    7    6    6
         2    10       2    6    3    3
         3    10       1    6    5    3
  3      1     3       9    4   10    5
         2     4       9    3   10    3
         3    10       9    3    9    2
  4      1     3       6    2    9    8
         2     7       4    2    7    5
         3    10       3    1    7    4
  5      1     1       4    3    4    8
         2     4       3    3    4    6
         3     8       2    3    2    3
  6      1     1       5   10    1    5
         2     6       3    7    1    4
         3     8       2    4    1    2
  7      1     2       5    6    6    5
         2     3       5    6    4    5
         3     7       3    6    4    4
  8      1     2       4    6    3    9
         2     8       4    5    2    1
         3     8       2    5    2    3
  9      1     4       8    7   10    6
         2     4       7    6   10    7
         3     8       6    6    9    4
 10      1     1       5    6    9    8
         2     3       4    4    6    7
         3    10       3    3    3    7
 11      1     5       4    8    6    8
         2     9       4    8    4    5
         3     9       4    7    2    7
 12      1     1       9    8    8    8
         2     6       8    6    7    6
         3     9       8    6    6    5
 13      1     1       6    8    9    8
         2     3       6    4    6    8
         3     5       6    4    6    7
 14      1     2       8   10    4    9
         2     6       8    9    3    9
         3     9       4    9    2    9
 15      1     3       9    5    8   10
         2     9       4    3    5    3
         3     9       4    4    6    2
 16      1     1       7   10    8    9
         2     6       5    8    6    7
         3     9       4    6    5    7
 17      1     1       4   10    8    5
         2     5       4    8    6    3
         3     7       1    7    4    2
 18      1     1       6    6    5    8
         2     2       6    4    4    7
         3     3       6    3    4    7
 19      1     8       6    8    5   10
         2     9       5    7    5   10
         3    10       4    2    4    9
 20      1     1       7    6    5    7
         2     5       6    6    2    6
         3     5       6    5    4    5
 21      1     3       9    7    7    9
         2     8       9    4    6    8
         3    10       8    1    6    5
 22      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   23   29   97  108
************************************************************************
