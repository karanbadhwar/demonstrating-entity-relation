package com.social.media.config;

import com.social.media.repositories.PostRepository;
import com.social.media.repositories.SocialProfileRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.social.media.models.Post;
import com.social.media.models.SocialGroup;
import com.social.media.models.SocialProfile;
import com.social.media.models.SocialUser;
import com.social.media.repositories.GroupRepository;
import com.social.media.repositories.SocialUserRepository;

@Configuration
public class DataInitializer {

    private final SocialUserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final PostRepository postRepository;

    //Constructor Initializing of the Repositories
    public DataInitializer(SocialUserRepository userRepository, GroupRepository groupRepository, SocialProfileRepository socialProfileRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create some users
            SocialUser user1 = new SocialUser();
            SocialUser user2 = new SocialUser();
            SocialUser user3 = new SocialUser();

            // Save users to the database
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // Create some groups
            SocialGroup group1 = new SocialGroup();
            SocialGroup group2 = new SocialGroup();

            // Add users to groups
            group1.getSocialUsers().add(user1);
            group1.getSocialUsers().add(user2);

            group2.getSocialUsers().add(user2);
            group2.getSocialUsers().add(user3);

            // Save groups to the database
            groupRepository.save(group1);
            groupRepository.save(group2);

            // Associate users with groups
            user1.getGroups().add(group1);
            user2.getGroups().add(group1);
            user2.getGroups().add(group2);
            user3.getGroups().add(group2);

            // Save users back to database to update associations
            // Imp to store the changes to the Combined "user_group" table...
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);


            // Create some posts
            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();

            // Associate posts with users
            post1.setSocialUser(user1);
            post2.setSocialUser(user2);
            post3.setSocialUser(user3);

            // Save posts to the database (assuming you have a PostRepository)
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            // Create some social profiles
            SocialProfile profile1 = new SocialProfile();
            SocialProfile profile2 = new SocialProfile();
            SocialProfile profile3 = new SocialProfile();

            // Associate profiles with users
            profile1.setUser(user1);
            profile2.setUser(user2);
            profile3.setUser(user3);

            // Save profiles to the database (assuming you have a SocialProfileRepository)
            socialProfileRepository.save(profile1);
            socialProfileRepository.save(profile2);
            socialProfileRepository.save(profile3);


            // FETCH TYPES
            System.out.println("FETCHING SOCIAL USER");
            userRepository.findById(1L);
        };
    }
}

//🚀 What is CommandLineRunner?
//CommandLineRunner is a special interface in Spring Boot used to run code after the application context is loaded,
// and just before the Spring Boot app fully starts.
//It’s mostly used for:
//    Seeding the database with initial data
//    Running test logic
//    Running setup code at startup


//✅ "Application context is loaded" means:
//    Spring has finished scanning, creating, configuring, and wiring all your beans,
//    and the application is fully initialized — but it hasn’t started serving requests yet.

//🔍 Now Your Code
//@Bean
//public CommandLineRunner initializeData() {
//    You're telling Spring: “I want to run some logic after the application is fully initialized.”
//    You return a CommandLineRunner bean, which Spring will automatically detect and execute at startup.