
===========================DOCUMENTATION===========================

*************************************************************************************************************************************

Information about pattern and responses:

1. "patterns.txt"

    1.1 File format

    Each line of this file contains a patterns and the corresponding response of the format:
        <pattern> -> <response>
    where pattern is the pattern and response is the corresponding response.

    For example, take a look at the following:
        \bmother\b|\bmom\b|\bmommy\b -> What makes you think of your mother?
    -In here, "\bmother\b|\bmom\b|\bmommy\b" is the pattern and "What makes you think of your mother?" is the corresponding response

    CAUTION:
    -Do not forget the spaces before and after "->"

    ===================================================================================================================

    1.2 Pattern documentation

    First of all, please keep in mind that these patterns are case insensitive
    (only the patterns though! the responses are still case sensitive).

    1. Angry pattern
    \bfuck\b|\bshit\b|\bbitch\b -> I will terminate if you're going to be like that!
    Description: Any sentence containing the word "fuck", "shit", or "bitch"

    2. Hello pattern
    \bhi\b|\bhello\b|\bhey\b -> How are you? What would you like to talk about today?
    Description: Any sentence containing the word "hi", "hello", or "hey"

    3. Mother pattern
    \bmother\b|\bmom\b|\bmommy\b -> What makes you think of your mother?
    Description: Any sentence containing the word "mother", "mom", or "mommy"

    4. Father pattern
    \bfather\b|\bdad\b|\bdaddy\b -> What makes you think of your father?
    Description: Any sentence containing the word "father", "dad", or "daddy"

    5. Sad pattern
    \bsad\b|\bdepressed\b -> What makes you sad?
    Description: Any sentence containing the word "sad" or "depressed"

    6. Hate pattern
    \bhate\b|\bloathe\b|\bdetest\b|\bdespise\b -> Hate is not a good thing..
    Description: Any sentence containing the word "hate", "loathe", "detest", or "despise"

    7. Study pattern
    \bstudy\b|\bstudying\b -> Well, I hate studying. Studying = student + dying.
    Description: Any sentence containing the word "study" or "studying"

    8. I hate X pattern
    \bi\shate\s%X%\b -> What makes you hate %X%? Did %X% do anything wrong?
    Description: Any sentence containing the string "i hate X" where X is a placeholder word

    9. I loathe X pattern
    \bi\sloathe\s%X%\b -> What makes you loathe %X%? Did %X% do anything wrong?
    Description: Any sentence containing the string "i loathe X" where X is a placeholder word

    10. I detest X pattern
    \bi\sdetest\s%X%\b -> What makes you detest %X%? Did %X% do anything wrong?
    Description: Any sentence containing the string "i detest X" where X is a placeholder word

    11. I despise X pattern
    \bi\sdespise\s%X%\b -> What makes you despise %X%? Did %X% do anything wrong?
    Description: Any sentence containing the string "i despise X" where X is a placeholder word

    12. I like X pattern
    \bi\slike\s%X%\b -> What makes you like %X%? Does %X% interests you in a particular way?
    Description: Any sentence containing the string "i like X" where X is a placeholder word

    13. I love X pattern
    \bi\slove\s%X%\b -> Oh you're in love with %X% are you? Is %X% a person?
    Description: Any sentence containing the string "i love X" where X is a placeholder word

    14. A is B pattern
    \b%A%\sis\s%B%\b -> Why do you think that %A% is %B%?
    Description: Any sentence containing any placeholder word A followed by the word "is" followed by any placeholder word B.

    15. A is not B pattern
    \b%A%\sis\snot\s%B%\b -> Why do you think that %A% is not %B%?
    Description: Any sentence containing the string "i like X" where X is a placeholder word

    16. Asking pattern
    \bcan\syou\stell\sme\swhat\s%X%\sis\b -> Unfortunately, I don't know what %X% is. Perhaps you could search it on google?
    Description: Any sentence containing the string "can you tell me what X is" where X is a placeholder word

    17. Confession advise pattern
    \bhow\scan\sI\sconfess\smy\slove\sto\s%X%\b -> Just ask %X% out already you big baby!
    Description: Any sentence containing the string "how can I confess my love to %X% " where X is a placeholder word

    18. B hates A pattern
    \b%B%\shates\s%A%\b -> Really? Did %A% do anything wrong to %B%?
    Description: Any sentence containing any placeholder word B followed by the word "hates" followed by any placeholder word A.

    19. I'm bad at X pattern
    \bi'm\sbad\sat\s%X%\b -> You should practice doing %X% a lot more! Go ask some friends for help! Or maybe %X% isn't your thing.
    Description: Any sentence containing the string "i'm bad at X" where X is a placeholder word

    20. I'm good at X pattern
    \bi'm\sgood\sat\s%X%\b -> Meh I could do better.
    Description: Any sentence containing the string "i'm good at X" where X is a placeholder word

    ===================================================================================================================

    1.3 Explanation on the weird symbols...

    These are what the weird characters ("\b","|",etc) actually mean:
    1. |: the OR operator ("foo|bar" means contains the word foo or bar)
    2. \b: word boundary (word delimiter)
    3. \s: white space (space, newline, tabs etc)
    4. %<any alphabetic character>%: placeholders (e.g. %X%)

    For example, take a look at the following pattern/response pair.
        \bhate\b -> Hate is not a good thing.
    -This line means for any input that contains the word "hate",
    the program will print the response "Hate is not a good thing."
    -The pattern "\bhate\b" means a word boundary, followed by the string "hate", followed by another word boundary

    Another example with the OR operator "|":
        \bhi\b|\bhello\b|\bhey\b -> How are you? What would you like to talk about today?
    -This line means for any input that contains the word "hi", "hello", or "hey",
    the program will print the response "How are you? What would you like to talk about today?"
    -At this point you may be thinking why can't we just use "hi|hello|hey" and has to include the word boundary.
    Well if I do that let's say the user enters "nothing" the response "How are you..." will also be produced because
    "hi" is a substring of "nothing". The word boundary "\b" is actually used to prevent this from happening.

    Last example with placeholders and "\s":
        \bi\slove\s%X%\b -> Oh you're in love with %X% are you? Is %X% a person?
    -This line means for any input that contains "I love %X%" where "%X%" is a any string,
    the program will produce the output "Oh you're in love with %X% are you? Is %X% a person?"
    where "%X%" is the original string typed by the user.
    -For example if the user inputs "i love coding" the program will produce the response
    "Oh you're in love with coding are you? Is coding a person?"

    CAUTION:
    -Do not attempt to use the "|" operator for patterns with placeholders
    -Do not use the weird characters ("\b", "\s", etc.) on responses. Only use these in patterns.

*************************************************************************************************************************************

Error/violation reporting and log files:

1. As per the requirement the program will not print any indication of program malfunction.
This includes timing violations as well as the file I/O errors.

2. When the program startup the folder "log" and the log files listed bellow will be created.
The program will write to these log files if there are any errors/timing violations.

    - io_error_log.txt
        A log file which contains log entries of any file I/O errors (such as missing input files).

    - timing_log.txt
        A log file which contains log entries of any timing violations

3. However there's a bit of a catch!
-The reporting of the file I/O errors are written in the "io_error_log.txt" which is also a file.
-This means if the file I/O error is related to this file (this file goes missing in the way, etc),
there's no other way of reporting so I just make the program exit if this is the case
-If there is a file I/O error related to this particular file the program will just print "Exiting program..."