/*
 *   Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License").
 *   You may not use this file except in compliance with the License.
 *   A copy of the License is located at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file. This file is distributed
 *   on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 *   express or implied. See the License for the specific language governing
 *   permissions and limitations under the License.
 */

lexer grammar OpenDistroPPLLexer;

channels { WHITESPACE, ERRORCHANNEL }


// COMMAND KEYWORDS
SEARCH:                             'SEARCH';
FROM:                               'FROM';
WHERE:                              'WHERE';
FIELDS:                             'FIELDS';
RENAME:                             'RENAME';
STATS:                              'STATS';
DEDUP:                              'DEDUP';
SORT:                               'SORT';
EVAL:                               'EVAL';

// COMMAND ASSIST KEYWORDS
AS:                                 'AS';
BY:                                 'BY';
SOURCE:                             'SOURCE';
INDEX:                              'INDEX';
D:                                  'D';
DESC:                               'DESC';

// CLAUSE KEYWORDS
SORTBY:                             'SORTBY';

// FIELD KEYWORDS
AUTO:                               'AUTO';
STR:                                'STR';
IP:                                 'IP';
NUM:                                'NUM';

// ARGUMENT KEYWORDS
KEEPEMPTY:                          'KEEPEMPTY';
CONSECUTIVE:                        'CONSECUTIVE';
DEDUP_SPLITVALUES:                  'DEDUP_SPLITVALUES';
PARTITIONS:                         'PARTITIONS';
ALLNUM:                             'ALLNUM';
DELIM:                              'DELIM';

// COMPARISON FUNCTION KEYWORDS
CASE:                               'CASE';
IN:                                 'IN';

// LOGICAL KEYWORDS
NOT:                                'NOT';
OR:                                 'OR';
AND:                                'AND';
XOR:                                'XOR';
TRUE:                               'TRUE';
FALSE:                              'FALSE';

// DATASET TYPES
DATAMODEL:                          'DATAMODEL';
LOOKUP:                             'LOOKUP';
SAVEDSEARCH:                        'SAVEDSEARCH';

// SPECIAL CHARACTERS AND OPERATORS
PIPE:                               '|';
COMMA:                              ',';
DOT:                                '.';
EQUAL:                              '=';
GREATER:                            '>';
LESS:                               '<';
NOT_GREATER:                        '<' '=';
NOT_LESS:                           '>' '=';
NOT_EQUAL:                          '!' '=';
PLUS:                               '+';
MINUS:                              '-';
STAR:                               '*';
DIVIDE:                             '/';
MODULE:                             '%';
EXCLAMATION_SYMBOL:                 '!';
COLON:                              ':';
LT_PRTHS:                           '(';
RT_PRTHS:                           ')';
LT_SQR_PRTHS:                       '[';
RT_SQR_PRTHS:                       ']';
SINGLE_QUOTE:                       '\'';
DOUBLE_QUOTE:                       '"';
BACKTICK:                           '`';

// AGGREGATIONS
AVG:                                'AVG';
COUNT:                              'COUNT';
SUM:                                'SUM';


// BASIC FUNCTIONS
ABS:                                'ABS';
CEIL:                               'CEIL';
CEILING:                            'CEILING';
EXACT:                              'EXACT';
EXP:                                'EXP';
FLOOR:                              'FLOOR';
LN:                                 'LN';
LOG:                                'LOG';
PI:                                 'PI';
POW:                                'POW';
POWER:                              'POWER';
ROUND:                              'ROUND';
SIGFIG:                             'SIGFIG';
SQRT:                               'SQRT';

// ES FUNCTIONS
DATE_HISTOGRAM:                     'DATE_HISTOGRAM';
DAY_OF_MONTH:                       'DAY_OF_MONTH';
DAY_OF_YEAR:                        'DAY_OF_YEAR';
DAY_OF_WEEK:                        'DAY_OF_WEEK';
EXCLUDE:                            'EXCLUDE';
EXTENDED_STATS:                     'EXTENDED_STATS';
FIELD:                              'FIELD';
FILTER:                             'FILTER';
GEO_BOUNDING_BOX:                   'GEO_BOUNDING_BOX';
GEO_CELL:                           'GEO_CELL';
GEO_DISTANCE:                       'GEO_DISTANCE';
GEO_DISTANCE_RANGE:                 'GEO_DISTANCE_RANGE';
GEO_INTERSECTS:                     'GEO_INTERSECTS';
GEO_POLYGON:                        'GEO_POLYGON';
HISTOGRAM:                          'HISTOGRAM';
HOUR_OF_DAY:                        'HOUR_OF_DAY';
INCLUDE:                            'INCLUDE';
IN_TERMS:                           'IN_TERMS';
MATCHPHRASE:                        'MATCHPHRASE';
MATCH_PHRASE:                       'MATCH_PHRASE';
MATCHQUERY:                         'MATCHQUERY';
MATCH_QUERY:                        'MATCH_QUERY';
MINUTE_OF_DAY:                      'MINUTE_OF_DAY';
MINUTE_OF_HOUR:                     'MINUTE_OF_HOUR';
MONTH_OF_YEAR:                      'MONTH_OF_YEAR';
MULTIMATCH:                         'MULTIMATCH';
MULTI_MATCH:                        'MULTI_MATCH';
NESTED:                             'NESTED';
PERCENTILES:                        'PERCENTILES';
REGEXP_QUERY:                       'REGEXP_QUERY';
REVERSE_NESTED:                     'REVERSE_NESTED';
QUERY:                              'QUERY';
SCORE:                              'SCORE';
SECOND_OF_MINUTE:                   'SECOND_OF_MINUTE';
TERM:                               'TERM';
TERMS:                              'TERMS';
TOPHITS:                            'TOPHITS';
WEEK_OF_YEAR:                       'WEEK_OF_YEAR';
WILDCARDQUERY:                      'WILDCARDQUERY';
WILDCARD_QUERY:                     'WILDCARD_QUERY';

// LITERALS AND VALUES
//STRING_LITERAL:                     DQUOTA_STRING | SQUOTA_STRING | BQUOTA_STRING;
ID:                                 ID_LITERAL;
INTEGER_LITERAL:                    DEC_DIGIT+;
DECIMAL_LITERAL:                    (DEC_DIGIT+)? '.' DEC_DIGIT+;

fragment ID_LITERAL:                [A-Z_$0-9@]*?[A-Z_$\-]+?[A-Z_$\-0-9]*;
DQUOTA_STRING:                      '"' ( '\\'. | '""' | ~('"'| '\\') )* '"';
SQUOTA_STRING:                      '\'' ('\\'. | '\'\'' | ~('\'' | '\\'))* '\'';
BQUOTA_STRING:                      '`' ( '\\'. | '``' | ~('`'|'\\'))* '`';
fragment DEC_DIGIT:                 [0-9];


ERROR_RECOGNITION:                  .    -> channel(ERRORCHANNEL);