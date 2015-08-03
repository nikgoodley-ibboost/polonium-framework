# Start #
Extend test class with PoloniumTest to access new functionality. Now all tests are required to be annotated with given/when/then descriptions. Remember: those three are used only for test description.
Other JUnit 4.0 annotations allowed (@Before, @After, etc... )
```
public class SimpleTest extends PoloniumTest{
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void shouldServiceBeOK(){
          ...
}
```

# Description #
By default your IDE will generate new test description, according to this above:

![http://polonium-framework.googlecode.com/files/common.jpg](http://polonium-framework.googlecode.com/files/common.jpg)

To use more detailed decription, where test is root and has 3 children, annotate you class with @DetailedDescription or extend DetailedPoloniumTest.

```
@DetailedDescription
public class SimpleTest extends PoloniumTest{
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void shouldServiceBeOK(){
         ...
```

Result would be:

http://polonium-framework.googlecode.com/files/coreok.PNG

# Exceptions #
By default polonium recognizes 3 types of provided exceptions Given/When/ThenException. Throw certain to have one of description marked as fail. Example:

```
public class SimpleService {
	public static boolean serviceLocked = true;
	
	public SimpleService(){
		if(serviceLocked){
			throw new GivenException("Cannot create service instance, it is blocked");
		}
	}
```

The result of test running would be:

http://polonium-framework.googlecode.com/files/coregivenfail.PNG

What if other type of exception will be thrown ? Answer: root and 3 children will be marked as fail. What if you would like to have exception marked as fail of 1 children ? Answer: use one of annotations: MarkedGiven/When/ThenFail.<br />
Exapmle: When NullPointerException thrown, we want to have 'when' fail in result:

```
@DetailedDescription
@MarkedWhenFail({NullPointerException.class})
public class SimpleTest extends PoloniumTest{
	
	@Given("simple service object")
	@When("doService method invoked")
	@Then("result of service is correct")
	@Test
	public void shouldServiceBeOK(){
         ...
```

result would be:

http://polonium-framework.googlecode.com/files/corenullfail.PNG

# Notifier #
In detailed description mode, polonium notifier will also recognize:
  * assertion errors (given, when descriptions will be marked ok, and then will be marked as assertion fail - blue cross)
  * assumption violated errors (root test will be marked ok, all children will be marked as ignore)