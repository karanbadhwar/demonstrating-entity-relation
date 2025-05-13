package com.social.media.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne()
    @JoinColumn(name = "social_user")
    @JsonIgnore
    private SocialUser user;

    private String description;

    public void setSocialUser(SocialUser socialUser){
        this.user = socialUser;
        if(socialUser.getSocialProfile() != this){
            socialUser.setSocialProfile(this);
        }
    }
}

//JoinColumn
//This tells JPA:
//Create a foreign key column named social_user in the SocialProfile, table that references the primary key of SocialUser.
// Moreover, The owning side will have the JoinColumn, if it is Bi-Directional Relationship

//🔍 How JPA decides ownership:
//Attribute	Description
//@JoinColumn	The side with this annotation is the owner. It contains the foreign key.
//mappedBy = "..."	Used on the inverse side to refer to the owning side's field.


//🔍 What is mappedBy in JPA?
//    mappedBy is an attribute used in JPA annotations (@OneToOne, @OneToMany, etc.)
//    to indicate that this side is not the owner of the relationship, and the foreign key is handled by the other side.
//It literally means:
//    "The mapping is defined by the field on the other side of the relationship."