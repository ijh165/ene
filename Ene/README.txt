
===========================DOCUMENTATION===========================


Input files:

1. "patterns.txt"

    1.1 What's in it?

    Each line of this file contains a patterns and the corresponding response of the format:
        <pattern> -> <response>
    where pattern is the pattern and response is the corresponding response.

    For example, take a look at the following:
        \bhate\b -> Hate is not a good thing.
    -In here, "\bhate\b" is the pattern and "Hate is not a good thing." is the corresponding response

    CAUTION:
    -Do not forget the spaces before and after "->"

    1.2 Deeper explanation about patterns and responses

    First of all, please keep in mind that the patterns are case insensitive
    (only the patterns though! the responses are still case sensitive).

    These are what the weird characters ("\b","|",etc) actually mean:
    1. |: the OR operator ("foo|bar" means contains the word foo or bar)
    2. \b: word boundary (the beginning and ending of a word)
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
    -You can use the same placeholder in responses but not in patterns
        For example, this is valid:
            \bi\swant\s%X%\b -> %X% and %X%
        But this is not:
            \b%X%\sand\s%X%\b -> %X%

Information about error/violation reporting and log files:

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
-This means if the file I/O error is related to this file, there's no other way