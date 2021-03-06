package com.calmeter.core.account.model;

import javax.persistence.*;

import com.calmeter.core.account.controller.UserDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonDeserialize(using = UserDeserializer.class)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	private String firstname;

	private String lastname;

	private String password;

	private String passwordConfirm;

	private String email;

	private Boolean enabled;

	private Set<UserRole> roles;

	private Boolean isUserProfileSet;

	private UserProfile UserProfile;

	public User() {
		isUserProfileSet = false;
		roles = new HashSet<>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@Transient
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	@Column(name = "email", nullable = false, unique = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role_map", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public Boolean getIsUserProfileSet() {
		return isUserProfileSet;
	}

	public void setIsUserProfileSet(Boolean isUserProfileSet) {
		this.isUserProfileSet = isUserProfileSet;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_profile_id")
	public UserProfile getUserProfile() {
		return UserProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		UserProfile = userProfile;
	}

}
