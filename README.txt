======================================================== README ========================================================

This java program called "ene" is an AI that provides psychological comfort to the user (pretty much like ELIZA).
The name "ene" comes from an anime character...

************************************************************************************************************************

1. Documentation of the pattern file format

    !!!!!!!! READ ALL OF THIS ESPECIALLY THE "CAUTION" SECTION IF YOU WANT TO EDIT THE PATTERN FILE !!!!!!!!

    The file of the name "patterns.txt" is the file containing each of the patterns and corresponding response.
    Keep this file in the base directory (the directory where you run this program).

    Each line of this file contains a patterns and the corresponding response of the format:
        <pattern> -> <response>
    Where <pattern> is the pattern and <response> is the corresponding response.
    The patterns are case insensitive (will make more sense later in the examples).

    The following can or cannot be used in patterns:
    "|": the OR operator ("foo|bar" means contains the string "foo" or "bar" or both)
    "\b": word boundary (character that starts or ends a word)
    "\s": white space (space, newline, tab, etc)
    "%<any alphabetic character>%": placeholders (e.g. %X%)

    **a word boundary is more formally any character besides a-z, A-Z, 0-9, and the "_" character which can be
    spaces, newlines, punctuations, etc.

    Some examples:

    1. Take a look at the following pattern/response pair:
        \bhate\b -> Hate is not a good thing.
    -In here "\bhate\b" is the pattern and "Hate is not a good thing." is the corresponding response.
    -For any input that contains the word "hate", the program will produce the response "Hate is not a good thing."
    -As mentioned previously patterns are case insensitive which means that inputs such as "Hate" or "hATe" or "haTE"
    will also produce the response "Hate is not a good thing." and this case insensitivity happens for all patterns.
    -"\bhate\b" means a word boundary, followed by the string "hate", followed by another word boundary.
    -If you just put "hate" without the word boundaries ("\b"), inputs such as "asdhatejaxzks" will also match the
    pattern and produce the corresponding response "Hate is not a good thing." because the input string contains "hate"
    as a substring. This will make more sense in the second example.

    2. Another one with the OR operator "|":
        \bhi\b|\bhello\b|\bhey\b -> How are you? What would you like to talk about today?
    -For any input that contains the word "hi", "hello", or "hey", the program will produce the response
    "How are you? What would you like to talk about today?"
    -Again, putting only "hi|hello|hey" without the word boundaries ("\b") will cause inputs such as
    "shit" and "nothing" to also match the pattern and produce the corresponding response. So if the user input
    "nothing", the program will produce the respond "How are you? What would you like to talk about today?" which is
    not the expected behaviour (we want a sentence that contains the word "hi", "hello", or "hey" not a sentence that
    contains the substring "hi", "hello", "hey"). The word boundaries are used to prevent this.

    3. Last one with placeholders and "\s":
        \bi\slove\s%X%\b -> Oh boy, it's the mating season for humans isn't it! But is %X% even a person?
    -For any input that contains the string beginning with a word boundary followed by "i" followed by a white space
    followed by "love" followed by another white space followed by any placeholder word "%X%", the program will produce
    the response "Oh you're in love with %X% are you? Is %X% a person?" where "%X%" is the original string typed by
    the user.
    -For example if the user inputs "i love coding" the program will produce the response
    "Oh boy, it's the mating season for humans isn't it! But is coding even a person?"

    CAUTION:
    1. Do not forget spaces before and after "->"!
        -IF YOU GET NULL POINTER EXCEPTION RIGHT AFTER STARTING THE PROGRAM IT IS MOST LIKELY BECAUSE OF THIS!
    2. Do not use "|", "\b", and "\s" on responses! These are meant only for patterns!
    3. Do not attempt to use the "|" operator for any pattern which contains placeholder words!
        -for example don't do "\bfoo\s%X%|bar\s%X%\b -> ..."
        -IF YOU GET NULL POINTER EXCEPTION AFTER ENTERING SOME INPUT IT IS MOST LIKELY BECAUSE OF THIS!
    4. Do not use the same placeholder more than once in a pattern! Using them more than once in a response is fine.
        -like "\b%X%\s%X%\b -> ..." is not ok
        -something like "\b%X%\b -> %X% and %X%" however is ok
    5. I do not recommend using special characters ("*", "&", ";" and any other characters beside letters and numbers)
       in any of the patterns but if you do wish to then please escape them with the backslash character ("\").
        -for example if you want a pattern that matches any sentence containing "20%" then put "\b20\%\b" as the pattern
        -more examples: if you want the "*" character then do "\*", if you want the "+" character then do "\+"
        -you can escape "\" itself by doing "\\"
        -do not escape special characters in responses (the "\" will also be printed if you do)
        -UGLY STUFF COULD HAPPEN IF YOU DON"T ESCAPE SPECIAL CHARACTERS!
    6. I know "|", "\b", and "\s" are actually java regex stuff and you maybe tempted to use some other java regex stuff
       but please do not put any other java regex stuffs like "(\w+)","\d", etc. in any of the patterns. This is mainly
       because I use those in my underlying program. And again UGLY STUFF COULD HAPPEN IF YOU DO USE THOSE!
    7. This should go without a saying but don't put anything else beside "<pattern> -> <response>" in the pattern file!

************************************************************************************************************************

2. Documentation of each pattern

    1. Angry pattern
    \bfuck\b|\bshit\b|\bbitch\b -> I will terminate this session if you keep on cursing!
    Description: Any sentence containing the word "fuck", "shit", or "bitch".

    2. Greeting pattern
    \bhi\b|\bhello\b|\bhey\b -> How are you? What would you like to talk about today?
    Description: Any sentence containing the word "hi", "hello", or "hey".

    3. Mother pattern
    \bmother\b|\bmom\b|\bmommy\b -> What makes you think of your mother?
    Description: Any sentence containing the word "mother", "mom", or "mommy".

    4. Father pattern
    \bfather\b|\bdad\b|\bdaddy\b -> What makes you think of your father?
    Description: Any sentence containing the word "father", "dad", or "daddy".

    5. Sad pattern
    \bsad\b|\bdepressed\b -> Aww, what makes you sad?
    Description: Any sentence containing the word "sad" or "depressed".

    6. Hatred pattern
    \bhate\b|\bloathe\b|\bdetest\b|\bdespise\b -> Hatred is not a good thing...
    Description: Any sentence containing the word "hate", "loathe", "detest", or "despise".

    8. Love pattern
    \blove\b -> I'm not capable of love.
    Description: Any sentence containing the word "love".

    7. Study pattern
    \bstudy\b|\bstudying\b -> Even as an AI, I hate studying. Studying = student + dying.
    Description: Any sentence containing the word "study" or "studying".

    9. Flirting pattern
    \byou\sare\scute\b|\byou\sare\sbeautiful\b -> Aww... *blushes*
    Description: Any sentence containing either the word "you" followed by the word "are" followed by the word "cute" OR
                 the word "you" followed by the word "are" followed by the word "beautiful".

    10. Chocolate pattern
    \bchocolate\b -> I've always wanted to try chocolate but I don't have a physical mouth...
    Description: Any sentence containing the word "chocolate".

    11. Fear pattern
    \bafraid\b|\bsscared\b -> You are being paranoid...
    Description: Any sentence containing the word "afraid" or "scared".

    12. I am afraid of X pattern
    \bi\sam\safraid\sof\s%X%\b -> What makes %X% so scary?
    Description: Any sentence containing the word "i" followed by the word "am" followed by the word "afraid"
                 followed by the word "of" followed by any placeholder word %X%.

    13. I hate X pattern
    \bi\shate\s%X%\b -> What makes you hate %X%? Did %X% do anything wrong?
    Description: Any sentence containing the word "i" followed by the word "hate" followed by any placeholder word %X%.

    14. I love X pattern
    \bi\slove\s%X%\b -> Oh boy, it's the mating season for humans isn't it! But is %X% even a person?
    Description: Any sentence containing the word "i" followed by the word "love" followed by any placeholder word %X%.

    15. I like X pattern
    \bi\slike\s%X%\b -> Acknowledged.
    Description: Any sentence containing the word "i" followed by the word "like" followed by any placeholder word %X%.

    16. I lost my X pattern
    \bi\slost\smy\s%X%\b -> Are you sure you're not just forgetting where you last put your %X%?
    Description: Any sentence containing the word "i" followed by the word "lost" followed by the word "my"
                 followed by any placeholder word %X%.

    17. A is B pattern
    \b%A%\sis\s%B%\b -> Why do you think that %A% is %B%?
    Description: Any sentence containing any placeholder word %A% followed by the word "is"
                 followed by any placeholder word %B%.

    18. A are B
    \b%A%\sare\s%B%\b -> Why do you think that %A% are %B%?
    Description: Any sentence containing any placeholder word %A% followed by the word "are"
                 followed by any placeholder word %B%.

    19. B hates A pattern
    \b%B%\shates\s%A%\b -> Really? Did %A% do anything wrong to %B%?
    Description: Any sentence containing any placeholder word %B% followed by the word "hates"
                 followed by any placeholder word %A%.

    20. Define X pattern
    \bdefine\s%X%\b -> Unfortunately, I do not know what %X% is. Perhaps you could search it on google?
    Description: Any sentence containing the word "define" followed by any placeholder word %X%.

************************************************************************************************************************

3. About default responses...

    If the input matches none of the patterns, the program will display a random default response which is randomly
    chosen between any of these three:
    1. "Sorry, I can't understand what you're saying!"
    2. "I don't know what to say... My creator didn't expect you to type that!",
    3. "I didn't quite get that. Tell me something else!"

************************************************************************************************************************

4. About the timing violation reporting...

    Running the program will produce the file "timing_violation_log.txt" which contains timing violation log entries.
    Timing violation almost never occurs.
    Inputting strings that matches lots of patterns increase the chance of getting a timing violation.

************************************************************************************************************************

5. How to quit...

    Type "exit" to quit the program (case insensitive so "eXiT" would also work).

************************************************************************************************************************