================= PLEASE READ ALL OF THIS DOCUMENTATION BEFORE CHANGING ANYTHING IN THE PATTERN FILE!! =================

************************************************************************************************************************

1. Documentation of the pattern file format

    The file of the name "patterns.txt" is the file containing each of the patterns and corresponding response.
    Keep this file in the base directory (the directory where you run this program).

    Each line of this file contains a patterns and the corresponding response of the format:
        <pattern> -> <response>
    where pattern is the pattern and response is the corresponding response.

    Explanation on weird characters ("\b" and "\s" both counts as 1 character not 2 characters!):
    "|": the OR operator ("foo|bar" means contains the string "foo" or "bar" or both)
    "\b": word boundary (the character that starts or ends a word)
    "\s": white space (space, newline, tab, etc)
    "%<any alphabetic character>%": placeholders (e.g. %X%)

    CAUTION:
    -Do not forget spaces before and after "->"!
    -Do not use "|", "\b", and "\s" on responses! These are meant only for patterns!
    -Do not attempt to use the "|" operator for any pattern which contains placeholder words!
    -Do not use the same placeholder more than once in a pattern! Using them more than once in a response is fine.
        -like "\b%X%\s%X%\b -> ..." is not ok
        -something like "\b%X%\b -> %X% and %X%" however is ok

    -I know that "|", "\b", and "\s" look a lot like java regex and you maybe tempted to use some other java regex stuff
    but please do not put any other weird characters besides these 4 in any of the patterns, not even another java regex
    stuffs like "(\w+)","\d", etc.!
    -This should go without a saying but do not put anything else besides "<pattern> -> <response>" in the pattern file!

    Some examples:

    1. Take a look at the following pattern/response pair:
        \bhate\b -> Hate is not a good thing.
    -In here "\bhate\b" is the pattern and "Hate is not a good thing." is the corresponding response
    -For any input that contains the word "hate", the program will print the response "Hate is not a good thing."
    -"\bhate\b" means a word boundary, followed by the string "hate", followed by another word boundary.
    -If you just put "hate" without the word boundaries ("\b"), inputs such as "dsara asdhatejaxzks" will also match the
    pattern and produce the corresponding response because the input string contains the string "hate" as a substring.

    2. Another one with the OR operator "|":
        \bhi\b|\bhello\b|\bhey\b -> How are you? What would you like to talk about today?
    -For any input that contains the word "hi", "hello", or "hey",
    the program will print the response "How are you? What would you like to talk about today?"
    -At this point you may be thinking why can't we just use "hi|hello|hey" and has to include the word boundary.
    Well if I do that let's say the user enters "nothing" the response "How are you..." will also be produced because
    "hi" is a substring of "nothing". The word boundary "\b" is actually used to prevent this from happening.
    -Just like the previous example, putting only "hi|hello|hey" without the word boundaries ("\b") will cause inputs
    such as "shit", "wqhelloworld", "dsaheydsa" to also match the pattern and produce the corresponding response.

    3. Last one with placeholders and "\s":
        \bi\slove\s%X%\b -> Oh boy, it's the mating season for humans isn't it! But is %X% even a person?
    -For any input that contains the string beginning with a word boundary followed by "i" followed by a white space
    followed by "love" followed by another white space followed by any placeholder word "%X%", the program will produce
    the response "Oh you're in love with %X% are you? Is %X% a person?" where "%X%" is the original string typed by
    the user.
    -For example if the user inputs "i love coding" the program will produce the response
    "Oh boy, it's the mating season for humans isn't it! But is coding even a person?"

************************************************************************************************************************

2. Documentation of each pattern

    First of all, please keep in mind that these patterns are case insensitive.
    Only the patterns though! the responses are still case sensitive.

    1. Angry pattern
    \bfuck\b|\bshit\b|\bbitch\b -> I will terminate this session if you keep cursing!
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

    Running this program will produce the file "timing_violation_log.txt" which contains timing violation log entries.

************************************************************************************************************************