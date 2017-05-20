package com.calmeter.core.account.model;


import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {
	
    private Long id;
    private String username;
    private String password;
    private String passwordConfirm;
    private String email;
    private Set<Role> roles;
    private UserProfile UserProfile;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

	@Column(name = "email", nullable = false, unique = true)
	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}
    
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_profile_id")
	public UserProfile getUserProfile () {
		return UserProfile;
	}

	public void setUserProfile (UserProfile userProfile) {
		UserProfile = userProfile;
	}

}

