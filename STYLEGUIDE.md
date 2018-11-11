# STYLEGUIDE
### For FTC Team 8581 Aedificatores.

![alt text](https://pbs.twimg.com/profile_images/823235140067409920/3bLfT8sI_400x400.jpg)

The style guide IS NOT A STRICT SET OF RULES.
Flexibility exists, and breaking any conventions set is ok if the programmer is conscious of his/her decision, and it doesn't hinder readability/writability not only for yourself but for the other programmers.

Also, feel free to propose changes to the styleguide.

### BRACKETS
* Use *Egyptian* Brackets:
```java
if (bar == 30) {
	System.out.println("bat");
}
```
**Do not** use:
```java
if (bar == 30)
{
	System.out.println("bat");
}
```

That's just weird and confusing and makes finding what statements go with what brackets hard.

* One liners are ok for in same cases, like:
```java
if (shorty != 3) foo();
```

This just makes reading the code so much nicer.

DO NOT do this with multiple statements.
```java
while (true) {if (shorty != 3) foo();}
```

BUT, this is acceptable
```java
while (true) {
	if (shorty != 3) foo();
}
```

Brackets should be excluded from such single-liners
```java
if (shorty != 3) {foo();} // This is a no no
if (shorty != 3) foo();   // This does the same thing and has a lot less clutter.
```

* With if-statements, try and keep the same style throughout the entirety of the if-else clause.

i.e. this is bad:
```java
if (foo == 1) die();
else if (foo == 3) {
	live();
} else {transcend();} // This line also breaks previous convention
```

This is better:
```java
if (foo == 1 ) {
	die();
} else if (foo == 3) {
	live();
} else {
	transcend();
}
```

Or, if you prefer, this:
```java
if (foo == 1) die();
else if (foo == 3) live();
else transcend();
```
But, is discouraged.

* **NEVER** do this for function or class definitions with at least 1 line within them. (So pretty much never.)
so...
```java
void doNothing() {} // This...
```
Is just as good as...

```java
void stillDoNothing() { // This.
}
```

* The rest about brackets is up to programmer preferences, so long as it is easy to read. And don't be picky.

### COMMENTS
* Make comments **clear** and **readable,** capitalize your comments when they are full sentences.

* When you are making a TODO, be clear and specific about what needs to be done.

For example.
```java
void badFunction() { // TODO Refactor code to not rely on badFunction
	while (true) {
		openNewJVMInstance();
	}
}
```

* When making little notes to yourself, make sure you either tell everybody that it is a note just for you.

For example.

```java
if (foo == bar) say("hello"); // mark
```
Like, wtf does this mean?
Tell us more or get rid of it, it's like going out and speaking random nonsense. Or,
**Pro Tip**: use `NOTE`.


* Comments are used as a LAST RESORT. Programmers should not be reliant on comments to explain poopy code Also, refrain from making fancy shmancy ASCII art in comments like this:

```java
/*- - - - - - - - - - - -
 *	THE CLASS           |
 *   AUTHOR: SMOLBOT	|
 *	 8581 (c)	        |
 - - - - - - - - - - - */
```

Please **no**.

* As in line with FTC, make sure that you are putting your name at the top, you can be as verbose as you want,
preferably put the date.

* For multi - line comments, it's fine if you do whatever in terms of style. Nobody really cares.

```java
/* This is good.
	And is cool.
*/

/*
This is also cool.
And good.
*/

/*
 * And if you're weird and do this,
 * you can do that too.
 */

// And, instead of multi line comments where you use the languages built-in features,
// you can just make it by making multiple comments in a line if you want.

/* However, the only thing that isn't fine is making a lot of unnecessary space, that includes
 multi - line comments with only one line in them, like what??? Why would you every do that???
*/

/*
Seriously <- not fine
*/

// Seriously <- good and cool
```

* Everything else is at the programmer's discretion.



### CLASSES AND METHODS
* When naming classes capitalize the first letter. Use camel case.
```java
public class Lemon extends Fruit {
	foo();
	// Filler!
}
```

Do not use:
```java
public class Realistic_Fiction extends Fruit {
	// Wait why is this a fruit?
}

public class realisticFiction extends Fruit {
	// Seriously, tell me why.
}

public class Realisticfiction extends Fruit {
	// I have no clue.
}
```
These are ALL wrong.
because...
It makes no sense. Which is something that you shouldn't be doing.


* Method names should represent predicates, like "getValue" "moveMotor."
	* Also, will almost certainly be verbs.

* When defining methods / functions / macros use camel case with the first letter lowercase, for example:
```java
boolean isTrue() {
	if bar == true {return true;}
}
```



### VARIABLES AND CONSTANTS
* Please be VERY clear in communicating what the variable does, a good rule of thumb is:

If someone who is not you can be reading the file that that function is called in and have a fairly good idea of what that function does, then you're good.


For example. This. . .
```java
void doThing() {
	System.out.print("aosidh");
}
```

. . . is dumb and poopy and dumb. This. . .
```java
void printJibberish() {
	System.out.print("uaiodbguiosbhd");
}
```

. . . is 9000% better since it actually says what it does.

* Comments describing methods should say WHAT the method does, not HOW. (From linux kernal style guide). Same applies with classes.

* This also ties into some variable naming rules. Regarding acronyms and such.

* Declarations should be clear and direct, make it as simple as possible, but...
Simple != Small, avoid acronyms at all costs.

For instance. The Google style guide illustrates this perfectly:
```java
String unitAbbrev = "μs";

// vs

String unitAbbrev = "\u03bcs";
```
The second was very hard to understand because we as humans just don't get it.

* While it is ok if you don't do this, try and line up variables in a rapidfire definition of variables.
Try not to spread it around the code.

For example.
```java
int amphibians = 3;
int toads      = 4;
int frogs      = 2;
int fish       = 8;
int elephants  = 1;
```
Is better than
```java
int amphibians = 3;
int toads = 4;
int frogs = 2;
int fish = 8;
int elephants = 1;
```
 * Use spaces not tabs, see spacing.


* **NO ABBREVIATIONS, OR ACRONYMS, PLEASE**

The only abbreviations you should be using are common things like dt for delta time.
Or basic stuff like languages, for example:

```java
String hello_en = "Hello";
String hello_ka = "გამარჯობა";
String hello_fr = "Bonjour";
String hello_fi = "Hei";
String hello_de = "Hallo";
String hello_ru = "Здравствуйте";
```

Or
```java
double dt_standard = 2.1;
```

Here's a bad example
```java
int rm = 3; // How are we supposed to know what this means?
```

Shortenings are ok, but try and stay away from them as much as possible, and try and use commonly
used abbreviations.
For example:
```java
final int STAND_TIME_IN_HOSAKA_JP_MIN = 30;
```

So as you can see, standard as been shortened to stand, just because this variable name is lucridously long,
(NOTE: This variable name is just an example, a better name would be STANDARD_HOSAKA_TIME_MIN, we'll talk about
that later)
And min is usually understood by most people as being minutes, or minimum.

For constants, use all caps snake case.

Like:
```java
final double START_TIME = 13.33;
```


* For anything that is a "attribute" use snake case to delineate that type.

For example.
```java
String hello_en = "Hello";
String hello_ka = "გამარჯობა";
String hello_fr = "Bonjour";
String hello_fi = "Hei";
String hello_de = "Hallo";
String hello_ru = "Здравствуйте";
```

	* We used this earlier, the reason hello_ is there is to show that is part of a set of variables that
		all pertain to the greeting "hello", or however you say it.

	* So this is basically a type that doesn't get noticed by the compiler, so it's abstraction just for you.


* As the great Linus Torvalds once said, don't be dainty about your variable names.

None of this:

```java
String thisIsAVariable = "this is a variable";
```


* CONSTANTS should use UPPER_SNAKE_CASE. . .
```java
public static final int MR_FOO = 15;
```

. . . and should generally be used to eliminate magic numbers in the code.

* For temporary variables, use an `_`  before a variable, this is anything that isn't all that relevant.

* Lastly, don't be afraid to make long variable names.

* Joke method names are fine if everyone else is OK with it.

### INSTANCES
When you make an instance of a class, either name it something else that makes sense, or you can name it the name of the class.

Example:
```java
private GyroAngles gyroAngles;
// or
private SystemTimer timer;
```

### STATE MACHINES
* State machines should pair enums with switch-cases.

Example:
```java
enum Foo {STEP_UNO, STEP_DOS}

Foo foo;

public void init() {
	foo = Foo.STEP_UNO;
}

public void loop() {
	switch (foo) { // note that 'case' is indented the same amount as 'switch'
	case STEP_UNO:
		foo = Foo.STEP_DOS
		break;
	case STEP_DOS:
		break;
	}


}
```

## Some other conventions:
Motors will always be named:
```java
public DcMotor rf, lf, ra, la;
```

Divide your imports based on the domains of the URLs.
Example:
```java
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
```

Please be consistent.
