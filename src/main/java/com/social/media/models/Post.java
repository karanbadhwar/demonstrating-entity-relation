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
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private SocialUser socialUser;
}

//"What if I want SocialUser to be the owning side?" But then I realized that in a relational database,
// a single SocialUser cannot store multiple Post IDs in one column. That’s why the foreign key must always be on the 'many' side,
// making @ManyToOne the default owner.

//✅ @ManyToOne is always the owning side (no mappedBy needed).
//✅ @OneToMany is the inverse side and requires mappedBy to reference the owner.