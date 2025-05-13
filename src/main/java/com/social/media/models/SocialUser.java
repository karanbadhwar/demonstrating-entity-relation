package com.social.media.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name="SocialUser") // user is a Keyword in H2-database, will throw error while creating a table with User...
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE} ) // Mapped by will have the Field name that is used in the Owning Table/Entity.
//    @JoinColumn(name="social_profile_id")
    private SocialProfile socialProfile;

    @OneToMany(mappedBy = "socialUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Post> posts = new ArrayList<>();

//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany(fetch = FetchType.LAZY)
//    @ManyToMany
    @EqualsAndHashCode.Exclude
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<SocialGroup> groups = new HashSet<>();

    // All explanation down below.
    public void setSocialProfile(SocialProfile socialProfile){
        socialProfile.setUser(this);
        this.socialProfile = socialProfile;
    }

}



//### ⚠️ Why having **foreign keys in both tables** is usually **bad** (for One-to-One relationships):
//
//#### 🤝 One-to-One mapping means:
//
//> One record in Table A is related to **exactly one** record in Table B, and vice versa.
//
//Now, if **both tables have foreign keys to each other**, it creates **redundancy and confusion**, because:
//
//---
//
//### 🔄 1. **Circular Dependency**
//
//Both tables will be waiting for each other during inserts:
//
//* Table A needs Table B's ID
//* Table B needs Table A's ID
//
//This creates a **chicken-and-egg problem**. You can't insert either without the other.
//--
//### ❗ 2. **Unnecessary Redundancy**
//
//Two foreign keys for the same relationship = double tracking the same thing. For example:
//
//```sql
//-- Table A
//id | table_b_id (FK)
//
//-- Table B
//id | table_a_id (FK)
//```
//
//Which one is "true"? What if someone updates one and forgets the other? This **increases complexity and risk of inconsistency**.
//
//---
//
//### 💣 3. **Breaks Referential Integrity**
//
//If you try to delete a row in one table, and two foreign keys reference each other, the database might:
//
//* Refuse to delete (`FOREIGN KEY constraint fails`)
//* Cascade in both directions (if cascade delete is on), potentially **deleting both records unintentionally**
//
//---
//
//### ✅ Best Practice: Put the foreign key in **only one table**
//
//Typically, you choose the table that:
//
//* **Owns** the relationship (in domain terms), or
//* Makes more sense logically or performance-wise (e.g., the "dependent" entity holds the FK)
//
//In JPA, this is called the **owning side** of the relationship.
//---
//### 🧠 Example in Java:
//
//```java
//@Entity
//public class SocialProfile {
//    @Id
//    private Long id;

//    @OneToOne
//    @JoinColumn(name = "user_id")  // FK goes here
//    private SocialUser user;
//}
//```

//This means:
//
//* The `social_profile` table has a `user_id` foreign key
//* `SocialUser` doesn't need to have a back-reference (unless bidirectional mapping is needed)
//---
//### TL;DR

//| Having FK in One Table | Having FK in Both Tables |
//| ---------------------- | ------------------------ |
//| ✅ Clean, simple        | ❌ Redundant, confusing   |
//| ✅ Easy to insert data  | ❌ Hard to insert data    |
//| ✅ Consistent state     | ❌ Risk of inconsistency  |


//✅ 1. How Jackson Calls Your Custom Setter
//When Jackson (used by Spring Boot under the hood) deserializes JSON into a Java object:
//
//It first creates the object using the no-arg constructor (@NoArgsConstructor is important).
//
//Then, for each JSON field, it:
//
//Looks for a setter method matching the field name (e.g., setSocialProfile for "socialProfile").
//
//If found, it calls your method — even if it's custom (i.e., contains logic like socialProfile.setUser(this)).
//
//So your custom logic inside the setter gets executed as long as the method name matches the JSON property.
//
//📌 Jackson prefers setters for deserialization. If none is found, it can use reflection to directly set fields — but it checks for methods first.
//
//✅ 2. How Jackson Knows It's Another Entity (e.g., SocialProfile)
//When parsing this JSON:
//
//json
//Copy
//Edit
//{
//  "socialProfile": {
//    "description": "Test"
//  }
//}
//Jackson uses the type of the socialProfile field in your SocialUser class to know what to map the inner JSON to:
//
//java
//Copy
//Edit
//private SocialProfile socialProfile;
//It sees this field and:
//
//Says, “Okay, I need to map the inner JSON to a SocialProfile object.”
//
//Creates a SocialProfile instance.
//
//Fills in its fields like description.
//
//So it's all based on Java field types + matching JSON structure.
//
//✅ TL;DR
//Question	Answer
//How is the custom setter called?	Jackson looks for a setSocialProfile() method during deserialization and calls it with the parsed SocialProfile object.
//How does Jackson know it's another entity?	It looks at the Java type of the field (SocialProfile), and maps the nested JSON accordingly.



//```json
//{
//  "socialProfile": {
//    "description": "Test profile"
//  }
//}
//```
//
//### 💡 Assume your Java class looks like this:
//
//```java
//public class SocialUser {
//    private SocialProfile socialProfile;
//
//    public void setSocialProfile(SocialProfile socialProfile) {
//        socialProfile.setUser(this); // custom logic
//        this.socialProfile = socialProfile;
//    }
//}
//```
//
//---
//
//## 🔍 What Jackson Does (Step-by-Step)
//
//---
//
//### 🔸 1. Jackson starts deserialization
//
//Spring Boot receives your JSON payload. It detects the controller method argument is a `SocialUser`, so it asks Jackson:
//
//> "Please convert this JSON to a `SocialUser` object."
//
//---
//
//### 🔸 2. Jackson creates the object
//
//* It finds a **no-arg constructor** and does:
//
//  ```java
//  SocialUser user = new SocialUser();
//  ```
//
//---
//
//### 🔸 3. Jackson reads the JSON field `socialProfile`
//
//* It sees that your JSON contains:
//
//  ```json
//  "socialProfile": { "description": "Test profile" }
//  ```
//
//* It looks at the field in the class:
//
//  ```java
//  private SocialProfile socialProfile;
//  ```
//
//* It understands: “Ah, I need to create a `SocialProfile` object for this nested JSON.”
//
//---
//
//### 🔸 4. Jackson creates the `SocialProfile`
//
//```java
//SocialProfile profile = new SocialProfile();
//profile.setDescription("Test profile");
//```
//
//(Uses setters or field access to populate it.)
//
//---
//
//### 🔸 5. Jackson finds the `setSocialProfile()` method
//
//Now it calls your custom setter:
//
//```java
//user.setSocialProfile(profile);
//```
//
//Inside this method:
//
//```java
//socialProfile.setUser(this); // sets back-reference
//this.socialProfile = socialProfile;
//```
//
//So your bidirectional link is created here.
//
//---
//
//### 🔸 6. Jackson finishes and hands over to Spring
//
//* `SocialUser` is now fully populated with the nested `SocialProfile`.
//* Control goes to your controller method (e.g., `@PostMapping` handler).
//
//---
//
//## ✅ Summary Flow Chart
//
//```text
//JSON  ───>  Jackson → new SocialUser()
//               │
//               └─> Detects field: socialProfile
//                         │
//                         └─> Creates SocialProfile
//                                │
//                                └─> Calls setSocialProfile()
//                                          │
//                                          └─> Runs custom logic
//```
//
//---
//


//2. Cascade Parent (Lifecycle Control):
//The side that has the @OneToMany(cascade = ...) is considered the "lifecycle parent" (not the owning side in JPA, but the one that can cascade operations like delete).
//
//Example:
//
//java
//Copy
//Edit
//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//private List<Post> posts;
//✅ Here, User is the cascading parent, and deleting it (with cascade) will delete its associated Posts.