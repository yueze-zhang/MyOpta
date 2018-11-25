************************************************************************
file with basedata            : md360_.bas
initial value random generator: 185478677
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  22
horizon                       :  150
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     20      0       18        9       18
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3           5   9  14
   3        3          2          10  13
   4        3          3           6   7  11
   5        3          1           6
   6        3          3          10  15  20
   7        3          3           8   9  12
   8        3          1          18
   9        3          1          18
  10        3          3          16  17  18
  11        3          2          14  16
  12        3          3          16  17  20
  13        3          2          17  21
  14        3          3          15  19  20
  15        3          1          21
  16        3          1          19
  17        3          1          19
  18        3          1          21
  19        3          1          22
  20        3          1          22
  21        3          1          22
  22        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     6       9    3    9    6
         2     7       7    3    8    5
         3     8       5    2    7    2
  3      1     1       7   10    2    7
         2     4       3    8    2    6
         3     6       3    6    1    6
  4      1     1       7    7    4    4
         2     4       3    5    3    3
         3     9       2    1    1    2
  5      1     1       8    8    6    5
         2     3       6    5    5    4
         3     8       3    4    4    4
  6      1     1       4    8    9    5
         2     4       3    7    9    3
         3     6       1    7    9    3
  7      1     4       5    3    8    5
         2     8       4    2    8    5
         3    10       4    2    7    4
  8      1     1       6    9    2    3
         2     1       6    7    2    4
         3     3       6    2    1    2
  9      1     1       5    4    7    7
         2     3       5    4    6    4
         3     6       4    4    5    2
 10      1     2       5    5    6    4
         2     4       4    4    5    2
         3     7       2    3    5    2
 11      1     3       8    4    3    2
         2     5       7    4    3    2
         3     6       7    2    2    1
 12      1     2      10    9   10    8
         2     8       7    8    9    8
         3     9       5    7    9    7
 13      1     1       9    7    5    9
         2     4       5    5    4    6
         3     8       5    3    3    4
 14      1     3       4    3    8    7
         2     5       4    3    7    7
         3     6       1    1    3    6
 15      1     1      10    8   10    8
         2     4       7    8    8    7
         3     8       7    8    7    3
 16      1     7       5    3    9    7
         2     9       4    3    9    7
         3    10       3    2    6    3
 17      1     2       5    7    6    5
         2     4       4    6    5    5
         3    10       4    5    3    4
 18      1     2      10    4    9    7
         2     4      10    3    6    4
         3     8       9    3    4    4
 19      1     1       6    9    9    5
         2     2       6    7    5    5
         3    10       6    6    5    5
 20      1     1       8    8    8    6
         2     4       8    4    8    6
         3     7       8    3    6    5
 21      1     3       6    6    7    4
         2     4       4    5    6    3
         3     5       3    5    6    2
 22      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   31   24  105   82
************************************************************************
