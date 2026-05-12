# EXPERIMENT 6 - INHERITANCE AND POLYMORPHISM IN JAVA
## Complete Viva Notes

---

## TABLE OF CONTENTS
1. [Single Inheritance](#1-single-inheritance-banking-transaction)
2. [Multilevel Inheritance](#2-multilevel-inheritance-device-hierarchy)
3. [Hierarchical Inheritance](#3-hierarchical-inheritance-course-management)
4. [Method Overriding](#4-method-overriding-concepts)
5. [Polymorphism - Runtime](#5-runtime-polymorphism)
6. [Abstract Classes](#6-abstract-classes-payment-system)
7. [Final Classes](#7-final-classes-logger-system)
8. [Super Keyword](#8-super-keyword)
9. [Access Modifiers](#9-access-modifiers)
10. [Key Points & FAQ](#10-key-points--faq)

---

## 1. SINGLE INHERITANCE: BANKING TRANSACTION

### Definition
Single Inheritance is when a derived class inherits from only one base class. It is the simplest form of inheritance.

### Class Structure (Q1_BankingTransaction.java)
```
Base Class: Account
    ↓
Derived Class: SavingsAccount
```

### Key Concepts

#### A) Base Class (Account)
- Contains basic properties common to all accounts
- **Properties**: `accountNumber`, `accountHolder`
- **Methods**: Getter/Setter methods, `displayDetails()`
- Can have **default constructors** (no arguments)
- Can have **parameterized constructors** (with arguments)

#### B) Derived Class (SavingsAccount)
- Inherits all properties and methods from Account
- Uses `extends` keyword: `class SavingsAccount extends Account`
- **Additional properties**: `interestRate`
- **Constructor chaining**: Uses `super()` to call parent constructor
- **@Override annotation**: Overrides parent method `displayDetails()`

### Constructor Chaining in Single Inheritance
```java
// Parent Constructor
public Account(String accountNumber, String accountHolder) {
    this.accountNumber = accountNumber;
    this.accountHolder = accountHolder;
}

// Child Constructor with super()
public SavingsAccount(String accountNumber, String accountHolder, double interestRate) {
    super(accountNumber, accountHolder);  // Calls parent constructor
    this.interestRate = interestRate;
}
```

### Method Overriding
- Child class `SavingsAccount` overrides `displayDetails()` method
- Calls parent method using `super.displayDetails()` and adds additional functionality
- Uses `@Override` annotation for compile-time checking

### Code Execution Flow
```
SavingsAccount savingsUser = new SavingsAccount(accNum, name, interest);
↓
Calls SavingsAccount constructor
↓
super(accNum, name) is called first
↓
Account constructor executes
↓
SavingsAccount constructor completes
↓
Object is created successfully
```

### Advantages
- Code reusability (Account properties used by SavingsAccount)
- Logical hierarchy
- Easy maintenance

---

## 2. MULTILEVEL INHERITANCE: DEVICE HIERARCHY

### Definition
Multilevel Inheritance is when a derived class is inherited by another class. Inheritance goes down multiple levels: **A → B → C** pattern.

### Class Hierarchy (Q2_DeviceHierarchy.java)
```
Device (Base Class)
    ↓
Computer (Intermediate Class - inherits from Device)
    ↓
Laptop (Derived Class - inherits from Computer)
```

### Three-Level Structure

#### Level 1: Device (Base Class)
- **Properties**: `deviceName`
- **Methods**: Getter/Setter, default constructor
- **Constructor**: Default constructor prints initialization message

#### Level 2: Computer (Intermediate Class)
- Extends Device
- **Additional properties**: `processor`
- Calls `super()` to initialize Device
- Prints its own initialization message

#### Level 3: Laptop (Final/Derived Class)
- Extends Computer
- **Additional properties**: `batteryLife`
- Calls `super()` to initialize Computer
- Computer constructor calls `super()` to initialize Device
- Each level prints initialization message showing execution order

### Constructor Execution Order
```
Laptop laptop = new Laptop(name, processor, battery);
↓
Laptop constructor called
  ↓
  super() calls Computer constructor
    ↓
    Computer constructor called
      ↓
      super() calls Device constructor
        ↓
        Device constructor executes
        Prints: "1. Device (Base Class) initialized."
      ↓
      Computer constructor completes
      Prints: "2. Computer (Intermediate Class) initialized."
    ↓
  ↓
Laptop constructor completes
Prints: "3. Laptop (Derived Class) initialized."
```

### Property Access Pattern
- Laptop has access to: `deviceName` (from Device), `processor` (from Computer), `batteryLife` (own)
- Can access using getter methods: `getDeviceName()`, `getProcessor()`, `getBatteryLife()`

### Key Characteristics
- **Depth**: Inheritance chain creates a depth relationship
- **Code reuse**: Each class reuses properties from its parent
- **Cascading initialization**: Constructor calls cascade from derived to base class

### Important Notes
- Default constructor calls: If no explicit `super()` call, Java automatically calls parent's default constructor
- Explicit `super()` must be first statement in constructor
- Each class adds its own specialized functionality

---

## 3. HIERARCHICAL INHERITANCE: COURSE MANAGEMENT

### Definition
Hierarchical Inheritance occurs when multiple derived classes inherit from a single base class. One parent, **many children**.

### Class Hierarchy (Q3_CourseManagement.java)
```
          Course (Base Class)
           /        \
          /          \
  OnlineCourse    OfflineCourse
  (Derived)       (Derived)
```

### Structure

#### A) Base Class: Course
```java
class Course {
    public double calculateCourseFee() {
        return baseFee;  // Base implementation
    }
}
```
- Contains common properties: `courseName`, `baseFee`
- Defines method: `calculateCourseFee()` (can be overridden)

#### B) Derived Class 1: OnlineCourse
```java
class OnlineCourse extends Course {
    @Override
    public double calculateCourseFee() {
        return getBaseFee() + platformFee;  // Adds platform fee
    }
}
```
- **Additional property**: `platformFee`
- Overrides `calculateCourseFee()` to add platform fee

#### C) Derived Class 2: OfflineCourse
```java
class OfflineCourse extends Course {
    @Override
    public double calculateCourseFee() {
        return getBaseFee() + facilityFee;  // Adds facility fee
    }
}
```
- **Additional property**: `facilityFee`
- Overrides `calculateCourseFee()` to add facility fee

### Key Features
1. **Single Parent**: Both OnlineCourse and OfflineCourse inherit from Course
2. **Polymorphic Behavior**: Different implementations of `calculateCourseFee()`
3. **Code Reuse**: Both classes inherit name and baseFee from Course
4. **Independent Extensions**: OnlineCourse and OfflineCourse are independent of each other

### Advantages
- **Share common code**: Both course types inherit basic properties
- **Specialized implementations**: Each type implements specific logic
- **Scalability**: Easy to add more course types (e.g., HybridCourse)

---

## 4. METHOD OVERRIDING: CONCEPTS

### Definition
Method Overriding is a mechanism to achieve **runtime polymorphism** where a child class provides a specific implementation of a method that is already defined in the parent class.

### Rules for Method Overriding

1. **Same Method Name**: Must have identical name as parent class method
   ```java
   Parent: public void displayDetails() { }
   Child:  public void displayDetails() { }  // Same name ✓
   ```

2. **Same Parameter List**: Parameters must match exactly
   ```java
   Parent: public void processPayment(double amount) { }
   Child:  public void processPayment(double amount) { }  // Same params ✓
   Child:  public void processPayment(int amount) { }     // Different - ERROR ✗
   ```

3. **Same Return Type**: Return type must be same or covariant
   ```java
   Parent: public double getAmount() { }
   Child:  public double getAmount() { }  // Same return type ✓
   ```

4. **No Reduction in Access Level**
   ```java
   Parent: public void method() { }
   Child:  private void method() { }  // Reduced access - ERROR ✗
   Child:  public void method() { }   // Same or increased - OK ✓
   ```

5. **Cannot Override Static Methods**: Static methods cannot be overridden
   ```java
   public static void staticMethod() { }  // Cannot override
   ```

6. **Cannot Override Final Methods**: Final methods cannot be overridden
   ```java
   public final void finalMethod() { }  // Cannot override
   ```

### @Override Annotation
- Not mandatory but **highly recommended**
- Helps catch errors at compile time
- Improves code readability
- Informs developer this method overrides parent method

### Examples from Code

#### Example 1: Banking Transaction (Q1)
```java
// Parent class
public void displayDetails() {
    System.out.println("Account Details");
}

// Child class
@Override
public void displayDetails() {
    super.displayDetails();  // Call parent implementation
    System.out.println("Interest Rate: " + interestRate);  // Add child-specific info
}
```

#### Example 2: Course Management (Q3)
```java
// Parent
public double calculateCourseFee() {
    return baseFee;
}

// Child - OnlineCourse
@Override
public double calculateCourseFee() {
    return getBaseFee() + platformFee;
}

// Child - OfflineCourse
@Override
public double calculateCourseFee() {
    return getBaseFee() + facilityFee;
}
```

### Method Resolution Order
- JVM looks for method starting from the **actual runtime class** of the object
- If not found, searches parent class, then grandparent, etc.
- Goes up the hierarchy until method is found

---

## 5. RUNTIME POLYMORPHISM

### Definition
Runtime Polymorphism (Dynamic Binding) allows a parent class reference to point to child class objects, and method calls are resolved at **runtime based on the actual object type**.

### Key Concept
```java
Parent reference;
reference = new Child1();  // Can reference child1
reference = new Child2();  // Can reference child2

// Method called depends on actual object, not reference type
reference.method();  // Calls Child1's method if referencing Child1 object
```

### How It Works

#### Step 1: Declare Parent Reference
```java
Course courseRef;  // Reference type is parent class
```

#### Step 2: Point to Child Object
```java
if(type.equals("online"))
    courseRef = new OnlineCourse(...);
else
    courseRef = new OfflineCourse(...);
```

#### Step 3: Call Method
```java
courseRef.calculateCourseFee();  // Which implementation runs?
```

### Method Resolution at Runtime
```
At COMPILE TIME:
courseRef is of type Course
↓
Course has method calculateCourseFee()
↓
Compilation succeeds

At RUNTIME:
courseRef actually points to OnlineCourse object
↓
OnlineCourse's calculateCourseFee() is called
↓
Returns baseFee + platformFee
```

### Type Casting in Polymorphism
```java
// Upcasting (always safe)
OfflineCourse offline = new OfflineCourse();
Course course = offline;  // Implicit conversion

// Downcasting (needs explicit typecast)
Course course = new OnlineCourse();
OnlineCourse online = (OnlineCourse) course;  // Must be explicit

// Check before downcasting
if(course instanceof OnlineCourse) {
    OnlineCourse online = (OnlineCourse) course;
}
```

### Real-World Example from Code
```java
// Code snippet from CourseManagement.java and PaymentSystem.java
Course c;

if(type.equalsIgnoreCase("online"))
    c = new OnlineCourse();
else
    c = new OfflineCourse();

c.calculateCourseFee();  // Dynamic binding happens here
// If c references OnlineCourse → OnlineCourse method runs
// If c references OfflineCourse → OfflineCourse method runs
```

### Advantages
1. **Flexibility**: Can add new types without changing existing code
2. **Extensibility**: Easy to extend with new derived classes
3. **Loose Coupling**: Code depends on parent class interface, not specific implementations
4. **Code Reuse**: Same code works with any derived class

---

## 6. ABSTRACT CLASSES: PAYMENT SYSTEM

### Definition
An abstract class is a **class that cannot be instantiated** directly. It serves as a **blueprint for derived classes**. It can contain abstract methods (without implementation) and concrete methods (with implementation).

### Declaration
```java
abstract class Payment {
    // Can have concrete methods
    public void validatePayment() { }
    
    // Can have abstract methods
    public abstract void processPayment();
}
```

### Why Abstract Classes?

1. **Cannot Instantiate**: Cannot create objects directly
   ```java
   Payment p = new Payment();  // COMPILATION ERROR ✗
   ```

2. **Must Implement Abstract Methods**: Derived classes must override all abstract methods
   ```java
   class CreditCardPayment extends Payment {
       @Override
       public void processPayment() {
           // Implementation required
       }
   }
   ```

3. **Provide Contract**: Abstract methods define a contract that all derived classes must follow

### Structure (PaymentSystem.java)

#### Abstract Class
```java
abstract class Payment {
    private double amount;
    
    public Payment(double amount) {
        this.amount = amount;
    }
    
    // Concrete method
    public double getAmount() { return amount; }
    
    // Abstract method - no implementation
    public abstract void processPayment();
}
```

#### Concrete Implementation 1
```java
class CreditCardPayment extends Payment {
    private String cardNumber;
    
    public CreditCardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }
    
    @Override
    public void processPayment() {
        System.out.println("Processing Credit Card: " + cardNumber);
    }
}
```

#### Concrete Implementation 2
```java
class UPIPayment extends Payment {
    private String upiId;
    
    public UPIPayment(double amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }
    
    @Override
    public void processPayment() {
        System.out.println("Processing UPI: " + upiId);
    }
}
```

### Key Rules

1. **Cannot instantiate**: `new Payment()` → ERROR
2. **Can create reference**: `Payment p;` → OK
3. **Reference to subclass**: `p = new CreditCardPayment()` → OK
4. **Must implement ALL abstract methods** in derived class
5. **Can be partial implementation**: If derived class is also abstract
6. **Can have constructors**: Derived class calls `super()`
7. **Can have static/final methods**: These are not abstract

### Abstract vs Concrete Methods

| Aspect | Abstract Method | Concrete Method |
|--------|-----------------|-----------------|
| Implementation | No | Yes |
| Declaration | `abstract void method();` | `void method() { }` |
| Can override | Must in subclass | Optional in subclass |
| Usage | Define contract | Provide shared implementation |

### Access Level in Abstract Class
```java
abstract class Payment {
    private double amount;      // Private - only in Payment
    protected String reference; // Protected - visible to subclasses
    public double getAmount();  // Public - visible everywhere
}
```

### Advantages
1. **Enforce Implementation**: Derived classes must implement required methods
2. **Shared Code**: Can have concrete methods that subclasses inherit
3. **Polymorphism**: Use abstract class references with any implementation
4. **Design Pattern**: Defines clear interface/contract

---

## 7. FINAL CLASSES: LOGGER SYSTEM

### Definition
A `final` class is a class that **cannot be extended/inherited**. Once declared as final, no other class can inherit from it.

### Declaration
```java
final class Logger {
    // Cannot extend this class
}

class MaliciousLogger extends Logger {  // COMPILATION ERROR ✗
    // This will not compile
}
```

### Use Cases

1. **Security**: Prevent unauthorized subclassing
   ```java
   final String text = new String("Secure");  // Cannot subclass String
   ```

2. **Immutability**: Prevent modification through inheritance
3. **API Stability**: Ensure class behavior doesn't change through subclassing
4. **Performance**: JVM can optimize final classes better

### Structure (LoggerSystem.java)

#### Final Class
```java
final class Logger {
    public Logger() {}
    
    public void logMessage(String message) {
        System.out.println("[LOG] " + LocalDateTime.now() + " : " + message);
    }
}
```

### What's Allowed with Final Classes

#### ✓ Can Instantiate
```java
Logger log = new Logger();  // OK
```

#### ✓ Can Use Methods
```java
log.logMessage("Application started");  // OK
```

#### ✓ Can Have Constructor
```java
public Logger() { }  // OK
```

#### ✓ Can Have Methods
```java
public void logMessage(String message) { }  // OK
```

#### ✗ Cannot Extend
```java
class ExtendedLogger extends Logger { }  // COMPILATION ERROR
```

### Final Methods vs Final Classes

#### Final Method
```java
class Parent {
    final void importantMethod() { }  // Cannot override
}

class Child extends Parent {
    void importantMethod() { }  // ERROR - cannot override final method
}
```

#### Final Class
```java
final class Parent { }  // Cannot extend

class Child extends Parent { }  // ERROR - cannot extend final class
```

### Built-in Final Classes in Java
- `java.lang.String` - Final class, cannot be extended
- `java.lang.Integer`, `java.lang.Double` - Final wrapper classes
- `java.lang.System` - Final class
- Many utility classes are final for security

### Advantages
1. **Security**: Prevent unauthorized modifications
2. **Immutability**: Ensures object behavior is constant
3. **Performance**: Compiler/JVM can apply optimizations
4. **Design Intent**: Clearly indicates class is not meant to be extended

### Disadvantages
1. **Limited Flexibility**: Cannot customize through inheritance
2. **Reduced Extensibility**: If requirements change, cannot subclass
3. **Code Reuse**: Cannot reuse through inheritance

### Design Consideration
- Use final when you're **certain** the class should never be extended
- Use final for utility classes, security-critical classes
- Avoid using final unless there's a specific reason

---

## 8. SUPER KEYWORD

### Definition
The `super` keyword is used to refer to the **immediate parent class object**. It is used to:
1. Call parent class constructor
2. Call parent class method
3. Access parent class variable (rarely)

### Types of Usage

### Type 1: Calling Parent Constructor
```java
class Parent {
    public Parent(String name) {
        this.name = name;
    }
}

class Child extends Parent {
    public Child(String name, int age) {
        super(name);  // Calls Parent constructor
        this.age = age;
    }
}
```

**Rules**:
- Must be **first statement** in constructor
- If not explicitly called, Java calls parent's default constructor automatically
- Can pass arguments to parent constructor

### Type 2: Calling Parent Method
```java
class Parent {
    public void display() {
        System.out.println("Parent");
    }
}

class Child extends Parent {
    @Override
    public void display() {
        super.display();  // Calls parent method
        System.out.println("Child");  // Then adds child's logic
    }
}
```

**Usage**:
- To extend parent functionality rather than replace it
- `super.methodName()` to explicitly call parent version
- Useful in overridden methods to maintain parent behavior

### Type 3: Accessing Parent Variable
```java
class Parent {
    protected int value = 10;
}

class Child extends Parent {
    int value = 20;  // Child's own value
    
    void display() {
        System.out.println(super.value);  // Accesses parent's value (10)
        System.out.println(this.value);   // Accesses child's value (20)
    }
}
```

### Multilevel Inheritance with Super

#### Example: Device Hierarchy (Q2)
```java
class Device {
    Device(String name) {
        this.deviceName = name;
    }
}

class Computer extends Device {
    Computer(String name, String proc) {
        super(name);  // Calls Device constructor
        this.processor = proc;
    }
}

class Laptop extends Computer {
    Laptop(String name, String proc, int batt) {
        super(name, proc);  // Calls Computer constructor
        this.batteryLife = batt;
    }
}
```

**Execution Flow**:
```
Laptop constructor called
    ↓
super(name, proc) → Computer constructor
    ↓
    super(name) → Device constructor
    ↓
Device initialization complete
    ↓
Computer initialization complete
    ↓
Laptop initialization complete
```

### Super vs This

| super | this |
|-------|------|
| Refers to parent class | Refers to current class |
| `super()` calls parent constructor | `this()` calls current class constructor |
| `super.method()` calls parent method | `this.method()` calls current class method |
| `super.variable` accesses parent variable | `this.variable` accesses current class variable |

### Important Points
1. Cannot call `super` without inheritance
2. Cannot call `super` in static context
3. Must be first statement in constructor if used
4. If `super()` not called explicitly, Java calls parent's default constructor

---

## 9. ACCESS MODIFIERS

### Types and Visibility

| Modifier | Same Class | Same Package | Subclass (Different Package) | Outside |
|----------|-----------|--------------|------------------------------|---------|
| `public` | ✓ | ✓ | ✓ | ✓ |
| `protected` | ✓ | ✓ | ✓ | ✗ |
| `package` (default) | ✓ | ✓ | ✗ | ✗ |
| `private` | ✓ | ✗ | ✗ | ✗ |

### Usage in Inheritance

#### Public Members
```java
class Parent {
    public int publicVar;  // Visible everywhere
    public void publicMethod() { }  // Visible everywhere
}

class Child extends Parent {
    void test() {
        System.out.println(publicVar);  // Can access
        publicMethod();  // Can call
    }
}
```

#### Protected Members
```java
class Parent {
    protected int protectedVar;  // Visible to subclasses
    protected void protectedMethod() { }
}

class Child extends Parent {
    void test() {
        System.out.println(protectedVar);  // Can access
        protectedMethod();  // Can call
    }
}
```

#### Private Members
```java
class Parent {
    private int privateVar;  // Only visible in Parent
    private void privateMethod() { }
}

class Child extends Parent {
    void test() {
        System.out.println(privateVar);  // CANNOT access - ERROR
        privateMethod();  // CANNOT call - ERROR
    }
}
```

### Design Best Practice
- **Variables**: Keep `private`, provide public getter/setter
- **Methods**: 
  - `private` for internal helper methods
  - `protected` for methods meant to be overridden
  - `public` for interface methods

### From Course Code Examples
```java
// Q1 Banking Transaction
class Account {
    private String accountNumber;  // Private - accessed via getter/setter
    private String accountHolder;  // Private
}

class SavingsAccount extends Account {
    private double interestRate;  // Private
    
    // Can access via inherited public methods
    public String getAccountHolder() {
        return accountHolder;  // Via parent's public method
    }
}
```

---

## 10. KEY POINTS & FAQ

### Key Takeaways

#### Q1: Single Inheritance
✓ One parent, one child
✓ SavingsAccount extends Account
✓ Simple, direct relationship
✓ Reuses Account properties

#### Q2: Multilevel Inheritance
✓ Chain of inheritance: Device → Computer → Laptop
✓ Each level adds its own properties
✓ Constructors call cascade from child to parent
✓ All properties available down the chain

#### Q3: Hierarchical Inheritance
✓ One parent, multiple children: OnlineCourse and OfflineCourse from Course
✓ Each child can override methods differently
✓ Siblings don't share each other's properties
✓ Reuses common code from parent

#### Q4: Method Overriding
✓ Child provides specific implementation of parent method
✓ Same signature required
✓ Called using parent reference due to polymorphism
✓ Runtime decision which method to call

#### Q5: Runtime Polymorphism
✓ Parent reference points to child object
✓ Method resolution happens at runtime
✓ Based on actual object type, not reference type
✓ Enables flexible, extensible code

#### Q6: Abstract Classes
✓ Cannot be instantiated: `new Payment()` → ERROR
✓ Can have both abstract and concrete methods
✓ Derived classes must implement abstract methods
✓ Use abstract reference with concrete objects

#### Q7: Final Classes
✓ Cannot be extended: `extends Logger` → ERROR
✓ Can be instantiated: `new Logger()` → OK
✓ Used for security, immutability
✓ JVM can optimize better

#### Q8: Super Keyword
✓ Calls parent constructor (must be first statement)
✓ Calls parent method (to extend functionality)
✓ Access parent variable (in simple inheritance)
✓ Essential for constructor chaining

---

### Frequently Asked Questions

**Q: Can an abstract class have concrete methods?**
A: Yes! Abstract class can have both abstract methods (no implementation) and concrete methods (with implementation). Child classes must implement abstract methods but can inherit concrete methods.

**Q: Can we instantiate abstract class?**
A: No. `new Payment()` gives compilation error. But we can create references: `Payment p;` and point to concrete objects: `p = new CreditCardPayment();`

**Q: What's the difference between abstract class and interface?**
A: Abstract class can have concrete methods and state variables. Interface (Java 8+) can have default methods and static methods. Abstract class is `is-a` relationship, interface is `can-do` relationship.

**Q: Why use `super` in constructor?**
A: Parent class constructor initializes parent properties. Child must call parent constructor to properly initialize all properties down the hierarchy.

**Q: Can we override private methods?**
A: No. Private methods are not inherited, so sub-class methods with same name are new methods, not overrides.

**Q: Can we override static methods?**
A: No. Static methods belong to class, not object. It's called method hiding, not overriding.

**Q: Why is final class important?**
A: Prevents unauthorized modification through inheritance. `String` class is final so it cannot be extended and broken. Ensures immutability and security.

**Q: What's the output of multilevel inheritance constructor calls?**
A: Constructors execute from child to parent (following super calls), but parent constructors complete first due to execution stack. Order: Parent body → Intermediate body → Child body.

**Q: Can final method be overridden?**
A: No. `final` keyword on method prevents overriding. `final void show() { }` cannot be overridden in subclass.

**Q: Can abstract method have body?**
A: No. Abstract method must not have body: `abstract void method();` If it has body, it's concrete method, not abstract.

**Q: What is method hiding?**
A: When derived class has static method with same signature as parent's static method. It's hiding, not overriding. JVM calls based on reference type (compile time), not object type.

---

### Important Concepts Map

```
INHERITANCE
├── Single Inheritance
│   └── One parent → One child
│       Example: Account → SavingsAccount
│
├── Multilevel Inheritance
│   └── Parent → Child → Grandchild (chain)
│       Example: Device → Computer → Laptop
│
├── Hierarchical Inheritance
│   └── One parent → Multiple children
│       Example: Course → OnlineCourse, OfflineCourse
│
└── Multiple Inheritance (Not directly in Java)
    └── Achieved through interfaces

METHOD OVERRIDING
├── Same method name
├── Same parameters
├── Same return type
└── Used for polymorphism

POLYMORPHISM
├── Runtime (Dynamic)
│   └── Method decision at runtime based on object
│       Parent ref = new Child(); ref.method();
│
├── Compile Time (Static)
│   └── Method overloading (not in this experiment)
│
└── Benefits
    ├── Flexibility
    ├── Extensibility
    └── Loose coupling

SUPER KEYWORD
├── super() for constructor chaining
├── super.method() for calling parent method
└── Must be first statement in constructor

ACCESS MODIFIERS IN INHERITANCE
├── public → visible everywhere
├── protected → visible to subclasses
├── default → visible in same package
└── private → NOT inherited

ABSTRACT CLASSES
├── Cannot instantiate
├── Can have abstract methods (contract)
├── Can have concrete methods (implementation)
├── Subclass must implement all abstract methods
└── Used for enforcing method implementation

FINAL CLASSES
├── Cannot be extended
├── Can be instantiated
├── Used for security, immutability
└── Examples: String, Integer, System

```

---

## COMMON MISTAKES & DEBUGGING

### Mistake 1: Forgetting super() in Constructor
```java
// WRONG ❌
class Child extends Parent {
    Child(String name) {
        this.name = name;  // Parent not initialized!
    }
}

// CORRECT ✓
class Child extends Parent {
    Child(String name) {
        super(name);  // Initialize parent first
        this.name = name;
    }
}
```

### Mistake 2: Trying to Extend Final Class
```java
// WRONG ❌
class MyLogger extends Logger { }  // Logger is final!

// CORRECT ✓
Logger log = new Logger();  // Use composition/encapsulation
```

### Mistake 3: Instantiating Abstract Class
```java
// WRONG ❌
Payment pay = new Payment();  // Cannot instantiate abstract class

// CORRECT ✓
Payment pay = new CreditCardPayment();  // Use concrete subclass
```

### Mistake 4: Not Implementing All Abstract Methods
```java
// WRONG ❌
class MyPayment extends Payment {
    // Missing processPayment() implementation
}

// CORRECT ✓
class MyPayment extends Payment {
    @Override
    public void processPayment() {
        // Implementation
    }
}
```

### Mistake 5: Accessing Private Members from Child
```java
// WRONG ❌
class SavingsAccount extends Account {
    void show() {
        System.out.println(accountNumber);  // Private, cannot access
    }
}

// CORRECT ✓
class SavingsAccount extends Account {
    void show() {
        System.out.println(getAccountNumber());  // Use public accessor
    }
}
```

---

## SUMMARY TABLE

| Concept | Definition | Example | Usage |
|---------|-----------|---------|-------|
| Single Inheritance | One parent, one child | Account → SavingsAccount | Basic code reuse |
| Multilevel | Chain of inheritance | Device → Computer → Laptop | Hierarchical relationships |
| Hierarchical | One parent, multiple child | Course → Online, Offline | Shared code in siblings |
| Overriding | Child implements method | Both classes have displayDetails() | Polymorphic behavior |
| Polymorphism | Runtime method selection | `Parent ref = new Child()` | Flexibility and extensibility |
| Abstract Class | Cannot instantiate | `abstract class Payment` | Define contract for subclasses |
| Final Class | Cannot extend | `final class Logger` | Prevent inheritance |
| Super | Reference parent class | `super()`, `super.method()` | Constructor chaining and method extension |

---

**END OF VIVA NOTES**

---

## QUICK REVISION POINTS (For last-minute prep)

1. **Inheritance**: Code reuse mechanism, derived class inherits from base class
2. **Single Inheritance**: Simplest form, one parent-child relationship
3. **Multilevel**: Chain relationship, constructor calls cascade
4. **Hierarchical**: One parent, multiple children inherit separately
5. **Method Overriding**: Child provides specific implementation
6. **Polymorphism**: Use parent reference with child objects, method resolution at runtime
7. **Abstract Class**: Blueprint, cannot instantiate, enforces implementation through abstract methods
8. **Final Class**: Cannot be extended, used for security and immutability
9. **Super**: Calls parent constructor/method, must be first statement if in constructor
10. **Access Modifiers**: public (everywhere), protected (subclasses), private (none outside class)

---
